package org.iffat.file_and_path;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

	public static void main(String[] args) {

		useFile("testfile.txt");
		usePath("pathFile.txt");
	}

	private static void useFile(String filename) {

		File file = new File(filename);
		boolean fileExists = file.exists();

		System.out.printf("File '%s' %s%n", filename,
				fileExists ? "exists." : "does not exist.");

		if (fileExists) {
			System.out.println("Deleting File: " + filename);
			fileExists = !file.delete();
		}

		if (!fileExists) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Something went wrong");
			}
			System.out.println("Created File: " + filename);
			if (file.canWrite()) {
				System.out.println("Would write to file here");
			}
		}
	}

	private static void usePath(String filename) {

		Path path = Path.of(filename);
		boolean fileExists = Files.exists(path);

		System.out.printf("File '%s' %s%n", filename,
				fileExists ? "exists." : "does not exist.");

		if (fileExists) {
			System.out.println("Deleting File: " + filename);
			try {
				Files.delete(path);
				fileExists = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!fileExists) {
			try {
				Files.createFile(path);
				System.out.println("Created File: " + filename);
				if (Files.isWritable(path)) {
					Files.writeString(path, """
							Here is some data,
							For my file,
							just to prove,
							Using the Files class and path are better
							""");
				}
				System.out.println("And I can read too");
				System.out.println("-".repeat(90));
				Files.readAllLines(path).forEach(System.out::println);
			} catch (IOException e) {
				System.out.println("Something went wrong");
			}
		}
	}
}
