/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jjesusmonroy
 */
public class Database {
    Connection con;
    String database,username,password,socket;
    Methods m;
    public Database() {
        m=new Methods();
        con = null;
        database="generator";
        username="root";
        password="root";
        socket="localhost:3306";
    }
    
    public ResultSet connection(String query){
        ResultSet rs=null;
        try{
            con = DriverManager.getConnection
                ("jdbc:mysql://"+socket+"/"+database+"?autoReconnect=true&useSSL=false",username,password);
            Statement st = con.createStatement();
            rs=st.executeQuery(query);
        }catch(SQLException e){
            System.out.println("Error on m connection at c Database\n"+e.getMessage());
        }
        return rs;
    }
    
    public void execute(String query){
        try{
            
            con = DriverManager.getConnection
                ("jdbc:mysql://"+socket+"/"+database+"?autoReconnect=true&useSSL=false",username,password);      
            try (Statement st = con.createStatement()) {
                st.executeUpdate(query);
                st.close();
            }
        }catch(SQLException e){
            System.out.println("Error on m execute at c Database\n"+e.getMessage());
        }finally{
            if(con!=null)try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public void insert(String table, String [] elements){
        String insert="insert into "+ table+" values (";
        for(int i=0; i<elements.length;i++){
            if(i==elements.length-1){
                if(elements[i]==null){insert+="null";}
                else insert+="'"+elements[i]+"'";}
            else {
                if(elements[i]==null){insert+="null,";}
                else insert+="'"+elements[i]+"',";}
        }
        insert+=")";
        try{
            con = DriverManager.getConnection
                ("jdbc:mysql://"+socket+"/"+database+"?autoReconnect=true&useSSL=false",username,password);      
            try (Statement st = con.createStatement()) {
                st.executeUpdate(insert);
                st.close();
            }
        }catch(SQLException e){
            System.out.println("Error on m insert at c Database\n"+e.getMessage());
        }finally{
            if(con!=null)try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String[][]  query(String query){
        String [] columnNames = m.columnNames(query);
        ResultSet resul = connection(query);        
        int columnas = columns(query);
        int renglones = rows(query);
        String [][] datos = new String [renglones][columnas]; 
            try{
                int aux=0;
                while(resul.next()){
                
                    for(int i=0; i<columnas; i++){
                        if(!m.needColumnNames(query)){
                        datos[aux][i]=resul.getString(i+1);
                        }
                        else datos[aux][i]=resul.getString(columnNames[i]);
                    }
                if(aux<renglones)aux++;   
            }
            }catch(SQLException e){
                System.out.println("Error en m obtener consultas c BDD \n"+e.getMessage());
            }finally{
            try {
                if(resul!=null)resul.close();
                if(con!=null)con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        return datos;
    }
    
    private int columns(String query){
        int columnas=0;
        ResultSet resul = connection(query);
        try {
            ResultSetMetaData rsmd = resul.getMetaData();
            columnas = rsmd.getColumnCount();
        } catch (SQLException e) {
            System.out.println("Error en m columnCounter c BDD\n" + e.getMessage());
        } finally {
            try {
                if (resul != null) {
                    resul.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return columnas;
    }
    
    private int rows(String query){
        int renglones=0;
        ResultSet resul = connection(query);
        try{
            resul.last();
            renglones = resul.getRow();
        }catch(SQLException e){
            System.out.println("Error en m noregistros c BDD \n"+e.getMessage());
        }
        finally{
            try {
                if(resul!=null)resul.close();
                if(con!=null)con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        return renglones;
    }
}


