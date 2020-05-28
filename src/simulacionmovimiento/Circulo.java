/**
 * @author Sergio Vega García y David Santomé Galván.
 *
 * Clase Circulo que define las bolas para la simulacion de movimiento. Los
 * bolas se caracterizan por su color, posición, velocidad, y aceleración.
 *
 * El constructor crea una bola de color y posición aleatorios, y una velocidad
 * y aceleración concretas.
 * 
 * El metodo dibuja pinta la bola.
 */
package simulacionmovimiento;

import java.awt.*;
import java.util.Random;

class Circulo {
    
    private final int diametro = 40;            //Diametro bola
    private final Color col;                    //Color bola
    private Vector posicion,velocidad,aceleracion;
    public static final float BORDE = 5;        //Tamaño del BORDE de la bola
    
    public Circulo () {
        Random rand = new Random();
        //Genera posición aleatoria para la bola
        int x = rand.nextInt(SimulacionMovimiento.ABOLAS); 
        int y = rand.nextInt(SimulacionMovimiento.ALTURA);                   
        //Ajusta la posición teniendo en cuenta limites, tamaño bolas y BORDE bola
        if (x>=SimulacionMovimiento.ABOLAS-diametro-BORDE*2) 
            x = (int) (SimulacionMovimiento.ABOLAS-diametro-BORDE/2);
        if (y>=SimulacionMovimiento.ALTURA-diametro*2-BORDE/2) 
            y = (int) (SimulacionMovimiento.ALTURA-diametro*2+BORDE*2);              
        //Genera color aleatoria para la bola
        float rojo  = (float) Math.random(); float verde = (float) Math.random(); float azul  = (float) Math.random();
        col = new Color(rojo,verde,azul);
        //Establecemos vectores posición, velocidad y aceleración
        this.posicion = new Vector(x,y);
        this.velocidad = new Vector(1, 1);
        this.aceleracion = new Vector(0.05, 0.5);
    }
    
    /**
     * Método que dibuja la bola en el panel
     * @param g
     */
    public void dibuja(Graphics g) {
        g.setColor(Color.BLACK);        //Color BORDE
        g.drawOval((int) this.getPosicion().getX(), (int) this.getPosicion().getY(), diametro, diametro);       //Dibuja BORDE
        g.setColor(col);                //Color interior
        g.fillOval((int) this.getPosicion().getX(), (int) this.getPosicion().getY(), diametro, diametro);       //Dibuja interior  
        
    }
    
    /**
     * Método que devuelve la posición de la bola
     * @return posicion
     */
    public Vector getPosicion() {
        return posicion;
    }
    
    /**
     * Método para establecer la posición de la bola vectorialmente
     * @param posicion
     */
    public void setPosicion(Vector posicion){
        this.posicion = posicion;
    }
    
    /**
     * Método para establecer la posición de la bola por componentes
     * @param x
     * @param y
     */
    public void setPosicion(double x, double y){
        this.posicion.setX(x);
        this.posicion.setY(y);
    }
    
    /**
     * Método para obetener la velocidad de la bola
     * @return velocidad
     */
    public Vector getVelocidad() {
        return velocidad;
    }
    
    /**
     * Método para establecer la velocidad de la bola por componentes
     * @param x
     * @param y
     */
    public void setVelocidad(double x, double y){
        this.velocidad.setX(x);
        this.velocidad.setY(y);
    }
    
    /**
     * Método para obtener la aceleración de la bola
     * @return aceleracion
     */
    public Vector getAceleracion() {
        return aceleracion;
    }
    
    /**
     * Método para establecer la aceleracion de la bola vectorialmente
     * @param aceleracion
     */
    public void setAceleracion(Vector aceleracion){
        this.aceleracion = aceleracion;
    }
    
    /**
     * Método para establecer la aceleracion de la bola por componentes
     * @param x
     * @param y
     */
    public void setAceleracion(double x, double y){
        this.aceleracion.setX(x);
        this.aceleracion.setY(y);
    }
    
    /**
     * Método para obtener el diametro de la bola
     * @return diametro
     */
    public int getDiametro() {
        return diametro;
    }
    
    /**
     * Método para acelerar la bola sumando la aceleracion a la velocidad
     * describiendo la ecuación v = v0 + at donde t es cada instante
     */
    public void acelerar(){
        this.velocidad.sumar(aceleracion);
    }
    
    /**
     * Método para elegir que limites aplicar a la bola
     */
    public void limites () {
        boolean walls = SimulacionMovimiento.getWalls();
        if (!walls) 
            limites0();
        else 
            limites1();
    }
    
    /**
     * Método que calcula los limites cuando se elige que no haya muros
     */
    private void limites0() {
        // If: Pared Izquierda ; Else If: Pared Derecha
        if(this.getPosicion().getX() < 0) {
            this.setPosicion(SimulacionMovimiento.ABOLAS-BORDE, this.getPosicion().getY());             //Recolocamos la bola
        } 
        else if (this.getPosicion().getX() > SimulacionMovimiento.ABOLAS-BORDE){
            this.setPosicion(0, this.getPosicion().getY());                                             //Recolocamos la bola
        }
            
        // If: Pared Arriba ; Else If: Pared Abajo
        if (this.getPosicion().getY() < 0){
            this.setPosicion(this.getPosicion().getX(), SimulacionMovimiento.ALTURA-diametro-BORDE*2);  //Recolocamos la bola
        }
        else if (this.getPosicion().getY() > SimulacionMovimiento.ALTURA-diametro-2*BORDE) {        
            this.setPosicion(this.getPosicion().getX(), 0);                                             //Recolocamos la bola
        }
            
    
    }
    
    /**
     * Método que calcula los limites cuando se elige que haya muros
     */
    private void limites1() {
        // If: Pared Izquierda ; Else If: Pared Derecha
        if (this.getPosicion().getX() < BORDE) {
            this.setVelocidad(-this.getVelocidad().getX(), this.getVelocidad().getY());         //Velocidad igual y opuesta en el eje X
            this.setAceleracion(-this.getAceleracion().getX(), this.getAceleracion().getY());   //Aceleración igual y opuesta en el eje X
            this.setPosicion(BORDE, this.getPosicion().getY());                                 //Recolocamos la bola
        } 
        else if (this.getPosicion().getX() > SimulacionMovimiento.ABOLAS-diametro) {
            this.setVelocidad(-this.getVelocidad().getX(), this.getVelocidad().getY());         //Velocidad igual y opuesta en el eje X
            this.setAceleracion(-this.getAceleracion().getX(), this.getAceleracion().getY());   //Aceleración igual y opuesta en el eje X
            this.setPosicion(SimulacionMovimiento.ABOLAS-diametro, this.getPosicion().getY());  //Recolocamos la bola
        }
        //If: Pared Arriba ; Else If: Pared Abajo
        if (this.getPosicion().getY() < BORDE) {
            this.setVelocidad(this.getVelocidad().getX(), -this.getVelocidad().getY());         //Velocidad igual y opuesta en el eje Y
            this.setAceleracion(this.getAceleracion().getX(), -this.getAceleracion().getY());   //Aceleración igual y opuesta en el eje Y  
            this.setPosicion(this.getPosicion().getX(), BORDE);                                 //Recolocamos la bola
        } 
        else if (this.getPosicion().getY() > SimulacionMovimiento.ALTURA-diametro*2+BORDE*2) {
            this.setVelocidad(this.getVelocidad().getX(), -this.getVelocidad().getY());                     //Velocidad igual y opuesta en el eje Y
            this.setAceleracion(this.getAceleracion().getX(), -this.getAceleracion().getY());               //Aceleración igual y opuesta en el eje Y
            this.setPosicion(this.getPosicion().getX(), SimulacionMovimiento.ALTURA-diametro*2+BORDE*2);    //Recolocamos la bola
        }
    }    
    
}
