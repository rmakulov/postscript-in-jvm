package psObjects.values.composite.dictionaries;

import operators.GlythAndFont.ShowOp;
import operators.GlythAndFont.ShowPageOp;
import operators.GlythAndFont.StringWidthOp;
import operators.arithmetic.RandOp;
import operators.arithmetic.RrandOp;
import operators.arithmetic.binary.*;
import operators.arithmetic.unary.*;
import operators.array.CloseSquareBracketOp;
import operators.array.OpenSquareBracketOp;
import operators.common.*;
import operators.control.*;
import operators.coordinatSystemAndMatrix.*;
import operators.dictionary.*;
import operators.file.StackOp;
import operators.graphicsState.*;
import operators.miscellaneous.BindOp;
import operators.operandStackManipulation.*;
import operators.painting.FillOp;
import operators.painting.StrokeOp;
import operators.pathConstruction.*;
import operators.relationBooleanBitwise.*;
import operators.typeAttributeConvertation.*;
import operators.virtualMemory.*;
import psObjects.PSObject;
import psObjects.values.simple.Operator;

import java.util.ArrayList;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class DefaultDicts {
    private static PSDictionary systemDict = null;
    private static PSDictionary userDict = null;
    private static PSDictionary globalDict = null;
    private static ArrayList<PSObject> entries = new ArrayList<PSObject>();


    public static PSDictionary getSystemDict() {

        if (systemDict == null) {
            addArithmeticOperators();
            addCommonOperators();
            addControlOperators();
            addCoordinateSystemAndMatrix();
            addDictionaryOperators();
            addGlythAndFontOperators();
            addGraphicsStateOperators();
            addMiscellaneousOperators();
            addOperandStackOperationOperators();
            addPaintingOperators();
            addPathConstructionOperators();
            addRelationBooleanOperators();
            addTypeAttributeOperators();
            addVirtualMemoryOperators();
            addArrayOperators();
            addFileOperators();
            systemDict = new PSDictionary(entries);
        }
        return systemDict;
    }


    public static PSDictionary getUserDict() {
        if (userDict == null) {
            userDict = new PSDictionary();
        }
        return userDict;
    }

    public static PSDictionary getGlobalDict() {
        if (globalDict == null) {
            globalDict = new PSDictionary();
        }
        return globalDict;
    }

    private static void addOperator(Operator op) {
        entries.add(new PSObject(op.getDefaultKeyName()));
        entries.add(new PSObject(op));
    }

    private static void addArithmeticOperators() {
        //binary
        addOperator(AddOp.instance);
        addOperator(SubOp.instance);
        addOperator(DivOp.instance);
        addOperator(IdivOp.instance);
        addOperator(ModOp.instance);
        addOperator(ExpOp.instance);
        addOperator(AtanOp.instance);
        addOperator(MulOp.instance);
        addOperator(AddOp.instance);
        addOperator(AddOp.instance);
        addOperator(AddOp.instance);
        addOperator(AddOp.instance);

        //unary
        addOperator(AbsOp.instance);
        addOperator(CeilingOp.instance);
        addOperator(CosOp.instance);
        addOperator(FloorOp.instance);
        addOperator(LnOp.instance);
        addOperator(LogOp.instance);
        addOperator(NegOp.instance);
        addOperator(RoundOp.instance);
        addOperator(SinOp.instance);
        addOperator(SqrtOp.instance);
        addOperator(SrandOp.instance); //todo operator is not ready
        addOperator(TruncateOp.instance);

        //without operands
        addOperator(RandOp.instance);
        addOperator(RrandOp.instance);  //todo operator is not ready
    }

    private static void addMiscellaneousOperators() {
        addOperator(BindOp.instance);
    }

    private static void addDictionaryOperators() {
        addOperator(BeginOp.instance);
        addOperator(EndOp.instance);
        addOperator(ClearDictStackOp.instance);
        addOperator(DefOp.instance);
        addOperator(OpenChevronOp.instance);
        addOperator(CloseChevronOp.instance);
    }

    private static void addVirtualMemoryOperators() {
        addOperator(CurrentGlobalOp.instance);
        addOperator(GcheckOp.instance);
        addOperator(RestoreOp.instance);
        addOperator(SaveOp.instance);
        addOperator(SetGlobalOp.instance);
    }

    private static void addTypeAttributeOperators() {
        addOperator(CviOp.instance);
        addOperator(CvlitOp.instance);
        addOperator(CvnOp.instance);
        addOperator(CvrOp.instance);
        addOperator(CvrsOp.instance);
        addOperator(CvsOp.instance);
        addOperator(CvxOp.instance);
        addOperator(ExecuteonlyOp.instance);
        addOperator(NoaccessOp.instance);
        addOperator(RcheckOp.instance);
        addOperator(RestoreOp.instance);
        addOperator(TypeOp.instance);
        addOperator(WcheckOp.instance);
        addOperator(XcheckOp.instance);
    }

    private static void addRelationBooleanOperators() {
        addOperator(AndOp.instance);
        addOperator(BitshiftOp.instance);
        addOperator(EqOp.instance);
        addOperator(FalseOp.instance);
        addOperator(GeOp.instance);
        addOperator(GtOp.instance);
        addOperator(LeOp.instance);
        addOperator(LtOp.instance);
        addOperator(NeOp.instance);
        addOperator(NotOp.instance);
        addOperator(OrOp.instance);
        addOperator(TrueOp.instance);
        addOperator(XorOp.instance);
    }

    private static void addPathConstructionOperators() {
        addOperator(ArcnOp.instance);
        addOperator(ArcOp.instance);
        addOperator(ClipPathOp.instance);
        addOperator(ClipOp.instance);
        addOperator(ClosePathOp.instance);
        addOperator(CurrentPointOp.instance);
        addOperator(LineToOp.instance);
        addOperator(MoveToOp.instance);
        addOperator(NewPathOp.instance);
        addOperator(PathBBoxOp.instance);
        addOperator(RLineToOp.instance);
        addOperator(RMoveToOp.instance);
    }

    private static void addPaintingOperators() {
        addOperator(FillOp.instance);
        addOperator(StrokeOp.instance);
    }

    private static void addOperandStackOperationOperators() {
        addOperator(ClearOp.instance);
        addOperator(DupOp.instance);
        addOperator(ExchOp.instance);
        addOperator(MarkOp.instance);
        addOperator(PopOp.instance);
    }

    private static void addGraphicsStateOperators() {
        addOperator(GRestoreOp.instance);
        addOperator(GRestoreAllOp.instance);
        addOperator(GSaveOp.instance);
        addOperator(SetGrayOp.instance);
        addOperator(SetHsbColorOp.instance);
        addOperator(SetLineCapOp.instance);
        addOperator(SetLineJoinOp.instance);
        addOperator(SetLineWidthOp.instance);
        addOperator(SetMiterLimitOp.instance);
        addOperator(SetRgbColorOp.instance);

    }

    private static void addGlythAndFontOperators() {
        addOperator(ShowOp.instance);
        addOperator(ShowPageOp.instance);
        addOperator(StringWidthOp.instance);
    }

    private static void addCoordinateSystemAndMatrix() {
        addOperator(ITransformOp.instance);
        addOperator(RotateOp.instance);
        addOperator(ScaleOp.instance);
        addOperator(TransformOp.instance);
        addOperator(TranslateOp.instance);
    }

    private static void addControlOperators() {
        addOperator(ExecOp.instance);
        addOperator(IfOp.instance);
        addOperator(IfElseOp.instance);
        addOperator(RepeatOp.instance);
        addOperator(ForOp.instance);
    }

    private static void addCommonOperators() {
        addOperator(AloadOp.instance);
        addOperator(ForAllOp.instance);
        addOperator(GetIntervalOp.instance);
        addOperator(GetOp.instance);
        addOperator(LengthOp.instance);
        addOperator(PutIntervalOp.instance);
        addOperator(PutOp.instance);
    }

    private static void addArrayOperators() {
        addOperator(CloseSquareBracketOp.instance);
        addOperator(OpenSquareBracketOp.instance);
    }

    private static void addFileOperators() {
        addOperator(StackOp.instance);
    }
}
