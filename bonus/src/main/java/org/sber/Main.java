package org.sber;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Runnable digits = () -> {
            Iterator<Integer> iterator = IntStream.range(0, 10).iterator();
            try {
                while (iterator.hasNext()) {
                    System.out.printf("%d ", iterator.next());
                    TimeUnit.MILLISECONDS.sleep(50);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable chars = () -> {
            Iterator<Character> iterator = IntStream.range(0, 10)
                    .mapToObj(i -> (char) ('a' + i))
                    .iterator();
            try {
                TimeUnit.MILLISECONDS.sleep(20);
                while (iterator.hasNext()) {
                    System.out.printf("%s ", iterator.next());
                    TimeUnit.MILLISECONDS.sleep(50);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread intsThread = new Thread(digits);
        Thread charsThread = new Thread(chars);

        long startTime = System.currentTimeMillis();

        intsThread.start();
        charsThread.start();

        intsThread.join();
        charsThread.join();

        long endTime = System.currentTimeMillis();
        System.out.printf("%n%s sec%n", (endTime - startTime) / 1000.0);

//        0 a 1 b 2 c 3 d 4 e 5 f 6 g 7 h 8 i 9 j
//        0.652 sec
    }
}