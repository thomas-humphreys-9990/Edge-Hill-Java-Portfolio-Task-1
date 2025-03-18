
public class Student
{
	/*	====================================================================================
	 * 	Definition of encapsulated global variables
	 *  ====================================================================================
	 */
	private int studentID;
	private String studentName;
	//private String [] studentCourseList = new String [100];
	
	/*	====================================================================================
	 * 	Getters and setters for encapsulation
	 *  ====================================================================================
	 */
	public int getStudentID ()
	{
		return studentID;
	}
	public void setStudentID (int theStudentID)
	{
		studentID = theStudentID;
	}
	
	public String getStudentName ()
	{
		return studentName;
	}
	public void setStudentName (String theStudentName)
	{
		studentName = theStudentName;
	}
	
	public int getStudentCourseList ()
	{
		return studentID;
	}
	//public void setStudentCourseList (String [] theStudentCourseList)
	//{
	//	studentCourseList = theStudentCourseList;
	//}
	
	/*	====================================================================================
	 * 	toString method to return all variables in a Student object for file storage
	 *  ====================================================================================
	 */
	public String toString ()
	{
		String outString = studentID + "α" + studentName;
		return outString;
	}
}
