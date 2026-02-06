# Pure Assert

[![Maven Central](https://img.shields.io/maven-central/v/io.github.sympol/pure-assert.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.sympol/pure-assert)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://openjdk.org/)

A lightweight, **zero-dependency**, expressive Java library for pure input assertions.

Ensure your code is **Always Valid** with a fluent and readable API, perfect for domain invariants or input validation in any layer.

## ✨ Features

- 🚫 **Zero Dependencies** – Pure Java, no transitive pollution
- 🎯 **Typed Exceptions** – Get `StringTooShortException` instead of `IllegalArgumentException`
- 📝 **Rich Metadata** – Exceptions contain field name, invalid value, and constraints
- 🔗 **Fluent API** – Chainable, expressive, self-documenting code
- 🏗️ **DDD Ready** – Perfect for domain invariants in Clean Architecture

## 📦 Installation

### Maven

```xml
<dependency>
  <groupId>io.github.sympol</groupId>
  <artifactId>pure-assert</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.sympol:pure-assert:1.0.0'
```

## 🚀 Quick Start

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

## 📚 Supported Assertions

| Type | Available Methods |
|------|-------------------|
| **Strings** | `notBlank()`, `minLength(n)`, `maxLength(n)`, `matches(pattern)`, `email()`, `url()`, `satisfies(predicate)` |
| **Numbers** | `min(n)`, `max(n)`, `positive()`, `strictlyPositive()`, `satisfies(predicate)` |
| **Collections** | `notEmpty()`, `maxSize(n)`, `noNullElement()` |
| **Dates** | `inPast()`, `inFuture()`, `after(date)`, `before(date)` |
| **UUID** | `isValid()`, `isVersion(v)`, `isNotNil()` |

## 🎯 Custom Validations

Extend the validation chain using `satisfies`:

```java
Assert.field("username", username)
      .notBlank()
      .satisfies(u -> u.startsWith("user_"), "Username must start with 'user_'");
```

## 🆚 Comparison with Alternatives

| Feature | **Pure Assert** | Guava / Apache | Jakarta Validator |
|:--------|:---------------:|:--------------:|:-----------------:|
| **Dependencies** | **Zero** | Heavy | Heavy |
| **API Style** | Fluent | Static | Annotations |
| **Typed Exceptions** | ✅ Yes | ❌ No | ❌ No |
| **Rich Metadata** | ✅ Yes | Limited | Yes |
| **Clean Domain** | ✅ Perfect | Acceptable | Poor |

## 🧠 When Should I Use Pure Assert?

<table>
<tr>
<td width="50%">

### ✅ Good Fit

- Domain invariants
- Constructor validation
- Business rules inside entities or value objects
- Clean / Hexagonal / DDD-inspired architectures

</td>
<td width="50%">

### 🚫 Not a Good Fit

- DTO validation for REST APIs
- UI or form validation
- Internationalized error messages
- Aggregating multiple validation errors

</td>
</tr>
</table>

> 💡 For API or UI validation, consider **Jakarta Validation** (Bean Validation) instead.


## 🏛️ Clean Architecture Integration

To use this library while enforcing a "Zero Dependency" rule in your domain layer:

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
                            <exclude>*</exclude>
                        </excludes>
                        <includes>
                            <include>*:*:*:*:test</include>
                            <!-- Explicitly allow pure-assert (zero transitive deps) -->
                            <include>io.github.sympol:pure-assert</include>
                        </includes>
                    </bannedDependencies>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## 🤝 Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
