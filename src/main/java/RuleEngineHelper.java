import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;

import java.util.*;

/**
 * Created by vijaysy on 08/03/17.
 */
public class RuleEngineHelper {

    public static <T extends Rule> Optional<T> getMatchingRuleHigherPriority(List<T> ruleList, T inputRule, List<String> priorityOfKeys) {
        List<T> matchingRules = getMatchingRules(ruleList, inputRule, priorityOfKeys);
        matchingRules.sort((o1, o2) -> o2.getPriority() - o1.getPriority());
        return matchingRules.isEmpty() ? Optional.empty() : Optional.of(matchingRules.get(0));
    }


    public static <T extends Rule> List<T> getMatchingRules(List<T> ruleList, T inputRule, List<String> priorityOfKeys) {
        Map<String, String> inputRuleMap = inputRule.toMap();
        Map<Integer, List<T>> weightRuleListMap = new HashMap<>();
        ruleList.forEach(rule -> {
            int equals = priorityOfKeys.size() * 100;
            int matches = priorityOfKeys.size() * 10;
            boolean ifMatchOrEquals = true;
            Map<String, String> ruleMap = rule.toMap();
            int weight = 0;
            for (String key : priorityOfKeys) {
                if (inputRule.customComparison(rule, priorityOfKeys)) {
                    ifMatchOrEquals = false;
                    break;
                }
                if (inputRuleMap.get(key).equals(ruleMap.get(key))) {
                    weight += equals;
                } else if (inputRuleMap.get(key).matches(ruleMap.get(key))) {
                    weight += matches;
                } else {
                    ifMatchOrEquals = false;
                    break;
                }
                equals = equals - 100;
                matches = matches - 10;
            }
            if (ifMatchOrEquals) {
                List<T> rulesForGivenWeight = weightRuleListMap.getOrDefault(weight, new ArrayList<>());
                rulesForGivenWeight.add(rule);
                weightRuleListMap.put(weight, rulesForGivenWeight);
            }

        });
        return weightRuleListMap.isEmpty() ? new ArrayList<>() : weightRuleListMap.get(Collections.max(weightRuleListMap.keySet()));
    }

    public static <T extends Rule> List<T> findConflictingRules(List<T> rules, Rule newRule, List<String> keys) {

        List<T> intersectingRules = new ArrayList<>();
        Map<String, String> newRuleMap = newRule.toMap();

        rules.forEach(rule -> {
            Map<String, String> ruleMap = rule.toMap();
            int intersectKeys = 0;
            for (String key : keys) {
                RegExp re = new RegExp("(" + newRuleMap.get(key) + ")&(" + ruleMap.get(key) + ")", RegExp.INTERSECTION); // Parse RegExp
                Automaton a = re.toAutomaton();
                if (!a.isEmpty()) {
                    intersectKeys++;
                    System.out.println("Intersection is non-empty, example: " + a.getShortestExample(true) + " For: (" + newRuleMap.get(key) + ")&(" + ruleMap.get(key) + ")");
                }
            }
            if (intersectKeys == keys.size()) {
                intersectingRules.add(rule);
            }


        });
        System.out.println("intersectingRulesList size: "+ intersectingRules.size());
        return intersectingRules;
    }
}
