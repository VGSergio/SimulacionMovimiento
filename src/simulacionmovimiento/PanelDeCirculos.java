/**
 * @author Sergio Vega García y David Santomé Galván.
 *
 * La clase PanelDeCirculos es un panel gráfico activo al movimiento del ratón,
 * contiene la coleción de bolas, y se encarga del movimiento de las bolas
 * según si siguen el ratón o no.
 */
package simulacionmovimiento;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelDeCirculos extends JPanel implements MouseMotionListener{
    
    private Circulo c[];                                //Array de bolas con las que trabajamos
    private final Vector mouse = new Vector(0,0);       //Posicion del mouse
    
    /**
     * Crea tantas bolas como se le introduzcan por parametro
     * @param numbolas
     */
    public PanelDeCirculos(int numbolas){
        c = new Circulo[numbolas];
        for (int i = 0; i < numbolas; i++) {
            c[i] = new Circulo();
        }
        this.addMouseMotionListener(this);      //Listener del moviemiento de ratón
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(Circulo.BORDE));               //Borde de la bola
        for (int i = 0; i<SimulacionMovimiento.numbolas;i++) {
            c[i].dibuja(g);                                         //Dibuja las bolas
        }
    }
    
    /**
     * Crea una nueva serie de bolas
     * @param numbolas
     */
    public void reset(int numbolas) {
        c = new Circulo[numbolas];
        for (int i = 0; i < numbolas; i++) {
            c[i] = new Circulo();
        }
    }
    
    /**
     * Método que obtiene el valor del seguimiento de ratón y calcula el 
     * moviemiento de la bola según su estado
     */
    public void movimiento() {
        boolean Emouse = SimulacionMovimiento.getMouse();
        if (!Emouse) 
            movimiento0();      //No sigue al ratón
        else
            movimiento1();      //Sigue al ratón
    }
    
    /**
     * Método que calcula el movimiento de la bola cuando no se sigue al ratón
     */
    private void movimiento0() {
        for (Circulo c1 : c) {
            c1.limites();                                           //Calculamos la interación con los bordes
            c1.setAceleracion(c1.getAceleracion().getX(), 0.05);     //Aplicamos el efecto de caida
            c1.acelerar();                                          //Aceleramos la bola        
            c1.getVelocidad().limitar();                            // Comprobamos que no exceda el límite de velocidad
            c1.getPosicion().sumar(c1.getVelocidad());              // Obtenemos la nueva Posición de la bola
        }
    }
   
    /**
     * Método que calcula el movimiento de la bola cuando se sigue al ratón
     */
    private void movimiento1() {
        for (Circulo c1 : c) {
            //Hacemos que siga al raton
            Vector v = new Vector(mouse.getX(), mouse.getY());          //Vector v para calcular la nueva aceleración de la bola, inicialmente vale mouse 
            v.restar(c1.getPosicion());                                 //Restamos mouse-posicion bola 
            v.setX(v.getX()/SimulacionMovimiento.ABOLAS);               //Controlamos el valor de v 
            v.setY(v.getY()/SimulacionMovimiento.ABOLAS);               //Controlamos el valor de v 
            c1.setAceleracion(v);                                       //Le damos a la bola la aceleración calculada

            c1.limites();                               //Calculamos la interación con los bordes
            c1.acelerar();                              //Aceleramos la bola
            c1.getVelocidad().limitar();                // Comprobamos que no exceda el límite de velocidad
            c1.getPosicion().sumar(c1.getVelocidad());  // Obtenemos la nueva posición de la bola
            
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent me) {
        mouse.setX(me.getX());
        mouse.setY(me.getY());
        me.consume();       //Libera memoria
    }    

    @Override
    public void mouseDragged(MouseEvent me){}
}
