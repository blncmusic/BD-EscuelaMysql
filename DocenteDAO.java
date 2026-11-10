package seminario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {

    // Registrar un docente
    public void addDocente(Docente docente) {
        String sql = "INSERT INTO docente (DNI, nombre, apellido) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, docente.getDNI());
            stmt.setString(2, docente.getNombre());
            stmt.setString(3, docente.getApellido());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar el docente: " + e.getMessage(), e);
        }
    }

    // Consultar un docente por DNI
    public Docente getDocenteByDNI(int dni) {
        String sql = "SELECT DNI, nombre, apellido FROM docente WHERE DNI = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dni);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Docente(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getInt("DNI")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar el docente: " + e.getMessage(), e);
        }

        return null;
    }

    // Listar todos los docentes
    public List<Docente> getAllDocentes() {
        List<Docente> docentes = new ArrayList<>();

        String sql = "SELECT DNI, nombre, apellido FROM docente "
                + "ORDER BY apellido, nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Docente docente = new Docente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("DNI")
                );

                docentes.add(docente);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los docentes: " + e.getMessage(), e);
        }

        return docentes;
    }

    // Modificar los datos de un docente
    public void updateDocente(int dniOriginal, Docente docente) {
        String sql = "UPDATE docente SET DNI = ?, nombre = ?, apellido = ? "
                + "WHERE DNI = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, docente.getDNI());
            stmt.setString(2, docente.getNombre());
            stmt.setString(3, docente.getApellido());
            stmt.setInt(4, dniOriginal);

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException(
                        "No se encontró el docente que se desea modificar.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al modificar el docente: " + e.getMessage(), e);
        }
    }

    // Eliminar un docente
    public void deleteDocente(int dni) {
        String sql = "DELETE FROM docente WHERE DNI = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dni);

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException(
                        "No se encontró el docente que se desea eliminar.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el docente: " + e.getMessage(), e);
        }
    }
}
