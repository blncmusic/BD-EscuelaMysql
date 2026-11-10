package seminario;

public class Chofer extends Persona {

    public Chofer() {
    }

    public Chofer(String nombre, String apellido, int DNI) {
        super(nombre, apellido, DNI);
    }

    public void modificarDatosPersonales(String nuevoNombre, String nuevoApellido, int nuevoDNI) {
        this.setNombre(nuevoNombre);
        this.setApellido(nuevoApellido);
        this.setDNI(nuevoDNI);

        System.out.println("Datos del chofer modificados correctamente.");
    }
}
