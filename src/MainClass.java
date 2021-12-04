/*
Организуем гонки:
Все участники должны стартовать одновременно, несмотря на то, что на подготовку у
каждого из них уходит разное время.//CyclicBarrier
В туннель не может заехать одновременно больше половины участников (условность).
Попробуйте всё это синхронизировать.//Semaphore
Только после того как все завершат гонку, нужно выдать объявление об окончании.
Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {
    static final int CARS_COUNT = 4;
    static final CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(CARS_COUNT);
    static final CountDownLatch END_OF_PREPARED = new CountDownLatch(CARS_COUNT);
    static final CountDownLatch END_OF_RACE = new CountDownLatch(CARS_COUNT);
    static final Semaphore TUNNEL = new Semaphore(CARS_COUNT/2);

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));//создание трассы
        Car[] cars = new Car[CARS_COUNT];//создание участников
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        for (int i = 0; i < cars.length; i++) { //запуск отдельного потока для каждой машины
            new Thread(cars[i]).start();
        }


        try {
            END_OF_PREPARED.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            END_OF_RACE.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}