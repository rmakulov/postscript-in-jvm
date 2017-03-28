package runtime.graphics;

import java.awt.*;

/**
 * Created by Дмитрий on 19.03.14.
 */
public class GraphicsSettings {
    //    public static GraphicsSettings mainInstance = new GraphicsSettings();
    public Color color;
    public double lineWidth; // 1/72 inch
    public int lineJoin;
    public int lineCap;
    public double miterLimit;
    //public PSObject dash;
    public float[] dash;
    public float dashPhase = 0;
    public int flatness;

    public GraphicsSettings(Color color, double lineWidth, int lineJoin, int lineCap, double miterLimit, float[] dash, float dashPhase) {
        this.color = color;
        this.lineWidth = lineWidth;
        this.lineJoin = lineJoin;
        this.lineCap = lineCap;
        this.miterLimit = miterLimit;
        this.dash = dash;
        //setDash(dash);
        if (dash != null && dash.length == 0) {
            this.dash = null;
        }
        this.dashPhase = dashPhase;
        this.flatness = 1;
    }

    public GraphicsSettings() {
        lineWidth = GState.psUnitToPixel(1.0);
        color = Color.BLACK;
        lineJoin = 0;
        lineCap = 0;
        miterLimit = 10.0;
        //dash = new PSObject(new PSArray(new PSObject[]{new PSObject(new PSReal(0.))}));

    }
/*
    public void setDash(float[] arr){
        PSObject[] objArr = new PSObject[arr.length] ;
        for(int i = 0 ; i < arr.length ; i ++){
            objArr[i] = new PSObject(new PSReal( arr[i] )) ;
        }
        this.dash = new PSObject(new PSArray(objArr)) ;
    }

    public float[] getDash(){
        if(dash == null) return null ;
        PSObject[] arr = ((PSArray)dash.getValue()).getArray() ;
        float[] ans = new float[arr.length] ;
        for(int i = 0 ; i < arr.length ; i ++){
            ans[i] = (float) ((PSReal)arr[i].getValue()).getRealValue() ;
        }
        return ans ;
    }*/

    public GraphicsSettings clone() {
        return new GraphicsSettings(new Color(color.getRGB()), lineWidth, lineJoin, lineCap, miterLimit, dash, dashPhase);
    }
}
