# MINI GIT DEMONSTRATION SCRIPT

This file shows a complete demonstration of Mini Git functionality.

## Demo Session 1: Basic Operations

```
Step 1: Start Mini Git
$ java -cp bin com.minigit.MiniGit

╔═══════════════════════════════════════════════╗
║   SMART VERSION CONTROL SYSTEM (MINI GIT)    ║
║          Lightweight File Versioning          ║
╚═══════════════════════════════════════════════╝

Type 'help' to see available commands
Type 'exit' to quit

Step 2: Initialize Repository
minigit> init
Initialized empty Mini Git repository in /home/user/project/.minigit

Step 3: Add Files
minigit> add sample1.txt
Added file to staging area: sample1.txt

minigit> add sample2.txt
Added file to staging area: sample2.txt

Step 4: Check Status
minigit> status
=== Mini Git Status ===
Current HEAD: No commits yet

Staged files:
  sample1.txt
  sample2.txt

Step 5: Create First Commit
minigit> commit Initial commit with sample files
Created commit: a3f2d1c8
Message: Initial commit with sample files

Step 6: View Commit History
minigit> log
=== Commit History ===

Commit: a3f2d1c8b4e9c7f2d5a8e3f1c6b9d2e5a7f4c8
Parent: null
Date: 2025-02-07 14:30:45
Message: Initial commit with sample files

Step 7: Check Status Again
minigit> status
=== Mini Git Status ===
Current HEAD: a3f2d1c8

No files staged
```

## Demo Session 2: Making Changes

```
Step 1: Edit sample1.txt (add a new line)
(Assume we added: "This is a new line added to the file.")

Step 2: View Changes
minigit> diff sample1.txt
=== Changes in sample1.txt ===
(Comparing working directory with HEAD commit)

  1: Hello World!
  2: This is the first version of sample file 1.
  3: It contains some initial content.
  4: 
  5: Mini Git is a version control system.
+ 6: This is a new line added to the file.

Step 3: Stage Modified File
minigit> add sample1.txt
Added file to staging area: sample1.txt

Step 4: Create Second Commit
minigit> commit Updated sample1 with additional content
Created commit: b5e9c2f1
Message: Updated sample1 with additional content

Step 5: View History (Now with 2 commits)
minigit> log
=== Commit History ===

Commit: b5e9c2f1a8d3e7f2c4b6a9d8e1f3c5b7a2d4e6f8
Parent: a3f2d1c8b4e9c7f2d5a8e3f1c6b9d2e5a7f4c8
Date: 2025-02-07 14:35:20
Message: Updated sample1 with additional content

Commit: a3f2d1c8b4e9c7f2d5a8e3f1c6b9d2e5a7f4c8
Parent: null
Date: 2025-02-07 14:30:45
Message: Initial commit with sample files
```

## Demo Session 3: Comparing Versions

```
Step 1: Compare File Between Two Commits
minigit> diff sample1.txt a3f2d1c8 b5e9c2f1
=== Diff for sample1.txt ===
Commit 1: a3f2d1c8 - Initial commit with sample files
Commit 2: b5e9c2f1 - Updated sample1 with additional content

  1: Hello World!
  2: This is the first version of sample file 1.
  3: It contains some initial content.
  4: 
  5: Mini Git is a version control system.
+ 6: This is a new line added to the file.

Legend:
  (space) = Line unchanged
- (minus) = Line deleted
+ (plus)  = Line added

Step 2: Make More Changes
(Edit sample2.txt - modify line 2)

Step 3: Compare Working File with HEAD
minigit> diff sample2.txt
=== Changes in sample2.txt ===
(Comparing working directory with HEAD commit)

  1: This is sample file 2.
- 2: It demonstrates version control.
+ 2: It demonstrates version control features.
  3: 
  4: Features:
  5: - File tracking
  6: - Commit history
  7: - Diff viewing
  8: - Version rollback
```

## Demo Session 4: Version Restoration

```
Step 1: View Available Commits
minigit> log
=== Commit History ===

Commit: b5e9c2f1a8d3e7f2c4b6a9d8e1f3c5b7a2d4e6f8
Parent: a3f2d1c8b4e9c7f2d5a8e3f1c6b9d2e5a7f4c8
Date: 2025-02-07 14:35:20
Message: Updated sample1 with additional content

Commit: a3f2d1c8b4e9c7f2d5a8e3f1c6b9d2e5a7f4c8
Parent: null
Date: 2025-02-07 14:30:45
Message: Initial commit with sample files

Step 2: Restore to First Commit
minigit> checkout a3f2d1c8
Checking out commit: a3f2d1c8
Message: Initial commit with sample files

Restored: sample1.txt
Restored: sample2.txt

Successfully checked out commit a3f2d1c8

Step 3: Verify Restoration
(sample1.txt now back to original version without the new line)

Step 4: Restore Just One File from a Specific Commit
minigit> checkout b5e9c2f1 sample1.txt
Restored 'sample1.txt' from commit b5e9c2f1

(Now sample1.txt has the new line again)
```

## Demo Session 5: Advanced Features

```
Step 1: Using Partial Hashes
minigit> checkout a3f2d1c8
(Works the same as using full hash)

Step 2: Multiple File Operations
minigit> add file1.txt file2.txt file3.txt
Added file to staging area: file1.txt
Added file to staging area: file2.txt
Added file to staging area: file3.txt

Step 3: Commit Messages with Spaces
minigit> commit This is a multi-word commit message
Created commit: c7d9e4f2
Message: This is a multi-word commit message

Step 4: Get Help
minigit> help
=== Mini Git - Available Commands ===

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
    Show differences in a file

checkout <commit-hash> [file]
    Restore working directory to a previous commit

help
    Show this help message

Step 5: Exit
minigit> exit
Goodbye!
```

## Expected Behavior Summary

### When Everything Works:
✓ Files are tracked and versioned
✓ Changes are detected and displayed
✓ Commits create snapshots
✓ History shows all commits in order
✓ Checkout restores previous versions
✓ Data integrity maintained via hashing

### Error Handling Examples:

```
minigit> init
(in already initialized repo)
Error: Repository already initialized

minigit> add nonexistent.txt
Error: File not found: nonexistent.txt

minigit> commit
Error: No files staged for commit. Use 'add' to stage files.

minigit> checkout invalidhash
Error: No commit found matching: invalidhash

minigit> diff file.txt
(when file not in current commit)
File 'file.txt' is not tracked in current commit
```

## Performance Notes

- **Fast Operations**: init, add, status
- **Medium Operations**: commit (depends on file size)
- **Slower Operations**: checkout (copies files), diff (reads files)

## Storage Efficiency

Example with duplicate content:
```
File1.txt (100 KB) - Hash: abc123...
File2.txt (100 KB) - Same content - Hash: abc123...
Total Storage: 100 KB (not 200 KB) ✓ Deduplication working!
```

## Demonstration Complete

This demonstration shows:
✓ All core features working
✓ Error handling functional
✓ User interface clear and intuitive
✓ Version control concepts properly implemented

Try it yourself with the provided sample files!
