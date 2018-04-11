
CREATE TABLE area (
	id int PRIMARY KEY,
	parent_id integer,
	name varchar NOT NULL
);

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
    area_id             integer NOT NULL REFERENCES area
);

CREATE TABLE rating (
    employer_id     integer PRIMARY KEY REFERENCES employer,
    rating          float,
    people_rated    integer NOT NULL,
    star1           integer NOT NULL,
    star2           integer NOT NULL,
    star3           integer NOT NULL,
    star4           integer NOT NULL,
    star5           integer NOT NULL,
    star1_5         integer NOT NULL,
    star2_5         integer NOT NULL,
    star3_5         integer NOT NULL,
    star4_5         integer NOT NULL,
    star0_5         integer NOT NULL
);
