
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import service.ParkingLot;

public class Server {

    public static void main(String[] args) throws InterruptedException {

        ParkingLot parkingLot = ParkingLot.getInstance();

        parkingLot.addFloor(100, 50, 200, 50, 20, 30);
        parkingLot.addFloor(120, 60, 250, 50, 20, 30);
        parkingLot.addFloor(150, 70, 300, 50, 20, 30);
        parkingLot.addFloor(180, 80, 350, 50, 20, 30);
        parkingLot.addFloor(200, 90, 400, 50, 20, 30);

        int threadCount = 500;

        AtomicInteger successful = new AtomicInteger();
        AtomicInteger rejected = new AtomicInteger();
        AtomicInteger exceptions = new AtomicInteger();

        AtomicInteger cars = new AtomicInteger();
        AtomicInteger bikes = new AtomicInteger();
        AtomicInteger trucks = new AtomicInteger();

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        for (int i = 1; i <= threadCount; i++) {

            int threadId = i;

            Thread thread = new Thread(() -> {

                try {

                    startLatch.await();

                    /*
                     * Every thread gets a UNIQUE physical spot.
                     *
                     * Each floor:
                     * 50 CAR
                     * 30 BIKE
                     * 20 TRUCK
                     *
                     * Total = 100 spots/floor
                     */
                    int floor = ((threadId - 1) / 100) + 1;
                    int position = (threadId - 1) % 100;

                    String vehicleType;
                    int spot;

                    if (position < 50) {

                        // CAR: 1 - 50
                        vehicleType = "car";
                        spot = position + 1;

                    } else if (position < 80) {

                        // BIKE: 1 - 30
                        vehicleType = "bike";
                        spot = position - 50 + 1;

                    } else {

                        // TRUCK: 1 - 20
                        vehicleType = "truck";
                        spot = position - 80 + 1;
                    }

                    boolean result = parkingLot.generateTicket(
                            floor,
                            vehicleType,
                            "MP04" + vehicleType.toUpperCase() + threadId,
                            1,
                            spot,
                            "UPI"
                    );

                    if (result) {

                        successful.incrementAndGet();

                        switch (vehicleType) {

                            case "car":
                                cars.incrementAndGet();
                                break;

                            case "bike":
                                bikes.incrementAndGet();
                                break;

                            case "truck":
                                trucks.incrementAndGet();
                                break;
                        }

                    } else {
                        rejected.incrementAndGet();
                    }

                } catch (Exception e) {

                    exceptions.incrementAndGet();
                    e.printStackTrace();

                } finally {
                    doneLatch.countDown();
                }

            }, "Parking-Thread-" + threadId);

            thread.start();
        }

        System.out.println("Starting 500 concurrent parking requests...");

        startLatch.countDown();

        doneLatch.await();

        System.out.println();
        System.out.println("========== PARKING TEST RESULT ==========");
        System.out.println("Total Requests : " + threadCount);
        System.out.println("Successful     : " + successful.get());
        System.out.println("Rejected       : " + rejected.get());
        System.out.println("Exceptions     : " + exceptions.get());
        System.out.println();
        System.out.println("Cars           : " + cars.get());
        System.out.println("Bikes          : " + bikes.get());
        System.out.println("Trucks         : " + trucks.get());
        System.out.println("=========================================");
    }
}
