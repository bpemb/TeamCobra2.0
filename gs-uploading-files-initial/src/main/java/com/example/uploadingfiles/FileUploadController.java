package com.example.uploadingfiles;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import com.example.uploadingfiles.fileParsing.DataObject;
import com.example.uploadingfiles.fileParsing.ExpectedResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.uploadingfiles.fileParsing.FileParser;
import com.example.uploadingfiles.fileParsing.Parameter;
import com.example.uploadingfiles.storage.StorageFileNotFoundException;
import com.example.uploadingfiles.storage.StorageService;


/**
 * Class: FileUploadController
 * purpose: decides the routes that the API takes when a file is uploaded
 * We did not create this file but modified it to fit our needs
 * Source: https://spring.io/guides/gs/uploading-files/
 */
@Controller
public class FileUploadController {

	private final StorageService storageService;
	private final FileParser fileParser; // add this instance variable in order to use our custom methods

	// update the constructor to use our instance variable
	@Autowired
	public FileUploadController(StorageService storageService, FileParser fileParser) {
		this.storageService = storageService;
		this.fileParser = fileParser;
	}


	/**
	 * method: listUploadedFiles
	 * renders user view of webpage when the page is loaded
	 * remains unchanged
	 * @param model
	 * @return String
	 * @throws IOException
	 */
	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
								.build().toUri().toString())
						.collect(Collectors.toList()));

		return "uploadForm";
	}

	/**
	 * method: serveFile
	 * gets a specific file resource in the upload-dir folder
	 * remained unchanged
	 * @param filename
	 * @return ResourceEntity<Resource>
	 */
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	/**
	 * method: processFile
	 * parses the passed file using FileParser and writes the output file using the combinations used in the passed file
	 * @param file
	 * @param fileNameNoExt
	 * @throws Exception
	 */
	public void processFile(MultipartFile file, String fileNameNoExt) throws Exception {
		// store the json file in order to use it later
		storageService.store(file);
		// create the File object inside the upload-dir location. Create the
		// BufferedWriter to write to the File
		File outputFile = new File("upload-dir/" + fileNameNoExt + "-combos.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

		// parse the file and create the count variable and update it as you populate
		// the arrayList
		DataObject dataObject = fileParser.parseJSONFile("upload-dir/" + file.getOriginalFilename());

		//Saif
		//Prints out Parameters and CoverageGroups names
		try {
			//Parsing the contents of the JSON file
			JSONObject jsonObject = (JSONObject)new JSONParser().parse(new FileReader("upload-dir/" + file.getOriginalFilename()));
			//Retrieving the array
			JSONArray jsonArray = (JSONArray) jsonObject.get("CoverageGroups");
			//Cycles through the array, writes the Parameters and CoverageGroups names to the txt file
			//Also removes unwanted brackets and such
			for(Object e : jsonArray) {

				writer.write(e.toString().replace("{","").replace("}","").replace("\"","").replace(","," | ").replace(":",": ") + "\n");

			}
			writer.write("\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		ArrayList<Parameter> arrList = fileParser.parseParameters(dataObject);
		int count = 1;

		// create a list of expected results to compare with the combinations of parameters
		ArrayList<ExpectedResult> expectedResults = fileParser.parseExpectedResultsFromTestConditions(dataObject);

		// write to the file and update the count variable, so it can be used later.
		for (Parameter temp : arrList) {
			writer.write(
					"Parameter Name: " + temp.getName() + " | Equivalence Classes: " +
							temp.getEquivalenceClasses());
			writer.write("\n");
			count = count * temp.getEquivalenceClasses().size();
		}

		writer.write("\n");

		// create a matrix and populate it using the createCombos method. Pass in
		// arrList and count as parameters
		String[][] combos = fileParser.createAllCombinationsOfEquivalenceClasses(arrList, count);

		// create a map to keep track of which index corresponds to which parameter name.
		// make the map after generating the test cases in case the order of the parameters
		// changes somehow
		Map<String, Integer> parameterNameIndexMap = fileParser.getParamMap(arrList);

		// New method added
		writeOutputTestCases(combos, writer, arrList, expectedResults, parameterNameIndexMap);

		writer.close();
	}

	/**
	 * method: writeOutputTestCases
	 * writes the test cases when the file is parsed in FileParser.java into a .txt file for the user to read
	 * @param combos
	 * @param writer
	 * @param arrList
	 * @param expectedResults
	 * @param parameterNameIndexMap
	 * @throws Exception
	 */
	private void writeOutputTestCases(String[][] combos, BufferedWriter writer, ArrayList<Parameter> arrList,
									  ArrayList<ExpectedResult> expectedResults,
									  Map<String, Integer> parameterNameIndexMap) throws Exception{
		// this is used to write the test case combinations to the text file.
		int testCaseNumber = 1;

		for (String[] combo : combos) {
			if (testCaseNumber < 10) {
				writer.write("Test Case " + testCaseNumber + ":   ");
			}
			else if (testCaseNumber >= 10 && testCaseNumber < 100) {
				writer.write("Test Case " + testCaseNumber + ":  ");
			}
			else {
				writer.write("Test Case " + testCaseNumber + ": ");
			}
			testCaseNumber++;
			for (int column = 0; column < combo.length; column++) {
				String paramName = arrList.get(column).getName();
				String conjunction = column == 0 ? "When " : "and ";
				String output = conjunction + paramName + " = " + combo[column] + " ";
				writer.write(output);
			}

			// use a string builder so the concatenation of multiple expected results is
			// slightly more efficient
			StringBuilder expectedResultsStringBuilder = new StringBuilder("");
			for (ExpectedResult er : expectedResults) {
				Queue<String> condition = fileParser.prepareCondition(er.getCondition());
				if (fileParser.compareTestCaseWithCondtions(condition, combo, parameterNameIndexMap)) {
					expectedResultsStringBuilder.append(er.getName()).append(" ");
				}
			}
			if (expectedResultsStringBuilder.toString().equals("")) {
				expectedResultsStringBuilder.append("Invalid");
			}
			writer.write("- Expected result => " + expectedResultsStringBuilder.toString());

			writer.write("\n");
		}
	}

	/**
	 * method: directAPI
	 * takes the passed file and loads the completed output file to be displayed on the webpage
	 * also returns a badRequest resource if there is an issue with processFile()
	 *
	 * @param file
	 * @return ResourceEntity<Resource>
	 */
	@PostMapping("/api")
	@ResponseBody
	public ResponseEntity<Resource> directAPI(@RequestParam("file") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		int dotIndex = fileName.lastIndexOf(".");
		String fileNameNoExt = fileName.substring(0, dotIndex);
		
		try {
			processFile(file, fileNameNoExt);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.badRequest().body(null);
		}
		
		Resource res = storageService.loadAsResource(fileNameNoExt + "-combos.txt");
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + res.getFilename() + "\"")
				.body(res);
	}


	/**
	 * method: handleFileUpload
	 * stores submitted JSON file to be used and parsed to create combinations
	 * writes a new file with the result from processFile() and adds the file to the upload-dir folder
	 * modified to gain needed functionality
	 * @param file
	 * @param redirectAttributes
	 * @return String
	 */
	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		// create the text file name based on the JSON file
		String fileName = file.getOriginalFilename();
		int dotIndex = fileName.lastIndexOf(".");
		String fileNameNoExt = fileName.substring(0, dotIndex);

		try {
			processFile(file, fileNameNoExt);

			// catch and error and throw an error message. This gets rid of the server error
			// page
		} catch (Exception e) {
			// e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/";
		}

		// return a message to indicate the file was successfully submitted and that
		// they received their text file.
		redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "\n"
				+ "and received " + fileNameNoExt + "-combos.txt!");

		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
