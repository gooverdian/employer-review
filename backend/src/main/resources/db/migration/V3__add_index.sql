
CREATE INDEX employer_name_index
  ON employer (name);

CREATE INDEX area_name_index
  ON area (name);

CREATE INDEX rating_index
  ON rating (people_rated, rating);
