package db;

/**
 * Created by RuLemur on 24.07.2017.
 * magnitCore
 */
public class SqlQueryTemplate {

    public static String getCreateTableQuery() {
        return "CREATE TABLE IF NOT EXISTS test" +
                "(`id` INT NOT NULL AUTO_INCREMENT," +
                "  `num` INT NOT NULL," +
                "  PRIMARY KEY (`id`));";
    }

    public static String getClearTableQuery() {
        return "TRUNCATE TABLE test;";
    }

    public static String getAddNewEntryQuery(String num) {
        return "INSERT INTO test(num) VALUES(" + num + ")";
    }

    public static String getSelectAll() {
        return "SELECT num FROM test";
    }

    public static String getAddManyEntryQuery(int from, int to) {
        StringBuilder sb = new StringBuilder("INSERT INTO test(num) VALUES");
        for (int i = from; i < to; i++) {
            sb.append("(" + i + "),");
        }
        sb.append("(" + to + ");");
        System.out.println(sb);
        return sb.toString();
    }
}
