package seminario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    // Agregar un alumno
    public void addAlumno(Alumno alumno) {
        String sql = "INSERT INTO Alumno (DNI, nombre, apellido, fechaNacimiento, curso) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alumno.getDNI());
            stmt.setString(2, alumno.getNombre());
            stmt.setString(3, alumno.getApellido());
            stmt.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            stmt.setString(5, alumno.getCurso());

            stmt.executeUpdate();
            System.out.println("Alumno agregado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al agregar alumno: " + e.getMessage());
        }
    }

    // Consultar un alumno por DNI
    public Alumno getAlumnoByDNI(int dni) {
        String sql = "SELECT DNI, nombre, apellido, fechaNacimiento, curso FROM Alumno WHERE DNI = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dni);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Alumno alumno = new Alumno();

                    alumno.setDNI(rs.getInt("DNI"));
                    alumno.setNombre(rs.getString("nombre"));
                    alumno.setApellido(rs.getString("apellido"));
                    alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                    alumno.setCurso(rs.getString("curso"));

                    return alumno;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar alumno: " + e.getMessage());
        }

        return null;
    }

    // Listar todos los alumnos
    public List<Alumno> getAllAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT DNI, nombre, apellido, fechaNacimiento, curso FROM Alumno";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Alumno alumno = new Alumno();

                alumno.setDNI(rs.getInt("DNI"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setCurso(rs.getString("curso"));

                alumnos.add(alumno);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar alumnos: " + e.getMessage());
        }

        return alumnos;
    }

    // Modificar un alumno
    public void updateAlumno(Alumno alumno) {
        String sql = "UPDATE Alumno SET nombre = ?, apellido = ?, fechaNacimiento = ?, curso = ? WHERE DNI = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getApellido());
            stmt.setDate(3, Date.valueOf(alumno.getFechaNacimiento()));
            stmt.setString(4, alumno.getCurso());
            stmt.setInt(5, alumno.getDNI());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Alumno modificado correctamente.");
            } else {
                System.out.println("No se encontró el alumno.");
            }

        } catch (SQLException e) {
            System.out.println("Error al modificar alumno: " + e.getMessage());
        }
    }

    // Eliminar un alumno
    public void deleteAlumno(int dni) {
        String sql = "DELETE FROM Alumno WHERE DNI = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dni);

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Alumno eliminado correctamente.");
            } else {
                System.out.println("No se encontró el alumno.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar alumno: " + e.getMessage());
        }
    }
}
