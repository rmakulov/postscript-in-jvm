package runtime;

import psObjects.PSObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 03.12.13
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
public class PSTable implements Cloneable {
    private ArrayList<PSObject> table = new ArrayList<PSObject>();

    public PSTable() {
    }

    public PSTable(ArrayList<PSObject> table) {
        this.table = table;
    }

    public void add(PSObject psObject){
        table.add(psObject);
    }

    public boolean contains(PSObject psObject){
        return table.contains(psObject);
    }

 /*   public PSTable clone(){
        return new PSTable((ArrayList<PSObject>) table.clone());
    }*/

    public String toString(){
        return table.toString();
    }

    public PSTable clone(){
       // PSObject[] psObjects = (PSObject[])stack.toArray();
        PSTable psTable = new PSTable();
        for(PSObject o: table){
            psTable.add(o.clone());
        }
        return psTable;
    }
}
