public class Driver {

    static int idCounter = 1;

    int id;
    String name;
    String phone;
    Vehicle vehicle;
    boolean isAvailable;
    double rating;
    int totalRides;

    public Driver(String name, String phone, Vehicle vehicle) {
        this.id = idCounter++;
        this.name = name;
        this.phone = phone;
        this.vehicle = vehicle;
        this.isAvailable = true;
        this.rating = 5.0;
        this.totalRides = 0;
    }

    public void showInfo() {
        String status = isAvailable ? "Available" : "On Trip";
        System.out.println("Driver ID: " + id + " | Name: " + name + " | Phone: " + phone +
                " | Rating: " + rating + " | Rides: " + totalRides + " | Status: " + status);
        vehicle.showInfo();
    }
}


