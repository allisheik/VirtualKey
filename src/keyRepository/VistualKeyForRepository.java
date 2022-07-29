package keyRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

	public class VistualKeyForRepository {
		public static void main(String[] args) {
		FileOperation.createYourRepositoryIfNotPresent("YourRepository");
		HandleOpts.handleWelcomeScreenInput();
		}

		public static class MainMenu {
			public static void displayMenu() {
				String menu = "\n     Enter Your Choice Which You Want To Select     \n"
				+ "1) Retrieve Files in YourRepository folder in Ascending Order\n" + 
				  "2) Bussiness Level Operation Menu\n"
				+ "3) Exit From The Application\n";
				System.out.println(menu);
			}

			public static void bussinessLevelOperationMenu() {
				String fileMenu = "\n       Bussiness Level Operation Menu      \n"
				+ "1) Add a file and its content to YourRepository folder\n" + 
				  "2) Delete a file from YourRepository folder\n"
				+ "3) Search for a file from YourRepository folder and showing its contents\n" + 
				  "4) Exit From Bussiness Level Operation\n";

				System.out.println(fileMenu);
			}
		}

		public static class HandleOpts {
			public static void handleWelcomeScreenInput() {
				boolean running = true;
				Scanner sc = new Scanner(System.in);
				do {
					try {
						MainMenu.displayMenu();
						int input = sc.nextInt();

				switch (input) {
				case 1:
					FileOperation.displayAllFilesInRepository("YourRepository");
					break;
				case 2:
					HandleOpts.handleFileMenuOptions();
					break;
				case 3:
					System.out.println("Program exited successfully.");
					running = false;
					sc.close();
					System.exit(0);
					break;
				default:
					System.out.println("Please select a valid option from above.");
				}
			} catch (Exception e) {
				System.out.println(e.getClass().getName());
				handleWelcomeScreenInput();
			} 
		} while (running == true);
	}
	
	public static void handleFileMenuOptions() {
		boolean running = true;
		Scanner sc = new Scanner(System.in);
		do {
			try {
				MainMenu.bussinessLevelOperationMenu();
				FileOperation.createYourRepositoryIfNotPresent("YourRepository");
				
				int input = sc.nextInt();
				switch (input) {
				case 1:
					// File Add
					System.out.println("Enter the name of the file to be added to"
							+ " the \"YourRepository\" folder");
					String fileToAdd = sc.next();
					FileOperation.createFile(fileToAdd, sc);
					break;
				case 2:
					// File/Folder delete
					System.out.println("Enter the name of the file to be deleted"
							+ " from \"YourRepository\" folder");
					String fileToDelete = sc.next();
					File file = new File("C:\\Users\\USER\\eclipse-workspace\\VirtualKey"
							+ "\\YourRepository");

					boolean folder2 = new File(file, fileToDelete).exists();
                    System.out.println(folder2);
                    if (folder2 == true) {
                        File folder3 = new File(file, fileToDelete);
                        folder3.delete();
                        System.out.println("File successfully deleted");
                    } else {
                        System.out.println("file does not exist");
                    }
					break;
				case 3:
					System.out.println("Enter the name of the file to be searched"
							+ " from \"YourRepository\" folder");
					String fileName = sc.next();					
					FileOperation.searchFileDisplay(fileName, "YourRepository");
					break;
				case 4:
					// Exit
					System.out.println("Program exited successfully.");
					running = false;
					sc.close();
					System.exit(0);
				default:
					System.out.println("Please select a valid option from above.");
				}
			} catch (Exception e) {
				System.out.println(e.getClass().getName());
				handleFileMenuOptions();
			}
		} while (running == true);
	}
}
		
		public static class FileOperation {
			public static void createYourRepositoryIfNotPresent(String folderName) {
				File file = new File(folderName);
				if (!file.exists()) {
					file.mkdirs();
				}
			}

			public static void displayAllFilesInRepository(String path) {
				FileOperation.createYourRepositoryIfNotPresent("YourRepository");
	List<String> filesListNames = FileOperation.listFilesInDirectory(path, new ArrayList<String>());
				System.out.println("Displaying all files in ascending order\n");
				Collections.sort(filesListNames);
				for(String F:filesListNames) {
					System.out.print(F+" ");
				}System.out.println();
			}

	public static List<String> listFilesInDirectory(String path, List<String> fileListNames) {
				File dir = new File(path);
				File[] files = dir.listFiles();
				List<File> filesList = Arrays.asList(files);
				Collections.sort(filesList);
				if (files != null && files.length > 0) {
					for (File file : filesList) {
				if (file.isDirectory()) {
					fileListNames.add(file.getName());
					listFilesInDirectory(file.getAbsolutePath(), fileListNames);
				} else {
					fileListNames.add(file.getName());
						}
					}
				} else {
					System.out.println("Empty Directory");
					}
				System.out.println();
				return fileListNames;
			}

	public static void createFile(String fileToAdd, Scanner sc) {
		FileOperation.createYourRepositoryIfNotPresent("YourRepository");
		Path pathToFile = Paths.get("./YourRepository/" + fileToAdd);
		try {
			Files.createDirectories(pathToFile.getParent());
			Files.createFile(pathToFile);
			System.out.println(fileToAdd + " created successfully");

			System.out.println("Would you like to add some content to the file? (Y/N)");
			String choice = sc.next().toLowerCase();

			sc.nextLine();
			if (choice.equals("y")) {
				System.out.println("\n\nInput content and press enter\n");
				String content = sc.nextLine();
				Files.write(pathToFile, content.getBytes());
				System.out.println("\nContent written to file " + fileToAdd);
			}

		} catch (IOException e) {
			System.out.println("Failed to create file " + fileToAdd);
			System.out.println(e.getClass().getName());
		}
	}

	public static List<String> searchFileDisplay(String fileName, String path) throws IOException {
		List<String> fileListNames = new ArrayList<>();
		FileOperation.searchFileRecursively(path, fileName, fileListNames);

		if (fileListNames.isEmpty()) {
System.out.println("\n\n***** Couldn't find any file with given file name \"" + fileName + "\" *****\n\n");
		} else {
			System.out.println(fileName+" File Found");
			System.out.println("Content in the "+fileName+" is displayed below: ");
			for(String F:fileListNames) {
				try (FileReader reader = new FileReader(F)) {
					int data;
					
					while((data=reader.read())!=-1){
						
						System.out.print((char)data);
					}System.out.println();
				}
			}
		}

		return fileListNames;
	}

	public static void searchFileRecursively(String path, String fileName, List<String> fileListNames) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		List<File> filesList = Arrays.asList(files);

		if (files != null && files.length > 0) {
			for (File file : filesList) {

				if (file.getName().startsWith(fileName)) {
					fileListNames.add(file.getAbsolutePath());
				}
				if (file.isDirectory()) {
					searchFileRecursively(file.getAbsolutePath(), fileName, fileListNames);
				}
			}
		}
	}
}

}
