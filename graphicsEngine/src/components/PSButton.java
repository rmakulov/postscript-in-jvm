package components;

/**
 * Created by User on 28/1/2015.
 */
public class PSButton extends PSComponent {
    public PSButton(int x, int y, int w, int h, String buttonLabel, PSComponent owner, String name) {
        super(name);
        generatedString = String.format("/%s %d %d %d %d (%s) %s <</click [{pop}[]]>> button def", name, x, y, w, h, buttonLabel, owner.getName());
    }

    public PSButton(int x, int y, int w, int h, String buttonLabel, PSComponent owner) {
        super();
        generatedString = String.format("%d %d %d %d (%s) %s << >> button pop", x, y, w, h,  buttonLabel, owner.getName());
    }

}
