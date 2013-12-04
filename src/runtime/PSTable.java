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

    public int add(PSObject psObject){
        table.add(psObject);
        return table.size()-1;
    }

    public int getIndex(PSObject psObject){
        for (int i=0;i<table.size();i++){
            if(table.get(i).equals(psObject)) return i;
        }
        return -1;
    }

    public boolean contains(PSObject psObject){
        return table.contains(psObject);
    }

    public PSObject get(int index){
        return table.get(index);
    }

    public String toString(){
        return table.toString();
    }

    public PSTable clone(){
        PSTable psTable = new PSTable();
        for(PSObject o: table){
            psTable.add(o.clone());
        }
        return psTable;
    }
}
