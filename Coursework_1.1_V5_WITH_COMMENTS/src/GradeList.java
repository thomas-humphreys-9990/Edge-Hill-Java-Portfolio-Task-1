import java.io.*;

public class GradeList
{
	/*	====================================================================================
	 * 	Global Variables
	 *  ====================================================================================
	 */
	Grade [] gradeList = new Grade [1000];
	int currentPosition = 0;
	
	/*	====================================================================================
	 * 	Enrol Method to add a Grade object to the array of Grades.
	 *  ====================================================================================
	 */
	public void enrolStudent (Grade theGrade)
	{
		gradeList [currentPosition] = theGrade;
		currentPosition ++;
	}
	
	/*	====================================================================================
	 * 	Assign grade to update the Grade value
	 * 	====================================================================================
	 */
	public void assignGrade (int thePosition, double gradePercentage)
	{
		String newGradeValue = gradePercentage + ": ";
		
		if (gradePercentage >= 70)
		{
			newGradeValue = newGradeValue + "First Class";
			System.out.println ("The student passed in First Class");
		}
		else if (gradePercentage >= 60)
		{
			newGradeValue = newGradeValue + "Upper Second Class";
			System.out.println ("The student passed in Upper Second Class");
		}
		else if (gradePercentage >= 50)
		{
			newGradeValue = newGradeValue + "Lower Second Class";
			System.out.println ("The student passed in Lower Second Class");
		}
		else if (gradePercentage >= 40)
		{
			newGradeValue = newGradeValue + "Third Class";
			System.out.println ("The student passed in Third Class");
		}
		else
		{
			newGradeValue = newGradeValue + "Fail";
			System.out.println ("The student did not pass");
		}
		
		gradeList [thePosition].setGradeValue (newGradeValue);
	}
	
	/*	====================================================================================
	 * 	Encryption to store Grade details in an encrypted format.
	 *  ====================================================================================
	 */
	public String encrypt (String toEncrypt)
	{
		String outString = "";
		
		for (int i = 0; i < toEncrypt.length (); i ++)
		{
			char currentChar = toEncrypt.charAt(i);
			int currentCharASCII = (int)currentChar;
			
			int encryptedCharASCII = currentCharASCII - 4;
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
			
			int encryptedCharASCII = currentCharASCII + 4;
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
	public void writeGradesToFile ()
	{
		try
		{
			BufferedWriter bw = new BufferedWriter (new FileWriter ("Grade.txt"));
			
			Grade currentGrade = new Grade ();
			
			for (int i = 0; i < currentPosition; i ++)
			{
				currentGrade = gradeList [i];
				
				bw.write(encrypt (currentGrade.toString()) + "\n");
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
	 * 	Read method to ensure that Grade data can be retrieved from the text file in a
	 *  decrypted format for use in the main program.
	 *  ====================================================================================
	 */
	public void readCoursesFromFile ()
	{
		try
		{
			BufferedReader br = new BufferedReader (new FileReader ("Grade.txt"));
			String encryptedCurrentLine = br.readLine ();
			
			while (encryptedCurrentLine != null)
			{
				String decryptedDetails = decrypt (encryptedCurrentLine);
				String [] splitDetails = decryptedDetails.split("α");
				
				Grade currentGrade = new Grade ();
				currentGrade.setGradeStudentID (Integer.parseInt(splitDetails[0]));
				currentGrade.setGradeCourseCode (splitDetails [1]);
				currentGrade.setGradeValue (splitDetails [2]);
				
				enrolStudent (currentGrade);
				encryptedCurrentLine = br.readLine ();
			}
			
			br.close ();
		}
		catch (Exception unWritable)
		{
			System.out.println ("Error when reading to file");
			unWritable.printStackTrace ();
		}
	}
	
	/*	====================================================================================
	 * 	Linear search to find if an enrolment/grade already exists
	 * 	====================================================================================
	 */
	public boolean findDuplicate (String theCourseCode, int theStudentID)
	{
		boolean found = false;
		
		for (int i = 0; i < currentPosition; i ++)
		{
			Grade currentGrade = gradeList [i];
			
			if (theCourseCode.equals (currentGrade.getGradeCourseCode()) && (theStudentID == currentGrade.getGradeStudentID()))
			{
				found = true;
				//System.out.println ("DUPLICATE DETECTED");
				break;
			}
		}
		
		return found;
	}
	
	/*	====================================================================================
	 * 	Loop to return all grades that are N/A
	 * 	====================================================================================
	 */
	public int [] getNAPositions ()
	{
		int[] thePositions = new int [currentPosition];
		int positionsArrayPosition = 0;
		for (int j = 0; j < currentPosition; j ++)
		{
			thePositions [j] = -1;
		}
		
		for (int i = 0; i < currentPosition; i ++)
		{
			Grade currentGrade = gradeList [i];
			
			if (currentGrade.getGradeValue().equals("N/A"))
			{
				thePositions [positionsArrayPosition] = i;
				positionsArrayPosition ++;
			}
		}
		return(thePositions);
	}
	
	/*	====================================================================================
	 * 	Loop to return all Courses that a Student is enroled on
	 * 	====================================================================================
	 */
	public Grade [] getStudentCourses (int studentID)
	{
		Grade [] enroled = new Grade [currentPosition];
		int enroledPosition = 0;
		
		for (int i = 0; i < currentPosition; i ++)
		{
			Grade currentGrade = gradeList [i];
			
			if (currentGrade.getGradeStudentID () == studentID)
			{
				enroled [enroledPosition] = currentGrade;
				enroledPosition ++;
			}
		}
		
		return enroled;
	}
	
	/*	====================================================================================
	 * 	Calculation to generate a Student's average score
	 * 	====================================================================================
	 */
	public double generateAverageScore (int studentID)
	{
		double average = 0.0;
		double total = 0.0;
		int numCoursesGraded = 0;
		
		for (int i = 0; i < currentPosition; i ++)
		{
			Grade currentGrade = gradeList [i];
			
			if (currentGrade.getGradeStudentID() == studentID)
			{
				String theGradeValue = currentGrade.getGradeValue();
				
				if (theGradeValue.equals("N/A") == false)
				{
					numCoursesGraded ++;
					
					theGradeValue = theGradeValue.replace(": First Class", "");
					theGradeValue = theGradeValue.replace(": Upper Second Class", "");
					theGradeValue = theGradeValue.replace(": Lower Second Class", "");
					theGradeValue = theGradeValue.replace(": Third Class", "");
					theGradeValue = theGradeValue.replace(": Fail", "");
						
					double currentPercentage = Double.parseDouble(theGradeValue);
					
					total = total + currentPercentage;
				}
			}
		}
		
		if (numCoursesGraded != 0)
		{
			average = total / numCoursesGraded;
		}
		
		return average;
	}
	
	/*	====================================================================================
	 * 	Loop to return all Students on a Course
	 * 	====================================================================================
	 */
	public Grade [] returnStudentIDCourse (String theCourseCode)
	{
		Grade [] grades = new Grade [currentPosition];
		int gradesPosition = 0;
		
		
		for (int i = 0; i < currentPosition; i ++)
		{
			Grade currentGrade = gradeList [i];
			
			if (currentGrade.getGradeCourseCode().equalsIgnoreCase(theCourseCode))
			{
				grades [gradesPosition] = currentGrade;
				gradesPosition ++;
			}
		}
		
		return grades;
	}
}

