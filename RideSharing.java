import java.util.ArrayList;
import java.io.*;

public class RideSharing {

    ArrayList<Rider> riders = new ArrayList<>();
    ArrayList<Driver> drivers = new ArrayList<>();
    ArrayList<Ride> allRides = new ArrayList<>();

    public RideSharing() {
      
        loadRiders();
        loadDrivers();
        loadRides();
        

        
        if (drivers.isEmpty()) {
            drivers.add(new Driver("Rahim", "01711-000001", new Vehicle("CAR", "Toyota Axio", "DHK-1234")));
            drivers.add(new Driver("Karim", "01711-000002", new Vehicle("BIKE", "Honda CB150R", "DHK-5678")));
            drivers.add(new Driver("Sumon", "01711-000003", new Vehicle("SUV", "Toyota Prado", "DHK-9101")));
            drivers.add(new Driver("Liton", "01711-000004", new Vehicle("CAR", "Nissan March", "DHK-1121")));
            drivers.add(new Driver("Milon", "01711-000005", new Vehicle("BIKE", "Yamaha FZS", "DHK-3141")));
        }

        if (riders.isEmpty()) {
            riders.add(new Rider("Nadia", "01812-000001", "nadia@email.com"));
            riders.add(new Rider("Farhan", "01812-000002", "farhan@email.com"));
            riders.add(new Rider("Tania", "01812-000003", "tania@email.com"));
        }
    }

    

    public void saveAll() {
        saveRiders();
        saveDrivers();
        saveRides();
        System.out.println("All data saved successfully!");
    }

    private void saveRiders() {
        try {
            FileWriter fw = new FileWriter("riders.txt");
            for (Rider r : riders) {
               
                fw.write(r.id + "," + r.name + "," + r.phone + "," + r.email + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving riders: " + e.getMessage());
        }
    }

    private void saveDrivers() {
        try {
            FileWriter fw = new FileWriter("drivers.txt");
            for (Driver d : drivers) {
              
                fw.write(d.id + "," + d.name + "," + d.phone + "," +
                        d.vehicle.type + "," + d.vehicle.model + "," + d.vehicle.plate + "," +
                        d.isAvailable + "," + d.rating + "," + d.totalRides + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving drivers: " + e.getMessage());
        }
    }

    private void saveRides() {
        try {
            FileWriter fw = new FileWriter("rides.txt");
            for (Ride r : allRides) {
              
                fw.write(r.id + "," + r.request.rider.name + "," + r.driver.name + "," +
                        r.request.pickup + "," + r.request.destination + "," +
                        r.distanceKm + "," + r.fare + "," + r.status + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving rides: " + e.getMessage());
        }
    }

  

    private void loadRiders() {
        File file = new File("riders.txt");
        if (!file.exists()) return; 

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
               
                Rider rider = new Rider(p[1], p[2], p[3]);
                riders.add(rider);
            }
            br.close();
            System.out.println("Riders loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading riders: " + e.getMessage());
        }
    }

    private void loadDrivers() {
        File file = new File("drivers.txt");
        if (!file.exists()) return;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
              
                Vehicle vehicle = new Vehicle(p[3], p[4], p[5]);
                Driver driver = new Driver(p[1], p[2], vehicle);
                driver.isAvailable = Boolean.parseBoolean(p[6]);
                driver.rating = Double.parseDouble(p[7]);
                driver.totalRides = Integer.parseInt(p[8]);
                drivers.add(driver);
            }
            br.close();
            System.out.println("Drivers loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading drivers: " + e.getMessage());
        }
    }

    private void loadRides() {
        File file = new File("rides.txt");
        if (!file.exists()) return;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
              
                Rider rider = findRiderByName(p[1]);
                Driver driver = findDriverByName(p[2]);

                if (rider != null && driver != null) {
                    RideRequest request = new RideRequest(rider, p[3], p[4]);
                    request.status = "ACCEPTED";
                    Ride ride = new Ride(request, driver, Double.parseDouble(p[5]));
                    ride.status = p[7];
                    allRides.add(ride);
                }
            }
            br.close();
            System.out.println("Rides loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading rides: " + e.getMessage());
        }
    }

   

    public void registerRider(String name, String phone, String email) {
        Rider rider = new Rider(name, phone, email);
        riders.add(rider);
        System.out.println("Rider registered! ID: " + rider.id);
    }

    public void registerDriver(String name, String phone, String vehicleType,
                               String vehicleModel, String plate) {
        Vehicle vehicle = new Vehicle(vehicleType, vehicleModel, plate);
        Driver driver = new Driver(name, phone, vehicle);
        drivers.add(driver);
        System.out.println("Driver registered! ID: " + driver.id);
    }

   

    public void bookRide(int riderId, String pickup, String destination,
                         String vehicleType, double distance) {
        Rider rider = findRider(riderId);
        if (rider == null) {
            System.out.println("Rider not found!");
            return;
        }

        Driver driver = findAvailableDriver(vehicleType);
        if (driver == null) {
            System.out.println("No available " + vehicleType + " drivers right now.");
            return;
        }

        RideRequest request = new RideRequest(rider, pickup, destination);
        request.status = "ACCEPTED";

        Ride ride = new Ride(request, driver, distance);
        driver.isAvailable = false;
        allRides.add(ride);

        System.out.println("Ride booked successfully!");
        System.out.println("Driver : " + driver.name + " | " + driver.vehicle.model);
        System.out.println("From   : " + pickup + " -> To: " + destination);
        System.out.println("Fare   : " + ride.fare + " BDT");
    }

    public void completeRide(int rideId, double driverRating) {
        Ride ride = findRide(rideId);
        if (ride == null) { System.out.println("Ride not found!"); return; }
        if (!ride.status.equals("ONGOING")) { System.out.println("Ride is not ongoing."); return; }

        ride.status = "COMPLETED";

        Driver driver = ride.driver;
        driver.rating = ((driver.rating * driver.totalRides) + driverRating) / (driver.totalRides + 1);
        driver.totalRides++;
        driver.isAvailable = true;

        ride.request.rider.rideHistory.add(ride);

        System.out.println("Ride completed! Fare: " + ride.fare + " BDT");
    }

    public void cancelRide(int rideId) {
        Ride ride = findRide(rideId);
        if (ride == null) { System.out.println("Ride not found!"); return; }
        if (!ride.status.equals("ONGOING")) { System.out.println("Only ongoing rides can be cancelled."); return; }

        ride.status = "CANCELLED";
        ride.driver.isAvailable = true;
        System.out.println("Ride #" + rideId + " cancelled.");
    }

    public void calculateFare(String vehicleType, double distance) {
        double fare = Ride.calculateFare(distance, vehicleType);
        System.out.println("Estimated fare: " + fare + " BDT");
    }

 

    public void showAllRiders() {
        if (riders.isEmpty()) { System.out.println("No riders found."); return; }
        for (Rider r : riders) r.showInfo();
    }

    public void showAllDrivers() {
        if (drivers.isEmpty()) { System.out.println("No drivers found."); return; }
        for (Driver d : drivers) d.showInfo();
    }

    public void showAvailableDrivers(String vehicleType) {
        boolean found = false;
        for (Driver d : drivers) {
            if (d.isAvailable && (vehicleType == null || d.vehicle.type.equals(vehicleType))) {
                d.showInfo();
                found = true;
            }
        }
        if (!found) System.out.println("No available drivers found.");
    }

    public void showAllRides() {
        if (allRides.isEmpty()) { System.out.println("No rides yet."); return; }
        for (Ride r : allRides) r.showInfo();
    }

    public void showAdminPanel() {
        int completed = 0, cancelled = 0, ongoing = 0;
        double totalRevenue = 0;

        for (Ride r : allRides) {
            if (r.status.equals("COMPLETED")) { completed++; totalRevenue += r.fare; }
            else if (r.status.equals("CANCELLED")) cancelled++;
            else ongoing++;
        }

        System.out.println(" ADMIN PANEL ");
        System.out.println("Total Riders  : " + riders.size());
        System.out.println("Total Drivers : " + drivers.size());
        System.out.println("Total Rides   : " + allRides.size());
        System.out.println("Completed     : " + completed);
        System.out.println("Ongoing       : " + ongoing);
        System.out.println("Cancelled     : " + cancelled);
        System.out.println("Total Revenue : " + totalRevenue + " BDT");
    }

    

    public Rider findRider(int id) {
        for (Rider r : riders) {
            if (r.id == id) return r;
        }
        return null;
    }

    public Ride findRide(int id) {
        for (Ride r : allRides) {
            if (r.id == id) return r;
        }
        return null;
    }

    public Driver findAvailableDriver(String vehicleType) {
        for (Driver d : drivers) {
            if (d.isAvailable && d.vehicle.type.equals(vehicleType)) return d;
        }
        return null;
    }

    private Rider findRiderByName(String name) {
        for (Rider r : riders) {
            if (r.name.equals(name)) return r;
        }
        return null;
    }

    private Driver findDriverByName(String name) {
        for (Driver d : drivers) {
            if (d.name.equals(name)) return d;
        }
        return null;
    }
}
