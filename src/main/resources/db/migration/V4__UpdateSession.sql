ALTER TABLE user_session
ADD COLUMN date_session DATE;

ALTER TABLE user_session
ADD COLUMN is_auth tinyint;