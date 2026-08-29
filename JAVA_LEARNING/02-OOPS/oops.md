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

---

## 5. Inheritance

**Inheritance** allows one class to acquire properties and behavior from another class.

### Basic Example

```java
class Animal {
    String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println("Eating");
    }
}
```

```java
class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    public void bark() {
        System.out.println("Barking");
    }
}
```

```java
Dog dog = new Dog("Rex");
dog.eat();   // inherited from Animal
dog.bark();  // defined in Dog
```

### IS-A vs HAS-A

| | IS-A | HAS-A |
|---|---|---|
| Represents | Inheritance | Composition |
| Example | Dog **IS-A** Animal | Car **HAS-A** Engine |
| Code | `class Dog extends Animal` | `class Car { private Engine engine; }` |

**When considering inheritance, ask**: Is the child genuinely a specialized form of the parent? Not "can I reuse some code from the parent." Use composition for code reuse.

### `super()` — Calling the Parent Constructor

```java
class Dog extends Animal {
    public Dog(String name) {
        super(name);  // parent constructor runs first
    }
}
```

**First parent state is initialized, then child state.** `super()` should always be the first statement.

Why must the parent constructor execute? Because the child is also an instance of the parent type. The parent portion of the object needs initialization too.

**If the child constructor doesn't explicitly call `super(...)`, Java implicitly inserts `super()`.** But this only works if the parent has an accessible no-argument constructor.

```java
class Animal {
    Animal(String name) {}  // no no-argument constructor
}

class Dog extends Animal {
    Dog() {}  // won't compile! Java tries super() but Animal() doesn't exist
}
```

You must explicitly call `super(name)`.

### Accessing Parent Members with `super`

```java
class Dog extends Animal {
    @Override
    public void eat() {
        super.eat();  // call the parent implementation
        System.out.println("Dog eating");
    }
}
```

### Method Overriding

A child provides its own implementation of an inherited method:

```java
class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof");
    }
}
```

**Rules for overriding:**
- The child method must have a compatible return type
- The child method cannot be more restrictive in access
- Use `@Override` to let the compiler verify you're actually overriding

**What can't be overridden:**
- `final` methods (inherited, not another implementation)
- `private` methods (not inherited, not accessible out of class)
- `static` methods (they're hidden, not overridden)
- Constructors (not inherited)

### Inheritance Levels

Java supports **multilevel inheritance**:

```
Animal
   ↑
 Mammal
   ↑
  Dog
```

Every class ultimately extends `Object`. If you write `class Dog {}`, Java treats it as `class Dog extends Object {}`. That's why every object has `toString()`, `equals()`, etc.

### Multiple Inheritance — Not Supported for Classes

This is invalid:

```java
class Dog extends Animal, Pet {}  // compile error
```

Java supports only single class inheritance. **Reason: the Diamond Problem.** If two parent classes have the same method, the child doesn't know which one to call.

```java
class A { public void show() {} }
class B { public void show() {} }
class C extends A, B {}
// C obj = new C(); obj.show();  // Which show()?
```

Java uses **interfaces** for multiple inheritance instead.

### Upcasting

```java
class Dog extends Animal {}

Dog dog = new Dog();
Animal animal = dog;  // upcasting: child → parent reference
```

This is safe because Dog IS-A Animal. You often upcast because you care about general behavior, not the specific implementation.

### When to Use Inheritance

- There is a genuine specialization relationship
- The child should satisfy the parent type
- There is meaningful shared behavior/state
- You want runtime polymorphism

---

## 6. Polymorphism

**Polymorphism** means the same interface/reference can represent different concrete types, and the behavior depends on the actual object.

### Why Do We Need It?

Without polymorphism:

```java
class Dog { void makeSound() { System.out.println("Woof"); } }
class Cat { void makeSound() { System.out.println("Meow"); } }

Dog dog = new Dog();
Cat cat = new Cat();
dog.makeSound();  // must know it's a Dog
cat.makeSound();  // must know it's a Cat
```

With polymorphism:

```java
class Animal {
    void makeSound() {}
}

class Dog extends Animal {
    @Override
    void makeSound() { System.out.println("Woof"); }
}

class Cat extends Animal {
    @Override
    void makeSound() { System.out.println("Meow"); }
}
```

```java
Animal animal1 = new Dog();
Animal animal2 = new Cat();
```

Common type: `Animal`. Actual objects: `Dog` and `Cat`.

### Compile-Time Polymorphism (Method Overloading)

Same method name, different parameter lists. The compiler determines which method to call:

```java
class Calculator {
    int add(int a, int b) { return a + b; }
    int add(int a, int b, int c) { return a + b + c; }
    double add(double a, double b) { return a + b; }
}
```

You **cannot** overload by changing only the return type.

### Runtime Polymorphism (Method Overriding)

```java
Animal animal = new Dog();
animal.makeSound();  // executes Dog's method
```

The actual implementation is determined at **runtime** based on the object type, not the reference type.

### Dynamic Method Dispatch

```java
Animal animal;
animal = new Dog();
animal.makeSound();  // Dog's method

animal = new Cat();
animal.makeSound();  // Cat's method
```

The same `animal.makeSound()` produces different behavior depending on the actual object. This is the core of runtime polymorphism.

### What the Compiler Sees vs What Runs

```java
class Animal {
    void eat() {}
}

class Dog extends Animal {
    void bark() {}
}

Animal animal = new Dog();
animal.eat();    // works: eat() is defined in Animal
// animal.bark(); // compile error: compiler looks at reference type (Animal)
```

The compiler uses the **reference type** to decide what methods are callable. The JVM uses the **actual object type** to decide which overridden method runs.

### Upcasting and Downcasting

**Upcasting** (safe, implicit):

```java
Dog dog = new Dog();
Animal animal = dog;  // safe: Dog IS-A Animal
```

**Downcasting** (requires explicit cast, not safe):

```java
Animal animal = new Dog();
Dog dog = (Dog) animal;   // works here
dog.bark();

Animal animal2 = new Cat();
Dog dog2 = (Dog) animal2;  // compiles but ClassCastException at runtime!
```

### `instanceof`

Check before downcasting:

```java
if (animal instanceof Dog dog) {
    dog.bark();
}
```

### Polymorphism with Collections

```java
List<Animal> animals = new ArrayList<>();
animals.add(new Dog());
animals.add(new Cat());
animals.add(new Dog());

for (Animal animal : animals) {
    animal.makeSound();  // polymorphism works naturally
}
```

The list doesn't need to know every concrete subtype.

### Polymorphism Eliminates Type Checking

Without polymorphism:

```java
if (animal instanceof Dog) {
    ((Dog) animal).makeSound();
} else if (animal instanceof Cat) {
    ((Cat) animal).makeSound();
}
```

With polymorphism:

```java
animal.makeSound();  // the object itself provides the right behavior
```
