import java.util.*;

public class StudentsList
{
    LinkedList<Student> students;
    LinkedList<Student> searchedStudents;
    Student currentStudent;
    Integer selectedIndex;

    public void init()
    {
        students = new LinkedList<>();   
        searchedStudents = new LinkedList<>();
        currentStudent = null;
        selectedIndex = null;
    }

    public void addEntry(Student student)
    {
        students.add(student);
    }

    public void deleteEntryAt(int index)
    {
        
        students.remove(index);
    }

    public void deleteEntryOf(Student student)
    {
        students.remove(student);
    }

    public Student searchEntry(String first, String last, String roll)
    {
        searchedStudents.clear();
        for(Student s: students)
            if(s.roll.equals(roll))
            {
                currentStudent = s;
                searchedStudents.add(s);    
                return s;
            }
        return null;        
    }

    public void searchAllEntriesByName(String first, String last)
    {
        searchedStudents.clear();
        for(Student s: students)
            if(s.firstName.equals(first) && s.lastName.equals(last))
                searchedStudents.add(s);   
        currentStudent = searchedStudents.peekFirst();
    }

    public int getIndexOf(Student s)
    {
        return students.indexOf(s);
    }

    public Student getStudentByIndex(int index)
    {
        return students.get(index);
    }

    public boolean modifyEntry(Student student, int index)
    {
        try
        {
            students.set(index,student);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    public int getSize()
    {
        return students.size();
    }
}
