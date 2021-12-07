/*
Организуем гонки:
Все участники должны стартовать одновременно, несмотря на то, что на подготовку у
каждого из них уходит разное время.//CyclicBarrier
В туннель не может заехать одновременно больше половины участников (условность).
Попробуйте всё это синхронизировать.//Semaphore
Только после того как все завершат гонку, нужно выдать объявление об окончании.
Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.
 */


public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        Concurrent concurrent = new Concurrent(CARS_COUNT);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));//создание трассы
        Car[] cars = new Car[CARS_COUNT];//создание участников
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10),concurrent);
        }

        for (Car car : cars) { //запуск отдельного потока для каждой машины
            new Thread(car).start();
        }


        try {
            concurrent.getEndOfPrepared().await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            concurrent.getEndOfRace().await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}