package modelo;

import controlador.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * Clase que maneja la interacción con la base de datos para la entidad Galleta.
 *
 */
public class GalletaDAO {

    ConexionBD conexion = new ConexionBD(); // instancia de conexionBD
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Galleta> listar() {
        String sql = "SELECT * FROM galletas";
        List<Galleta> lista = new ArrayList<>();
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Galleta galleta = new Galleta();
                galleta.setIdGalleta(rs.getInt("Id_Galleta"));
                galleta.setNombre(rs.getString("Nombre"));
                galleta.setSabor(rs.getString("Sabor"));
                galleta.setMarca(rs.getString("Marca"));
                galleta.setPrecioPaquete(rs.getDouble("PrecioPaquete"));
                galleta.setCantidadPaquete(rs.getInt("CantidadPaquete"));
                galleta.setCantidadPaquetesStock(rs.getInt("CantidadPaquetesStock"));
                lista.add(galleta);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e);
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    public void agregar(Galleta galleta) {
    String sqlBuscar = "SELECT * FROM Galletas WHERE Nombre = ? AND Sabor = ? AND Marca = ?";
    String sqlInsertar = "INSERT INTO Galletas (Nombre, Sabor, Marca, PrecioPaquete, CantidadPaquete, CantidadPaquetesStock) VALUES (?, ?, ?, ?, ?, ?)";
    try {
        con = conexion.conectarBaseDatos();

        // Verificar si la galleta ya existe en la base de datos
        PreparedStatement psBuscar = con.prepareStatement(sqlBuscar);
        psBuscar.setString(1, galleta.getNombre());
        psBuscar.setString(2, galleta.getSabor());
        psBuscar.setString(3, galleta.getMarca());
        ResultSet rs = psBuscar.executeQuery();

        if (rs.next()) {
            // Si la galleta ya existe, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "La galleta ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Si la galleta no existe, agregarla a la base de datos
            PreparedStatement psInsertar = con.prepareStatement(sqlInsertar);
            psInsertar.setString(1, galleta.getNombre());
            psInsertar.setString(2, galleta.getSabor());
            psInsertar.setString(3, galleta.getMarca());
            psInsertar.setDouble(4, galleta.getPrecioPaquete());
            psInsertar.setInt(5, galleta.getCantidadPaquete());
            psInsertar.setInt(6, galleta.getCantidadPaquetesStock());
            psInsertar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Galleta agregada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al agregar la galleta", "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println("Error en agregar: " + e);
    } finally {
        cerrarRecursos();
    }
}


    public void actualizar(Galleta galleta) {
        String sql = "UPDATE Galletas SET Nombre=?, Sabor=?, Marca=?, PrecioPaquete=?, CantidadPaquete=?, CantidadPaquetesStock=? WHERE Id_Galleta=?";
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.setString(1, galleta.getNombre());
            ps.setString(2, galleta.getSabor());
            ps.setString(3, galleta.getMarca());
            ps.setDouble(4, galleta.getPrecioPaquete());
            ps.setInt(5, galleta.getCantidadPaquete());
            ps.setInt(6, galleta.getCantidadPaquetesStock());
            ps.setInt(7, galleta.getIdGalleta());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e);
        } finally {
            cerrarRecursos();
        }
    }

    public void borrar(int id) {
        String sql = "DELETE FROM Galletas WHERE Id_Galleta=?";
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al borrar: " + e);
        } finally {
            cerrarRecursos();
        }
    }

    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e);
        }
    }
}
