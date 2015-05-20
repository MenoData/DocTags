DocTags
=======

Additional JavaDoc-Tags


Goals:
------

This small library mainly serves for improvements of the JavaDoc of Time4J but might also be useful for any other java project. Four extra tags are offered:

- **doctags.concurrency** (documents the concurrency behaviour of a class)
- **doctags.experimental** (documents the experimental status of a class)
- **doctags.spec** (documents a specification requirement)
- **doctags.exclude** (excludes a program element from public API and JavaDoc)

Usage:
------

```java
/** @doctags.concurrency {immutable} */
```

will be displayed like

**Concurrency:** This class is immutable and can be used by multiple threads in parallel.

