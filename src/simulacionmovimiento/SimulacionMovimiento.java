/**
 * @author Sergio Vega García y David Santomé Galván.
 * https://youtu.be/CUoOrrcs9nE
 * 
 * La clase SimulaciónMovimiento contiene las caracteristicas de la interficie
 * gráfica, la inicia y la actualiza constantemente segun el estado del juego 
 * hasta que el usuario cierre la ventana.
 */
package simulacionmovimiento;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class SimulacionMovimiento extends JFrame implements KeyListener{
    
    private static PanelDeCirculos panel;
    
    //Dimensiones ventana
    public final static int ABOLAS = 800;        //Anchura región bolas
    public final static int ADATOS = 250;        //Anchura región datos
    public final static int ALTURA = 500;        //Altura de la ventana
    
    //Componentes que forman la ventana
    private static final JPanel panel2 = new JPanel();                                 //Panel de datos
    private static final JTextField texto = new JTextField(" # Balls");                //Texto informativo
    private static final JTextField bolas = new JTextField();                          //Selector de número de bolas
    private static final JCheckBox box1 = new JCheckBox("With walls", false);          //Selector de choque con paredes
    private static final JCheckBox box2 = new JCheckBox("Follow mouse", false);        //Selector de seguimiento del ratón
        
    public static int numbolas = 0;     //Int que nos indica el número de bolas introducidas por el usuario en el TextField específico
    
    /**
     * Monta el frame del juego
     */
    public SimulacionMovimiento() { 
        super("Flying balls");                                              //Titulo ventana
        setLayout(null);                                                    //Estructura ventana
        //Montamos ventana
        add(panel);     add(panel2);                                        //Añadimos los paneles a la ventana
        setPreferredSize(new Dimension(ABOLAS+ADATOS, ALTURA));             //Establecemos tamaño ventana
        setResizable(false);                                                //Tamaño ventana invariable
        pack();                                                             //Ajustamos la ventana a su contenido
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                     //Salir del programa al puslar X
        setLocationRelativeTo(null);                                        //Ventana en centro de pantalla
        bolas.addKeyListener(this);                                         //Añadimos KeyListener al selector de bolas
    }

    /**
     * Inicia el juego y lo hace visible
     * @param args
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException{
        panel = new PanelDeCirculos(numbolas);                  //Constructor del panel de juego
        panel.setBackground(Color.WHITE);                       //Color de fondo de la zona de bolas
        panel.setBounds(0, 0, ABOLAS, ALTURA);                  //Espacio que ocupa la zona de bolas en el frame
        
        //Configuramos panel de datos
        panel2.setLayout(null);                                 //Distribución del panel de datos
        panel2.setBackground(Color.LIGHT_GRAY);                 //Color de fondo de la zona de datos
        panel2.setBounds(ABOLAS, 0, ADATOS, ALTURA);            //Espacio que ocupa la zona de datos en el frame
        
        //Componentes del panel de datos
        texto.setBounds(75, 50, 150, 50);                       //Espacio que ocupa el texto en la zona de datos
        texto.setFont(new Font("Dialog", Font.BOLD, 24));       //Fuente del texto
        texto.setBackground(Color.LIGHT_GRAY);                  //Color de fondo del TextField, hacemos que coincida con el de la zona de datos
        texto.setBorder(null);                                  //TextField sin borde
        texto.setEditable(false);                               //No se puede editar el contenido del texto
        
        bolas.setBounds(75, 105, 100, 50);                      //Espacio que ocupa el selector de bolas en la zona de datos
        bolas.setFont(new Font("Dialog", Font.BOLD, 28));       //Fuente del texto
        bolas.setHorizontalAlignment(JTextField.RIGHT);         //Al escribir, el cursor se coloca a la derecha del TextField
        bolas.setToolTipText("Enter para introducir el valor");
        
        box1.setBounds(75, 200, 150, 30);                       //Espacio que ocupa el selector de choque con paredes en la zona de datos
        box1.setBackground(Color.LIGHT_GRAY);                   //Color de fondo del CheckBox, hacemos que coincida con el de la zona de datos
        box1.setFont(new Font("Dialog", Font.BOLD, 12));        //Fuente del texto
        
        box2.setBounds(75, 230, 150, 30);                       //Espacio que ocupa el selector de seguimineto del ratón en la zona de datos
        box2.setBackground(Color.LIGHT_GRAY);                   //Color de fondo del CheckBox, hacemos que coincida con el de la zona de datos
        box2.setFont(new Font("Dialog", Font.BOLD, 12));        //Fuente del texto
        
        panel2.add(texto);          //Añadimos el texto al panel de datos
        panel2.add(bolas);          //Añadimos el selector de bolas al panel de datos
        panel2.add(box1);           //Añadimos el selector de choque con paredes al panel de datos
        panel2.add(box2);           //Añadimos el selector de seguimiento de raton al panel de datos
        
        new SimulacionMovimiento().setVisible(true);            //Iniciamos el constructor de ventana
        
        while (true) {              //Bucle infinito
            panel.movimiento();     //Calcula la velocidad, aceleración, posición e interación con los límites según el estado del juego
            panel.repaint();        //Muestra en pantalla lo calculado en el panel.movimiento()
            Thread.sleep(10);       //Retraso de 10ms
        }
    }

    /**
     * Método que reinicia la zona de bolas para dibujar una nueva serie de bolas
     * @throws InterruptedException
     */
    private void reset() throws InterruptedException {
        panel.reset(numbolas);      //Creamos una serie nueva de X bolas con las que trabajaremos
    }
    
    /**
     * Metodo que devulve el valor del selector de bolas
     * @return numbolas
     */
    public static int getNumBolas () {
        String s = bolas.getText();         //Obtenemos el contenido del selector de bolas
        if (s.isEmpty())                    //Si el selector esta vacio devuelve 0
            return 0;
        else
            return Integer.valueOf(s);      //Si no esta vacio devuelve el valor que contiene
    }
    
    /**
     * Nos devuelve el valor del selector de choque con muros
     * @return boolean
     */
    public static boolean getWalls() {
        return box1.isSelected();
    }
    
    /**
     * Nos devuelve el valor del selector de seguimiento del raton
     * @return boolean
     */
    public static boolean getMouse() {
        return box2.isSelected();
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        char c = ke.getKeyChar();                   //Obtenemos la tecla pulsada
        if (c<'0' || c>'9') 
            ke.consume();                           //Si no es un numero lo desecha, ademas libera memoria  
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();                  //Obtenemos codigo de la tecla pulsada
        if (key == KeyEvent.VK_ENTER){              
            numbolas = getNumBolas();               //Si es el Enter actualizamos el numero de bolas
            try {
                reset();
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulacionMovimiento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ke.consume();                               //Libera memoria
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {}
}
