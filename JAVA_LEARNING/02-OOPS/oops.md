# Object-Oriented Programming in Java
---

## Table of Contents

1. [Why OOP Exists](#1-why-oop-exists)
2. [Classes and Objects](#2-classes-and-objects)
3. [Constructors](#3-constructors)
4. [Encapsulation](#4-encapsulation)
5. [Inheritance](#5-inheritance)
6. [Polymorphism](#6-polymorphism)
7. [Abstraction](#7-abstraction)
8. [Interfaces](#8-interfaces)
9. [Abstract Classes](#9-abstract-classes)
10. [Composition vs Inheritance](#10-composition-vs-inheritance)
11. [Association, Aggregation, Composition](#11-association-aggregation-composition)
12. [Coupling](#12-coupling)
13. [Cohesion](#13-cohesion)
14. [SOLID Principles](#14-solid-principles)
15. [Design Principles](#15-design-principles-dry-kiss-yagni-separation-of-concerns)

---

## 1. Why OOP Exists

Without OOP, programs become a giant collection of unrelated variables and functions. OOP gives you:

- **Organize code** around real-world concepts (objects)
- **Protect data** so objects can't be put into invalid states
- **Reuse code** through inheritance and composition
- **Write flexible code** that's easy to extend and modify
- **Model complexity** by breaking systems into manageable pieces

OOP is built on the idea that **data and the behavior that operates on that data should live together** inside objects.

---

## 2. Classes and Objects

### What is a Class?

A **class** is a blueprint. It defines what an object is (its state) and what it can do (its behavior).

### What is an Object?

An **object** is a particular instance of a class. Multiple objects can be created from the same class — they share the same structure but can have different state.

```java
public class Human {
    int eyes;           // state (fields)
    int ears;
    String skinColor;

    void walk() {       // behavior (methods)
        // walking logic
    }
}
```

```java
Human h1 = new Human();
Human h2 = new Human();
```

`Human` is the class (blueprint). `h1` and `h2` are objects (instances). They have the same structure but can have different skin colors.

### Reference Type vs Reference Variable vs Object

```java
Human h = new Human();
```

| Term | What it is |
|---|---|
| Reference type | The type declared: `Human` |
| Reference variable | The variable name: `h` |
| Object | The actual instance in memory: `new Human()` |

### Why Do Classes Exist?

Because we don't want our program to be a giant collection of unrelated variables and functions. Classes group related **state** (fields) and **behavior** (methods) together.
