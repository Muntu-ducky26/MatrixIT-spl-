# TensorOp.java Documentation

- Source file: `src/tensor/TensorOp.java`
- Package: `tensor`
- Role: Tensor CLI command parser, dispatcher, and persistence adapter

## 1. Business Purpose
Enables users to create, persist, query, and transform named tensors from the command line.

## 2. Storage Model
- Persistence file: `<user-home>/variablest.txt`
- Record format: `<tensorName>=<serializedTensor>`
- Uses `Tensor.toString()` and `Tensor.fromString()` for serialization

## 3. Command Interface
### Create / Read
- `<name>=[tensor-data]` -> create and persist tensor
- `<name>` -> print tensor

### Tensor Operations
- `add <A> <B>`
- `sub <A> <B>`
- `mul <A> <B>`
- `div <A> <B>`
- `dot <A> <B>`

### Scalar Operations
- `sadd <A> <k>`
- `ssub <A> <k>`
- `smul <A> <k>`
- `sdiv <A> <k>`

### Structural / Metadata Operations
- `reshape <A> <d> <r> <c>`
- `trans <A>`
- `slice <A>` (interactive bounds)
- `dim <A>`
- `str <A>`

### Help
- `help`
- Topic-style suffix help (for example `help transpose`)

## 4. Runtime Responsibilities
- Parses command prefixes
- Loads named tensors from persistence
- Dispatches computation to `Tensor`
- Prints results and user guidance
- Provides `clearFile(path)` for module-level cleanup

## 5. Error Behavior
- Unknown command prints fallback message
- Missing tensor names print recognition error
- IO and computation exceptions are caught and printed with stack traces

## 6. Dependencies
- `tensor.Tensor`
- JDK IO classes and `java.util.Scanner`

## 7. Known Risks / Improvement Backlog
- Some substring parsing paths are brittle and sensitive to trailing characters
- No duplicate-name normalization strategy in storage file
- Command parsing and presentation logic are tightly coupled with domain operations
