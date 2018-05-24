CREATE TABLE average_salary_in_employer_by_proff_field(
  employer_id     INT NOT NULL REFERENCES employer,
  proff_field_id  INT NOT NULL REFERENCES proff_field,
  salary          FLOAT NOT NULL,
  PRIMARY KEY(employer_id, proff_field_id)
);
