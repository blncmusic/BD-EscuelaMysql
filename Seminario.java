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

        SalidaDAO salidaDAO = new SalidaDAO();
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        AlumnoController alumnoController = new AlumnoController(alumnoDAO);
        AlumnoView alumnoView = new AlumnoView();

        ChoferDAO choferDAO = new ChoferDAO();

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Administrar Alumnos");
            System.out.println("2. Gestionar Salida Extracurricular");
            System.out.println("3. Administrar Choferes");
            System.out.println("4. Salir");
            System.out.print("Ingrese la opción deseada: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        administrarAlumnos(scanner, alumnoController, alumnoView);
                        break;

                    case 2:
                        gestionarSalidaExtracurricular(
                                scanner, salidaDAO, choferDAO,
                                alumnoController, alumnoView
                        );
                        break;

                    case 3:
                        administrarChoferes(scanner, choferDAO);
                        break;

                    case 4:
                        System.out.println("¡Hasta luego!");
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");

            } catch (RuntimeException e) {
                System.out.println("Ocurrió un error al realizar la operación.");
                System.out.println("Detalle: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // ADMINISTRACIÓN DE ALUMNOS

    private static void administrarAlumnos(
            Scanner scanner,
            AlumnoController alumnoController,
            AlumnoView alumnoView) {

        System.out.println("\n--- Administrar Alumnos ---");
        System.out.println("1. Agregar Alumno");
        System.out.println("2. Modificar Datos Personales");
        System.out.println("3. Consultar Datos de Alumno");
        System.out.println("4. Eliminar Alumno");
        System.out.println("5. Listar Todos los Alumnos");
        System.out.print("Ingrese la opción deseada: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    agregarAlumno(scanner, alumnoController);
                    break;

                case 2:
                    modificarDatosPersonales(scanner, alumnoController);
                    break;

                case 3:
                    consultarDatosAlumno(scanner, alumnoController, alumnoView);
                    break;

                case 4:
                    eliminarAlumno(scanner, alumnoController);
                    break;

                case 5:
                    listarTodosLosAlumnos(alumnoController, alumnoView);
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese una opción numérica.");
        }
    }

    private static void agregarAlumno(
            Scanner scanner,
            AlumnoController alumnoController) {

        try {
            System.out.println("\n--- Registrar Alumno ---");

            System.out.print("Nombre del alumno: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido del alumno: ");
            String apellido = scanner.nextLine();

            System.out.print("DNI del alumno: ");
            int dni = Integer.parseInt(scanner.nextLine());

            System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
            LocalDate fechaNacimiento =
                    LocalDate.parse(scanner.nextLine());

            System.out.print("Curso: ");
            String curso = scanner.nextLine();

            // Datos de la ficha médica
            FichaMedica fichaMedica = new FichaMedica();

            System.out.println("\n--- Ficha Médica ---");

            System.out.print("Historial médico: ");
            fichaMedica.setHistorial(scanner.nextLine());

            System.out.print("Alergias: ");
            fichaMedica.setAlergias(scanner.nextLine());

            System.out.print("Nombre de contacto de emergencia: ");
            fichaMedica.setEmergenciaNombre(scanner.nextLine());

            System.out.print("Número de contacto de emergencia: ");
            int emergenciaNro = Integer.parseInt(scanner.nextLine());
            fichaMedica.setEmergenciaNro(emergenciaNro);

            System.out.print("Tipo de sangre: ");
            fichaMedica.setTipoSangre(scanner.nextLine());

            Alumno nuevoAlumno = new Alumno(
                    dni,
                    nombre,
                    apellido,
                    fechaNacimiento,
                    curso
            );

            nuevoAlumno.setFichaMedica(fichaMedica);

            alumnoController.addAlumno(nuevoAlumno);

            System.out.println("\nAlumno agregado correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("El DNI debe contener únicamente números.");

        } catch (DateTimeParseException e) {
            System.out.println(
                    "Fecha incorrecta. Utilice el formato AAAA-MM-DD."
            );

        } catch (RuntimeException e) {
            System.out.println("No se pudo registrar el alumno.");
            System.out.println("Detalle: " + e.getMessage());
        }
    }

    private static void modificarDatosPersonales(
            Scanner scanner,
            AlumnoController alumnoController) {

        try {
            System.out.print("\nIngrese el DNI del alumno a modificar: ");
            int dni = Integer.parseInt(scanner.nextLine());

            Alumno alumno = alumnoController.getAlumnoByDNI(dni);

            if (alumno != null) {

                System.out.print("Nuevo nombre: ");
                String nuevoNombre = scanner.nextLine();

                System.out.print("Nuevo apellido: ");
                String nuevoApellido = scanner.nextLine();

                System.out.print("Nuevo DNI: ");
                int nuevoDNI = Integer.parseInt(scanner.nextLine());

                System.out.print("Nueva fecha de nacimiento (AAAA-MM-DD): ");
                LocalDate nuevaFechaNacimiento =
                        LocalDate.parse(scanner.nextLine());

                System.out.print("Nuevo curso: ");
                String nuevoCurso = scanner.nextLine();

                alumno.setNombre(nuevoNombre);
                alumno.setApellido(nuevoApellido);
                alumno.setDNI(nuevoDNI);
                alumno.setFechaNacimiento(nuevaFechaNacimiento);
                alumno.setCurso(nuevoCurso);

                alumnoController.updateAlumno(alumno);

                System.out.println(
                        "Datos del alumno actualizados correctamente."
                );

            } else {
                System.out.println("Alumno no encontrado.");
            }

        } catch (NumberFormatException e) {
            System.out.println("El DNI debe contener únicamente números.");

        } catch (DateTimeParseException e) {
            System.out.println(
                    "Fecha incorrecta. Utilice el formato AAAA-MM-DD."
            );

        } catch (RuntimeException e) {
            System.out.println("No se pudo modificar el alumno.");
            System.out.println("Detalle: " + e.getMessage());
        }
    }



private static void consultarDatosAlumno(
        Scanner scanner,
        AlumnoController alumnoController,
        AlumnoView alumnoView) {

    try {
        System.out.print("\nIngrese el DNI del alumno a consultar: ");
        int dni = Integer.parseInt(scanner.nextLine());

        Alumno alumno = alumnoController.getAlumnoByDNI(dni);

        if (alumno != null) {
            alumnoView.printAlumnoDetails(alumno);
        } else {
            System.out.println("Alumno no encontrado.");
        }

    } catch (NumberFormatException e) {
        System.out.println("El DNI debe contener únicamente números.");

    } catch (RuntimeException e) {
        System.out.println("No se pudo consultar el alumno.");
        System.out.println("Detalle: " + e.getMessage());
    }
}
    private static void eliminarAlumno(
            Scanner scanner,
            AlumnoController alumnoController) {

        try {
            System.out.print("\nIngrese el DNI del alumno a eliminar: ");
            int dni = Integer.parseInt(scanner.nextLine());

            Alumno alumno = alumnoController.getAlumnoByDNI(dni);

            if (alumno != null) {
                alumnoController.deleteAlumno(dni);
                System.out.println("Alumno eliminado correctamente.");
            } else {
                System.out.println("No existe un alumno con ese DNI.");
            }

        } catch (NumberFormatException e) {
            System.out.println("El DNI debe contener únicamente números.");

        } catch (RuntimeException e) {
            System.out.println("No se pudo eliminar el alumno.");
            System.out.println("Detalle: " + e.getMessage());
        }
    }

    private static void listarTodosLosAlumnos(
            AlumnoController alumnoController,
            AlumnoView alumnoView) {

        try {
            List<Alumno> alumnos = alumnoController.getAllAlumnos();

            if (alumnos.isEmpty()) {
                System.out.println("No hay alumnos registrados.");
            } else {
                System.out.println("\n--- Alumnos Registrados ---");

                for (Alumno alumno : alumnos) {
                    alumnoView.printAlumnoDetails(alumno);
                }
            }

        } catch (RuntimeException e) {
            System.out.println("No se pudo obtener la lista de alumnos.");
            System.out.println("Detalle: " + e.getMessage());
        }
    }


    
    // ADMINISTRACIÓN DE CHOFERES

    private static void administrarChoferes(
            Scanner scanner,
            ChoferDAO choferDAO) {

        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- Administrar Choferes ---");
            System.out.println("1. Registrar Chofer");
            System.out.println("2. Consultar Chofer");
            System.out.println("3. Modificar Datos del Chofer");
            System.out.println("4. Listar Todos los Choferes");
            System.out.println("5. Eliminar Chofer");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Ingrese la opción deseada: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarChofer(scanner, choferDAO);
                        break;
                    case 2:
                        consultarChofer(scanner, choferDAO);
                        break;
                    case 3:
                        modificarChofer(scanner, choferDAO);
                        break;
                    case 4:
                        listarChoferes(choferDAO);
                        break;
                    case 5:
                        eliminarChofer(scanner, choferDAO);
                        break;
                    case 6:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese una opción numérica.");
            } catch (RuntimeException e) {
                System.out.println("Error al administrar choferes: " + e.getMessage());
            }
        }
    }

    // REGISTRAR CHOFER

    private static void registrarChofer(
            Scanner scanner,
            ChoferDAO choferDAO) {

        try {
            System.out.println("\n--- Registrar Chofer ---");

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("DNI: ");
            int dni = Integer.parseInt(scanner.nextLine());

            if (choferDAO.getChoferByDNI(dni) != null) {
                System.out.println("Ya existe un chofer con ese DNI.");
                return;
            }

            Chofer nuevoChofer = new Chofer(nombre, apellido, dni);
            choferDAO.addChofer(nuevoChofer);

            System.out.println("Chofer registrado correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("El DNI debe contener únicamente números.");
        }
    }

    // CONSULTAR CHOFER

    private static void consultarChofer(
            Scanner scanner,
            ChoferDAO choferDAO) {

        try {
            System.out.print("\nIngrese el DNI del chofer: ");
            int dni = Integer.parseInt(scanner.nextLine());

            Chofer chofer = choferDAO.getChoferByDNI(dni);

            if (chofer != null) {
                System.out.println("\n--- Datos del Chofer ---");
                System.out.println("DNI: " + chofer.getDNI());
                System.out.println("Nombre: " + chofer.getNombre());
                System.out.println("Apellido: " + chofer.getApellido());
            } else {
                System.out.println("Chofer no encontrado.");
            }

        } catch (NumberFormatException e) {
            System.out.println("El DNI debe contener únicamente números.");
        }
    }

    // MODIFICAR CHOFER

    private static void modificarChofer(
            Scanner scanner,
            ChoferDAO choferDAO) {

        try {
            System.out.print("\nIngrese el DNI del chofer a modificar: ");
            int dniOriginal = Integer.parseInt(scanner.nextLine());

            Chofer chofer = choferDAO.getChoferByDNI(dniOriginal);

            if (chofer == null) {
                System.out.println("Chofer no encontrado.");
                return;
            }

            System.out.print("Nuevo nombre: ");
            String nuevoNombre = scanner.nextLine();

            System.out.print("Nuevo apellido: ");
            String nuevoApellido = scanner.nextLine();

            System.out.print("Nuevo DNI: ");
            int nuevoDNI = Integer.parseInt(scanner.nextLine());

            Chofer existente = choferDAO.getChoferByDNI(nuevoDNI);

            if (existente != null && nuevoDNI != dniOriginal) {
                System.out.println("Ya existe otro chofer con ese DNI.");
                return;
            }

            Chofer choferModificado = new Chofer(
                    nuevoNombre,
                    nuevoApellido,
                    nuevoDNI
            );

            choferDAO.updateChofer(dniOriginal, choferModificado);

            System.out.println("Datos del chofer modificados correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("El DNI debe contener únicamente números.");
        }
    }

    // LISTAR CHOFERES

    private static void listarChoferes(ChoferDAO choferDAO) {

        List<Chofer> choferes = choferDAO.getAllChoferes();

        if (choferes.isEmpty()) {
            System.out.println("No hay choferes registrados.");
            return;
        }

        System.out.println("\n--- Choferes Registrados ---");

        for (Chofer chofer : choferes) {
            System.out.println("-------------------------");
            System.out.println("DNI: " + chofer.getDNI());
            System.out.println("Nombre: " + chofer.getNombre());
            System.out.println("Apellido: " + chofer.getApellido());
        }

        System.out.println("-------------------------");
    }

    // ELIMINAR CHOFER

    private static void eliminarChofer(
            Scanner scanner,
            ChoferDAO choferDAO) {

        try {
            System.out.print("\nIngrese el DNI del chofer a eliminar: ");
            int dni = Integer.parseInt(scanner.nextLine());

            Chofer chofer = choferDAO.getChoferByDNI(dni);

            if (chofer != null) {
                choferDAO.deleteChofer(dni);
                System.out.println("Chofer eliminado correctamente.");
            } else {
                System.out.println("Chofer no encontrado.");
            }

        } catch (NumberFormatException e) {
            System.out.println("El DNI debe contener únicamente números.");
        }
    }

    // BUSCAR CHOFER

    private static Chofer buscarChofer(
            ChoferDAO choferDAO,
            int dni) {

        return choferDAO.getChoferByDNI(dni);
    }

    // GESTIÓN DE SALIDAS EXTRACURRICULARES

    private static void gestionarSalidaExtracurricular(
            Scanner scanner,
            SalidaDAO salidaDAO,
            ChoferDAO choferDAO,
            AlumnoController alumnoController,
            AlumnoView alumnoView) {

        int opcion = 0;

        while (opcion != 8) {
            System.out.println("\n--- Gestionar Salida Extracurricular ---");
            System.out.println("1. Crear Salida Extracurricular");
            System.out.println("2. Listar Alumnos en Salida");
            System.out.println("3. Agregar Alumno a Salida");
            System.out.println("4. Cambiar Destino");
            System.out.println("5. Cambiar Fecha");
            System.out.println("6. Modificar Salida Extracurricular");
            System.out.println("7. Eliminar Salida Extracurricular");
            System.out.println("8. Volver al Menú Principal");
            System.out.print("Ingrese la opción deseada: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        crearSalidaExtracurricular(
                                scanner, salidaDAO, choferDAO,
                                alumnoController);
                        break;

                    case 2:
                        listarAlumnosDeSalida(scanner, salidaDAO);
                        break;

                    case 3:
                        agregarAlumnoASalida(
                                scanner, salidaDAO, alumnoController);
                        break;

                    case 4:
                        cambiarDestino(scanner, salidaDAO);
                        break;

                    case 5:
                        cambiarFecha(scanner, salidaDAO);
                        break;

                    case 6:
                        modificarSalidaExtracurricular(scanner, salidaDAO);
                        break;

                    case 7:
                        eliminarSalidaExtracurricular(scanner, salidaDAO);
                        break;

                    case 8:
                        System.out.println("Volviendo al menú principal...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");

            } catch (RuntimeException e) {
                System.out.println("Error al gestionar la salida.");
                System.out.println("Detalle: " + e.getMessage());
            }
        }
    }

    // CREAR SALIDA

    private static void crearSalidaExtracurricular(
            Scanner scanner,
            SalidaDAO salidaDAO,
            ChoferDAO choferDAO,
            AlumnoController alumnoController) {

        try {
            System.out.println("\n--- Crear Salida Extracurricular ---");

            System.out.print("Destino de la salida: ");
            String destino = scanner.nextLine();

            System.out.print("Fecha de la salida (AAAA-MM-DD): ");
            Date fecha = Date.valueOf(scanner.nextLine());

            System.out.print("DNI del chofer: ");
            int dniChofer = Integer.parseInt(scanner.nextLine());

            Chofer chofer = choferDAO.getChoferByDNI(dniChofer);

            if (chofer == null) {
                System.out.println("Chofer no encontrado. No se pudo crear la salida.");
                return;
            }

            System.out.println("\nIngrese los DNI de los alumnos participantes.");
            System.out.println("Para finalizar, ingrese -1.");
            System.out.println("La capacidad máxima es de 30 alumnos.");

            SalidaExtracurricular nuevaSalida = new SalidaExtracurricular();

            while (nuevaSalida.getAlumnos().size() < 30) {

                System.out.print("DNI del alumno: ");
                int dniAlumno = Integer.parseInt(scanner.nextLine());

                if (dniAlumno == -1) {
                    break;
                }

                Alumno alumno = alumnoController.getAlumnoByDNI(dniAlumno);

                if (alumno != null) {
                    int cantidadAnterior = nuevaSalida.getAlumnos().size();

                    nuevaSalida.agregarAlumno(alumno);

                    if (nuevaSalida.getAlumnos().size() > cantidadAnterior) {
                        System.out.println("Alumno agregado a la salida.");
                    }

                } else {
                    System.out.println("Alumno no encontrado.");
                }
            }

            nuevaSalida.crearSalidaExtracurricular(
                    destino, fecha, chofer, null,
                    nuevaSalida.getAlumnos());

            if (salidaDAO.addSalida(nuevaSalida)) {
                System.out.println("ID de la salida creada: " + nuevaSalida.getId());
            }

        } catch (NumberFormatException e) {
            System.out.println("El DNI debe contener únicamente números.");

        } catch (IllegalArgumentException e) {
            System.out.println("Fecha incorrecta. Utilice el formato AAAA-MM-DD.");
        }
    }

    // LISTAR SALIDAS

    private static void listarSalidas(SalidaDAO salidaDAO) {

        List<SalidaExtracurricular> salidas = salidaDAO.getAllSalidas();

        if (salidas.isEmpty()) {
            System.out.println("No hay salidas extracurriculares registradas.");
            return;
        }

        System.out.println("\n--- Salidas Extracurriculares Registradas ---");

        for (SalidaExtracurricular salida : salidas) {
            System.out.println("--------------------------------");
            System.out.println("ID: " + salida.getId());
            System.out.println("Destino: " + salida.getDestino());
            System.out.println("Fecha: " + salida.getFecha());

            if (salida.getChofer() != null) {
                System.out.println("Chofer: "
                        + salida.getChofer().getNombre() + " "
                        + salida.getChofer().getApellido());
            } else {
                System.out.println("Chofer: No asignado");
            }

            System.out.println("Cantidad de alumnos: "
                    + salida.getAlumnos().size());
        }

        System.out.println("--------------------------------");
    }

    // LISTAR ALUMNOS DE UNA SALIDA

    private static void listarAlumnosDeSalida(
            Scanner scanner,
            SalidaDAO salidaDAO) {

        listarSalidas(salidaDAO);

        try {
            System.out.print("\nIngrese el ID de la salida: ");
            int id = Integer.parseInt(scanner.nextLine());

            SalidaExtracurricular salida = salidaDAO.getSalidaById(id);

            if (salida != null) {
                System.out.println("\nDestino: " + salida.getDestino());
                System.out.println("Fecha: " + salida.getFecha());
                salida.listarAlumnos();
            } else {
                System.out.println("No se encontró una salida con ese ID.");
            }

        } catch (NumberFormatException e) {
            System.out.println("El ID debe contener únicamente números.");
        }
    }

    // AGREGAR ALUMNO A UNA SALIDA

    private static void agregarAlumnoASalida(
            Scanner scanner,
            SalidaDAO salidaDAO,
            AlumnoController alumnoController) {

        try {
            listarSalidas(salidaDAO);

            System.out.print("\nIngrese el ID de la salida: ");
            int id = Integer.parseInt(scanner.nextLine());

            SalidaExtracurricular salida = salidaDAO.getSalidaById(id);

            if (salida == null) {
                System.out.println("Salida no encontrada.");
                return;
            }

            System.out.print("Ingrese el DNI del alumno a agregar: ");
            int dni = Integer.parseInt(scanner.nextLine());

            Alumno alumno = alumnoController.getAlumnoByDNI(dni);

            if (alumno == null) {
                System.out.println("Alumno no encontrado.");
                return;
            }

            int cantidadAnterior = salida.getAlumnos().size();

            salida.agregarAlumno(alumno);

            if (salida.getAlumnos().size() > cantidadAnterior) {
                salidaDAO.updateSalida(salida);
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese un número válido.");
        }
    }

    // CAMBIAR DESTINO

    private static void cambiarDestino(
            Scanner scanner,
            SalidaDAO salidaDAO) {

        try {
            listarSalidas(salidaDAO);

            System.out.print("\nIngrese el ID de la salida: ");
            int id = Integer.parseInt(scanner.nextLine());

            SalidaExtracurricular salida = salidaDAO.getSalidaById(id);

            if (salida == null) {
                System.out.println("Salida no encontrada.");
                return;
            }

            System.out.print("Ingrese el nuevo destino: ");
            String nuevoDestino = scanner.nextLine();

            salida.cambiarDestino(nuevoDestino);

            salidaDAO.updateSalida(salida);

        } catch (NumberFormatException e) {
            System.out.println("El ID debe contener únicamente números.");
        }
    }

    // CAMBIAR FECHA

    private static void cambiarFecha(
            Scanner scanner,
            SalidaDAO salidaDAO) {

        try {
            listarSalidas(salidaDAO);

            System.out.print("\nIngrese el ID de la salida: ");
            int id = Integer.parseInt(scanner.nextLine());

            SalidaExtracurricular salida = salidaDAO.getSalidaById(id);

            if (salida == null) {
                System.out.println("Salida no encontrada.");
                return;
            }

            System.out.print("Ingrese la nueva fecha (AAAA-MM-DD): ");
            Date nuevaFecha = Date.valueOf(scanner.nextLine());

            salida.cambiarFecha(nuevaFecha);

            salidaDAO.updateSalida(salida);

        } catch (NumberFormatException e) {
            System.out.println("El ID debe contener únicamente números.");

        } catch (IllegalArgumentException e) {
            System.out.println("Fecha incorrecta. Utilice el formato AAAA-MM-DD.");
        }
    }

    // MODIFICAR SALIDA

    private static void modificarSalidaExtracurricular(
            Scanner scanner,
            SalidaDAO salidaDAO) {

        try {
            listarSalidas(salidaDAO);

            System.out.print("\nIngrese el ID de la salida a modificar: ");
            int id = Integer.parseInt(scanner.nextLine());

            SalidaExtracurricular salida = salidaDAO.getSalidaById(id);

            if (salida == null) {
                System.out.println("Salida no encontrada.");
                return;
            }

            System.out.println("Destino actual: " + salida.getDestino());
            System.out.print("Nuevo destino: ");
            String nuevoDestino = scanner.nextLine();

            System.out.println("Fecha actual: " + salida.getFecha());
            System.out.print("Nueva fecha (AAAA-MM-DD): ");
            Date nuevaFecha = Date.valueOf(scanner.nextLine());

            salida.modificarSalidaExtracurricular(
                    nuevoDestino, nuevaFecha);

            salidaDAO.updateSalida(salida);

        } catch (NumberFormatException e) {
            System.out.println("El ID debe contener únicamente números.");

        } catch (IllegalArgumentException e) {
            System.out.println("Fecha incorrecta. Utilice el formato AAAA-MM-DD.");
        }
    }

    // ELIMINAR SALIDA

    private static void eliminarSalidaExtracurricular(
            Scanner scanner,
            SalidaDAO salidaDAO) {

        try {
            listarSalidas(salidaDAO);

            System.out.print("\nIngrese el ID de la salida a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());

            SalidaExtracurricular salida = salidaDAO.getSalidaById(id);

            if (salida == null) {
                System.out.println("Salida no encontrada.");
                return;
            }

            System.out.println("Está por eliminar la salida: "
                    + salida.getDestino());

            System.out.print("¿Confirma la eliminación? (S/N): ");
            String confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {
                salidaDAO.deleteSalida(id);
            } else {
                System.out.println("Operación cancelada.");
            }

                } catch (NumberFormatException e) {
            System.out.println("El ID debe contener únicamente números.");
        }
    }

}