package com.minigit.commands;

import com.minigit.core.RepositoryManager;

/**
 * Command to create a commit
 */
public class CommitCommand implements Command {
    private RepositoryManager repoManager;
    
    public CommitCommand(RepositoryManager repoManager) {
        this.repoManager = repoManager;
    }
    
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: " + getUsage());
            return;
        }
        
        repoManager.loadRepository();
        
        // Join all args to form the commit message
        String message = String.join(" ", args);
        repoManager.commit(message);
    }
    
    @Override
    public String getUsage() {
        return "commit <message>";
    }
    
    @Override
    public String getDescription() {
        return "Create a commit with staged files";
    }
}
