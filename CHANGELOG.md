# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.1.1] - 2019-11-07
### Added
- This CHANGELOG.md file to help keep track of changes
- Clojars badge to README to keep version info in docs current

### Fixed
- `wrap-mdc` will now stringify keys and values before inserting them into the
  MDC to prevent exceptions of type `java.lang.ClassCastException`

## [0.1.0] - 2019-11-03
### Added
- `wrap-mdc` ring middleware to support determining
- `wrap-clear-mdc` ring middleware

[Unreleased]: https://github.com/tessellator/ring-mdc/compare/v0.1.1...HEAD
[0.1.1]: https://github.com/tessellator/ring-mdc/compare/v0.1.0...v0.1.1
[0.1.0]: https://github.com/tessellator/ring-mdc/releases/tag/v0.1.0
