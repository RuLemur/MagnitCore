import db.DBManger;


/**
 * Created by RuLemur on 24.07.2017.
 * magnitCore
 */
public class Main {

    public static void main(String[] args) {
        DBManger manger = new DBManger();
        manger.connectToDB();
        manger.fillTable();
        manger.selectFromDb();

    }

}

