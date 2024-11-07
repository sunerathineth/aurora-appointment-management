
package Main;

import BasicSecurity.LowLevelLogin;
import MainComponents.Appointments_component;
import MainComponents.Doctor_component;
import MainComponents.NewAppointments_component;
import MainComponents.Treatment_component;
import WelcomeComponents.welcomeScreen;
import actionTriggers.action_selectedMenu;
import components.Appointment;
import components.DataRepository;
import components.Doctor;
import components.Patient;
import components.Treatment;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.ws.Response;

public class Main extends javax.swing.JFrame {
    
    
    
    public Main() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        menu.initMoving(Main.this);
        DataRepository.createPreliminaryData();
        sectionSwitch(new welcomeScreen());
        
        menu.add_action_selectedMenu(new action_selectedMenu() {
            @Override
            public void selected(int index) {
                if (index == 1) {
                    sectionSwitch(new Appointments_component());
                } else if (index == 2) {
                    sectionSwitch(new Treatment_component());
                } else if (index == 3) {
                } else if (index == 4) {
                    sectionSwitch(new Doctor_component());
                } else if (index == 10) {
                    int response = JOptionPane.showConfirmDialog(Main.this, "Do you really want to lock Aurora?", "Aurora Security", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (response == JOptionPane.YES_OPTION) {
                        lockSystem();
                    }
                }
                
                else if (index == 11) {
                    ExitAurora();
                }
            }
        });
    }
    
    private void lockSystem() {
        this.setVisible(false);
        LowLevelLogin login = new LowLevelLogin();
        login.setVisible(true);
    }
    
    public void sectionSwitch(JComponent sectionForm) {
        foundationalPanel.removeAll();
        foundationalPanel.add(sectionForm);
        foundationalPanel.repaint();
        foundationalPanel.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu2 = new Sections.Menu();
        menu1 = new Sections.Menu();
        panelBorder1 = new SwingModerations.roundBorders();
        menu = new Sections.Menu();
        roundBorders1 = new SwingModerations.roundBorders();
        foundationalPanel = new javax.swing.JPanel();
        roundBorders2 = new SwingModerations.roundBorders();
        newAppointment_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelBorder1.setBackground(new java.awt.Color(245, 245, 245));

        roundBorders1.setBackground(new java.awt.Color(255, 255, 255));

        foundationalPanel.setMaximumSize(new java.awt.Dimension(939, 695));
        foundationalPanel.setOpaque(false);
        foundationalPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout roundBorders1Layout = new javax.swing.GroupLayout(roundBorders1);
        roundBorders1.setLayout(roundBorders1Layout);
        roundBorders1Layout.setHorizontalGroup(
            roundBorders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundBorders1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(foundationalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundBorders1Layout.setVerticalGroup(
            roundBorders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundBorders1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(foundationalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        roundBorders2.setBackground(new java.awt.Color(255, 255, 255));

        newAppointment_btn.setBackground(new java.awt.Color(255, 255, 255));
        newAppointment_btn.setText("New Appointment");
        newAppointment_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newAppointment_btnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel1.setText("Fresh start ahead, Monica");

        javax.swing.GroupLayout roundBorders2Layout = new javax.swing.GroupLayout(roundBorders2);
        roundBorders2.setLayout(roundBorders2Layout);
        roundBorders2Layout.setHorizontalGroup(
            roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundBorders2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 657, Short.MAX_VALUE)
                .addComponent(newAppointment_btn)
                .addContainerGap())
        );
        roundBorders2Layout.setVerticalGroup(
            roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundBorders2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newAppointment_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundBorders1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundBorders2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundBorders2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundBorders1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void newAppointment_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newAppointment_btnActionPerformed
        sectionSwitch(new NewAppointments_component());
        
    }//GEN-LAST:event_newAppointment_btnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    
    public void ExitAurora() {
        int reponse = JOptionPane.showConfirmDialog(Main.this, "You are about to Logout", "Confirm Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (reponse == JOptionPane.YES_OPTION) {
//            LowLevelLogin login = new LowLevelLogin();
//            login.dispose();
            
            Main.this.dispose();
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel foundationalPanel;
    private javax.swing.JLabel jLabel1;
    private Sections.Menu menu;
    private Sections.Menu menu1;
    private Sections.Menu menu2;
    private javax.swing.JButton newAppointment_btn;
    private SwingModerations.roundBorders panelBorder1;
    private SwingModerations.roundBorders roundBorders1;
    private SwingModerations.roundBorders roundBorders2;
    // End of variables declaration//GEN-END:variables
}
