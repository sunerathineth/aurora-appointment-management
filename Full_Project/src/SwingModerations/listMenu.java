
package SwingModerations;

import ItemBluePrints.menuBar_bluePrint;
import actionTriggers.action_selectedMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

public class listMenu <E extends Object> extends JList <E> {
    
    private final DefaultListModel model;
    private int selectedIndex = -1;
    private int overIndex = -1;
    
    private action_selectedMenu action;
    
    public void add_action_selectedMenu(action_selectedMenu action) {
        this.action = action;
    }
    
    public listMenu() {
        model = new DefaultListModel();
        setModel(model);
        addMouseListener(new MouseAdapter() {
           @Override
           public void mousePressed(MouseEvent me) {
               if (SwingUtilities.isLeftMouseButton(me)) {
                   int index = locationToIndex(me.getPoint());
                   Object o = model.getElementAt(index);
                   if (o instanceof menuBar_bluePrint) {
                       menuBar_bluePrint menu = (menuBar_bluePrint) o;
                       if (menu.getType() == menuBar_bluePrint.MenuType.MENU) {
                           selectedIndex = index;
                           if (action != null) {
                               action.selected(index);
                           }
                       }
                   } else {
                       selectedIndex = index;
                   }
                   
                   repaint();
               }
           }

            @Override
            public void mouseExited(MouseEvent me) {
                overIndex = -1;
                repaint();
            }
           
           
        });
        
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                int index = locationToIndex(me.getPoint());
                if (index != overIndex) {
                    Object o = model.getElementAt(index);
                    if (o instanceof menuBar_bluePrint) {
                        menuBar_bluePrint menu = (menuBar_bluePrint) o;
                        if (menu.getType() == menuBar_bluePrint.MenuType.MENU) {
                            overIndex = index;
                        } else {
                            overIndex = -1;
                        }                        
                        repaint();
                    }
                }
            }
           
        });
    }

    @Override
    public ListCellRenderer<? super E> getCellRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> jlist, Object o, int index, boolean selected, boolean focus) {
                menuBar_bluePrint data;
                if (o instanceof menuBar_bluePrint) {
                    data = (menuBar_bluePrint) o;
                } else {
                    data = new menuBar_bluePrint(o + "", menuBar_bluePrint.MenuType.EMPTY);
                }
                
                menuItem item = new menuItem(data);
                item.setSelected(selectedIndex == index);
                item.setOVer(overIndex == index);
                return item;
            }
        };
    }
    
    public void addItem(menuBar_bluePrint data) {
        model.addElement(data);
    }
    
    
    
    
    
}
