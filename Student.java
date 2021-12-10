import java.util.ArrayList;
import java.util.StringTokenizer;

public class Student
{
    Object firstName;
    Object lastName;
    Object roll;
    Object marks;
    Object grade;
    Student()
    {
        firstName = null;
        lastName = null;
        roll = null;
        marks = null;
        grade  = null;
    }

    Student(Object firstName, Object lastName, Object roll, Object marks, Object grade)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roll = roll;
        this.marks = marks;
        this.grade = grade;
    }

    Student(Student s)
    {
        this.firstName = s.firstName.toString();
        this.lastName = s.lastName.toString();
        this.roll = s.roll.toString();
        this.marks = s.marks.toString();
        this.grade = s.marks.toString();
    }

    public Object getFirstName()
    {
        return firstName;
    }

    public Object getLastName()
    {
        return lastName;
    }

    public Object getRoll()
    {
        return roll;
    }

    public Object getMarks()
    {
        return marks;
    }

    public Object getGrade()
    {
        return grade;
    }

    @Override
    public String toString()
    {
        return "Name: "+firstName+" "+lastName+" Roll: "+roll+" Marks: "+marks+" Grade: "+grade;
    }

    public static Student toStudent(String entry)
    {
        if(entry.equals(""))
            return null;
        ArrayList<String> words = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(entry);
        while(tokenizer.hasMoreTokens())
            words.add(tokenizer.nextToken());
        try 
        {
            return new Student(words.get(1),words.get(2),words.get(4),words.get(6),words.get(8));    
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        }
    }
}