package eu.kunas.homeclowd;

import eu.kunas.homeclowd.utils.OrientDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Kunas on 26.06.2015.
 */
@Ignore
public class RuntimeRunner {

    @Before
    public void init() {
        OrientDB.start();
    }

    @After
    public void after() {
        OrientDB.stop();
    }

    @Test
    public void runtimeExec() throws Exception {
        System.out.println("Im Test");
        Thread.sleep(10000);
    }
}
