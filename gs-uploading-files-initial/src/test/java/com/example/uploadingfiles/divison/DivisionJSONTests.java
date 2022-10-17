import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class DivisionJSONTests {

    private HashMap<String, Integer> map;

    String parameterAndEquivalenceClass1 = "Divisor, Positive";
    String parameterAndEquivalenceClass2 = "Divisor, Negative";
    String parameterAndEquivalenceClass3 = "Divisor, 0";
    String parameterAndEquivalenceClass4 = "Dividend, Positive";
    String parameterAndEquivalenceClass5 = "Dividend, Negative";
    String parameterAndEquivalenceClass6 = "Dividend, 0";

    private int p1Usage = 0;
    private int p2Usage = 0;
    private int p3Usage = 0;
    private int p4Usage = 0;
    private int p5Usage = 0;
    private int p6Usage = 0;

    @Test
    public void testMapSize() {
        map = loadHashMap();

        Assert.assertEquals("Map size is wrong", 6, map.size());
    }

    @Test
    public void updateHashMapUsageCountTest() {
        map = loadHashMap();

        // Check if both test combinations exist in map have been used
        String testCase1 = "Divisor, Positive";
        String testCase2 = "Divisor, Negative";

        int testCase1UsageCount = map.get(testCase1);

        Assert.assertEquals("Usage is not correct", 0, testCase1UsageCount);

        map.put(testCase2, 1);

        int testCase2UsageCount = map.get(testCase2);

        Assert.assertEquals("Usage is not correct", 1, testCase2UsageCount);
    }

    @Test
    public void incrementTwoParamsAndTheirEquivalenceClassUsage() {
        map = loadHashMap();

        String parameterAndEquivalenceClass1 = "Divisor, Positive";
        String parameterAndEquivalenceClass2 = "Divisor, Negative";

        int p1Usage = 0;
        int p2Usage = 0;
        if (map.containsKey(parameterAndEquivalenceClass1) && map.containsKey(parameterAndEquivalenceClass2)) {
            // get their values to check if each have been used.
            p1Usage = map.get(parameterAndEquivalenceClass1);
            p2Usage = map.get(parameterAndEquivalenceClass2);
        }

        if (p1Usage == 0 && p2Usage == 0) {
            // increment their usage
            map.put(parameterAndEquivalenceClass1, p1Usage + 1);
            map.put(parameterAndEquivalenceClass2, p2Usage + 1);
        }

        p1Usage = map.get(parameterAndEquivalenceClass1);
        p2Usage = map.get(parameterAndEquivalenceClass2);

        Assert.assertEquals("p1Usage count is wrong", 1, p1Usage);
        Assert.assertEquals("p1Usage count is wrong", 1, p2Usage);
    }

    @Test
    public void updateMapForUnequalCounts() {
        map = loadHashMap();

        // Update the map so that paramClass1 and paramClass2 do not have equal counts.
        String parameterAndEquivalenceClass1 = "Divisor, Positive";

        map.put(parameterAndEquivalenceClass1, 1);

        String parameterAndEquivalenceClass2 = "Divisor, Negative";

        int p1Usage = map.get(parameterAndEquivalenceClass1);
        int p2Usage = map.get(parameterAndEquivalenceClass2);

        if (p1Usage != 0 || p2Usage != 0) {
            p1Usage = p1Usage + 1;
            p2Usage = p2Usage + 1;
        }

        Assert.assertEquals("Should be 2", 2,  p1Usage);
        Assert.assertEquals("Should be 1", 1, p2Usage);
    }

    @Test
    public void divisorJSONTest() {
        map = loadHashMap();
        int count = 0;

        p1Usage = map.get(parameterAndEquivalenceClass1);
        p2Usage = map.get(parameterAndEquivalenceClass2);
        p3Usage = map.get(parameterAndEquivalenceClass3);
        p4Usage = map.get(parameterAndEquivalenceClass4);
        p5Usage = map.get(parameterAndEquivalenceClass5);
        p6Usage = map.get(parameterAndEquivalenceClass6);

        // Test Case 1 (adds 2 to count total)
        // Divisor: Positive, Dividend: Positive
        testCaseHelper(p1Usage, p4Usage, parameterAndEquivalenceClass1, parameterAndEquivalenceClass4);

        // Test Case 2 (adds 2 to count total)
        // Divisor: Positive, Dividend: Negative
        testCaseHelper(p1Usage, p5Usage, parameterAndEquivalenceClass1, parameterAndEquivalenceClass5);

        // Test case 3 (adds 2 to count total)
        // Divisor: Positive, Dividend: 0
        testCaseHelper(p1Usage, p6Usage, parameterAndEquivalenceClass1, parameterAndEquivalenceClass6);

        // Test case 4 (adds 2 to count total)
        // Divisor: Negative Dividend: Positive
        testCaseHelper(p2Usage, p6Usage, parameterAndEquivalenceClass2, parameterAndEquivalenceClass6);

        // Test case 5 (does not add 2 to count total because of duplicates)
        // Divisor: Negative, Dividend: Negative
        testCaseHelper(p2Usage, p5Usage, parameterAndEquivalenceClass2, parameterAndEquivalenceClass5);

        // Test case 6 (does not add 2 to count total because of duplicates)
        // Divisor: Negative, Dividend: 0
        testCaseHelper(p2Usage, p6Usage, parameterAndEquivalenceClass2, parameterAndEquivalenceClass6);

        // Test case 7 (adds 2 to count total) count should equal 10
        // Divisor: 0, Dividend: Positive
        testCaseHelper(p3Usage, p4Usage, parameterAndEquivalenceClass3, parameterAndEquivalenceClass4);

        // Test case 8 (does not add 2 to count total because of duplicates)
        // Divisor: 0, Dividend: Negative
        testCaseHelper(p3Usage, p5Usage, parameterAndEquivalenceClass3, parameterAndEquivalenceClass5);

        // Test case 9 (does not add 2 to count total because of duplicates)
        // Divisor: 0, Dividend: 0
        testCaseHelper(p3Usage, p6Usage, parameterAndEquivalenceClass3, parameterAndEquivalenceClass6);

        count = p1Usage + p2Usage + p3Usage + p4Usage + p5Usage + p6Usage;
        Assert.assertEquals("The usages do not match up.", 10, count);
    }

    public void reloadUsage() {
        p1Usage = map.get(parameterAndEquivalenceClass1);
        p2Usage = map.get(parameterAndEquivalenceClass2);
        p3Usage = map.get(parameterAndEquivalenceClass3);
        p4Usage = map.get(parameterAndEquivalenceClass4);
        p5Usage = map.get(parameterAndEquivalenceClass5);
        p6Usage = map.get(parameterAndEquivalenceClass6);
    }


    public void testCaseHelper(int usage1, int usage2, String paramAndEquivClass1, String paramAndEquivClass2) {
        if (usage1 == 0 || usage2 == 0) {
            usage1++;
            usage2++;
            map.put(paramAndEquivClass1, usage1);
            map.put(paramAndEquivClass2, usage2);
        }
        reloadUsage();
    }

    // Helper class
    public HashMap<String, Integer> loadHashMap() {
        map = new HashMap<>();

        map.put(parameterAndEquivalenceClass1, 0);
        map.put(parameterAndEquivalenceClass2, 0);
        map.put(parameterAndEquivalenceClass3, 0);
        map.put(parameterAndEquivalenceClass4, 0);
        map.put(parameterAndEquivalenceClass5, 0);
        map.put(parameterAndEquivalenceClass6, 0);

        return map;
    }
}
