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

    public boolean isInBox(PSPoint[] box){
        if(x >= box[0].getX() && x <= box[1].getX() && y >= box[1].getY() && y <= box[1].getY() ){
            return true ;
        }
        return false ;
    }

    public double getX(){
        return x ;
    }
    public double getY(){
        return y ;
    }
    public void setX(double newX){x = newX ;}
    public void setY(double newY){y = newY ;}

    public static double[] lineEquation(PSPoint a, PSPoint b){
        double x1 = a.getX(), y1 = a.getY() ;
        double x2 = b.getX(), y2 = b.getY() ;
        return new double[]{ y1-y2, x2-x1, y1*x2 + x1*y2 } ;
    }

    public static PSPoint getVector(PSPoint begin, PSPoint end){
        PSPoint ans = new PSPoint(end.getX() - begin.getX(), end.getY() - begin.getY()) ;
        return ans ;
    }

    public double distanceToLine(double[] line){
        double a = line[0], b = line[1], c = line[2];
        return Math.abs( (a*x + b*y + c )/(a*a + b*b)) ;
    }

    public static PSPoint lineIntersect(double[] line1, double[] line2){
        double a1 = line1[0], b1 = line1[1], c1 = line1[2] ;
        double a2 = line2[0], b2 = line2[1], c2 = line2[2] ;
        double x = (c1*b2 - c2*b1)/(b1*a2 - b2*a1) , y = (c2*a1 - c1*a2)/(b1*a2 - b2*a1) ;
        return new PSPoint(x,y) ;
    }

    public boolean isOnSection(PSPoint sBegin, PSPoint sEnd){
        PSPoint vector = getVector(sBegin, sEnd) ; //vectorx, vectory
        double t ;
        if(getX() != 0) {
           t =  (x - sBegin.getX())/ vector.getX() ;
        }
        else{
            t = (y - sBegin.getY())/ vector.getY() ;
        }
        return (t >= 0 && t <= 1) ;
    }
}
