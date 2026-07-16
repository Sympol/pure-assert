# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2026-07-16

### Added

- **Enum assertions** via `field(String, E extends Enum<E>)` with `isIn()`, `isNotIn()`, `satisfies()`
- **Byte array assertions** via `field(String, byte[])` with `notEmpty()`, `maxSize()`, `satisfies()`
- **LocalDateTime assertions** via `field(String, LocalDateTime)` with `inPast()`, `inFuture()`, `after()`, `before()`, `satisfies()`
- `isGreaterThanOrEqualTo()` and `isLessThanOrEqualTo()` aliases on all numeric asserters (Integer, Long, Float, Double, BigDecimal)
- `toField()` alias on all asserters for clearer final field assignment in constructors
- `UUID_IS_NIL` and `UUID_VERSION_MISMATCH` error types in `AssertionErrorType` enum
- `satisfies()` method on `ArrayAsserter` for consistency with other asserters

### Fixed

- Javadoc typos: `lastName` replaced by `value` throughout `Assert.java` (~126 occurrences)
- Duplicate Javadoc blocks removed in `NumberValueTooLowException` and `NumberValueTooHighException`
- `LocalDateAsserter` now throws `NotBeforeTimeException`/`NotAfterTimeException` instead of deprecated `RequiredValueException`
- `UUIDIsNilException.type()` now returns `UUID_IS_NIL` instead of `MISSING_MANDATORY_VALUE`
- `UUIDVersionMismatchException.type()` now returns `UUID_VERSION_MISMATCH` instead of `MISSING_MANDATORY_VALUE`
- French comments translated to English in `Validation.java` and `RequiredValueException.java`

## [1.0.0] - 2026-02-06

### Added

- **Fluent API** for input assertions via `Assert.field()` entry point
- **String assertions**: `notBlank()`, `minLength()`, `maxLength()`, `matches()`, `email()`, `url()`, `uuid()`
- **Number assertions** (Integer, Long, Float, Double, BigDecimal): `min()`, `max()`, `positive()`, `strictlyPositive()`
- **Collection assertions**: `notEmpty()`, `maxSize()`, `noNullElement()`
- **Map assertions**: `notEmpty()`, `maxSize()`
- **Date/Time assertions** (LocalDate, Instant): `inPast()`, `inFuture()`, `before()`, `after()`
- **UUID assertions**: `notNull()`, `isVersion()`, `isNotNil()`
- **Custom predicates** via `satisfies()` on all asserters
- **Typed exception hierarchy** with rich metadata:
  - `AssertionException` (base class with `field()`, `type()`, `parameters()`)
  - `MissingMandatoryValueException`, `StringTooShortException`, `StringTooLongException`
  - `NumberValueTooLowException`, `NumberValueTooHighException`
  - `TooManyElementsException`, `NullElementInCollectionException`
  - `NotBeforeTimeException`, `NotAfterTimeException`
  - `UUIDIsNilException`, `UUIDVersionMismatchException`

### Changed

- Minimum Java version updated to **17** (LTS)

## [0.1.0] - 2026-02-03

### Added

- Initial release with core assertion functionality
- Publication to Maven Central
