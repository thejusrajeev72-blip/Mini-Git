package com.minigit.commands;

import java.util.Map;

/**
 * Command to display help information
 */
public class HelpCommand implements Command {
    private Map<String, Command> commands;
    
    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }
    
    @Override
    public void execute(String[] args) throws Exception {
        System.out.println("=== Mini Git - Available Commands ===\n");
        
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            Command cmd = entry.getValue();
            System.out.println(cmd.getUsage());
            System.out.println("    " + cmd.getDescription());
            System.out.println();
        }
    }
    
    @Override
    public String getUsage() {
        return "help";
    }
    
    @Override
    public String getDescription() {
        return "Show this help message";
    }
}
