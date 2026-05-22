# Recursive Descent Compiler

A compiler implemented in Java for a small imperative language, featuring lexical analysis, LL(1) recursive descent parsing, AST generation, semantic analysis, type checking and stack-based code generation targeting the `dc` language.

---

## Features

- Lexical analysis via scanner/tokenizer
- LL(1) recursive descent parser
- Abstract Syntax Tree (AST) generation
- Semantic analysis and type checking
- Stack-based code generation (`dc`)
- Automated JUnit test suite
- Maven build system

---

## Compiler Architecture

The compiler pipeline is composed of four main stages:

### 1. Scanner

The scanner performs lexical analysis by converting the source code into a sequence of tokens.

Supported token categories include:
- identifiers
- integer and float literals
- arithmetic operators
- assignment operators
- delimiters
- keywords (`int`, `float`, `print`)

### 2. Parser

The parser implements an LL(1) recursive descent parsing strategy and builds an Abstract Syntax Tree representing the source program structure.

### 3. Semantic Analyzer / Type Checker

The semantic analysis phase decorates the AST with type information and validates semantic correctness.

Checks include:
- variable declarations
- type compatibility
- expression correctness
- assignment validation

### 4. Code Generator

The code generator traverses the AST and emits stack-machine code targeting the Unix `dc` calculator language.

---

## Grammar

```txt
0.  Prg  -> DSs $
1.  DSs  -> Dcl DSs
2.  DSs  -> Stm DSs
3.  DSs  -> ε
4.  Dcl  -> Ty id DclP
5.  DclP -> ;
6.  DclP -> = Exp;
7.  Stm  -> id Op Exp;
8.  Stm  -> print id;
9.  Exp  -> Tr ExpP
10. ExpP -> + Tr ExpP
11. ExpP -> - Tr ExpP
12. ExpP -> ε
13. Tr   -> Val TrP
14. TrP  -> * Val TrP
15. TrP  -> / Val TrP
16. TrP  -> ε
17. Ty   -> float
18. Ty   -> int
19. Val  -> intVal
20. Val  -> floatVal
21. Val  -> id
22. Op   -> =
23. Op   -> opAss
```

---

## Code Generation Example

Input program:

```c
int a = 0;
a += 1;
print a;
```

Generated `dc` code:

```dc
0 sa
la 1 + sa
la p P
```

---

## Build & Test

Clone the repository and run:

```bash
mvn clean test
```

The project includes automated JUnit tests covering:
- scanner
- parser
- token handling
- semantic analysis
- type checking
- code generation

---

## Technologies

- Java 21
- Maven
- JUnit 5

---

## Notes

This project was developed for educational purposes as part of a compiler construction course and focuses on the implementation of the fundamental phases of a compiler:
- lexical analysis
- syntax analysis
- semantic analysis
- code generation
