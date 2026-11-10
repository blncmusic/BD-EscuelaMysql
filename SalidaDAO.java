
package seminario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SalidaDAO {

    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private final ChoferDAO choferDAO = new ChoferDAO();
    private final DocenteDAO docenteDAO = new DocenteDAO();

    // Registrar una salida y sus alumnos
    public boolean addSalida(SalidaExtracurricular salida) {

        String sql = "INSERT INTO salidaextracurricular "
                + "(destino, fecha, DNI_Docente, DNI_Chofer) "
                + "VALUES (?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, salida.getDestino());
                stmt.setDate(2, new Date(salida.getFecha().getTime()));

                if (salida.getDocenteSalida() != null) {
                    stmt.setInt(3, salida.getDocenteSalida().getDNI());
                } else {
                    stmt.setNull(3, java.sql.Types.INTEGER);
                }

                if (salida.getChofer() != null) {
                    stmt.setInt(4, salida.getChofer().getDNI());
                } else {
                    stmt.setNull(4, java.sql.Types.INTEGER);
                }

                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        salida.setId(rs.getInt(1));
                    }
                }
            }

            guardarAlumnos(conn, salida);

            conn.commit();
            System.out.println("Salida extracurricular registrada correctamente.");
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error al deshacer la operación: " + ex.getMessage());
                }
            }

            System.out.println("Error al registrar salida: " + e.getMessage());
            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }

    // Guardar los alumnos asociados a una salida
    private void guardarAlumnos(Connection conn, SalidaExtracurricular salida)
            throws SQLException {

        String sql = "INSERT INTO alumno_salida (id_Salida, DNI_Alumno) "
                + "VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Alumno alumno : salida.getAlumnos()) {
                stmt.setInt(1, salida.getId());
                stmt.setInt(2, alumno.getDNI());
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    // Consultar una salida por ID
    public SalidaExtracurricular getSalidaById(int id) {

        String sql = "SELECT * FROM salidaextracurricular WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String destino = rs.getString("destino");
                    java.util.Date fecha = rs.getDate("fecha");

                    int dniChofer = rs.getInt("DNI_Chofer");
                    boolean choferNulo = rs.wasNull();

                    int dniDocente = rs.getInt("DNI_Docente");
                    boolean docenteNulo = rs.wasNull();

                    Chofer chofer = choferNulo
                            ? null : choferDAO.getChoferByDNI(dniChofer);

                    Docente docente = docenteNulo
                            ? null
                            : docenteDAO.getDocenteByDNI(dniDocente);

                    ArrayList<Alumno> alumnos = obtenerAlumnos(conn, id);

                    return new SalidaExtracurricular(
                            id, destino, fecha, chofer, docente, alumnos);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar salida: " + e.getMessage());
        }

        return null;
    }

    // Recuperar los alumnos asociados a una salida
    private ArrayList<Alumno> obtenerAlumnos(Connection conn, int id)
            throws SQLException {

        ArrayList<Alumno> alumnos = new ArrayList<>();

        String sql = "SELECT DNI_Alumno FROM alumno_salida WHERE id_Salida = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int dni = rs.getInt("DNI_Alumno");

                    Alumno alumno = alumnoDAO.getAlumnoByDNI(dni);

                    if (alumno != null) {
                        alumnos.add(alumno);
                    }
                }
            }
        }

        return alumnos;
    }

    // Listar todas las salidas
    public List<SalidaExtracurricular> getAllSalidas() {

        List<SalidaExtracurricular> salidas = new ArrayList<>();

        String sql = "SELECT id FROM salidaextracurricular ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SalidaExtracurricular salida =
                        getSalidaById(rs.getInt("id"));

                if (salida != null) {
                    salidas.add(salida);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar salidas: " + e.getMessage());
        }

        return salidas;
    }

    // Modificar una salida y sus alumnos
    public boolean updateSalida(SalidaExtracurricular salida) {

        String sql = "UPDATE salidaextracurricular "
                + "SET destino = ?, fecha = ?, DNI_Docente = ?, DNI_Chofer = ? "
                + "WHERE id = ?";

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, salida.getDestino());
                stmt.setDate(2, new Date(salida.getFecha().getTime()));

                if (salida.getDocenteSalida() != null) {
                    stmt.setInt(3, salida.getDocenteSalida().getDNI());
                } else {
                    stmt.setNull(3, java.sql.Types.INTEGER);
                }

                if (salida.getChofer() != null) {
                    stmt.setInt(4, salida.getChofer().getDNI());
                } else {
                    stmt.setNull(4, java.sql.Types.INTEGER);
                }

                stmt.setInt(5, salida.getId());

                int filas = stmt.executeUpdate();

                if (filas == 0) {
                    conn.rollback();
                    System.out.println("No se encontró la salida.");
                    return false;
                }
            }

            // Reemplazar las relaciones anteriores
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM alumno_salida WHERE id_Salida = ?")) {

                stmt.setInt(1, salida.getId());
                stmt.executeUpdate();
            }

            guardarAlumnos(conn, salida);

            conn.commit();
            System.out.println("Salida modificada correctamente.");
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error al deshacer la operación: " + ex.getMessage());
                }
            }

            System.out.println("Error al modificar salida: " + e.getMessage());
            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }

    // Eliminar una salida y sus alumnos asociados
    public boolean deleteSalida(int id) {

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM alumno_salida WHERE id_Salida = ?")) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM salidaextracurricular WHERE id = ?")) {

                stmt.setInt(1, id);

                int filas = stmt.executeUpdate();

                if (filas == 0) {
                    conn.rollback();
                    System.out.println("No se encontró la salida.");
                    return false;
                }
            }

            conn.commit();
            System.out.println("Salida eliminada correctamente.");
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error al deshacer la operación: " + ex.getMessage());
                }
            }

            System.out.println("Error al eliminar salida: " + e.getMessage());
            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }
}