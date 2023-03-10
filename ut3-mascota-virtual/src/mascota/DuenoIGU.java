package mascota;


import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.*;

import java.net.URL;

public class DuenoIGU extends JFrame {
    private JPanel contentPane;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JToolBar jToolBar1 = new JToolBar();
    private JButton botonEstadisticas = new JButton();
    private JButton botonLimpiar = new JButton();
    private JButton botonCurar = new JButton();
    private JButton botonAlimentar = new JButton();
    private JButton botonJugar = new JButton();
    private TitledBorder titledBorder1;
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JSplitPane jSplitPaneText = new JSplitPane();
    private JLabel labelGrafica = new JLabel();
    private JTextArea labelEstadisticas = new JTextArea();
    private JTextArea labelSalida = new JTextArea();
    private TitledBorder titledBorder2;

    private RatoncitoFiuFiu mascota;
    private Timer temporizador;
    private long horaActual, horaAnterior;
    private boolean estadoAnimacion = true;
    private boolean estadisticasVisibles = true;

    class Envejecimiento extends TimerTask {
        RatoncitoFiuFiu mascota;
        JLabel labelGrafica;

        public Envejecimiento(RatoncitoFiuFiu mascota, JLabel labelGrafica) {
            this.mascota = mascota;
            this.labelGrafica = labelGrafica;
        }

        protected void animacion() {
            ImageIcon estado1, estado2;
            String ruta, ruta1, ruta2;
            Integer tramoEdad = mascota.queTramoEdad();
            String rutaImagenes;
            URL URLruta1, URLruta2;
            boolean despierto, sucio, enfermo, despiertoSucio, despiertoEnfermo, despiertoEnfermoSucio, dormido, dormidoSucio, dormidoEnfermo, dormidoEnfermoSucio, muerto, jugando, triste;

            despierto = !mascota.estasDormido();
            dormido = !despierto;
            enfermo = mascota.estasEnfermo();
            sucio = mascota.estasSucio();
            triste = !mascota.estasFeliz();
            despiertoSucio = despierto && sucio;
            despiertoEnfermo = despierto && enfermo;
            despiertoEnfermoSucio = despiertoSucio && despiertoEnfermo;
            dormidoSucio = dormido && sucio;
            dormidoEnfermo = dormido && enfermo;
            dormidoEnfermoSucio = dormidoSucio && dormidoEnfermo;
            muerto = mascota.estasMuerto();
            jugando = mascota.estaJugando();

            try {
                rutaImagenes = getClass().getResource("../imagenes").toString();
                ruta1 = "";
                ruta2 = "";

                if (muerto) {
                    ruta1 = rutaImagenes + "/muerto.gif";
                    ruta2 = rutaImagenes + "/muerto.gif";
                    labelSalida.setText(mascota.getName() + " ha muerto...");
                } else if (jugando) {
                    ruta1 = rutaImagenes + "/jugar-" + tramoEdad.toString() + "-00.png";
                    ruta2 = rutaImagenes + "/jugar-" + tramoEdad.toString() + "-01.png";
                    labelSalida.setText("Jugando");
                    mascota.setJugar(false);
                } else if (!mascota.tienesQuejas() || enfermo || triste) {
                    if (despiertoEnfermoSucio) {
                        labelSalida.setText("Estoy malito...");
                        ruta1 = rutaImagenes + "/despierto-enfermo-sucio-" + tramoEdad.toString() + "-00.gif";
                        ruta2 = rutaImagenes + "/despierto-enfermo-sucio-" + tramoEdad.toString() + "-01.gif";
                    } else if (despiertoSucio) {
                        ruta1 = rutaImagenes + "/despierto-sucio-" + tramoEdad.toString() + "-00.gif";
                        ruta2 = rutaImagenes + "/despierto-sucio-" + tramoEdad.toString() + "-01.gif";
                    } else if (despiertoEnfermo) {
                        labelSalida.setText("Estoy malito...");
                        ruta1 = rutaImagenes + "/despierto-enfermo-" + tramoEdad.toString() + "-00.gif";
                        ruta2 = rutaImagenes + "/despierto-enfermo-" + tramoEdad.toString() + "-01.gif";
                    } else if (dormidoEnfermoSucio) {
                        ruta1 = rutaImagenes + "/dormido-enfermo-sucio-" + tramoEdad.toString() + "-00.gif";
                        ruta2 = rutaImagenes + "/dormido-enfermo-sucio-" + tramoEdad.toString() + "-01.gif";
                    } else if (dormidoSucio) {
                        ruta1 = rutaImagenes + "/dormido-sucio-" + tramoEdad.toString() + "-00.gif";
                        ruta2 = rutaImagenes + "/dormido-sucio-" + tramoEdad.toString() + "-01.gif";
                    } else if (dormidoEnfermo) {
                        labelSalida.setText("Estoy malito...");
                        ruta1 = rutaImagenes + "/dormido-enfermo-" + tramoEdad.toString() + "-00.gif";
                        ruta2 = rutaImagenes + "/dormido-enfermo-" + tramoEdad.toString() + "-01.gif";
                    } else if (dormido) {
                        ruta1 = rutaImagenes + "/dormido-" + tramoEdad.toString() + "-00.gif";
                        ruta2 = rutaImagenes + "/dormido-" + tramoEdad.toString() + "-01.gif";
                    } else if (triste) {
                        ruta1 = rutaImagenes + "/triste-" + tramoEdad.toString() + "-00.png";
                        ruta2 = rutaImagenes + "/triste-" + tramoEdad.toString() + "-01.png";
                        labelSalida.setText("Estoy triste...");
                    } else if (despierto) {
                        ruta1 = rutaImagenes + "/despierto-" + tramoEdad.toString() + "-00.gif";
                        ruta2 = rutaImagenes + "/despierto-" + tramoEdad.toString() + "-01.gif";
                    }
                } else {
                    if (sucio) {
                        ruta1 = rutaImagenes + "/quejarse-sucio-" + tramoEdad.toString() + "-00.gif";
                        ruta2 = rutaImagenes + "/quejarse-sucio-" + tramoEdad.toString() + "-01.gif";
                        labelSalida.setText("Estoy sucio!");
                    } else {
                        ruta1 = rutaImagenes + "/quejarse-" + tramoEdad.toString() + "-00.gif";
                        ruta2 = rutaImagenes + "/quejarse-" + tramoEdad.toString() + "-01.gif";
                        labelSalida.setText("Tengo hambre!");
                    }
                }

                try {
                    URLruta1 = new URL(ruta1);
                    estado1 = new ImageIcon(URLruta1);

                    try {
                        URLruta2 = new URL(ruta2);
                        estado2 = new ImageIcon(URLruta2);

                        if (estadoAnimacion) {
                            labelGrafica.setIcon(estado1);
                            estadoAnimacion = false;
                        } else {
                            labelGrafica.setIcon(estado2);
                            estadoAnimacion = true;
                        }
                    } catch (Exception e) {
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
            }
        }

        public void run() {
            if (!mascota.estasMuerto()) {
                mascota.envejecer(1);
            } else {
                botonAlimentar.setEnabled(false);
                botonCurar.setEnabled(false);
                botonLimpiar.setEnabled(false);
                botonJugar.setEnabled(false);
            }
            if (System.currentTimeMillis() - horaAnterior >= 5000)
                labelSalida.setText("");
            if (System.currentTimeMillis() - horaAnterior >= 1000 && mascota.tienesQuejas())
                hazmeCaso();
            animacion();

            if (estadisticasVisibles) {
                labelEstadisticas.setText(mascota.estadisticas());
            }
        }
    }

    //Construir el marco
    public DuenoIGU() {
        String nombre = "Ratatouille";
        mascota = new RatoncitoFiuFiu(nombre, 50, (byte) 50, (byte) 50, (byte) 100, (byte) 100, (byte) 100);
        labelSalida.setText("Hola! Soy " + nombre);
        temporizador = new Timer();
        temporizador.schedule(new Envejecimiento(mascota, labelGrafica), 0, 500);
        horaAnterior = System.currentTimeMillis();
        horaActual = horaAnterior;

        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
            this.setTitle("mascota.RatoncitoFiuFiu : " + nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Inicializaci???n de componentes
    private void jbInit() throws Exception {
        contentPane = (JPanel) this.getContentPane();
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder("");
        contentPane.setMaximumSize(new Dimension(100, 100));
        contentPane.setMinimumSize(new Dimension(100, 100));
        contentPane.setPreferredSize(new Dimension(100, 100));
        contentPane.setLayout(borderLayout1);
        this.getContentPane().setBackground(Color.white);
        this.setSize(new Dimension(341, 337));
        this.setTitle("mascota.RatoncitoFiuFiu : ");
        botonEstadisticas.setText("Ocultar estad??sticas");
        botonEstadisticas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                botonEstadisticas_mouseClicked(e);
            }
        });
        botonLimpiar.setText("Limpiar");
        botonLimpiar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                botonLimpiar_mouseClicked(e);
            }
        });
        botonCurar.setText("Curar");
        botonCurar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                botonCurar_mouseClicked(e);
            }
        });
        botonAlimentar.setText("Alimentar");
        botonAlimentar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                botonAlimentar_mouseClicked(e);
            }
        });
        botonJugar.setText("Jugar");
        botonJugar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                botonJugar_mouseClicked(e);
            }
        });
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setBorder(null);
        jSplitPane1.setBottomComponent(labelGrafica);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setDividerSize(0);
        jSplitPaneText.setOrientation((JSplitPane.HORIZONTAL_SPLIT));
        jSplitPaneText.setBorder(null);
        jSplitPaneText.setContinuousLayout(true);
        jSplitPaneText.setDividerSize(0);
        labelGrafica.setBackground(Color.white);
        labelGrafica.setForeground(Color.white);
        labelGrafica.setHorizontalAlignment(SwingConstants.CENTER);
        labelGrafica.setHorizontalTextPosition(SwingConstants.CENTER);
        labelSalida.setEditable(false);
        labelSalida.setMargin(new Insets(5, 5, 5, 5));
        labelEstadisticas.setEditable(false);
        labelSalida.setMargin(new Insets(5, 5, 5, 5));
        jToolBar1.add(botonAlimentar, null);
        jToolBar1.add(botonCurar, null);
        jToolBar1.add(botonLimpiar, null);
        jToolBar1.add(botonJugar, null);
        jToolBar1.add(botonEstadisticas, null);
        contentPane.add(jSplitPane1, BorderLayout.CENTER);
        contentPane.add(jSplitPaneText, BorderLayout.NORTH);
        jSplitPaneText.add(labelEstadisticas, JSplitPane.LEFT);
        jSplitPaneText.add(labelSalida, JSplitPane.RIGHT);
        jSplitPaneText.setDividerLocation(120);
        jSplitPane1.add(labelGrafica, JSplitPane.RIGHT);
        jSplitPane1.add(jSplitPaneText, JSplitPane.LEFT);
        contentPane.add(jToolBar1, BorderLayout.SOUTH);
        jSplitPane1.setDividerLocation(140);
    }

    //Modificado para poder salir cuando se cierra la ventana
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    void botonAlimentar_mouseClicked(MouseEvent e) {
        // Aqui alimentamos a la mascota
        mascota.alimentar(15);
        labelSalida.setText("");
    }

    void botonCurar_mouseClicked(MouseEvent e) {
        //Aqu??? curamos a la mascota
        mascota.curar(15);
        labelSalida.setText("");
    }

    void botonLimpiar_mouseClicked(MouseEvent e) {
        //Aqu?? limpiamos a la mascota
        mascota.limpiar(15);
        labelSalida.setText("");
    }

    void botonJugar_mouseClicked(MouseEvent e) {
        //Aqu?? jugamos con la mascota
        mascota.jugar(30);
    }

    void botonEstadisticas_mouseClicked(MouseEvent e) {
        //Aqu??? pedimos estadisticas
        if (estadisticasVisibles) {
            botonEstadisticas.setText("Mostrar estad??sticas");
            labelEstadisticas.setText("");
            estadisticasVisibles = false;
        } else {
            botonEstadisticas.setText("Ocultar estad??sticas");
            labelEstadisticas.setText(mascota.estadisticas());
            estadisticasVisibles = true;
        }
        horaAnterior = System.currentTimeMillis();
    }

    void hazmeCaso() {
        String rutaAudio = "../audio/argh.wav";
        try (AudioInputStream audioInput = AudioSystem.getAudioInputStream(DuenoIGU.class.getResource(rutaAudio))) {
            try (Clip clip = AudioSystem.getClip()) {
                clip.open(audioInput);
                clip.start();
            } catch (Exception e) {
                System.out.println("No se pudo crear el audio.");
            }
        } catch (Exception e) {
            System.out.println("Fichero de audio no encontrado");
        }

        horaAnterior = System.currentTimeMillis();
    }
}
