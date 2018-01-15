
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matthieulenoir
 */
public class AjouterUtilisateurAGroupeFrame extends javax.swing.JFrame {

    /**
     * Creates new form AjouterUtilisateurFrame
     */
    public AjouterUtilisateurAGroupeFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));

        jPanel1.setBackground(java.awt.Color.darkGray);
        jPanel1.setLayout(new java.awt.GridLayout(1, 3));

        jPanel2.setBackground(java.awt.Color.darkGray);
        jPanel2.setLayout(new java.awt.GridLayout(5, 0));

        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("Id groupe:");
        jPanel2.add(jLabel2);
        jPanel2.add(jLabel1);

        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setText("Id utilisateur:");
        jPanel2.add(jLabel3);
        jPanel2.add(jLabel4);

        jButton2.setBackground(java.awt.Color.white);
        jButton2.setText("Annuler");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        jPanel1.add(jPanel2);

        jPanel3.setBackground(java.awt.Color.darkGray);
        jPanel3.setLayout(new java.awt.GridLayout(5, 0));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField1);

        jPanel6.setBackground(java.awt.Color.darkGray);
        jPanel3.add(jPanel6);
        jPanel3.add(jTextField3);

        jPanel5.setBackground(java.awt.Color.darkGray);
        jPanel3.add(jPanel5);

        jButton1.setBackground(java.awt.Color.white);
        jButton1.setText("Valider");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jPanel1.add(jPanel3);

        jPanel4.setBackground(java.awt.Color.darkGray);
        jPanel1.add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
        new JFrameServeur().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String idUtilisateur="";
        Boolean idOK=true;
        GestionBDD serv=new GestionBDD();
        try{
            idUtilisateur=jTextField3.getText();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(idOK){ 
            try{
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println(" Unable to load driver. ");
            }
            String url = "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11215552";
            String username = "sql11215552";
            String passd = "HLaZ9P7drD";
            try {
                Connection conn = DriverManager.getConnection(url, username, passd);
                Statement state= conn.createStatement();
                idUtilisateur="'"+idUtilisateur+"'";
                ResultSet result = state.executeQuery("SELECT * FROM utilisateur WHERE uti_id="+idUtilisateur+"");
                if(!result.next()){
                    jTextField3.setText("Id utilisateur inexistant");
                    idOK=false;
                }
                result.close();
                state.close();
                conn.close();
            }catch(Exception e){
                idOK=false;
                jTextField3.setText("Erreur inconnu");
                e.printStackTrace();
            }
            if(idOK){
                int idGroupe=0;
                try{
                    idGroupe=Integer.parseInt(jTextField1.getText());
                }catch(java.lang.NumberFormatException e){
                     idOK=false;
                     jTextField1.setText("Id Groupe est un entier");
                }
                if(idOK){ 
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                    } catch (ClassNotFoundException e) {
                        System.out.println(" Unable to load driver. ");
                    }
                    url = "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11215552";
                    username = "sql11215552";
                    passd = "HLaZ9P7drD";
                    try {
                        Connection conn = DriverManager.getConnection(url, username, passd);
                        Statement state= conn.createStatement();
                        ResultSet result = state.executeQuery("SELECT * FROM groupe WHERE grp_id="+idGroupe+"");
                        if(!result.next()){
                            jTextField1.setText("Id groupe inexistant");
                            idOK=false;
                        }
                        result.close();
                        state.close();
                        conn.close();
                     }catch(Exception e){
                        idOK=false;
                        jTextField1.setText("Erreur inconnu");
                        e.printStackTrace();
                    }
                    if(idOK){
                        if(serv.ajouterUtilisateurAGroupe(idGroupe, idUtilisateur)){
                                    this.setVisible(false);
                                    new JFrameServeur().setVisible(true);
                        }
                        jTextField1.setText("idGroupe et idUtilisateur déjà associer");
                        jTextField3.setText("idGroupe et idUtilisateur déjà associer");
                    }
                }
            }   
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AjouterUtilisateurFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AjouterUtilisateurFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AjouterUtilisateurFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AjouterUtilisateurFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AjouterUtilisateurFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
