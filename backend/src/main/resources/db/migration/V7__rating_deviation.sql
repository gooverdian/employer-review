
CREATE TABLE rating_deviation(
  employer_id     INT PRIMARY KEY REFERENCES employer,
  deviation       FLOAT
);
