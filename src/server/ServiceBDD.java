import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.text.SimpleDateFormat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matthieulenoir
 */
public class ServiceBDD implements IServiceBDD{
    @Override
    public boolean authentification(String idUtilisateur,String motDePasse){
        boolean authOK;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            idUtilisateur="'"+idUtilisateur+"'";
            motDePasse="'"+motDePasse+"'";
            ResultSet result = state.executeQuery("SELECT * FROM utilisateur WHERE uti_id="+idUtilisateur+" AND uti_password="+motDePasse+"");
            authOK=result.next();
            result.close();
            state.close();
            return authOK;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
    }
    @Override
    public int nextIdFil (){
        int nextID=0;
        int nbFil=nbFil();
        if(nbFil==0){
            return nextID;
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM fil_de_discussion");
            for(int i=0;i<nbFil;i++){
                result.next();
            }
            nextID=Integer.parseInt(result.getObject(1).toString())+1;
            result.close();
            state.close();
            return nextID;
            }catch(Exception e){
                e.printStackTrace();
                return nextID;
            }
    }
    public int nbFil (){
        int nbFil=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM fil_de_discussion");
            while(result.next()) nbFil++;
            result.close();
            state.close();
            return nbFil;
            }catch(Exception e){
                e.printStackTrace();
                return nbFil;
            }
    }
    @Override
    public int nextIdMsg(){
        int nextID=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM message");
            while(result.next()) nextID++;
            result.close();
            state.close();
            return nextID;
            }catch(Exception e){
                e.printStackTrace();
                return nextID;
            }
    }
    @Override
    public boolean ajouterFil(int idFil,String nom,int idGrp,int idMsg,String contenuMsg){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            nom="'"+nom+"'";
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            currentTime="'"+currentTime+"'";
            state.executeUpdate("INSERT INTO fil_de_discussion (fil_id, fil_nom,fil_groupe_id,fil_premier_msg_id,fil_date_dernier_msg) VALUES ("+idFil+","+nom+","+idGrp+","+idMsg+","+currentTime+");");
            state.close();
            ServiceBDD s=new ServiceBDD();
            return s.ajouterMsg(idMsg, contenuMsg, idFil);
        }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1){
            System.out.println("idFil déjà utiliser !");
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public int getIdGrp(int idFil){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM fil_de_discussion WHERE fil_id="+idFil+"");
            result.next();
            int idGrp= Integer.parseInt(result.getObject(3).toString());
            result.close();
            state.close();
            return idGrp;
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public boolean ajouterMsg(int idMsg,String contenuMsg,int idFil){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            contenuMsg="'"+contenuMsg+"'";
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            currentTime="'"+currentTime+"'";
            int idGrp=getIdGrp(idFil);
            state.executeUpdate("INSERT INTO message (msg_id, msg_date,msg_contenu,msg_fil_id,msg_groupe_id) VALUES ("+idMsg+","+currentTime+","+contenuMsg+","+idFil+","+idGrp+");");
            state.executeUpdate("UPDATE fil_de_discussion SET fil_date_dernier_msg= "+currentTime+" WHERE fil_id="+idFil+";");
            state.close();
        }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1){
            System.out.println("idFil déjà utiliser !");
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
