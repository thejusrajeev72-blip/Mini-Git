package com.minigit.commands;

import com.minigit.core.CommitManager;
import com.minigit.core.DiffViewer;
import com.minigit.core.RepositoryManager;

/**
 * Command to show differences between file versions
 */
public class DiffCommand implements Command {
    private RepositoryManager repoManager;
    private CommitManager commitManager;
    private DiffViewer diffViewer;
    
    public DiffCommand(RepositoryManager repoManager, CommitManager commitManager, DiffViewer diffViewer) {
        this.repoManager = repoManager;
        this.commitManager = commitManager;
        this.diffViewer = diffViewer;
    }
    
    @Override
    public void execute(String[] args) throws Exception {
        repoManager.loadRepository();
        
        if (args.length == 1) {
            // Compare working file with HEAD
            diffViewer.diffWorkingFile(args[0]);
        } else if (args.length == 3) {
            // Compare file between two commits
            String filename = args[0];
            String commit1 = commitManager.findCommitByPartialHash(args[1]);
            String commit2 = commitManager.findCommitByPartialHash(args[2]);
            diffViewer.diff(filename, commit1, commit2);
        } else {
            System.out.println("Usage: " + getUsage());
        }
    }
    
    @Override
    public String getUsage() {
        return "diff <file> [commit1] [commit2]";
    }
    
    @Override
    public String getDescription() {
        return "Show differences in a file (between commits or with working directory)";
    }
}
