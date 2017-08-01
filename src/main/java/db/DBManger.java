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
        clearTable();
        long startTime = System.currentTimeMillis();
        try (Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUser(), dbBean.getPassword());
             Statement statement = connection.createStatement()) {
            statement.execute(SqlQueryTemplate.getCreateTableQuery());
            int startNum = 1;
            int endNum = dbBean.getLimit();
            for (int i = 0; i <= dbBean.getEntryCount() / dbBean.getLimit(); i++) {
                if (dbBean.getEntryCount() > endNum) {
                    statement.executeUpdate(SqlQueryTemplate.getAddManyEntryQuery(startNum, endNum));
                    startNum = endNum + 1;
                    endNum = endNum + dbBean.getLimit();
                    continue;
                }
                if (dbBean.getEntryCount() < endNum) {
                    statement.execute(SqlQueryTemplate.getAddManyEntryQuery(startNum, dbBean.getEntryCount()));
                    break;
                }
                if (dbBean.getEntryCount() == endNum) {
                    statement.executeUpdate(SqlQueryTemplate.getAddManyEntryQuery(startNum, endNum));
                    break;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - startTime) / 1000);

    }

    public void clearTable() {
        try (Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUser(), dbBean.getPassword());
             Statement statement = connection.createStatement()) {
            statement.execute(SqlQueryTemplate.getClearTableQuery());
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
            dbBean.setLimit(Integer.valueOf(properties.getProperty("limit")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dbBean;
    }

    public void selectFromDb() {
        try (Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUser(), dbBean.getPassword());
             Statement statement = connection.createStatement()) {
            long startTime = System.currentTimeMillis();
            ResultSet resultSet = statement.executeQuery(SqlQueryTemplate.getSelectAll());
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
            }
            System.out.println((System.currentTimeMillis() - startTime) / 1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
