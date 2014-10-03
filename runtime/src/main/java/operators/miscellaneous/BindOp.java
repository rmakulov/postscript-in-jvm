package operators.miscellaneous;

import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

import java.util.ArrayList;

/**
 * Created by 1 on 19.03.14.
 */
public class BindOp extends Operator {
    public static final BindOp instance = new BindOp();

    protected BindOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject o = runtime.popFromOperandStack();
        if (!o.isProc() && !o.isBytecode()) {
            fail();
            runtime.pushToOperandStack(o);
            return;
        }
        if (o.isProc()) {
            PSArray psArr = (PSArray) o.getValue();
            ArrayList<PSObject> resArray = new ArrayList<PSObject>();
            for (PSObject innerObj : psArr.getArray()) {
                if (innerObj.getType() == Type.NAME && innerObj.treatAs() == Attribute.TreatAs.EXECUTABLE) {
                    PSObject value = runtime.search(innerObj);
                    if (value != null && value.getType() == Type.OPERATOR) {
                        resArray.add(value);
                    } else {
                        resArray.add(innerObj);
                    }
                } else {
                    resArray.add(innerObj);
                }
            }
            PSArray result = new PSArray(resArray);
            o.setValue(result);
            runtime.pushToOperandStack(o);
        } else {
            runtime.pushToOperandStack(o);
/*            try {
                String strValue= ((PSBytecode) o.getValue()).getStrValue();
                Class c = DynamicClassLoader.instance.loadClass(strValue);
                String name = BytecodeGenerator.operatorIndexesName;
                ArrayList<Integer> arrayList = (ArrayList<Integer>) c.getField(name).get(new ArrayList<Integer>());
                for(Integer i: arrayList){
                    //todo replace those methods with operator invocations

                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }*/

        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("bind");
    }
}
