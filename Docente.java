/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seminario;



public class Docente extends Persona {
    private String [] aulasAsignadas;

    public Docente() {
    }
    
    public Docente (String nombre, String apellido, int dni) {
        super (nombre, apellido, dni);
        this.aulasAsignadas = new String [10];
    }
    
    public void asignarAula(String aula) {
        for (int i=0; i<aulasAsignadas.length; i++){
            if (aulasAsignadas[i] == null) {
                aulasAsignadas[i] = aula;
                System.out.println("Aula asignada: " + aula);
                return;
            }
        }
                System.out.println ("El docente ya tiene todas las aulas asignadas");
    }
    public void modificarDatosPersonales(String nuevoNombre, String nuevoApellido, int nuevoDNI) {
        this.setNombre(nuevoNombre);
        this.setApellido(nuevoApellido);
        this.setDNI(nuevoDNI);
        System.out.println ("Datos modificados");
    }
    
    public void eliminarDocente() {
        
    }
    
    public void consultarLegajoDocente () {
        System.out.println ("Nombre completo: " + this.getNombre() + this.getApellido());
        System.out.println ( "DNI: " + this.getDNI());
    }
}
