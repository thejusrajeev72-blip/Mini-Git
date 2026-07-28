package com.minigit.core;

import com.minigit.models.Commit;
import com.minigit.models.Repository;
import com.minigit.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Core class for repository management
 */
public class RepositoryManager {
    private static final String MINIGIT_DIR = ".minigit";
    private static final String OBJECTS_DIR = "objects";
    private static final String COMMITS_DIR = "commits";
    private static final String REPO_FILE = "repository.dat";
    
    private File workingDirectory;
    private File minigitDirectory;
    private File objectsDirectory;
    private File commitsDirectory;
    private Repository repository;
    
    public RepositoryManager(String workingPath) {
        this.workingDirectory = new File(workingPath);
        this.minigitDirectory = new File(workingDirectory, MINIGIT_DIR);
        this.objectsDirectory = new File(minigitDirectory, OBJECTS_DIR);
        this.commitsDirectory = new File(minigitDirectory, COMMITS_DIR);
    }
    
    /**
     * Initializes a new repository
     */
    public void initRepository() throws IOException {
        if (minigitDirectory.exists()) {
            throw new IOException("Repository already initialized");
        }
        
        FileUtil.createDirectory(minigitDirectory);
        FileUtil.createDirectory(objectsDirectory);
        FileUtil.createDirectory(commitsDirectory);
        
        repository = new Repository();
        saveRepository();
        
        System.out.println("Initialized empty Mini Git repository in " + minigitDirectory.getAbsolutePath());
    }
    
    /**
     * Loads existing repository
     */
    public void loadRepository() throws IOException, ClassNotFoundException {
        if (!minigitDirectory.exists()) {
            throw new IOException("Not a Mini Git repository. Use 'init' to initialize.");
        }
        
        File repoFile = new File(minigitDirectory, REPO_FILE);
        repository = SerializationUtil.deserialize(repoFile, Repository.class);
    }
    
    /**
     * Saves repository state
     */
    public void saveRepository() throws IOException {
        File repoFile = new File(minigitDirectory, REPO_FILE);
        SerializationUtil.serialize(repository, repoFile);
    }
    
    /**
     * Checks if repository exists
     */
    public boolean isRepository() {
        return minigitDirectory.exists();
    }
    
    /**
     * Adds file to staging area
     */
    public void addFile(String filename) throws IOException {
        File file = new File(workingDirectory, filename);
        
        if (!file.exists()) {
            throw new IOException("File not found: " + filename);
        }
        
        if (!file.isFile()) {
            throw new IOException("Not a file: " + filename);
        }
        
        repository.stageFile(filename, file.getAbsolutePath());
        saveRepository();
        
        System.out.println("Added file to staging area: " + filename);
    }
    
    /**
     * Creates a commit with staged files
     */
    public void commit(String message) throws IOException {
        if (!repository.hasStagedFiles()) {
            throw new IOException("No files staged for commit. Use 'add' to stage files.");
        }
        
        if (message == null || message.trim().isEmpty()) {
            throw new IOException("Commit message cannot be empty");
        }
        
        Map<String, String> fileHashes = new HashMap<>();
        
        // Store each staged file with content-based naming
        for (Map.Entry<String, String> entry : repository.getStagedFiles().entrySet()) {
            String filename = entry.getKey();
            String filepath = entry.getValue();
            File file = new File(filepath);
            
            String fileHash = HashUtil.generateFileHash(file);
            fileHashes.put(filename, fileHash);
            
            // Store file content in objects directory using hash
            File objectFile = new File(objectsDirectory, fileHash);
            if (!objectFile.exists()) {
                FileUtil.copyFile(file, objectFile);
            }
        }
        
        // Create commit
        Commit commit = new Commit(message, repository.getHead(), fileHashes);
        
        // Save commit object
        File commitFile = new File(commitsDirectory, commit.getCommitHash());
        SerializationUtil.serialize(commit, commitFile);
        
        // Update repository
        repository.addCommit(commit.getCommitHash());
        repository.setHead(commit.getCommitHash());
        repository.clearStagedFiles();
        saveRepository();
        
        System.out.println("Created commit: " + commit.getCommitHash().substring(0, 8));
        System.out.println("Message: " + message);
    }
    
    /**
     * Shows status of working directory
     */
    public void showStatus() throws IOException {
        System.out.println("=== Mini Git Status ===");
        System.out.println("Current HEAD: " + (repository.getHead() != null ? 
            repository.getHead().substring(0, 8) : "No commits yet"));
        System.out.println();
        
        if (repository.hasStagedFiles()) {
            System.out.println("Staged files:");
            for (String filename : repository.getStagedFiles().keySet()) {
                System.out.println("  " + filename);
            }
        } else {
            System.out.println("No files staged");
        }
        System.out.println();
    }
    
    public Repository getRepository() {
        return repository;
    }
    
    public File getObjectsDirectory() {
        return objectsDirectory;
    }
    
    public File getCommitsDirectory() {
        return commitsDirectory;
    }
    
    public File getWorkingDirectory() {
        return workingDirectory;
    }
}
