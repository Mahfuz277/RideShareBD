public class RideRequest {

    static int idCounter = 1;

    int id;
    Rider rider;
    String pickup;
    String destination;
    String status;

    public RideRequest(Rider rider, String pickup, String destination) {
        this.id = idCounter++;
        this.rider = rider;
        this.pickup = pickup;
        this.destination = destination;
        this.status = "PENDING";
    }

    public void showInfo() {
        System.out.println("Request ID: " + id + " | Rider: " + rider.name +
                " | From: " + pickup + " -> To: " + destination +
                " | Status: " + status);
    }
}

