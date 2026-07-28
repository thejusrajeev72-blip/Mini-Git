# MINI GIT SYSTEM ARCHITECTURE

## 🏗️ System Architecture Diagram

```
┌────────────────────────────────────────────────────────────────┐
│                         USER INTERFACE                          │
│                                                                 │
│  ╔═══════════════════════════════════════════════════════╗    │
│  ║              Interactive Mode (CLI)                    ║    │
│  ║                  MiniGit.java                          ║    │
│  ║  • Reads user input                                    ║    │
│  ║  • Parses commands                                     ║    │
│  ║  • Displays output                                     ║    │
│  ╚═══════════════════════════════════════════════════════╝    │
└───────────────────────────┬────────────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────────────┐
│                      COMMAND LAYER                              │
│                   (Command Pattern)                             │
│  ┌──────────┬──────────┬──────────┬──────────┬──────────┐     │
│  │  Init    │   Add    │  Commit  │  Status  │   Log    │     │
│  │ Command  │ Command  │ Command  │ Command  │ Command  │     │
│  └──────────┴──────────┴──────────┴──────────┴──────────┘     │
│  ┌──────────┬──────────┬──────────┐                            │
│  │   Diff   │ Checkout │   Help   │                            │
│  │ Command  │ Command  │ Command  │                            │
│  └──────────┴──────────┴──────────┘                            │
└───────────────────────────┬────────────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────────────┐
│                       CORE LAYER                                │
│                  (Business Logic)                               │
│  ┌───────────────────────────────────────────────────────┐     │
│  │           RepositoryManager                            │     │
│  │  • Initialize repository                               │     │
│  │  • Load/save repository state                          │     │
│  │  • Add files to staging                                │     │
│  │  • Create commits                                      │     │
│  │  • Show status                                         │     │
│  └───────────────────────────────────────────────────────┘     │
│  ┌───────────────────────────────────────────────────────┐     │
│  │           CommitManager                                │     │
│  │  • Show commit history                                 │     │
│  │  • Load commit objects                                 │     │
│  │  • Find commits by hash                                │     │
│  │  • Show commit details                                 │     │
│  └───────────────────────────────────────────────────────┘     │
│  ┌───────────────────────────────────────────────────────┐     │
│  │           DiffViewer                                   │     │
│  │  • Compare file versions                               │     │
│  │  • Show line-by-line differences                       │     │
│  │  • Display changes (add/delete/modify)                 │     │
│  └───────────────────────────────────────────────────────┘     │
│  ┌───────────────────────────────────────────────────────┐     │
│  │           VersionRestore                               │     │
│  │  • Checkout commits                                    │     │
│  │  • Restore files                                       │     │
│  │  • Create backups                                      │     │
│  └───────────────────────────────────────────────────────┘     │
└───────────────────────────┬────────────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────────────┐
│                     UTILITY LAYER                               │
│                  (Helper Functions)                             │
│  ┌──────────────┬──────────────┬──────────────────────┐        │
│  │  HashUtil    │  FileUtil    │  SerializationUtil   │        │
│  │              │              │                      │        │
│  │ • SHA-256    │ • Copy files │ • Save objects       │        │
│  │   hashing    │ • Read files │ • Load objects       │        │
│  │ • File hash  │ • Write files│                      │        │
│  │ • Verify     │ • List files │                      │        │
│  └──────────────┴──────────────┴──────────────────────┘        │
└───────────────────────────┬────────────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────────────┐
│                      DATA LAYER                                 │
│                   (Data Models)                                 │
│  ┌───────────────────────────────────────────────────────┐     │
│  │           Repository Model                             │     │
│  │  • HEAD pointer (current commit)                       │     │
│  │  • List of all commits                                 │     │
│  │  • Staged files map                                    │     │
│  └───────────────────────────────────────────────────────┘     │
│  ┌───────────────────────────────────────────────────────┐     │
│  │           Commit Model                                 │     │
│  │  • Commit hash (unique ID)                             │     │
│  │  • Parent commit hash                                  │     │
│  │  • Commit message                                      │     │
│  │  • Timestamp                                           │     │
│  │  • File hashes map                                     │     │
│  └───────────────────────────────────────────────────────┘     │
└───────────────────────────┬────────────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────────────┐
│                   FILE SYSTEM STORAGE                           │
│                                                                 │
│  ┌────────────────────────────────────────────────────┐        │
│  │  .minigit/                                          │        │
│  │  ├── objects/          ← File contents (hash-named)│        │
│  │  ├── commits/          ← Commit objects            │        │
│  │  └── repository.dat    ← Repository state          │        │
│  └────────────────────────────────────────────────────┘        │
└────────────────────────────────────────────────────────────────┘
```

## 📊 Data Flow Diagrams

### 1. INIT Command Flow
```
User: "init"
    │
    ▼
MiniGit.java ──► InitCommand.execute()
    │
    ▼
RepositoryManager.initRepository()
    │
    ├─► Create .minigit/
    ├─► Create objects/
    ├─► Create commits/
    ├─► Create Repository object
    └─► Save repository.dat
    │
    ▼
Display: "Initialized empty Mini Git repository"
```

### 2. ADD Command Flow
```
User: "add file.txt"
    │
    ▼
MiniGit.java ──► AddCommand.execute("file.txt")
    │
    ▼
RepositoryManager.loadRepository()
    │
    ▼
RepositoryManager.addFile("file.txt")
    │
    ├─► Check file exists
    ├─► Add to staging map
    └─► Save repository state
    │
    ▼
Display: "Added file to staging area: file.txt"
```

### 3. COMMIT Command Flow
```
User: "commit Initial version"
    │
    ▼
MiniGit.java ──► CommitCommand.execute("Initial version")
    │
    ▼
RepositoryManager.loadRepository()
    │
    ▼
RepositoryManager.commit("Initial version")
    │
    ├─► For each staged file:
    │   ├─► Calculate file hash (SHA-256)
    │   ├─► Copy file to objects/ with hash name
    │   └─► Store filename → hash mapping
    │
    ├─► Create Commit object
    │   ├─► Generate commit hash
    │   ├─► Set parent hash (previous HEAD)
    │   ├─► Set timestamp
    │   └─► Set message
    │
    ├─► Save commit to commits/
    ├─► Update HEAD to new commit
    ├─► Clear staging area
    └─► Save repository state
    │
    ▼
Display: "Created commit: a3f2d1c8"
```

### 4. LOG Command Flow
```
User: "log"
    │
    ▼
MiniGit.java ──► LogCommand.execute()
    │
    ▼
RepositoryManager.loadRepository()
    │
    ▼
CommitManager.showLog()
    │
    ├─► Get list of commits from repository
    │
    ├─► For each commit (newest to oldest):
    │   ├─► Load commit object from commits/
    │   └─► Display commit info
    │
    ▼
Display: Complete commit history
```

### 5. DIFF Command Flow
```
User: "diff file.txt"
    │
    ▼
MiniGit.java ──► DiffCommand.execute("file.txt")
    │
    ▼
RepositoryManager.loadRepository()
    │
    ▼
DiffViewer.diffWorkingFile("file.txt")
    │
    ├─► Get current commit (HEAD)
    ├─► Get file hash from commit
    ├─► Load committed file from objects/
    ├─► Read working directory file
    │
    ├─► Compare line by line:
    │   ├─► Lines in both: unchanged (  )
    │   ├─► Only in committed: deleted (-)
    │   └─► Only in working: added (+)
    │
    ▼
Display: Line-by-line differences
```

### 6. CHECKOUT Command Flow
```
User: "checkout a3f2d1c8"
    │
    ▼
MiniGit.java ──► CheckoutCommand.execute("a3f2d1c8")
    │
    ▼
RepositoryManager.loadRepository()
    │
    ▼
VersionRestore.checkout("a3f2d1c8")
    │
    ├─► Find full commit hash (if partial)
    ├─► Load commit object
    │
    ├─► For each file in commit:
    │   ├─► Get file hash
    │   ├─► Load file from objects/
    │   └─► Copy to working directory
    │
    ├─► Update HEAD to this commit
    └─► Save repository state
    │
    ▼
Display: "Successfully checked out commit a3f2d1c8"
```

## 🔄 State Transitions

```
┌──────────────┐
│ Uninitialized│
│  Repository  │
└──────┬───────┘
       │ init
       ▼
┌──────────────┐
│ Empty Repo   │  ◄───┐
│ (No commits) │      │
└──────┬───────┘      │
       │ add + commit │
       ▼              │
┌──────────────┐      │
│  Has Commits │      │
│  (HEAD set)  │ ─────┘
└──────┬───────┘   checkout
       │
       ├─► add (files staged)
       ├─► commit (new snapshot)
       ├─► log (view history)
       ├─► diff (compare)
       └─► checkout (restore)
```

## 📦 Storage Structure

```
Working Directory/
├── file1.txt                 ← User's working files
├── file2.txt
│
└── .minigit/                 ← Hidden repository folder
    │
    ├── objects/              ← Content storage (by hash)
    │   ├── a3f2d1c8...       ← File content (hash-named)
    │   ├── b5e9c2f1...
    │   └── c7d9e4f2...
    │
    ├── commits/              ← Commit objects
    │   ├── a3f2d1c8...       ← Commit metadata
    │   ├── b5e9c2f1...
    │   └── c7d9e4f2...
    │
    └── repository.dat        ← Repository state
                                • HEAD pointer
                                • Commit list
                                • Staged files
```

## 🔐 Hashing Mechanism

```
File Content ──► SHA-256 ──► Hash (64 hex chars)
                              │
                              ▼
                    Used for:
                    • Unique file identification
                    • Content-based storage
                    • Data integrity verification
                    • Deduplication

Example:
"Hello World" ──► SHA-256 ──► "a591a6d40bf420404a..."
                              (first 8 chars: a591a6d4)
```

## 🔗 Commit Graph Example

```
Time ──────────────────────────────────────────►

null ◄── Commit A ◄── Commit B ◄── Commit C
         │            │            │
         │            │            └─ HEAD (current)
         │            │
         │            └─ message: "Updated files"
         │               date: 2025-02-07 14:35:20
         │               files: {file1.txt, file2.txt}
         │
         └─ message: "Initial commit"
            date: 2025-02-07 14:30:45
            files: {file1.txt, file2.txt}

When you checkout Commit A:
- Working directory restored to Commit A state
- HEAD moves to Commit A
- Files match Commit A content
```

## 🎯 Key Design Decisions

### 1. Content-Based Storage
**Why:** Saves space, ensures data integrity
**How:** Files stored by SHA-256 hash

### 2. Immutable Commits
**Why:** Maintains history integrity
**How:** Once created, commits never change

### 3. Staging Area
**Why:** Allows selective commits
**How:** Temporary area before committing

### 4. Command Pattern
**Why:** Extensible, maintainable
**How:** Each command is a separate class

### 5. SHA-256 Hashing
**Why:** Strong security, collision-resistant
**How:** Java MessageDigest library

## 📈 Scalability Considerations

**Current Design:**
- ✓ Works great for small to medium projects
- ✓ Efficient storage with deduplication
- ✓ Fast operations on typical file sizes

**Future Enhancements:**
- Compression for large files
- Delta storage for incremental changes
- Index caching for faster operations
- Pack files for efficient storage

## 🎓 Learning Outcomes

By studying this architecture, you understand:
- ✓ Layered architecture design
- ✓ Command pattern implementation
- ✓ Data persistence strategies
- ✓ Hashing and cryptography
- ✓ File system operations
- ✓ State management
- ✓ Graph data structures
- ✓ Version control concepts

---

**This architecture provides:**
- Clear separation of concerns
- Easy to understand and maintain
- Extensible for new features
- Robust error handling
- Efficient data storage
