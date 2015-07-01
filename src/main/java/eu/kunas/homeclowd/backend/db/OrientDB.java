package eu.kunas.homeclowd.backend.db;



import java.io.*;

/**
 * Created by ramazan on 27.06.15.
 */
public class OrientDB implements Serializable {

    private static final String serverSh = new File(".").getAbsolutePath().replace(".", "") + "db/bin/server.sh";

    private static final String shutdownSh = new File(".").getAbsolutePath().replace(".", "") + "db/bin/shutdown.sh";


    public static void start() {

        try {
            System.out.println("OrientDB Starting ... ");
            System.out.println("Running command " + serverSh);
            Runtime runtime = Runtime.getRuntime();

            Process process = runtime.exec("sh " + serverSh);

            String line = "";
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            int i = 0;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                i++;
                if(i==20){
                    System.out.println("... OrientDB Started Successfull.");
                    break;
                }
            }


            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {

        try {
            System.out.println("OrientDB Stopping... ");
            System.out.println("Running command " + shutdownSh);
            Runtime runtime = Runtime.getRuntime();

            Process process = runtime.exec("sh " + shutdownSh);

            String line = "";
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("... OrientDB Stopping Succesfull.");
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
