
package MainComponents;

import SwingModerations.ScrollBar;
import components.Appointment;
import components.DataRepository;
import components.Treatment;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;

public class Treatment_component extends javax.swing.JPanel {

    private static DefaultTableModel tableModel;
    
    List<Treatment> treatments = DataRepository.treatments;
    
    public Treatment_component() {
        initComponents();
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        initializeTable();
        
        ScrollBar customScrollBar = new ScrollBar();
        customScrollBar.setPreferredSize(new Dimension(10, Integer.MAX_VALUE));

        customScrollBar.setUI(new BasicScrollBarUI() {
            private final Dimension d = new Dimension();

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    @Override public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {
                    @Override public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Scrollbar background should be changed into hash color later
                
                g2.setColor(new Color(255, 255, 255, 0));
                g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color color;
                if (isDragging) {
                    color = new Color(101, 178, 252);
                } else if (isThumbRollover()) {
                    color = new Color(197, 233, 252, 255);
                } else {
                    color = new Color(20, 86, 204, 180);
                }

                g2.setPaint(color);
                g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
                g2.setPaint(Color.WHITE);
                g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected void setThumbBounds(int x, int y, int width, int height) {
                super.setThumbBounds(x, y, width, height);
                scrollbar.repaint();
            }
        });

        jScrollPane1.setVerticalScrollBar(customScrollBar);
        customScrollBar.setOpaque(false);
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);

        for (Treatment treatment : DataRepository.treatments) {
            String treatmentName = treatment.getName();
            double treatmentPrice = treatment.getPrice();

            tableModel.addRow(new Object[]{treatmentName, treatmentPrice});
        }
    }

    private void initializeTable() {
        if (tableModel == null) {
            String[] columnHeaders = {"Treatment Type", "Treatment Price"};
            tableModel = new DefaultTableModel(columnHeaders, 0);
            treatment_table.setModel(tableModel);
            populateTableWithAppointments();
        } else {
            treatment_table.setModel(tableModel);
        }
    }

    private void populateTableWithAppointments() {
        for (Treatment treatment : DataRepository.treatments) {
            String treatmentName = treatment.getName();
            double treatmentPrice = treatment.getPrice();

            tableModel.addRow(new Object[]{treatmentName, treatmentPrice});
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        treatment_table = new SwingModerations.Table();

        setMaximumSize(new java.awt.Dimension(953, 695));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(953, 695));

        jScrollPane1.setBorder(null);

        treatment_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(treatment_table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 905, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private SwingModerations.Table treatment_table;
    // End of variables declaration//GEN-END:variables
}
