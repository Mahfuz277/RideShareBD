public class Vehicle {

    String type;
    String model;
    String plate;

    public Vehicle(String type, String model, String plate) {
        this.type = type;
        this.model = model;
        this.plate = plate;
    }

    public void showInfo() {
        System.out.println("Vehicle: " + type + " | Model: " + model + " | Plate: " + plate);
    }
}
