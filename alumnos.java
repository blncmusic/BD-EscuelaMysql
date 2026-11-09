package seminario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class alumnos {
    private List<Alumno> alumnos;

    public alumnos() {
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
        alumnos seminario = new alumnos();
        seminario.administrarAlumnos();
    }
}

class Alumno {
    private String nombre;

    public Alumno(String nombre) {
        this.nombre = nombre;
    }

    public Alumno(int dni, String nombre2, String apellido, LocalDate fechaNacimiento, String curso) {
		//TODO Auto-generated constructor stub
	}

	@Override
    public String toString() {
        return nombre;
    }

	public void setNombre(String nuevoNombre) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setNombre'");
	}

	public void setFichaMedica(FichaMedica fichaMedica) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setFichaMedica'");
	}

	public void setApellido(String nuevoApellido) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setApellido'");
	}

	public void setDNI(int nuevoDNI) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setDNI'");
	}

	public void setFechaNacimiento(LocalDate nuevaFechaNacimiento) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setFechaNacimiento'");
	}

	public void setCurso(String nuevoCurso) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setCurso'");
	}
}
