CREATE TABLE employer_salary_statistics(
  employer_id     INT NOT NULL REFERENCES employer,
  proff_field_id  INT NOT NULL REFERENCES proff_field,
  salary          FLOAT NOT NULL,
  PRIMARY KEY(employer_id, proff_field_id)
);
