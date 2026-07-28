# QUICK START GUIDE

## Get Started in 5 Minutes!

### Step 1: Compile the Project

**On Windows:**
```batch
build.bat
```

**On Linux/Mac:**
```bash
chmod +x build.sh
./build.sh
```

### Step 2: Run Mini Git

```bash
java -jar MiniGit.jar
```

Or:

```bash
java -cp bin com.minigit.MiniGit
```

### Step 3: Try it Out!

```
minigit> init
minigit> add sample1.txt
minigit> add sample2.txt
minigit> commit Initial commit with sample files
minigit> status
minigit> log
minigit> help
```

### Step 4: Experiment

1. Edit sample1.txt (change some text)
2. Run: `diff sample1.txt` to see changes
3. Run: `add sample1.txt` to stage
4. Run: `commit Updated sample1`
5. Run: `log` to see both commits
6. Run: `checkout <first-commit-hash>` to go back

That's it! You're now using Mini Git!

## Command Cheat Sheet

```
init                    - Start a new repository
add <file>              - Stage a file
commit <message>        - Save a snapshot
status                  - Check current state
log                     - View history
diff <file>             - See changes
checkout <hash>         - Restore version
help                    - Show all commands
exit                    - Quit
```

## Example Session

```
minigit> init
Initialized empty Mini Git repository

minigit> add README.md
Added file to staging area: README.md

minigit> commit Added README file
Created commit: a3f2d1c8
Message: Added README file

minigit> log
=== Commit History ===

Commit: a3f2d1c8...
Parent: null
Date: 2025-02-07 14:30:45
Message: Added README file

minigit> exit
Goodbye!
```

## Next Steps

- Read the full USER_GUIDE.md for detailed documentation
- Check README.md for project overview and architecture
- Experiment with different commands
- Try creating multiple commits and using diff/checkout

Enjoy using Mini Git! 🚀
