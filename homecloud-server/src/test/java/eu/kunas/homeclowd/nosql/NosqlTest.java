package eu.kunas.homeclowd.nosql;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ramazan on 10.07.15.
 */
public class NosqlTest {

    @Test
    @Ignore
    public void createDataOnNoSql(){
        OObjectDatabaseTx db = new OObjectDatabaseTx ("remote:localhost/homecloud").open("root", "-_-");

        db.getEntityManager().registerEntityClasses("eu.kunas.homeclowd.common.model");

        db.open("root", "-_-");

        db.drop();
    }
}
