package psObjects;

/**
 * Created by 1 on 04.12.13.
 */
public class PSNull extends PSObject {
    public final static PSNull NULL = new PSNull();


    private PSNull() {
    }

    @Override
    public PSObject clone() {
        return null;
    }
}
