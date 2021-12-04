import java.util.concurrent.BrokenBarrierException;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
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
        MainClass.END_OF_PREPARED.countDown();
        Thread.sleep(10);
        MainClass.CYCLIC_BARRIER.await();
    }

    private void start() {
        for (int i = 0; i < race.getStages().size(); i++) {
            Stage stage = race.getStages().get(i);
            if (stage instanceof Tunnel) {
                try {
                    MainClass.TUNNEL.acquire();
                    stage.go(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    MainClass.TUNNEL.release();
                }
            } else {
                stage.go(this);
            }
        }
        System.out.println(name + " прошел все этапы!");
        MainClass.END_OF_RACE.countDown();
    }
}