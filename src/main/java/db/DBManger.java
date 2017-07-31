package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by RuLemur on 24.07.2017.
 * magnitCore
 */
public class DBManger {
    private DBBean dbBean;

    public DBManger() {
        this.dbBean = createDBean();
    }

    public DBManger(DBBean dbBean) {
        this.dbBean = dbBean;
    }

    public DBBean getDbBean() {
        return dbBean;
    }

    public void connectToDB() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillTable() {
        try (Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUser(), dbBean.getPassword());
             Statement statement = connection.createStatement()) {
            statement.execute(SqlQueryTemplate.getCreateTableQuery());
            for (int i = 0; i <= dbBean.getEntryCount() % 50000; i++) {
                statement.executeUpdate(SqlQueryTemplate.getAddManyEntryQuery(i+1, dbBean.getEntryCount()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBBean createDBean() {
        Properties properties = new Properties();
        DBBean dbBean = new DBBean();

        try (InputStream input = new FileInputStream("src/main/resources/db.properties")) {
            properties.load(input);

            dbBean.setUrl(properties.getProperty("url"));
            dbBean.setPassword(properties.getProperty("password"));
            dbBean.setUser(properties.getProperty("user"));
            dbBean.setEntryCount(Integer.valueOf(properties.getProperty("entryCount")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dbBean;
    }
}
