import com.example.uploadingfiles.fileParsing.CoverageGroup;
import com.example.uploadingfiles.fileParsing.Parameter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chase Harrod
 */

public class ComboRepetitionChecker {

    public static void main(String[] args) {
        ArrayList<CoverageGroup> covGroupList = new ArrayList<>();
        HashMap<String, Integer> comboMap;


        ComboRepetitionChecker crc = new ComboRepetitionChecker();

        CoverageGroup covGroupABC = new CoverageGroup("A, B, C", new ArrayList<>(Arrays.asList(
                new Parameter("A", new ArrayList<>(Arrays.asList("A1", "A2", "A3", "A4"))),
                new Parameter("B", new ArrayList<>(Arrays.asList("B1", "B2", "B3"))),
                new Parameter("C", new ArrayList<>(Arrays.asList("C1", "C2"))))));


        CoverageGroup covGroupBCDE = new CoverageGroup("B, C, D, E", new ArrayList<>(Arrays.asList(
                new Parameter("B", new ArrayList<>(Arrays.asList("B1", "B2"))),
                new Parameter("C", new ArrayList<>(Arrays.asList("C1", "C2"))),
                new Parameter("D", new ArrayList<>(Arrays.asList("D1", "D2", "D3"))),
                new Parameter("E", new ArrayList<>(Arrays.asList("E1", "E2", "E3"))))));


        CoverageGroup covGroupAC = new CoverageGroup("A, C", new ArrayList<>(Arrays.asList(
                new Parameter("A", new ArrayList<>(Arrays.asList("A1", "A2"))),
                new Parameter("C", new ArrayList<>(Arrays.asList("C1", "C2"))))));


        covGroupList.add(covGroupABC);
        covGroupList.add(covGroupBCDE);
        covGroupList.add(covGroupAC);

        comboMap = crc.createCombosHelper(covGroupList);

        System.out.println("Number of Combinations: " + comboMap.size());

        crc.terminationCase(comboMap);



    }


    /**
     * method: terminationCase
     * terminates the program if all combinations have a value of 1
     * map key is the combination
     * map values are the number of times appeared
     * @param map map of created combinations from coverage group
     */
    public void terminationCase(HashMap<String, Integer> map){
        int countOfCombos = 0;

        for(String s: map.keySet()){
            if(map.get(s) == 1){
                countOfCombos++;
            }
        }

        if (countOfCombos == map.size()){
            System.out.println("All combinations have been used.");
            System.exit(0);
        }
    }



    /**
     * method: createCombosHelper
     * takes an ArrayList of CoverageGroups and adds combinations of parameters to a HashMap
     * calls other methods to create the combinations based on the number of parameters for a given coverage group up to
     * 5 parameters at the moment
     * @param covGroups list of coverage groups to be looped through
     * @return HashMap<String, Integer> which holds all combinations present in all coverage groups
     */
    public HashMap<String, Integer> createCombosHelper(ArrayList<CoverageGroup> covGroups){
        HashMap<String, Integer> comboMap = new HashMap<>();

        for(CoverageGroup cg: covGroups){


            if(cg.getList().size() == 2){
                comboMap.putAll(createCombosFromCovGroupsTwoParams(cg, comboMap));
            }
            else if(cg.getList().size() == 3){
                comboMap.putAll(createCombosFromCovGroupsThreeParams(cg, comboMap));
            }
            else if(cg.getList().size() == 4){
                comboMap.putAll(createCombosFromCovGroupsFourParams(cg, comboMap));
            }
            else if(cg.getList().size() == 5){
                comboMap.putAll(createCombosFromCovGroupsFiveParams(cg, comboMap));
            }

            System.out.println();
        }

        return comboMap;
    }

    /**
     * method: createCombosFromCovGroupsFiveParams
     * creates combinations from a passed list of parameters and adds it to a Map to store how many times each combo
     * appears
     * @param cg coverage group
     * @param comboMap HashMap of created combinations and the number of times that they appear
     */
    private Map<String, Integer> createCombosFromCovGroupsFiveParams(CoverageGroup cg, HashMap<String, Integer> comboMap) {

        //loop through first parameter of the coverage group
        for(int i = 0; i < cg.getList().get(0).getEquivalenceClasses().size(); i++){
            String paramOne = cg.getList().get(0).getEquivalenceClasses().get(i);

            //loop through second parameter of the coverage group
            for(int j = 0; j < cg.getList().get(1).getEquivalenceClasses().size(); j++) {
                String paramTwo = cg.getList().get(1).getEquivalenceClasses().get(j);

                //loop through third parameter of the coverage group
                for(int k = 0; k < cg.getList().get(2).getEquivalenceClasses().size(); k++) {
                    String paramThree = cg.getList().get(2).getEquivalenceClasses().get(k);

                    //loop through fourth parameter of the coverage group
                    for(int l = 0; l < cg.getList().get(3).getEquivalenceClasses().size(); l++) {
                        String paramFour = cg.getList().get(3).getEquivalenceClasses().get(l);


                        for(int m = 0; m < cg.getList().get(4).getEquivalenceClasses().size(); m++) {
                            String paramFive = cg.getList().get(4).getEquivalenceClasses().get(m);

                            String combo = paramOne + paramTwo + paramThree + paramFour + paramFive;

                            comboMap.put(combo, 1);
                            System.out.println(combo);

                        }
                    }
                }
            }
        }

        return comboMap;
    }

    /**
     * method: createCombosFromCovGroupsFourParams
     * creates combinations from a passed list of parameters and adds it to a Map to store how many times each combo
     * appears

     * @param cg coverage group
     * @param comboMap HashMap of created combinations and the number of times that they appear
     */
    private Map<String, Integer> createCombosFromCovGroupsFourParams(CoverageGroup cg, HashMap<String, Integer> comboMap) {

        //loop through first parameter of the coverage group
        for(int i = 0; i < cg.getList().get(0).getEquivalenceClasses().size(); i++){
            String paramOne = cg.getList().get(0).getEquivalenceClasses().get(i);

            //loop through second parameter
            for(int j = 0; j < cg.getList().get(1).getEquivalenceClasses().size(); j++) {
                String paramTwo = cg.getList().get(1).getEquivalenceClasses().get(j);

                //loop through third parameter of the coverage group
                for(int k = 0; k < cg.getList().get(2).getEquivalenceClasses().size(); k++) {
                    String paramThree = cg.getList().get(2).getEquivalenceClasses().get(k);

                    for(int l = 0; l < cg.getList().get(3).getEquivalenceClasses().size(); l++) {
                        String paramFour = cg.getList().get(3).getEquivalenceClasses().get(l);

                        String combo = paramOne + paramTwo + paramThree + paramFour;

                        comboMap.put(combo, 1);
                        System.out.println(combo);

                    }
                }
            }
        }

        return comboMap;
    }

    /**
     * method: createCombosFromCovGroupsThreeParams
     * creates combinations from a passed list of parameters and adds it to a Map to store how many times each combo
     * appears
     * @param cg coverage group
     * @param comboMap HashMap of created combinations and the number of times that they appear
     */
    private Map<String, Integer> createCombosFromCovGroupsThreeParams(CoverageGroup cg, HashMap<String, Integer> comboMap) {

        //loop through first parameter of the coverage group
        for(int i = 0; i < cg.getList().get(0).getEquivalenceClasses().size(); i++){
            String paramOne = cg.getList().get(0).getEquivalenceClasses().get(i);

            //loop through second parameter
            for(int j = 0; j < cg.getList().get(1).getEquivalenceClasses().size(); j++) {
                String paramTwo = cg.getList().get(1).getEquivalenceClasses().get(j);

                //loop through third parameter of the coverage group
                for(int k = 0; k < cg.getList().get(2).getEquivalenceClasses().size(); k++) {
                    String paramThree = cg.getList().get(2).getEquivalenceClasses().get(k);

                    String combo = paramOne + paramTwo + paramThree;

                    comboMap.put(combo, 1);
                    System.out.println(combo);
                }
            }
        }

        return comboMap;
    }

    /**
     * method: createCombosFromCovGroupsTwoParams
     * creates combinations from a passed list of parameters and adds it to a Map to store how many times each combo
     * appears
     * @param cg coverage group
     * @param comboMap HashMap of created combinations and the number of times that they appear
     */
    private HashMap<String, Integer> createCombosFromCovGroupsTwoParams(CoverageGroup cg, HashMap<String, Integer> comboMap) {


            //loop through first parameter of the coverage group
            for(int i = 0; i < cg.getList().get(0).getEquivalenceClasses().size(); i++){
                String paramOne = cg.getList().get(0).getEquivalenceClasses().get(i);

                //loop through second parameter
                for(int j = 0; j < cg.getList().get(1).getEquivalenceClasses().size(); j++) {
                    String paramTwo = cg.getList().get(1).getEquivalenceClasses().get(j);

                    String combo = paramOne + paramTwo;

                    comboMap.put(combo, 1);
                    System.out.println(combo);
                }
            }

            return comboMap;
    }




}



