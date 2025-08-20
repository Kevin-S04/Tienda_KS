package Modelos;

import Servicios.ConexionBD;
import Vistas.PrincipalView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Clientes {

    //Atributos de la clase
    private int id;
    private String identificacion;
    private String nombre;

    //Atributo externos
    PrincipalView principalView;

    //Costructor 1
    public  Clientes(){

    }
    //Constructor 2
    public Clientes(int id, String identificacion , String nombre){
        this.id=id;
        this.identificacion=identificacion;
        this.nombre=nombre;
    }

    public List<Clientes> obtenerClientes(){

        //Le asignamos en un array
        List<Clientes> clientes=new ArrayList<>();

        //consulta
        String sql="SELECT * FROM clientes";

        //Conexion con la base de datos
        try(Connection conn= ConexionBD.obtenerConexion();
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery(sql)){

            while(rs.next()){
                Clientes cliente= new Clientes(
                  rs.getInt("id"),
                  rs.getString("identificacion"),
                  rs.getString("nombre")
                );
                clientes.add(cliente);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return clientes;
    }

    //Metodo para insertar clientes
    public void insertarClientes(String identificacion , String nombre){

        //QUERY
        String sql="INSERT INTO clientes(identificacion,nombre) VALUES (?,?)";

        //conexion con la base de datos
        try(Connection conn= ConexionBD.obtenerConexion();
        PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setString(1,identificacion);
            ps.setString(2,nombre);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Cliente insertado correctamente.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Validar Cliente
    public boolean verificarCliente(String identificacion) {
        String sql = "SELECT * FROM clientes WHERE identificacion=?";
        //Conexion
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            //verifica el dato
            ps.setString(1, identificacion);
            //Obtener el resultado
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int obtenerIdCliente(String nombreCliente) {
        int idCliente = -1;
        String sql = "SELECT id FROM clientes WHERE nombre = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idCliente = rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCliente;
    }

    public String obtenerIdentificacion(int idCliente) {
        String sql = "SELECT identificacion FROM clientes WHERE id=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("identificacion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // getter y setter
    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public int getId() {
        return id;
    }
}
