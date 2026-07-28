package com.minigit.core;

import com.minigit.models.Commit;
import com.minigit.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Handles file comparison and diff viewing
 */
public class DiffViewer {
    private RepositoryManager repoManager;
    private CommitManager commitManager;
    
    public DiffViewer(RepositoryManager repoManager, CommitManager commitManager) {
        this.repoManager = repoManager;
        this.commitManager = commitManager;
    }
    
    /**
     * Compares a file between two commits
     */
    public void diff(String filename, String commit1Hash, String commit2Hash) 
            throws IOException, ClassNotFoundException {
        
        Commit commit1 = commitManager.loadCommit(commit1Hash);
        Commit commit2 = commitManager.loadCommit(commit2Hash);
        
        String fileHash1 = commit1.getFileHashes().get(filename);
        String fileHash2 = commit2.getFileHashes().get(filename);
        
        if (fileHash1 == null && fileHash2 == null) {
            System.out.println("File '" + filename + "' does not exist in either commit");
            return;
        }
        
        if (fileHash1 == null) {
            System.out.println("File '" + filename + "' was added in commit " + commit2Hash.substring(0, 8));
            displayFileContent(fileHash2, ">>> NEW FILE");
            return;
        }
        
        if (fileHash2 == null) {
            System.out.println("File '" + filename + "' was deleted in commit " + commit2Hash.substring(0, 8));
            displayFileContent(fileHash1, "<<< DELETED FILE");
            return;
        }
        
        if (fileHash1.equals(fileHash2)) {
            System.out.println("No changes in file '" + filename + "' between commits");
            return;
        }
        
        // Show differences
        System.out.println("=== Diff for " + filename + " ===");
        System.out.println("Commit 1: " + commit1Hash.substring(0, 8) + " - " + commit1.getMessage());
        System.out.println("Commit 2: " + commit2Hash.substring(0, 8) + " - " + commit2.getMessage());
        System.out.println();
        
        File file1 = new File(repoManager.getObjectsDirectory(), fileHash1);
        File file2 = new File(repoManager.getObjectsDirectory(), fileHash2);
        
        List<String> lines1 = FileUtil.readFileLines(file1);
        List<String> lines2 = FileUtil.readFileLines(file2);
        
        displayDiff(lines1, lines2);
    }
    
    /**
     * Displays differences between two file versions
     */
    private void displayDiff(List<String> lines1, List<String> lines2) {
        int maxLines = Math.max(lines1.size(), lines2.size());
        
        for (int i = 0; i < maxLines; i++) {
            String line1 = i < lines1.size() ? lines1.get(i) : null;
            String line2 = i < lines2.size() ? lines2.get(i) : null;
            
            if (line1 == null) {
                // Line added
                System.out.println("+ " + (i + 1) + ": " + line2);
            } else if (line2 == null) {
                // Line deleted
                System.out.println("- " + (i + 1) + ": " + line1);
            } else if (!line1.equals(line2)) {
                // Line modified
                System.out.println("- " + (i + 1) + ": " + line1);
                System.out.println("+ " + (i + 1) + ": " + line2);
            } else {
                // Line unchanged
                System.out.println("  " + (i + 1) + ": " + line1);
            }
        }
    }
    
    /**
     * Displays file content with a label
     */
    private void displayFileContent(String fileHash, String label) throws IOException {
        File file = new File(repoManager.getObjectsDirectory(), fileHash);
        List<String> lines = FileUtil.readFileLines(file);
        
        System.out.println(label);
        for (int i = 0; i < lines.size(); i++) {
            System.out.println((i + 1) + ": " + lines.get(i));
        }
    }
    
    /**
     * Compares working directory file with committed version
     */
    public void diffWorkingFile(String filename) throws IOException, ClassNotFoundException {
        Commit currentCommit = commitManager.getCurrentCommit();
        
        if (currentCommit == null) {
            System.out.println("No commits yet");
            return;
        }
        
        String committedFileHash = currentCommit.getFileHashes().get(filename);
        if (committedFileHash == null) {
            System.out.println("File '" + filename + "' is not tracked in current commit");
            return;
        }
        
        File workingFile = new File(repoManager.getWorkingDirectory(), filename);
        if (!workingFile.exists()) {
            System.out.println("File '" + filename + "' has been deleted from working directory");
            return;
        }
        
        File committedFile = new File(repoManager.getObjectsDirectory(), committedFileHash);
        
        List<String> committedLines = FileUtil.readFileLines(committedFile);
        List<String> workingLines = FileUtil.readFileLines(workingFile);
        
        if (committedLines.equals(workingLines)) {
            System.out.println("No changes in file '" + filename + "'");
            return;
        }
        
        System.out.println("=== Changes in " + filename + " ===");
        System.out.println("(Comparing working directory with HEAD commit)\n");
        displayDiff(committedLines, workingLines);
    }
}
