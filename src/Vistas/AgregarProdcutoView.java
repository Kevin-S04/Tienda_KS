package Vistas;

import Modelos.Productos;
import com.mysql.cj.log.NullLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarProdcutoView extends  JFrame{

    private JPanel PRegistrarP;
    private JTextField textCodigo;
    private JTextField textNombre;
    private JTextField textPrecio;
    private JTextField textStock;
    private JButton btnRegresar;
    private JButton btnConfirmar;

    //Constructor
    public AgregarProdcutoView(){
        //Configuracion de la interfaz
        setTitle("Registrar Producto");
        setContentPane(PRegistrarP);
        setSize(400,450);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
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

    //Metodo para registrar producto

    public void agregarProducto(){
        String codigo=textCodigo.getText();
        String nombre=textNombre.getText();
        String precio=textPrecio.getText();
        String stock=textStock.getText();

        Productos productos=new Productos();
        if(!productos.verificarProducto(codigo)){
            //validar campos vacios
            if(codigo.trim().isEmpty() || nombre.trim().isEmpty() || precio.trim().isEmpty()|| stock.trim().isEmpty()){
                JOptionPane.showMessageDialog(null,"No puede ingresar campos vacios");
            }else{
                double preciodouble=Double.parseDouble(precio);
                int stockint=Integer.parseInt(stock);

                productos.insertarProductos(codigo,nombre,preciodouble,stockint);
                JOptionPane.showMessageDialog(null,"Producto Agregado correctamente");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Un Producto ya cuenta con ese Código");
        }

        //Limpiar los campos
        textCodigo.setText("");
        textNombre.setText("");
        textPrecio.setText("");
        textStock.setText("");
    }


}
