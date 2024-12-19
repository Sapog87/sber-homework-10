package org.sber;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Main {
    private final static int THREADS = 10;

    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = generateFile(2000);
        try (Scanner scanner = new Scanner(path)) {
            List<Thread> threads = new ArrayList<>();
            Lock lock = new ReentrantLock();

            for (int i = 0; i < THREADS; i++) {
                Thread thread = new Thread(new FactorialWorker(scanner, lock));
                threads.add(thread);
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            log.info("Done");
        }
    }

    private static Path generateFile(int size) throws IOException {
        Path path = Files.createTempFile("numbers", ".txt");
        List<String> lines = new Random()
                .ints(size, 1, 51)
                .mapToObj(String::valueOf)
                .toList();
        return Files.write(path, lines);
    }
}