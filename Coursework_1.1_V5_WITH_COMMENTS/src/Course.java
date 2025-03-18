
public class Course
{
	/*	====================================================================================
	 * 	Definition of encapsulated global variables
	 *  ====================================================================================
	 */
	private String courseCode;
	private String courseName;
	//private int [] courseEnrolledStudents = new int [100];
	
	/*	====================================================================================
	 * 	Getters and setters for encapsulation
	 *  ====================================================================================
	 */
	public String getCourseCode ()
	{
		return courseCode;
	}
	public void setCourseCode (String theCourseCode)
	{
		courseCode = theCourseCode;
	}
	
	public String getCourseName ()
	{
		return courseName;
	}
	public void setCourseName (String theCourseName)
	{
		courseName = theCourseName;
	}
	
	//public int [] getCourseEnrolledStudents ()
	//{
	//	return courseEnrolledStudents;
	//}
	//public void setCourseEnrolledStudents (int [] theCourseEnrolledStudents)
	//{
	//	courseEnrolledStudents = theCourseEnrolledStudents;
	//}
	
	/*	====================================================================================
	 * 	toString method to return all variables in a Course object for file storage
	 *  ====================================================================================
	 */
	public String toString ()
	{
		String outString = courseCode + "α" + courseName;
		return outString;
	}
}
