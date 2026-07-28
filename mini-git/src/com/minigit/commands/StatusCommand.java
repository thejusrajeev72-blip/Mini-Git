package com.minigit.commands;

import com.minigit.core.RepositoryManager;

/**
 * Command to show repository status
 */
public class StatusCommand implements Command {
    private RepositoryManager repoManager;
    
    public StatusCommand(RepositoryManager repoManager) {
        this.repoManager = repoManager;
    }
    
    @Override
    public void execute(String[] args) throws Exception {
        repoManager.loadRepository();
        repoManager.showStatus();
    }
    
    @Override
    public String getUsage() {
        return "status";
    }
    
    @Override
    public String getDescription() {
        return "Show the status of the working directory";
    }
}
