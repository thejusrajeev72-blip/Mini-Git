package com.minigit.commands;

import com.minigit.core.CommitManager;
import com.minigit.core.RepositoryManager;

/**
 * Command to show commit history
 */
public class LogCommand implements Command {
    private RepositoryManager repoManager;
    private CommitManager commitManager;
    
    public LogCommand(RepositoryManager repoManager, CommitManager commitManager) {
        this.repoManager = repoManager;
        this.commitManager = commitManager;
    }
    
    @Override
    public void execute(String[] args) throws Exception {
        repoManager.loadRepository();
        commitManager.showLog();
    }
    
    @Override
    public String getUsage() {
        return "log";
    }
    
    @Override
    public String getDescription() {
        return "Show commit history";
    }
}
