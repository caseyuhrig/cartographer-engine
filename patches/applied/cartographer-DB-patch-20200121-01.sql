

SELECT * FROM mapping WHERE source_row_id = target_row_id;
SELECT * FROM mapping WHERE source_field_id = target_field_id;


/* The dictionary_row table appears to have an incorrect dictionary linked to it. */
/* the mapping.source_dictionary_id does not coorespond to the dictionary_row.dictionary_id */

DROP VIEW view_bad_mappings;
CREATE VIEW view_bad_mappings AS
SELECT * FROM (
SELECT
    map.id AS map_id,
    map.label AS label,
    mapping.id AS mapping_id,
    map.source_dictionary_id AS sd_id,
    (SELECT id FROM dictionary WHERE id = sr.dictionary_id) AS sd2_id,
    mapping.source_row_id AS sr_id,
    (SELECT row_id FROM field WHERE id = mapping.source_field_id) AS sr2_id,
    mapping.source_field_id AS sf_id,

    map.target_dictionary_id AS td_id,
    (SELECT id FROM dictionary WHERE id = tr.dictionary_id) AS td2_id,
    mapping.target_row_id AS tr_id,
    (SELECT row_id FROM field WHERE id = mapping.target_field_id) AS tr2_id,
    mapping.target_field_id AS tf_id,
    mapping.create_date
FROM
    map, 
    mapping, 
    dictionary_row AS sr, 
    dictionary_row AS tr,
    field AS sf,
    field AS tf
WHERE
    /*map.id = 2 AND*/
    mapping.map_id = map.id AND
    sr.id = mapping.source_row_id AND
    tr.id = mapping.target_row_id AND
    sf.id = mapping.source_field_id AND
    tf.id = mapping.target_field_id
) AS a
WHERE
    sd_id != sd2_id OR 
    sr_id != sr2_id OR 
    td_id != td2_id OR
    tr_id != tr2_id;


/* constraints to check that source and target rows are not the same. */
/* when inserting into mapping check that the map.source_dictionary_id = source_row.dictionary_id */

ALTER TABLE mapping DROP CONSTRAINT check_mapping;

DROP FUNCTION check_mapping (a_mapping mapping);
CREATE FUNCTION check_mapping (a_mapping mapping)
RETURNS BOOLEAN 
IMMUTABLE 
LANGUAGE SQL AS
$$
SELECT
 	CASE COUNT(*)
 		WHEN 0 THEN true
 		ELSE false
 	END FROM view_bad_mappings;
$$;

ALTER TABLE mapping ADD CONSTRAINT check_mapping CHECK (check_mapping(mapping));

DELETE FROM mapping WHERE id IN (select mapping_id from view_bad_mappings);
ALTER TABLE mapping ADD CONSTRAINT unique_mapping_fields UNIQUE (source_field_id, target_field_id);


DELETE FROM mapping WHERE source_field_id = target_field_id;
ALTER TABLE mapping ADD CONSTRAINT check_mapping_same_field CHECK (source_field_id != target_field_id);
ALTER TABLE mapping ADD CONSTRAINT check_mapping_same_row CHECK (source_row_id != target_row_id);


DELETE FROM mapping WHERE id IN (select mapping_id from view_bad_mappings);
ALTER TABLE mapping DROP CONSTRAINT check_mapping;

DROP FUNCTION check_mapping (a_mapping mapping);

CREATE OR REPLACE FUNCTION check_mapping()
  RETURNS trigger AS
$BODY$
BEGIN
   IF (SELECT count(*) FROM view_bad_mappings) > 0 THEN
        RAISE EXCEPTION 'Bad Mapping: Please contact support.';
   END IF;
   RETURN NEW;
END;
$BODY$ LANGUAGE plpgsql;


CREATE TRIGGER check_mapping
    AFTER INSERT OR UPDATE ON mapping
    FOR EACH ROW
    EXECUTE PROCEDURE check_mapping();


