package com.example.uploadingfiles.fileParsing;

import java.util.ArrayList;

/**
 * Class: Parameter
 * Edited Class: Saif Shaikh
 * @author Brian Smithers, Baldwin Pemberton, Chase Harrod, Saif Shaikh, Javier Zarate Zaragoza
 * Version: 1.0
 * Course: ITEC 3870
 * Written: September 26th, 2022
 *
Purpose: The purpose of this class is to be able to create Parameter objects when parsing a JSON file.
 * The Parameter object will persist of a parameter name and the list of equivalence classes it has. The goal is to
 * use the Parameter objects to create all combinations of equivalence classes for each parameter.
 */


public class Parameter {

	private String name;
	private ArrayList<String> equivalenceClasses;
	
	/**
	 * Constructor: Parameter
	 * Default constructor used to initialized the instance variables to a default value.
	 * used in FileParser -> parseParameters
	 */
	public Parameter() {
		this.name = "";
		this.equivalenceClasses = new ArrayList<String>();
	}



	/**
	 *
	 * Constructor: Parameter
	 * Constructor used for creating method for getting combinations from coverage groups
	 */
	public Parameter(String name, ArrayList<String> equivalenceClasses) {
		this.name = name;
		this.equivalenceClasses = equivalenceClasses;
	}



	/**
	 * method: addParam()
	 * This method will add the equivalence classes of a particular parameter to an arrayList
	 * @param equivClassName
	 * used in FileParser -> parseParameters
	 */
	public void addParam(String equivClassName) {
		equivalenceClasses.add(equivClassName);
	}


	/**
	 * method: getName
	 * This method gets the name of a parameter
	 * @return returns the name of the parameter
	 * used in FileUploadController -> processFile, writeOutputTestCases
	 * 		   testParameters -> testNameGetters_Setters, testAddParam
	 *
	 */
	public String getName() {
		return name;
	}


	/**
	 * method: setName
	 * This method sets the name of a parameter
	 * @param name
	 * used in FileParser -> parseParameters
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * method: getEquivalenceClasses
	 * This method gets the arrayList of equivalence classes for a parameter
	 * @return returns the arrayList of equivalence class names
	 * used in FileUploadController -> processFile
	 * 		   testFileParse -> testCreateCombos, testCountParameter
	 * 		   testParameter -> testEquivClassGetters_Setters
	 */
	public ArrayList<String> getEquivalenceClasses() {
		return equivalenceClasses;
	}


	/**
	 * method: setEquivalenceClasses
	 * This method sets the arrayList
	 * @param equivalenceClasses
	 * used in testParameters -> testEquivClassGetters_Setters, testAddParam
	 */
	public void setEquivalenceClasses(ArrayList<String> equivalenceClasses) {
		this.equivalenceClasses = equivalenceClasses;
	}
	
}
