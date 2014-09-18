package runtime.graphics.matrix;

/**
 * Created by user on 15.03.14.
 */

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.ArrayElement;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.numbers.PSReal;
import runtime.graphics.figures.PSPoint;

//todo check in methods if PSObject is array
public class TransformMatrix implements Cloneable {
    private PSObject matrix;

    public TransformMatrix() {// [a b c d t_x t_y ]
        double scale = 1;//4.0/3;
        //matrix = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 0.0};//or -631
        ArrayElement[] arr = new ArrayElement[]{new ArrayElement(new PSObject(new PSReal(scale))),
                new ArrayElement(new PSObject(new PSReal(0.0))),
                new ArrayElement(new PSObject(new PSReal(0.0))),
                new ArrayElement(new PSObject(new PSReal(scale))),
                new ArrayElement(new PSObject(new PSReal(0.0))),
                new ArrayElement(new PSObject(new PSReal(0.0)))};
        matrix = new PSObject(new PSArray(arr));
    }

    public TransformMatrix(PSObject arrObj) {
        if (arrObj.getType() != Type.ARRAY) {
            return;
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
        matrix = new PSObject(new PSArray(new PSObject[]{o1, o2, o3, o4, o5, o6}));
    }

    private double[] getDoubleArray() {
        PSArray psArray = (PSArray) matrix.getValue();

        PSObject[] objArray = psArray.getArray();
        return new double[]{((PSReal) objArray[0].getValue()).getRealValue(),
                ((PSReal) objArray[1].getValue()).getRealValue(),
                ((PSReal) objArray[2].getValue()).getRealValue(),
                ((PSReal) objArray[3].getValue()).getRealValue(),
                ((PSReal) objArray[4].getValue()).getRealValue(),
                ((PSReal) objArray[5].getValue()).getRealValue(),
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
        double a1 = ((PSReal) objTransf[0].getValue()).getRealValue();
        double b1 = ((PSReal) objTransf[1].getValue()).getRealValue();
        double c1 = ((PSReal) objTransf[2].getValue()).getRealValue();
        double d1 = ((PSReal) objTransf[3].getValue()).getRealValue();
        double tx1 = ((PSReal) objTransf[4].getValue()).getRealValue();
        double ty1 = ((PSReal) objTransf[5].getValue()).getRealValue();

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
        matrix = (new TransformMatrix(transform)).multMatrix(matrix).getMatrix();
    }

    public void translate(double tx, double ty) {
        double[] transform = new double[]{1, 0, 0, 1, tx, ty};
        matrix = (new TransformMatrix(transform)).multMatrix(matrix).getMatrix();
    }

    public void rotate(double angle) {
        double cos = Math.cos(angle), sin = Math.sin(angle);
        double[] transform = new double[]{cos, sin, -sin, cos, 0, 0};
        matrix = (new TransformMatrix(transform)).multMatrix(matrix).getMatrix();
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
        double a = doubleMatrix[0], с = doubleMatrix[2];

        double xScale = getXScale();
        double y = -с / xScale;
        double x = a / xScale;
        return Math.atan2(y, x) * 180 / Math.PI;
    }
}
