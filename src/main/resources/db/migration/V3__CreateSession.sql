CREATE TABLE user_session (
  id bigint not null auto_increment,
  user_id bigint,
  session_id varchar(255),
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8