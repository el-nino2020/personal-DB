package dao;

import domain.FileInfo;

import java.sql.Connection;

public class FileInfoDAO extends BasicDAO<FileInfo> {
    public FileInfoDAO(Connection connection) {
        super(connection);
    }
}
