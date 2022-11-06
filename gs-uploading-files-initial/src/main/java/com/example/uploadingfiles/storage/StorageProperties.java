package com.example.uploadingfiles.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Class: StorageProperties
 * Edited Class: Saif Shaikh
 * @author Brian Smithers, Baldwin Pemberton, Chase Harrod, Saif Shaikh, Javier Zarate Zaragoza
 * Version: 1.0
 * Course: ITEC 3870
 * Written: September 26th, 2022
 *
Purpose: This is a folder location for storing files
 */

@ConfigurationProperties("storage")
public class StorageProperties {

	private String location = "upload-dir";

	//getter
	//used in FileSystemStorageService -> FileSystemStorageService
	public String getLocation() {
		return location;
	}

	//setter
	// used in FileSystemStorageServiceTests -> saveAbsolutePathInFilenamePermitted
	public void setLocation(String location) {
		this.location = location;
	}

}
