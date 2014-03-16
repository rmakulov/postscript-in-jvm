package runtime.graphics.point;

/**
 * Created by user on 14.03.14.
 */
public class PSPoint {
    private double x ;
    private double y ;
    public PSPoint(){ }
    public PSPoint(double pX, double pY){
        x = pX ; y = pY ;
    }
    public static double distance(PSPoint a, PSPoint b){
        double xDif = a.getX() - b.getX() ;
        double yDif = a.getY() - b.getY() ;
        return Math.sqrt(xDif * xDif + yDif * yDif) ;
    }
    public double getX(){
        return x ;
    }
    public double getY(){
        return y ;
    }
    public void setX(double newX){x = newX ;}
    public void setY(double newY){y = newY ;}

    public boolean isInBox(PSPoint[] box){
        if(x >= box[0].getX() && x <= box[1].getX() && y >= box[1].getY() && y <= box[1].getY() ){
            return true ;
        }
        return false ;
    }
}
