
--Посещение страницы компании
CREATE TABLE employer_visit(
  employer_id                     INTEGER REFERENCES employer,
  visit_counter                   INTEGER NOT NULL,
  visit_before_date_total_counter INTEGER NOT NULL,
  date                            DATE,
  PRIMARY KEY(employer_id, date)
);
