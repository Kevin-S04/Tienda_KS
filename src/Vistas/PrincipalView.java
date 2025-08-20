package Vistas;

import Modelos.Clientes;
import Modelos.Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PrincipalView extends JFrame {
    private JTable tbClientes;
    private JButton btnBuscarC;
    private JButton BtnAgregarC;
    private JButton btnBuscarP;
    private JButton BtnAgregarP;
    private JTextField textBuscarP;
    private JTextField textBuscarC;
    private JButton BtnFactura;
    private JLabel lbNombre;
    private JLabel lbProducto;
    private JLabel lbPrecio;
    private JLabel lbStock;
    private JTable tbProductos;
    private JPanel PrincipaP;

    // COFIGURAR LA Tabla
    DefaultTableModel ModelProductos;
    DefaultTableModel ModelClientes;


    //Constructor
    public PrincipalView(){
        //Configuracion de interfaz
        setTitle("Caja");
        setContentPane(PrincipaP);
        setSize(1200,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Productos en la tabla
        cargarProductos();
        //Cliente en la Tabla
        cargarClientes();

        BtnAgregarC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarClienteView();
                dispose();
            }
        });

        BtnAgregarP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarProdcutoView();
                dispose();
            }
        });
        btnBuscarC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCliente();
            }
        });
        btnBuscarP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
        BtnFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CompraView();
                dispose();
            }
        });
    }

    //Instanciamos lo metodos
    Clientes clientes = new Clientes();
    Productos productos=new Productos();


    //Metodo para poder cargar los porductos en la tabla
    public void cargarProductos(){


        List<Productos> lista=productos.obtenerProducto();

        //Configuracion de la tabla
        ModelProductos=new DefaultTableModel();
        ModelProductos.setColumnIdentifiers(new String[]{"id","Codigo","Nombre","Precio","Stock"});

        //Cargar los datos
        // Llenar modelo con datos
        for (Productos prod : lista){
            Object[] fila = {
                    prod.getId(),
                    prod.getCodigo(),
                    prod.getNombre(),
                    prod.getPrecio(),
                    prod.getStock()
            };
            ModelProductos.addRow(fila);
        }

        // Asignar modelo a la tabla
        tbProductos.setModel(ModelProductos);
    }

    public void cargarClientes(){


        List<Clientes> listaC=clientes.obtenerClientes();

        //Configuracion de la tabla
        ModelClientes=new DefaultTableModel();
        ModelClientes.setColumnIdentifiers(new String[]{"id","identificacion","Nombre"});

        //Cargar los datos
        // Llenar modelo con datos
        for (Clientes cli : listaC){
            Object[] fila = {
                cli.getId(),
                cli.getIdentificacion(),
                cli.getNombre()
            };
            ModelClientes.addRow(fila);
        }

        // Asignar modelo a la tabla
        tbClientes.setModel(ModelClientes);
    }

    public void buscarCliente(){
        String indetificacionB=textBuscarC.getText().trim();

        if(!indetificacionB.isEmpty()){
            if(clientes.verificarCliente(indetificacionB)){

                for (Clientes cli: clientes.obtenerClientes()){
                    if(cli.getIdentificacion().equals(indetificacionB)){
                        lbNombre.setText(cli.getNombre());
                    }
                }
            }else{
                lbNombre.setText("Cliente no esta registrado");
                JOptionPane.showMessageDialog(null,"Cliente no Registrado");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Ingrese una identificación para Buscar");
        }
        // limpia el campo para buscar
        textBuscarC.setText("");
    }

    public void buscarProducto(){
        String codigoB=textBuscarP.getText().trim();

        if(!codigoB.isEmpty()){
            if(productos.verificarProducto(codigoB)){

                for (Productos pro: productos.obtenerProducto()){
                    if(pro.getCodigo().equals(codigoB)){
                        String precioB=String.valueOf(pro.getPrecio());
                        String stockB=String.valueOf(pro.getStock());

                        //Mostrar en el label
                        lbProducto.setText(pro.getNombre());
                        lbPrecio.setText(precioB);
                        lbStock.setText(stockB);
                    }
                }
            }else{
                lbProducto.setText("No Registrado");
                lbPrecio.setText("No Registrado");
                lbStock.setText("No Registrado");

                JOptionPane.showMessageDialog(null,"Producto no Registrado");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Ingrese un Código para Buscar");
        }
        // Limpia el campo para buscar
        textBuscarP.setText("");
    }
}
