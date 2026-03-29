# MatrixIT Project Context

## 1. Product Overview
MatrixIT is a console-based Java application for matrix operations, tensor operations, and linear equation solving methods.

## 2. High-Level Architecture
- `main` package: user entry and mode routing
- `matrix` package: matrix model and matrix command layer
- `tensor` package: tensor model and tensor command layer
- `linear` package: numerical methods and method command layer

## 3. Execution Model
1. User selects mode (`m`, `t`, `l`)
2. Mode-specific command loop starts
3. Command dispatcher calls corresponding computational functions
4. Results are printed directly to console

## 4. Persistence Strategy
- Matrix variables: `<user-home>/variablesm.txt`
- Tensor variables: `<user-home>/variablest.txt`
- Linear module is interactive/stateless in file terms

## 5. Quality Notes
- Strong educational trace output for step-by-step visibility
- Error handling and logging are console-centric
- Opportunities exist for service-layer separation, stronger validation, and automated testing
