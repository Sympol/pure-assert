# Pure Assert

A lightweight, zero-dependency, expressive Java library for pure input assertions.

Ensure your code is **Always Valid** with a fluent and readable API, perfect for domain invariants or input validation in any layer.

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>io.github.sympol</groupId>
  <artifactId>pure-assert</artifactId>
  <version>0.1.0</version>
</dependency>
```

## Usage

### Fluent API (Recommended)

The `Assert` class provides a fluent API to validate your fields. If an assertion fails, it throws a specific sub-class of `AssertionException`.

```java
import io.github.sympol.pure.asserts.Assert;

public class User {
    private final String email;
    private final int age;

    public User(String email, int age) {
        this.email = Assert.field("email", email)
                        .notBlank()
                        .email()
                        .value(); // Returns the validated value

        this.age = Assert.field("age", age)
                        .min(18)
                        .max(120)
                        .value();
    }
}
```

### Supported Assertions

- **Strings**: `notBlank()`, `minLength(n)`, `maxLength(n)`, `matches(pattern)`, `email()`, `url()`, `satisfies(predicate)`.
- **Numbers**: `min(n)`, `max(n)`, `positive()`, `strictlyPositive()`, `satisfies(predicate)`.
- **Collections/Maps**: `notEmpty()`, `maxSize(n)`, `noNullElement()`.
- **Dates (LocalDate/Instant)**: `inPast()`, `inFuture()`, `after(date)`, `before(date)`.
- **UUID**: `isValid()`, `isVersion(v)`, `isNotNil()`.

### Custom Validations

You can easily extend the validation chain using `satisfies`:

```java
Assert.field("username", username)
      .notBlank()
      .satisfies(u -> u.startsWith("user_"), "Username must start with 'user_'");
```

## Comparison with Alternatives

| Feature | **Pure Assert** | Guava / Apache | Jakarta Validator |
| :--- | :---: | :---: | :---: |
| **Dependencies** | **Zero (Pure Java)** | Heavy (Transitive) | Heavy (Framework) |
| **API Style** | Fluent / Chainable | Static / Utility | Annotation-based |
| **Typed Exceptions**| **Yes (Explicit)** | No (Generic) | No (ConstraintViolation) |
| **Rich Metadata** | **Yes (Field/Value)** | Limited | Yes |
| **Clean Domain** | **Perfect** | Acceptable | Poor (Pollution) |

## Why use Pure Assert?

1. **Zero Dependencies**: Pure Java, no transitive pollution. Ideal for clean architecture and pure domains.
2. **Explicit Exceptions**: Get `NumberValueTooLowException` instead of a generic `IllegalArgumentException`.
3. **Rich Metadata**: Exceptions contain the field name, the invalid value, and the expected constraints.
4. **Expressive Code**: Your constructors and methods become self-documenting.

## Clean Architecture & Zero Dependencies

To authorize this library while maintaining a strict "Zero Dependency" rule in your other projects, simply use the `<includes>` section of the `maven-enforcer-plugin`. It allows you to define explicit exceptions to the exclusion rule.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-enforcer-plugin</artifactId>
    <version>3.0.0</version>
    <executions>
        <execution>
            <id>enforce-no-external-deps</id>
            <goals>
                <goal>enforce</goal>
            </goals>
            <configuration>
                <rules>
                    <bannedDependencies>
                        <excludes>
                            <!-- Banned all by default -->
                            <exclude>*</exclude>
                        </excludes>
                        <includes>
                            <!-- 1. Allow test dependencies (JUnit, etc.) -->
                            <include>*:*:*:*:test</include>
                            <!-- 2. Explicitly allow this library -->
                            <!-- We accept it because we know it has no transitive dependencies -->
                            <include>io.github.sympol:pure-assert</include>
                        </includes>
                    </bannedDependencies>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```
