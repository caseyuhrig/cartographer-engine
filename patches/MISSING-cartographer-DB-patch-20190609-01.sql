BEGIN;

/*SET search_path = cartographer;
SET ROLE cartographer;*/

DROP TABLE IF EXISTS version;

CREATE TABLE version
(
    id BIGINT NOT NULL DEFAULT 1 PRIMARY KEY,
    major INTEGER NOT NULL,
    minor INTEGER NOT NULL,
    build INTEGER NOT NULL,
    revision BIGINT NOT NULL,
    create_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT clock_timestamp(),
    create_user BIGINT, -- NOT NULL REFERENCES user_profile (id),
    update_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT clock_timestamp(),
    update_user BIGINT -- NOT NULL REFERENCES user_profile (id)
);
GRANT ALL ON TABLE version TO cartographer;
REVOKE ALL ON TABLE version FROM public;

INSERT INTO version (major, minor, build, revision, create_user, update_user) VALUES (5, 0, 1, 0, 1, 1);

ALTER TABLE version ADD CONSTRAINT version_create_user_fkey FOREIGN KEY (create_user) REFERENCES user_profile (id);
ALTER TABLE version ADD CONSTRAINT version_update_user_fkey FOREIGN KEY (update_user) REFERENCES user_profile (id);
ALTER TABLE version ALTER COLUMN create_user SET NOT NULL;
ALTER TABLE version ALTER COLUMN update_user SET NOT NULL;

ALTER TABLE row RENAME TO dictionary_row;

ALTER SEQUENCE seq_row_id RENAME TO seq_dictionary_row_id;

SELECT insert_function_definition ('LITERAL_PREFIX','');

COMMIT;
