package eu.kunas.homeclowd;

import eu.kunas.homeclowd.backend.db.OrientDB;

/**
 * Created by Kunas on 26.06.2015.
 */
public class RuntimeRunner {

    public static void main(String[] args) throws Exception{
        OrientDB.start();

        Thread.sleep(10000);

        OrientDB.stop();
    }





}
