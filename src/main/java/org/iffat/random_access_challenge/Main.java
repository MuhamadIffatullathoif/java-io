package org.iffat.random_access_challenge;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

record Employee(int employeeId, String firstName, String lastName, double salary) {
}

public class Main {

	private static final Map<Integer, Long> indexedIds = new HashMap<>();

	static {
		int recordsInFile = 0;
		try (RandomAccessFile randomAccessFile = new RandomAccessFile("employees.dat", "r")) {

			recordsInFile = randomAccessFile.readInt();
			System.out.println(recordsInFile + " records in file");
			for (int i = 0; i < recordsInFile; i++) {
				indexedIds.put(randomAccessFile.readInt(), randomAccessFile.readLong());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try (RandomAccessFile randomAccessFile = new RandomAccessFile("employees.dat", "rw")) {
			Scanner scanner = new Scanner(System.in);
			List<Integer> ids = new ArrayList<>(indexedIds.keySet());
			Collections.sort(ids);

			while (true) {
				System.out.println(ids);
				System.out.println("Enter an Employee Id or 0 to quit");
				if (!scanner.hasNext()) break;
				int employeeId = Integer.parseInt(scanner.nextLine());
				if (employeeId < 1) {
					break;
				}
				if (!ids.contains(employeeId)) {
					continue;
				}
				Employee employee = readRecord(randomAccessFile, employeeId);
				System.out.println("Enter new salary, nothing if no change:");
				try {
					double salary = Double.parseDouble(scanner.nextLine());
					randomAccessFile.seek(indexedIds.get(employeeId) + 4);
					randomAccessFile.writeDouble(salary);
					readRecord(randomAccessFile, employeeId);
				} catch (NumberFormatException ignore) {
					// If entered input is not a valid number, I'll ignore it.
				}
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	private static Employee readRecord(RandomAccessFile randomAccessFile, int employeeId) throws IOException {
		randomAccessFile.seek(indexedIds.get(employeeId));
		int id = randomAccessFile.readInt();
		double salary = randomAccessFile.readDouble();
		String first = randomAccessFile.readUTF();
		String last = randomAccessFile.readUTF();

		Employee employee = new Employee(id, first, last, salary);
		System.out.println(employee);
		return employee;
	}
}
