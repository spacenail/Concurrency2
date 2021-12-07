import java.util.concurrent.BrokenBarrierException;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private final Concurrent concurrent;

    static {
        CARS_COUNT = 0;
    }

    private final Race race;
    private final int speed;
    private final String name;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, Concurrent concurrent) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.concurrent = concurrent;
    }

    @Override
    public void run() {
        try {
            preparing();
        } catch (Exception e) {
            e.printStackTrace();
        }
        start();
    }

    private void preparing() throws InterruptedException, BrokenBarrierException {
        System.out.println(this.name + " готовится");
        Thread.sleep(500 + (int) (Math.random() * 800));
        System.out.println(this.name + " готов");
        concurrent.getEndOfPrepared().countDown();
        Thread.sleep(10);
        concurrent.getStart().await();
    }

    private void start() {
        for (int i = 0; i < race.getStages().size(); i++) {
            Stage stage = race.getStages().get(i);
                    stage.go(this);
        }
        concurrent.getEndOfRace().countDown();
        if(concurrent.getEndOfRace().getCount() == concurrent.getThreads() - 1){
            System.out.println(name + " победитель гонки!");
        }
    }
}