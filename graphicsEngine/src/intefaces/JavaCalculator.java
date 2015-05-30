package intefaces;

import components.*;
import listeners.PSEvent;
import listeners.PSListener;
import runtime.*;
import runtime.events.EventType;

import static runtime.events.EventType.CLICK;

/**
 * Created by Rustam on 28.05.2015.
 */
public class JavaCalculator {

    public static void main(String[] args) {
        GraphicInterface graphicInterface = new GraphicInterface();

        PSComponent label1 = new PSLabelField(250, 750, 250, 50, "Welcome!");
        PSComponent label2 = new PSLabelField(150, 700, 250, 50, "You can calculate here.");
        PSComponent calc    = new PSWindow(100, 100, 300, 300, "calculator");

        final PSTextField numberField = new PSTextField(155, 320, 180, 30);

        PSComponent b0     = new PSButton(150, 150, 40, 30, "0");
        PSComponent point  = new PSButton(200, 150, 40, 30, ".");
        PSComponent equal  = new PSButton(250, 150, 40, 30, "=");
        PSComponent b1     = new PSButton(150, 190, 40, 30, "1");
        PSComponent b2     = new PSButton(200, 190, 40, 30, "2");
        PSComponent b3     = new PSButton(250, 190, 40, 30, "3");
        PSComponent b4     = new PSButton(150, 230, 40, 30, "4");
        PSComponent b5     = new PSButton(200, 230, 40, 30, "5");
        PSComponent b6     = new PSButton(250, 230, 40, 30, "6");
        PSComponent b7     = new PSButton(150, 270, 40, 30, "7");
        PSComponent b8     = new PSButton(200, 270, 40, 30, "8");
        PSComponent b9     = new PSButton(250, 270, 40, 30, "9");
        PSComponent plus   = new PSButton(300, 150, 40, 30, "+");
        PSComponent minus  = new PSButton(300, 190, 40, 30, "-");
        PSComponent mult   = new PSButton(300, 230, 40, 30, "*");
        PSComponent divis  = new PSButton(300, 270, 40, 30, "/");
        PSComponent clear  = new PSButton(350, 320, 40, 30, "c");

        graphicInterface.add(label1);
        graphicInterface.add(label2);
        graphicInterface.add(calc);

        calc.add(numberField);
        calc.add(b0);
        calc.add(point);
        calc.add(equal);
        calc.add(b1);
        calc.add(b2);
        calc.add(b3);
        calc.add(b4);
        calc.add(b5);
        calc.add(b6);
        calc.add(b7);
        calc.add(b8);
        calc.add(b9);
        calc.add(plus);
        calc.add(minus);
        calc.add(mult);
        calc.add(divis);
        calc.add(clear);

        b0.addListener(CLICK,new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                numberField.setText(numberField.getText()+"0");

            }
        });
        clear.addListener(CLICK,new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                numberField.setText("");
            }
        });

        graphicInterface.setVisible(true);

    }
}