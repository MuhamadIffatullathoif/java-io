package org.iffat.path_listings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

public class Main {

	public static void main(String[] args) {
		Path path = Path.of("this/is/several/folders/testing.csv");
		// printPathInfo(path);
		logStatement(path);
		extraInfo(path);
	}

	private static void printPathInfo(Path path) {

		System.out.println("Path: " + path);
		System.out.println("fileName= " + path.getFileName());
		System.out.println("parent= " + path.getParent());
		Path absoultPath = path.toAbsolutePath();
		System.out.println("Absolute Path = " + absoultPath);
		System.out.println("Absolute Path Root = " + absoultPath.getRoot());
		System.out.println("Root = " + path.getRoot());
		System.out.println("isAbsolute = " + path.isAbsolute());

		System.out.println(absoultPath.getRoot());
		int i = 1;
		var it = path.toAbsolutePath().iterator();
		while (it.hasNext()) {
			System.out.println(".".repeat(i++) + " " + it.next());
		}

		int pathParts = absoultPath.getNameCount();
		for (int j = 0; j < pathParts; j++) {
			System.out.println(".".repeat(j + 1) + " " + absoultPath.getName(j));
		}
		System.out.println("-".repeat(90));
	}

	private static void logStatement(Path path) {

		try {
			Path parent = path.getParent();
			if (!Files.exists(path)) {
				Files.createDirectories(parent);
			}
			Files.writeString(path, Instant.now() +
					": hello file world\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void extraInfo(Path path) {

		try {
			var atts = Files.readAttributes(path, "*");
			atts.entrySet().forEach(System.out::println);
			System.out.println(Files.probeContentType(path));
		} catch (IOException e) {
			System.out.println("Problem getting attributes");
		}
	}
}
