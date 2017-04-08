package intefaces;

import components.*;
import listeners.PSEvent;
import listeners.PSListener;
import operators.RepaintOp;
import operators.coordinatSystemAndMatrix.RotateOp;
import operators.coordinatSystemAndMatrix.ScaleOp;
import operators.coordinatSystemAndMatrix.TranslateOp;
import runtime.Runtime;

import java.awt.*;

import static runtime.events.EventType.CLICK;
import static runtime.events.EventType.RELEASE;

/**
 * Created by 1 on 13.03.2017.
 */
public class JavaDemo2 extends GraphicInterface {
    public static void translate(double x, double y){
        TranslateOp.instance.interpret(Runtime.getInstance().getMainContext(), x, y);
        RepaintOp.instance.interpret(Runtime.getInstance().getMainContext());
    }

    public static void scale(double sx, double sy){
        ScaleOp.instance.interpret(Runtime.getInstance().getMainContext(), sx, sy);
        RepaintOp.instance.interpret(Runtime.getInstance().getMainContext());
    }

    public static void rotate(double angle){
        RotateOp.instance.interpret(Runtime.getInstance().getMainContext(), angle);
        RepaintOp.instance.interpret(Runtime.getInstance().getMainContext());
    }

    public static void clockwise(){
        TranslateOp.instance.interpret(Runtime.getInstance().getMainContext(), 651, 0);
        ScaleOp.instance.interpret(Runtime.getInstance().getMainContext(), -1, 1);
        RepaintOp.instance.interpret(Runtime.getInstance().getMainContext());
    }

    public static void anticlockwise(){
        TranslateOp.instance.interpret(Runtime.getInstance().getMainContext(), 0, 843);
        ScaleOp.instance.interpret(Runtime.getInstance().getMainContext(), 1, -1);
        RepaintOp.instance.interpret(Runtime.getInstance().getMainContext());
    }

    public static void invert(GraphicInterface gi){
        for(PSComponent component : gi.getMainComponents()){
            invertLabel(component);
        }
        RepaintOp.instance.interpret(Runtime.getInstance().getMainContext());
    }

    private static void invertLabel(PSComponent start) {
        String label = start.getLabel();
        start.setLabel(invertString(label));
        for(PSComponent component : start.getChildren()){
            invertLabel(component);
        }
    }

    private static String invertString(String label) {
        if(label.length() == 0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = label.length() - 1; i >= 0 ; i--) {
            sb.append(label.charAt(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        final GraphicInterface graphicInterface = new GraphicInterface();
        PSComponent lf1 = new PSLabelField(250, 750, 250, 50, "Hello!");
        PSComponent lf2 = new PSLabelField(115, 700, 250, 50, "Try various effects");
        //<</CLICK [{pop quit}[]] >>
        PSButton b1 = new PSButton(50, 50, 120, 40, "close");
        PSComponent lf3 = new PSLabelField(280, 610, 250, 40, "scales");
        //<</CLICK [{pop 1.1 1.1 scale  repaintAll}[]] >>
        PSButton b2 = new PSButton(265, 550, 50, 40, "+");
        PSButton b3 = new PSButton(340, 550, 50, 40, "-");

        PSComponent lf4 = new PSLabelField(460, 510, 250, 40, "rotates");
        PSButton b4 = new PSButton(430, 450, 200, 40, "clockwise");
        PSButton b5 = new PSButton(430, 380, 200, 40, "anticlockwise");

        PSComponent lf5 = new PSLabelField(50, 510, 250, 40, "translates");
        PSButton b6 = new PSButton(30, 450, 90, 40, "left");//<</CLICK [{pop -50  0 translate  repaintAll}[]] >>
        PSButton b7 = new PSButton(140, 450, 90, 40, "right");
        PSButton b8 = new PSButton(30, 385, 90, 40, "up");  //<</CLICK [{pop  0  50 translate  repaintAll}[]] >>
        PSButton b9 = new PSButton(140, 385, 90, 40, "down");//<</CLICK [{pop  0 -50 translate  repaintAll}[]] >>

        PSComponent lf6 = new PSLabelField(50, 260, 250, 40, "reflections");
        PSComponent cb1 = new PSCheckBox(65, 215, 16, "horizontal");//<</CLICK [{pop 651 0 translate -1 1 scale repaintAll}[]] >>
        PSComponent cb2 = new PSCheckBox(65, 175, 16, "vertical");//<</CLICK [{pop 0 843 translate 1 -1 scale repaintAll}[]] >>

        PSComponent lf7 = new PSLabelField(460, 260, 250, 40, "invert labels");
        PSButton b10 = new PSButton(480, 185, 120, 40, "invert");

        graphicInterface.add(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10,
                cb1, cb2, lf1, lf2, lf3, lf4, lf5, lf6, lf7);


        PSButton.setColor(Color.BLUE, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10);

        b6.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                translate(-50, 0);
            }
        });
        b7.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                translate(50, 0);
            }
        });
        b8.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                translate(0, 50);
            }
        });
        b9.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                translate(0, -50);
            }
        });

        b2.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                scale(1.1, 1.1);
            }
        });
        b3.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                scale(0.909, 0.909);
            }
        });

        b4.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                rotate(-10);
            }
        });
        b5.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                rotate(10);
            }
        });

        cb1.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                clockwise();
            }
        });
        cb2.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                anticlockwise();
            }
        });

        b10.addListener(RELEASE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                invert(graphicInterface);
            }
        });

        graphicInterface.setVisible(true);


    }
}
