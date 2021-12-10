import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*; 

public class Main 
{
    StudentsList studentsList;
    FileHandler fileHandler;
    Window window;
    LinkedList<String> logs;

    boolean isLogged = false;

    LinkedList<Undo> undoStack;

    Main()
    {
        logs = new LinkedList<>();
        undoStack = new LinkedList<>();
        studentsList = new StudentsList();

        try
        {
            fileHandler = new FileHandler();
        }
        catch(Exception e)
        {
            System.exit(1);
        }
        
        window = new Window();

        fileHandler.init();
        studentsList.init();


        window.addEntryButton.addActionListener(e -> 
        {
            if(addEntry())
                window.statusString = "STATUS: New Entry has been added"; 
            else
                window.statusString = "ERROR: Insufficient Details to add entry";
            addStudentsToJList(); 
            window.setDefaultAllComponents();    
        });

        window.entriesJList.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(isLogged)
                    return;
                super.mouseClicked(e);
                if(e.getClickCount() == 2)
                {
                    studentsList.selectedIndex = window.entriesJList.getSelectedIndex();
                    studentsList.currentStudent = studentsList.getStudentByIndex(studentsList.selectedIndex);
                    window.indexString = Integer.toString(studentsList.selectedIndex);
                    setFieldsByStudentDetails(studentsList.getStudentByIndex(studentsList.selectedIndex));
                    window.setDefaultAllComponents();
                }
            }
        });

        window.modifyEntryButton.addActionListener(e -> 
        {
            if(window.isNoneSelected())
            {  
                window.statusString = "ERROR: No entry selected.";
                window.setDefaultAllComponents();
                return;
            }
            studentsList.selectedIndex = window.entriesJList.getSelectedIndex();
            studentsList.currentStudent = studentsList.getStudentByIndex(studentsList.selectedIndex);
            window.indexString = Integer.toString(studentsList.selectedIndex);
            if(modifyEntry())
                window.statusString = "STATUS: Entry no."+window.indexString+" has been modified.";
            else
                window.statusString = "ERROR: Insufficient Details to modify entry";
            addStudentsToJList(); 
            window.setDefaultAllComponents();
        });

        window.deleteEntryButton.addActionListener(e -> {
            if(window.isNoneSelected())
            {
                window.statusString = "ERROR: No entry selected.";
                window.setDefaultAllComponents();
                return;
            }  
            studentsList.selectedIndex = window.entriesJList.getSelectedIndex();
            window.indexString = Integer.toString(studentsList.selectedIndex);
            deleteEntry();
            window.statusString = "STATUS: Entry no."+window.indexString+" has been deleted.";
            addStudentsToJList();
            window.setDefaultAllComponents();
        });

        window.searchEntryButton.addActionListener(new ActionListener()
        {
            boolean isSearched = false;
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(isSearched)
                {
                    addStudentsToJList();
                    window.searchEntryButton.setText("SEARCH ENTRY");
                    window.clearEntryTexts();
                    window.setDefaultAllComponents();
                    isSearched=false;
                    return;
                }
                if(!searchEntry())
                {   
                    window.statusString = "ERROR: Insufficient Details to search for entry";
                    window.setDefaultAllComponents();
                    return;
                }
                addSearchToJList();
                window.searchEntryButton.setText("RETURN TO ORIGINAL DATABASE");
                window.setDefaultAllComponents();
                isSearched=true;
            }
        });



        window.newFileButton.addActionListener(e -> 
        {
            createFile();
            window.currentlyViewingMessage = "CURRENTLY VIEWING: "+fileHandler.currentFileName;
            window.statusString = "STATUS: New database file has been created";
            addStudentsToJList();
            window.setDefaultAllComponents();
        });

        window.saveFileButton.addActionListener(e ->
        {
            rewriteListOnFile(fileHandler.currentFile);
            window.statusString = "STATUS: File has been saved.";
            window.setDefaultAllComponents();
        });

        window.loadFileButton.addActionListener(e ->
        {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(window.frame) == JFileChooser.APPROVE_OPTION) 
            {
                fileHandler.loadDatabaseFile(chooser.getSelectedFile(), chooser.getCurrentDirectory().getParent(), chooser.getCurrentDirectory().toString());
                studentsList.init();
                window.currentlyViewingMessage = "CURRENTLY VIEWING: "+fileHandler.currentFileName;
                window.statusString = "STATUS: The File "+fileHandler.currentFileName+" has been loaded";
                inputData();
                addStudentsToJList();
                fileHandler.logLoadFile();
                window.setDefaultAllComponents();
            }
        });

        window.viewLogButton.addActionListener(e ->
        {
            if(isLogged)
            {
                if(window.isFileSelected)
                {
                    window.enableOperations();
                    addStudentsToJList(); 
                    window.currentlyViewingMessage = "CURRENTLY VIEWING: "+fileHandler.currentFileName;
                    window.statusString = "STATUS: The File "+fileHandler.currentFileName+" has been loaded";   
                }
                else
                {
                    window.currentlyViewingMessage = "CURRENTLY VIEWING: No file selected";
                    window.statusString = "STATUS: Program is working correctly.";
                    addEmptyListtoJList();
                }
                isLogged = false;
                window.viewLogButton.setText("VIEW LOG FILE"); 
            }
            else
            {
                fileHandler.loadLogFile();   
                window.disableOperations();
                isLogged = true;
                window.viewLogButton.setText("RETURN TO ORIGINAL DATABASE");
                window.currentlyViewingMessage = "CURRENTLY VIEWING: Event Log File";
                window.statusString = "STATUS: Log file "+fileHandler.log.getName()+" loaded.";
                try 
                {
                    logs = fileHandler.readFromFile(fileHandler.log);
                } 
                catch (Exception e1) 
                {
                    window.statusString = "ERROR: Could not load Log File.";
                }
                addLogtoJList();
            }
            window.setDefaultAllComponents();
        });

        window.deleteFileButton.addActionListener(e -> 
        {
            deleteFile();
            window.currentlyViewingMessage = "CURRENTLY VIEWING: No file selected";
            window.statusString = "STATUS: Current file has been deleted.";
            addEmptyListtoJList();
            window.setDefaultAllComponents();
        });
        
        window.undoButton.addActionListener(e ->
        {
            if(undoStack.isEmpty())
            {
                window.statusString = "STATUS: No action to be undone.";
                window.setDefaultAllComponents();
                return;
            }
            determineUndoAction(undoStack.pop());
            window.statusString = "STATUS: Last action has been undone.";
            window.setDefaultAllComponents();
        });

    }    

    public void createFile()
    {
        studentsList.init();
        fileHandler.createNewFile();
        fileHandler.logCreateFile();
    }

    public void deleteFile()
    {
        fileHandler.logDeleteFile();
        fileHandler.deleteCurrentFile();
    }

    public void setFieldsByStudentDetails(Student s)
    {
        window.firstNameString = s.getFirstName().toString();
        window.lastNameString = s.getLastName().toString();
        window.rollString = s.getRoll().toString();
        window.marksString = s.getMarks().toString();
        window.gradeString = s.getGrade().toString();
    }

    public void addStudentsToJList()
    {
        window.entryListModel.clear();
        for(Student i:studentsList.students)
            window.entryListModel.addElement(i.toString());
    }

    public void addStudentsFromJList()
    {
        studentsList.students.clear();
        for(Object i:window.entryListModel.toArray())
            studentsList.students.add((Student) i);
        
    }

    public void addSearchToJList()
    {
        window.entryListModel.clear();
        for(Student i:studentsList.searchedStudents)
            window.entryListModel.addElement(i.toString());
    }

    public void addEmptyListtoJList()
    {
        window.entryListModel.clear();
    }

    public void addLogtoJList()
    {
        window.entryListModel.clear();
        for(String i:logs)
            window.entryListModel.addElement(i);
    }

    public void rewriteListOnFile(File file)
    {
        fileHandler.clearContents(file);
        for(Student i:studentsList.students)
            fileHandler.writeOnFile(file, i.toString());
    }

    public boolean addEntry()
    {
        window.getInputStrings();
        if(aFieldIsEmpty())
            return false;
        studentsList.currentStudent = new Student(window.firstNameString,window.lastNameString,window.rollString,window.marksString,window.gradeString);
        studentsList.addEntry(studentsList.currentStudent);
        fileHandler.logAddStudent(studentsList.currentStudent);
        studentsList.selectedIndex = studentsList.getIndexOf(studentsList.currentStudent);
        undoStack.push(new Undo("ADD",studentsList.currentStudent,studentsList.selectedIndex));
        return true;
    }

    public boolean modifyEntry()
    {
        window.getInputStrings();
        if(aFieldIsEmpty())
            return false;
        Student tempStudent = new Student(studentsList.currentStudent);
        studentsList.currentStudent = new Student(window.firstNameString,window.lastNameString,window.rollString,window.marksString,window.gradeString);
        studentsList.modifyEntry(studentsList.currentStudent, studentsList.selectedIndex);
        fileHandler.logModifyStudent(studentsList.currentStudent);
        undoStack.push(new Undo("MODIFY",tempStudent,studentsList.selectedIndex));
        return true;
    }

    public void deleteEntry()
    {
        fileHandler.logRemoveStudent(studentsList.currentStudent);
        undoStack.push(new Undo("DELETE",studentsList.currentStudent,studentsList.selectedIndex));
        studentsList.deleteEntryAt(studentsList.selectedIndex);
    }

    public boolean searchEntry()
    {
        window.getInputStrings();

        if(!window.rollString.equals(""))
        {
            studentsList.searchEntry(window.firstNameString, window.lastNameString, window.rollString);
            fileHandler.logSearchStudent(studentsList.currentStudent);
        }
        else if(!(window.firstNameString.equals("") || window.lastNameString.equals("")))
        {
            studentsList.searchAllEntriesByName(window.firstNameString, window.lastNameString);
            fileHandler.logSearchStudent(studentsList.currentStudent);
        }
        else
            return false;
        
        if(studentsList.searchedStudents.isEmpty())
            window.statusString = "STATUS: No match Found.";
        return true;
    }

    public boolean aFieldIsEmpty()
    {
        return (window.firstNameString.equals("") || window.lastNameString.equals("") || window.rollString.equals("") || window.marksString.equals("") || window.gradeString.equals(""));
    }

    public boolean allFieldsAreEmpty()
    {
        return (window.firstNameString.equals("") && window.lastNameString.equals("") && window.rollString.equals("") && window.marksString.equals("") && window.gradeString.equals(""));
    }

    public void inputData()
    {   
        LinkedList<String> wholeData = new LinkedList<>();

        try
        {
            wholeData = fileHandler.readFromFile(fileHandler.currentFile);
        }
        catch(Exception e)
        {
            window.statusString = "ERROR: Cannot open that file";
        }

        for(String line:wholeData)
            studentsList.addEntry(Student.toStudent(line));    
    }

    public void determineUndoAction(Undo undo)
    {
        if(undo.action.equalsIgnoreCase("ADD"))
        {
            studentsList.deleteEntryAt(studentsList.getIndexOf(undo.student));
            addStudentsToJList();
        }
        else if(undo.action.equalsIgnoreCase("MODIFY"))
        {
            studentsList.modifyEntry(undo.student, undo.index);
            addStudentsToJList();
        }
        else if(undo.action.equalsIgnoreCase("DELETE"))
        {
            studentsList.students.add(undo.index, undo.student);
            addStudentsToJList();
        }
    }

    public static void main(String[] args) 
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run() 
            {
                try 
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } 
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) 
                {
                    System.exit(1);
                }
                new Main();
            }
        });
          
    }
}
