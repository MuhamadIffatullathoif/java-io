package org.iffat.random_access;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

	private static final Map<Long, Long> indexedIds = new LinkedHashMap<>();
	private static int recordsInFile = 0;

	static {
		try (RandomAccessFile rb = new RandomAccessFile("student.idx", "r")) {
			loadIndex(rb, 0);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static void main(String[] args) {
		// BuildStudentData.build("student", true);

		try (RandomAccessFile randomAccessFile = new RandomAccessFile("student.dat", "r")) {
			//loadIndex(randomAccessFile, 0);
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter a Student Id or 0 to quit");
			while (scanner.hasNext()) {
				long studentId = Long.parseLong(scanner.nextLine());
				if (studentId < 1) {
					break;
				}
				randomAccessFile.seek(indexedIds.get(studentId));
				String targetedRecord = randomAccessFile.readUTF();
				System.out.println(targetedRecord);
				System.out.println("Enter another Student Id or 0 to quit");
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	private static void loadIndex(RandomAccessFile randomAccessFile, int indexPosition) {
		try {
			randomAccessFile.seek(indexPosition);
			recordsInFile = randomAccessFile.readInt();
			System.out.println(recordsInFile);
			for (int i = 0; i < recordsInFile; i++) {
				indexedIds.put(randomAccessFile.readLong(), randomAccessFile.readLong());
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
