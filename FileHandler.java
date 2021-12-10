import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  
import java.util.LinkedList;

public class FileHandler 
{
    File currentFile;
    String currentFileName;
    String directoryPath;
    String date_and_time;
    String currentDirectoryPath;
    File log;


    public void init()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");  
        LocalDateTime now = LocalDateTime.now();  
        date_and_time = dtf.format(now);
        currentDirectoryPath = System.getProperty("user.dir");
        currentFileName = "List@"+date_and_time+".DBS";
        currentFile = null;
        log = null;
        directoryPath = "";
    }

    public void createNewFile()
    {
        try 
        {    
            init();

            directoryPath = makeDirectory(currentDirectoryPath+File.separator+"Students Lists");

            currentFile = makeFile(directoryPath,currentFileName);
        
            log = makeFile(currentDirectoryPath, "logs.txt");
        }
        catch(Exception e)
        {
        }
    }

    public void loadDatabaseFile(File currentFile, String currentDirectoryPath, String directoryPath)
    {
        init();
        this.currentFile = currentFile;
        this.currentDirectoryPath = currentDirectoryPath;
        this.currentFileName = currentFile.getName();
        this.directoryPath = directoryPath;

        StringBuilder dateAndTime = new StringBuilder("");
        for(int i = 0; i<currentFileName.length(); i++)
            if(i>4 && i<currentFileName.indexOf('.'))
                dateAndTime.append(currentFileName.charAt(i));
        
        date_and_time = new String(dateAndTime.toString());

        
    }

    public void loadLogFile()
    {
        currentDirectoryPath = System.getProperty("user.dir");
        log = makeFile(currentDirectoryPath, "logs.txt");
    }
    /*
    public void createFile() throws IOException
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");  
        LocalDateTime now = LocalDateTime.now();  
        String date_and_time = dtf.format(now);

        File fileDir=createDirectory(System.getProperty("user.dir")+File.separator+"Student Lists");
        File file = new File("Student List @ "+date_and_time+".txt");
        System.out.println(file.getAbsolutePath());
        if(file.createNewFile())
            System.out.println("File created "+ file.getName());
        else
            System.out.println("File could not be created");
        try(FileWriter fw=new FileWriter(file))
        {
            fw.write("INSERT ME WHERE MY JAR IS");
            fw.flush();
            fw.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex);
        }
    }

    public File createDirectory(String directoryPath) throws IOException 
    {
        File dir = new File(directoryPath);
        if (dir.exists()) 
            return dir;
        if (dir.mkdirs()) 
            return dir;
        throw new IOException("Failed to create directory '" + dir.getAbsolutePath() + "' for an unknown reason.");
    }
    */

    public File makeFile(String dir, String filename)
    {
        File file = new File(dir+File.separator+filename);
        try
        {
            if(file.exists())
                System.out.println("File exists");
            else if(file.createNewFile())
                System.out.println("File created at path "+file);
        }
        catch(Exception e)
        {
        }
        return file;
    }

    public void deleteCurrentFile()
    {
        if(currentFile.delete())
            System.out.println("File has been deleted");
        currentFileName = "";
    }

    public boolean clearContents(File file)
    {
        try
        {
            file.delete();
            file.createNewFile();
        }   
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    public boolean writeOnFile(File file,String text)
    {
        try(FileWriter fw = new FileWriter(file,true))
        {
            fw.write(text);
            fw.write("\n");
            fw.flush();
            fw.close();
        }
        catch (Exception e) 
        {
            return false;
        }
        return true;
    } 

    public String makeDirectory(String dirPath) throws IOException
    {
        File dir = new File(dirPath);
        if(dir.exists())
        {
            System.out.println("Directory exists");
            return dir.getAbsolutePath();
        }
        if(dir.mkdirs())
        {
            System.out.println("Directory has been made");
            return dir.getAbsolutePath();
        }
        throw new IOException("Failed to create directory '" + dir.getAbsolutePath() + "' for an unknown reason.");
    }

    public LinkedList<String> readFromFile(File file) throws Exception
    {
        LinkedList<String> data = new LinkedList<>();
        try(FileReader fileReader = new FileReader(file))
        {
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while((line=br.readLine())!=null)
                data.add(line);
            fileReader.close();
        }
        catch(Exception e)
        {
        }
        return data;
    }

    public void openFile(String path)
    {
        currentFile = new File(path);
    }
    
    public void logAddStudent(Student student)
    {
        try 
        {
            writeOnFile(log,"<ADD> "+student.toString()+" has been added to the list '"+currentFileName+"' at "+date_and_time+"\n");
        } 
        catch (Exception e) 
        {
        }
    }

    public void logRemoveStudent(Student student)
    {
        try 
        {
            writeOnFile(log,"<REMOVE> "+student.toString()+" has been removed from the list '"+currentFileName+"' at "+date_and_time+"\n");
        } 
        catch (Exception e) 
        {
        }
    }

    public void logSearchStudent(Student student)
    {
        try 
        {
            writeOnFile(log,"<SEARCH> "+student.toString()+" has been searched in the list '"+currentFileName+"' at "+date_and_time+"\n");
        } 
        catch (Exception e) 
        {
        }
    }

    public void logModifyStudent(Student student)
    {
        try 
        {
            writeOnFile(log,"<MODIFY> "+student.toString()+", details of which have been modified in the list '"+currentFileName+"' at "+date_and_time+"\n");
        } 
        catch (Exception e) 
        {
        }
    }

    public void logCreateFile()
    {
        try 
        {
            writeOnFile(log,"<NEW> "+currentFileName+" has been created in "+directoryPath+" at "+getCurrentDateAndTime()+"\n");
        } 
        catch (Exception e) 
        {
        }
    }

    public void logLoadFile()
    {
        try 
        {
            writeOnFile(log,"<LOAD> "+currentFileName+" has been loaded from "+directoryPath+" at "+getCurrentDateAndTime()+"\n");
        } 
        catch (Exception e) 
        {
        }
    }

    public void logSaveFile()
    {
        try 
        {
            writeOnFile(log,"<SAVE> "+currentFileName+" has been saved in "+directoryPath+" at "+getCurrentDateAndTime()+"\n");
        } 
        catch (Exception e) 
        {
        }
    }

    public void logDeleteFile()
    {
        try 
        {
            writeOnFile(log,"<DELETE> "+currentFileName+" has been deleted in "+directoryPath+" at "+getCurrentDateAndTime()+"\n");
        } 
        catch (Exception e) 
        {
        }
    }

    public String getCurrentDateAndTime()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now);
    }    
}