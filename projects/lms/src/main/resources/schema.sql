CREATE TABLE IF NOT EXISTS `user` (
    `id`            INTEGER  PRIMARY KEY AUTO_INCREMENT,
     `first_name`   VARCHAR(255) NOT NULL,
     `last_name`    VARCHAR(255) NOT NULL,
     `email`        VARCHAR(255) NOT NULL,
     `password`     VARCHAR(255) NOT NULL
);