# MINI GIT USER GUIDE

## Introduction

Welcome to the Smart Version Control System (Mini Git)! This guide will help you understand and use all features of the system.

## Getting Started

### 1. First Time Setup

Before you can start tracking files, you need to initialize a repository:

```
minigit> init
```

This creates a `.minigit` folder in your current directory that stores all version control data.

### 2. Understanding the Workflow

Mini Git follows this basic workflow:

1. **Modify** files in your working directory
2. **Add** files to the staging area
3. **Commit** staged files to create a snapshot
4. **View** history and compare versions
5. **Restore** previous versions when needed

## Command Reference

### init - Initialize Repository

**Usage:** `init`

**Description:** Creates a new Mini Git repository in the current directory.

**Example:**
```
minigit> init
Initialized empty Mini Git repository in /home/user/myproject/.minigit
```

**Note:** You can only initialize once per directory. Running init again will show an error.

---

### add - Stage Files

**Usage:** `add <file1> [file2] [file3] ...`

**Description:** Adds files to the staging area, preparing them for the next commit.

**Examples:**
```
# Add a single file
minigit> add README.md
Added file to staging area: README.md

# Add multiple files
minigit> add file1.txt file2.txt file3.txt
Added file to staging area: file1.txt
Added file to staging area: file2.txt
Added file to staging area: file3.txt
```

**Tips:**
- Only staged files will be included in the next commit
- You can add the same file multiple times if you make changes
- Files must exist in the working directory

---

### commit - Create Snapshot

**Usage:** `commit <message>`

**Description:** Creates a snapshot of all staged files with a descriptive message.

**Examples:**
```
minigit> commit Initial version of the project
Created commit: a3f2d1c8
Message: Initial version of the project

minigit> commit Fixed bug in login function
Created commit: b5e9c2f1
Message: Fixed bug in login function
```

**Best Practices:**
- Write clear, descriptive messages
- Commit logical units of work
- Commit frequently to maintain good history

---

### status - Check Repository Status

**Usage:** `status`

**Description:** Shows the current state of your repository including HEAD commit and staged files.

**Example:**
```
minigit> status
=== Mini Git Status ===
Current HEAD: a3f2d1c8

Staged files:
  newfile.txt
  updated.txt
```

**What it tells you:**
- Current active version (HEAD)
- Files waiting to be committed

---

### log - View Commit History

**Usage:** `log`

**Description:** Displays the complete history of all commits in reverse chronological order.

**Example:**
```
minigit> log
=== Commit History ===

Commit: b5e9c2f1a8d3e7f2c4b6a9d8e1f3c5b7a2d4e6f8
Parent: a3f2d1c8b4e9c7f2d5a8e3f1c6b9d2e5a7f4c8
Date: 2025-02-07 14:45:30
Message: Added new features

Commit: a3f2d1c8b4e9c7f2d5a8e3f1c6b9d2e5a7f4c8
Parent: null
Date: 2025-02-07 14:30:15
Message: Initial commit
```

**Understanding the output:**
- Commit hash (unique identifier)
- Parent hash (previous version)
- Date and time of commit
- Commit message

---

### diff - Compare File Versions

**Usage:** `diff <file> [commit1] [commit2]`

**Description:** Shows differences between file versions.

**Examples:**

1. **Compare working file with HEAD:**
```
minigit> diff myfile.txt
=== Changes in myfile.txt ===
(Comparing working directory with HEAD commit)

  1: This line is unchanged
  2: This line is unchanged
- 3: This line was deleted
+ 3: This line was added
```

2. **Compare file between two commits:**
```
minigit> diff myfile.txt a3f2d1c8 b5e9c2f1
=== Diff for myfile.txt ===
Commit 1: a3f2d1c8 - Initial commit
Commit 2: b5e9c2f1 - Updated file

  1: Same line
- 2: Old content
+ 2: New content
```

**Legend:**
- `  ` (space) - Line unchanged
- `- ` (minus) - Line deleted/old version
- `+ ` (plus) - Line added/new version

**Tips:**
- Use short commit hashes (first 8 characters) for convenience
- Compare working files before committing to review changes

---

### checkout - Restore Versions

**Usage:** `checkout <commit-hash> [file]`

**Description:** Restores your working directory to a previous version.

**Examples:**

1. **Restore entire project to a commit:**
```
minigit> checkout a3f2d1c8
Checking out commit: a3f2d1c8
Message: Initial commit

Restored: file1.txt
Restored: file2.txt
Restored: file3.txt

Successfully checked out commit a3f2d1c8
```

2. **Restore single file from a commit:**
```
minigit> checkout a3f2d1c8 myfile.txt
Restored 'myfile.txt' from commit a3f2d1c8
```

**Warning:** 
- Checking out a commit will overwrite your current working files
- Make sure to commit any important changes before checking out
- Uncommitted changes will be lost

---

### help - Get Help

**Usage:** `help`

**Description:** Displays all available commands with brief descriptions.

---

## Common Workflows

### Creating Your First Commit

```
1. Initialize repository
   minigit> init

2. Create some files in your directory
   (create file1.txt, file2.txt)

3. Add files to staging
   minigit> add file1.txt file2.txt

4. Create commit
   minigit> commit My first commit

5. Verify with status and log
   minigit> status
   minigit> log
```

### Making Changes and Committing

```
1. Edit your files
   (modify file1.txt)

2. Review changes
   minigit> diff file1.txt

3. Stage the modified file
   minigit> add file1.txt

4. Commit changes
   minigit> commit Updated file1 with new content

5. View history
   minigit> log
```

### Comparing Versions

```
1. Get commit hashes
   minigit> log

2. Compare file between two commits
   minigit> diff myfile.txt a3f2d1c8 b5e9c2f1

3. Or compare with current working version
   minigit> diff myfile.txt
```

### Restoring Previous Version

```
1. View commit history
   minigit> log

2. Find the commit you want to restore
   (note the commit hash)

3. Restore entire project
   minigit> checkout a3f2d1c8

4. Or restore just one file
   minigit> checkout a3f2d1c8 myfile.txt
```

## Advanced Tips

### Using Short Hashes

You don't need to type the entire commit hash. The first 8 characters are usually enough:

```
Full hash:  a3f2d1c8b4e9c7f2d5a8e3f1c6b9d2e5a7f4c8
Short hash: a3f2d1c8

minigit> checkout a3f2d1c8
```

### Commit Message Best Practices

Good commit messages:
- ✓ "Added user authentication module"
- ✓ "Fixed null pointer exception in login"
- ✓ "Updated README with installation instructions"

Poor commit messages:
- ✗ "changes"
- ✗ "update"
- ✗ "stuff"

### Organizing Your Commits

- Commit related changes together
- Don't commit half-finished work
- Test before committing
- Commit frequently (but logically)

### Understanding Parent-Child Relationships

Each commit (except the first) has a parent:

```
Commit C (newest) → Parent: Commit B
Commit B → Parent: Commit A
Commit A (oldest) → Parent: null
```

This creates a chain of versions, allowing you to trace the complete history.

## Troubleshooting

### "Repository already initialized"
**Problem:** Trying to run `init` in an already initialized repository.
**Solution:** You don't need to initialize again. The repository is ready to use.

### "Not a Mini Git repository"
**Problem:** Running commands without initializing first.
**Solution:** Run `init` command first to create a repository.

### "No files staged for commit"
**Problem:** Trying to commit without adding files to staging.
**Solution:** Use `add` command to stage files before committing.

### "File not found"
**Problem:** Trying to add a file that doesn't exist.
**Solution:** Check the filename and ensure it exists in the working directory.

### "Commit not found"
**Problem:** Using an invalid commit hash.
**Solution:** Use `log` command to see valid commit hashes.

## How Mini Git Works Internally

### Storage Structure

```
your-project/
├── .minigit/                 # Hidden repository folder
│   ├── objects/              # File contents (hash-based)
│   ├── commits/              # Commit metadata
│   └── repository.dat        # Repository state
├── file1.txt                 # Your working files
└── file2.txt
```

### Hashing

Mini Git uses SHA-256 hashing to:
- Create unique identifiers for commits
- Store files efficiently (same content = same hash)
- Verify data integrity

### Content-Based Storage

If you commit the same file content multiple times, Mini Git only stores it once:

```
Commit 1: file.txt → hash: abc123...
Commit 2: file.txt → hash: abc123... (reuses same storage)
Commit 3: file.txt → hash: def456... (new content, new hash)
```

This saves disk space and improves efficiency.

## Summary

Mini Git provides a simple yet powerful way to:
- Track changes in your files
- Maintain complete project history
- Compare different versions
- Restore previous states
- Ensure data integrity

Happy versioning!
