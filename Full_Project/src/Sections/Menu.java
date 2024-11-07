
package Sections;

import ItemBluePrints.menuBar_bluePrint;
import actionTriggers.action_selectedMenu;
import java.awt.Color;
import java.awt.Event;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

public class Menu extends javax.swing.JPanel {

    private action_selectedMenu action;
    
    public void add_action_selectedMenu(action_selectedMenu action) {
        this.action = action;
        listMenu1.add_action_selectedMenu(action);
    }
    
    public Menu() {
        initComponents();
        setOpaque(false);
        listMenu1.setOpaque(false);
        init();
    }
    
    private void init() {
        listMenu1.addItem(new menuBar_bluePrint("Dashboard", menuBar_bluePrint.MenuType.TITLE));
        listMenu1.addItem(new menuBar_bluePrint("Appointments", menuBar_bluePrint.MenuType.MENU));
        listMenu1.addItem(new menuBar_bluePrint("Treatments", menuBar_bluePrint.MenuType.MENU));
        listMenu1.addItem(new menuBar_bluePrint("Payments", menuBar_bluePrint.MenuType.MENU));
        listMenu1.addItem(new menuBar_bluePrint("Doctors", menuBar_bluePrint.MenuType.MENU));
        listMenu1.addItem(new menuBar_bluePrint("Reports", menuBar_bluePrint.MenuType.MENU));
        
        listMenu1.addItem(new menuBar_bluePrint("", menuBar_bluePrint.MenuType.EMPTY));
        listMenu1.addItem(new menuBar_bluePrint("More", menuBar_bluePrint.MenuType.TITLE));
        listMenu1.addItem(new menuBar_bluePrint("Activity Log", menuBar_bluePrint.MenuType.MENU));
        listMenu1.addItem(new menuBar_bluePrint("Settings", menuBar_bluePrint.MenuType.MENU));
        listMenu1.addItem(new menuBar_bluePrint("Lock", menuBar_bluePrint.MenuType.MENU));
        listMenu1.addItem(new menuBar_bluePrint("Logout", menuBar_bluePrint.MenuType.MENU));
        
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new SwingModerations.roundBorders();
        movingItem = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        listMenu1 = new SwingModerations.listMenu<>();

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        movingItem.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo3.png"))); // NOI18N
        jLabel1.setText(" Aurora");

        javax.swing.GroupLayout movingItemLayout = new javax.swing.GroupLayout(movingItem);
        movingItem.setLayout(movingItemLayout);
        movingItemLayout.setHorizontalGroup(
            movingItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(movingItemLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel1)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        movingItemLayout.setVerticalGroup(
            movingItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(movingItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addContainerGap())
        );

        listMenu1.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listMenu1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(movingItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(movingItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        // g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        float[] fractions = { 0.0f, 0.5f, 1.0f };
//        Color[] colors = {
//            Color.decode("#000000"),
//            Color.decode("#434343"),
//            Color.decode("#f64f59")
//        };

//        LinearGradientPaint gradient = new LinearGradientPaint(
//            0, 0, getWidth(), getHeight(),
//            fractions,
//            colors
//        );
        
//        g2.setPaint(gradient);


        GradientPaint g = new GradientPaint(0, 0, Color.decode("#1488CC"), 0, getHeight(), Color.decode("#2B32B2"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());

        super.paintChildren(grphcs);
    }
    
    private int x;
    private int y;
    
    public void initMoving(JFrame fram) {
        movingItem.addMouseListener(new MouseAdapter() {            
            @Override
            public void mousePressed(MouseEvent me) {
                x = me.getX();
                y = me.getY();
            }
        });
        movingItem.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                fram.setLocation(me.getXOnScreen() - x, me.getYOnScreen() - y);
            }
        });
    }

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private SwingModerations.listMenu<String> listMenu1;
    private javax.swing.JPanel movingItem;
    private SwingModerations.roundBorders panelBorder1;
    // End of variables declaration//GEN-END:variables
}
