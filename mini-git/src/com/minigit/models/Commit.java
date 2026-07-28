package com.minigit.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a commit in the version control system
 */
public class Commit implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String commitHash;
    private String parentHash;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> fileHashes; // filename -> file content hash
    
    public Commit(String message, String parentHash, Map<String, String> fileHashes) {
        this.message = message;
        this.parentHash = parentHash;
        this.timestamp = LocalDateTime.now();
        this.fileHashes = new HashMap<>(fileHashes);
        this.commitHash = generateCommitHash();
    }
    
    private String generateCommitHash() {
        String data = message + timestamp.toString() + parentHash + fileHashes.toString();
        return com.minigit.utils.HashUtil.generateHash(data);
    }
    
    public String getCommitHash() {
        return commitHash;
    }
    
    public String getParentHash() {
        return parentHash;
    }
    
    public String getMessage() {
        return message;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public Map<String, String> getFileHashes() {
        return new HashMap<>(fileHashes);
    }
    
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    @Override
    public String toString() {
        return "Commit: " + commitHash + "\n" +
               "Parent: " + (parentHash != null ? parentHash : "null") + "\n" +
               "Date: " + getFormattedTimestamp() + "\n" +
               "Message: " + message;
    }
}
