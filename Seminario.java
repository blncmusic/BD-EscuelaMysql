package seminario;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Seminario {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        AlumnoController alumnoController = new AlumnoController(alumnoDAO);
        AlumnoView alumnoView = new AlumnoView();

        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Administrar Alumnos");
            System.out.println("2. Salir");
            System.out.print("Ingrese la opción deseada: ");
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 1 -> administrarAlumnos(scanner, alumnoController, alumnoView);
                    case 2 -> {
                        System.out.println("¡Hasta luego!");
                        return;
                    }
                    default -> System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }

    private static void administrarAlumnos(Scanner scanner, AlumnoController alumnoController, AlumnoView alumnoView) {
        while (true) {
            System.out.println("\n--- Administrar Alumnos ---");
            System.out.println("1. Agregar Alumno");
            System.out.println("2. Listar Todos los Alumnos");
            System.out.println("3. Volver al Menú Principal");
            System.out.print("Ingrese la opción deseada: ");
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 1 -> agregarAlumno(scanner, alumnoController);
                    case 2 -> listarTodosLosAlumnos(alumnoController, alumnoView);
                    case 3 -> {
                        return;
                    }
                    default -> System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }

    private static void agregarAlumno(Scanner scanner, AlumnoController alumnoController) {
        try {
            System.out.print("Nombre del alumno: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido del alumno: ");
            String apellido = scanner.nextLine();
            System.out.print("DNI del alumno: ");
            int dni = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
            String fechaNacimientoStr = scanner.nextLine();
            LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
            System.out.print("Curso: ");
            String curso = scanner.nextLine();

            Alumno nuevoAlumno = new Alumno(dni, nombre, apellido, fechaNacimiento, curso);
            alumnoController.addAlumno(nuevoAlumno);
            System.out.println("Alumno agregado correctamente.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha incorrecto. Por favor, use AAAA-MM-DD.");
        } catch (InputMismatchException e) {
            System.out.println("Error en la entrada de datos. Por favor, intente de nuevo.");
            scanner.nextLine(); // Limpiar buffer
        }
    }

    private static void listarTodosLosAlumnos(AlumnoController alumnoController, AlumnoView alumnoView) {
        List<Alumno> alumnos = alumnoController.getAllAlumnos();
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
        } else {
            alumnos.forEach(alumnoView::printAlumnoDetails);
        }
    }
}
