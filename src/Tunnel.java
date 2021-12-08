import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private final Semaphore tunnel;
    public Tunnel(int threads) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        tunnel = new Semaphore(threads/2);;
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                tunnel.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                tunnel.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}