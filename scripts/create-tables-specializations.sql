--Профф Область
CREATE TABLE  proff_field (
	id SERIAL PRIMARY KEY,
	hhid integer UNIQUE,
	name varchar NOT NULL
);

--Специализация
CREATE TABLE specialization (
	id SERIAL,
	hhid integer UNIQUE,
	proff_field_id integer REFERENCES proff_field,
	name varchar NOT NULL,
	PRIMARY KEY (id, proff_field_id)
);
