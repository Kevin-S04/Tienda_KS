package Modelos;

import Servicios.ConexionBD;
import Vistas.PrincipalView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Productos {
    //Atributos de la clase
    private int id;
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;

    //Atributo externos
    PrincipalView principalView;

    //Constructor
    public Productos(){
    }

    // Constructor
    public Productos(int id, String codigo, String nombre, double precio, int stock){
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    //Metodo para obtener los productos de la base de datos
    public List<Productos> obtenerProducto(){

        List<Productos> productos=new ArrayList<>();

        //Query
        String sql="SELECT * FROM productos";

        //Conexion con la base de datos
        try (Connection conn= ConexionBD.obtenerConexion();
             Statement st= conn.createStatement();
             ResultSet rs=st.executeQuery(sql)){
            while (rs.next()){
                Productos p=new Productos(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                productos.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productos;
    }

    //Metodo para insertar Productos
    public void insertarProductos(String codigo ,String nombre, double precio, int stock){

        //QUERY
        String sql="INSERT INTO productos(codigo,nombre,precio,stock) VALUES (?,?,?,?)";

        //conexion con la base de datos
        try(Connection conn= ConexionBD.obtenerConexion();
            PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setString(1,codigo);
            ps.setString(2,nombre);
            ps.setDouble(3,precio);
            ps.setInt(4,stock);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("producto insertado correctamente.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Verificar Producto
    public boolean verificarProducto(String codigo) {
        String sql = "SELECT * FROM productos WHERE codigo=?";
        //Conexion
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            //verifica el dato
            ps.setString(1, codigo);
            //Obtener el resultado
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //getter y setter
    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }


    @Override
    public String toString() {
        return nombre;
    }
}
