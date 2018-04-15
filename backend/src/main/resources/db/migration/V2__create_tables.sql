ALTER TABLE proff_field RENAME hhid TO hh_id;
ALTER TABLE specialization DROP CONSTRAINT specialization_hhid_key;
ALTER TABLE specialization RENAME hhid TO hh_id;
ALTER TABLE specialization ALTER COLUMN hh_id TYPE varchar;
ALTER TABLE specialization DROP CONSTRAINT specialization_pkey;
ALTER TABLE specialization ADD PRIMARY KEY (id);

