package Vistas;

import Modelos.Clientes;
import Modelos.DetallesFactura;
import Modelos.Factura;
import Modelos.Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CompraView extends JFrame{
    private JButton btnAgregarProdcuto;
    private JButton btnGenerarFactura;
    private JTable tbPrdocutosS;
    private JComboBox<Productos> cbProductos;
    private JTextField textCantidad;
    private JComboBox<String> cbCliente;
    private JLabel lbCodigoF;
    private JLabel lbfecha;
    private JLabel lbIdentificacion;
    private JLabel lbSubtotal;
    private JLabel lbDescuento;
    private JLabel lbTotal;
    private JPanel PCompra;
    private JButton bntRegresar;

    private List<DetallesFactura> listaDetalles=new ArrayList<>();
    private DefaultTableModel model1;



    public CompraView(){
        //Configuracion de interfaz
        setTitle("Seleccionar Productos");
        setContentPane(PCompra);
        setSize(575,450);
        setLocationRelativeTo(null);
        setVisible(true);

        //Lamar a lo metodos
        cargarProductosCB();
        cargarClientesCB();


        btnAgregarProdcuto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarProductoS();
                mostrarDatosFacturaPrevios();
            }
        });

        btnGenerarFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarFactura();
            }
        });
        bntRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrincipalView();
                dispose();
            }
        });
    }

    //Crear la instancia
    Productos productos = new Productos();
    Clientes clientes=new Clientes();
    Factura factura = new Factura();

    public void cargarProductosCB(){

        List<Productos> lista = productos.obtenerProducto();

        //limpiar combo box
        cbProductos.removeAllItems();

        // Agregar un campo vacío al inicio
        cbProductos.addItem(null);

        //Cargar los producto en el combo;
        for (Productos prod:lista){

            cbProductos.addItem(prod);
        }

    }
    public void cargarClientesCB(){

        List<Clientes> lista = clientes.obtenerClientes();

        //limpiar combo box
        cbCliente.removeAllItems();

        // Agregar un campo vacío al inicio
        cbCliente.addItem("");

        //Cargar los producto en el combo;
        for (Clientes cli:lista){
            cbCliente.addItem(cli.getNombre());
        }
    }
    public void registrarProductoS(){
        // Obtener producto seleccionado
        Productos seleccionado = (Productos) cbProductos.getSelectedItem();
        //Validar cantidad
        int cantidad = 0;

        // 1. Validar cliente seleccionado
        if (cbCliente.getSelectedItem() == null || cbCliente.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente");
            return;
        }

        // 2. Validar producto seleccionado
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
            return;
        }

        // 3. Validar campo cantidad vacío
        if (textCantidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese una cantidad");
            return;
        }

        // 4. Validar número y mayor a 0
        try {
            cantidad = Integer.parseInt(textCantidad.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(null, "Ingrese una cantidad mayor a 0");
                textCantidad.setText("");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser un número válido");
            textCantidad.setText("");
            return;
        }

        // 5. Validar stock
        if (cantidad > seleccionado.getStock()) {
            JOptionPane.showMessageDialog(null,
                    "Cantidad solicitada mayor al stock disponible (" + seleccionado.getStock() + ")");
            textCantidad.setText("");
            return;
        }

        //Crea un detalle temporal
        DetallesFactura detallesFactura=new DetallesFactura(
                seleccionado.getId(),
                cantidad,
                seleccionado.getPrecio()
        );

        listaDetalles.add(detallesFactura);

        //limpia los campos
        textCantidad.setText("");

        // Opcional: mostrar mensaje
        JOptionPane.showMessageDialog(null, "Producto agregado a la factura");

        // Actualizar tabla de productos en la factura
        MostrarProductoSeleccionados();
    }
    public void generarFactura(){

        //regresa a los valores sin asignar
        lbfecha.setText(" 0000/00/00");
        lbCodigoF.setText("");
        lbDescuento.setText("15%");
        lbTotal.setText("00.00");
        lbSubtotal.setText("00.00");

        //verifica que un cliente sea seleccionado
        if(cbCliente.getSelectedItem() == null || cbCliente.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }

        //verifica que se agregue al menos un producto
        if(listaDetalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto");
            return;
        }

        int idCliente = clientes.obtenerIdCliente(cbCliente.getSelectedItem().toString());
        double subtotal = listaDetalles.stream().mapToDouble(DetallesFactura::getTotalLinea).sum();
        double descuento = subtotal * 0.15;
        double total = subtotal - descuento;

        int idFactura = factura.insertarFactura(idCliente, subtotal, descuento, total);
        if(idFactura > 0) {
            for(DetallesFactura det : listaDetalles) {
                det.insertarDetalle(idFactura, det.getIdProducto(), det.getCantidad(),
                        det.getPrecioUnitario(), det.getTotalLinea());
                det.actualizarStock(det.getIdProducto(), det.getCantidad());
            }
            listaDetalles.clear();
            ((DefaultTableModel) tbPrdocutosS.getModel()).setRowCount(0);
            JOptionPane.showMessageDialog(this, "Factura registrada con éxito. ID: " + idFactura);
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la factura");
        }
    }
    private void MostrarProductoSeleccionados(){

        //Configuracion de la tabla
        model1=new DefaultTableModel();
        model1.setColumnIdentifiers(new String[]{"id_producto","Cantidad","Precio_unitario","Total_Lineal"});

        for (DetallesFactura det:listaDetalles){
            Object[] fila={
                    det.getIdProducto(),
                    det.getCantidad(),
                    det.getPrecioUnitario(),
                    det.getTotalLinea()
            };
            model1.addRow(fila);

            //Asignar el modelo a la tabla
            tbPrdocutosS.setModel(model1);
        }
    }
    private void mostrarDatosFacturaPrevios() {
        // Fecha actual
        lbfecha.setText(java.time.LocalDate.now().toString());

        // ID de factura temporal antes de insertar (opcional, puedes mostrar "Pendiente")
        lbCodigoF.setText("Pendiente");

        int idCliente = clientes.obtenerIdCliente(cbCliente.getSelectedItem().toString());
        String identificacion = clientes.obtenerIdentificacion(idCliente);

        // Calcular subtotal, descuento y total
        double subtotal = listaDetalles.stream()
                .mapToDouble(DetallesFactura::getTotalLinea)
                .sum();
        double descuento = subtotal * 0.15;
        double total = subtotal - descuento;

        lbIdentificacion.setText(identificacion);
        lbSubtotal.setText(String.format("%.2f", subtotal));
        lbDescuento.setText(String.format("%.2f", descuento));
        lbTotal.setText(String.format("%.2f", total));
    }
}
