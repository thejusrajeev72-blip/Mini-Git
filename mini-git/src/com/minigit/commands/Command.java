package com.minigit.commands;

/**
 * Command interface for implementing command pattern
 */
public interface Command {
    void execute(String[] args) throws Exception;
    String getUsage();
    String getDescription();
}
