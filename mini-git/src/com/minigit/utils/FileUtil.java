package com.minigit.utils;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for file operations
 */
public class FileUtil {
    
    /**
     * Copies a file from source to destination
     */
    public static void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    
    /**
     * Reads entire file content as string
     */
    public static String readFileContent(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }
    
    /**
     * Writes content to a file
     */
    public static void writeFileContent(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes());
    }
    
    /**
     * Reads file line by line
     */
    public static List<String> readFileLines(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }
    
    /**
     * Deletes a directory recursively
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
    
    /**
     * Lists all files in a directory (non-recursive)
     */
    public static List<File> listFiles(File directory) {
        List<File> fileList = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }
    
    /**
     * Checks if a file exists
     */
    public static boolean fileExists(String path) {
        return new File(path).exists();
    }
    
    /**
     * Creates directory if it doesn't exist
     */
    public static void createDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
