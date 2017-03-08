import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by vijaysy on 08/03/17.
 */
public class RuleEngineHelperTest {

    static List<TestRule> testRules = new ArrayList<>();

    static List<String> keys = new ArrayList<>();

    @BeforeClass
    public static void createRules(){
        testRules.add(new TestRule("r.*","last_ram","DEP1","EMP1",0,"outcome1"));
        testRules.add(new TestRule("s.*","last_sham","DEP2","EMP2",0,"outcome2"));
        testRules.add(new TestRule("b.*","last_bham","DEP3","EMP3",0,"outcome3"));
        keys = Arrays.asList("firstName","lastName","department","employeeId");
    }


    @Test
    public void testGetMatchingRuleHigherPriority() throws Exception {
        TestRule result;

        Optional<TestRule> rule = RuleEngineHelper.getMatchingRuleHigherPriority(testRules,new TestRule("ram","last_ram","DEP1","EMP1",0),keys);
        Assert.assertEquals(true,rule.isPresent());
        result = rule.get();
        Assert.assertEquals(result.getOutcome(),"outcome1");

        rule = RuleEngineHelper.getMatchingRuleHigherPriority(testRules,new TestRule("sham","last_sham","DEP2","EMP2",0),keys);
        Assert.assertEquals(true,rule.isPresent());
        result = rule.get();
        Assert.assertEquals(result.getOutcome(),"outcome2");

        rule = RuleEngineHelper.getMatchingRuleHigherPriority(testRules,new TestRule("bham","last_bham","DEP3","EMP3",0),keys);
        Assert.assertEquals(true,rule.isPresent());
        result = rule.get();
        Assert.assertEquals(result.getOutcome(),"outcome3");

        rule = RuleEngineHelper.getMatchingRuleHigherPriority(testRules,new TestRule("vijay","last_bham","DEP3","EMP3",0),keys);
        Assert.assertEquals(false,rule.isPresent());
    }

    @Test
    public void testGetMatchingRules() throws Exception {

    }

    @Test
    public void testFindConflictingRules() throws Exception {
        List<TestRule> testRules = new ArrayList<>();
        List<TestRule> conflictingRules;

        testRules.add(new TestRule("r.*","last_ram","DEP1","EMP1",0,"outcome1"));
        testRules.add(new TestRule("s.*","last_sham","DEP2","EMP2",0,"outcome2"));
        testRules.add(new TestRule("b.*","last_bham","DEP3","EMP3",0,"outcome3"));
        keys = Arrays.asList("firstName","lastName","department","employeeId");
        TestRule testRule = new TestRule("raj","xyz","DEP1","EMP1",0,"outcome1");
        conflictingRules = RuleEngineHelper.findConflictingRules(testRules,testRule,keys);
        Assert.assertEquals(0,conflictingRules.size());

        testRule = new TestRule("raj","l.*","DEP1","EMP1",0,"outcome1");
        conflictingRules = RuleEngineHelper.findConflictingRules(testRules,testRule,keys);
        Assert.assertEquals(1,conflictingRules.size());


    }
}
