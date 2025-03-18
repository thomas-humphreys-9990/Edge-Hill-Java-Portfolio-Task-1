
public class Grade
{
	/*	====================================================================================
	 * 	Definition of encapsulated global variables
	 *  ====================================================================================
	 */
	private int gradeStudentID;
	private String gradeCourseCode;
	private String gradeValue;
	
	/*	====================================================================================
	 * 	Getters and setters for encapsulation
	 *  ====================================================================================
	 */
	public int getGradeStudentID ()
	{
		return gradeStudentID;
	}
	public void setGradeStudentID (int theGradeStudentID)
	{
		gradeStudentID = theGradeStudentID;
	}
	
	public String getGradeCourseCode ()
	{
		return gradeCourseCode;
	}
	public void setGradeCourseCode (String theGradeCourseCode)
	{
		gradeCourseCode = theGradeCourseCode;
	}
	
	public String getGradeValue ()
	{
		return gradeValue;
	}
	public void setGradeValue (String theGradeValue)
	{
		gradeValue = theGradeValue;
	}
	
	/*	====================================================================================
	 * 	toString method to return all variables in a Grade object for file storage
	 *  ====================================================================================
	 */
	public String toString ()
	{
		String outString = gradeStudentID + "α" + gradeCourseCode + "α" + gradeValue;
		return outString;
	}
}
