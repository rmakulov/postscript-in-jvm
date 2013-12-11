package runtime;

import org.junit.Assert;
import org.junit.Test;
import psObjects.PSObject;
import psObjects.composite.PSArray;
import psObjects.composite.PSDictionary;
import psObjects.composite.PSString;
import psObjects.simple.PSInteger;

/**
 * Created by 1 on 07.12.13.
 */
public class Tests {
    Runtime runtime = new Runtime();

    @Test
    /*
     * Check if table repair after save only without any problem
     */
    public void saveRestoreTest1() {
        PSInteger numb1 = PSInteger.initInteger();
        runtime.addToLocalVM(numb1);
        runtime.save();
        PSInteger numb2 = PSInteger.initInteger();
        runtime.addToLocalVM(numb2);
        Assert.assertTrue(runtime.restore());
    }

    @Test
    /*
     * Check if table doesn't repair
     * after save when we have common object in operand stack and table(localVM)
     */
    public void saveRestoreTest2() {
        runtime.pushToOperandStack(PSInteger.initInteger());
        runtime.save();
        PSString str = PSString.initString();
        runtime.pushToOperandStack(str);
        runtime.addToLocalVM(str);
        runtime.exchangeTopOfOperandStack();
        Assert.assertFalse(runtime.restore());
    }

    @Test
    /*
     * Check if after save and change array element, "restore" repair original value in source array
     */
    public void saveRestore3() {
        PSArray psArray = PSArray.initArray(5);
        int arrIndex = runtime.addToLocalVM(psArray);
        //PSArray origin = psArray.clone();
        runtime.save();
        //psArray.setValue(1, PSInteger.initInteger());
        runtime.setValueArrayAtIndex(arrIndex, 1, PSInteger.initInteger());
        runtime.restore();
        Assert.assertArrayEquals(psArray.getArray(), ((PSArray) runtime.getPSObjectFromLocalVM(arrIndex)).getArray());
    }

    @Test
    /*
     * Check if after get subarray and change it element, "restore" repair original value in source array
     */
    public void saveRestore4() {
        PSArray psArray = PSArray.initArray(5);
        int arrIndex = runtime.addToLocalVM(psArray);
        int subArrIndex = runtime.getArrayInterval(arrIndex,1,3);
        runtime.save();
        runtime.setValueArrayAtIndex(subArrIndex, 1, PSInteger.initInteger());
        runtime.restore();
        Assert.assertArrayEquals(psArray.getArray(), ((PSArray) runtime.getPSObjectFromLocalVM(arrIndex)).getArray());
    }

    @Test
    /*
     *   checks put and get to dictionary
     */
    public void saveRestore5(){
        PSDictionary dict = PSDictionary.initDict(5);
        PSObject key = new PSString("Login");
        PSObject value = new PSString("Password");
        dict = dict.put(key,value);
        PSObject expected = runtime.getValueAtDictionary(dict,key);
        Assert.assertEquals(expected,value);
    }

    @Test
    /*
     * copy one dict to another and check one pair of key, value
     */
    public void saveRestore6(){
        PSDictionary dict = PSDictionary.initDict(5);
        PSDictionary dict2 = PSDictionary.initDict(3);
        PSObject key = new PSString("Login");
        PSObject value = new PSString("Password");
        dict = dict.put(key,value);
        //runtime.pushToDictionaryStack(dict);
        //runtime.pushToDictionaryStack(dict2);
        dict = dict.copy(dict2);
        PSObject expected = runtime.getValueAtDictionary(dict,key);
        Assert.assertEquals(expected,value);
    }


}
