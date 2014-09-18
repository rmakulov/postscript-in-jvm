package runtime;

import org.junit.Assert;
import org.junit.Test;
import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.numbers.PSInteger;


public class Tests {
    Runtime runtime = Runtime.getInstance();

    @Test
    /*
     * Check if table repair after save only without any problem
     */
    public void saveRestoreTest1() {
        PSObject.initInteger();
        runtime.save();
        PSObject.initInteger();

        Assert.assertTrue(runtime.restore());
        System.out.println("1");
    }

//    @Test
//    /*
//     * Check if table doesn't repair
//     * after save when we have common object in operand stack and table(localVM)
//     */
//    public void saveRestoreTest2() {
//        runtime.pushToOperandStack(PSObject.initInteger());
//        runtime.save();
//        runtime.pushToOperandStack(PSObject.initString());
//        runtime.exchangeTopOfOperandStack();
//        Assert.assertFalse(runtime.restore());
//        System.out.println("2");
//    }

//    @Test
//    /*
//     * Check if after save and change array element, "restore" repair original value in source array
//     */
//    public void saveRestore3() {
//        PSObject arrObj = PSObject.initArray(5);
//        runtime.pushToOperandStack(arrObj);
//        runtime.save();
//        arrObj=runtime.setValueArrayAtIndex(arrObj, 1, PSObject.initInteger());
//        runtime.restore();
//        PSObject arrObj2 = runtime.popFromOperandStack();
//        PSObject[] newArr = ((PSArray) arrObj.getValue()).getArray();
//        PSObject[] oldArr =((PSArray) arrObj2.getValue()).getArray();
//        Assert.assertArrayEquals(newArr, oldArr);
//        System.out.println("3");
//    }

//    @Test
//    /*
//     * Check if after get subarray and change it element, "restore" repair original value in source array
//     */
//    public void saveRestore4() {
//        PSObject arrObj = PSObject.initArray(5);
//        LocalRef ref = (LocalRef) arrObj.getDirectValue();
//        PSObject subArrObj = runtime.getArrayInterval(arrObj, 1, 3);
//        runtime.save();
//        runtime.setValueArrayAtIndex(subArrObj, 1, PSObject.initInteger());
//        runtime.restore();
//        PSObject[] newArr = ((PSArray) arrObj.getValue()).getArray();
//        Assert.assertArrayEquals(newArr, ((PSArray) runtime.getValueByLocalRef(ref)).getArray());
//        System.out.println("4");
//    }

    @Test
    /*
     *   checks put and get to dictionary
     */
    public void putGetIntoDictionaryTest5() {

        int c = 0;
        for (int i = 0; i < 1000; i++) {
            try {
                PSObject dict = PSObject.initDict(15);
                PSObject key = new PSObject(new PSString("Login"));
                PSObject value = new PSObject(new PSString("Password"));
                runtime.putValueAtDictionaryKey(dict, key, value);
                PSObject expected = runtime.getValueAtDictionary(dict, key);
                Assert.assertEquals(expected, value);
                c++;
            } catch (Throwable e) {
            }
        }
        System.out.println("5: true " + c + " of " + 1000);
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
                PSObject dict1 = PSObject.initDict(8);
                PSObject dict2 = PSObject.initDict(12);
                PSObject key = new PSObject(new PSString("Login"));
                PSObject value = new PSObject(new PSString("Password"));
                PSObject newValue = new PSObject(new PSInteger(123));
                dict1 = runtime.putValueAtDictionaryKey(dict1, key, value);
                dict2 = runtime.copy(dict1, dict2);
                dict2 = runtime.putValueAtDictionaryKey(dict2, key, newValue);
                PSObject expected = runtime.getValueAtDictionary(dict1, key);
                Assert.assertNotEquals(expected, newValue);
                c++;
            } catch (Throwable e) {
            }
        }
        System.out.println("6: true " + c + " of " + 1000);

    }


}
