package Vistas;

import Modelos.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends  JFrame {
    private JTextField textUsuario;
    private JPasswordField textContrasenia;
    private JButton btnIngresar;
    private JPanel InicioPrincipal;

    public LoginView(){
        //Intanciamos el metoodo
        Login login= new Login(this);

        //Confirmación de interfaz
        setTitle("Iniciar sesión");
        setContentPane(InicioPrincipal);
        setSize(400,400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Fucncionalidades de los botones
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(login.validarUsuario()){
                    new PrincipalView();
                    dispose();
                }
                //Limpiar los campos
                textUsuario.setText("");
                textContrasenia.setText("");
            }
        });
    }

    //Getter and Setter
    public String getTextUsuario() {
        return textUsuario.getText();
    }

    public char[] getTextContrasenia() {
        return textContrasenia.getPassword();
    }

}

