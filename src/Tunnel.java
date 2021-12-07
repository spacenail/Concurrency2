public class Tunnel extends Stage {
    private Concurrent concurrent;
    public Tunnel(Concurrent concurrent) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.concurrent = concurrent;
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                concurrent.getTunnel().acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                concurrent.getTunnel().release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}