package org.iffat.managing_files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

	public static void main(String[] args) {

//		File oldFile = new File("students.json");
//		File newFile = new File("student-activity.json");
//		if (oldFile.exists()) {
//			oldFile.renameTo(newFile);
//			System.out.println("File renamed successfully");
//		} else {
//			System.out.println("File doesn't exists");
//		}

//		Path oldPath = Path.of("students.json");
//		Path newPath = Path.of("files2/student-activity.json");
//
//		try {
//			Files.createDirectories(newPath.subpath(0, newPath.getNameCount() - 1));
//			Files.move(oldPath, newPath);
//			System.out.println(oldPath + " moved (renamed to) -->" + newPath);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		Path fileDir = Path.of("files2");
		Path resourceDir = Path.of("resource");
		try {
			Files.copy(fileDir, resourceDir);
			System.out.println("Directory renamed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
