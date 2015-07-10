package eu.kunas.homeclowd.nosql;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import eu.kunas.homeclowd.common.model.HCUserEntity;
import org.junit.Test;

/**
 * Created by ramazan on 10.07.15.
 */
public class NosqlTest {

    @Test
    public void createDataOnNoSql(){
        OObjectDatabaseTx db = new OObjectDatabaseTx ("remote:localhost/homeclowd").open("root", "-_-");

        db.getEntityManager().registerEntityClasses("eu.kunas.homeclowd.common");

        db.open("root","-_-");

        db.drop();
    }
}
