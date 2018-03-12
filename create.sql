CREATE DATABASE employers_review;

\c employers_review;

CREATE TABLE employer (
	id                  SERIAL PRIMARY KEY,
	hh_id               int UNIQUE,
	name                varchar,
	site_url            varchar,
	description         varchar,
	branded_descrption  varchar,
	alternate_url       varchar,
	logo_url_90         varchar,
	logo_url_240        varchar,
	logo_url_original   varchar,
	area_id             integer
);