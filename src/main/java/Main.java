import db.DBBean;
import db.DBManger;

import java.io.*;
import java.time.LocalTime;
import java.util.Properties;

/**
 * Created by RuLemur on 24.07.2017.
 * magnitCore
 */
public class Main {

    public static void main(String[] args) {
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

        DBManger dbManger = new DBManger(dbBean);
        dbManger.connectToDB();
        dbManger.addEntries();

    }
}

