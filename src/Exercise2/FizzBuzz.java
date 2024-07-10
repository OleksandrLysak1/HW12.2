package Exercise2;

class FizzBuzz {
    private final int n;
    private int current = 1;
    private final Object lock = new Object();

    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizz() throws InterruptedException {
        while (current <= n) {
            synchronized (lock) {
                if (current % 3 == 0 && current % 5 != 0) {
                    System.out.println("fizz");
                    current++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
        }
    }

    public void buzz() throws InterruptedException {
        while (current <= n) {
            synchronized (lock) {
                if (current % 5 == 0 && current % 3 != 0) {
                    System.out.println("buzz");
                    current++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
        }
    }

    public void fizzbuzz() throws InterruptedException {
        while (current <= n) {
            synchronized (lock) {
                if (current % 3 == 0 && current % 5 == 0) {
                    System.out.println("fizzbuzz");
                    current++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
        }
    }

    public void number() throws InterruptedException {
        while (current <= n) {
            synchronized (lock) {
                if (current % 3 != 0 && current % 5 != 0) {
                    System.out.println(current);
                    current++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
        }
    }

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

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        threadA.join();
        threadB.join();
        threadC.join();
        threadD.join();
    }
}
