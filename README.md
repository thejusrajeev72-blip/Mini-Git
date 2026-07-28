# SMART VERSION CONTROL SYSTEM (MINI GIT)

A lightweight, offline version control system developed in Java, inspired by Git. This system enables users to track file changes, maintain modification history, and restore previous versions.

## Project Overview

The Smart Version Control System (Mini Git) is designed to help students and small-scale developers manage different versions of their files and projects efficiently. It operates entirely offline through a command-line interface.

## Features

### Core Modules

1. **Repository Management Module**
   - Initialize project folders as version-controlled repositories
   - Manage repository configuration and active version (HEAD)

2. **File Tracking Module**
   - Add files to staging area
   - Monitor file status (modified, new, unchanged)

3. **Commit Management Module**
   - Create snapshots of project state with messages and timestamps
   - View complete commit history
   - Maintain parent-child commit relationships

4. **Version Restore Module**
   - Rollback to any previous version
   - Safe data recovery and restoration

5. **Diff Viewer Module**
   - Compare two versions of a file
   - Display added, deleted, and modified lines

6. **Hashing & Storage Module**
   - Generate unique hash values using SHA-256
   - Content-based storage to prevent duplication
   - Data integrity verification

## System Requirements

### Hardware Requirements
- **CPU**: Dual Core Processor
- **RAM**: 4 GB
- **Hard Disk**: 120 GB
- **Clock Speed**: 2.6 GHz

### Software Requirements
- **Java**: JDK 8 or higher
- **Operating System**: Windows 7+ / Linux / macOS
- **Development Environment**: IntelliJ IDEA / Eclipse / NetBeans (optional)

##  Installation

1. **Clone or download the project**
   ```bash
   cd mini-git
   ```

2. **Compile the project**
   ```bash
   javac -d bin src/com/minigit/*.java src/com/minigit/*/*.java
   ```

3. **Run the application**
   ```bash
   # Interactive mode
   java -cp bin com.minigit.MiniGit
   
   # Command-line mode
   java -cp bin com.minigit.MiniGit <command> [args]
   
   # Specify working directory
   java -cp bin com.minigit.MiniGit -d /path/to/project
   ```

##  Usage

### Available Commands

```
init
    Initialize a new Mini Git repository

add <file1> [file2] [file3] ...
    Add files to the staging area

commit <message>
    Create a commit with staged files

status
    Show the status of the working directory

log
    Show commit history

diff <file> [commit1] [commit2]
    Show differences in a file (between commits or with working directory)

checkout <commit-hash> [file]
    Restore working directory to a previous commit or restore a single file

help
    Show help message

exit/quit
    Exit the program (in interactive mode)
```

### Example Workflow

```bash
# Start Mini Git
minigit> init
Initialized empty Mini Git repository in .minigit

# Create and add files
minigit> add file1.txt
Added file to staging area: file1.txt

minigit> add file2.txt
Added file to staging area: file2.txt

# Check status
minigit> status
=== Mini Git Status ===
Current HEAD: No commits yet

Staged files:
  file1.txt
  file2.txt

# Create a commit
minigit> commit Initial commit with two files
Created commit: a3f2d1c8
Message: Initial commit with two files

# View commit history
minigit> log
=== Commit History ===

Commit: a3f2d1c8b4e9...
Parent: null
Date: 2025-02-07 14:30:45
Message: Initial commit with two files

# Make changes and add again
minigit> add file1.txt
minigit> commit Updated file1

# Compare versions
minigit> diff file1.txt
=== Changes in file1.txt ===
(Comparing working directory with HEAD commit)

# View differences between commits
minigit> diff file1.txt a3f2d1c8 b5e9c2f1

# Restore previous version
minigit> checkout a3f2d1c8
Checking out commit: a3f2d1c8
Message: Initial commit with two files

Restored: file1.txt
Restored: file2.txt

Successfully checked out commit a3f2d1c8
```

## Project Structure

```
mini-git/
├── src/
│   └── com/
│       └── minigit/
│           ├── MiniGit.java           # Main application class
│           ├── models/
│           │   ├── Commit.java         # Commit data model
│           │   └── Repository.java     # Repository state model
│           ├── core/
│           │   ├── RepositoryManager.java  # Repository operations
│           │   ├── CommitManager.java      # Commit operations
│           │   ├── DiffViewer.java         # File comparison
│           │   └── VersionRestore.java     # Version restoration
│           ├── utils/
│           │   ├── HashUtil.java           # Hashing utilities
│           │   ├── FileUtil.java           # File operations
│           │   └── SerializationUtil.java  # Object serialization
│           └── commands/
│               ├── Command.java            # Command interface
│               ├── InitCommand.java
│               ├── AddCommand.java
│               ├── CommitCommand.java
│               ├── StatusCommand.java
│               ├── LogCommand.java
│               ├── DiffCommand.java
│               ├── CheckoutCommand.java
│               └── HelpCommand.java
└── README.md
```

## Security Features

- **SHA-256 Hashing**: All files are stored using SHA-256 hash values
- **Data Integrity**: Hash verification ensures files haven't been tampered with
- **Content-Based Storage**: Identical files are stored only once (deduplication)

## Key Concepts

### Repository Structure
When you initialize a repository, Mini Git creates a `.minigit` directory containing:
- `objects/` - Stores file content using hash-based naming
- `commits/` - Stores commit metadata
- `repository.dat` - Stores repository state

### Commits
Each commit contains:
- Unique commit hash (SHA-256)
- Parent commit hash (for version history)
- Commit message
- Timestamp
- Snapshot of all tracked files

### Staging Area
Files must be added to the staging area before committing. This allows you to:
- Select which changes to include in a commit
- Review changes before committing
- Create logical, atomic commits

##  Educational Value

This project demonstrates:
- **File I/O Operations**: Reading, writing, and managing files
- **Data Structures**: Hash maps, lists for managing commits and files
- **Hashing Algorithms**: SHA-256 for data integrity
- **Object Serialization**: Persisting Java objects
- **Design Patterns**: Command pattern for CLI operations
- **Version Control Concepts**: Commits, staging, diff, checkout

## Troubleshooting

**Repository not initialized**
```
Error: Not a Mini Git repository. Use 'init' to initialize.
Solution: Run 'init' command first
```

**File not found**
```
Error: File not found: filename.txt
Solution: Ensure the file exists in the working directory
```

**No files staged**
```
Error: No files staged for commit. Use 'add' to stage files.
Solution: Add files using 'add' command before committing
```

## Future Enhancements

- Branch support for parallel development
- Merge functionality for combining branches
- Remote repository support
- Graphical user interface (GUI)
- File ignore patterns (.minigitignore)
- Binary file support optimization
- Compression for storage efficiency

##Development

**Compilation with specific Java version:**
```bash
javac -source 8 -target 8 -d bin src/com/minigit/*.java src/com/minigit/*/*.java
```

**Creating JAR file:**
```bash
jar cvfe MiniGit.jar com.minigit.MiniGit -C bin .
java -jar MiniGit.jar
```

## License

This project is developed for educational purposes.

## Acknowledgments

Inspired by Git - the distributed version control system created by Linus Torvalds.

---

**Smart Version Control System (Mini Git)** - Efficient, Reliable, Educational
