import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

public class RuntimeTest {

    ArrayList<Object> table = new ArrayList<Object>();
    Stack<Object> operandStack = new Stack<Object>();

    /*
     * As yet save snapshot to operandStack
     */
    private void save() {
        operandStack.push(table.clone());
    }

    /*
    * As yet get snapshot from operandStack
    */
    private boolean restore() {
        Object snapshot = operandStack.pop();
        //Here must be checking for type of snapshot - it must be arraylist
        //System.out.println(snapshot.getClass());
        // Must check if there is common Composite object created after save in operand stack and table(localVM)
        // Now check if there is common object in operand stack and table(localVM) at all
        Iterator<Object> iterator = operandStack.iterator();
        while (iterator.hasNext()) {
            Object current = iterator.next();
            if (table.contains(current))
                return false;
        }
        table = (ArrayList<Object>) snapshot;
        return true;
    }

    class PSString {
        String s = null;

        // Generate random string and add it into local VM
        PSString() {
            byte[] temp = new byte[10];
            Random generator = new java.util.Random();
            generator.nextBytes(temp);
            s = new String(temp, 0);
            table.add(s);
        }

        @Override
        public boolean equals(Object object) {
            boolean isEqual = false;
            if (object != null && object.hashCode() == this.hashCode()) {
                isEqual = (this.s.equals(object));
            }
            return isEqual;
        }

        @Override
        public int hashCode() {
            return this.s.hashCode();
        }
    }

    class PSNumber {
        int i = 0;

        // Generate random int and add it into local VM
        PSNumber() {
            Random rand = new Random();
            i = rand.nextInt();
            table.add(i);
        }

        @Override
        public boolean equals(Object object) {
            boolean isEqual = false;
            if (object != null && object.hashCode() == this.hashCode()) {
                isEqual = (this.i == ((Integer) object));
            }
            return isEqual;
        }

        @Override
        public int hashCode() {
            return this.i;
        }
    }

    class PSDictionary {
        PSDictionary() {
        }
    }

    @Test
    /*
     * Check if table repair after save only without any problem
     */
    public void saveRestoreTest1() {
        PSNumber numb1 = new PSNumber();
        save();
        PSNumber numb2 = new PSNumber();
        Assert.assertTrue(restore());
    }

    @Test
    /*
     * Check if table doesn't repair
     * after save when we have common object in operand stack and table(localVM)
     */
    public void saveRestoreTest2() {
        operandStack.push(new PSNumber());
        save();
        Assert.assertFalse(restore());
    }
}