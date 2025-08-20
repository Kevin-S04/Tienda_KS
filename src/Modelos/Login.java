package Modelos;

import Servicios.ConexionBD;
import Vistas.LoginView;


import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    //Vairables externas
    LoginView loginView;

    //Constructor
    public Login(LoginView loginView){
        this.loginView=loginView;
    }

    //Metodos para validar el ususario con la base de datos
    public boolean validarUsuario(){
        //Atributos para la vista
        String usuario=loginView.getTextUsuario();
        String password= new String(loginView.getTextContrasenia());

        //Vañidacion de campos vacios
        if(usuario.trim().isEmpty() || password.trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"Los Campos no pueden estar vacios");
            return false;
        }

        //realizamos la consulta
        String sql="SELECT id FROM usuarios WHERE usuario= ? AND pass= ? ";

        //Realiza al conexion
        try(Connection conn= ConexionBD.obtenerConexion();
            PreparedStatement ps= conn.prepareStatement(sql)){
                ps.setString(1,usuario);
                ps.setString(2,password);

            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return true;
            }else{
                JOptionPane.showMessageDialog(null,"Usuario y Contraseña no Existen");
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Conexion de la base de datos fallida");
            return false;
        }

    }
}
