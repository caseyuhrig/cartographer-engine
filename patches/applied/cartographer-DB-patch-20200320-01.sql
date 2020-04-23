

ALTER TABLE conversion DROP CONSTRAINT "conversion_target_dictionary_id_fkey";
ALTER TABLE conversion ADD CONSTRAINT "conversion_target_dictionary_id_fkey" FOREIGN KEY (target_dictionary_id) REFERENCES dictionary (id) ON DELETE CASCADE;

ALTER TABLE conversion DROP CONSTRAINT "conversion_source_dictionary_id_fkey";
ALTER TABLE conversion ADD CONSTRAINT "conversion_source_dictionary_id_fkey" FOREIGN KEY (source_dictionary_id) REFERENCES dictionary (id) ON DELETE CASCADE;

ALTER TABLE conversion DROP CONSTRAINT "conversion_map_id_fkey";
ALTER TABLE conversion ADD CONSTRAINT "conversion_map_id_fkey" FOREIGN KEY (map_id) REFERENCES map (id) ON DELETE CASCADE;

ALTER TABLE data DROP CONSTRAINT "data_dictionary_id_fkey";
ALTER TABLE data ADD CONSTRAINT "data_dictionary_id_fkey" FOREIGN KEY (dictionary_id) REFERENCES dictionary (id) ON DELETE CASCADE;

ALTER TABLE data_field DROP CONSTRAINT "data_field_dictionary_id_fkey";
ALTER TABLE data_field ADD CONSTRAINT "data_field_dictionary_id_fkey" FOREIGN KEY (dictionary_id) REFERENCES dictionary(id) ON DELETE CASCADE;

ALTER TABLE data_row DROP CONSTRAINT "data_row_dictionary_id_fkey";
ALTER TABLE data_row ADD CONSTRAINT "data_row_dictionary_id_fkey" FOREIGN KEY (dictionary_id) REFERENCES dictionary(id) ON DELETE CASCADE;


ALTER TABLE map DROP CONSTRAINT "map_source_dictionary_id_fkey";
ALTER TABLE map ADD CONSTRAINT "map_source_dictionary_id_fkey" FOREIGN KEY (source_dictionary_id) REFERENCES dictionary(id) ON DELETE CASCADE;

ALTER TABLE map DROP CONSTRAINT "map_target_dictionary_id_fkey";
ALTER TABLE map ADD CONSTRAINT "map_target_dictionary_id_fkey" FOREIGN KEY (target_dictionary_id) REFERENCES dictionary(id) ON DELETE CASCADE;

ALTER TABLE mapping DROP CONSTRAINT "mapping_source_field_id_fkey";
ALTER TABLE mapping ADD CONSTRAINT "mapping_source_field_id_fkey" FOREIGN KEY (source_field_id) REFERENCES field(id)  ON DELETE CASCADE;

ALTER TABLE mapping DROP CONSTRAINT "mapping_source_row_id_fkey";
ALTER TABLE mapping ADD CONSTRAINT "mapping_source_row_id_fkey" FOREIGN KEY (source_row_id) REFERENCES dictionary_row(id) ON DELETE CASCADE;

ALTER TABLE mapping DROP CONSTRAINT "mapping_target_field_id_fkey";
ALTER TABLE mapping ADD CONSTRAINT "mapping_target_field_id_fkey" FOREIGN KEY (target_field_id) REFERENCES field(id) ON DELETE CASCADE;

ALTER TABLE mapping DROP CONSTRAINT "mapping_target_row_id_fkey";
ALTER TABLE mapping ADD CONSTRAINT "mapping_target_row_id_fkey" FOREIGN KEY (target_row_id) REFERENCES dictionary_row(id) ON DELETE CASCADE;



