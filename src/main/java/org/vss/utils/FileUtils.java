package org.vss.utils;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    /**
     * Get all the files and folders from a directory
     * @param directoryName to be looked
     */
    public static List<File> getFilesAndFolders(String directoryName){
        File directory = new File(directoryName);
        return Arrays.asList(directory.listFiles());
    }
    /**
     * Get all the files under a directory
     * @param directoryName to be looked
     */
    public static List<File> getFiles(String directoryName){
        File directory = new File(directoryName);
        File[] all = directory.listFiles();
        List<File> files = new ArrayList<>();
        for (File file : all){
            if (file.isFile()){
                files.add(file);
            }
        }
        return files;
    }
    /**
     * Get all the folder under a directory
     * @param directoryName to be looked
     */
    public static List<File> getFolders(String directoryName){
        File directory = new File(directoryName);
        File[] all = directory.listFiles();
        List<File> folders = new ArrayList<>();
        for (File file : all){
            if (file.isDirectory()){
                folders.add(file);
            }
        }
        return folders;
    }
    /**
     * Get all files from a directory and its subdirectories
     * @param directoryName to be looked
     */
    public static List<File> getFilesAndFilesSubDirectories(String directoryName){
        File directory = new File(directoryName);
        File[] all = directory.listFiles();
        List<File> files = new ArrayList<>();
        for (File file : all){
            if (file.isFile()){
                files.add(file);
            } else if (file.isDirectory()){
                files.addAll(getFilesAndFilesSubDirectories(file.getAbsolutePath()));
            }
        }
        return files;
    }
}
