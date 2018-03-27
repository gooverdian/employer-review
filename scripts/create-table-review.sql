--Отзыв
CREATE TABLE review (
	id SERIAL PRIMARY KEY,
	employer_id integer NOT NULL REFERENCES employer,
	created_on timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
	text varchar,
	rating real NOT NULL
);
