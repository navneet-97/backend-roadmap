# Programming Fundamentals

Before going deep into Java, build a strong understanding of these core programming fundamentals.

## Topics To Learn

1. [ ] Variables and Data Types
2. [ ] Operators
3. [ ] Conditions
4. [ ] Loops
5. [ ] Functions / Methods
6. [ ] Recursion
7. [ ] Scope
8. [ ] Mutability vs Immutability
9. [ ] Pass-by-Value
10. [ ] Stack vs Heap
11. [ ] References
12. [ ] Error Handling
13. [ ] Basic Complexity Analysis

Notes link: [link](https://cdn.codewithmosh.com/image/upload/v1702943783/cheat-sheets/java.pdf)
Video link: [link](https://www.youtube.com/watch?v=eIrMbAQSU34)

## Project

### CLI Library Management System

> Build a command-line library system to manage books and members, and handle borrowing and returning books.

**Features:** Add Book · Remove Book · Search Book · Register Member · Borrow Book · Return Book · Show Borrowed Books · Show Available Books


Important Point:
1. # Java Scanner: `nextInt()` + `nextLine()` Issue

## The Problem

When you use `nextInt()` followed by `nextLine()`, `nextLine()` may appear to skip input.

```java
int age = scanner.nextInt();
String name = scanner.nextLine();
```

If the user enters:

```text
25⏎
```

`nextInt()` reads only `25` and leaves the newline (`\n`) behind.

Then `nextLine()` immediately consumes that leftover newline and returns an empty string.

## Why?

Think of the input as:

```text
25\n
```

After `nextInt()`:

```text
25\n
  ↑
  newline is still here
```

Then `nextLine()` sees the newline immediately and returns:

```text
""
```

## Fix

Consume the leftover newline before reading the next line:

```java
int age = scanner.nextInt();
scanner.nextLine();

String name = scanner.nextLine();
```

The first `nextLine()` consumes the leftover newline.
The second `nextLine()` waits for the user's actual input.

## Important Difference

| Method         | Reads         |
| -------------- | ------------- |
| `nextInt()`    | Integer token |
| `nextDouble()` | Double token  |
| `next()`       | One token     |
| `nextLine()`   | Entire line   |

### Best Mental Model

`nextInt()` and `next()` are **token-based**.

`nextLine()` is **line-based**.

> `nextLine()` reads from the current cursor position until it reaches a newline.

## Alternative

To avoid this issue completely, you can read everything with `nextLine()` and convert when needed:

```java
int age = Integer.parseInt(scanner.nextLine());
double price = Double.parseDouble(scanner.nextLine());
```
