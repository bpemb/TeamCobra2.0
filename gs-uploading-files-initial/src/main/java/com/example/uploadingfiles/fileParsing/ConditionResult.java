package com.example.uploadingfiles.fileParsing;

/**
 * Class: ConditionResult
 * Edited Class: Saif Shaikh
 * @author Brian Smithers, Baldwin Pemberton, Chase Harrod, Saif Shaikh, Javier Zarate Zaragoza
 * Version: 1.0
 * Course: ITEC 3870
 * Written: September 26th, 2022
 *
 * Purpose:
 * This class is used to check is something is valid or invalid
 * This class is also used to set something specific Invalid
 * Returns error message
 * Mainly used in FileParser, testFileParser
 */

public class ConditionResult {


    //Instance Variables
    boolean isvalid;
    String errorMessage;

    //used in testFileParser -> testIsConditionValid, testIsConditionInvalid, testIsConditionalid2, testIsConditionInvalid2
    public boolean isIsvalid() {
        return isvalid;
    }

    //getter - not used, delete later, not yet to make sure we don't need it
    public String getErrorMessage() {
        return errorMessage;
    }

    //setters - used in FileParser -> isConditionValid
    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    //used in FileParser -> isConditionValid
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
