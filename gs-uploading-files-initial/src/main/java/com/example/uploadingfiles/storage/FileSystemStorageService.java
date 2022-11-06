package com.example.uploadingfiles.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Class:FileSystemStorageService
 * Purpose: Creates a rootLocation path where we store the uploaded files and its generated
 * combos text file.
 * Documented by Javier Zarate
 */
@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	/**
	 * Creates Path rootLocation as location ("upload-dir") from the StorageProperties Class
	 *
	 */
	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	/**
	 * This method deletes the contents of the rootLocation recursively upon running the application.
	 * This means that even if the folder has contents, it will be deleted.
	 */
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	/**
	 * Creates the rootLocation directory and all nonexistent parent directories
	 *
	 */

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	/**
	 * Store method passes the file into SyncStore
	 *
	 */
	@Override
	public void store(MultipartFile file) {
		syncStore(file);
	}

	/**
	 * SyncStore Checks if the uploaded file is the right extension and then moves it into the
	 * upload-dir folder
	 */

	private synchronized void syncStore(MultipartFile file) {

		try {

			File dir = new File(rootLocation.toString());
			deletePreviousStoredFiles(dir);
			checkForValidJSONFile(file);

			//gives us the path for the json file we uploaded
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}

			//writes the file bytes to the stream
			FileOutputStream stream = new FileOutputStream(destinationFile.toString());
			stream.write(file.getBytes());
			stream.close();
			
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	/**
	 * Method: deletePreviousStoredFiles
	 * Purpose: If the user wishes to upload another file, the method will
	 * delete the previously stored JSON and combos text file.
	 */

	private static void deletePreviousStoredFiles(File dir){
		for (File it : dir.listFiles())
		{
			if (!it.isDirectory())
			{
				it.delete();
			}
		}
	}

	/**
	 * Method: CheckForValidJSONFile
	 * Purpose: Checks to see if the uploaded file is a JSON file or an empty file
	 */
	private static void checkForValidJSONFile(MultipartFile file){
		//Filename is assigned the file the user is uploading
		String filename = file.getOriginalFilename();
		int lastIndex = filename.lastIndexOf('.');

		//extension is assigned the uploaded file's extension
		String extension = filename.substring(lastIndex, filename.length());

		//If the extension does not contain the .json extension we throw StorageException
		if (!(extension).equalsIgnoreCase(".json"))
		{
			throw new StorageException("Failed to store non-json file");
		}

		if (file.isEmpty())
		{
			throw new StorageException("Failed to store empty file.");
		}
	}

	/**
	 * loadAll Searches the root directory and returns a streamed path.
	 * Running the app for the first time, Refreshing the page, or uploading a file creates a new streamed path
	 */
	@Override
	public Stream<Path> loadAll() {

		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	/**
	 * loadAsResource creates and returns a resource URL
	 *
	 */

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);

			//Creates a Resource URL that contains our combos text file
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}

		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}

	}

	/**
	 * Returns the rootLocation of the created combos file.
	 * Ex. upload-dir\ExecutionQueueOnSave-combos.txt
	 *
	 */
	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}



}
