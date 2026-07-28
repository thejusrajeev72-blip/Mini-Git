package com.minigit.core;

import com.minigit.models.Commit;
import com.minigit.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Handles version restoration and rollback operations
 */
public class VersionRestore {
    private RepositoryManager repoManager;
    private CommitManager commitManager;
    
    public VersionRestore(RepositoryManager repoManager, CommitManager commitManager) {
        this.repoManager = repoManager;
        this.commitManager = commitManager;
    }
    
    /**
     * Restores working directory to a specific commit (checkout)
     */
    public void checkout(String commitHash) throws IOException, ClassNotFoundException {
        // Find full commit hash if partial hash provided
        if (commitHash.length() < 64) {
            commitHash = commitManager.findCommitByPartialHash(commitHash);
        }
        
        Commit commit = commitManager.loadCommit(commitHash);
        
        System.out.println("Checking out commit: " + commitHash.substring(0, 8));
        System.out.println("Message: " + commit.getMessage());
        System.out.println();
        
        // Restore all files from the commit
        Map<String, String> fileHashes = commit.getFileHashes();
        
        for (Map.Entry<String, String> entry : fileHashes.entrySet()) {
            String filename = entry.getKey();
            String fileHash = entry.getValue();
            
            File sourceFile = new File(repoManager.getObjectsDirectory(), fileHash);
            File destFile = new File(repoManager.getWorkingDirectory(), filename);
            
            FileUtil.copyFile(sourceFile, destFile);
            System.out.println("Restored: " + filename);
        }
        
        // Update HEAD
        repoManager.getRepository().setHead(commitHash);
        repoManager.saveRepository();
        
        System.out.println("\nSuccessfully checked out commit " + commitHash.substring(0, 8));
    }
    
    /**
     * Restores a single file from a specific commit
     */
    public void checkoutFile(String filename, String commitHash) 
            throws IOException, ClassNotFoundException {
        
        // Find full commit hash if partial hash provided
        if (commitHash.length() < 64) {
            commitHash = commitManager.findCommitByPartialHash(commitHash);
        }
        
        Commit commit = commitManager.loadCommit(commitHash);
        
        String fileHash = commit.getFileHashes().get(filename);
        if (fileHash == null) {
            throw new IOException("File '" + filename + "' does not exist in commit " + 
                                commitHash.substring(0, 8));
        }
        
        File sourceFile = new File(repoManager.getObjectsDirectory(), fileHash);
        File destFile = new File(repoManager.getWorkingDirectory(), filename);
        
        FileUtil.copyFile(sourceFile, destFile);
        
        System.out.println("Restored '" + filename + "' from commit " + commitHash.substring(0, 8));
    }
    
    /**
     * Creates a backup of the current working directory state
     */
    public void createBackup(String backupName) throws IOException, ClassNotFoundException {
        File backupDir = new File(repoManager.getWorkingDirectory().getParent(), 
                                 backupName + "_backup");
        
        if (backupDir.exists()) {
            throw new IOException("Backup already exists: " + backupName);
        }
        
        FileUtil.createDirectory(backupDir);
        
        // Copy all tracked files
        Commit currentCommit = commitManager.getCurrentCommit();
        if (currentCommit != null) {
            for (String filename : currentCommit.getFileHashes().keySet()) {
                File sourceFile = new File(repoManager.getWorkingDirectory(), filename);
                if (sourceFile.exists()) {
                    File destFile = new File(backupDir, filename);
                    FileUtil.copyFile(sourceFile, destFile);
                }
            }
        }
        
        System.out.println("Created backup: " + backupDir.getAbsolutePath());
    }
    
    /**
     * Resets working directory to match HEAD commit
     */
    public void resetHard() throws IOException, ClassNotFoundException {
        String head = repoManager.getRepository().getHead();
        if (head == null) {
            System.out.println("No commits yet, nothing to reset to");
            return;
        }
        
        System.out.println("WARNING: This will discard all uncommitted changes!");
        System.out.print("Are you sure? (yes/no): ");
        
        checkout(head);
    }
}
