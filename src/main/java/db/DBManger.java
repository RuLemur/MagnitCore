package db;

import java.sql.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * Created by RuLemur on 24.07.2017.
 * magnitCore
 */
public class DBManger {
    private DBBean dbBean;

    public DBManger(DBBean dbBean) {
        this.dbBean = dbBean;
    }

    public DBBean getDbBean() {
        return dbBean;
    }

    public void setDbBean(DBBean dbBean) {
        this.dbBean = dbBean;
    }


    public void connectToDB() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEntries() {
        LocalTime startTime = LocalTime.now();
        try (Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUser(), dbBean.getPassword());
             Statement statement = connection.createStatement()) {

            statement.execute(SqlQueryTemplate.getCreateTableQuery());
//            for (int i = 1; i <= dbBean.getEntryCount(); i++) {
            statement.executeUpdate(SqlQueryTemplate.getAddManyEntryQuery(1, dbBean.getEntryCount()));
//            }
            System.out.println("end: " + startTime.until(LocalTime.now(), ChronoUnit.SECONDS));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
