package eu.kunas.homeclowd;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

/**
 * Created by Kunas on 26.06.2015.
 */
public class RuntimeRunner {

    @Test
    @Ignore
    public void runtimeExec() throws Exception {
        String s = new File(".").getAbsolutePath().replace(".","") + "db\\bin\\server.bat";
        String s2 = "";
        System.out.println(s2);

        Runtime r = Runtime.getRuntime();

        ProcessBuilder p = new ProcessBuilder();
        p.command("cmd /c C:\\Users\\kunas\\Dev\\Projekte\\homeclowd\\db\\bin\\server.bat");
        p.start();


        String[] e = {"ORIENTDB_HOME=C:\\Users\\kunas\\Dev\\Projekte\\homeclowd\\db"};

        System.out.println();

       // r.exec("cmd /c start "+s2, e);

    }
}
