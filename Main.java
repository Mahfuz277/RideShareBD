import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static RideSharing rs = new RideSharing();

    public static void main(String[] args) {

       
        System.out.println("   Welcome to RideShare BD   ");
       

        boolean running = true;

        while (running) {
            System.out.println("\n MAIN MENU  ");
            System.out.println("1. Rider Menu");
            System.out.println("2. Driver Menu");
            System.out.println("3. Ride Menu");
            System.out.println("4. Admin Panel");
            System.out.println("5. Fare Calculator");
            System.out.println("6. Save Data");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) riderMenu();
            else if (choice == 2) driverMenu();
            else if (choice == 3) rideMenu();
            else if (choice == 4) rs.showAdminPanel();
            else if (choice == 5) fareMenu();
            else if (choice == 6) rs.saveAll();
            else if (choice == 0) {
                rs.saveAll(); 
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    static void riderMenu() {
        System.out.println("\n-- RIDER MENU --");
        System.out.println("1. Register Rider");
        System.out.println("2. Show All Riders");
        System.out.println("3. Show Ride History");
        System.out.print("Choice: ");
        int ch = Integer.parseInt(sc.nextLine());

        if (ch == 1) {
            System.out.print("Name  : "); String name = sc.nextLine();
            System.out.print("Phone : "); String phone = sc.nextLine();
            System.out.print("Email : "); String email = sc.nextLine();
            rs.registerRider(name, phone, email);

        } else if (ch == 2) {
            rs.showAllRiders();

        } else if (ch == 3) {
            System.out.print("Rider ID: ");
            int id = Integer.parseInt(sc.nextLine());
            Rider rider = rs.findRider(id);
            if (rider != null) rider.showRideHistory();
            else System.out.println("Rider not found.");
        }
    }

    static void driverMenu() {
        System.out.println("\n-- DRIVER MENU --");
        System.out.println("1. Register Driver");
        System.out.println("2. Show All Drivers");
        System.out.println("3. Show Available Drivers");
        System.out.print("Choice: ");
        int ch = Integer.parseInt(sc.nextLine());

        if (ch == 1) {
            System.out.print("Name          : "); String name = sc.nextLine();
            System.out.print("Phone         : "); String phone = sc.nextLine();
            System.out.print("Vehicle Type (BIKE/CAR/SUV): "); String vt = sc.nextLine().toUpperCase();
            System.out.print("Vehicle Model : "); String vm = sc.nextLine();
            System.out.print("License Plate : "); String plate = sc.nextLine();
            rs.registerDriver(name, phone, vt, vm, plate);

        } else if (ch == 2) {
            rs.showAllDrivers();

        } else if (ch == 3) {
            System.out.print("Vehicle type (BIKE/CAR/SUV/ALL): ");
            String vt = sc.nextLine().toUpperCase();
            rs.showAvailableDrivers(vt.equals("ALL") ? null : vt);
        }
    }

    static void rideMenu() {
        System.out.println("\n-- RIDE MENU --");
        System.out.println("1. Book a Ride");
        System.out.println("2. Complete a Ride");
        System.out.println("3. Cancel a Ride");
        System.out.println("4. Show All Rides");
        System.out.print("Choice: ");
        int ch = Integer.parseInt(sc.nextLine());

        if (ch == 1) {
            System.out.print("Rider ID      : "); int riderId = Integer.parseInt(sc.nextLine());
            System.out.print("Pickup        : "); String pickup = sc.nextLine();
            System.out.print("Destination   : "); String dest = sc.nextLine();
            System.out.print("Vehicle (BIKE/CAR/SUV): "); String vt = sc.nextLine().toUpperCase();
            System.out.print("Distance (km) : "); double dist = Double.parseDouble(sc.nextLine());
            rs.bookRide(riderId, pickup, dest, vt, dist);

        } else if (ch == 2) {
            System.out.print("Ride ID: "); int rideId = Integer.parseInt(sc.nextLine());
            System.out.print("Rate driver (1.0 - 5.0): "); double rating = Double.parseDouble(sc.nextLine());
            rs.completeRide(rideId, rating);

        } else if (ch == 3) {
            System.out.print("Ride ID: "); int rideId = Integer.parseInt(sc.nextLine());
            rs.cancelRide(rideId);

        } else if (ch == 4) {
            rs.showAllRides();
        }
    }

    static void fareMenu() {
        System.out.println("\n-- FARE CALCULATOR --");
        System.out.print("Vehicle Type (BIKE/CAR/SUV): "); String vt = sc.nextLine().toUpperCase();
        System.out.print("Distance (km): "); double dist = Double.parseDouble(sc.nextLine());
        rs.calculateFare(vt, dist);
        System.out.println("Rates: BIKE = 20 + 10/km | CAR = 50 + 20/km | SUV = 80 + 30/km");
    }
}


