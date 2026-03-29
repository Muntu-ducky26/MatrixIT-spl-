# Linear.java Documentation

- Source file: `src/linear/Linear.java`
- Package: `linear`
- Role: Numerical method engine for nonlinear roots and linear systems

## 1. Business Purpose
Implements educational and practical numerical methods used by linear-equation CLI commands.

## 2. Functional Scope
### Polynomial Evaluation
- `f(x, p, q, r, s)` for cubic polynomial
- `fbar(x, p1, q1, r1)` for derivative polynomial

### Root-Finding Methods
- `findBisection(...)`
- `findFalsePosition(...)`
- `Secant(...)`
- `NewtonRaphson(...)`

### Linear-System Solvers
- `GaussJordan(int n)`
- `GaussSeidel(int n)`
- `checkGaussseidel(double[][] matrix, int n)`

### Utility
- `printMatrix(double[][] matrix, int n)`

## 3. Method Behavior Standards
- Most iterative methods stop when residual magnitude is below `0.01`
- Gauss-Seidel uses convergence threshold `epsilon = 0.0001`
- Methods print iteration-by-iteration diagnostics for transparency

## 4. Input/Output Contract
- Inputs are provided interactively through `Scanner` in solver methods
- Outputs are printed to stdout with step traces and final approximations
- No structured return objects for iteration logs or convergence metadata

## 5. Validation and Error Behavior
- False Position checks initial sign-change condition
- Gauss-Seidel checks diagonal dominance via `checkGaussseidel`
- Runtime behavior assumes valid numeric input from console

## 6. Dependencies
- JDK: `java.util.Scanner`, `java.lang.Math`

## 7. Known Risks / Improvement Backlog
- Hardcoded tolerances are not configurable via command parameters
- Methods mix algorithm logic and console IO, reducing testability
- Fixed-size arrays (`50`) limit scalability and introduce silent constraints
- Potential resource-leak risk from scanners instantiated inside methods
