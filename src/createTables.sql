-- All tables have the following schema

USE cloud_backup;
-- The two statements should be viewd as a part of initialization,
-- template_table should not keep any record, only serving as
-- template for creating other tables
CREATE TABLE template_table(
	id INT PRIMARY KEY AUTO_INCREMENT,
	filename VARCHAR(255) NOT NULL,
	lastmodified DATETIME NOT NULL,
	passwd VARCHAR(255) NOT NULL,
	md5value char(32) NOT NULL,
	note VARCHAR(255), -- some comments about the file, if any
	filesize INT NOT NULL
)ENGINE=INNODB;

CREATE INDEX filenameIndex ON template_table(filename);

-- change 'newTale' to other (table/folder) name
CREATE TABLE newTable LIKE template_table;