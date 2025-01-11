Fraud Detection Project
This project implements a fraud detection system based on network flow algorithms.
The goal is to identify suspicious activities in a financial transaction network
by analyzing data flow between different parties.

Problem Overview
In a network of bank accounts, transactions are modeled as a flow of money between
sources (payer accounts) and sinks (payee accounts). The project utilizes the maximum
flow and minimum cut algorithms to detect anomalies, such as cases where a small
group of payees might be colluding to siphon funds.

Key Files
BoostingAlgorithm.java: Implements boosting techniques for anomaly detection.
Clustering.java: Handles the clustering of accounts based on transaction patterns.
WeakLearner.java: Defines the weak learners used in the boosting algorithm for identifying suspicious behaviors.

Algorithms Used
Edmonds-Karp Algorithm: For solving the maximum flow problem.
Minimum Cut: To determine the boundaries between normal and suspicious transactions.

Running the Project

Compile the Java files:
javac *.java
Run the project:
java BoostingAlgorithm

Credits to Princeton COS 226 various libaries used as part of final project
