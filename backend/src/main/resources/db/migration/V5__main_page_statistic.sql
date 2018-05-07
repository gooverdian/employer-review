
--Тип статисики
CREATE TYPE main_page_statistic_type_enum AS ENUM (
  'REVIEW_COUNT',
  'EMPLOYER_WITH_REVIEW_COUNT'
);

CREATE TABLE main_page_statistic(
  key main_page_statistic_type_enum PRIMARY KEY,
  value INTEGER
);
