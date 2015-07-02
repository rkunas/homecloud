package eu.kunas.homeclowd.common;

import eu.kunas.homeclowd.backend.db.OrientDB;

/**
 * Created by ramazan on 01.07.15.
 */
public class StarterOrientDb {

    public void startDb(){
        OrientDB.start();
    }

    public void shutdownDb(){
        OrientDB.stop();
    }
}
