package com.minigit.core;

import com.minigit.models.Commit;
import com.minigit.utils.SerializationUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages commit operations and history
 */
public class CommitManager {
    private RepositoryManager repoManager;
    
    public CommitManager(RepositoryManager repoManager) {
        this.repoManager = repoManager;
    }
    
    /**
     * Shows commit history (log)
     */
    public void showLog() throws IOException, ClassNotFoundException {
        List<String> commits = repoManager.getRepository().getCommits();
        
        if (commits.isEmpty()) {
            System.out.println("No commits yet");
            return;
        }
        
        System.out.println("=== Commit History ===\n");
        
        // Show commits in reverse order (newest first)
        for (int i = commits.size() - 1; i >= 0; i--) {
            String commitHash = commits.get(i);
            Commit commit = loadCommit(commitHash);
            
            System.out.println(commit);
            System.out.println();
        }
    }
    
    /**
     * Shows detailed information about a specific commit
     */
    public void showCommit(String commitHash) throws IOException, ClassNotFoundException {
        Commit commit = loadCommit(commitHash);
        
        System.out.println("=== Commit Details ===");
        System.out.println(commit);
        System.out.println("\nFiles in this commit:");
        for (String filename : commit.getFileHashes().keySet()) {
            System.out.println("  " + filename);
        }
    }
    
    /**
     * Loads a commit object from file
     */
    public Commit loadCommit(String commitHash) throws IOException, ClassNotFoundException {
        File commitFile = new File(repoManager.getCommitsDirectory(), commitHash);
        if (!commitFile.exists()) {
            throw new IOException("Commit not found: " + commitHash);
        }
        return SerializationUtil.deserialize(commitFile, Commit.class);
    }
    
    /**
     * Gets the current commit (HEAD)
     */
    public Commit getCurrentCommit() throws IOException, ClassNotFoundException {
        String head = repoManager.getRepository().getHead();
        if (head == null) {
            return null;
        }
        return loadCommit(head);
    }
    
    /**
     * Gets commit by partial hash
     */
    public String findCommitByPartialHash(String partialHash) throws IOException {
        List<String> commits = repoManager.getRepository().getCommits();
        List<String> matches = new ArrayList<>();
        
        for (String commitHash : commits) {
            if (commitHash.startsWith(partialHash)) {
                matches.add(commitHash);
            }
        }
        
        if (matches.isEmpty()) {
            throw new IOException("No commit found matching: " + partialHash);
        }
        
        if (matches.size() > 1) {
            throw new IOException("Ambiguous commit hash. Multiple matches found.");
        }
        
        return matches.get(0);
    }
}
