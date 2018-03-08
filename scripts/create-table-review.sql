--Отзыв
CREATE TABLE review (
	id SERIAL PRIMARY KEY,
	employer_id integer REFERENCES NOT NULL employer,
	created_on timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
	text varchar,
	rating numeric NOT NULL CHECK (rating >= 1 AND rating <= 5)
);
