public class Undo 
{
    public String action;
    public Student student;
    public int index;

    Undo(String a, Student s, int i)
    {
        action = a;
        student = s;
        index = i;
    }
    
}
