# Gladiator Duel: Decentralized Betting Smart Contract

**Course:** Blockchain Applications    
**Collaboration:** Individual Project  
**Tech Stack:** `Solidity` , `Ethereum (EVM)` , `Smart Contracts` , `Sepolia Testnet`  
**Domain:** Web3, Decentralized Applications (DApps), Smart Contract Security  

***

### Project Overview
A decentralized, turn-based betting application deployed on the Ethereum (Sepolia) testnet. This project explores the core principles of trustless environments by replacing centralized game servers with an immutable smart contract. The system securely escrows cryptocurrency bets, enforces strict turn-based game logic, and automatically distributes payouts to the winner without requiring third-party mediation.

***

### System Architecture & Implementation

* **State Machine Architecture:** Engineered a strict state machine utilizing Solidity `enums` (Open, InProgress, Finished) and custom `structs` to track player health metrics, active turn states, and escrowed balances securely on-chain.
* **Access Control & Security Modifiers:** Authored custom function modifiers (`duelExists`, `inState`, `yourTurn`) to enforce rigorous access control. This ensures state-changing functions can only be executed by authorized addresses during valid game phases, preventing unauthorized interference.
* **Event-Driven Off-Chain Syncing:** Implemented a comprehensive event logging system (`DuelCreated`, `Attack`, `DuelFinished`) to allow external Web3 frontends (e.g., React + Web3.js) to monitor block changes and render real-time UI updates without continuously querying the blockchain.
* **Time-Bound Escrow Logic:** Designed a secure withdrawal mechanism utilizing `block.timestamp` to allow players to reclaim their locked funds if an opponent abandons the session or fails to connect within a predefined timeout window.

***

### Engineering Challenges: Gas Optimization & On-Chain Randomness
A critical challenge in developing this DApp was balancing complex state logic with EVM gas limits and security. During load testing on the Sepolia network, frequent transaction failures occurred due to the high computational cost of the attack logic. This required careful optimization of storage variables to minimize gas fees. 

Furthermore, implementing fair combat mechanics highlighted a major security vulnerability inherent to the EVM: pseudo-randomness. Initially, I utilized `block.timestamp` and `block.prevrandao` to generate the 50% hit-chance logic. However, I identified that this method is vulnerable to miner manipulation. In a production environment with real financial stakes, this architecture would require integration with an off-chain oracle, such as Chainlink VRF (Verifiable Random Function), to guarantee cryptographically secure and tamper-proof randomness.

***

### 🔒 Academic Integrity Note
To comply with university academic integrity policies, the documentation in this repository has been abstracted. Specific grading rubrics and full university reports have been omitted. This repository serves to demonstrate smart contract architecture and Web3 security paradigms without providing a direct, copy-paste solution for active university coursework.

***

### ⚙️ How to View
* Access the `src/` directory to view the core `GladiatorDuel.sol` smart contract and its architectural logic.
* Detailed architectural slides and game mechanics are available in the media/ folder