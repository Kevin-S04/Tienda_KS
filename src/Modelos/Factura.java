package Modelos;

import Servicios.ConexionBD;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class Factura {
    //Atributos
    private int id;
    private int idCliente;
    private LocalDateTime fecha;
    private double subtotal;
    private double descuento;
    private double total;

    private List<DetallesFactura> detalles;

    public Factura() {}

    public Factura(int idCliente, double subtotal, double descuento, double total) {
        this.idCliente = idCliente;
        this.fecha = LocalDateTime.now();
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
    }


    // Insertar factura y devolver el ID generado
    public int insertarFactura(int idCliente, double subtotal, double descuento, double total) {
        String sql = "INSERT INTO facturas (id_cliente, subtotal, descuento, total) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idCliente);
            ps.setDouble(2, subtotal);
            ps.setDouble(3, descuento);
            ps.setDouble(4, total);
            ps.executeUpdate();

            // Obtener el ID generado
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
