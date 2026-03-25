package prodCon;

import javax.swing.*;
import java.awt.*;

public class ProducerConsumerGUI {
    private static final int BUFFER_SIZE = 4;
    private static final boolean[] SCENARIO = {
            true,false,false,false,
            true,true,true,true,
            true,true,true,true,
            false,true,true,true,
            false,false,false,false,
            false,false,false,false
    };

    private final int[] buffer = new int[BUFFER_SIZE];
    private int inPtr = 0, outPtr = 0;
    private int nrfull = 0, nrempty = BUFFER_SIZE;
    private int nextItem = 0;
    private String statusMessage = "Ready";

    private boolean producerBlocked = false;
    private boolean consumerBlocked = false;
    private int producerBlockCount = 0;
    private int consumerBlockCount = 0;

    private int step = 0;
    private final CircularBufferPanel panel = new CircularBufferPanel(BUFFER_SIZE);
    private final JLabel statusLabel = new JLabel(statusMessage, SwingConstants.CENTER);

    public ProducerConsumerGUI() {
        for (int i = 0; i < BUFFER_SIZE; i++) buffer[i] = -1;

        JFrame frame = new JFrame("Producer–Consumer Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        statusLabel.setFont(statusLabel.getFont().deriveFont(16f));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        startScenario();
    }

    private void startScenario() {
        Timer timer = new Timer(800, e -> {
            if (step >= SCENARIO.length) {
                statusMessage = "Scenario complete.";
                updateGUI();
                ((Timer)e.getSource()).stop();
                return;
            }
            if (SCENARIO[step]) {
                produce();
            } else {
                consume();
            }
            updateGUI();
            step++;
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private void produce() {
        if (nrempty > 0) {
            int pos = inPtr, item = nextItem++;
            buffer[pos] = item;
            inPtr = (inPtr + 1) % BUFFER_SIZE;
            nrfull++;  nrempty--;
            statusMessage = "Producer produced item " + item + " at A[" + pos + "]";
            producerBlocked = false;
        } else {
            producerBlocked = true;
            producerBlockCount++;
            statusMessage = "Producer blocked #" + producerBlockCount + " (full)";
        }
    }

    private void consume() {
        if (nrfull > 0) {
            int pos = outPtr, item = buffer[pos];
            buffer[pos] = -1;
            outPtr = (outPtr + 1) % BUFFER_SIZE;
            nrfull--;  nrempty++;
            statusMessage = "Consumer consumed item " + item + " from A[" + pos + "]";
            consumerBlocked = false;
        } else {
            consumerBlocked = true;
            consumerBlockCount++;
            statusMessage = "Consumer blocked #" + consumerBlockCount + " (empty)";
        }
    }

    private void updateGUI() {
        SwingUtilities.invokeLater(() -> {
            panel.update(inPtr, outPtr, nrfull, 1,1, nrfull, nrempty,
                    buffer, producerBlocked, consumerBlocked);
            statusLabel.setText(statusMessage);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProducerConsumerGUI::new);
    }

    static class CircularBufferPanel extends JPanel {
        private final int size;
        private int inPtr, outPtr, count, mutexP, mutexC, nrfull, nrempty;
        private int[] bufferSnapshot;
        private boolean producerBlocked, consumerBlocked;

        private final Color emptyColor   = new Color(240,240,240);
        private final Color fullColor    = new Color(173,216,230);
        private final Color pointerColor = new Color(255,165,0);
        private final Color blockedColor = new Color(255,0,0);
        private final Color arrowColor   = new Color(100,100,100);

        CircularBufferPanel(int size) {
            this.size = size;
            setPreferredSize(new Dimension(700,550));
        }

        void update(int inPtr, int outPtr, int count,
                    int mutexP, int mutexC, int nrfull, int nrempty,
                    int[] buffer, boolean prodBlk, boolean consBlk) {
            this.inPtr = inPtr;    this.outPtr = outPtr;
            this.count = count;
            this.mutexP = mutexP;  this.mutexC = mutexC;
            this.nrfull = nrfull;  this.nrempty = nrempty;
            this.bufferSnapshot = buffer.clone();
            this.producerBlocked = prodBlk;
            this.consumerBlocked = consBlk;
            repaint();
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int cx = w/3, cy = h/2, r = Math.min(w,h)/4, slot = 60;

            Font valFont = new Font("SansSerif",Font.BOLD,16);
            Font lblFont = new Font("SansSerif",Font.PLAIN,12);
            g2.setFont(valFont);
            FontMetrics fm = g2.getFontMetrics();

            // compute centers
            Point[] centers = new Point[size];
            for (int i=0;i<size;i++){
                double theta = 2*Math.PI*i/size - Math.PI/2;
                centers[i] = new Point(
                        cx+(int)(r*Math.cos(theta)),
                        cy+(int)(r*Math.sin(theta))
                );
            }

            // draw arrows
            g2.setColor(arrowColor);
            for(int i=0;i<size;i++){
                Point a=centers[i], b=centers[(i+1)%size];
                g2.drawLine(a.x,a.y,b.x,b.y);
                int mx=(a.x+b.x)/2, my=(a.y+b.y)/2;
                int dx=b.x-a.x, dy=b.y-a.y;
                double len=Math.hypot(dx,dy), ux=dx/len, uy=dy/len;
                int hs=8;
                double bx=mx-ux*hs, by=my-uy*hs;
                double px=-uy*hs*0.5, py= ux*hs*0.5;
                Polygon head=new Polygon();
                head.addPoint(mx,my);
                head.addPoint((int)(bx+px),(int)(by+py));
                head.addPoint((int)(bx-px),(int)(by-py));
                g2.fill(head);
            }

            // draw slots & pointers
            for(int i=0;i<size;i++){
                Point p=centers[i];
                int x=p.x, y=p.y;
                // slot background
                g2.setColor(bufferSnapshot[i]!=-1?fullColor:emptyColor);
                g2.fillOval(x-slot/2,y-slot/2,slot,slot);
                // border
                g2.setColor(Color.BLACK);
                g2.drawOval(x-slot/2,y-slot/2,slot,slot);
                // index
                g2.setFont(lblFont);
                String idx="A["+i+"]";
                int lw=g2.getFontMetrics().stringWidth(idx);
                g2.drawString(idx,x-lw/2,y-slot/2-10);
                // value
                g2.setFont(valFont);
                if(bufferSnapshot[i]!=-1){
                    String v=String.valueOf(bufferSnapshot[i]);
                    int vw=fm.stringWidth(v);
                    g2.drawString(v,x-vw/2,y+fm.getAscent()/2);
                }
                // pointers
                g2.setFont(lblFont);
                FontMetrics pfm=g2.getFontMetrics();
                // "In" on left
                if(i==inPtr){
                    String L="In";
                    int pw=pfm.stringWidth(L)+12, ph=pfm.getHeight()+4;
                    int px=x-slot/2-pw-8, py=y-ph/2+pfm.getAscent()/2;
                    g2.setColor(producerBlocked?blockedColor:pointerColor);
                    g2.fillRoundRect(px,py-pfm.getAscent()-2,pw,ph,10,10);
                    g2.setColor(Color.WHITE);
                    g2.drawString(L,px+6,py);
                }
                // "Out" on right
                if(i==outPtr){
                    String L="Out";
                    int pw=pfm.stringWidth(L)+12, ph=pfm.getHeight()+4;
                    int px=x+slot/2+8, py=y-ph/2+pfm.getAscent()/2;
                    g2.setColor(consumerBlocked?blockedColor:pointerColor);
                    g2.fillRoundRect(px,py-pfm.getAscent()-2,pw,ph,10,10);
                    g2.setColor(Color.WHITE);
                    g2.drawString(L,px+6,py);
                }
                g2.setColor(Color.BLACK);
            }

            // draw semaphores
            String[] names={"mutexP:","mutexC:","Full:","Empty:"};
            int[] vals={mutexP,mutexC,nrfull,nrempty};
            g2.setFont(new Font("Monospaced",Font.BOLD,14));
            int sx=(int)(w*0.75), sy=h/3, bw=80, bh=40, gap=50;
            for(int i=0;i<names.length;i++){
                int x=sx, y=sy+i*gap;
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x,y,bw,bh);
                g2.setColor(Color.BLACK);
                g2.drawRect(x,y,bw,bh);
                g2.drawString(names[i],x+10,y+20);
                String v=String.valueOf(vals[i]);
                int wv=g2.getFontMetrics().stringWidth(v);
                g2.drawString(v,x+bw-wv-10,y+20);
            }
        }
    }
}
