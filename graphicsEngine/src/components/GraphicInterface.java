package components;

import interpreter.Interpreter;
import operators.customs.*;
import psObjects.PSObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by User on 20/5/2015.
 */
public class GraphicInterface {
    StringBuilder psProgramStringBuilder = new StringBuilder();
    ArrayList<PSComponent> mainComponents=new ArrayList<PSComponent>();

    public GraphicInterface() {
        psProgramStringBuilder.append("(graphicsEngine/basics/glib.ps) (r) file run");
    }

    public void add(PSComponent component) {
        mainComponents.add(component);
    }

    public void finishConstruction() {
            for(PSComponent component:mainComponents){
                psProgramStringBuilder.append("\n").append(component.getGeneratedString());
            }
            psProgramStringBuilder.append("\nrepaintAll\nshowpage");
    }

    public void run() {
        try {
            Interpreter.instance.setCustomOperators(getCustomsOperators());
            Interpreter.instance.run(psProgramStringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<PSObject> getCustomsOperators() {
        ArrayList<PSObject> entries = new ArrayList<PSObject>();
        entries.add(new PSObject(MouseEventOp.instance));
        entries.add(new PSObject(KeyEventOp.instance));
        entries.add(new PSObject(InitOp.instance));
        entries.add(new PSObject(GetColorOp.instance));
        entries.add(new PSObject(DebugOp.instance));
        entries.add(new PSObject(PrintOp.instance));
        entries.add(new PSObject(CursorOp.instance));
        entries.add(new PSObject(NewThreadOp.instance));
        entries.add(new PSObject(RepaintMainContextOp.instance));
        entries.add(new PSObject(RepaintOp.instance));
        return entries;
    }

}
