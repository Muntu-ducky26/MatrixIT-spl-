# Main.java Documentation

- Source file: `src/main/Main.java`
- Package: `main`
- Role: Application entry point and top-level mode router

## 1. Business Purpose
`Main` is the CLI orchestrator for MatrixIT. It presents the product menu and directs users into one of three functional domains:
- Matrix computations
- Tensor computations
- Linear equation methods

## 2. Runtime Responsibilities
- Initializes shared console input (`Scanner`)
- Displays startup and guidance messages
- Accepts top-level mode selection (`m`, `t`, `l`, `exit`)
- Maintains command loops per mode (`mat>`, `ten>`, `lin>` prompts)
- Delegates command execution to domain handlers:
  - `matrix.MatrixOp`
  - `tensor.TensorOp`
  - `linear.LinearEq`
- Performs module-exit cleanup for persisted matrix/tensor variable files

## 3. Integration Contract
- Input: Interactive user commands from standard input
- Output: Colored console text for prompts, results, and errors
- Calls:
  - `MatrixOp.executeCommand(command, scanner)`
  - `TensorOp.executeCommand(command, scanner)`
  - `LinearEq.executeCommand(command, scanner)`
- Exit behavior:
  - Matrix mode exit clears `variablesm.txt`
  - Tensor mode exit clears `variablest.txt`
  - Linear mode exit returns to top-level menu without file cleanup

## 4. Error and Control Behavior
- Unrecognized top-level command prints retry message
- Delegated handlers own most command-level validation and exception printing
- Scanner is closed once application loop ends (`exit`)

## 5. Dependencies
- Internal modules: `matrix`, `tensor`, `linear`
- JDK: `java.util.Scanner`, `java.io.File`

## 6. Operational Notes
- ANSI escape constants are hardcoded in this class for colored output
- Module command loops are intentionally persistent until `exit`

## 7. Known Risks / Improvement Backlog
- Mixed `nextLine()` with downstream `nextInt()/nextDouble()` can cause newline consumption issues in some paths
- Cleanup strategy clears persisted matrix/tensor state on module exit, which may surprise users expecting persistence
- No centralized exception boundary for uniform error UX
