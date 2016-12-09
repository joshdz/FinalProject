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
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jjlui
 */
public class FortunaServer {

    public static ServerSocket server;
    public static Socket connection;
    protected final static int port = 19999;
    public static StringBuffer mensaje;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mysql";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/frases1";

    Connection conn = null;

    public void server() {
        int caracter;
        System.out.println("esperando cliente");

        try {
            server = new ServerSocket(port);
            while (true) {
                try {
                    connection = server.accept();
                    System.out.println("Acepto una conexion");
                    BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader isr = new InputStreamReader(bis);

                    mensaje = new StringBuffer();

                    while ((caracter = isr.read()) != 13) {
                        mensaje.append((char) caracter);
                    }

                    conn = (Connection) DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                    System.out.println("SE HA CONECTADO A LA BASE DE DATOS");
                    com.mysql.jdbc.Statement st = (com.mysql.jdbc.Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT frases FROM frases where id like " + mensaje);
                    while (rs.next()) {
                        String frases = rs.getString("frases");
                        System.out.println(frases + "\n");

                        System.out.println("mensaje recibido: " + mensaje);
                        String mensajeRetorno = frases + (char) 13;

                        BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
                        OutputStreamWriter osw = new OutputStreamWriter(bos);
                        osw.write(mensajeRetorno);
                        osw.flush();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FortunaServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FortunaServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FortunaServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        FortunaServer fs = new FortunaServer();
        fs.server();
    }

}
