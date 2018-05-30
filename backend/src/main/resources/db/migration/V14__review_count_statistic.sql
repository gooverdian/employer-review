
CREATE TABLE main_page_review_count(
  proff_field_id  INT PRIMARY KEY REFERENCES proff_field,
  counter         INTEGER NOT NULL
)
