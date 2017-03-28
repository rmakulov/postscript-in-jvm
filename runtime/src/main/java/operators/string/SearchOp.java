package operators.string;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import runtime.Context;

/*

string seek search   -->> post match pre true     (if found)
                          string false            (if not found)

looks for the first occurrence of the string seek within string
and returns the results of this search on the operand stack

(abbc) (ab) search  -->> (bc) (ab) () true
(abbc) (bb) search  -->> (c) (bb) (a) true
(abbc) (bc) search  -->> ( ) (bc) (ab) true
(abbc) (B) search   -->> (abbc) false

All three of these strings are substrings sharing
intervals of the value of the original string
* */

/**
 * Created by user on 5/21/15.
 */
public class SearchOp extends Operator {
    public static final SearchOp instance = new SearchOp();

    protected SearchOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) return;
        PSObject oSeek = context.popFromOperandStack();
        PSObject oStr  = context.popFromOperandStack();

        if (oSeek.getType() != Type.STRING || oStr.getType() != Type.STRING) {
            context.pushToOperandStack(oSeek);
            context.pushToOperandStack(oStr);
            fail();
            return;
        }

        PSString seekStr = (PSString) oSeek.getValue();
        String seek = seekStr.getString();

        PSString origStr = (PSString) oStr.getValue();
        String str  = origStr.getString();
        int seekLen = seek.length();
        int index = 0 ;
        boolean ans = false;
        int strLen = str.length();
        for(int i = 0; i < strLen; i ++){
            boolean localAns = true;
            for(int j = 0; j < seekLen && i + j < strLen; j ++ ){
                if(str.charAt(i+j) != seek.charAt(j)){
                    localAns = false;
                    break;
                }
            }
            if(localAns == true){
                index = i;
                ans = true;
                break;
            }
        }
        if(ans){
            context.pushToOperandStack(new PSObject(origStr.getInterval(index + seekLen, strLen - index - seekLen)));
            context.pushToOperandStack(new PSObject(origStr.getInterval(index, seekLen)));
            context.pushToOperandStack(new PSObject(origStr.getInterval(0, index)));
            context.pushToOperandStack(new PSObject(PSBoolean.TRUE));
        } else{
            context.pushToOperandStack(oStr);
            context.pushToOperandStack(new PSObject(PSBoolean.FALSE));
        }

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("search");
    }
}
