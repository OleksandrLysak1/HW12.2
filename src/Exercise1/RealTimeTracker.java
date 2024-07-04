package Exercise1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class RealTimeTracker {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();

        MyThread myThread2 = new MyThread();
        myThread2.start();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new FiveSecondNotifier(myThread2), 0, 5000);
    }
}

class MyThread extends Thread {
    public void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimePrinter(), 0, 1000);
    }
}

class TimePrinter extends TimerTask {
    private final LocalDateTime startTime;
    public TimePrinter() {
        this.startTime = LocalDateTime.now();
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(startTime, now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedNow = now.format(formatter);
        System.out.println("Current Time: " + formattedNow + " | Elapsed Time: " + duration.getSeconds() + " seconds");
    }
}

class FiveSecondNotifier extends TimerTask{
    private final MyThread myThread2;

    public FiveSecondNotifier(MyThread myThread){
        this.myThread2 = myThread;
    }
    @Override
    public void run(){
        System.out.println("\"5 seconds have passed\" " + myThread2);
    }
}
