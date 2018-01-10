/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS_main;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author walte
 */
public class FileDir
{

    private boolean callSubDir;
    private String fileDir; //the directory name being sought
    private String path; //the directory path
    private boolean objectState; //path ok, file ok, pattern ok
    private Set<String> files = new HashSet<>();

    public FileDir(Boolean callSubDir, String fileDirectory, String rootFolder)
    {
        this.callSubDir = callSubDir; //recursive search in folder for files
        this.fileDir = fileDirectory; //get current folder to find file directory
        try {
            setDirPath(rootFolder); //Sets "Path" matching fileDir recursive call searches subfolders
            listFiles(); //lists files in a set
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        //this.printObj();
    }

    public void printObj()
    {
        System.out.println("callSubDir: " + this.isCallSubDir());
        System.out.println("fileDir: " + this.getFileDir());
        System.out.println("objectState: " + this.isObjectState());
    }

    public void setDirPath(String currentFolder) throws Exception
    {
        //Finds required folder from loaded system directory location
        File file = new File(currentFolder);
        //Recursively walk through file directory until fileDir is found;
        String[] names = file.list();

        for (String name : names) {
            if (new File(file + "\\" + name).isDirectory()) {
                if (name.equals(this.getFileDir())) {
                    this.setPath(new File(file + "\\" + name).getCanonicalPath());
                    this.setObjectState(true);
                   
                }
                else {
                    this.setDirPath(
                            new File(file + "\\" + name).getCanonicalPath());
                }
            }
        }

    }
    
   public void listFiles() {      
      File f;
      File[] paths;
      
      try {  
      
         f = new File(this.getPath());

         paths = f.listFiles();
         
         for(File aPath:paths) {
         
            addSetItem(aPath.toString());
         }
         
      } catch(Exception e) {
          // if any error occurs

      }
   }

    /**
     * Get the value of path
     *
     * @return the value of path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Set the value of path
     *
     * @param path new value of path
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * Get the value of fileDir
     *
     * @return the value of fileDir
     */
    public String getFileDir()
    {
        return fileDir;
    }

    /**
     * Set the value of fileDir
     *
     * @param fileDir new value of fileDir
     */
    public void setFileDir(String fileDir)
    {
        this.fileDir = fileDir;
    }

    /**
     * Get the value of objectState
     *
     * @return the value of objectState
     */
    public boolean isObjectState()
    {
        return objectState;
    }

    /**
     * Set the value of objectState
     *
     * @param objectState new value of objectState
     */
    public void setObjectState(boolean objectState)
    {
        this.objectState = objectState;
    }

    /**
     * Get the value of callSubDir
     *
     * @return the value of callSubDir
     */
    public boolean isCallSubDir()
    {
        return callSubDir;
    }

    /**
     * Set the value of callSubDir
     *
     * @param callSubDir new value of callSubDir
     */
    public void setCallSubDir(boolean callSubDir)
    {
        this.callSubDir = callSubDir;
    }
    
    public void addSetItem(String item)
    {
        this.files.add(item);
    }

    public Set<String> getFiles()
    {
        return files;
    }

}
