package Vistas;

import Modelos.Clientes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarClienteView  extends  JFrame{
    private JTextField textidentificacion;
    private JTextField textNombre;
    private JButton btnRegresar;
    private JButton btnConfirmar;
    private JPanel PRegistroC;

    public AgregarClienteView(){
        //Configurar la interfaz
        setTitle("Agregar Cliente");
        setContentPane(PRegistroC);
        setSize(400,450);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCliente();
            }
        });
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrincipalView();
                dispose();
            }
        });
    }

    //Metodo para agregar producto
    public void agregarCliente(){
        String identificacion= textidentificacion.getText();
        String nombre=textNombre.getText();

        //Asignar a la base de datos
        Clientes clientes=new Clientes();

        if(!clientes.verificarCliente(identificacion)){
            //Validación de campos vacios
            if(identificacion.trim().isEmpty() || nombre.trim().isEmpty()){
                JOptionPane.showMessageDialog(null,"No puede ingresar campos vacios");
            }else{

                clientes.insertarClientes(identificacion,nombre);
                JOptionPane.showMessageDialog(null,"Cliente Agregado Correctamente");
            }
        }else{
            JOptionPane.showMessageDialog(null,"El Cliente ya se Encuentra Registarado");
        }
        //Limpiar los campos
        textidentificacion.setText("");
        textNombre.setText("");
    }
}
