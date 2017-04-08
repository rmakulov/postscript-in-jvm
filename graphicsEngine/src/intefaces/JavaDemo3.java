package intefaces;

import components.*;

/**
 * Created by 1 on 14.03.2017.
 */
public class JavaDemo3 extends GraphicInterface {
    public static void main(String[] args) {
        GraphicInterface graphicInterface = new GraphicInterface();
        PSComponent w1 = new PSWindow(400, 400, 200, 180, "numerating");
        PSComponent cb1 = new PSCheckBox(430, 470, 16, "second");
        PSComponent cb2 = new PSCheckBox(430, 510, 16, "first");
        PSComponent cb3 = new PSCheckBox(430, 430, 16, "third");
        w1.add(cb1, cb2, cb3);

        PSComponent w2 = new PSWindow(100, 100, 200, 150, "close button");
        PSComponent b1 = new PSButton(150, 150, 100, 40, "close");
        w2.add(b1);

        PSComponent w3 = new PSWindow(300, 230, 270, 220, "List box");
        PSComponent lb1 = new PSListBox(320, 300, 200, 90,
                new String[]{"Sekatski", "Martin Gardner", "Evdokimova"}, 1);
        w3.add(lb1);

        PSComponent w4 = new PSWindow(100, 430, 380, 270, "Input");
        PSComponent lf1 = new PSLabelField(125, 625, 100, 35, "Type yor message, please!");
        PSComponent tf1 = new PSTextField(110, 545, 350, 30);
        PSComponent b2 = new PSButton(235, 465, 100, 40, "send");
        w4.add(lf1, tf1, b2);

        graphicInterface.add(w1, w2, w3, w4);

        graphicInterface.setVisible(true);



    }
}
