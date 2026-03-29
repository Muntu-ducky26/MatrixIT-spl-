# MatrixOp.java Documentation

- Source file: `src/matrix/MatrixOp.java`
- Package: `matrix`
- Role: Matrix command parser, dispatcher, and persistence adapter

## 1. Business Purpose
Translates user CLI input into executable matrix operations and manages named matrix storage across commands.

## 2. Storage Model
- Persistence file: `<user-home>/variablesm.txt`
- Record format: `<matrixName>=<serializedMatrix>`
- Serialization contract delegated to `Matrix.toString()` / `Matrix.fromString()`

## 3. Command Interface
### Create / Read
- `<name>=[matrix-data]` -> create and persist matrix
- `<name>` -> print matrix by name

### Algebra and Analysis
- `sqr <A>` -> square matrix
- `mul <A> <B>` -> matrix multiplication
- `add <A> <B>` -> element-wise addition
- `sub <A> <B>` -> element-wise subtraction
- `det <A>` -> determinant
- `inv <A>` -> inverse
- `trans <A>` -> transpose
- `rank <A>` -> rank
- `RE <A>` -> row echelon form
- `eig <A>` -> dominant eigenvalue (power iteration)
- `calsprs <A>` -> sparsity percentage
- `extsub <A>` -> interactive submatrix extraction

### Help
- `help`
- `help <topic>` behavior via suffix matching (for example, `help add`)

## 4. Control Flow and Responsibilities
- Validates command shape and matrix-name availability
- Calls `Matrix` static operations
- Prints operation results and intermediate educational traces
- Provides `clearFile(path)` helper used by `Main`

## 5. Error Behavior
- Invalid command format prints guidance message
- Missing matrices print recognition error
- IO exceptions are caught and printed with stack traces
- Computation errors from `Matrix` propagate to catch blocks and are printed

## 6. Dependencies
- `matrix.Matrix`
- JDK IO (`BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter`, `FileOutputStream`)
- `java.util.Scanner`

## 7. Operational Notes
- `readMatrixFromFile()` batch replays stored commands by feeding lines into `executeCommand(...)`
- File writes append records; duplicate matrix names can coexist and first match logic affects retrieval behavior

## 8. Known Risks / Improvement Backlog
- Duplicate key handling is not normalized (no explicit overwrite semantics)
- Scanner-driven integer reads can interfere with line-based command loops if newline handling is inconsistent
- Error handling is console-focused; no machine-readable status return
