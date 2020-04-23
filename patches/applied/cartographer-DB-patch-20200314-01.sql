
INSERT INTO data_type (identifier, create_user, update_user) VALUES ('PHONE',1,1);

INSERT INTO data_format (data_type_id, format, create_user, update_user) VALUES ((SELECT id FROM data_type WHERE identifier = 'PHONE'), '###-###-####',1,1);
INSERT INTO data_format (data_type_id, format, create_user, update_user) VALUES ((SELECT id FROM data_type WHERE identifier = 'PHONE'), '(###) ###-####',1,1);
INSERT INTO data_format (data_type_id, format, create_user, update_user) VALUES ((SELECT id FROM data_type WHERE identifier = 'PHONE'), '##########',1,1);

ALTER TABLE mapping DROP CONSTRAINT "mapping_map_id_fkey";
ALTER TABLE mapping ADD CONSTRAINT "mapping_map_id_fkey" FOREIGN KEY (map_id) REFERENCES map (id) ON DELETE CASCADE;

ALTER TABLE mapping_function DROP CONSTRAINT "mapping_function_mapping_id_fkey";
ALTER TABLE mapping_function ADD CONSTRAINT "mapping_function_mapping_id_fkey" FOREIGN KEY (mapping_id) REFERENCES mapping (id) ON DELETE CASCADE;

DROP INDEX "idx_dictionary_dictionary_id";
ALTER TABLE dictionary_row DROP CONSTRAINT "row_dictionary_id_fkey";
ALTER TABLE dictionary_row ADD CONSTRAINT "row_dictionary_id_fkey" FOREIGN KEY (dictionary_id) REFERENCES dictionary (id) ON DELETE CASCADE;

ALTER TABLE field DROP CONSTRAINT "field_row_id_fkey";
ALTER TABLE field ADD CONSTRAINT "field_row_id_fkey" FOREIGN KEY (row_id) REFERENCES dictionary_row(id) ON DELETE CASCADE;

ALTER TABLE dictionary_row DROP CONSTRAINT "dictionary_row_dictionary_id";




ALTER TABLE field DROP CONSTRAINT "field_aggregate_definition_id_fkey";
ALTER TABLE field ADD CONSTRAINT "field_aggregate_definition_id_fkey" FOREIGN KEY (aggregate_definition_id) REFERENCES aggregate_definition(id) ON DELETE CASCADE;

ALTER TABLE field DROP CONSTRAINT "field_aggregate_field_id_fkey";
ALTER TABLE field ADD CONSTRAINT "field_aggregate_field_id_fkey" FOREIGN KEY (aggregate_field_id) REFERENCES field(id) ON DELETE CASCADE;

ALTER TABLE field DROP CONSTRAINT "field_aggregate_row_id_fkey";
ALTER TABLE field ADD CONSTRAINT "field_aggregate_row_id_fkey" FOREIGN KEY (aggregate_row_id) REFERENCES dictionary_row(id) ON DELETE CASCADE;

ALTER TABLE field DROP CONSTRAINT "field_data_type_id_fkey";
ALTER TABLE field ADD CONSTRAINT "field_data_type_id_fkey" FOREIGN KEY (data_type_id) REFERENCES data_type(id) ON DELETE CASCADE;



