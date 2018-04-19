--Регион
CREATE TABLE area (
	id int PRIMARY KEY,
	parent_id integer,
	name varchar NOT NULL
);

--Рейтинг
CREATE TABLE rating (
  id              SERIAL PRIMARY KEY,
  people_rated    INT,
  rating          FLOAT
);

--Компания
CREATE TABLE employer (
    id                  SERIAL PRIMARY KEY,
    hh_id               int UNIQUE,
    name                varchar NOT NULL,
    site_url            varchar,
    description         varchar,
    branded_descrption  varchar,
    alternate_url       varchar,
    logo_url_90         varchar,
    logo_url_240        varchar,
    logo_url_original   varchar,
    area_id             integer REFERENCES area,
    rating_id           integer REFERENCES rating
);

--Звезды в рейтинге
CREATE TABLE stars_in_rating(
  employer_id     INT REFERENCES employer,
  star_value      FLOAT NOT NULL,
  star_counter    INT NOT NULL,
  PRIMARY KEY(employer_id, star_value)
);

--Тип озыва
CREATE TYPE review_type_enum AS ENUM (
    'EMPLOYEE',
    'INTERVIEWEE'
);

--Отзыв
CREATE TABLE review (
	id SERIAL PRIMARY KEY,
	employer_id integer NOT NULL REFERENCES employer,
	created_on timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
	review_type review_type_enum NOT NULL,
	text varchar,
	rating real NOT NULL
);

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
