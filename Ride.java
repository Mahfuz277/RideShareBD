public class Ride {

    static int idCounter = 1;

    int id;
    RideRequest request;
    Driver driver;
    double distanceKm;
    double fare;
    String status;

    public Ride(RideRequest request, Driver driver, double distanceKm) {
        this.id = idCounter++;
        this.request = request;
        this.driver = driver;
        this.distanceKm = distanceKm;
        this.fare = calculateFare(distanceKm, driver.vehicle.type);
        this.status = "ONGOING";
    }

    public static double calculateFare(double distance, String vehicleType) {
        double baseFare = 0;
        double ratePerKm = 0;

        if (vehicleType.equals("BIKE")) {
            baseFare = 20;
            ratePerKm = 10;
        } else if (vehicleType.equals("CAR")) {
            baseFare = 50;
            ratePerKm = 20;
        } else if (vehicleType.equals("SUV")) {
            baseFare = 80;
            ratePerKm = 30;
        }

        return baseFare + (ratePerKm * distance);
    }

    public void showInfo() {
        System.out.println("Ride ID: " + id +
                " | Driver: " + driver.name +
                " | From: " + request.pickup +
                " -> To: " + request.destination +
                " | Distance: " + distanceKm + " km" +
                " | Fare: " + fare + " BDT" +
                " | Status: " + status);
    }
}
