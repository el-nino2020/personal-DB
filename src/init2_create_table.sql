CREATE DATABASE cloud_backup;
USE cloud_backup;

-- store the information of directories/folders
CREATE TABLE directories(
	dirname VARCHAR(255) UNIQUE NOT NULL, -- unqiue will also create index
	note VARCHAR(255), -- some comments about the directory, if any
	id INT PRIMARY KEY AUTO_INCREMENT
)ENGINE=INNODB;


-- store the information of files
CREATE TABLE files(
	filename VARCHAR(255) NOT NULL,
	note VARCHAR(255), -- some comments about the file, if any
	filesize BIGINT NOT NULL, -- file size in bytes
	lastmodified DATETIME NOT NULL,
	passwd VARCHAR(255) NOT NULL,
	md5value char(32) NOT NULL,
	id INT PRIMARY KEY AUTO_INCREMENT,
	dirid INT NOT NULL , -- directories.id
	FOREIGN KEY (dirid) REFERENCES directories(id)
)ENGINE=INNODB;

CREATE INDEX filenameIndex ON files(filename);

-- change 'newTale' to other (table/folder) name
-- CREATE TABLE newTable LIKE template_table;