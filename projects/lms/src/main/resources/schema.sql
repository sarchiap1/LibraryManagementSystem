CREATE TABLE IF NOT EXISTS `user` (
    id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
     first_name   VARCHAR(255) NOT NULL,
     last_name    VARCHAR(255) NOT NULL,
     email       VARCHAR(255) NOT NULL,
     `password`     VARCHAR(255) NOT NULL
);

ALTER TABLE IF EXISTS `user`
    ADD CONSTRAINT IF NOT EXISTS uq_email UNIQUE (email);

CREATE TABLE IF NOT EXISTS `token`(
    id  INTEGER  PRIMARY KEY AUTO_INCREMENT,
    refresh_token VARCHAR(255),
    exprired_at DATETIME NOT NULL,
    issued_at  DATETIME NOT NULL,
    user_Id BIGINT NOT NULL,
    CONSTRAINT fk_token_user foreign key (user_Id) references `user` (id)
);

