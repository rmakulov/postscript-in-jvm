package runtime;

import org.junit.Assert;
import org.junit.Test;
import psObjects.PSObject;
import psObjects.composite.CompositeObject;
import psObjects.composite.PSArray;
import psObjects.composite.PSString;
import psObjects.composite.Snapshot;
import psObjects.simple.PSInteger;

public class RuntimeTest {

    PSTable table = new PSTable();
    PSStack operandStack = new PSStack();

    /*
     * save snapshot to operandStack
     */
    private void save() {
        Snapshot snapshot = new Snapshot(table.clone(), operandStack.clone());
        operandStack.push(snapshot);
    }

    /*
    * getting snapshot from top of operandStack
    */
    private boolean restore() {
        PSObject psObject= operandStack.pop();
        if(!(psObject instanceof Snapshot)) return false;
        Snapshot snapshot = (Snapshot) psObject;
        PSStack savedOperandStack = snapshot.getOperandStack();
        for (PSObject current : operandStack) {
            //if operand stack contains reference to composite object which was created after saving, we can't restore
            boolean check = current instanceof CompositeObject;
            check = table.contains(current);
            check = !savedOperandStack.contains(current);
            if (current instanceof CompositeObject && table.contains(current) && !savedOperandStack.contains(current)) {
                return false;
            }
        }
        table = snapshot.getTable();
        return true;
    }


    @Test
    /*
     * Check if table repair after save only without any problem
     */
    public void saveRestoreTest1() {
        PSInteger numb1 = PSInteger.initInteger();
        table.add(numb1);
        save();
        PSInteger numb2 = PSInteger.initInteger();
        table.add(numb2);
        Assert.assertTrue(restore());
    }

    @Test
    /*
     * Check if table doesn't repair
     * after save when we have common object in operand stack and table(localVM)
     */
    public void saveRestoreTest2() {
        operandStack.push(PSInteger.initInteger());
        save();
        PSString str = PSString.initString();
        operandStack.push(str);
        table.add(str);
        operandStack.exch();
        Assert.assertFalse(restore());
    }

    @Test
    /*
     * Check if after get subarray and change it element, "restore" repair original value in source array
     */
    public void saveRestore4(){
        PSArray psArray =PSArray.initArray(5);
        table.add(psArray);
        PSArray origin = psArray.clone();
        PSArray subArray = psArray.getInterval(1,3);
        table.add(subArray);
        save();
        subArray.setValue(1,PSInteger.initInteger());
        restore();
        Assert.assertArrayEquals(origin.getArray(),psArray.getArray());
    }

    @Test
    /* TODO: doesn't work correctly clone of snapshots, implement abstract factory pattern
     * Check if after save and change array element, "restore" repair original value in source array
     */
    public void saveRestore3(){
        PSArray psArray = PSArray.initArray(5);
        table.add(psArray);
        PSArray origin = psArray.clone();
        table.add(psArray);
        save();
        psArray.setValue(1, PSInteger.initInteger());
        restore();
        //Assert.assertArrayEquals(origin.getArray(), psArray.getArray());
    }

}