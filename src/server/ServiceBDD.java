import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.List;
import java.util.ArrayList;
import struct.Groupe;
import struct.Message;
import struct.Ticket;
import java.util.Date;


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
    public int nextIdFil (int idGrp){
        int nextID=0;
        int nbFil=nbFil(idGrp);
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
            ResultSet result = state.executeQuery("SELECT * FROM fil_de_discussion WHERE fil_groupe_id="+idGrp+"");
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
    public int nbFil (int idGrp){
        int nbFil=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM fil_de_discussion WHERE fil_groupe_id="+idGrp+"");
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
    public int nextIdMsg(int idFil,int idGrp){
        int nextID=0;
        int nbFil=nbMsg(idFil,idGrp);
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
            ResultSet result = state.executeQuery("SELECT * FROM message WHERE msg_fil_id="+idFil+" AND msg_groupe_id="+idGrp+"");
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
    public int nbMsg (int idFil,int idGrp){
        int nbMsg=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM message WHERE msg_fil_id="+idFil+" AND msg_groupe_id="+idGrp+"");
            while(result.next()) nbMsg++;
            result.close();
            state.close();
            return nbMsg;
            }catch(Exception e){
                e.printStackTrace();
                return nbMsg;
            }
    }
    @Override
    public boolean ajouterFil(int idFil,String nom,int idGrp,int idMsg,String contenuMsg,Date date){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            nom="'"+nom+"'";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(date);
            currentTime="'"+currentTime+"'";
            state.executeUpdate("INSERT INTO fil_de_discussion (fil_id, fil_nom,fil_groupe_id,fil_premier_msg_id,fil_date_dernier_msg) VALUES ("+idFil+","+nom+","+idGrp+","+idMsg+","+currentTime+");");
            state.close();
            ServiceBDD s=new ServiceBDD();
            return s.ajouterMsg(idMsg, contenuMsg, idFil,idGrp,date);
        }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1){
            System.out.println("idFil déjà utiliser !");
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean ajouterMsg(int idMsg,String contenuMsg,int idFil,int idGrp,Date date){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            contenuMsg="'"+contenuMsg+"'";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(date);
            currentTime="'"+currentTime+"'";
            state.executeUpdate("INSERT INTO message (msg_id, msg_date,msg_contenu,msg_fil_id,msg_groupe_id) VALUES ("+idMsg+","+currentTime+","+contenuMsg+","+idFil+","+idGrp+");");
            state.executeUpdate("UPDATE fil_de_discussion SET fil_date_dernier_msg= "+currentTime+" WHERE fil_id="+idFil+" AND fil_groupe_id="+idGrp+";");
            state.close();
        }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1){
            System.out.println("idFil déjà utiliser pour un message!");
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List <Message> getListeMessage(int idFil,int idGrp){
        List<Message> l= new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM message WHERE msg_fil_id="+idFil+" AND msg_groupe_id="+idGrp+"");
            while(result.next()){
                int id=Integer.parseInt(result.getObject(1).toString());
                String contenu=result.getObject(3).toString();
                DateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                Date date=(Date) formatter.parse(result.getObject(2).toString());
                Message m= new Message(contenu,date,idGrp,idFil,id);
                l.add(m);
            }
            result.close();
            state.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return l;
    }
    public Message getFirstMessage(int idGrp,int idFil,int msgId){
        Message m;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM message WHERE msg_groupe_id="+idGrp+" AND msg_fil_id="+idFil+" AND msg_id="+msgId+"");
            result.next();
            int id=Integer.parseInt(result.getObject(1).toString());
            String contenu=result.getObject(3).toString();
            DateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
            Date date=(Date) formatter.parse(result.getObject(2).toString());
            m= new Message(contenu,date,idGrp,idFil,id);
            result.close();
            state.close();
            return m;
        }catch(Exception e){
            e.printStackTrace();
        }
        throw new NullPointerException();
    }
    @Override
    public List <Ticket> getListeTicket(int idGrp){
        List<Ticket> l= new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM fil_de_discussion WHERE fil_groupe_id="+idGrp+"");
            while(result.next()){
                int idFil=Integer.parseInt(result.getObject(1).toString());
                String nom=result.getObject(2).toString();
                int msgId=Integer.parseInt(result.getObject(4).toString());
                DateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                Date date=(Date) formatter.parse(result.getObject(5).toString());
                Ticket t=new Ticket(nom,idFil,getFirstMessage(idGrp,idFil,msgId),date,idGrp);
                l.add(t);
            }
            result.close();
            state.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return l;   
    }
    public String getNomGroupe(int idGroupe){
        String nom="";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM groupe WHERE grp_id="+idGroupe+"");
            result.next();
            nom=result.getObject(2).toString();
            result.close();
            state.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return nom;
    }
    @Override
    public List <Groupe> getListeGroupe(String idUti){
        List<Groupe> l= new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            idUti="'"+idUti+"'";
            ResultSet result = state.executeQuery("SELECT * FROM utilisateur_has_groupe WHERE utilisateur_uti_id="+idUti+"");
            while(result.next()){
                int gId=Integer.parseInt(result.getObject(2).toString());
                Groupe g= new Groupe(getNomGroupe(gId),gId);
                l.add(g);
            }
            result.close();
            state.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return l;   
    }
    @Override
    public List <Ticket> getListeTicket(String idUti){
        List<Ticket> l= new ArrayList();
        List<Groupe> l2=getListeGroupe(idUti);
        for(Groupe g : l2){
          l.addAll(getListeTicket(g.getIdGroupe()));
        }
        return l;       
    }
    @Override
    public boolean utilisateurExiste(String idUti){
        boolean existe;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:8889/mydb";
            String user="root";
            String passwd ="root";
            Connection conn= DriverManager.getConnection(url,user,passwd);
            Statement state= conn.createStatement();
            idUti="'"+idUti+"'";
            ResultSet result = state.executeQuery("SELECT * FROM utilisateur WHERE uti_id="+idUti+"");
            existe=result.next();
            result.close();
            state.close();
            return existe;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
    }





}
