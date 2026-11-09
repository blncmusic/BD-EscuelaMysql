package seminario;

import java.util.ArrayList;
import java.util.Date;

public class SalidaExtracurricular {
    private String destino;
    private Date fecha;
    private Chofer chofer;
    private Docente docenteSalida;
    private ArrayList<Alumno> alumnos;

    public SalidaExtracurricular() {
        this.alumnos = new ArrayList<>(); // Constructor vacío
    }

    public SalidaExtracurricular(String destino, Date fecha, Chofer chofer, Docente docenteSalida, ArrayList<Alumno> alumnos) {
        this.destino = destino;
        this.fecha = fecha;
        this.chofer = chofer;
        this.docenteSalida = docenteSalida;
        this.alumnos = alumnos;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public Docente getDocenteSalida() {
        return docenteSalida;
    }

    public void setDocenteSalida(Docente docenteSalida) {
        this.docenteSalida = docenteSalida;
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    // Método para agregar un alumno a la salida
    public void agregarAlumno(Alumno alumno) {
        if (alumnos.size() < 30) { // Capacidad máxima de pasajeros
            alumnos.add(alumno);
            System.out.println("Alumno agregado correctamente");
        } else {
            System.out.println("La capacidad de pasajeros está completa");
        }
    }

    // Método para cambiar el destino
    public void cambiarDestino(String nuevoDestino) {
        this.destino = nuevoDestino;
        System.out.println("Destino cambiado a: " + nuevoDestino);
    }

    // Método para cambiar la fecha
    public void cambiarFecha(Date nuevaFecha) {
        this.fecha = nuevaFecha;
        System.out.println("Fecha cambiada a: " + nuevaFecha);
    }

    // Método para crear una nueva salida extracurricular
    public void crearSalidaExtracurricular(String nuevoDestino, Date nuevaFecha, Chofer nuevoChofer, Docente nuevoDocente, ArrayList<Alumno> alumnosSalida) {
        this.destino = nuevoDestino;
        this.fecha = nuevaFecha;
        this.chofer = nuevoChofer;
        this.docenteSalida = nuevoDocente;
        System.out.println("Salida extracurricular creada correctamente");
    }

    // Método para modificar una salida extracurricular existente
    public void modificarSalidaExtracurricular(String nuevoDestino, Date nuevaFecha) {
        this.destino = nuevoDestino;
        this.fecha = nuevaFecha;
        System.out.println("Salida extracurricular modificada correctamente");
    }

    // Método para eliminar una salida extracurricular
    public void eliminarSalidaExtracurricular() {
        // Implementa la lógica para eliminar la salida extracurricular (por ejemplo, establecer atributos a null)
        System.out.println("Salida extracurricular eliminada correctamente");
    }

    // Método para listar alumnos
    public void listarAlumnos() {
        for (Alumno alumno : alumnos) {
            System.out.println("Nombre: " + alumno.getNombre() + ", Apellido: " + alumno.getApellido() + ", DNI: " + alumno.getDNI());
        }
    }
}
