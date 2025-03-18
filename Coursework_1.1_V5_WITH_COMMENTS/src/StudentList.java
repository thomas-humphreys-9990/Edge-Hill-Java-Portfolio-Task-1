// This import statement allows the class to utilise file handling
import java.io.*;


public class StudentList
{
	/*	====================================================================================
	 * 	Global Variables
	 *  ====================================================================================
	 */
	Student [] studentList = new Student [100];
	int currentPosition = 0;
	
	/*	====================================================================================
	 * 	Add Student Method to add a Student object to the array of Student objects.
	 *  ====================================================================================
	 */
	public void addStudent (Student theStudent)
	{
		theStudent.setStudentID(currentPosition + 1);
		studentList [currentPosition] = theStudent;
		currentPosition ++;
	}
	
	/*	====================================================================================
	 * 	Encryption to store Student details in an encrypted format.
	 *  ====================================================================================
	 */
	public String encrypt (String toEncrypt)
	{
		String outString = "";
		
		for (int i = 0; i < toEncrypt.length (); i ++)
		{
			char currentChar = toEncrypt.charAt(i);
			int currentCharASCII = (int)currentChar;
			
			int encryptedCharASCII = currentCharASCII - 2;
			char encryptedChar = (char)encryptedCharASCII;
			
			outString += encryptedChar;
		}
		
		return outString;
	}
	
	/*	====================================================================================
	 * 	Decryption to convert cipher-text into plain-text to allow users to read it.
	 *  ====================================================================================
	 */
	public String decrypt (String encrypted)
	{
		String outString = "";
		
		for (int i = 0; i < encrypted.length (); i ++)
		{
			char currentChar = encrypted.charAt(i);
			int currentCharASCII = (int)currentChar;
			
			int encryptedCharASCII = currentCharASCII + 2;
			char encryptedChar = (char)encryptedCharASCII;
			
			outString += encryptedChar;
		}
		
		return outString;
	}
	
	/*	====================================================================================
	 * 	Write method to save Student data in an encrypted format for later use, even after
	 *  the program has terminated.
	 *  ====================================================================================
	 */
	public void writeStudentsToFile ()
	{
		try
		{
			BufferedWriter bw = new BufferedWriter (new FileWriter ("Student.txt"));
			
			Student currentStudent = new Student ();
			
			for (int i = 0; i < currentPosition; i ++)
			{
				currentStudent = studentList [i];
				
				bw.write(encrypt (currentStudent.toString()) + "\n");
				//bw.write(currentStudent.toString() + "\n");
			}
			
			bw.close();
		}
		catch (Exception unWritable)
		{
			System.out.println ("Error when writing to file");
			unWritable.printStackTrace ();
		}
	}
	
	/*	====================================================================================
	 * 	Read method to ensure that Student data can be retrieved from the text file in a
	 *  decrypted format for use in the main program.
	 *  ====================================================================================
	 */
	public void readStudentsFromFile ()
	{
		try
		{
			BufferedReader br = new BufferedReader (new FileReader ("Student.txt"));
			String encryptedCurrentLine = br.readLine ();
			
			while (encryptedCurrentLine != null)
			{
				String decryptedDetails = decrypt (encryptedCurrentLine);
				String [] splitDetails = decryptedDetails.split("α");
				//String [] splitDetails = encryptedCurrentLine.split("α");
				
				Student currentStudent = new Student ();
				currentStudent.setStudentID (Integer.parseInt(splitDetails [0]));
				currentStudent.setStudentName (splitDetails [1]);
				
				
				//System.out.println (splitDetails [0] + ", " + splitDetails [1]);
				
				
				addStudent (currentStudent);
				encryptedCurrentLine = br.readLine ();
			}
			
			br.close ();
		}
		catch (Exception unWritable)
		{
			System.out.println ("Error when writing to file");
			unWritable.printStackTrace ();
		}
	}
}
