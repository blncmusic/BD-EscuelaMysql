/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seminario;

public class FichaMedica {
    private String historial;
    private String alergias;
    private String emergenciaNombre;
    private int emergenciaNro;
    private String tipoSangre;

    public FichaMedica() {
    }

    public FichaMedica(String historial, String alergias, String emergenciaNombre, int emergenciaNro, String tipoSangre) {
        this.historial = historial;
        this.alergias = alergias;
        this.emergenciaNombre = emergenciaNombre;
        this.emergenciaNro = emergenciaNro;
        this.tipoSangre = tipoSangre;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getEmergenciaNombre() {
        return emergenciaNombre;
    }

    public void setEmergenciaNombre(String emergenciaNombre) {
        this.emergenciaNombre = emergenciaNombre;
    }

    public int getEmergenciaNro() {
        return emergenciaNro;
    }

    public void setEmergenciaNro(int emergenciaNro) {
        this.emergenciaNro = emergenciaNro;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    // Método para crear una nueva ficha médica
    public void crearFichaMedica(String historial, String alergias, String emergenciaNombre, int emergenciaNro, String tipoSangre) {
        this.historial = historial;
        this.alergias = alergias;
        this.emergenciaNombre = emergenciaNombre;
        this.emergenciaNro = emergenciaNro;
        this.tipoSangre = tipoSangre;
        System.out.println("Ficha médica creada correctamente");
    }

    // Método para modificar una ficha médica existente
    public void modificarFichaMedica(String nuevoHistorial, String nuevasAlergias) {
        this.historial = nuevoHistorial;
        this.alergias = nuevasAlergias;
        System.out.println("Ficha médica modificada correctamente");
    }

    // Método para eliminar una ficha médica
    public void eliminarFichaMedica() {
        // Implementa la lógica para eliminar la ficha médica (por ejemplo, establecer atributos a null)
        System.out.println("Ficha médica eliminada correctamente");
    }
}
