package Exercise1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class RealTimeTracker {
    public static void main(String[] args) {
        MyThread myThread1 = new MyThread(1, 1000);
        myThread1.start();

        MyThread myThread2 = new MyThread(2, 5000);
        myThread2.start();
    }
}

class MyThread extends Thread {
    private final int threadId;
    private final int interval;

    public MyThread(int threadId, int interval) {
        this.threadId = threadId;
        this.interval = interval;
    }

    public void run() {
        Timer timer = new Timer();
        if (interval == 1000) {
            timer.scheduleAtFixedRate(new TimePrinter(threadId), 0, interval);
        } else if (interval == 5000) {
            timer.scheduleAtFixedRate(new FiveSecondNotifier(threadId), 0, interval);
        }
    }
}

class TimePrinter extends TimerTask {
    private final LocalDateTime startTime;
    private final int threadId;

    public TimePrinter(int threadId) {
        this.startTime = LocalDateTime.now();
        this.threadId = threadId;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(startTime, now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedNow = now.format(formatter);
        System.out.println("Thread " + threadId + " - Current Time: " + formattedNow + " | Elapsed Time: " + duration.getSeconds() + " seconds");
    }
}

class FiveSecondNotifier extends TimerTask {
    private final int threadId;

    public FiveSecondNotifier(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadId + " - 5 seconds have passed");
    }
}
