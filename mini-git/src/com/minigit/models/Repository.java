package com.minigit.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the repository state
 */
public class Repository implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String head; // Current commit hash (active version)
    private List<String> commits; // List of all commit hashes
    private Map<String, String> stagedFiles; // Files in staging area: filename -> file path
    
    public Repository() {
        this.head = null;
        this.commits = new ArrayList<>();
        this.stagedFiles = new HashMap<>();
    }
    
    public String getHead() {
        return head;
    }
    
    public void setHead(String head) {
        this.head = head;
    }
    
    public List<String> getCommits() {
        return new ArrayList<>(commits);
    }
    
    public void addCommit(String commitHash) {
        commits.add(commitHash);
    }
    
    public Map<String, String> getStagedFiles() {
        return new HashMap<>(stagedFiles);
    }
    
    public void stageFile(String filename, String filepath) {
        stagedFiles.put(filename, filepath);
    }
    
    public void clearStagedFiles() {
        stagedFiles.clear();
    }
    
    public boolean hasStagedFiles() {
        return !stagedFiles.isEmpty();
    }
}
