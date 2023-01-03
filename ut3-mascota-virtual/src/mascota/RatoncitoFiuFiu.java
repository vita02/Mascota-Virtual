package mascota;

import java.util.Random;

public class RatoncitoFiuFiu {

    private String nombre;
    private int edad;
    private int peso;
    private byte hambre;
    private byte suciedad;
    private byte salud;
    private byte energia;
    private byte felicidad;
    private boolean dormido;
    static Random rn = new Random();
    private static final int INFANCIA = rn.nextInt((2600 - 2400)) + 2400; //2500;
    private static final int ADULTA = rn.nextInt((8100 - 7900)) + 7900; //8000;
    private static final int VEJEZ = rn.nextInt((13500 - 13300)) + 13300; //13450;
    private static final int MIN_PESO = 5;
    private static final int RITMO_ENVEJECIMIENTO = 1;
    private static final int ENERGIA_JUGAR = 10;
    private static final int FELICIDAD_JUGAR = 20;
    private static final int HAMBRE_JUGAR = 5;

    public RatoncitoFiuFiu(String nombre, int peso, byte hambre, byte suciedad, byte salud, byte energia, byte felicidad) {
        this.nombre = nombre;
        this.peso = peso;
        this.hambre = hambre;
        this.suciedad = suciedad;
        this.salud = salud;
        this.energia = energia;
        this.felicidad = felicidad;
        dormido = false;
    }

    public String getName() {
        return nombre;
    }

    public String estadisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Peso: ").append(peso);
        sb.append("\nHambre: ").append(hambre);
        sb.append("\nSuciedad: ").append(suciedad);
        sb.append("\nSalud: ").append(salud);
        sb.append("\nEnergía: ").append(energia);
        sb.append("\nFelicidad: ").append(felicidad);
        //sb.append("\nEdad: ").append(edad);
        return sb.toString();
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

    public boolean estasMuerto() {
        return salud <= 0 || edad > VEJEZ;
    }

    /**
     * Devuelve el atributo felicidad, que cuantifica el nivel de felicidad en vez
     * de las necesidades de la mascota.
     * La felicidad no varía con el tiempo en sí, sino en función de los demás atributos.
     */
    public boolean estasFeliz() {
        return felicidad >= 40;
    }

    /**
     * @return true si la mascota tiene hambre o está sucia
     */
    public boolean tienesQuejas() {
        return tieneHambre() || estasSucio();
    }

    public void envejecer(int segundos) {
        edad += segundos;
        ganarPeso(-RITMO_ENVEJECIMIENTO);
        alimentar(-RITMO_ENVEJECIMIENTO);
        modificarSuciedad(RITMO_ENVEJECIMIENTO);
        aumentarSalud(-RITMO_ENVEJECIMIENTO);
        if (estasDormido()) {
            aumentarEnergia(RITMO_ENVEJECIMIENTO);
        } else {
            aumentarEnergia(-RITMO_ENVEJECIMIENTO);
        }
        if (tienesQuejas() || estasEnfermo()){
            modificarFelicidad(-RITMO_ENVEJECIMIENTO);
        }
    }


    public void alimentar(float cantidadAlimento) {
        if (tieneHambre()) { // mejora su salud si come con hambre
            aumentarSalud(rn.nextInt((3 - 1) + 1) + 1);
        }

        if (hambre - cantidadAlimento >= 0 && hambre + cantidadAlimento <= 100) {
            hambre -= cantidadAlimento;
        } else {
            hambre = 0;
            salud = 20; // enferma si se empacha
        }
        if (!estasEnfermo()) { // solo gana peso si está sano
            ganarPeso(1);
        }

    }

    public void curar(float cantidadMedicina) {
        aumentarSalud(cantidadMedicina);
    }

    public void limpiar(float esfuerzoHigienico) {
        modificarSuciedad(-esfuerzoHigienico);
    }

    private void aumentarEnergia(float cantidad) {
        energia += cantidad;
        if (energia <= 50) {
            dormido = true;
        } else if (energia >= 75) {
            dormido = false;
        }
    }

    /**
     * Incrementa o decrementa un valor random ([1-3]) para que el peso no se comporte igual que el hambre
     *
     * @param pn positivo o negativo (para poder ser invocado tanto desde envejecer como desde almientar)
     */
    private void ganarPeso(int pn) {
        int cantidad = rn.nextInt((3 - 1) + 1) + 1;
        if (pn > 0) {
            peso += cantidad;
        } else {
            if (peso - cantidad >= MIN_PESO) {
                peso -= cantidad;
            } else {
                peso = MIN_PESO;
            }
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

    private void modificarFelicidad(float cantidad){
        if (felicidad + cantidad <= 100 && felicidad + cantidad >= 0) {
            felicidad += cantidad;
        }
    }

    public boolean jugar(float cantidadDiversion) {
        if (estasDormido() || !estasFeliz() || estasEnfermo()) {
            return false;
        } else {
            aumentarEnergia(-ENERGIA_JUGAR);
            alimentar(-HAMBRE_JUGAR);
            modificarFelicidad(cantidadDiversion);
            return true;
        }
    }
}
