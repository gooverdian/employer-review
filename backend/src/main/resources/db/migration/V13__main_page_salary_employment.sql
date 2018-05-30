CREATE TABLE main_page_employment(
  proff_field_id  INT PRIMARY KEY REFERENCES proff_field,
  duration        FLOAT NOT NULL
);

CREATE TABLE main_page_salary(
  proff_field_id  INT PRIMARY KEY REFERENCES proff_field,
  salary          FLOAT NOT NULL
);
