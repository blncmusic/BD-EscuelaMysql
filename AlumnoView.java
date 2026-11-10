package seminario;

public class AlumnoView {

    public void printAlumnoDetails(Alumno alumno) {

        if (alumno == null) {
            System.out.println("No hay datos del alumno para mostrar.");
            return;
        }

        System.out.println("\n--- Datos del Alumno ---");
        System.out.println("DNI: " + alumno.getDNI());
        System.out.println("Nombre: " + alumno.getNombre());
        System.out.println("Apellido: " + alumno.getApellido());
        System.out.println("Fecha de nacimiento: " + alumno.getFechaNacimiento());
        System.out.println("Curso: " + alumno.getCurso());
        System.out.println("-------------------------");
    }
}
