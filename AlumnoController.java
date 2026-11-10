
package seminario;

import java.util.List;

public class AlumnoController {

    private AlumnoDAO alumnoDAO;

    public AlumnoController(AlumnoDAO alumnoDAO) {
        this.alumnoDAO = alumnoDAO;
    }

    public Alumno getAlumnoByDNI(int dni) {
        return alumnoDAO.getAlumnoByDNI(dni);
    }

    public void addAlumno(Alumno nuevoAlumno) {
        alumnoDAO.addAlumno(nuevoAlumno);
    }

    public void updateAlumno(Alumno alumno) {
        alumnoDAO.updateAlumno(alumno);
    }

    public List<Alumno> getAllAlumnos() {
        return alumnoDAO.getAllAlumnos();
    }

    public void deleteAlumno(int dni) {
        alumnoDAO.deleteAlumno(dni);
    }
}
