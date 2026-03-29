# LinearEq.java Documentation

- Source file: `src/linear/LinearEq.java`
- Package: `linear`
- Role: CLI command gateway for numerical methods in `Linear`

## 1. Business Purpose
Provides a compact command language for executing root-finding and linear-system solving methods.

## 2. Command Contract
- `bis` -> Bisection
- `fp` -> False Position
- `scnt` -> Secant
- `NR` -> Newton-Raphson
- `gjor` -> Gauss-Jordan
- `gsei` -> Gauss-Seidel
- `help` -> prints command references

## 3. Runtime Responsibilities
- Matches user command prefix
- Prompts user for method-specific parameters
- Parses numeric values via shared `Scanner`
- Dispatches to `Linear` static methods

## 4. Input Collection by Method
- Bisection/False Position: interval bounds + cubic coefficients
- Newton-Raphson: initial guess + function and derivative coefficients
- Secant: two initial guesses + cubic coefficients
- Gauss-Jordan/Gauss-Seidel: number of unknowns followed by matrix/system data

## 5. Error and Validation Behavior
- Unknown command prints fallback message
- Numeric parsing errors are not centrally handled here and will surface from scanner usage
- Method-level validation is delegated to `Linear`

## 6. Dependencies
- `linear.Linear`
- `java.util.Scanner`

## 7. Known Risks / Improvement Backlog
- Prefix-only matching may allow ambiguous input acceptance
- Command help is static and not synchronized automatically with implementation changes
- No return status contract for upstream orchestration or automated testing
