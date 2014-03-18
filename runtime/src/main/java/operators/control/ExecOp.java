package operators.control;

import operators.array.OpenSquareBracketOp;
import operators.dictionary.CloseChevronOp;
import operators.dictionary.OpenChevronOp;
import operators.operandStackManipulation.MarkOp;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;

public class ExecOp extends Operator {
    public static final ExecOp instance = new ExecOp();

    protected ExecOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null)
            return;
        switch (psObject.getType()) {
            case NAME:
                Attribute.TreatAs treatAs = psObject.treatAs();
                if (treatAs == LITERAL) {
                    runtime.pushToOperandStack(psObject);
                } else {
                    runtime.pushToOperandStack(runtime.findValue(psObject));
                    execute();
                }
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
                PSMark mark = (PSMark) psObject.getValue();
                if (mark.equals(PSMark.OPEN_SQUARE_BRACKET)) {
                    OpenSquareBracketOp.instance.execute();
                } else if (mark.equals(PSMark.CLOSE_SQUARE_BRACKET)) {
                    MarkOp.instance.execute();
                } else if (mark.equals(PSMark.OPEN_CHEVRON_BRACKET)) {
                    OpenChevronOp.instance.execute();
                } else if (mark.equals(PSMark.CLOSE_CHEVRON_BRACKET)) {
                    CloseChevronOp.instance.execute();
                } else if (mark.equals(PSMark.OPEN_CURLY_BRACE)) {
                    //todo
                } else if (mark.equals(PSMark.CLOSE_CURLY_BRACE)) {
                    //todo
                }
                break;
            case OPERATOR:
                Operator operator = (Operator) psObject.getValue();
                operator.execute();
                break;
            case ARRAY:
            case PACKEDARRAY:
                if (psObject.treatAs() == LITERAL) {
                    runtime.pushToOperandStack(psObject);
                } else {
                    PSObject[] execArray = ((PSArray) psObject.getValue()).getArray();
                    for (PSObject o : execArray) {
                        runtime.pushToOperandStack(o);
                        execute();
                    }
                }
                break;
            case STRING:
                if (psObject.treatAs() == LITERAL) {
                    runtime.pushToOperandStack(psObject);
                } else {
                    // todo think about what it
                    runtime.pushToOperandStack(psObject);
                }
                break;
        }

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exec");
    }
}
