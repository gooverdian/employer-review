--Тип озыва
CREATE TYPE review_positivity_enum AS ENUM (
    'POSITIVE',
    'NEGATIVE'
);

--Отзывы с сайта pravda-sotrudnikov.ru
CREATE TABLE external_review(
	id SERIAL PRIMARY KEY,	
	positivity review_positivity_enum,
	text varchar 
);

CREATE INDEX external_review_positivity_index ON external_review (positivity);

