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
 * Clase que maneja la interacción con la base de datos para la entidad Fideo.
 *
 */
public class FideoDAO {

    ConexionBD conexion = new ConexionBD(); // instancia de ConexionBD
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Fideo> listar() {
        String sql = "SELECT * FROM fideos";
        List<Fideo> lista = new ArrayList<>();
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Fideo fideo = new Fideo();
                fideo.setIdFideo(rs.getInt("Id_Fideo"));
                fideo.setTipo(rs.getString("Tipo"));
                fideo.setMarca(rs.getString("Marca"));
                fideo.setPrecioPaquete(rs.getDouble("PrecioPaquete"));
                fideo.setPesoPaquete(rs.getDouble("PesoPaquete"));
                fideo.setCantidadPaquetesStock(rs.getInt("CantidadPaquetesStock"));
                lista.add(fideo);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e);
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

   public void agregar(Fideo fideo) {
    String sqlBuscar = "SELECT * FROM Fideos WHERE Tipo = ? AND Marca = ?";
    String sqlInsertar = "INSERT INTO Fideos (Tipo, Marca, PrecioPaquete, PesoPaquete, CantidadPaquetesStock) VALUES (?, ?, ?, ?, ?)";
    try {
        con = conexion.conectarBaseDatos();

        // Verificar si el fideo ya existe en la base de datos
        PreparedStatement psBuscar = con.prepareStatement(sqlBuscar);
        psBuscar.setString(1, fideo.getTipo());
        psBuscar.setString(2, fideo.getMarca());
        ResultSet rs = psBuscar.executeQuery();

        if (rs.next()) {
            // Si el fideo ya existe, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "El fideo ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Si el fideo no existe, agregarlo a la base de datos
            PreparedStatement psInsertar = con.prepareStatement(sqlInsertar);
            psInsertar.setString(1, fideo.getTipo());
            psInsertar.setString(2, fideo.getMarca());
            psInsertar.setDouble(3, fideo.getPrecioPaquete());
            psInsertar.setDouble(4, fideo.getPesoPaquete());
            psInsertar.setInt(5, fideo.getCantidadPaquetesStock());
            psInsertar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Fideo agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al agregar el fideo", "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println("Error en agregar: " + e);
    } finally {
        cerrarRecursos();
    }
}


    public void actualizar(Fideo fideo) {
        String sql = "UPDATE Fideos SET Tipo=?, Marca=?, PrecioPaquete=?, PesoPaquete=?, CantidadPaquetesStock=? WHERE Id_Fideo=?";
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.setString(1, fideo.getTipo());
            ps.setString(2, fideo.getMarca());
            ps.setDouble(3, fideo.getPrecioPaquete());
            ps.setDouble(4, fideo.getPesoPaquete());
            ps.setInt(5, fideo.getCantidadPaquetesStock());
            ps.setInt(6, fideo.getIdFideo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e);
        } finally {
            cerrarRecursos();
        }
    }

    public void borrar(int id) {
        String sql = "DELETE FROM Fideos WHERE Id_Fideo=?";
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
