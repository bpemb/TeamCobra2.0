package com.example.uploadingfiles.fileParsing;

/**
 * Class: ExpectedResult
 * @author David Marshall
 * Version: 1.0
 * Course: ITEC 3870
 * Written: February 16th, 2022
 *
 * Purpose: The purpose of this class is to be able to create ExpectedResult objects when parsing a JSON file.
 * The object will consist of a parameter name and the list of conditions it has. The goal is to
 * use the Parameter objects to create all combinations of equivalence classes for each parameter.
 *
 */
public class ExpectedResult {
    private String name;
    private String condition;

    /**
     * Constructor: ExpectedResult
     * Default constructor used to initialize the instance variables to a default value.
     */
    public ExpectedResult() {
        this.name = "";
        this.condition = "";
    }

    /**
     * Constructor: ExpectedResult
     * 2 arg constructor used to initialize the instance variables to given values.
     */
    public ExpectedResult(String name, String condition) {
        this.name = name;
        this.condition = condition;
    }

    /**
     * method: getName
     * This method gets the name of an expected result
     * @return returns the name of the expected result
     */
    public String getName() {
        return name;
    }

    /**
     * method: setName
     * This method sets the name of an expected result
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method: getCondition
     * This method gets the condition under which the result is expected
     * @return returns the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * method: setCondition
     * This method sets the condition under which the result is expected
     * @param condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }
}
