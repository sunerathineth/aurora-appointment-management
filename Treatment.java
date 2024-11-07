
package components;

public class Treatment {
    private static int idCounter = 1;
    private String name;
    private int treatmentID;
    private double price;
    
    public Treatment(String name, double price) {
        this.treatmentID = idCounter++;
        this.name = name;
        this.price = price;
    }
    
    // Setter
    
    public void setName(String name) { this.name = name; }
    
    // Getters
    
    public String getName() { return name; }
    public double getPrice() { return price; }
    
    public void displayTreatment() {
        System.out.println("Treatment Name: " + name);
        System.out.println("Treatment Price: " + price + " LKR");
    }
}
