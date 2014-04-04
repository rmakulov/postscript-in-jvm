package procedures;

import operators.array.CloseSquareBracketOp;
import operators.common.CloseCurlyBraceOp;
import operators.dictionary.CloseChevronOp;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import runtime.Runtime;

import static psObjects.Attribute.TreatAs.EXECUTABLE;
import static psObjects.Attribute.TreatAs.LITERAL;

/**
 * Created by Дмитрий on 28.03.14.
 */
public abstract class Procedure {
    private String name = "Unnamed";
    private int procDepth = 0;
    protected Runtime runtime = Runtime.getInstance();

    protected Procedure(String name) {
        this.name = name;
    }

    protected Procedure() {
    }

    public abstract boolean hasNext();

    public void execNext() {
        if (!hasNext()) return;
        PSObject nextObject = next();
        if (nextObject == null) {
            return;
        }
        execObject(nextObject);
    }

    private void execObject(PSObject psObject) {
        if (psObject == null)
            return;
        switch (psObject.getType()) {
            case NAME:
                execName(psObject);
                break;

            //            todo: font_id execution
            //            case FONT_ID:
            //                break;
            case INTEGER:
            case REAL:
            case BOOLEAN:
            case DICTIONARY:
            case NULL:
            case GSTATE:
            case FILE:
            case SAVE:
                runtime.pushToOperandStack(psObject);
                break;
            case MARK:
                execMark(psObject);
                break;
            case OPERATOR:
                execOperator(psObject);
                break;
            case ARRAY:
            case PACKEDARRAY:
                execArray(psObject);
                break;
            case STRING:
                execString(psObject);
                break;
        }
    }

    private void execOperator(PSObject psObject) {
        if (procDepth > 0) {
            runtime.pushToOperandStack(psObject);
            return;
        }
        Operator operator = (Operator) psObject.getValue();
        operator.execute();
    }

    private void execString(PSObject psObject) {
        if (psObject.getType() != Type.STRING) return;
        if (procDepth > 0) {
            runtime.pushToOperandStack(psObject);
            return;
        }
        if (psObject.treatAs() == LITERAL) {
            runtime.pushToOperandStack(psObject);
        } else {
            runtime.pushToCallStack(new StringProcedure(psObject));
            runtime.pushToOperandStack(psObject);
        }
    }

    private void execArray(PSObject psObject) {
        if (psObject.getType() != Type.ARRAY || psObject.getType() != Type.PACKEDARRAY) return;
        if (procDepth > 0) {
            runtime.pushToOperandStack(psObject);
            return;
        }
        if (psObject.treatAs() == LITERAL) {
            runtime.pushToOperandStack(psObject);
        } else {
            runtime.pushToCallStack(new ArrayProcedure(psObject));
        }
    }

    private void execMark(PSObject psObject) {
        PSMark mark = (PSMark) psObject.getValue();
        runtime.pushToOperandStack(psObject);
        if (mark.equals(PSMark.CLOSE_CURLY_BRACE)) {
            procDepth--;
            if (procDepth == 0) {
                CloseCurlyBraceOp.instance.execute();
            }
        } else if (mark.equals(PSMark.OPEN_CURLY_BRACE)) {
            procDepth++;
        } else if (mark.equals(PSMark.CLOSE_SQUARE_BRACKET)) {
            if (procDepth == 0) {
                CloseSquareBracketOp.instance.execute();
            }
        } else if (mark.equals(PSMark.CLOSE_CHEVRON_BRACKET)) {
            if (procDepth == 0) {
                CloseChevronOp.instance.execute();
            }
        } else if (mark.equals(PSMark.OPEN_SQUARE_BRACKET)) {
            //OpenSquareBracketOp.instance.execute();
        } else if (mark.equals(PSMark.OPEN_CHEVRON_BRACKET)) {
            //OpenChevronOp.instance.execute();
        }
    }

    public void execName(PSObject psObject) {
        if (psObject.getType() != Type.NAME) return;
        if (procDepth > 0) {
            runtime.pushToOperandStack(psObject);
            return;
        }
        Attribute.TreatAs treatAs = psObject.treatAs();
        if (treatAs == LITERAL) {
            runtime.pushToOperandStack(psObject);
        } else {
            PSObject value = runtime.findValue(psObject);
            String procName = ((PSName) psObject.getValue()).getStrValue();
            while (value.getType() == Type.NAME && value.treatAs() == EXECUTABLE) {
                procName = procName + " -> " + ((PSName) value.getValue()).getStrValue();
                value = runtime.findValue(value);
            }
            if (value.isProc()) {
                runtime.pushToCallStack(new ArrayProcedure(procName, value));
                return;
            }

            if (value.getType() == Type.STRING && value.xcheck()) {
                runtime.pushToCallStack(new StringProcedure(procName, value));
                return;
            }

            if (value.getType() == Type.OPERATOR) {
                ((Operator) value.getValue()).execute();
                return;
            }
            //value cannot be a Mark
            runtime.pushToOperandStack(value);
        }
    }

    protected abstract PSObject next();

    public void procBreak() {
        // override in stoppedProcedure
    }

    public void procTerminate() {
        // override in stoppedProcedure
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public boolean isExitable() {
        return false;
    }
}
