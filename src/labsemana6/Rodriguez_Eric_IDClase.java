package labsemana6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Rodriguez_Eric_IDClase extends JFrame {

    String[] palabras = {
        "HONDURAS", "PROGRAMAR", "JENNIE", "JAVA", "CINNABON", 
        "TAYLOR", "CAMINATA", "HORMIGA", "FANTASMA", "EXAMEN"
    };
    
    String palabraActual;
    char[] palabraOculta;
    int oportunidades;
    
    JPanel panelMenu, panelJuego, panelCambiar;
    JTextArea textoJuego;
    JTextField campoLetra;
    JLabel mensaje;
    JTextArea textoPalabras;

    public Rodriguez_Eric_IDClase() {
        setTitle("Juego Adivinar Palabras");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        // Panel Menu
        panelMenu = new JPanel();
        JButton btnJugar = new JButton("Jugar");
        JButton btnCambiar = new JButton("Cambiar Palabras");
        panelMenu.add(btnJugar);
        panelMenu.add(btnCambiar);
        add(panelMenu, "menu");

        // Panel para juga 
        panelJuego = new JPanel(new BorderLayout());
        textoJuego = new JTextArea();
        textoJuego.setEditable(false);
        campoLetra = new JTextField(1);
        JButton btnEnviar = new JButton("Probar Letra");
        mensaje = new JLabel(" ");
        JPanel entrada = new JPanel();
        entrada.add(new JLabel("Letra:"));
        entrada.add(campoLetra);
        entrada.add(btnEnviar);
        panelJuego.add(textoJuego, BorderLayout.CENTER);
        panelJuego.add(entrada, BorderLayout.SOUTH);
        panelJuego.add(mensaje, BorderLayout.NORTH);
        add(panelJuego, "juego");

        // Panel para cambiar la palabra
        panelCambiar = new JPanel(new BorderLayout());
        textoPalabras = new JTextArea();
        textoPalabras.setEditable(false);
        actualizarListaPalabras();
        JTextField nuevaPalabra = new JTextField(10);
        JButton btnAgregar = new JButton("Agregar");
        JButton btnVolver = new JButton("Volver");
        JPanel cambiarAbajo = new JPanel();
        cambiarAbajo.add(new JLabel("Palabra:"));
        cambiarAbajo.add(nuevaPalabra);
        cambiarAbajo.add(btnAgregar);
        cambiarAbajo.add(btnVolver);
        panelCambiar.add(new JScrollPane(textoPalabras), BorderLayout.CENTER);
        panelCambiar.add(cambiarAbajo, BorderLayout.SOUTH);
        add(panelCambiar, "cambiar");

        // Acciones menú
        btnJugar.addActionListener(e -> {
            iniciarJuego();
            mostrarPanel("juego");
        });

        btnCambiar.addActionListener(e -> mostrarPanel("cambiar"));

        // Acciones juego
        btnEnviar.addActionListener(e -> {
            String letraIngresada = campoLetra.getText().toUpperCase();
            if (letraIngresada.length() == 1) {
                verificarLetra(letraIngresada.charAt(0));
            }
            campoLetra.setText("");
        });

        // Acciones cambiar
        btnAgregar.addActionListener(e -> {
            String nueva = nuevaPalabra.getText().toUpperCase();
            if (!nueva.isEmpty()) {
                agregarPalabra(nueva);
                nuevaPalabra.setText("");
            }
        });

        btnVolver.addActionListener(e -> mostrarPanel("menu"));
    }

    void mostrarPanel(String nombre) {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), nombre);
    }

    void iniciarJuego() {
        Random rand = new Random();
        palabraActual = palabras[rand.nextInt(palabras.length)];
        palabraOculta = new char[palabraActual.length()];
        for (int i = 0; i < palabraOculta.length; i++) {
            palabraOculta[i] = '_';
        }
        oportunidades = 5;
        actualizarPantallaJuego();
        mensaje.setText("Adivino la palabra! Oportunidades = " + oportunidades);
    }

    void actualizarPantallaJuego() {
        StringBuilder sb = new StringBuilder();
        for (char c : palabraOculta) {
            sb.append(c).append(' ');
        }
        textoJuego.setText(sb.toString());
    }

    void verificarLetra(char letra) {
        boolean acierto = false;
        for (int i = 0; i < palabraActual.length(); i++) {
            if (palabraActual.charAt(i) == letra && palabraOculta[i] == '_') {
                palabraOculta[i] = letra;
                acierto = true;
            }
        }
        if (acierto) {
            mensaje.setText("Adivino el caracter Oportunidades = " + oportunidades);
        } else {
            oportunidades--;
            mensaje.setText("Fallaste Oportunidades = " + oportunidades);
        }

        actualizarPantallaJuego();

        if (String.valueOf(palabraOculta).equals(palabraActual)) {
            JOptionPane.showMessageDialog(this, "Adivinaste la palabra");
            mostrarPanel("menu");
        } else if (oportunidades == 0) {
            JOptionPane.showMessageDialog(this, "Se quedo sin oportunidades, la palabra era: " + palabraActual);
            mostrarPanel("menu");
        }
    }

    void agregarPalabra(String nueva) {
        for (int i = 0; i < palabras.length; i++) {
            if (palabras[i] == null || palabras[i].isEmpty()) {
                palabras[i] = nueva;
                break;
            }
        }
        actualizarListaPalabras();
    }

    void actualizarListaPalabras() {
        StringBuilder sb = new StringBuilder("Palabras actuales:\n");
        for (int i = 0; i < palabras.length; i++) {
            sb.append((i + 1)).append(". ").append(palabras[i]).append("\n");
        }
        textoPalabras.setText(sb.toString());
    }

    public static void main(String[] args) {
        new Rodriguez_Eric_IDClase().setVisible(true);
    }
}
