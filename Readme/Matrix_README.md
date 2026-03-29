# Matrix.java Documentation

- Source file: `src/matrix/Matrix.java`
- Package: `matrix`
- Role: Matrix entity and algorithm library

## 1. Business Purpose
Provides the core matrix data model and numerical operations used by matrix commands in the application.

## 2. Data Model
- `double[][] data`: matrix elements
- `int rows`, `int cols`: matrix dimensions

## 3. Public API Surface
### Constructors
- `Matrix(int rows, int cols)`
- `Matrix(double[][] data)`

### Core Algebra
- `addMatrices(Matrix a, Matrix b)`
- `subMatrices(Matrix a, Matrix b)`
- `multiply(Matrix a, Matrix b)`
- `transpose(Matrix m)`

### Advanced Operations
- `determinant(Matrix m)`
- `invert(Matrix m)`
- `rank(Matrix m)`
- `toRowEchelonForm(Matrix m)`
- `extractMatrix(Matrix m, int numRows, int numCols, int startRow, int startCol)`
- `sparseMatrix(Matrix m)`
- `powerIteration(Matrix m, double[] initialVector, int maxIterations, double tolerance)`

### Utility / Conversion
- `fromString(String matrixData)`
- `toString()`
- `print()`
- `getData()`, `setData(...)`

## 4. Input/Output Contract
- Parsing format (`fromString`): rows separated by `;`, values by whitespace
  - Example: `1 2 3;4 5 6`
- Serialization (`toString`): same row convention using `;`
- Many methods print step-by-step calculations to stdout for transparency

## 5. Validation and Error Behavior
- Dimension mismatch in arithmetic throws `IllegalArgumentException`
- Inversion of singular matrix throws `ArithmeticException`
- Submatrix extraction validates bounds and throws `IllegalArgumentException`
- Scalar utility methods assume finite numeric input

## 6. Complexity Notes
- `multiply`: O(r * c * k)
- Recursive `determinant`/`invert` via minors are expensive for larger matrices
- Row-echelon based rank is more scalable than recursive determinant-based approaches

## 7. Implementation Notes
- `toRowEchelonForm` clones input data first to avoid mutating original matrix
- `powerIteration` estimates dominant eigenvalue using repeated normalization
- ANSI constants exist for colored error strings in thrown messages

## 8. Known Risks / Improvement Backlog
- Floating-point equality checks (`== 0`) can be numerically unstable
- No explicit enforcement of square matrix for all methods where mathematically required
- Logging and computation are coupled; production-grade separation is recommended
