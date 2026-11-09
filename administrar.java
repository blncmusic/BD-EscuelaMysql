package seminario;

import java.util.ArrayList;
import java.util.List;

public class administrar {
    private List<Alumno> alumnos;

    public administrar() {
        alumnos = new ArrayList<>();
    }

    public void listarTodosLosAlumnos() {
        if (alumnos == null) {
            System.out.println("La lista de alumnos no ha sido inicializada.");
            return;
        }

        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
        } else {
            for (Alumno alumno : alumnos) {
                System.out.println(alumno);
            }
        }
    }

    public void administrarAlumnos() {
        // Lógica que usa la lista alumnos
        listarTodosLosAlumnos();
    }

    public static void main(String[] args) {
        administrar seminario = new administrar();
        seminario.administrarAlumnos();
    }
}

class Alumno {
    private String nombre;

    public Alumno(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
