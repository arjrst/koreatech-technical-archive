#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include "UART0.h"
#include "ADC.h"

// 서보 모터 핀 정의 (PWM 핀)
#define SERVO1_PIN PH5
#define SERVO2_PIN PH4
#define SERVO3_PIN PH3
#define SERVO4_PIN PE3

// 조이스틱 핀 정의
#define JOYSTICK_X PF0
#define JOYSTICK_BUTTON_PIN PB7

// 버튼 핀 정의
#define BUTTON1_PIN PB4
#define BUTTON2_PIN PB5
#define BUTTON3_PIN PB6
#define BUTTON4_PIN PH6

// 서보 각도 초기화 값 (0° ~ 180°)
int servo_angles[4] = {90, 0, 90, 91};
int joy1X = 512;
int selected_servo = 1;

// 함수 프로토타입
void go_to_home(void);
void playMelody(void);
void read_and_analyze_buttons(void);
void analysis_joystick_data(void);
void control_servo_motors(void);
void init_io(void);
void pwm_init(void);
void init_interrupt(void);
void timer1_init(void);
uint16_t angle_to_pwm(int angle);

int main(void) {
    UART0_init();                            // UART 초기화
    ADC_init(JOYSTICK_X, SINGLE_CONVERSION); // ADC 초기화

    init_io();                               // I/O 핀 초기화
    pwm_init();                              // PWM 초기화
    init_interrupt();                        // 버튼 인터럽트 초기화
    timer1_init();                           // 타이머 인터럽트 초기화

    sei();                                   // 전역 인터럽트 활성화

    go_to_home();                            // 서보를 초기 위치로 이동

    while (1) {
        if (!(PINB & (1 << JOYSTICK_BUTTON_PIN))) {
            playMelody();
            _delay_ms(500);                  // 디바운스 딜레이
        }

        read_and_analyze_buttons();
        joy1X = read_ADC();
        analysis_joystick_data();
        control_servo_motors();

        _delay_ms(30);                       // 안정성을 위한 딜레이
    }
}

// 초기 위치 설정
void go_to_home(void) {
    servo_angles[0] = 90;
    servo_angles[1] = 0;
    servo_angles[2] = 90;
    servo_angles[3] = 91;

    control_servo_motors();
}

// PWM 초기화 (Timer4와 Timer3 사용 예제)
void pwm_init(void) {
    // Timer4 설정 (Servo1, Servo2, Servo3)
    TCCR4A |= (1 << COM4A1) | (1 << COM4B1) | (1 << COM4C1) | (1 << WGM41);
    TCCR4B |= (1 << WGM43) | (1 << WGM42) | (1 << CS41);  // 분주비 8
    ICR4 = 40000;  // 20ms 주기 (50Hz)

    // Timer3 설정 (Servo4)
    TCCR3A |= (1 << COM3A1) | (1 << WGM31);
    TCCR3B |= (1 << WGM33) | (1 << WGM32) | (1 << CS31);
    ICR3 = 40000;
}

// I/O 핀 초기화
void init_io(void) {
    DDRH |= (1 << SERVO1_PIN) | (1 << SERVO2_PIN) | (1 << SERVO3_PIN);
    DDRE |= (1 << SERVO4_PIN);

    DDRB &= ~(1 << JOYSTICK_BUTTON_PIN) & ~(1 << BUTTON1_PIN) & ~(1 << BUTTON2_PIN) & ~(1 << BUTTON3_PIN);
    PORTH |= (1 << BUTTON4_PIN);
    PORTB |= (1 << JOYSTICK_BUTTON_PIN) | (1 << BUTTON1_PIN);
    DDRG |= (1 << PG5); // 부저 핀을 출력으로 설정
}

// 버튼 인터럽트 초기화
void init_interrupt(void) {
    // BUTTON1_PIN을 외부 인터럽트로 설정 (INT6 사용)
    EICRB |= (1 << ISC61) | (1 << ISC60);  // 상승 엣지에서 인터럽트 발생
    EIMSK |= (1 << INT6);                  // 외부 인터럽트 6 활성화
}

// 타이머1 초기화 (1초 주기로 인터럽트 발생)
void timer1_init(void) {
    TCCR1B |= (1 << WGM12) | (1 << CS12) | (1 << CS10);  // CTC 모드, 분주비 1024
    OCR1A = 15624;  // 1초 주기 (16MHz / 1024 / 1Hz)

    TIMSK1 |= (1 << OCIE1A);  // Timer1 비교일치 인터럽트 활성화
}

// ISR (외부 인터럽트 6)
ISR(INT6_vect) {
    selected_servo = 1;  // 버튼 1을 누르면 서보 1 선택
    UART0_print("Button 1 pressed: Servo 1 selected\n");
}

// ISR (Timer1 비교일치 인터럽트)
ISR(TIMER1_COMPA_vect) {
    UART0_print("Timer Interrupt: 1 second elapsed\n");
}

// 버튼 입력 처리
void read_and_analyze_buttons(void) {
    if (PINB & (1 << BUTTON1_PIN)) selected_servo = 1;
    else if (PINB & (1 << BUTTON2_PIN)) selected_servo = 2;
    else if (PINB & (1 << BUTTON3_PIN)) selected_servo = 3;
    else if (PINH & (1 << BUTTON4_PIN)) selected_servo = 4;
}

// 조이스틱 데이터 분석
void analysis_joystick_data(void) {
    if (selected_servo >= 1 && selected_servo <= 4) {
        int* current_servo = &servo_angles[selected_servo - 1];

        if (joy1X < 400) {
            *current_servo -= 5;
            if (*current_servo < 0) *current_servo = 0;
        } else if (joy1X > 630) {
            *current_servo += 5;
            if (*current_servo > 180) *current_servo = 180;
        }
    }
}

// 서보 각도를 펄스 폭으로 변환하는 함수
uint16_t angle_to_pwm(int angle, bool Is_EE) {
    if(Is_EE == 1){
      if (angle < 91) angle = 91;
    }
    else{
      if (angle < 0) angle = 0;
    }
    if (angle > 180) angle = 180;
    return (angle * 17.78) + 1400;  // 0° -> 1400, 180° -> 4600
}

// 서보 모터 제어
void control_servo_motors(void) {
    OCR4A = angle_to_pwm(servo_angles[0], 0);  // Servo 1
    OCR4B = angle_to_pwm(servo_angles[1], 0);  // Servo 2
    OCR4C = angle_to_pwm(servo_angles[2], 0);  // Servo 3
    OCR3A = angle_to_pwm(servo_angles[3], 1);  // Servo 4
}

void playTone(uint16_t frequency, uint16_t duration_ms) {
    // Prevent division by zero
    if (frequency == 0) {
        // Silent pause
        PORTG &= ~(1 << PG5);  // Ensure buzzer is off
        _delay_ms(duration_ms);
        return;
    }

    // Pre-calculate half-period with a constant
    // Use a lookup table or compile-time constant approach
    uint16_t half_period_us;
    switch(frequency) {
        case 262:  half_period_us = 1908; break;  // C4
        case 294:  half_period_us = 1700; break;  // D4
        case 330:  half_period_us = 1515; break;  // E4
        case 349:  half_period_us = 1432; break;  // F4
        case 392:  half_period_us = 1276; break;  // G4
        case 440:  half_period_us = 1136; break;  // A4
        default:   half_period_us = 1000; break;  // Safe default
    }

    // Calculate total number of cycles
    uint32_t total_cycles = ((uint32_t)duration_ms * 1000UL) / (2UL * half_period_us);

    // Generate tone
    for (uint32_t i = 0; i < total_cycles; i++) {
        // Toggle buzzer pin
        PORTG |= (1 << PG5);   // Buzzer ON
        
        // Use multiple fixed _delay_us() calls to approximate variable delay
        for (uint16_t j = 0; j < half_period_us / 10; j++) {
            _delay_us(10);
        }
        
        PORTG &= ~(1 << PG5);  // Buzzer OFF
        
        // Same delay for OFF period
        for (uint16_t j = 0; j < half_period_us / 10; j++) {
            _delay_us(10);
        }
    }
}

void playMelody() {
    // Twinkle Twinkle Little Star melody
    uint16_t melody[] = {
        262, 262, 392, 392, 440, 440, 392, 0,
        349, 349, 330, 330, 294, 294, 262, 0,
        392, 392, 349, 349, 330, 330, 294, 0,
        392, 392, 349, 349, 330, 330, 294, 0,
        262, 262, 392, 392, 440, 440, 392, 0,
        349, 349, 330, 330, 294, 294, 262
    };
    
    const uint16_t noteDuration = 300;
    const uint16_t pauseBetweenNotes = 50;

    for (uint8_t i = 0; i < sizeof(melody) / sizeof(melody[0]); i++) {
        playTone(melody[i], noteDuration);
        
        // Short pause between notes
        PORTG &= ~(1 << PG5);  // Ensure buzzer is off
        _delay_ms(pauseBetweenNotes);
    }
}

