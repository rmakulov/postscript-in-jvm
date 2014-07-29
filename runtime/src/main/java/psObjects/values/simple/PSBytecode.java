package psObjects.values.simple;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by user on 28.07.14.
 */
public class PSBytecode extends PSName {

    private Queue<Double> args = new ArrayDeque<Double>();

    public Queue<Double> getArgs() {
        Queue<Double> res = new ArrayDeque<Double>();
        res.addAll(args);

//        for(Iterator itr = args.iterator();itr.hasNext();)  {
//            res.add((Double) itr.next());
//        }
        return res;
    }

    public PSBytecode(String strValue, Queue<Double> args) {
        super(strValue);
        this.args.addAll(args);
//        for(Iterator itr = args.iterator();itr.hasNext();)  {
//            this.args.add((Double) itr.next());
//        }
    }

}
