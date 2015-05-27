package components;

import java.util.ArrayList;

/**
 * Created by User on 20/5/2015.
 */
public class PSComponent {
    protected int number;
    protected String name;
    protected String generatedString;
    protected ArrayList<PSComponent> children=new ArrayList<PSComponent>();
    protected PSComponent owner;
    private static int id=1;

    public PSComponent(String name) {
        this.name = name;
    }

    public PSComponent() {
    }

    public void add(PSComponent component) {
        children.add(component);
        component.setOwner(this);
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getGeneratedString() {
        updateGeneratedString();
        StringBuilder stringBuilder= new StringBuilder(generatedString);
        for(PSComponent component:children){
            stringBuilder.append("\n").append(component.getGeneratedString());
        }
        return stringBuilder.toString();
    }

    protected  void updateGeneratedString() {
              //to override
    }

    public void setOwner(PSComponent owner) {
        this.owner = owner;
    }

    public static int getNewId(){
        return id++;
    }
}
