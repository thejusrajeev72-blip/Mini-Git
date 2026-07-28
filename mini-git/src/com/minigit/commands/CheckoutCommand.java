package com.minigit.commands;

import com.minigit.core.CommitManager;
import com.minigit.core.RepositoryManager;
import com.minigit.core.VersionRestore;

/**
 * Command to checkout (restore) a previous version
 */
public class CheckoutCommand implements Command {
    private RepositoryManager repoManager;
    private CommitManager commitManager;
    private VersionRestore versionRestore;
    
    public CheckoutCommand(RepositoryManager repoManager, CommitManager commitManager, 
                          VersionRestore versionRestore) {
        this.repoManager = repoManager;
        this.commitManager = commitManager;
        this.versionRestore = versionRestore;
    }
    
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: " + getUsage());
            return;
        }
        
        repoManager.loadRepository();
        
        if (args.length == 1) {
            // Checkout entire commit
            versionRestore.checkout(args[0]);
        } else if (args.length == 2) {
            // Checkout single file from commit
            versionRestore.checkoutFile(args[1], args[0]);
        } else {
            System.out.println("Usage: " + getUsage());
        }
    }
    
    @Override
    public String getUsage() {
        return "checkout <commit-hash> [file]";
    }
    
    @Override
    public String getDescription() {
        return "Restore working directory to a previous commit or restore a single file";
    }
}
