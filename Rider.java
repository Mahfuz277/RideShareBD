import java.util.ArrayList;

public class Rider {

    static int idCounter = 1;

    int id;
    String name;
    String phone;
    String email;
    ArrayList<Ride> rideHistory;

    public Rider(String name, String phone, String email) {
        this.id = idCounter++;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.rideHistory = new ArrayList<>();
    }

    public void showInfo() {
        System.out.println("Rider ID: " + id + " | Name: " + name +
                " | Phone: " + phone + " | Email: " + email +
                " | Total Rides: " + rideHistory.size());
    }

    public void showRideHistory() {
        if (rideHistory.isEmpty()) {
            System.out.println("No ride history found.");
            return;
        }
        System.out.println("Ride history for " + name + ":");
        for (Ride ride : rideHistory) {
            ride.showInfo();
        }
    }
}
