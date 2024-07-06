package Exercise2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class FizzBuzz {
    private final int n;
    private int current = 1;
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public FizzBuzz(int n) {
        this.n = n;
    }

    public synchronized void fizz() throws InterruptedException {
        while (current <= n) {
            if (current % 3 == 0 && current % 5 != 0) {
                queue.put("fizz");
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void buzz() throws InterruptedException {
        while (current <= n) {
            if (current % 5 == 0 && current % 3 != 0) {
                queue.put("buzz");
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void fizzbuzz() throws InterruptedException {
        while (current <= n) {
            if (current % 15 == 0) {
                queue.put("fizzbuzz");
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void number() throws InterruptedException {
        while (current <= n) {
            if (current % 3 != 0 && current % 5 != 0) {
                queue.put(String.valueOf(current));
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public void print() throws InterruptedException {
        while (true) {
            String value = queue.take();
            if (value.equals("END")) {
                break;
            }
            System.out.println(value);
        }
    }

    public void addEndMarker() throws InterruptedException {
        queue.put("END");
    }
}

class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 15;
        FizzBuzz fizzBuzz = new FizzBuzz(n);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread printThread = new Thread(() -> {
            try {
                fizzBuzz.print();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        printThread.start();

        threadA.join();
        threadB.join();
        threadC.join();
        threadD.join();

        fizzBuzz.addEndMarker();
        printThread.join();
    }
}
