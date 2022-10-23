//import com.example.uploadingfiles.fileParsing.CoverageGroup;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//public class CovGroupPrecedenceDemo {
//
//    public static void main(String[] args) {
//        ArrayList<CoverageGroup> cgList = new ArrayList<>();
//        HashMap<Integer, String> precMap = new HashMap<>();
//
//            CoverageGroup cg1 = new CoverageGroup("cg1", new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E')));
//            cgList.add(cg1);
//
//            CoverageGroup cg2 = new CoverageGroup("cg2", new ArrayList<>(Arrays.asList('A')));
//            cgList.add(cg2);
//
//            CoverageGroup cg3 = new CoverageGroup("cg3", new ArrayList<>(Arrays.asList('A', 'B')));
//            cgList.add(cg3);
//
//            for(CoverageGroup cg: cgList){
//                precMap.put(cg.getList().size(), cg.getName());
//            }
//
//            ArrayList<Integer> keys = new ArrayList<>(precMap.keySet());
//            for (int i = keys.size() - 1; i>=0; i--){
//                System.out.println(precMap.get(keys.get(i)));
//            }
//
//    }
//
//
//
//}
