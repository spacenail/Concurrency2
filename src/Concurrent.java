import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Concurrent {
    private final CyclicBarrier start;
    private final CountDownLatch endOfPrepared;
    private final CountDownLatch endOfRace;
    private final int threads;


    public Concurrent(int threads) {
        start = new CyclicBarrier(threads);
        endOfPrepared = new CountDownLatch(threads);
        endOfRace = new CountDownLatch(threads);
        this.threads = threads;
    }

    public CyclicBarrier getStart() {
        return start;
    }

    public CountDownLatch getEndOfPrepared() {
        return endOfPrepared;
    }

    public CountDownLatch getEndOfRace() {
        return endOfRace;
    }


    public int getThreads() {
        return threads;
    }
}
