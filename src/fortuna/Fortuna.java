/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortuna;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jjlui
 */
public class Fortuna {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mysql";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/frases1";

    public void conectar (){
        try {
            Connection conn = null;

        try {
            conn = (Connection) DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            System.out.println("SE HA CONECTADO A LA BASE DE DATOS");
            com.mysql.jdbc.Statement st = (com.mysql.jdbc.Statement) conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT frases FROM frases ORDER BY RAND() LIMIT 1");
            while (rs.next()) {
                String frases = rs.getString("frases");
                System.out.println(frases + "\n");
            }

        } catch (SQLException ex) {
            System.err.println(ex);

        }
            
            String host = "localhost";
            int port = 19999;
            StringBuffer mensaje = new StringBuffer();
            
            InetAddress ia = InetAddress.getByName(host);
            Socket connection = new Socket(ia, port);
            
            BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(bos);
            
            
            osw.flush();
            
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            InputStreamReader isr = new InputStreamReader(bis);
            
            int caracter;
            while((caracter = isr.read()) != 13)
            {
                mensaje.append((char)caracter);                
            }
            System.out.println(mensaje);
            connection.close();
        } 
        catch (UnknownHostException ex) {
            
        }
        catch (IOException ex) {
            
        }
        
    }
    
    public static void main(String[] args) {
        Fortuna f = new Fortuna();
        f.conectar();
    }
         
    }
    /**
     * @param args the command line arguments
     */
    

