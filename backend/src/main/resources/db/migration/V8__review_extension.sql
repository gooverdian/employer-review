CREATE TABLE review_specialization (
	review_id integer NOT NULL REFERENCES review,
	specialization_id integer NOT NULL REFERENCES specialization,
	PRIMARY KEY (review_id, specialization_id)
);

CREATE TABLE position (
	id SERIAL PRIMARY KEY,
	name varchar
);
CREATE INDEX position_name_index ON position (name);

ALTER TABLE review ADD COLUMN position_id integer REFERENCES position; 
ALTER TABLE review ADD COLUMN salary integer;
ALTER TABLE review ADD COLUMN employment_duration smallint;
ALTER TABLE review ADD COLUMN employment_terminated boolean;
