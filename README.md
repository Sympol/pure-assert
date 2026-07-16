# Pure Assert

[![Maven Central](https://img.shields.io/maven-central/v/io.github.sympol/pure-assert.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.sympol/pure-assert)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://openjdk.org/)

A lightweight, expressive Java library for pure input assertions, designed for domains that choose to be **technically agnostic**.

Ensure your code is **Always Valid** with a fluent API that prioritizes **expressiveness, longevity, and semantic error richness** over framework-driven validation.

## ✨ Features

- 🚫 **Zero Dependencies** – Pure Java, no transitive pollution
- 🎯 **Typed Exceptions** – Get `StringTooShortException` instead of `IllegalArgumentException`
- 📝 **Rich Metadata** – Exceptions contain field name, invalid value, and constraints
- 🔗 **Fluent API** – Chainable, expressive, self-documenting code
- 🏗️ **DDD Optimized** – Perfect for critical business invariants

## 📦 Installation

### Maven

```xml
<dependency>
  <groupId>io.github.sympol</groupId>
  <artifactId>pure-assert</artifactId>
  <version>1.1.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.sympol:pure-assert:1.1.0'
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
| **Numbers** | `min(n)`, `max(n)`, `isGreaterThanOrEqualTo(n)`, `isLessThanOrEqualTo(n)`, `positive()`, `strictlyPositive()`, `satisfies(predicate)` |
| **Collections** | `notEmpty()`, `maxSize(n)`, `noNullElement()`, `satisfies(predicate)` |
| **Arrays** | `notEmpty()`, `maxSize(n)`, `noNullElement()`, `satisfies(predicate)` |
| **Maps** | `notEmpty()`, `maxSize(n)`, `satisfies(predicate)` |
| **Enums** | `isIn(values...)`, `isNotIn(values...)`, `satisfies(predicate)` |
| **byte[]** | `notEmpty()`, `maxSize(n)`, `satisfies(predicate)` |
| **Dates** | `inPast()`, `inFuture()`, `after(date)`, `before(date)`, `satisfies(predicate)` |
| **UUID** | `isVersion(v)`, `isNotNil()`, `satisfies(predicate)` |

## 🎯 Custom Validations

Extend the validation chain using `satisfies`:

```java
Assert.field("username", username)
      .notBlank()
      .satisfies(u -> u.startsWith("user_"), "Username must start with 'user_'");
```

## 🔒 Final Fields Support

The library is designed to work seamlessly with `final` fields. Since `.value()` returns the validated value, you can assign it directly in the constructor:

```java
public class Order {
    private final String orderId;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;

    public Order(String orderId, BigDecimal amount, LocalDateTime createdAt) {
        this.orderId = Assert.field("orderId", orderId)
                            .notBlank()
                            .value();  // Returns the validated String

        this.amount = Assert.field("amount", amount)
                           .notNull()
                           .isGreaterThanOrEqualTo(BigDecimal.ZERO)
                           .value();

        this.createdAt = Assert.field("createdAt", createdAt)
                              .notNull()
                              .inPast()
                              .value();
    }
}
```

This pattern ensures your domain objects are **Always Valid** while keeping fields immutable.

## 🆚 Comparison with Alternatives

| Feature | **Pure Assert** | Guava / Apache | Jakarta Validator |
|:--------|:---------------:|:--------------:|:-----------------:|
| **Exceptions** | **Typed & Rich** | Generic | ConstraintViolation |
| **Validation** | **Immediate** | Immediate | Deferred |
| **Dependencies** | **Zero** | Stable / Mature | Specification |
| **Primary Use** | **Core Domain** | Utility | Infrastructure / UX |

## 🧠 When Should I Use Pure Assert?

<table>
<tr>
<td width="50%">

### ✅ Strategic Choice

- **Complex business core**
- Long-lived applications (5+ years)
- Critical invariants (finance, legal, health)
- DDD / Hexagonal architectures

</td>
<td width="50%">

### 🚫 Consider Alternatives

- Simple **CRUD** / Data-centric apps
- Strong **time-to-market** pressure
- Need for **error aggregation**
- Highly localized/i18n UI forms

</td>
</tr>
</table>

> 💡 For peripheral validation (DTOs, UI), **Jakarta Validation** remains the standard. For core domain invariants where "Always Valid" is key, `pure-assert` provides stronger guarantees.

## 🏛️ Maintenance & Lock-in

We understand that adding a dependency to your domain is a major architectural decision.

- **Minimalist API**: The surface is intentionally small to limit breaking changes.
- **Easy Internalization**: The core logic is under 2000 lines of pure Java—if the library is ever abandoned, it is trivial to copy the source into your project.
- **Semantic Versioning**: We strictly follow SemVer. No breaking changes without a major version bump.


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

## 📖 Deep Dive

For a detailed technical explanation of the philosophy behind this library and a comparison with Jakarta Validation/Guava, read our featured article:
**[Mastering Domain Invariants: How pure-assert enhances DDD and Clean Architecture](https://symplice.hashnode.dev/mastering-domain-invariants-how-pure-assert-enhances-ddd-and-clean-architecture)**

## 📋 Changelog

### 1.1.0 (2026-07-16)

**New features:**
- `EnumAsserter` — `field(String, Enum)` with `isIn()`, `isNotIn()`, `satisfies()`
- `ByteArrayAsserter` — `field(String, byte[])` with `notEmpty()`, `maxSize()`, `satisfies()`
- `LocalDateTimeAsserter` — `field(String, LocalDateTime)` with `inPast()`, `inFuture()`, `after()`, `before()`, `satisfies()`

**Fixes:**
- Javadoc typos corrected (~126 occurrences)
- `LocalDateAsserter` now uses proper time exceptions instead of deprecated `RequiredValueException`
- UUID exceptions now return dedicated error types (`UUID_IS_NIL`, `UUID_VERSION_MISMATCH`)
- `ArrayAsserter` now supports `satisfies()`

See [CHANGELOG.md](CHANGELOG.md) for full details.

### 1.0.0 (2026-02-06)

Initial stable release with fluent API for Strings, Numbers, Collections, Maps, Dates, and UUIDs.

---

## 🤝 Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
