CREATE TABLE users (
  user_id  bigint not null auto_increment,
  name varchar(255),
  login varchar(255) not null,
  email varchar(255) not null,
  password varchar(255) not null,
  PRIMARY KEY(user_id),
  UNIQUE (login),
  UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;