# Hashira Placements Assignment

This Java project solves the Hashira Placements polynomial problem.

## Features
- Reads polynomial roots from a JSON file.
- Converts values from different bases to decimal.
- Solves for the constant term using Gaussian elimination with high precision.

## Tools Used
- Java 17+
- Gson 2.8.9 for JSON parsing
- BigInteger and BigDecimal for large number calculations

## How to Run

1. Compile:
javac -cp lib\gson-2.8.9.jar HashiraPlacements.java

2. Run:
java -cp ".;lib\gson-2.8.9.jar" HashiraPlacements input1.json

    java -cp ".;lib\gson-2.8.9.jar" HashiraPlacements input2.json


4. Outputs are printed to the console.
## Project Structure

HashiraPlacements/
├── lib/
│ └── gson-2.8.9.jar
├── input1.json
├── input2.json
├── HashiraPlacements.java
├── output1.txt (optional)
├── output2.txt (optional)
└── README.md

4. After editing, save the file.

5. Stage and commit the file:

git add README.md
 
git commit -m "Add README file explaining the project"

git push


