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

---

## 3. Constructors

A **constructor** is a special method that runs when you create an object. It initializes the object into a valid state.

### How Object Creation Works

```java
BankAccount account = new BankAccount("ACC001", "user", 5000);
```

```
new BankAccount(...)
       ↓
Java allocates memory for the object
       ↓
Fields initially receive default values
       ↓
Constructor executes
       ↓
Constructor initializes the object
       ↓
Reference returned
       ↓
account points to the object
```

`new` creates the object; the constructor initializes it.

### Basic Constructor

```java
public class BankAccount {

    private String accountNumber;
    private String ownerName;
    private BigDecimal balance;

    public BankAccount(String accountNumber, String ownerName, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }
}
```

`this` refers to the current object. `this.accountNumber = accountNumber` distinguishes the field from the parameter.

### What is an Invariant?

An **invariant** is a condition that should always be true for an object. For a bank account: `balance >= 0`.

```java
public BankAccount(BigDecimal initialBalance) {
    if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException("Initial balance cannot be negative");
    }
    this.balance = initialBalance;
}

public void withdraw(BigDecimal amount) {
    if (amount.compareTo(balance) > 0) {
        throw new IllegalArgumentException("Insufficient balance");
    }
    balance = balance.subtract(amount);
}
```

Both creation and behavior protect the invariant. The object controls how its state changes.

### Constructor Types

#### 1. No-Argument Constructor

```java
public BankAccount() {
}
```

If you don't write any constructor, Java provides a default no-argument constructor automatically.

**Rule**: Once you define any constructor yourself, Java no longer provides the default no-argument constructor. If you need one, write it explicitly.

```java
public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }
}

// This won't compile:
// User user = new User();  // No default constructor
```

#### 2. Constructor Overloading

Multiple constructors with different parameter lists:

```java
public class User {
    private String name;
    private String email;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
```

```java
User user1 = new User("user");
User user2 = new User("User", "user@example.com");
```

**Don't abuse constructor overloading.** Too many constructors create the **telescoping constructor problem**:

```java
// Don't do this:
User(String name)
User(String name, String email)
User(String name, String email, int age)
User(String name, String email, int age, String city)
User(String name, String email, int age, String city, String phone)
```

This becomes ugly. You now have a class with many ways to construct it, and it's difficult to know which combination represents a valid object.
This is called telescoping constructor problem. In larger applications, alternatives such as builders, factories can be better.

#### 3. Constructor Chaining

One constructor can call another to avoid duplicating initialization logic:

```java
public class User {
    private String name;
    private String email;

    public User(String name) {
        this(name, "user@example.com");  // calls the other constructor
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
```

### `this()` vs `super()`

| | `this(...)` | `super(...)` |
|---|---|---|
| Calls | Another constructor in the same class | A constructor in the parent class |
| Must be | First statement in the constructor | First statement in the constructor |

You **cannot** use both in the same constructor:

```java
Dog() {
    this("");      // can't have both
    super("");     // only one constructor chain
}
```
Bcoz a constructor can have only one constructor chain leading upward. If this() calls another constructor in the same class, that constructor will eventually call super(). And we don't need 2 independent paths.

### Constructor Restrictions

A constructor:
- Has the same name as the class
- Has no return type
- Can be overloaded
- Can have access modifiers
- Isn't inherited
- Can call another constructor using `this(...)`
- Can call the parent constructor using `super(...)`
- Cannot be `abstract`, `static`, or `final`

### Private Constructors

```java
public class User {
    private User() {}
}
```

Nobody outside the class can create objects. Used in utility classes to prevent meaningless instances, and in design patterns like Singleton and Factory.

---

## 4. Encapsulation

**Encapsulation** means an object should control how its state can be changed.
Or
Encapsulation means controlling access to an object's state and protecting the rules that govern that state.

### The Problem Without Encapsulation

```java
public class BankAccount {
    int balance;
}
```

Anyone can do:

```java
account.balance = -500000;
```

Your object is now invalid. The class has no control over itself.

### The Solution

```java
public class BankAccount {
    private int balance;

    public int getBalance() {
        return this.balance;
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        if (balance < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance -= amount;
    }
}
```

The object now protects its own state. Callers must go through controlled methods.

### Encapsulation Also Hides Implementation Details

The caller doesn't need to know *how* you get the balance. You could have a `balance` field, or calculate it from a list of transactions. The interface stays the same.

### Encapsulation Maintains Invariants

```java
public void changePrice(BigDecimal price) {
    if (price == null || price.signum() <= 0) {
        throw new IllegalArgumentException("Price must be greater than zero");
    }
    this.price = price;
}
```

Price can never be negative. The object protects itself.

### Encapsulation vs Data Hiding

| | Data Hiding | Encapsulation |
|---|---|---|
| What | Don't allow direct access to internal details | Bundle state and behavior behind a controlled interface |
| Scope | Narrower | Broader |
| Example | `private BigDecimal balance;` | private fields + controlled operations + business rules + hidden implementation |
