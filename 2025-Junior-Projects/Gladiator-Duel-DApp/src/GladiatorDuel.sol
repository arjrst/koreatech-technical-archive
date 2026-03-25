// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.7.0 < 0.9.0;

/**
 * @title GladiatorDuel
 * @dev 2개의 플레이어로 베팅 결투 게임 스마트 컨트랙트
 */
contract GladiatorDuel {

    // 상태 변수 

    uint256 private duelCounter; //private 부호 없는 정수 변수 선언 , 각 결투에 대한 고유 ID를 생성하는 데 사용 

    enum GameState { Open, InProgress, Finished } //gamestate enum 선언, enum은 결투의 가능한 상태를 나타내는 상수 집합을 정의
    //open은 결투가 생성되어 두 번째 플레이어를 기다림, inprogress은 두 플레이어가 모두 참여하여 결투가 진행 중인 상태, finished 결투가 종료된 상태 

    struct Duel { //struct(구조체) 선언 
        address payable player1; //이더 받을 수 있는 첫번째 플레이어의 주소 
        address payable player2; //이더 받을 수 있는 두번째 플레이어의 주소 
        uint256 bet; //결투를 위해 두 플레이어가 베팅하는 이더 (wei로)
        GameState state; //결투 현재 상태
        uint8 player1_hp; //현재 플레이어 1의 HP(health points) 
        uint8 player2_hp; //현재 플레이어 2의 HP(health points) 
        address currentPlayer; //현재 attack 턴을 갖는 플레이어 주소
        uint256 lastActionTimestamp; //결투에서 마지막으로 수행된 동작의 타임스탬프 (시간 기반 규칙에 사용된디)
    }

    mapping(uint256 => Duel) public duels; //Duel 구조체를 저장하기 위한 public 매핑을 생성, 고유한 Duel ID로 접근 가능

    // events

    event DuelCreated(uint256 indexed duelId, address indexed player1, uint256 bet); //새로운 결투가 성공적으로 시작되면 기록하는 event 선언
    event DuelJoined(uint256 indexed duelId, address indexed player2); //두번째 플레이어가 결투에 성공적으로 참가한 것을 기록하는 event
    event Attack(uint256 indexed duelId, address indexed attacker, address indexed defender, bool hit); //결투 중 공격 결과를 기록하는 event
    event DuelFinished(uint256 indexed duelId, address winner); //결투가 종료되고 승자가 결정될 때 기록하는 event
    event BetReclaimed(uint256 indexed duelId, address indexed player); //참여하지 않은 결투에서 플레이어가 베팅을 회수할 때 기록하는 event

    // modifiers

    modifier duelExists(uint256 _duelId) { //주어진 ID를 가진 결투가 실제로 존재하도록 하는 modifier
        require(duels[_duelId].player1 != address(0), "Duel does not exist"); //player1의 주소가 0이 아닌지 확안하여 결투가 존재함을 나타내는 modifier
        _; //확인 후 실행될 함수 코드의 placeholder
    }

    modifier inState(uint256 _duelId, GameState _state) { //결투가 특정 GameState에 속하도록 하는 modifier
        require(duels[_duelId].state == _state, "Duel is not in the correct state"); //결투에 현재 상태가 필요한 상태와 일치하는 확인
        _;
    }

    modifier yourTurn(uint256 _duelId) { //호출 주소가 현재 플레이어의 턴인지 확인 
        require(msg.sender == duels[_duelId].currentPlayer, "It is not your turn"); //결투를 위한 호출자의 주소가 현재 플레이어의 주소와 일치하는지 확인
        _;
    }

    // functions

    function createDuel() external payable { //누구나 이더리움을 전송하여 새로운 결투를 시작할 수 있도록 함 
        require(msg.value > 0, "Bet must be greater than zero"); //거래와 함께 일부 이더리움이 전송되도록 함

        duelCounter++; //새로운 고유 ID를 생성하기 위해 전역 결투 카운터를 증가시킴 
        uint256 duelId = duelCounter; //새로운 카운터 값을 결투의 고유 ID로 할당함

        duels[duelId] = Duel({ //storage에 새로운 Duel 구조체를 생성하고 초기화
            player1: payable(msg.sender), //player1를 이 함수의 호출자로 설정
            player2: payable(address(0)), //player2의 주소를 0로 초기화하여 아직 참여하지 않았음을 나타냄
            bet: msg.value, //호출자가 결투 베팅으로 보낸 이더를 기록
            state: GameState.Open, //결투의 초기 상태를 'open'으로 설정하고 player2를 기다림
            player1_hp: 3, //player1의 시작 HP 설정
            player2_hp: 3, //player2의 시작 HP 설정
            currentPlayer: address(0), //게임이 시작되지 않았으므로 현재 플레이어를 0으로 초기화
            lastActionTimestamp: block.timestamp //현재 블록의 타임스탬프를 기록
        });

        emit DuelCreated(duelId, msg.sender, msg.value); //결투가 성공적으로 생성되었음을 알리는 event를 생성
    }

    function joinDuel(uint256 _duelId) external payable duelExists(_duelId) inState(_duelId, GameState.Open) { //일치하는 베팅을 전송하여 player2가 기존 오플 결투에 참가할 수 있도록 함
        Duel storage duel = duels[_duelId]; //storage에서 특정 결투 데이터에 대한 참조를 가져옴
        require(msg.sender != duel.player1, "Cannot duel yourself"); //player1이 자신의 결투에 참가하지 못하도록 함
        require(msg.value == duel.bet, "Bet must match player 1's bet"); //참가하는 플레이어의 베팅이 player1의 베팅과 일치하도록 함

        duel.player2 = payable(msg.sender); //player2를 이 함수의 호출자로 설정
        duel.state = GameState.InProgress; //두 플레이어가 모두 참여했으므로 상태를 'inprogress'로 변경
        duel.currentPlayer = duel.player1; //player1한테 첫번째 턴을 할당
        duel.lastActionTimestamp = block.timestamp; //마지막 동작의 타임스탬프를 업데이트 

        emit DuelJoined(_duelId, msg.sender); //결투 참여 성공을 알리는 event를 발생시킴
    }

    function attack(uint256 _duelId) external duelExists(_duelId) inState(_duelId, GameState.InProgress) yourTurn(_duelId) { //현재 플레이어가 진행 중인 결투에서 공격을 수행할 수 있도록 함
        Duel storage duel = duels[_duelId]; //storage에서 특정 결투 데이터에 대한 참조를 가져옴
        
        // 명중 확률을 위한 유사 난수 -약 50%
        bytes32 randomHash = keccak256(abi.encodePacked(block.timestamp, block.prevrandao, msg.sender, duelCounter)); //공격이 hit인지 miss인지 결정하기 위한 유사 난수 해시 생성
        bool hit = uint256(randomHash) % 2 == 0; //해시를 기반으로 공격이 hit (true)인지 miss(false)인지 결정

        address defender; //defender의 주소를 저장하는 지역 변수 선언

        if (hit) { //공격이 hit이면 이 블록을 실행 
            if (msg.sender == duel.player1) { //player1이 공경하는 경우 player2의 HP 감소시키고 player2를 defender로 설정
                duel.player2_hp--;
                defender = duel.player2;
            } else { //player2가 공경하는 경우 player1의 HP를 감소시키고 player1를 defender로 설정
                duel.player1_hp--;
                defender = duel.player1;
            }
        } else { //공격이 miss이면 이 블록을 실행
            defender = (msg.sender == duel.player1) ? duel.player2 : duel.player1; //miss 경우에도 누가 defender 되었을지 식별
        }

        emit Attack(_duelId, msg.sender, defender, hit); //공격 세부 정보(공격자, defender, hit)를 기록하는 event를 발생시킴

        if (duel.player1_hp == 0) { //player1의 HP가 0으로 떨어졌는디 확인하고 player2를 승자로 선언하고 결투를 종료하는 내부 함수를 호출 
            _endDuel(_duelId, duel.player2);
        } else if (duel.player2_hp == 0) {
            _endDuel(_duelId, duel.player1); //player2의 HP가 0으로 떨어졌는지 확인하고 player1을 승자로 선언하고 결투를 종료하는 내부 함수를 호출
        } else {
            duel.currentPlayer = (msg.sender == duel.player1) ? duel.player2 : duel.player1; //두 플레이어 중에 아직 승자가 없으면 결투가 계속되고 다른 플레이어에게 차례를 전환
            duel.lastActionTimestamp = block.timestamp; //마지막 동작의 타임스탬프를 업데이트
        } 
    }

    function _endDuel(uint256 _duelId, address payable _winner) internal { //결투를 종료하고 상금을 승자에게 전달하는 내부 helper 함수 
        Duel storage duel = duels[_duelId]; //storage에서 특정 결투 데이터에 대한 참조를 가져옴 
        duel.state = GameState.Finished; //결투 상태를 'finished'로 설정

        uint256 pot = duel.bet * 2; //총 상금(원래 베팅 금액의 두 배)을 계산함
        _winner.transfer(pot); //전체 상금을 지정된 승자한테 전달 

        emit DuelFinished(_duelId, _winner); //결투 종료 및 승자를 알리는 event를 발생시킴 
    }

    function reclaimBet(uint256 _duelId) external duelExists(_duelId) inState(_duelId, GameState.Open) { //제한 시간 내에 결투에 참여한 플레이어가 없으면 플레이어 1이 베팅을 회수할 수 있도록 함
        Duel storage duel = duels[_duelId]; //storage에서 특정 결투 데이터에 대한 참조를 가져옴 
        require(msg.sender == duel.player1, "Only player 1 can reclaim the bet"); //player1만 이 함수를 호출할 수 있도록 함
        require(block.timestamp > duel.lastActionTimestamp + 10 minutes, "Cannot reclaim bet yet, wait 10 minutes"); //결투가 생성된 후 최소 10분이 지나야 함

        duel.state = GameState.Finished; //결투 상태를 'finished'로 설정
        duel.player1.transfer(duel.bet); //player1의 원래 베팅 금액을 player1한테 되돌려줌

        emit BetReclaimed(_duelId, msg.sender); //베팅 회수를 알리는 event를 발생시킴 
    }
}
