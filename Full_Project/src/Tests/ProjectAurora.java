
package Tests;

public class ProjectAurora {
    
    String name;
    
    public static void main(String[] args) {
        ProjectAurora pa = new ProjectAurora();
        pa.setName("Sunera Thineth");
        String result = pa.getName();
        
        System.out.println(result);
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
}
