package mascota;

public class RatoncitoFiuFiu {

    private String name;
    private int edad;
    private int peso;
    private byte hambre; // 0-10
    private byte suciedad; // 0-100
    private byte salud; // 0-100
    private byte energia; // 0-100

    public RatoncitoFiuFiu(String nombre, int peso, byte hambre, byte suciedad, byte salud, byte energia) {
        // Un objeto mascota.RatoncitoFiuFiu debería informar cuando nace...
    }


    public String estadisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Peso: ").append(peso);
        sb.append("\nHambre: ").append(hambre);
        sb.append("\nSuciedad: ").append(suciedad);
        sb.append("\nSalud: ").append(salud);
        sb.append("\nEgnergía: ").append(energia);

        return sb.toString();
    }

    public void limpiar(float esfuerzoHigienico) {
        if (suciedad - esfuerzoHigienico >= 0) {
            suciedad -= esfuerzoHigienico;
        } else {
            suciedad = 0;
        }
    }

    public int queTramoEdad() {
        return 0;
    }

    public boolean estasDormido() {
        return false;
    }

    public boolean estasEnfermo() {
        return false;
    }

    public boolean estasSucio() {
        return false;
    }

    public boolean estasMuerto() {
        return false;
    }

    public void envejecer(int i) {
    }

    public boolean tienesQuejas() {
        return false;
    }

    public void alimentar(float cantidadAlimento) {
        if (hambre - cantidadAlimento >= 0) {
            hambre -= cantidadAlimento;
        } else {
            hambre = 0;
        }
    }

    public void curar(float cantidadMedicina) {
        if (salud + cantidadMedicina <= 100) {
            salud += cantidadMedicina;
        } else {
            salud = 100;
        }
    }
}