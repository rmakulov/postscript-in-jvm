package components;

/**
 * Created by User on 20/5/2015.
 */
public class PSComponent {
    protected int number;
    protected String name;
    protected String generatedString;

    public PSComponent(String name) {
        this.name = name;
    }

    public PSComponent() {

    }

    public void add(PSComponent component){
        //todo
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getGeneratedString() {
        return generatedString;
    }
}
