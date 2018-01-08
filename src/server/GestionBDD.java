
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matthieulenoir
 */
public class GestionBDD implements IGestionBDD{
    @Override
    public boolean ajouterUtilisateur(int idUtilisateur,String nom,String prenom,String password){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            nom="'"+nom+"'";
            prenom="'"+prenom+"'";
            password="'"+password+"'";
            state.executeUpdate("INSERT INTO utilisateur(uti_id, uti_nom, uti_prenom, uti_password) VALUES ("+idUtilisateur+","+nom+","+prenom+","+password+");");
            state.close();
        }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1){
            System.out.println("idUtilisateur déjà utiliser !");
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean supprimerUtilisateur(int idUtilisateur){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            state.executeUpdate("DELETE FROM utilisateur_has_groupe WHERE utilisateur_uti_id="+idUtilisateur+"");
            state.executeUpdate("DELETE FROM utilisateur WHERE uti_id="+idUtilisateur+"");
            state.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean ajouterGroupe(int idGroupe,String nom){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            nom="'"+nom+"'";
            state.executeUpdate("INSERT INTO groupe (grp_id, grp_nom) VALUES ("+idGroupe+","+nom+");");
            state.close();
        }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1){
            System.out.println("idGroupe déjà utiliser !");
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean supprimerGroupe(int idGroupe){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            state.executeUpdate("DELETE FROM message WHERE msg_groupe_id="+idGroupe+"");
            state.executeUpdate("DELETE FROM fil_de_discussion WHERE fil_groupe_id="+idGroupe+"");
            state.executeUpdate("DELETE FROM utilisateur_has_groupe WHERE groupe_grp_id="+idGroupe+"");
            state.executeUpdate("DELETE FROM groupe WHERE grp_id="+idGroupe+"");
            state.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean ajouterUtilisateurAGroupe(int idGroupe,int idUtilisateur){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            state.executeUpdate("INSERT INTO utilisateur_has_groupe (utilisateur_uti_id, groupe_grp_id) VALUES ("+idUtilisateur+","+idGroupe+");");
            state.close();
        }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1){
            System.out.println("idGroupe et idUtilisateur déjà associer !");
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean modifierUtilisateur(int idUtilisateur,String nom,String prenom,String password){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            nom="'"+nom+"'";
            prenom="'"+prenom+"'";
            password="'"+password+"'";
            state.executeUpdate("UPDATE utilisateur SET uti_nom= "+nom+" WHERE uti_id="+idUtilisateur+";");
            state.executeUpdate("UPDATE utilisateur SET uti_prenom= "+prenom+" WHERE uti_id="+idUtilisateur+";");
            state.executeUpdate("UPDATE utilisateur SET uti_password= "+password+" WHERE uti_id="+idUtilisateur+";");
            state.close();
        }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1){
            System.out.println("idUtilisateur déjà utiliser !");
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
           return true;
    }
}
