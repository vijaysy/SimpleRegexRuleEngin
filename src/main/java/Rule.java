import java.util.Hashtable;
import java.util.List;

/**
 * Created by vijaysy on 08/03/17.
 */
public interface  Rule {
    Hashtable<String,String> toMap();

    boolean customComparison(Rule rule, List<String> keys);

    int getPriority();

}
