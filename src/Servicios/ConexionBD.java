package Servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    //atributos para la conexion
    private static String url="jdbc:mysql://localhost:3306/ventas_sistema";
    private static String user="root";
    private static String password="12345";

    //Contructor para poder ser llamado

    //Metodo para establecer la conexion con la base de datos

    public static Connection obtenerConexion() throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }

}

