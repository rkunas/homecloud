package eu.kunas.homeclowd;

import java.util.*;

/**
 * Created by ramazan on 27.04.15.
 */
public class TablesTest {
    public static void main(String[] args) throws InterruptedException {

        LinkedList<String> a = new LinkedList<>();
        SortedSet<String> g = new TreeSet<>();

        a.add("a");
        a.add("b");
        a.add("c");

        Iterator<String> ita = a.iterator();

        ita.next();
        ita.remove();

        while(ita.hasNext()) {

            String s = ita.next();
            System.out.println(s);
        }


    }
}
