

CREATE INDEX idx_data_field_field_id_value ON data_field (field_id, value);
CREATE INDEX idx_data_field_value ON data_field (value);
CREATE INDEX idx_data_field_field_id ON data_field (field_id);
CREATE INDEX idx_field_label ON field (label);
CREATE INDEX idx_field_row_id ON field (row_id);
CREATE INDEX idx_data_row_row_id ON data_row (row_id);
CREATE INDEX idx_dictionary_row_label ON dictionary_row (label);
CREATE INDEX idx_dictionary_dictionary_id ON dictionary_row (dictionary_id);

---------------------------------------------------------------------------------------

ALTER TABLE conversion 
    DROP CONSTRAINT "conversion_source_data_id_fkey",
     ADD CONSTRAINT "conversion_source_data_id_fkey" 
    FOREIGN KEY (source_data_id) REFERENCES data(id) ON DELETE CASCADE
;

ALTER TABLE conversion
    DROP CONSTRAINT "conversion_target_data_id_fkey",
     ADD CONSTRAINT "conversion_target_data_id_fkey" 
    FOREIGN KEY (target_data_id) REFERENCES data(id) ON DELETE CASCADE   
;

ALTER TABLE data_row
    DROP CONSTRAINT "data_row_data_id_fkey",
     ADD CONSTRAINT "data_row_data_id_fkey" 
    FOREIGN KEY (data_id) REFERENCES data(id) ON DELETE CASCADE
;

ALTER TABLE data_field
    DROP CONSTRAINT "data_field_data_row_id_fkey",
     ADD CONSTRAINT "data_field_data_row_id_fkey" 
    FOREIGN KEY (data_row_id) REFERENCES data_row(id) ON DELETE CASCADE
;


-- VACUUM ANALYSE;


