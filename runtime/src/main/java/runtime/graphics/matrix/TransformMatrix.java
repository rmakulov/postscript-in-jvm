package runtime.graphics.matrix;

/**
 * Created by user on 15.03.14.
 */

import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.ArrayElement;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSNumber;
import psObjects.values.simple.numbers.PSReal;
import runtime.graphics.figures.PSPoint;

import java.awt.geom.AffineTransform;

//todo check in methods if PSObject is array
public class TransformMatrix implements Cloneable {
    private PSObject matrix = null;

    public void setMatrix(PSObject matrix) {
        this.matrix = matrix;
    }

    public TransformMatrix() {// [a b c d t_x t_y ]
        double scale = 1;//4.0/3;
        //matrix = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 0.0};//or -631
        ArrayElement[] arr = new ArrayElement[]{new ArrayElement(new PSObject(new PSReal(scale))),
                new ArrayElement(new PSObject(new PSReal(0.0))),
                new ArrayElement(new PSObject(new PSReal(0.0))),
                new ArrayElement(new PSObject(new PSReal(scale))),
                new ArrayElement(new PSObject(new PSReal(0.0))),
                new ArrayElement(new PSObject(new PSReal(0.0)))};
        matrix = new PSObject(new PSArray(arr), Attribute.TreatAs.LITERAL);
    }

    public TransformMatrix(PSObject arrObj) {
        if (arrObj == null || arrObj.getType() != Type.ARRAY || arrObj.getValue() == null || ((PSArray) arrObj.getValue()).length() != 6) {
            try {
                throw new Exception("wrong args for transform matrix constructor");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        matrix = arrObj;
    }

    public TransformMatrix(double[] arr) {
        PSObject o1 = new PSObject(new PSReal(arr[0]));
        PSObject o2 = new PSObject(new PSReal(arr[1]));
        PSObject o3 = new PSObject(new PSReal(arr[2]));
        PSObject o4 = new PSObject(new PSReal(arr[3]));
        PSObject o5 = new PSObject(new PSReal(arr[4]));
        PSObject o6 = new PSObject(new PSReal(arr[5]));
        matrix = new PSObject(new PSArray(new PSObject[]{o1, o2, o3, o4, o5, o6}), Attribute.TreatAs.LITERAL);
    }

    public double[] getDoubleArray() {
        PSArray psArray = (PSArray) matrix.getValue();

        PSObject[] objArray = psArray.getArray();
        return new double[]{((PSNumber) objArray[0].getValue()).getRealValue(),
                ((PSNumber) objArray[1].getValue()).getRealValue(),
                ((PSNumber) objArray[2].getValue()).getRealValue(),
                ((PSNumber) objArray[3].getValue()).getRealValue(),
                ((PSNumber) objArray[4].getValue()).getRealValue(),
                ((PSNumber) objArray[5].getValue()).getRealValue(),
        };
    }


    @Override
    public TransformMatrix clone() {
        // todo check if clone is needed
        return new TransformMatrix(matrix);
    }


    public TransformMatrix getInverseMatrix() {
        double[] doubleMatrix = getDoubleArray();
        double a = doubleMatrix[0], b = doubleMatrix[1], c = doubleMatrix[2];
        double d = doubleMatrix[3], tx = doubleMatrix[4], ty = doubleMatrix[5];
        double det = a * d - b * c;
        if (det == 0) {
            return null;
        }
        //A^-1 =1/det(a)*(~(A^T))
        //A^T transpose
        //~A  алгебраическое дополнение
        double[] newArr = new double[]{d / det,
                -b / det,
                -c / det,
                a / det,
                -(d * tx - c * ty) / det,
                (b * tx - a * ty) / det};
        TransformMatrix inverseMatrix = new TransformMatrix(newArr);
        return inverseMatrix;
    }

    public TransformMatrix multMatrix(PSObject transf) {
        double[] doubleMatrix = getDoubleArray();
        double a = doubleMatrix[0], b = doubleMatrix[1], c = doubleMatrix[2];
        double d = doubleMatrix[3], tx = doubleMatrix[4], ty = doubleMatrix[5];

        PSObject[] objTransf = ((PSArray) transf.getValue()).getArray();
        double a1 = ((PSNumber) objTransf[0].getValue()).getRealValue();
        double b1 = ((PSNumber) objTransf[1].getValue()).getRealValue();
        double c1 = ((PSNumber) objTransf[2].getValue()).getRealValue();
        double d1 = ((PSNumber) objTransf[3].getValue()).getRealValue();
        double tx1 = ((PSNumber) objTransf[4].getValue()).getRealValue();
        double ty1 = ((PSNumber) objTransf[5].getValue()).getRealValue();

        return new TransformMatrix(new double[]
                {a * a1 + b * c1,
                        a * b1 + b * d1,
                        a1 * c + c1 * d,
                        b1 * c + d * d1,
                        a1 * tx + tx1 + c1 * ty,
                        b1 * tx + d1 * ty + ty1
                });
    }

    public void scale(double sx, double sy) {
        double[] transform = new double[]{sx, 0, 0, sy, 0, 0};
        setMatrix((new TransformMatrix(transform)).multMatrix(matrix).getMatrix());
    }

    public void translate(double tx, double ty) {
        double[] transform = new double[]{1, 0, 0, 1, tx, ty};
        setMatrix((new TransformMatrix(transform)).multMatrix(matrix).getMatrix());
    }

    public void rotate(double angle) {
        double cos = Math.cos(angle), sin = Math.sin(angle);
        double[] transform = new double[]{cos, sin, -sin, cos, 0, 0};
        setMatrix((new TransformMatrix(transform)).multMatrix(matrix).getMatrix());
    }

    public PSPoint transform(double x, double y) {
        double[] doubleMatrix = getDoubleArray();
        double a = doubleMatrix[0], b = doubleMatrix[1], c = doubleMatrix[2];
        double d = doubleMatrix[3], tx = doubleMatrix[4], ty = doubleMatrix[5];

        double newX = a * x + c * y + tx;
        double newY = b * x + d * y + ty;
        return new PSPoint(newX, newY);
    }

    public PSPoint transform(PSPoint point) {
        return transform(point.getX(), point.getY());
    }

    public PSPoint iTransform(double x, double y) {
        TransformMatrix iMatrix = getInverseMatrix();
        if (iMatrix == null) {
            return null;
        }
        return iMatrix.transform(x, y);
    }

    public PSPoint iTransform(PSPoint point) {
        return iTransform(point.getX(), point.getY());
    }

    public PSObject getMatrix() {
        return matrix;
    }


    public double getXScale() {
        double[] doubleMatrix = getDoubleArray();
        double a = doubleMatrix[0], b = doubleMatrix[1];
        return Math.sqrt(a * a + b * b);
    }

    public double getYScale() {
        double[] doubleMatrix = getDoubleArray();
        double c = doubleMatrix[2], d = doubleMatrix[3];
        return Math.sqrt(c * c + d * d);
    }

    //int degrees
    public double getRotateAngle() {
        double[] doubleMatrix = getDoubleArray();
//        double a = doubleMatrix[0], b = doubleMatrix[1], c = doubleMatrix[2];
//        double d = doubleMatrix[3], tx = doubleMatrix[4], ty = doubleMatrix[5];
//
//        double newX = a ;
//        double newY = b;

        double a = doubleMatrix[0], b = doubleMatrix[1];


        double y = b;
        double x = a;
        return Math.atan2(y, x) * 180 / Math.PI;
    }

    public AffineTransform toAffineTransform() {
        double[] arr = getDoubleArray();
        return new AffineTransform(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
    }

    public AffineTransform toAffineTransform1() {
        double[] arr = getDoubleArray();
        return new AffineTransform(arr[0], arr[2], arr[1], arr[3], arr[4], arr[5]);
    }

    public static PSArray getIdentityMatrix() {
        PSObject one = new PSObject(new PSInteger(1));
        PSObject zero = new PSObject(new PSInteger(0));
        return new PSArray(new PSObject[]{one, zero, zero, one, zero, zero});
    }

    public static void main(String[] args) {
        TransformMatrix tm = new TransformMatrix();
        tm.scale(-1,1);
        tm.rotate(3*Math.PI/4);
        //tm.translate(24,25);
        System.out.println(tm.getRotateAngle());

    }
}
