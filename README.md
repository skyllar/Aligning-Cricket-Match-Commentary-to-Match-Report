# Aligning-Cricket-Match-Commentary-to-Match-Report

The project involves:
1. Extraction of mentions from Match Report.
2. Ball-by-Ball linking of these mentions to Match Commentary.
3. Using appropriate alignment-techniques and similarity measures.

Main components involved:

A. Data Parsing: Referred ESPNCricinfo.com dataset which includes:

    a. Parsing of Match Commentary.
    b. Parsing of Match Report.

B. Data Extraction & Preprocessing:

    a. Sentence Parameter Verification: Checking if candidate is mention.
    b. Sentence Parameter Extraction: It involves:
       1. Number of nouns.
       2. Number of "numbers".
       3. Count of Strong hit words.
       4. Count of Mild hit words.
    c. Lemmatization.

C. Designing of Classifier to detect events from match report.

D. Similarity measures to link events to Balls and Overs.
