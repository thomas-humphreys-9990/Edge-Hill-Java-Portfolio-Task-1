import java.util.*;

public class Main
{
	/*	====================================================================================
	 * 	Global variable objects to ensure that the other classes in this package can be
	 *  accessed, along with their methods and attributes.
	 * 	====================================================================================
	 */
	Scanner input = new Scanner (System.in);	
	StudentList sl = new StudentList ();
	CourseList cl = new CourseList ();
	GradeList gl = new GradeList ();
	
	/*	====================================================================================
	 * 	This method will make all the classes read from their respective files, to ensure
	 * 	that all saved data is accessible from said text files.
	 * 	====================================================================================
	 */
	public void readFromFiles ()
	{
		sl.readStudentsFromFile ();
		cl.readCoursesFromFile ();
		gl.readCoursesFromFile ();
	}
	
	/*	====================================================================================
	 * 	Add methods to prompt the user to add a new record to the system
	 * 	====================================================================================
	 */
	public void addNewStudent ()
	// Method to add a new student to the system
	{
		returnAllStudents ();
		// Shows all Students to the user so they know which records are already present in
		// the system.
		
		System.out.println ("Please enter the name of the new Student:\n(Enter e to return to options)");
		String newName = input.nextLine ();
		
		while (newName.equals(""))
		// Presence check to ensure that the user actually enters a name for the new Student
		{
			System.out.println ("Please enter the name of the new Student:\n(Enter e to return to options)");
			newName = input.nextLine ();
		}
		
		if (newName.equalsIgnoreCase("e") == false)
		// Allows a user to exit the method without adding a new Student
		{
			Student newStudent = new Student ();
			newStudent.setStudentName (newName);
			
			sl.addStudent (newStudent);
			sl.writeStudentsToFile ();
		}
		else
		{
			System.out.println ("Exit successful");
		}
	}
	
	public void addNewCourse ()
	// Method to add a new course to the system
	{
		boolean exitCode = false;
		boolean validCourseCode = false;
		String newCode = "";
		
		returnAllCourses ();
		// Returns all courses to the user to ensure that they don't repeat any course code
		
		
		while (!validCourseCode)
		// Validation to ensure that the user enters a valid course code, that is not already
		// present in the system.
		{
			System.out.println ("Please enter the code of the new Course:\n(Enter e to return to options)");
			newCode = input.nextLine ();
			
			if (newCode.equalsIgnoreCase("e"))
			// Allows the user to exit the method without adding a new course
			{
				exitCode = true;
				break;
			}
			
			if (newCode.equals("") == false)
			{
				if (!cl.findCourseCode(newCode))
				{
					validCourseCode = true;
				}
				else
				{
					System.out.println ("That Course Code already exists");
				}
			}
		}
		
		if (!exitCode)
		{
			System.out.println ("Please enter the name of the new Course:\n(Enter e to return to options)");
			String newName = input.nextLine ();
			
			while (newName.equals(""))
			{
				System.out.println ("Please enter the name of the new Course:\n(Enter e to return to options)");
				newName = input.nextLine ();
			}
			
			if (newName.equalsIgnoreCase("e"))
			{
				System.out.println ("Exit successful");
			}
			else
			{
				Course newCourse = new Course ();
				newCourse.setCourseCode(newCode);
				newCourse.setCourseName(newName);
				
				cl.addCourse(newCourse);
				cl.writeCoursesToFile();
			}
		}
		else
		{
			System.out.println ("Exit successful");
		}
	}
	
	public void addNewEnrolment ()
	{
		boolean exitStudent = false;
		
		returnAllStudents ();
		
		String strStudentID = "";
		int studentID = -1;
		String theCourseCode = "";
		
		boolean validStudent = false;
		
		while (!validStudent)
		{
			System.out.println ("Please enter the ID of the Student that you wish to enrol:\n(Enter e to return to options)");
			strStudentID = input.nextLine ();
			
			if (strStudentID != null)
			{
				if (strStudentID.equalsIgnoreCase ("e"))
				{
					System.out.println ("Exit successful");
					exitStudent = true;
					break;
				}
				else
				{
					try
					{
						studentID = Integer.parseInt(strStudentID);
						
						if (0 < studentID && studentID <= (sl.currentPosition))
						{
							validStudent = true;
						}
						else
						{
							System.out.println ("Please select one of the provided Student IDs");
						}
					}
					catch (Exception notAnInteger)
					{
						//notAnInteger.printStackTrace ();
						System.out.println ("Please enter an integer");
					}
				}
			}
		}
		
		Grade [] selectedStudentGrades = gl.getStudentCourses(studentID);
		
		Grade currentGrade = selectedStudentGrades [0];
		
		System.out.println ("===================================================================================");
		if (currentGrade != null)
		{
			System.out.println (sl.studentList [studentID - 1].getStudentName () + " is enroled on the following Courses:");
			int count = 0;
			
			while (currentGrade != null)
			{
				System.out.println ("RECORD " + (count + 1) + ":	Code: " + currentGrade.getGradeCourseCode()
									+ " || Name: " + cl.getCourseNameFromCode (currentGrade.getGradeCourseCode())
									+ " || Grade: " + currentGrade.getGradeValue ());
				
				count ++;
				currentGrade = selectedStudentGrades [count];
			}
		}
		else
		{
			System.out.println (sl.studentList [studentID - 1].getStudentName () + " is not yet enroled on any Courses");
		}
		
		System.out.println ("===================================================================================");
		
		if (!exitStudent)
		{
			returnAllCourses ();
			
			boolean validCourse = false;
			
			while (!validCourse)
			{
				System.out.println ("Please enter the Course Code of the Course you wish to enrol " + sl.studentList [studentID - 1].getStudentName() + " on:"
									+ "\n(Enter e to return to options)");
				theCourseCode = input.nextLine ();
				
				if (theCourseCode.equalsIgnoreCase ("e"))
				{
					System.out.println ("Exit successful");
					break;
				}
				else
				{
					if (cl.findCourseCode(theCourseCode))
					{
						if (!gl.findDuplicate(theCourseCode, studentID))
						{
							Grade newGrade = new Grade ();
							newGrade.setGradeCourseCode(theCourseCode);
							newGrade.setGradeStudentID(studentID);
							newGrade.setGradeValue("N/A");
							
							gl.enrolStudent (newGrade);
							gl.writeGradesToFile ();
							
							validCourse = true;
							
							System.out.println (sl.studentList [studentID - 1].getStudentName() + " is now enroled on " + cl.getCourseNameFromCode(newGrade.getGradeCourseCode ()));
						}
						else
						{
							System.out.println ("That enrolment already exists");
						}
					}
					else
					{
						System.out.println ("Please ensure that the Course Code is already in the system");
					}
				}
			}
		}
	}
	
	public void assignGrade ()
	{
		boolean exit = false;
		
		Grade selectedGrade = new Grade ();
		int [] thePositions = gl.getNAPositions();
		
		int upperBound = returnAllenrolments();
		boolean validInput = false;
		String userInput;
		int intUserInput = 0;
		boolean validScore = false;
		String score;
		double dblScore = 0;
		int selectedIndex = 0;
		
		if (upperBound != 0)
		{
			// Get the enrolment that the user wants to grade
			System.out.println ("Please enter the enrolment you want to grade (the number in the field labelled 'Enter:')\n(Enter e to return to options)");
			
			while (!validInput && !exit)
			{
				userInput = input.nextLine ();
				
				if (userInput.equalsIgnoreCase ("e"))
				{
					exit = true;
					System.out.println ("Exit successful");
				}
				else
				{
					try
					{
						intUserInput = Integer.parseInt(userInput);
						
						//System.out.println (upperBound);
						
						if (1 > intUserInput || intUserInput > upperBound)
						{
							System.out.println ("Please ensure that your input is an integer from the field labelled 'Enter:'\nTry again");
						}
						else
						{
							validInput = true;
							selectedIndex = thePositions [intUserInput - 1];
							selectedGrade = gl.gradeList [selectedIndex];
						}
					}
					catch (Exception notAnInteger)
					{
						// notAnInteger.printStackTrace ();
						System.out.println ("Please ensure that your input is an integer from the field labelled 'Enter:'\nTry again");
					}
				}
				
			}
			
			if (!exit)
			{
				// Take the actual grade
				System.out.println ("Please enter the percentage that "
									+ sl.studentList [selectedGrade.getGradeStudentID() - 1].getStudentName()
									+ " received for "
									+ cl.getCourseNameFromCode(selectedGrade.getGradeCourseCode()) + ":"
									+ "\n(Enter e to return to options)");
				
				while (!validScore && !exit)
				{
					score = input.nextLine ();
					
					if (score.equalsIgnoreCase("e"))
					{
						exit = true;
						System.out.println ("Exit successful");
					}
					else
					{
						try
						{
							dblScore = Double.parseDouble(score);
							
							if (0 > dblScore || dblScore > 100)
							{
								System.out.println ("Please ensure that your input is a positive number between 0 and 100\nTry again");
							}
							else
							{
								validScore = true;
							}
						}
						catch (Exception notAnInteger)
						{
							// notAnInteger.printStackTrace ();
							System.out.println ("Please ensure that your input is a positive number between 0 and 100\nTry again");
						}
					}
					
				}
				
				// Update the record
				
				if (!exit)
				{
					gl.assignGrade(selectedIndex, dblScore);
					gl.writeGradesToFile();
				}
			}
		}
		else
		{
			System.out.println ("All enrolments have been graded");
		}
	}
	
	
	public void returnAllStudents ()
	{
		for (int i = 0; i < sl.currentPosition; i ++)
		{
			Student currentStudent = sl.studentList [i];
			System.out.println ("RECORD " + (i+1) + ":	ID: " + currentStudent.getStudentID() + " || Name: "
								+ currentStudent.getStudentName ());
		}
	}
	
	public void returnAllCourses ()
	{
		for (int i = 0; i < cl.currentPosition; i ++)
		{
			Course currentCourse = cl.courseList [i];
			System.out.println ("RECORD " + (i+1) + ":	Code: " + currentCourse.getCourseCode() + " || Name: "
								+ currentCourse.getCourseName());
		}
	}
	
	public void returnAllGrades ()
	{
		for (int i = 0; i < gl.currentPosition; i ++)
		{
			Grade currentGrade = gl.gradeList [i];
			System.out.println ("RECORD " + (i+1) + ":	StudentID: " + currentGrade.getGradeStudentID() + " || Course Code: "
								+ currentGrade.getGradeCourseCode() + " || Grade: " + currentGrade.getGradeValue());
		}
	}
	
	public int returnAllenrolments ()
	{
		int[] positions = gl.getNAPositions();
		int i = 0;
		String currentPosition = positions [0] + "";
		
		while (currentPosition.equals("-1") == false)
		{
			Grade currentGrade = gl.gradeList [positions [i]];
			System.out.println ("Enter: " + (i + 1) + " || "
								+ "StudentID: " + currentGrade.getGradeStudentID() + " || "
								+ "StudentName: " + sl.studentList [currentGrade.getGradeStudentID() - 1].getStudentName() + " || "
								+ "Course Code: " + currentGrade.getGradeCourseCode() + " || "
								+ "Course Name: " + cl.getCourseNameFromCode(currentGrade.getGradeCourseCode()) + " || "
								+ "Grade: " + currentGrade.getGradeValue());
			
			i ++;
			
			if (i != positions.length)
			{
				currentPosition = positions [i] + "";
			}
			else
			{
				currentPosition = "";
			}
		}
		
		return i;
	}
	
	public void returnStudentCourses ()
	{
		boolean exitStudent = false;
		
		returnAllStudents ();
		
		String strStudentID = "";
		int studentID = -1;
		
		boolean validStudent = false;
		
		while (!validStudent)
		{
			System.out.println ("Please enter the ID of the Student that you want to see the Courses of:\n(Enter e to return to options)");
			strStudentID = input.nextLine ();
			
			if (strStudentID != null)
			{
				if (strStudentID.equalsIgnoreCase ("e"))
				{
					System.out.println ("Exit successful");
					exitStudent = true;
					break;
				}
				else
				{
					try
					{
						studentID = Integer.parseInt(strStudentID);
						
						if (0 < studentID && studentID <= (sl.currentPosition))
						{
							validStudent = true;
						}
						else
						{
							System.out.println ("Please select one of the provided Student IDs");
						}
					}
					catch (Exception notAnInteger)
					{
						//notAnInteger.printStackTrace ();
						System.out.println ("Please enter an integer");
					}
				}
			}
		}
		
		if (!exitStudent)
		{
			Grade [] selectedStudentGrades = gl.getStudentCourses(studentID);
			
			Grade currentGrade = selectedStudentGrades [0];
			
			if (currentGrade != null)
			{
				System.out.println (sl.studentList [studentID - 1].getStudentName () + " is enroled on the following Courses:");
				int count = 0;
				
				while (currentGrade != null)
				{
					System.out.println ("RECORD " + (count + 1) + ":	Code: " + currentGrade.getGradeCourseCode()
										+ " || Name: " + cl.getCourseNameFromCode (currentGrade.getGradeCourseCode())
										+ " || Grade: " + currentGrade.getGradeValue ());
					
					count ++;
					currentGrade = selectedStudentGrades [count];
				}
				
				System.out.println (sl.studentList [studentID - 1].getStudentName () +" has an average percentage score of: " + gl.generateAverageScore (studentID) + "%");
			}
			else
			{
				System.out.println (sl.studentList [studentID - 1].getStudentName () + " is not enroled on any Courses");
			}
		}
	}
	
	public void displayCourseStudents ()
	{
		returnAllCourses ();
		
		boolean exit = false;
		boolean validCourse = false;
		String theCourseCode = "";
		
		while (!validCourse)
		{
			System.out.println ("Please enter the Course Code of the Course you want to see the Students of:\n(Enter e to return to options)");
			theCourseCode = input.nextLine ();
			
			if (theCourseCode.equalsIgnoreCase ("e"))
			{
				System.out.println ("Exit successful");
				exit = true;
				break;
			}
			else
			{
				if (cl.findCourseCode(theCourseCode))
				{
					validCourse = true;
					
				}
				else
				{
					System.out.println ("Please ensure that the Course Code is already in the system");
				}
			}
		}
		
		if (!exit)
		{
			Grade [] grades = gl.returnStudentIDCourse (theCourseCode);
			int currentPosition = 0;
			Grade currentGrade = grades [currentPosition];
			
			while (currentGrade != null)
			{
				System.out.println ("Student " + (currentPosition + 1) + ":	ID: " + currentGrade.getGradeStudentID()
									+ " || Name: " + sl.studentList [currentGrade.getGradeStudentID() - 1].getStudentName()
									+ " || Grade Value: " + currentGrade.getGradeValue());
				
				currentPosition ++;
				currentGrade = grades [currentPosition];
			}
			
			if (grades [0] == null)
			{
				System.out.println ("No Students are enroled in " + cl.getCourseNameFromCode (theCourseCode));
			}
		}
	}
	
	public boolean displayOptions ()
	{
		boolean stop = false;
		boolean validOption = false;
		String strOption = "";
		int intOption = -1;
		
		System.out.println ("_________________________________________________________________________________\n"
							+ "Enter 1 to add a Student to the system\n"
							+ "Enter 2 to display all of a Student's courses with their average score\n"
							+ "Enter 3 to add a Course to the system\n"
							+ "Enter 4 to display all Students on a Course\n"
							+ "Enter 5 to enrol a Student onto a Course\n"
							+ "Enter 6 to Grade a Student's Course\n"
							+ "Enter 7 to display all Students\n"
							+ "Enter 8 to display all Courses\n"
							+ "Enter e to exit\n"
							+ "‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
		
		while (!validOption)
		{
			strOption = input.nextLine ();
			
			if (strOption.equalsIgnoreCase ("e"))
			{
				stop = true;
				break;
			}
			else
			{
				try
				{
					intOption = Integer.parseInt(strOption);
					
					if (0 < intOption && intOption < 9)
					{
						validOption = true;
					}
					else
					{
						System.out.println ("Please enter a valid option from the options provided");
					}
				}
				catch (Exception notInt)
				{
					//notInt.printStackTrace();
					System.out.println ("Please enter a valid option from the options provided");
				}
			}
		}
		
		if (strOption.equalsIgnoreCase("e") == false)
		{
			switch (intOption)
			{
			case (1):
				// Add a student to the system
				addNewStudent (); 											//COMPLETE
				break;
			case (2):
				// Display Student Courses with average score
				returnStudentCourses (); 									//COMPLETE
				break;
			case (3):
				// Add a Course to the system
				addNewCourse (); 											//COMPLETE
				break;
			case (4):
				// Display all Students on a Course
				displayCourseStudents();
				break;
			case (5):
				// Enrol a Student onto a Course
				addNewEnrolment(); 											//COMPLETE
				break;
			case (6):
				// Grade a Student's Course
				assignGrade (); 											//COMPLETE
				break;
			case (7):
				// Display all Students
				returnAllStudents (); 										//COMPLETE
				break;
			case (8):
				// Display all Courses
				returnAllCourses (); 										//COMPLETE
				break;
			}
		}
		
		return stop;
		
		
	}
	
	public static void main (String [] args)
	{
		Main m = new Main ();
		m.readFromFiles ();
		
		System.out.println (" __________________________________________________________\n"
							+ "|\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/|\n"
							+ "|	 Welcome to the Student Grade Calculator!	   |\n"
							+ "|/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\|\n"
							+ " ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
		
		boolean repeat = true;
		
		while (repeat)
		{
			repeat = !m.displayOptions();
		}
	}
}
