# Tensor.java Documentation

- Source file: `src/tensor/Tensor.java`
- Package: `tensor`
- Role: 3D tensor entity and computational utility library

## 1. Business Purpose
Provides tensor data representation and tensor arithmetic/transform operations used by tensor CLI workflows.

## 2. Data Model
- `double[][][] data`
- `int depth`, `int rows`, `int cols`

## 3. Public API Surface
### Constructors
- `Tensor(int depth, int rows, int cols)`
- `Tensor(double[][][] data)`

### Tensor-Tensor Operations
- `add`, `subtract`, `multiply`, `divide` (element-wise)
- `dotProduct` (matrix-style product per depth slice)

### Tensor-Scalar Operations
- `scalarAdd`, `scalarSub`, `scalarMul`, `scalarDiv`

### Structural Operations
- `reshape(int newDepth, int newRows, int newCols)`
- `transpose()` (maps `[d][r][c]` to `[c][r][d]`)
- `slicingTensor(...)`
- `dimension()`
- `dimensionalstride()`

### Utility
- `fromString(String tensorData)`
- `toString()`
- `print()`
- `getData()`, `setData(...)`

## 4. Serialization Contract
- Depth slices separated by `;`
- Rows within a depth separated by `,`
- Elements within a row separated by whitespace

## 5. Validation and Error Behavior
- Element-wise operations require identical dimensions
- `reshape` enforces equal total element count
- `divide`/`scalarDiv` guard against division by zero

## 6. Implementation Notes
- Many operations print arithmetic traces to stdout
- `dotProduct` assumes compatible per-slice matrix multiplication dimensions

## 7. Known Risks / Improvement Backlog
- Validation for `dotProduct` dimensional compatibility is implicit and not explicit
- Mixed responsibility: business logic plus verbose console output in same methods
- No immutable tensor option; state mutation risks when sharing data references
