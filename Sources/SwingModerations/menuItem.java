
package SwingModerations;

import ItemBluePrints.menuBar_bluePrint;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class menuItem extends javax.swing.JPanel {
    
    private boolean selected;
    private boolean over;
    
    public menuItem(menuBar_bluePrint data) {
        initComponents();
        setOpaque(false);
        if (data.getType() == menuBar_bluePrint.MenuType.MENU) {
            lbl_Name.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lbl_Name.setText(data.getName());
        } else if (data.getType() == menuBar_bluePrint.MenuType.TITLE) {
            lbl_Name.setText(data.getName());
            lbl_Name.setVisible(true);
            lbl_Name.setFont(new Font("Segoe UI", Font.BOLD, 14));
            //lbl_Name.setForeground(Color.YELLOW);
        } else {
            lbl_Name.setText("");
        }
    }
    
    public void setSelected(boolean  selected) {
        this.selected = selected;
        repaint();
    }
    
    public void setOVer(boolean over) {
        this.over = over;
        repaint();
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_Name = new javax.swing.JLabel();

        lbl_Name.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl_Name.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Name.setText("Menu Item Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lbl_Name)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Name, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        if (selected || over) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (selected) {
                g2.setColor(new Color(255, 255, 255, 80));
            } else {
                g2.setColor(new Color(255, 255, 255, 20));
            }
            g2.fillRoundRect(10, 0, getWidth() - 20, getHeight(), 5, 5);
        }

        super.paintComponent(grphcs);
    }
    
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_Name;
    // End of variables declaration//GEN-END:variables
}
