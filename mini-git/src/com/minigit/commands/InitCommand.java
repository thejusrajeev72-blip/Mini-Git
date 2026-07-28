package com.minigit.commands;

import com.minigit.core.RepositoryManager;

/**
 * Command to initialize a new repository
 */
public class InitCommand implements Command {
    private RepositoryManager repoManager;
    
    public InitCommand(RepositoryManager repoManager) {
        this.repoManager = repoManager;
    }
    
    @Override
    public void execute(String[] args) throws Exception {
        repoManager.initRepository();
    }
    
    @Override
    public String getUsage() {
        return "init";
    }
    
    @Override
    public String getDescription() {
        return "Initialize a new Mini Git repository";
    }
}
