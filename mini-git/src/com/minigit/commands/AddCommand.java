package com.minigit.commands;

import com.minigit.core.RepositoryManager;

/**
 * Command to add files to staging area
 */
public class AddCommand implements Command {
    private RepositoryManager repoManager;
    
    public AddCommand(RepositoryManager repoManager) {
        this.repoManager = repoManager;
    }
    
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: " + getUsage());
            return;
        }
        
        repoManager.loadRepository();
        
        for (String filename : args) {
            repoManager.addFile(filename);
        }
    }
    
    @Override
    public String getUsage() {
        return "add <file1> [file2] [file3] ...";
    }
    
    @Override
    public String getDescription() {
        return "Add files to the staging area";
    }
}
