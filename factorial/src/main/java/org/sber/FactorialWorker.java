package org.sber;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;

@Slf4j
public class FactorialWorker implements Runnable {
    private final Scanner scanner;
    private final Lock lock;

    public FactorialWorker(Scanner scanner, Lock lock) {
        this.scanner = scanner;
        this.lock = lock;
    }

    private BigInteger factorial(int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    @Override
    public void run() {
        while (true) {
            int n;
            try {
                lock.lockInterruptibly();
                if (!scanner.hasNextInt())
                    return;
                n = scanner.nextInt();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
            BigInteger fact = factorial(n);
            log.info("F({}) = {}", n, fact);
        }
    }
}