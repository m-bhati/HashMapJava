
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Assignment3 {

	public static void main(String[] args) throws InterruptedException{

		try {

			int hashTableCapacity = Integer.parseInt(args[0]);
			String methodType = args[1];
			String inputFile = args[2];

			MyHashTable_<Pair<String, String>, Student_> myhashTable = new DHHashTable<Pair<String, String> , Student_>(hashTableCapacity);
			
			if (methodType.equals("DH")) {
				myhashTable = new DHHashTable<Pair<String, String> , Student_>(hashTableCapacity);
			}
			else if (methodType.equals("SCBST")) {
				myhashTable = new SCBSTHashTable<Pair<String, String> , Student_>(hashTableCapacity);
			}

			BufferedReader reader = new BufferedReader(new FileReader(inputFile));

			String line = reader.readLine();

			while(line != null) {
				String[] tokens = line.split(" ");

				if (tokens[0].equals("insert")) {
					Student_ student = new Student(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
					Pair<String, String> pair = Pair.createPair(student.fname(), student.lname());
					int operations = myhashTable.insert(pair,student);
					System.out.println(operations);
				}
				else if (tokens[0].equals("update")) {
					Student_ student = new Student(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
					Pair<String, String> pair = Pair.createPair(student.fname(), student.lname());
					int operations = myhashTable.update(pair,student);
					System.out.println(operations);
				}
				else if (tokens[0].equals("contains")) {
					Pair<String, String> pair = Pair.createPair(tokens[1], tokens[2]);
					boolean contains = myhashTable.contains(pair);
					if (contains) {
						System.out.println("T");
					}
					else {
						System.out.println("F");
					}
				}
				else if (tokens[0].equals("delete")){
					Pair<String, String> pair = Pair.createPair(tokens[1], tokens[2]);
					int operations = myhashTable.delete(pair);
					System.out.println(operations);
				}
				else if (tokens[0].equals("get")) {
					try {
						Pair<String, String> pair = Pair.createPair(tokens[1], tokens[2]);
						Student_ studentObj = myhashTable.get(pair);
						System.out.println(studentObj.fname() +" "+studentObj.lname()+ " "+studentObj.hostel()+" "+studentObj.department()+" "+studentObj.cgpa());
					}catch (NotFoundException e) {
						 System.out.println("E");
					}
				}
				else if (tokens[0].equals("address")) {
					try {
						Pair<String, String> pair = Pair.createPair(tokens[1], tokens[2]);
						String address = myhashTable.address(pair);
						System.out.println(address);
					}catch (NotFoundException e) {
						 System.out.println("E");
					}
				}
				else {
					System.out.println("invalid action");
				}
				line = reader.readLine();

			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}