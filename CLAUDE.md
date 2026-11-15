# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

This is a minimal, single-module IntelliJ IDEA Java scratch project (module name `untitled`, see `untitled.iml`). There is no build tool (no Maven/Gradle) — source is compiled directly with `javac`/IntelliJ's own compiler.

This directory is its own Git repository (branch `main`, remote `origin` = https://github.com/adrianox073/untitled.git). It sits physically inside a clone of `eugenp/tutorials` (`D:\Cursos\tutorials`) but is unrelated to it; run git commands from this directory.

## Structure

- `src/Prueba.java` — the only real source file, module entry point (`public class Prueba`, `main` method).
- `test.java` (repo root) — a scratch/example file with intentional typos (`STring`, `maind`, `isEmpmty`, undefined `vacio` variable) and will not compile as-is.
- `out/production/untitled/` — IntelliJ's compiled output directory.

Both `Prueba.java` and `test.java` currently contain the same bug on purpose: they call `.isEmpty()` on a `null` String, which throws a `NullPointerException` at runtime.

## Common commands

Compile and run the main class from the project root:

```
javac -d out/production/untitled src/Prueba.java
java -cp out/production/untitled Prueba
```

There are no tests, linter, or build scripts configured in this project.