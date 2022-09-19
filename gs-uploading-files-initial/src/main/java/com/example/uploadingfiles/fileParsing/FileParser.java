package com.example.uploadingfiles.fileParsing;

import java.io.FileReader;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

/**
 * Class: FileParser
 * @author Cameron Ventimiglia, Michelle Watson, Puen Xie, Constance Yang
 * @author Christopher Wilder, David Marshall, Ephrem Engida, Matteo Kitic
 * Version: 1.1
 * Course: ITEC 3870
 * Written: September 16th, 2021
 * Modified: February 16th, 2022
 *
 * Purpose: The purpose of this class is to have the ability to parse a JSON file
 * in order to create Parameter objects. These Parameter objects will hold the name of the
 * input parameter in the file and an ArrayList<String> of the equivalence classes for that input parameter.
 * This class is also responsible for creating all possible combinations of equivalence classes. The goal is
 * to take this class and use it in the front-end in order to process files input by the user.
 */
@Service
public class FileParser {
	/**
	 * method: createAllCombinationsOfEquivalenceClasses
	 * Creates a matrix that holds all of the combinations of equivalence classes. The matrix is 
	 * made by setting the rows to the amount of combinations and columns to the amount of parameters.
	 * The algorithm populates the matrix from bottom to top. The parameter listOfParameters represents an
	 * ArrayList that stores all of the parameters. The parameter count represents the total amount of combinations.
	 * @param listOfParameters
	 * @param totalAmountOfParameters
	 * @return returns the populated matrix
	 */
	public String[][] createAllCombinationsOfEquivalenceClasses(ArrayList<Parameter> listOfParameters,
			int totalAmountOfParameters) {
		 // stores the strings of each parameter's equivalence classes
		ArrayList<ArrayList<String>> paramList = new ArrayList<>();

		//populate the paramList ArrayList with the equivalence Classes
		for (Parameter temp : listOfParameters) {
			paramList.add(temp.getEquivalenceClasses());
		}

		int totalParams = listOfParameters.size(); // amount of parameters
		int currentParam = paramList.get(totalParams - 1).size(); // the equivclass
		int change = 1; // use this to show when to change the equivClass
		int checkChange = change * currentParam; // use this to exit the change loop and reset
		String[][] paramArray = new String[totalAmountOfParameters][totalParams]; // array with the size of the total amount of params
		int temp = totalAmountOfParameters-1; //temp variable used to represent the total number of combinations - 1 (rows)
		int check = totalParams; // use this as a decision maker in the if statement

		 // keep going until every parameter is ran through
		while (totalParams > 0) {
			//keep going until every row is populated 
			while (temp > -1) { 
				// use j to populate the matrix
				for (int j = 0; j < currentParam; j++) {
					// this is used to decide which logic to use
					if ((check != totalParams) && (totalParams > 0)) { 
						change = 0; // reset change so it can iterate again
						while (change < checkChange) {
							paramArray[temp][totalParams - 1] = paramList.get(totalParams - 1).get(j);
							temp--;
							change++;
						}
					} 
					else {
						paramArray[temp][totalParams - 1] = paramList.get(totalParams - 1).get(j);
						temp--;
					}
				}
			}
			temp = totalAmountOfParameters - 1; // reset temp
			totalParams--; //change the parameter
			checkChange = change * currentParam; // update checkChange
			if (totalParams > 0) { // use this to update currentParam while totalParams > 1
				currentParam = paramList.get(totalParams - 1).size();
			}
		}
		return paramArray;
	}

	/**
	 * method: parseJSONFile
	 * This method parses JSON files. It will add the input parameter name and equivalence classes and create Parameter objects
	 * using these. Once one Parameter object is made, it will add it to an ArrayList of type Parameter.
	 * @param fileName
	 * @return returns an ArrayList of Parameter objects
	 * @throws Exception
	 */
	public DataObject parseJSONFile(String fileName) throws Exception {
		DataObject data = new DataObject();
		JSONObject obj = (JSONObject)new JSONParser().parse(new FileReader(fileName));
		parseObject(data, obj);

		return data;
	}

	/**
	 * method: parseParameters
	 * takes a DataObject and returns an ArrayList of Parameters for generating test cases.
	 * finds a parameter's name and its equivalence classes.
	 * generate a Parameter object based on them.
	 * adds the generated Parameter object to an ArrayList
	 * @param data
	 * @return an ArrayList of generated Parameter objects
	 */
	public ArrayList<Parameter> parseParameters(DataObject data) {
//		ArrayList<String> paramNameList = new ArrayList<>(); Commented out 9/18/22 BS
		ArrayList<Parameter> pList = new ArrayList<>();

		DataObject inputParameters = data.find("InputParameters");
		for (Map.Entry<String, DataObject> entry : inputParameters.getChildObject().entrySet()) {
			String paramName = entry.getKey();
//			paramNameList.add(paramName); Commented out 9/18/22 BS

			Parameter param = new Parameter();
			param.setName(paramName);

			DataObject equivClass = entry.getValue().find("EquivalenceClasses");

			for (Map.Entry<String, DataObject> subEntry : equivClass.getChildObject().entrySet())
				param.addParam(subEntry.getKey());

			pList.add(param);

		}
		return pList;
	}
	// test

	/**
	 * method: parseExpectedResultsFromTestConditions
	 * Takes a DataObject and returns an ArrayList of expected results for test cases.
	 * Finds an expected result's name and the condition under which it applies to a test case.
	 * Generates an ExpectedResult object based on the condition of the test case.
	 * Adds the generated ExpectedResult object to an ArrayList.
	 * @param data
	 * @return an ArrayList of generated ExpectedResult objects
	 */
	public ArrayList<ExpectedResult> parseExpectedResultsFromTestConditions(DataObject data) {
		ArrayList<ExpectedResult> expectedResultsList = new ArrayList<>();
		DataObject expResults = data.find("ExpectedResults");
		for (Map.Entry<String, DataObject> subEntry : expResults.getChildObject().entrySet()) {
			String name = subEntry.getKey();
			String condition = subEntry.getValue().find("Condition");
			ExpectedResult expResult = new ExpectedResult(name, condition);
			expectedResultsList.add(expResult);
		}
		return expectedResultsList;
	}

	/**
	 * method: prepareTestConditionForReversePolishNotation
	 * Returns a condition in reverse polish notation, a format which is easier to parse
	 * @return returns queue with the condition in reverse polish notation
	 * Author: Matteo, Ephrem, David, Chris: Group coding sessions

	 *SHUNTING YARD ALGORITHM GUIDE

	While there are tokens to be read:
	2.        Read a token
	3.        If it's a number add it to queue
	4.        If it's an operator
	5.               While there's an operator on the top of the stack with greater precedence:
	6.                       Pop operators from the stack onto the output queue
	7.               Push the current operator onto the stack
	8.        If it's a left bracket push it onto the stack
	9.        If it's a right bracket
	10.            While there's not a left bracket at the top of the stack:
	11.                     Pop operators from the stack onto the output queue.
	12.             Pop the left bracket from the stack and discard it
	13. While there are operators on the stack, pop them to the queue
	 */
	public Queue<String> prepareCondition(String condition) {
		//put space before and after operators in the condition
		condition = condition.replaceAll("[()]|!=|=(?<!!)", " $0 ");
		//make sure any space at the beginning and end is gone
		condition = condition.trim();

		//split condition into array of words
		String[] words = condition.split("\\s+");
		Stack<String> stack = new Stack<>();
		Queue<String> queue = new LinkedList<>();

		//Greater value in map = higher precedence
		HashMap<String, Integer> precedence = new HashMap<>();
		precedence.put("AND", 2);
		precedence.put("OR", 1);
		precedence.put("=", 3);
		precedence.put("!=", 3);

		for (String word : words) {
			//System.out.println("Word: " + word + "<- Word");
			if (word.equals("(")) {
				stack.push("(");
			}
			else if (word.equals(")")) {

				//while there is no left bracket at the top of the stack pop values from the stack onto the output queue.
				while (!stack.peek().equals("(")) {
					queue.add(stack.pop());
				}
				stack.pop(); //discard left bracket from stack

			}
			//account for AND/OR/=/!= operators
			else if (word.equalsIgnoreCase("AND") || word.equalsIgnoreCase("OR") || word.equals("=") || word.equals("!=")) {
				//make the word uppercase to properly find it in the map
				word = word.toUpperCase();
				//while the stack has a non-parenthesis operator with higher priority
				while(!stack.empty() && !stack.peek().equals("(") && precedence.get(stack.peek()) > precedence.get(word)) {
					queue.add(stack.pop());
				}
				stack.push(word);
			}
			// check if there is no word after AND
			else {
				//add normal words to queue
				queue.add(word);
			}
		}
		while (!stack.isEmpty()) {
			queue.add(stack.pop());
		}
		return queue;
	}

	public void parseArray(JSONObject json, String key, DataObject data) {
		JSONArray jsonArray = (JSONArray)json.get(key);
		// sanity checks
		if (jsonArray == null)
			return;

		ArrayList<DataObject> objectArray = new ArrayList<DataObject>();
		ArrayList<String> stringArray = new ArrayList<String>();

		for (Object object : jsonArray) {
			if (object instanceof JSONObject) {
				// json array contains an jsonobject
				// create a new data object and let parseObject handle it
				DataObject arrayObject = new DataObject();
				parseObject(arrayObject, (JSONObject) object);
				objectArray.add(arrayObject);
				continue;
			}
			stringArray.add((String) object);
		}

		// sanity checks
		if (objectArray.size() > 0)
			data.addDataArrayObject(key, objectArray);

		if (stringArray.size() > 0)
			data.addDataArray(key, stringArray);
	}

	/**
	 * method: isConditionValid
	 * checks to see if condition in the queue is valid or not
	 * returns test case for condition and whether condition is valid or not
	 * @return condition result
	 */

	public ConditionResult isConditionValid(Queue<String> condition){
		ConditionResult result = new ConditionResult();
		result.setIsvalid(true);
		if(condition.isEmpty()){
			result.setIsvalid(false);
			result.setErrorMessage("the condition is empty");
		}
		//if there is an extra unmatched bracket
		else if(condition.contains(")") || condition.contains("(")){
			result.setIsvalid(false);
			result.setErrorMessage("the condition has extra ( or )");
		}
		else if(condition.contains("!==") || condition.contains("==")){
			result.setIsvalid(false);
			result.setErrorMessage("the condition has invalid operator(s)");

		}
		else {
			result.setIsvalid(true);
		}

		return result;


	}


	/**
	 * method: compareTestCaseWithCondtions
	 * checks to see if a condition for an expected result matches a test case.
	 * returns true if it does and false if it does not.
	 * @param condition the condition for the expected result
	 * @param testCaseValues the test case which may have the expected result
	 * @param parameterIndexes a map of parameter names and their respective index in the test case array
	 * @return a boolean specifying if the expected result applies to the test case
	 */

	//handlling exception

	public boolean compareTestCaseWithCondtions(Queue<String> condition, String[] testCaseValues, Map<String, Integer> parameterIndexes) {
		// create a stack to keep track of what needs to happen next while parsing the condition
		// needs to hold booleans and strings, so initialize as a generic stack

		Stack stack = new Stack();
		// go through the entire condition
		while (!condition.isEmpty()) {
			String s = condition.poll();
			if(s.equals("=")) {
				stack.push(isConditionEqual(stack, testCaseValues, parameterIndexes));
			}
			else if(s.equals("!=")) {
				stack.push(!isConditionEqual(stack, testCaseValues, parameterIndexes));
			}
			// "and" and "or" should be uppercase here, but use equalsIgnoreCase to be safe
			else if(s.equalsIgnoreCase("AND")) {
				// boolean comparators short-circuit if they know their result from the first item,
				// so make sure to pop before comparing
				boolean b1 = (boolean) stack.pop();
				boolean b2 = (boolean) stack.pop();

				stack.push( b1 && b2);
			}
			else if(s.equalsIgnoreCase("OR")) {
				// boolean comparators short-circuit if they know their result from the first item,
				// so make sure to pop before comparing
				boolean b1 = (boolean) stack.pop();
				boolean b2 = (boolean) stack.pop();

				stack.push(b1 || b2);
			}
			else {
				stack.push(s);
			}
		}
		return (boolean) stack.pop();
	}

	/**
	 * method: isConditionEqual
	 * checks to see if the top two items in the stack are equal. In this case, equal means
	 * the value of the parameter in the test case matches the value given in the condition.
	 * @param stack the stack of pieces of the condition to test
	 * @param testCase the test case for which the condition is being checked
	 * @param parameters a map of which parameters correspond to which parts of the test case
	 * @return a boolean representing if the value for the parameter matches what was given
	 */
	private boolean isConditionEqual(Stack stack, String[] testCase, Map<String, Integer> parameters) {
		if(stack.peek() instanceof Boolean) {
			// something went wrong; deal with this later
			// for now, print a message and return false
			System.err.println("Something went wrong parsing the condition for expected results.");
			return false;
		}

		String paramVal = (String) stack.pop();

		if(stack.peek() instanceof Boolean) {
			// something went wrong; deal with this later
			// for now, print a message and return false
			System.err.println("Something went wrong parsing the condition for expected results.");
			return false;
		}

		String paramName = (String) stack.pop();

		return testCase[parameters.get(paramName)].equals(paramVal);
	}

	/**
	 *
	 * @param data
	 * @param json
	 */
	public void parseObject(DataObject data, JSONObject json) {
		// loop through the whole json
		Iterator<String> iter = json.keySet().iterator();
		while (iter.hasNext()) {

			String key = iter.next();

			if (json.get(key) instanceof JSONObject) {
				// if it is an object (surrounded by '{}')
				// create a new DataObject and do parseObject again
				DataObject child = new DataObject();
				data.addChild(key, child);
				parseObject(child, (JSONObject)json.get(key));
				continue;
			}

			if (json.get(key) instanceof JSONArray) {
				// if it is an array (surrounded by '[]')
				// let parseArray handle it
				parseArray(json, key, data);
				continue;
			}

			String value = (String)json.get(key);
			data.addDataField(key, value);
		}
	}

	/**
	 * method: getParamMap
	 * Links Columns and their equivalence classes from createCombos to their corresponding parameter names
	 * @param arr an arraylist of parameter names
	 * @return a hashmap of columns with their corresponding parameter names
	 */
	public HashMap<String, Integer> getParamMap(ArrayList<Parameter> arr) {
		HashMap<String, Integer> linkParamToColumn = new HashMap<>();
		for (int i = 0; i < arr.size(); i++) {
			String paramName = arr.get(i).getName();
			//parameter name is the key while the value is the column index that corresponds to it
			linkParamToColumn.put(paramName, i);
		}
		return linkParamToColumn;
	}

	public HashMap<Integer,String> getParamMapByIndex(ArrayList<Parameter> arr) {
		HashMap< Integer,String> linkParamToColumn = new HashMap<>();
		for (int i = 0; i < arr.size(); i++) {
			String paramName = arr.get(i).getName();
			//parameter name is the key while the value is the column index that corresponds to it
			linkParamToColumn.put(i,paramName);
		}
		return linkParamToColumn;
	}



	/**
	 * method: compareVariables
	 * IMPORTANT: This method is dated and needs to be modified to meet the project's needs
	 * Returns a list of parameter names that appear in any ExpectedResult Conditions for a given Json file.
	 * @return returns list of valid parameter names that appear in expectedResultsList
	 */
	/*public ArrayList<String> compareVariable(ArrayList<Parameter> parametersList , ArrayList<ExpectedResult> expectedResultList) {
		ArrayList<String> matchingVariables = new ArrayList<>();
		for (Parameter parameter: parametersList) {
			for (ExpectedResult expectedResult: expectedResultList) {
				if (expectedResult.getCondition().contains(parameter.getName()) && !matchingVariables.contains(parameter.getName())) {
					matchingVariables.add(parameter.getName());
				}

			}
		} return matchingVariables;
	} */
}
