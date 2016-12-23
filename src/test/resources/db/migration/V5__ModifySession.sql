ALTER TABLE user_session
MODIFY COLUMN user_id bigint NOT NULL;

ALTER TABLE user_session
MODIFY COLUMN date_session DATE NOT NULL;

ALTER TABLE user_session
MODIFY COLUMN is_auth tinyint NOT NULL;
