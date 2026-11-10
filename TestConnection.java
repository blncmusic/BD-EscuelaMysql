package seminario;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection != null && connection.isValid(2)) {
                System.out.println("================================");
                System.out.println("CONEXION EXITOSA");
                System.out.println("Base de datos: Escuela");
                System.out.println("MySQL conectado correctamente.");
                System.out.println("================================");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL CONECTAR CON MYSQL:");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}