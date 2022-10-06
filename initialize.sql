-- pay attention to the privilege of the user running this .sql file


SELECT * FROM mysql.user;

CREATE DATABASE cloud_backup;

CREATE USER 'backupadmin'@'localhost' IDENTIFIED BY yourPassword;

GRANT ALL ON cloud_backup.* TO backupadmin@localhost;

SHOW GRANTS FOR backupadmin@localhost;