package eu.kunas.homeclowd.utils;



import java.io.*;

/**
 * Created by ramazan on 27.06.15.
 */
public class OrientDB implements Serializable {

    private static final String serverSh = new File(".").getAbsolutePath().replace(".", "") + "db/bin/server.sh";

    private static final String shutdownSh = new File(".").getAbsolutePath().replace(".", "") + "db/bin/shutdown.sh";


    public static void start() {

        try {
            System.out.println("Starting " + serverSh);

            Runtime runtime = Runtime.getRuntime();

            Process process = runtime.exec("sh " + serverSh);

            String line = "";
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {

        try {
            System.out.println("Stopping " + shutdownSh);

            Runtime runtime = Runtime.getRuntime();

            Process process = runtime.exec("sh " + shutdownSh);

            String line = "";
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
