CREATE DATABASE employer_review;

\c employer_review;

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
    area_id             integer,
    area_name           varchar,
    score               float,
    people_rated        int
);

CREATE TABLE area (
	id int PRIMARY KEY,
	parent_id integer,
	name varchar NOT NULL
);
