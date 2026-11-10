package seminario;

import java.util.ArrayList;
import java.util.Date;

public class SalidaExtracurricular {

    private int id;
    private String destino;
    private Date fecha;
    private Chofer chofer;
    private Docente docenteSalida;
    private ArrayList<Alumno> alumnos;

    public SalidaExtracurricular() {
        this.alumnos = new ArrayList<>();
    }

    public SalidaExtracurricular(
            int id,
            String destino,
            Date fecha,
            Chofer chofer,
            Docente docenteSalida,
            ArrayList<Alumno> alumnos) {

        this.id = id;
        this.destino = destino;
        this.fecha = fecha;
        this.chofer = chofer;
        this.docenteSalida = docenteSalida;
        this.alumnos = alumnos != null ? alumnos : new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.alumnos = alumnos != null ? alumnos : new ArrayList<>();
    }

    public void agregarAlumno(Alumno alumno) {
        if (alumno == null) {
            System.out.println("El alumno no es válido.");
            return;
        }

        if (alumnos.size() >= 30) {
            System.out.println("La capacidad de pasajeros está completa.");
            return;
        }

        for (Alumno existente : alumnos) {
            if (existente.getDNI() == alumno.getDNI()) {
                System.out.println("El alumno ya está registrado en esta salida.");
                return;
            }
        }

        alumnos.add(alumno);
        System.out.println("Alumno agregado correctamente.");
    }

    public void cambiarDestino(String nuevoDestino) {
        this.destino = nuevoDestino;
    }

    public void cambiarFecha(Date nuevaFecha) {
        this.fecha = nuevaFecha;
    }

    public void crearSalidaExtracurricular(
            String nuevoDestino,
            Date nuevaFecha,
            Chofer nuevoChofer,
            Docente nuevoDocente,
            ArrayList<Alumno> alumnosSalida) {

        this.destino = nuevoDestino;
        this.fecha = nuevaFecha;
        this.chofer = nuevoChofer;
        this.docenteSalida = nuevoDocente;
        this.alumnos = alumnosSalida != null
                ? new ArrayList<>(alumnosSalida)
                : new ArrayList<>();
    }

    public void modificarSalidaExtracurricular(
            String nuevoDestino,
            Date nuevaFecha) {

        this.destino = nuevoDestino;
        this.fecha = nuevaFecha;
    }

    public void eliminarSalidaExtracurricular() {
        this.id = 0;
        this.destino = null;
        this.fecha = null;
        this.chofer = null;
        this.docenteSalida = null;
        this.alumnos.clear();
    }

    public void listarAlumnos() {

        if (alumnos == null || alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados en esta salida.");
            return;
        }

        System.out.println("\n--- Alumnos de la Salida Extracurricular ---");

        for (Alumno alumno : alumnos) {
            System.out.println("-------------------------");
            System.out.println("DNI: " + alumno.getDNI());
            System.out.println("Nombre: " + alumno.getNombre());
            System.out.println("Apellido: " + alumno.getApellido());
            System.out.println("Curso: " + alumno.getCurso());
        }

        System.out.println("-------------------------");
    }
}