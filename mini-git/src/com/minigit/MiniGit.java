package com.minigit;

import com.minigit.commands.*;
import com.minigit.core.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class for Mini Git application
 * Smart Version Control System
 */
public class MiniGit {
    private RepositoryManager repoManager;
    private CommitManager commitManager;
    private DiffViewer diffViewer;
    private VersionRestore versionRestore;
    private Map<String, Command> commands;
    
    public MiniGit(String workingDirectory) {
        this.repoManager = new RepositoryManager(workingDirectory);
        this.commitManager = new CommitManager(repoManager);
        this.diffViewer = new DiffViewer(repoManager, commitManager);
        this.versionRestore = new VersionRestore(repoManager, commitManager);
        
        initializeCommands();
    }
    
    private void initializeCommands() {
        commands = new HashMap<>();
        
        commands.put("init", new InitCommand(repoManager));
        commands.put("add", new AddCommand(repoManager));
        commands.put("commit", new CommitCommand(repoManager));
        commands.put("status", new StatusCommand(repoManager));
        commands.put("log", new LogCommand(repoManager, commitManager));
        commands.put("diff", new DiffCommand(repoManager, commitManager, diffViewer));
        commands.put("checkout", new CheckoutCommand(repoManager, commitManager, versionRestore));
        commands.put("help", new HelpCommand(commands));
    }
    
    public void executeCommand(String commandName, String[] args) {
        Command command = commands.get(commandName.toLowerCase());
        
        if (command == null) {
            System.out.println("Unknown command: " + commandName);
            System.out.println("Type 'help' to see available commands");
            return;
        }
        
        try {
            command.execute(args);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void runInteractive() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║   SMART VERSION CONTROL SYSTEM (MINI GIT)    ║");
        System.out.println("║          Lightweight File Versioning          ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Type 'help' to see available commands");
        System.out.println("Type 'exit' to quit");
        System.out.println();
        
        while (true) {
            System.out.print("minigit> ");
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                continue;
            }
            
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                break;
            }
            
            String[] parts = parseInput(input);
            if (parts.length == 0) {
                continue;
            }
            
            String commandName = parts[0];
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);
            
            executeCommand(commandName, args);
            System.out.println();
        }
        
        scanner.close();
    }
    
    private String[] parseInput(String input) {
        // Simple parsing - split by spaces, preserving quoted strings
        java.util.List<String> tokens = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        
        for (char c : input.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (current.length() > 0) {
                    tokens.add(current.toString());
                    current = new StringBuilder();
                }
            } else {
                current.append(c);
            }
        }
        
        if (current.length() > 0) {
            tokens.add(current.toString());
        }
        
        return tokens.toArray(new String[0]);
    }
    
    public static void main(String[] args) {
        String workingDirectory;
        
        if (args.length > 0 && args[0].equals("-d") && args.length > 1) {
            workingDirectory = args[1];
        } else {
            workingDirectory = System.getProperty("user.dir");
        }
        
        MiniGit miniGit = new MiniGit(workingDirectory);
        
        if (args.length > 0 && !args[0].equals("-d")) {
            // Command-line mode
            String commandName = args[0];
            String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
            miniGit.executeCommand(commandName, commandArgs);
        } else {
            // Interactive mode
            miniGit.runInteractive();
        }
    }
}
