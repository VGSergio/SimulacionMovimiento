/**
 * @author Sergio Vega García y David Santomé Galván.
 *
 * Clase Vector que cuenta con todas las operaciones necesariaas para 
 * trabajar con las bolas y sus diferentes interaciones.
 */
package simulacionmovimiento;

public class Vector {
    
    private double x,y;                 //Coordenadas de la bola
    private final double maxv = 5;      //Velocidad máxima
    
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Getter de la coordenada X de la bola
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * Setter de la coordenada X de la bola
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Getter de la coordenada Y de la bola
     * @return Y
     */
    public double getY() {
        return y;
    }

    /**
     * Setter de la coordenada Y de la bola
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }
    
    /**
     * Suma por componentes de un vector
     * @param x
     * @param y
     */
    public void sumar(double x, double y){
        this.x = this.x + x;
        this.y = this.y + y;
    }
    
    /**
     * Suma Vectorial de dos vectores
     * @param vec
     */
    public void sumar(Vector vec){
        this.x = this.x + vec.getX();
        this.y = this.y + vec.getY();
    }
    
    /**
     * Resta por componentes de un vector
     * @param x
     * @param y
     */
    public void restar(double x, double y){
        this.x = this.x - x;
        this.y = this.y - y;
    }
    
    /**
     * Resta vectorial de dos vectores
     * @param vec
     */
    public void restar(Vector vec){
        this.x = this.x - vec.getX();
        this.y = this.y - vec.getY();
    }
    
    /**
     * Producto escalar de un vector
     * @param escalar
     */
    public void ProductoEscalar(double escalar){
        this.x = x * escalar;
        this.y = y * escalar;
    }
    
    /**
     * División Escalar de un vector
     * @param escalar
     */
    public void DivisionEscalar(double escalar){
        this.x = x/escalar;
        this.y = y/escalar;
    }
    
    /**
     * Calcula el módulo del vector
     * @return modulo
     */
    public double modulo(){
        return Math.sqrt(x*x + y*y);
    }
    
    
    /**
     * Calcula el vector unitario, permitiendo una mejor visualización
     */
    public void VectorUnitario(){
        this.DivisionEscalar(this.modulo());
    }
    
    /**
     * Límita la velocidad de la bola para impedir que vaya demasiado rápido
     */
    public void limitar(){
        if(this.modulo() >= maxv){
            this.VectorUnitario();
            this.ProductoEscalar(maxv);
        }
    }
}
