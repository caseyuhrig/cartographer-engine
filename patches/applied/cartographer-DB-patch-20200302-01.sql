
ALTER TABLE dictionary_row ADD CONSTRAINT "idx_dictionary_id_row_order" UNIQUE (dictionary_id, row_order);

ALTER TABLE dictionary_row ADD CONSTRAINT "idx_dictionary_row_order_greater_than_zero" CHECK (row_order > 0);

CREATE INDEX "idx_mapping_target_field_id" ON mapping (target_field_id);

INSERT INTO data_type (identifier, create_user, update_user) VALUES ('KEY',1,1);

ALTER TABLE data_field ADD CONSTRAINT "unique_data_row_field_id" UNIQUE (data_row_id, field_id);

ALTER TABLE field DROP CONSTRAINT "field_row_id_label_key";

ALTER TABLE dictionary_row ALTER COLUMN min_occurrence SET DEFAULT 0;
ALTER TABLE dictionary_row ALTER COLUMN max_occurrence SET DEFAULT 2147483647;

UPDATE dictionary_row SET min_occurrence = 0;
UPDATE dictionary_row SET max_occurrence = 2147483647;

ALTER TABLE dictionary_row ADD CONSTRAINT "check_occurrence_range" CHECK (min_occurrence >=0 AND max_occurrence <= 2147483647);



