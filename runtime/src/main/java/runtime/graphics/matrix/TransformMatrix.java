package runtime.graphics.matrix;

/**
 * Created by user on 15.03.14.
 */
import runtime.graphics.point.PSPoint ;
public class TransformMatrix {
    private double[] matrix ;
    private double[] invertmatrix ;
    public TransformMatrix(){// [a b c d t_x t_y ]
        matrix= new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 0.0} ;//or -631
    }

    public TransformMatrix(double[] arr){
        matrix = arr ;
    }

    public TransformMatrix getInverseMatrix(){
        double a = matrix[0], b = matrix[1], c = matrix[2], d = matrix[3], tx = matrix[4], ty = matrix[5] ;
        double det = a*d - b*c ;
        if(det == 0){
            return null ;
        }
        double[] newArr = new double[]{d/det, -b /det, -c/det, a / det, -(d*tx-c*ty)/det, (b*tx-a*ty)/det } ;
        TransformMatrix inverseMatrix = new TransformMatrix(newArr) ;
        return inverseMatrix ;
    }

    public void multMatrix(double[] transf){
        double a = matrix[0], b = matrix[1], c = matrix[2], d = matrix[3], tx = matrix[4], ty = matrix[5] ;
        double a1= transf[0], b1= transf[1], c1= transf[2], d1= transf[3], tx1= transf[4], ty1= transf[5] ;
        matrix = new double[]{a*a1+b*c1, a*b1+b*d1, a1*c+c1*d, b1*c+d*d1, a1*tx+tx1+c1*ty, b1*tx+d1*ty+ty} ;
    }

    public void scale(double sx, double sy){
        double[] transform = new double[]{sx,0,0,sy,0,0};
        multMatrix(transform) ;
    }

    public void translate(double tx, double ty){
        double[] transform = new double[]{1,0,0,1,tx,ty};
        multMatrix(transform) ;
    }

    public void rotate(double angle){
        double cosin = Math.cos(angle), sinus = Math.sin(angle) ;
        double[] transform = new double[]{cosin,sinus,-sinus,cosin,0,0};
        multMatrix(transform) ;
    }

    public PSPoint transform(double x, double y){
        double a = matrix[0], b = matrix[1], c = matrix[2], d = matrix[3], tx = matrix[4], ty = matrix[5] ;
        double newX = a*x + c*y + tx ;
        double newY = b*x + d*y + ty ;
        return new PSPoint(newX,newY) ;
    }

    public PSPoint iTransform(double x, double y){
        TransformMatrix iMatrix = getInverseMatrix() ;
        if(iMatrix == null){
            return null ;
        }
        return iMatrix.transform(x,y) ;
    }

}