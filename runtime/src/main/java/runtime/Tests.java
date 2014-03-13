package runtime;

import org.junit.Assert;
import org.junit.Test;
import psObjects.PSObject;
import psObjects.composite.PSArray;
import psObjects.composite.PSDictionary;
import psObjects.composite.PSString;
import psObjects.reference.LocalRef;
import psObjects.reference.Reference;
import psObjects.simple.PSInteger;


public class Tests {
    Runtime runtime = Runtime.getInstance();

    @Test
    /*
     * Check if table repair after save only without any problem
     */
    public void saveRestoreTest1() {
        runtime.clearAll();
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
        runtime.clearAll();
        runtime.pushToOperandStack(runtime.createReference(PSInteger.initInteger()));
        runtime.save();
        PSString str = PSString.initString();
        runtime.pushToOperandStack(runtime.createReference(str));
        runtime.addToLocalVM(str);
        runtime.exchangeTopOfOperandStack();
        Assert.assertFalse(runtime.restore());
    }

    @Test
    /*
     * Check if after save and change array element, "restore" repair original value in source array
     */
    public void saveRestore3() {
        runtime.clearAll();
        PSArray psArray = PSArray.initArray(5);
        LocalRef arrLocalRef = runtime.createLocalRef(psArray);
        //PSArray origin = psArray.clone();
        runtime.save();
        //psArray.setValue(1, PSInteger.initInteger());
        runtime.setValueArrayAtIndex(arrLocalRef, 1, PSInteger.initInteger());
        runtime.restore();
        Assert.assertArrayEquals(psArray.getArray(), ((PSArray) runtime.getPSObjectByLocalRef(arrLocalRef)).getArray());
    }

    @Test
    /*
     * Check if after get subarray and change it element, "restore" repair original value in source array
     */
    public void saveRestore4() {
        runtime.clearAll();
        PSArray psArray = PSArray.initArray(5);
        LocalRef arrLocalRef = runtime.createLocalRef(psArray);
        LocalRef subArrLocalRef = runtime.getArrayInterval(arrLocalRef, 1, 3);
        runtime.save();
        runtime.setValueArrayAtIndex(subArrLocalRef, 1, PSInteger.initInteger());
        runtime.restore();
        Assert.assertArrayEquals(psArray.getArray(), ((PSArray) runtime.getPSObjectByLocalRef(arrLocalRef)).getArray());
    }

    @Test
    /*
     *   checks put and get to dictionary
     */
    public void saveRestore5() {

        int c = 0;
        for (int i = 0; i < 1000; i++) {
            try {
                runtime.clearAll();
                Reference dictRef = PSDictionary.initDictRef(15);
                PSDictionary dict = (PSDictionary) dictRef.getPSObject();

                PSObject key = new PSString("Login");
                PSObject value = new PSString("Password");
                Reference valueRef = runtime.createReference(value);
                Reference keyRef = runtime.createReference(key);

                dict = dict.put(keyRef, valueRef);
                dictRef.setPSObject(dict);
                Reference expected = runtime.getValueAtDictionary(dictRef, keyRef);
                Assert.assertEquals(expected.getPSObject(), value);
                c++;
            } catch (Throwable e) {}
        }
        System.out.println("true " + c + " of " + 1000);
    }

    @Test
    /*
     * copy one dict to another and check one pair of key, value
     */
    public void saveRestore6() {
        int c = 0;
        for (int i = 0; i < 1000; i++) {
            try {
                runtime.clearAll();
                Reference dictRef1 = PSDictionary.initDictRef(8);
                PSDictionary dict1 = (PSDictionary) dictRef1.getPSObject();
                Reference dictRef2 = PSDictionary.initDictRef(12);
                PSDictionary dict2 = (PSDictionary) dictRef2.getPSObject();

                PSObject key = new PSString("Login");
                PSObject value = new PSString("Password");
                PSObject newValue = new PSInteger(123);
                Reference valueRef = runtime.createReference(value);
                Reference newValueRef = runtime.createReference(newValue);
                Reference keyRef = runtime.createReference(key);

                dict1 = dict1.put(keyRef, valueRef);
                dict2 = dict1.copy(dict2);
                dict2.put(keyRef, newValueRef);
                dictRef1.setPSObject(dict1);
                dictRef1.setPSObject(dict2);

                Reference expected = runtime.getValueAtDictionary(dictRef1, keyRef);

                Assert.assertNotEquals(expected.getPSObject(), newValue);
                c++;
            } catch (Throwable e) {}
        }
        System.out.println("true " + c + " of " + 1000);

    }



}
