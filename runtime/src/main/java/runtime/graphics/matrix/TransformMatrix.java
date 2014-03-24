package runtime.graphics.matrix;

/**
 * Created by user on 15.03.14.
 */

import runtime.graphics.figures.PSPoint;

import java.awt.geom.AffineTransform;

public class TransformMatrix implements Cloneable {
    private double[] matrix;

    public TransformMatrix() {// [a b c d t_x t_y ]
        double scale = 1;//4.0/3;
        //matrix = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 0.0};//or -631
        matrix = new double[]{scale, 0.0, 0.0, scale, 0.0, 0.0};//or -631
    }

    @Override
    public TransformMatrix clone() {
        return new TransformMatrix(new double[]{matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]});
    }

    public TransformMatrix(double[] arr) {
        matrix = arr;
    }

    public TransformMatrix(TransformMatrix tm) {
        for (int i = 0; i < 6; i++) {
            matrix[i] = tm.getMatrix()[i];
        }
    }

    public TransformMatrix getInverseMatrix() {
        double a = matrix[0], b = matrix[1], c = matrix[2], d = matrix[3], tx = matrix[4], ty = matrix[5];
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

    public TransformMatrix multMatrix(double[] transf) {
        double a = matrix[0], b = matrix[1], c = matrix[2], d = matrix[3], tx = matrix[4], ty = matrix[5];
        double a1 = transf[0], b1 = transf[1], c1 = transf[2], d1 = transf[3], tx1 = transf[4], ty1 = transf[5];
        return new TransformMatrix(new double[]{a * a1 + b * c1,
                a * b1 + b * d1,
                a1 * c + c1 * d,
                b1 * c + d * d1,
                a1 * tx + tx1 + c1 * ty,
                b1 * tx + d1 * ty + ty1});

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
        double a = matrix[0], b = matrix[1], c = matrix[2], d = matrix[3], tx = matrix[4], ty = matrix[5];
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

    public double[] getMatrix() {
        return matrix;
    }

    public AffineTransform getAffineTransform() {
        return new AffineTransform(new double[]{matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]});
    }

    public double getXScale() {
        return Math.sqrt(matrix[0] * matrix[0] + matrix[1] * matrix[1]);
    }

    public double getYScale() {
        return Math.sqrt(matrix[2] * matrix[2] + matrix[3] * matrix[3]);
    }

    //int degrees
    public double getRotateAngle() {
        double xScale = getXScale();
        double y = -matrix[2] / xScale;
        double x = matrix[0] / xScale;
        return Math.atan2(y, x) * 180 / Math.PI;

    }
}
