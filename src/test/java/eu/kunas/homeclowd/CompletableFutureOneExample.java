package eu.kunas.homeclowd;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by ramazan on 27.04.15.
 */
public class CompletableFutureOneExample {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = Arrays.asList(10, 20, 30, 40);

        list.stream().map(data-> CompletableFuture.supplyAsync(() -> getNumber(data))).
                map(compFuture->compFuture.thenApply(n->n*n)).map(t->t.join())
                .forEach(s->System.out.println(s));
    }
    private static int getNumber(int a){
        return a*a;
    }
}
