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
    private boolean jugando;
    static Random rn = new Random();
    private static final int INFANCIA = rn.nextInt((2600 - 2400)) + 2400; //2500;
    private static final int ADULTA = rn.nextInt((8100 - 7900)) + 7900; //8000;
    private static final int VEJEZ = rn.nextInt((13500 - 13300)) + 13300; //13450;
    private static final int MIN_PESO = 5;
    private static final int CANTIDAD_ALIMENTO_ANCIANO = 10;
    private static final int CANTIDAD_ALIMENTO_NIÑO = 15;
    private static final int CANTIDAD_ALIMENTO_ADULTO = 20;
    private static final int MAXIMO_ENERGIA_DORMIR_NIÑO = 90;
    private static final int MAXIMO_ENERGIA_DORMIR_ADULTO = 80;
    private static final int MAXIMO_ENERGIA_DORMIR_ANCIANO = 75;
    private static final int MINIMO_ENERGIA_DORMIR = 40;
    private static final int RITMO_ENVEJECIMIENTO = 1;
    private static final int ENERGIA_JUGAR = 5;
    private static final int SALUD_ALIMENTAR = 10;
    private static final int LIMITE_HAMBRE = 10;
    private static final int LIMITE_SALUD = 90;
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

    public void setJugar(boolean b) {
        jugando = b;
    }

    public String estadisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Peso: ").append(peso);
        sb.append("\nHambre: ").append(hambre);
        sb.append("\nSuciedad: ").append(suciedad);
        sb.append("\nSalud: ").append(salud);
        sb.append("\nEnergía: ").append(energia);
        sb.append("\nFelicidad: ").append(felicidad);
        sb.append("\nEdad: ").append(edad);
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

    public boolean estaJugando() {
        return jugando;
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
        // EDAD
        edad += segundos;
        // PESO
        ganarPeso(-RITMO_ENVEJECIMIENTO);
        if (estasEnfermo()) {
            ganarPeso(-RITMO_ENVEJECIMIENTO);
        } // si está enferma pierde más peso
        // HAMBRE
        alimentar(-RITMO_ENVEJECIMIENTO);
        // SUCIEDAD
        modificarSuciedad(RITMO_ENVEJECIMIENTO);
        // SALUD
        aumentarSalud(-RITMO_ENVEJECIMIENTO);
        if (estasSucio()) { // si está sucia enferma más rápido
            aumentarSalud(-RITMO_ENVEJECIMIENTO);
        }
        if (queTramoEdad() == 2){
            aumentarSalud(-RITMO_ENVEJECIMIENTO);
        }
        // ENERGIA
        if (estasDormido()) {
            aumentarEnergia(RITMO_ENVEJECIMIENTO);
        } else {
            aumentarEnergia(-RITMO_ENVEJECIMIENTO);
        }
        // FELICIDAD
        if ((tienesQuejas() || estasEnfermo()) && estasFeliz()) { // si esta sucia o enferma no puede ser feliz
            deprimir();
        }
        if (!estasDormido()) {
            modificarFelicidad(-RITMO_ENVEJECIMIENTO);
        }
    }

    private void deprimir() {
        felicidad = 30;
    }


    public void alimentar(float cantidadAlimento) {
        if (cantidadAlimento > 0) {
            switch (queTramoEdad()) {
                case 0 -> cantidadAlimento = CANTIDAD_ALIMENTO_NIÑO;
                case 1 -> cantidadAlimento = CANTIDAD_ALIMENTO_ADULTO;
                default -> cantidadAlimento = CANTIDAD_ALIMENTO_ANCIANO;
            }
        }
        if (tieneHambre() && cantidadAlimento > 0) { // mejora su salud si come con hambre
            aumentarSalud(SALUD_ALIMENTAR);
        }

        if (hambre - cantidadAlimento >= 0 && hambre - cantidadAlimento <= 100) {
            hambre -= cantidadAlimento;
        } else {
            if (cantidadAlimento > 0) {
                hambre = 0;
            } else {
                hambre = 100;
            }
        }

        if (hambre + cantidadAlimento <= LIMITE_HAMBRE) {
            hambre = 0;
            if (salud > 20) {
                salud = 20;
            } else {
                aumentarSalud(-5);
            }// enferma si se empacha
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
        if (energia <= MINIMO_ENERGIA_DORMIR) {
            dormido = true;
        } else {
            switch (queTramoEdad()) {
                case 0:
                    if (energia >= MAXIMO_ENERGIA_DORMIR_NIÑO) {
                        dormido = false;
                    }
                    break;
                case 1:
                    if (energia >= MAXIMO_ENERGIA_DORMIR_ADULTO) {
                        dormido = false;
                    }
                    break;
                case 2:
                    if (energia >= MAXIMO_ENERGIA_DORMIR_ANCIANO) {
                        dormido = false;
                    }
            }
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
        } else if (salud + cantidad >= LIMITE_SALUD) {

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

    private void modificarFelicidad(float cantidad) {
        if (felicidad + cantidad <= 100 && felicidad + cantidad >= 0) {
            felicidad += cantidad;
        }
    }

    public boolean jugar(float cantidadDiversion) {
        if (estasDormido() || estasEnfermo()) {
            return false;
        } else {
            aumentarEnergia(-ENERGIA_JUGAR);
            alimentar(-HAMBRE_JUGAR);
            modificarFelicidad(cantidadDiversion);
            setJugar(true);
            return true;
        }
    }
}
