package seminario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChoferDAO {

    // Registrar un chofer
    public void addChofer(Chofer chofer) {
        String sql = "INSERT INTO chofer (DNI, nombre, apellido) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chofer.getDNI());
            stmt.setString(2, chofer.getNombre());
            stmt.setString(3, chofer.getApellido());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar el chofer: " + e.getMessage(), e);
        }
    }

    // Consultar un chofer por DNI
    public Chofer getChoferByDNI(int dni) {
        String sql = "SELECT DNI, nombre, apellido FROM chofer WHERE DNI = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dni);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Chofer(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getInt("DNI")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar el chofer: " + e.getMessage(), e);
        }

        return null;
    }

    // Listar todos los choferes
    public List<Chofer> getAllChoferes() {
        List<Chofer> choferes = new ArrayList<>();
        String sql = "SELECT DNI, nombre, apellido FROM chofer ORDER BY apellido, nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Chofer chofer = new Chofer(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("DNI")
                );

                choferes.add(chofer);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los choferes: " + e.getMessage(), e);
        }

        return choferes;
    }

    // Modificar los datos de un chofer
    public void updateChofer(int dniOriginal, Chofer chofer) {
        String sql = "UPDATE chofer SET DNI = ?, nombre = ?, apellido = ? WHERE DNI = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chofer.getDNI());
            stmt.setString(2, chofer.getNombre());
            stmt.setString(3, chofer.getApellido());
            stmt.setInt(4, dniOriginal);

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró el chofer que se desea modificar.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar el chofer: " + e.getMessage(), e);
        }
    }

    // Eliminar un chofer
    public void deleteChofer(int dni) {
        String sql = "DELETE FROM chofer WHERE DNI = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dni);

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró el chofer que se desea eliminar.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el chofer: " + e.getMessage(), e);
        }
    }
}
