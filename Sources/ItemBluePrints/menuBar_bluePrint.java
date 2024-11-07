
package ItemBluePrints;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class menuBar_bluePrint {
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public MenuType getType() {
        return type;
    }
    
    public void setType(MenuType type) {
        this.type = type;
    }

    public menuBar_bluePrint(String name, MenuType type) {
//        this.icon = icon;
        this.name = name;
        this.type = type;
    }
    
//    private String icon;
    private String name;
    private MenuType type;
    
//    public Icon toIcon() {
//        return new ImageIcon(getClass().getResource("/Icons/" + icon + ".png"));
//    }
    
    public static enum MenuType {
        TITLE, MENU, EMPTY
    }
}
