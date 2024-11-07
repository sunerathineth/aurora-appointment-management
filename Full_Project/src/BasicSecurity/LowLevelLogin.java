
package BasicSecurity;

import Main.Main;
import MainComponents.Appointments_component;
import java.awt.Color;
import javax.swing.JOptionPane;

public class LowLevelLogin extends javax.swing.JFrame {

    public LowLevelLogin() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        focusLBL.requestFocus();
    }
    
    private void BasicSecurity() {
        String get_username = usernameTF.getText().toLowerCase();
        String get_password = passwordTF.getText().toLowerCase();
        
        String correct_username = "sunera";
        String correct_password = "sunera";
        
        if (get_username.equals(correct_username) && get_password.equals(correct_password)) {
            Main main = new Main();
            main.setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect username or password.", "Aurora Security", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundBorders1 = new SwingModerations.roundBorders();
        roundBorders2 = new SwingModerations.roundBorders();
        jPanel1 = new javax.swing.JPanel();
        focusLBL = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        usernameTF = new javax.swing.JTextField();
        loginBtn = new javax.swing.JButton();
        passwordTF = new javax.swing.JTextField();
        loginBtn1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setUndecorated(true);

        roundBorders1.setBackground(new java.awt.Color(247, 246, 246));

        roundBorders2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setOpaque(false);

        focusLBL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        focusLBL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo8.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Aurora");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(focusLBL, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(focusLBL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        usernameTF.setForeground(new java.awt.Color(153, 153, 153));
        usernameTF.setText("Username");
        usernameTF.setMargin(new java.awt.Insets(2, 10, 2, 10));
        usernameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameTFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameTFFocusLost(evt);
            }
        });

        loginBtn.setBackground(new java.awt.Color(255, 255, 255));
        loginBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        loginBtn.setForeground(new java.awt.Color(102, 102, 102));
        loginBtn.setText("Cancel");
        loginBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        passwordTF.setForeground(new java.awt.Color(153, 153, 153));
        passwordTF.setText("Password");
        passwordTF.setMargin(new java.awt.Insets(2, 10, 2, 10));
        passwordTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordTFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordTFFocusLost(evt);
            }
        });

        loginBtn1.setBackground(new java.awt.Color(25, 103, 210));
        loginBtn1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        loginBtn1.setForeground(new java.awt.Color(255, 255, 255));
        loginBtn1.setText("Login");
        loginBtn1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        loginBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundBorders2Layout = new javax.swing.GroupLayout(roundBorders2);
        roundBorders2.setLayout(roundBorders2Layout);
        roundBorders2Layout.setHorizontalGroup(
            roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundBorders2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundBorders2Layout.createSequentialGroup()
                        .addComponent(loginBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(usernameTF)
                    .addComponent(passwordTF, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        roundBorders2Layout.setVerticalGroup(
            roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundBorders2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(roundBorders2Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(usernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordTF, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundBorders1Layout = new javax.swing.GroupLayout(roundBorders1);
        roundBorders1.setLayout(roundBorders1Layout);
        roundBorders1Layout.setHorizontalGroup(
            roundBorders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundBorders1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundBorders2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundBorders1Layout.setVerticalGroup(
            roundBorders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundBorders1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundBorders2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundBorders1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundBorders1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameTFFocusGained
        if (usernameTF.getText().equals("Username")) {
            usernameTF.setText("");
            usernameTF.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_usernameTFFocusGained

    private void usernameTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameTFFocusLost
        if (usernameTF.getText().equals("")) {
            usernameTF.setText("Username");
            usernameTF.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_usernameTFFocusLost

    private void passwordTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTFFocusGained
        if (passwordTF.getText().equals("Password")) {
            passwordTF.setText("");
            passwordTF.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_passwordTFFocusGained

    private void passwordTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTFFocusLost
        if (passwordTF.getText().equals("")) {
            passwordTF.setText("Password");
            passwordTF.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_passwordTFFocusLost

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "Do you really want to quite Aurora?", "Aurora Security", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_loginBtnActionPerformed

    private void loginBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtn1ActionPerformed
        BasicSecurity();
    }//GEN-LAST:event_loginBtn1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(LowLevelLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LowLevelLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LowLevelLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LowLevelLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LowLevelLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel focusLBL;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginBtn;
    private javax.swing.JButton loginBtn1;
    private javax.swing.JTextField passwordTF;
    private SwingModerations.roundBorders roundBorders1;
    private SwingModerations.roundBorders roundBorders2;
    private javax.swing.JTextField usernameTF;
    // End of variables declaration//GEN-END:variables
}
