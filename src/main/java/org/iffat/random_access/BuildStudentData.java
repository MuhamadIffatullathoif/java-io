package org.iffat.random_access;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildStudentData {

	public static void build(String datFileName) {

		Path studentJson = Path.of("students.json");
		String dataFile = datFileName + ".dat";
		Map<Long, Long> indexedIds = new LinkedHashMap<>();

		try {
			Files.deleteIfExists(Path.of(dataFile));
			String data = Files.readString(studentJson);
			data = data.replaceFirst("^(\\[)", "")
					.replaceFirst("(\\])$", "");
			var records = data.split(System.lineSeparator());
			System.out.println("# of records = " + records.length);

			long startingPos = 4 + (16L + records.length);

			Pattern idPattern = Pattern.compile("studentId\":([0-9]+)");
			try (RandomAccessFile ra = new RandomAccessFile(dataFile, "rw")) {
				ra.seek(startingPos);
				for (String record : records) {
					Matcher matcher = idPattern.matcher(record);
					if (matcher.find()) {
						long id = Long.parseLong(matcher.group(1));
						indexedIds.put(id, ra.getFilePointer());
						ra.writeUTF(record);
					}
				}
				writeIndex(ra, indexedIds);
			} catch (IOException e) {
				throw new RuntimeException();
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	private static void writeIndex(RandomAccessFile randomAccessFile, Map<Long, Long> indexMap) {
		try {
			randomAccessFile.seek(0);
			randomAccessFile.writeInt(indexMap.size());
			for (var studentIdx : indexMap.entrySet()) {
				randomAccessFile.writeLong(studentIdx.getKey());
				randomAccessFile.writeLong(studentIdx.getValue());
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
