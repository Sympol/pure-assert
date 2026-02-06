# Contributing to Pure Assert

Thank you for your interest in contributing to Pure Assert! This document provides guidelines and instructions for contributing.

## Code of Conduct

Be respectful and constructive in all interactions. We welcome contributors of all experience levels.

## How to Contribute

### Reporting Bugs

1. Check if the issue already exists in the [Issues](https://github.com/sympol/pure-assert/issues) section.
2. If not, create a new issue with:
   - A clear title
   - Steps to reproduce
   - Expected vs. actual behavior
   - Java version and environment details

### Suggesting Features

Open an issue with the `enhancement` label. Describe:
- The use case
- The proposed API (if applicable)
- Why it fits this library's philosophy

### Pull Requests

1. **Fork** the repository and create a branch from `main`.
2. **Write tests** for any new functionality.
3. **Follow the code style**: no external dependencies, comprehensive Javadoc.
4. **Run the build**: `mvn clean verify`
5. **Submit** your PR with a clear description.

## Development Setup

### Prerequisites

- Java 15+
- Maven 3.8+

### Building

```bash
mvn clean install
```

### Running Tests

```bash
mvn test
```

### Generating Javadoc

```bash
mvn javadoc:javadoc
```

## Guidelines

### Zero Dependencies

This library is intentionally **zero-dependency**. Do not add any runtime dependencies. The `maven-enforcer-plugin` will reject any transitive dependencies.

### Javadoc

All public classes and methods must have Javadoc comments. Run `mvn javadoc:javadoc` to check for warnings.

### Exception Design

When adding new exceptions:
1. Extend `AssertionException`
2. Implement `type()` returning an appropriate `AssertionErrorType`
3. Use a builder pattern for complex exceptions
4. Include the field name and relevant metadata

### Asserter Design

When adding new asserters (e.g., `StringAsserter`):
1. Follow the fluent API pattern
2. Return `this` for chaining
3. Include a `value()` method to retrieve the validated value
4. Throw specific exceptions, not generic ones

## Questions?

Feel free to open an issue for any questions about contributing.

---

Thank you for helping make Pure Assert better! 🚀
