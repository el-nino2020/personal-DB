CREATE DATABASE `cloud_backup`;
USE `cloud_backup`;

-- store the information of directories/folders
CREATE TABLE `cloud_backup`.`directories` (
    `dir_name` VARCHAR (255) NOT NULL,
    `dir_note` VARCHAR (500) COMMENT 'some comments about this directory, if any',
    `dir_id` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`dir_id`),
    UNIQUE INDEX `UNIQUE_DIR_NAME` (`dir_name`)
) ENGINE = INNODB;




-- store the information of files
CREATE TABLE `cloud_backup`.`files` (
    `file_name` VARCHAR (255) NOT NULL,
    `file_note` VARCHAR (500) COMMENT 'some comments about this file, if any',
    `size` BIGINT NOT NULL COMMENT 'file size in bytes',
    `last_modified` DATETIME NOT NULL COMMENT 'last modified time',
    `password` VARCHAR (255) NOT NULL COMMENT 'archive password',
    `md5_value` CHAR (32) NOT NULL COMMENT 'md5(archive)',
    `file_id` INT NOT NULL AUTO_INCREMENT,
    `dir_id` INT NOT NULL,
    PRIMARY KEY (`file_id`),
    INDEX `FILE_NAME_INDEX` (`file_name`) COMMENT "for fast query",
    CONSTRAINT `FOREIGN_DIR_ID` FOREIGN KEY (`dir_id`) REFERENCES `cloud_backup`.`directories` (`dir_id`)
) ENGINE = INNODB;



-- change 'newTale' to other (table/folder) name
-- CREATE TABLE newTable LIKE template_table;