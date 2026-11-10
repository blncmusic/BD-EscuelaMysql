package seminario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    // AGREGAR ALUMNO Y SU FICHA MÉDICA
    public void addAlumno(Alumno alumno) {
        String sql = "INSERT INTO Alumno (DNI, nombre, apellido, fechaNacimiento, curso) "
                   + "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, alumno.getDNI());
                stmt.setString(2, alumno.getNombre());
                stmt.setString(3, alumno.getApellido());
                stmt.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
                stmt.setString(5, alumno.getCurso());

                stmt.executeUpdate();
            }

            if (alumno.getFichaMedica() != null) {
                guardarFichaMedica(conn, alumno.getDNI(), alumno.getFichaMedica());
            }

            conn.commit();
            System.out.println("Alumno agregado correctamente.");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    e.addSuppressed(ex);
                }
            }

            throw new RuntimeException(
                    "Error al agregar el alumno y su ficha médica: " + e.getMessage(), e);

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Error al cerrar la conexión: " + e.getMessage(), e);
                }
            }
        }
    }

    // CONSULTAR ALUMNO POR DNI
    public Alumno getAlumnoByDNI(int dni) {
        String sql = "SELECT DNI, nombre, apellido, fechaNacimiento, curso "
                   + "FROM Alumno WHERE DNI = ?";

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

                    alumno.setFichaMedica(obtenerFichaMedica(conn, dni));

                    return alumno;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar el alumno en la base de datos: " + e.getMessage(), e);
        }

        return null;
    }

    // LISTAR TODOS LOS ALUMNOS
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

                alumno.setFichaMedica(
                        obtenerFichaMedica(conn, alumno.getDNI())
                );

                alumnos.add(alumno);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los alumnos de la base de datos: " + e.getMessage(), e);
        }

        return alumnos;
    }

    // MODIFICAR ALUMNO Y ACTUALIZAR SU FICHA MÉDICA
    public void updateAlumno(Alumno alumno) {
        String sql = "UPDATE Alumno SET nombre = ?, apellido = ?, "
                   + "fechaNacimiento = ?, curso = ? WHERE DNI = ?";

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            int filas;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, alumno.getNombre());
                stmt.setString(2, alumno.getApellido());
                stmt.setDate(3, Date.valueOf(alumno.getFechaNacimiento()));
                stmt.setString(4, alumno.getCurso());
                stmt.setInt(5, alumno.getDNI());

                filas = stmt.executeUpdate();
            }

            if (filas > 0 && alumno.getFichaMedica() != null) {
                guardarFichaMedica(conn, alumno.getDNI(), alumno.getFichaMedica());
            }

            conn.commit();

            if (filas > 0) {
                System.out.println("Alumno modificado correctamente.");
            } else {
                System.out.println("No se encontró el alumno.");
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    e.addSuppressed(ex);
                }
            }

            throw new RuntimeException(
                    "Error al modificar el alumno y su ficha médica: " + e.getMessage(), e);

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Error al cerrar la conexión: " + e.getMessage(), e);
                }
            }
        }
    }

    // ELIMINAR ALUMNO
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
            throw new RuntimeException(
                    "Error al eliminar el alumno de la base de datos: " + e.getMessage(), e);
        }
    }

    // GUARDAR O ACTUALIZAR LA FICHA MÉDICA
    private void guardarFichaMedica(
            Connection conn,
            int dniAlumno,
            FichaMedica ficha) throws SQLException {

        String sql = "INSERT INTO fichamedica "
                   + "(historial, alergias, emergenciaNombre, emergenciaNro, tipoSangre, DNI_Alumno) "
                   + "VALUES (?, ?, ?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE "
                   + "historial = VALUES(historial), "
                   + "alergias = VALUES(alergias), "
                   + "emergenciaNombre = VALUES(emergenciaNombre), "
                   + "emergenciaNro = VALUES(emergenciaNro), "
                   + "tipoSangre = VALUES(tipoSangre)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ficha.getHistorial());
            stmt.setString(2, ficha.getAlergias());
            stmt.setString(3, ficha.getEmergenciaNombre());

            // La columna emergenciaNro es VARCHAR en MySQL.
            stmt.setString(4, String.valueOf(ficha.getEmergenciaNro()));

            stmt.setString(5, ficha.getTipoSangre());
            stmt.setInt(6, dniAlumno);

            stmt.executeUpdate();
        }
    }

    // RECUPERAR LA FICHA MÉDICA DE UN ALUMNO
    private FichaMedica obtenerFichaMedica(
            Connection conn,
            int dniAlumno) throws SQLException {

        String sql = "SELECT historial, alergias, emergenciaNombre, "
                   + "emergenciaNro, tipoSangre "
                   + "FROM fichamedica WHERE DNI_Alumno = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dniAlumno);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    FichaMedica ficha = new FichaMedica();

                    ficha.setHistorial(rs.getString("historial"));
                    ficha.setAlergias(rs.getString("alergias"));
                    ficha.setEmergenciaNombre(rs.getString("emergenciaNombre"));

                    String numero = rs.getString("emergenciaNro");

                    if (numero != null && !numero.trim().isEmpty()) {
                        ficha.setEmergenciaNro(Integer.parseInt(numero));
                    }

                    ficha.setTipoSangre(rs.getString("tipoSangre"));

                    return ficha;
                }
            }
        }

        return null;
    }
}
