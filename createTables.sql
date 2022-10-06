-- All tables have the following schema
-- Change the two 'games' to another table name


CREATE TABLE games(
	id INT PRIMARY KEY AUTO_INCREMENT,
	filename VARCHAR(255) NOT NULL,
	lastmodified DATETIME NOT NULL,
	passwd VARCHAR(255) NOT NULL,
	md5value char(32) NOT NULL,
)ENGINE=INNODB;

CREATE INDEX filenameIndex ON games(filename);