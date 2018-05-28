CREATE TABLE duration_by_proff_field(
  employer_id     INT NOT NULL REFERENCES employer,
  proff_field_id  INT NOT NULL REFERENCES proff_field,
  duration        FLOAT NOT NULL,
  PRIMARY KEY(employer_id, proff_field_id)
);
