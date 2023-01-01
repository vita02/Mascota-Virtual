package mascota;

public class RatoncitoFiuFiu {

    private String nombre;
    private int edad;
    private int peso;
    private byte hambre;
    private byte suciedad;
    private byte salud;
    private byte energia;
    private boolean dormido;

    private static final int INFANCIA = 10;//2500;
    private static final int ADULTA = 20;//8000;
    private static final int VEJEZ = 300;//13450;
    private static final int MIN_PESO = 5;

    public RatoncitoFiuFiu(String nombre, int peso, byte hambre, byte suciedad, byte salud, byte energia) {
        // Un objeto mascota.RatoncitoFiuFiu debería informar cuando nace...
        this.nombre = nombre;
        this.peso = peso;
        this.hambre = hambre;
        this.suciedad = suciedad;
        this.salud = salud;
        this.energia = energia;
        dormido = false;
    }


    public String estadisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Peso: ").append(peso);
        sb.append("\nHambre: ").append(hambre);
        sb.append("\nSuciedad: ").append(suciedad);
        sb.append("\nSalud: ").append(salud);
        sb.append("\nEnergía: ").append(energia);
        sb.append("\nEdad: ").append(edad);

        return sb.toString();
    }

    public void limpiar(float esfuerzoHigienico) {
        modificarSuciedad(-esfuerzoHigienico);
    }

    public int queTramoEdad() {
        if (edad <= INFANCIA) {
            return 0;
        } else if (edad <= ADULTA) {
            return 1;
        } else {
            return 2;
        }
    }

    public boolean estasDormido() {
        if (energia <= 50) {
            dormido = true;
        } else if (energia >= 75) {
            dormido = false;
        }
        return dormido;
    }

    public boolean estasEnfermo() {
        return salud <= 20;
    }

    public boolean estasSucio() {
        return suciedad >= 80;
    }

    public boolean tieneHambre() {
        return hambre >= 80;
    }

    public boolean estasFeliz() {
        return !tieneHambre() && !estasEnfermo() && !estasSucio();
    }

    public boolean estasMuerto() {
        return salud <= 0 || edad > VEJEZ;
    }

    public void envejecer(int segundos) {
        edad += segundos;
        ganarPeso(-1);
        alimentar(-1);
        modificarSuciedad(1);
        aumentarSalud(-1);
        if (estasDormido()) {
            aumentarEnergia(1);
        } else {
            aumentarEnergia(-1);
        }
    }

    public boolean tienesQuejas() {
        return !estasFeliz();
    }

    public void alimentar(float cantidadAlimento) {
        if (hambre - cantidadAlimento >= 0 && hambre + cantidadAlimento <= 100) {
            hambre -= cantidadAlimento;
        } else {
            hambre = 0;
        }
        ganarPeso(cantidadAlimento);
    }

    public void curar(float cantidadMedicina) {
        aumentarSalud(cantidadMedicina);
    }

    private void ganarPeso(float cantidad) {
        if (peso >= MIN_PESO) {
            peso += cantidad;
        }
    }

    private void aumentarEnergia(float cantidad) {
        energia += cantidad;
        if (energia <= 50) {
            dormido = true;
        } else if (energia >= 75) {
            dormido = false;
        }
    }

    private void aumentarSalud(float cantidad) {
        if (salud + cantidad <= 100 && salud + cantidad >= 0) {
            salud += cantidad;
        } else {
            if (cantidad > 0) {
                salud = 100;
            } else {
                salud = 0;
            }
        }
    }

    private void modificarSuciedad(float cantidad) {
        if (suciedad + cantidad <= 100 && suciedad + cantidad >= 0) {
            suciedad += cantidad;
        }
    }
}
