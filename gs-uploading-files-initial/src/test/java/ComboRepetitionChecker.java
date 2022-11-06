import com.example.uploadingfiles.fileParsing.CoverageGroup;
import com.example.uploadingfiles.fileParsing.Parameter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ComboRepetitionChecker {

    public static void main(String[] args) {
        ArrayList<CoverageGroup> covGroupList = new ArrayList<>();
        HashMap<String, Integer> comboMap;


        ComboRepetitionChecker crc = new ComboRepetitionChecker();
//    public void createCoverageGroupList(){
        CoverageGroup covGroupAB = new CoverageGroup("A, B", new ArrayList<>(Arrays.asList(new Parameter("A", new ArrayList<>(Arrays.asList("A1", "A2", "A3", "A4"))), new Parameter("B", new ArrayList<>(Arrays.asList("B1", "B2", "B3"))))));
        CoverageGroup covGroupBC = new CoverageGroup("B, C", new ArrayList<>(Arrays.asList(new Parameter("B", new ArrayList<>(Arrays.asList("B1", "B2"))), new Parameter("C", new ArrayList<>(Arrays.asList("C1", "C2"))))));
        CoverageGroup covGroupAC = new CoverageGroup("A, C", new ArrayList<>(Arrays.asList(new Parameter("A", new ArrayList<>(Arrays.asList("A1", "A2"))), new Parameter("C", new ArrayList<>(Arrays.asList("C1", "C2"))))));


        covGroupList.add(covGroupAB);
        covGroupList.add(covGroupBC);
        covGroupList.add(covGroupAC);

        comboMap = crc.createCombosFromCovGroups(covGroupList, covGroupList.size(), 0, 1, new HashMap<>());

        System.out.println(comboMap);


    }



    /**
     * method: createCombosFromCovGroups
     * creates combinations from a passed list of parameters and adds it to a Map to store how many times each combo
     * appears
     * still trying to get it to work with different number of parameters
     * @param covList ArrayList of coverage groups
     * @param size  size of the ArrayList of coverage groups
     * @param firstParamIndex index for the first parameter in a coverage group
     * @param secondParamIndex index for the second parameter in a coverage group
     */
    private HashMap<String, Integer> createCombosFromCovGroups(ArrayList<CoverageGroup> covList, int size, int firstParamIndex, int secondParamIndex, HashMap<String, Integer> comboMap) {
        if(size < 1){
            return comboMap;
        }
        else{
            for(int i = firstParamIndex; i < covList.get(covList.size() - size).getList().get(firstParamIndex).getEquivalenceClasses().size(); i++){
                for(int j = 0; j < covList.get(covList.size() - size).getList().get(secondParamIndex).getEquivalenceClasses().size(); j++){
                    comboMap.put(covList.get(covList.size() - size).getList().get(firstParamIndex).getEquivalenceClasses().get(i)
                            + covList.get(covList.size() - size).getList().get(secondParamIndex).getEquivalenceClasses().get(j), 1);
                }

            }

            return createCombosFromCovGroups(covList, size - 1, 0, 1, comboMap);
        }
    }




}



