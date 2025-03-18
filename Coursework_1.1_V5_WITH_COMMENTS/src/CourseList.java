// This import statement allows the class to utilise file handling
import java.io.*;


public class CourseList
{
	/*	====================================================================================
	 * 	Global Variables
	 *  ====================================================================================
	 */
	Course [] courseList = new Course [100];
	int currentPosition = 0;
	
	/*	====================================================================================
	 * 	Add Course Method to add a Course object to the array of Course objects.
	 *  ====================================================================================
	 */
	public void addCourse (Course theCourse)
	{
		courseList [currentPosition] = theCourse;
		currentPosition ++;
	}
	
	/*	====================================================================================
	 * 	Encryption to store Course details in an encrypted format.
	 *  ====================================================================================
	 */
	public String encrypt (String toEncrypt)
	{
		String outString = "";
		
		for (int i = 0; i < toEncrypt.length (); i ++)
		{
			char currentChar = toEncrypt.charAt(i);
			int currentCharASCII = (int)currentChar;
			
			int encryptedCharASCII = currentCharASCII - 5;
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
			
			int encryptedCharASCII = currentCharASCII + 5;
			char encryptedChar = (char)encryptedCharASCII;
			
			outString += encryptedChar;
		}
		
		return outString;
	}
	
	/*	====================================================================================
	 * 	Write method to save Grade data in an encrypted format for later use, even after
	 *  the program has terminated.
	 *  ====================================================================================
	 */
	public void writeCoursesToFile ()
	{
		try
		{
			BufferedWriter bw = new BufferedWriter (new FileWriter ("Course.txt"));
			
			Course currentCourse = new Course ();
			
			for (int i = 0; i < currentPosition; i ++)
			{
				currentCourse = courseList [i];
				
				bw.write(encrypt (currentCourse.toString()) + "\n");
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
	 * 	Read method to ensure that Course data can be retrieved from the text file in a
	 *  decrypted format for use in the main program.
	 *  ====================================================================================
	 */
	public void readCoursesFromFile ()
	{
		try
		{
			BufferedReader br = new BufferedReader (new FileReader ("Course.txt"));
			String encryptedCurrentLine = br.readLine ();
			
			while (encryptedCurrentLine != null)
			{
				String decryptedDetails = decrypt (encryptedCurrentLine);
				String [] splitDetails = decryptedDetails.split("α");
				
				Course currentCourse = new Course ();
				currentCourse.setCourseCode (splitDetails [0]);
				currentCourse.setCourseName (splitDetails [1]);
				
				addCourse (currentCourse);
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
	
	/*	====================================================================================
	 * 	Linear search to find if a Course Code already exists
	 * 	====================================================================================
	 */	
	public boolean findCourseCode (String theCourseCode)
	{
		boolean found = false;
		
		for (int i = 0; i < currentPosition; i ++)
		{
			Course currentCourse = courseList [i];
			
			if (theCourseCode.equals (currentCourse.getCourseCode()))
			{
				found = true;
				//System.out.println ("DUPLICATE DETECTED");
				break;
			}
		}
		
		return found;
	}
	
	/*	====================================================================================
	 * 	Loop to return the name of a Course given the Course's code
	 * 	====================================================================================
	 */	
	public String getCourseNameFromCode (String theCode)
	{
		String courseName = "";
		
		for (int i = 0; i < currentPosition; i ++)
		{
			Course currentCourse = courseList [i];
			
			if (currentCourse.getCourseCode().equals(theCode))
			{
				courseName = currentCourse.getCourseName ();
				break;
			}
		}
		return courseName;
	}
}
