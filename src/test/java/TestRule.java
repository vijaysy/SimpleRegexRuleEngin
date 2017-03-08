import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by vijaysy on 08/03/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestRule implements Rule {

    private String firstName;

    private String lastName;

    private String department;

    private String employeeId;

    private int priority = 0;

    private String outcome;

    public TestRule(String firstName, String lastName, String department, String employeeId, int priority) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.department=department;
        this.employeeId=employeeId;
        this.priority=priority;
    }

    @Override
    public Hashtable<String, String> toMap() {
        Hashtable<String,String> stringStringHashtable = new Hashtable<>();
        stringStringHashtable.put("firstName",this.firstName);
        stringStringHashtable.put("lastName",this.lastName);
        stringStringHashtable.put("department",this.department);
        stringStringHashtable.put("employeeId",this.employeeId);
        return stringStringHashtable;
    }

    @Override
    public boolean customComparison(Rule rule, List<String> keys) {
        return false;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
