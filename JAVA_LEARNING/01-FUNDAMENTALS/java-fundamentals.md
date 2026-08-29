# Java Fundamentals

A practical reference for writing and reasoning about basic Java programs.

## 1. Java at a glance

Java source code is compiled into **bytecode** (`.class` files). The Java Virtual Machine (JVM) runs that bytecode on the current platform.

```text
Source (.java) → compiler (javac) → bytecode (.class) → JVM → operating system / hardware
```

Java is:

- **Statically typed**: every variable has a declared type, checked at compile time.
- **Object-oriented**: programs are built from classes and objects (while primitives are not objects).
- **Case-sensitive**: `total`, `Total`, and `TOTAL` are different names.
- **Garbage-collected**: unreachable objects can have their memory reclaimed automatically.

### Smallest useful program

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, Java!");
    }
}
```

`main` is the usual entry point. `String[] args` holds command-line arguments. A public top-level class must be in a file with the same name: `Main.java`.

### Naming conventions

| Item | Convention | Example |
|---|---|---|
| Class, interface, enum | PascalCase | `BankAccount` |
| Method, variable | camelCase | `calculateTotal`, `itemCount` |
| Constant (`static final`) | UPPER_SNAKE_CASE | `MAX_ATTEMPTS` |
| Package | lowercase, usually reversed domain | `com.example.app` |

Choose meaningful names. Prefer `totalPrice` to `x` except for short-lived, obvious values such as loop indexes.

### Packages, imports, and comments

A **package** groups related classes and prevents naming collisions. The package declaration, when present, is the first non-comment statement in a source file. An `import` lets code use a type by its short name.

```java
package com.example.store;

import java.util.ArrayList;
import java.util.List;

// A short explanation of the next line.
List<String> items = new ArrayList<>();
```

Avoid wildcard imports such as `import java.util.*;` in production code when explicit imports are clearer. Java comments are `//` for one line and `/* ... */` for a block. Use comments to explain *why*, not to restate obvious code.

## 2. Variables, types, and literals

A variable stores a value of its declared type.

```java
int age = 25;
age = 26;          // valid
// age = "twenty-six"; // compile-time error
```

### Primitive types

Primitives store simple values directly; they are not objects.

| Type | Size | Range / use | Literal examples |
|---|---:|---|---|
| `byte` | 8 bits | -128 to 127 | `byte b = 12;` |
| `short` | 16 bits | -32,768 to 32,767 | `short s = 32000;` |
| `int` | 32 bits | whole numbers; usual integer type | `int n = 42;` |
| `long` | 64 bits | large whole numbers | `long id = 9_000_000_000L;` |
| `float` | 32 bits | approximate decimal; rarely preferred | `float f = 3.5F;` |
| `double` | 64 bits | approximate decimal; usual floating-point type | `double d = 3.5;` |
| `char` | 16 bits | one UTF-16 code unit | `char grade = 'A';` |
| `boolean` | JVM-dependent storage | `true` or `false` | `boolean ready = true;` |

Whole-number literals are `int` by default; decimal literals are `double` by default. Use `L` for a `long` literal outside `int` range and `F` for a `float` literal. Integer literals that fit may be assigned to `byte` or `short` without a suffix:

```java
byte small = 100;
short year = 2026;
long population = 8_000_000_000L;
float ratio = 0.75F;
```

Underscores improve readable numeric literals: `1_000_000`.

> `float` and `double` are binary floating-point types, so many decimal fractions are not represented exactly. Use `BigDecimal` for financial calculations; it is beyond the core scope of these notes.

### Reference types

Variables of a reference type hold a reference to an object (or `null`). Common examples are `String`, arrays, and objects created from classes.

| Primitive | Reference type |
|---|---|
| Stores the value itself | Stores a reference to an object |
| Cannot be `null` | Can be `null` |
| Has a fixed built-in set of types | Includes classes, arrays, interfaces, enums, records |
| Example: `int count = 3;` | Example: `String name = "Asha";` |

### Declaration and initialization

```java
int count;          // declared, but local variables must be assigned before use
count = 3;

String name = "Asha"; // declaration and initialization together
var message = "Hi";   // local-variable type inference; inferred as String
```

`var` is only for local variables with an initializer; it does not make Java dynamically typed.

## 3. Conversion, casting, and arithmetic

### Widening and narrowing

**Widening conversion** goes to a type that can represent the original value and is automatic. **Narrowing conversion** may lose information, so it needs an explicit cast.

```java
int count = 42;
long larger = count;       // widening
double decimal = count;    // widening

double price = 19.99;
int whole = (int) price;   // narrowing: 19 (fraction is discarded)
```

Casting an out-of-range integer keeps only the low-order bits. It does not clamp the value:

```java
int a = 300;        // binary ends in 0010 1100
byte b = (byte) a;  // 44
```

`byte` and `short` operands are promoted to `int` during arithmetic:

```java
byte x = 10;
byte y = 20;
// byte sum = x + y;       // error: x + y is int
byte sum = (byte) (x + y); // safe here because the result fits
```

### Operators and precedence

Common arithmetic operators are `+`, `-`, `*`, `/`, and `%` (remainder). `*`, `/`, and `%` have higher precedence than `+` and `-`; expressions otherwise evaluate left to right. Use parentheses when they improve clarity.

```java
int result = 2 + 3 * 4;    // 14
int clearer = (2 + 3) * 4; // 20
int quotient = 7 / 2;      // 3: integer division
double exact = 7 / 2.0;    // 3.5
```

`++x` increments before producing its value; `x++` produces the current value, then increments. Avoid using either inside complex expressions.

```java
int x = 5;
int a = x++; // a is 5, x is 6
int b = ++x; // x is 7, b is 7
```

### Assignment operators

```java
int score = 10;
score += 5;  // score = score + 5
score *= 2;  // score = score * 2
```

## 4. Strings and characters

`String` is a reference type representing text. String literals use double quotes; `char` literals use single quotes.

```java
String greeting = "Hello";
char firstLetter = 'H';
```

Strings are **immutable**: an operation that appears to change a string actually creates a new string.

```java
String text = "hello";
String upper = text.toUpperCase();
// text is still "hello"; upper is "HELLO"
```

Use `.equals()` to compare string contents, not `==`:

```java
String input = new String("yes");
if ("yes".equals(input)) { // null-safe
    System.out.println("Confirmed");
}
```

`+` concatenates when either operand is a string. After Java encounters a string in a chained `+` expression, the rest are concatenated left to right.

```java
System.out.println("Total: " + 2 + 3);   // Total: 23
System.out.println("Total: " + (2 + 3)); // Total: 5
```

Useful methods: `length()`, `charAt(index)`, `substring(...)`, `contains(...)`, `startsWith(...)`, `toLowerCase()`, `toUpperCase()`, `trim()` and `isBlank()`.

## 5. Decisions and boolean logic

Comparison operators: `==`, `!=`, `<`, `>`, `<=`, `>=`. Logical operators: `&&` (and), `||` (or), and `!` (not).

```java
if (age >= 18 && hasId) {
    System.out.println("Allowed");
} else if (age >= 18) {
    System.out.println("ID required");
} else {
    System.out.println("Not allowed");
}
```

### Short-circuit evaluation

`&&` stops as soon as the result is known false; `||` stops as soon as the result is known true. This makes safe null checks possible:

```java
if (user != null && user.isActive()) {
    // isActive() is called only when user is not null
}
```

Put more restrictive overlapping conditions before broader ones:

```java
if (score >= 90) {
    grade = 'A';
} else if (score >= 80) {
    grade = 'B';
}
```

### `switch`

Use `switch` to select among alternatives for one value. Modern switch expressions avoid accidental fall-through:

```java
String label = switch (day) {
    case "SATURDAY", "SUNDAY" -> "Weekend";
    case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "Weekday";
    default -> "Unknown";
};
```

In a traditional colon-style `switch`, omitting `break` lets execution continue into the next case (**fall-through**). Use it only deliberately.

```java
switch (level) {
    case 1:
        System.out.println("Beginner");
        break;
    default:
        System.out.println("Other");
}
```

| Use | Prefer |
|---|---|
| General conditions, ranges, combined boolean logic | `if` / `else if` |
| One value matched against fixed alternatives | `switch` |

## 6. Loops

| Loop | Best for |
|---|---|
| `for` | A known count or index-based iteration |
| enhanced `for` | Visiting every element of an array/collection |
| `while` | Repeat while a condition remains true; count may be unknown |
| `do ... while` | Body must run at least once |

```java
for (int i = 0; i < 3; i++) {
    System.out.println(i);
}

int attempts = 0;
while (attempts < 3) {
    attempts++;
}

do {
    System.out.println("Runs once");
} while (false);
```

`break` exits the nearest loop or switch. `continue` skips to the next loop iteration. Nested loops are loops inside loops:

```java
for (int row = 1; row <= 3; row++) {
    for (int column = 1; column <= 2; column++) {
        System.out.println(row + ", " + column);
    }
}
```

## 7. Arrays

An array is a fixed-size object holding elements of one type. Indexes begin at 0.

```java
int[] scores = {90, 75, 88};
scores[1] = 80;
System.out.println(scores.length); // 3

for (int score : scores) {
    System.out.println(score);
}
```

Accessing an invalid index causes `ArrayIndexOutOfBoundsException`. Arrays are mutable: changing an element changes the same array object. Array references can be reassigned to a different array.

```java
int[] values = new int[3]; // all elements begin as 0
```

## 8. Methods

Methods group reusable behavior. A method has an access modifier, optional `static`, return type, name, parameters, and body.

```java
static int square(int number) {
    return number * number;
}

static void greet(String name) {
    System.out.println("Hello, " + name);
}
```

`void` means no value is returned. A non-`void` method must return a compatible value on every possible path.

**Parameters** are variables in the method definition; **arguments** are the actual values supplied in a call:

```java
int result = square(5); // number is a parameter; 5 is an argument
```

Method overloading permits the same method name with different parameter lists:

```java
static int add(int a, int b) { return a + b; }
static double add(double a, double b) { return a + b; }
```

## 9. Scope, lifetime, and `final`

**Scope** answers “where can this name be used?” A local variable is available from its declaration to the end of its enclosing block (`{}`), and must be initialized before use.

```java
if (true) {
    int value = 10;
    System.out.println(value);
}
// System.out.println(value); // error: outside scope
```

Method parameters are in scope throughout the method body. Inner blocks can use names from outer blocks, but a local variable cannot redeclare an enclosing local variable. A parameter can shadow a field:

```java
class Person {
    int age;

    void setAge(int age) {
        this.age = age;
    }
}
```

**Lifetime** is how long a variable or object exists at runtime. A local variable exists for its method invocation; an object can outlive that call if it remains reachable.

`final` prevents reassignment of a variable. It does **not** make a referenced object immutable.

```java
final int limit = 10;
final int[] numbers = {1, 2};
numbers[0] = 99;           // allowed: mutation
// numbers = new int[2];   // error: reassignment
```

## 10. Classes and objects

A class is a blueprint; an object is an instance of that class. Fields hold an object's state, and methods define behavior.

```java
class BankAccount {
    private String owner;
    private double balance;

    BankAccount(String owner, double openingBalance) {
        this.owner = owner;
        this.balance = openingBalance;
    }

    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    double getBalance() {
        return balance;
    }
}

BankAccount account = new BankAccount("Asha", 100.0);
account.deposit(25.0);
```

`new` creates an object and invokes its constructor. `this` refers to the current object, commonly used to distinguish a field from a parameter.

### Access control

| Modifier | Accessible from |
|---|---|
| `public` | Anywhere |
| `protected` | Same package and subclasses |
| *(no modifier)* | Same package |
| `private` | The declaring class only |

Use `private` fields and expose behavior through methods when appropriate; this is encapsulation.

### Instance vs. static members

Instance members belong to each object. `static` members belong to the class itself and are shared.

```java
class Counter {
    static int created = 0;
    int value = 0;

    Counter() { created++; }
}
```

Access a static member through the class name: `Counter.created`.

### Inheritance, polymorphism, interfaces, and enums

**Inheritance** lets a child class reuse and specialize a parent class with `extends`. Use it for a genuine “is-a” relationship. A child can override an inherited instance method; `@Override` asks the compiler to verify that it really does.

```java
class Animal {
    void speak() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    @Override
    void speak() {
        System.out.println("Woof");
    }
}

Animal pet = new Dog();
pet.speak(); // Woof: runtime polymorphism
```

All classes ultimately inherit from `Object`. Java supports single class inheritance, but a class can implement multiple interfaces.

An **interface** names behavior a class agrees to provide. It is useful when unrelated classes share a capability.

```java
interface Printable {
    void print();
}

class Receipt implements Printable {
    @Override
    public void print() {
        System.out.println("Receipt");
    }
}
```

An **abstract class** cannot be instantiated directly and can combine shared state/implemented methods with abstract methods. Use one when closely related classes share implementation; use an interface when you primarily need a contract.

An **enum** is a fixed set of named constants and is safer than unrelated strings for fixed choices:

```java
enum Status { NEW, IN_PROGRESS, DONE }

Status status = Status.NEW;
```

## 11. References, equality, mutability, and null

Two references can point to one object (**aliasing**):

```java
Person p1 = new Person();
Person p2 = p1;
// p1 and p2 refer to the same Person object
```

### `==` versus `.equals()`

| Comparison | Primitives | References |
|---|---|---|
| `==` | Compares values | Compares whether both references point to the same object |
| `.equals()` | Not available | Usually compares logical contents; behavior depends on the class |

```java
String a = new String("java");
String b = new String("java");

System.out.println(a == b);      // false: different objects
System.out.println(a.equals(b)); // true: same characters
```

`null` means “no object reference.” Calling an instance method or accessing an instance field through `null` throws `NullPointerException`.

```java
Person person = null;
if (person != null) {
    person.getName();
}
```

| Mutable object | Immutable object |
|---|---|
| State can change after creation | State cannot change after creation |
| Examples: arrays, `StringBuilder`, most ordinary objects | Examples: `String`, wrapper objects such as `Integer` |
| A method can visibly change it through a shared reference | “Changes” produce another object/reference |

```java
String word = "Hello";
word = "World"; // reassignment, not mutation of a String

StringBuilder builder = new StringBuilder("Hello");
builder.append(" World"); // mutates the same object
```

## 12. Java is pass-by-value

Java always passes a **copy of a value** to a method. For a primitive, the copied value is the primitive. For an object variable, the copied value is its reference. Therefore a method can mutate the shared object but cannot reassign the caller's variable.

### Primitive-value example

```java
static void addOne(int number) {
    number++;
}

int score = 10;
addOne(score);
System.out.println(score); // 10
```

At the call, Java copies `10` into the parameter. Incrementing the parameter changes only that local copy:

```text
caller: score = 10       method: number = 10
                               ↓ number++
caller: score = 10       method: number = 11
```

### Reference-value example: mutation

```java
class Person { int age; }

static void haveBirthday(Person person) {
    person.age++;
}

Person ada = new Person();
ada.age = 20;
haveBirthday(ada);
System.out.println(ada.age); // 21
```

Java copies the **reference value**, so both variables can reach one shared object. It does not copy the object and it does not pass the caller's variable itself.

```text
Before call:
  caller frame                  heap
  ada ───────────────────────▶ Person { age: 20 }

During haveBirthday(ada):
  caller frame                  heap
  ada ──────────┐
                ├────────────▶ Person { age: 20 → 21 }
  method frame  │
  person ───────┘
```

### Reference-value example: reassignment

```java
static void replace(Person person) {
    person = new Person();
    person.age = 50;
}

Person ada = new Person();
ada.age = 20;
replace(ada);
System.out.println(ada.age); // 20
```

Reassignment changes only the parameter's copy of the reference:

```text
Before reassignment:             After person = new Person():
ada ──────┐                      ada ─────────────▶ Person A { age: 20 }
          ├──▶ Person A {20}
person ───┘                      person ──────────▶ Person B { age: 50 }
```

The same rule applies to arrays and strings. A method can alter an array element because the array is mutable; assigning the parameter to another array does not change the caller's variable. Strings are immutable, so operations such as `toUpperCase()` create another string and assigning it to a parameter cannot change the caller's reference.

```java
class Person { int age; }

static void celebrate(Person person) {
    person.age = 21; // changes the shared object
}

static void replace(Person person) {
    person = new Person(); // changes only the local parameter
    person.age = 50;
}

Person p = new Person();
p.age = 20;
celebrate(p);
System.out.println(p.age); // 21
replace(p);
System.out.println(p.age); // still 21
```

Arrays follow the same rule: a method can change `array[0]`, but assigning the parameter to a new array does not replace the caller's reference.

## 13. Memory model: stack, heap, and garbage collection

This is a useful learning model, not a full JVM specification.

| Area | Conceptual role |
|---|---|
| Stack | Method-call frames: parameters, local variables, return information; frames are removed when methods return |
| Heap | Objects and arrays created at runtime; generally live until unreachable |

```java
static Person createPerson() {
    Person p = new Person();
    return p;
}

Person person = createPerson();
```

While `createPerson` runs, its local `p` refers to the object. When it returns, that stack frame ends, but the object remains reachable through `person`.

An object becomes **eligible for garbage collection** when it is no longer reachable from live program references. Eligibility does not mean immediate deletion; the JVM controls the timing. Do not use garbage collection for program logic.

Unbounded recursion repeatedly creates stack frames and can cause `StackOverflowError`:

```java
static void recurseForever() {
    recurseForever();
}
```

## 14. Exceptions: basic handling

Exceptions represent unusual or failed operations. Code that may throw can be handled with `try`/`catch`.

```java
try {
    int value = Integer.parseInt("not a number");
} catch (NumberFormatException e) {
    System.out.println("Enter a whole number.");
}
```

Common beginner exceptions:

| Exception | Typical cause |
|---|---|
| `NullPointerException` | Using `null` as though it referred to an object |
| `ArrayIndexOutOfBoundsException` | Invalid array index |
| `ArithmeticException` | Integer division by zero |
| `NumberFormatException` | Invalid numeric text passed to parsing methods |

Use exceptions for exceptional situations, not normal branching. Read the exception message and stack trace to find the line that failed.

## 15. Useful standard-library fundamentals

### Wrapper classes and autoboxing

Every primitive has a wrapper class: `int` → `Integer`, `double` → `Double`, `boolean` → `Boolean`, and so on. Wrappers are useful where an object is required.

```java
Integer boxed = 42; // autoboxing
int value = boxed;  // unboxing
```

An `Integer` can be `null`; unboxing a `null` wrapper throws `NullPointerException`.

### `Math` and input

```java
int larger = Math.max(10, 20);
double root = Math.sqrt(81);
```

For simple console input, `Scanner` is approachable:

```java
import java.util.Scanner;

Scanner scanner = new Scanner(System.in);
System.out.print("Name: ");
String name = scanner.nextLine();
```

Avoid closing a `Scanner` that wraps `System.in` unless the program is ending, because closing it also closes standard input.

### Scanner gotcha: `nextInt()` + `nextLine()`

When you use `nextInt()` followed by `nextLine()`, `nextLine()` may appear to skip input:

```java
int age = scanner.nextInt();
String name = scanner.nextLine(); // skips input
```

`nextInt()` reads only the integer token and leaves the newline (`\n`) in the buffer. Then `nextLine()` immediately consumes that leftover newline and returns an empty string.

Think of the input as `25\n`. After `nextInt()`, the newline is still in the buffer. `nextLine()` sees it immediately and returns an empty string.

**Fix:** Consume the leftover newline before reading the next line:

```java
int age = scanner.nextInt();
scanner.nextLine(); // consume leftover newline
String name = scanner.nextLine(); // waits for actual input
```

**Alternative:** Read everything with `nextLine()` and parse as needed:

```java
int age = Integer.parseInt(scanner.nextLine());
double price = Double.parseDouble(scanner.nextLine());
```

This avoids the issue entirely because every read consumes a full line.

## 16. Basic time and space complexity

**Time complexity** describes how the amount of work grows as input size `n` grows. **Space complexity** describes how much additional memory an algorithm uses. Big-O notation describes an upper-bound growth rate; it ignores constants and lower-order terms.

| Complexity | Typical pattern | Example |
|---|---|---|
| `O(1)` | Fixed work | Read `array[index]` |
| `O(log n)` | Repeatedly halve search space | Binary search in sorted data |
| `O(n)` | Visit each item once | Find a value by scanning an array |
| `O(n log n)` | Efficient comparison sorting | Merge sort (conceptually) |
| `O(n²)` | Compare many pairs / nested full loops | Compare every item with every other item |

```java
// O(n) time, O(1) extra space
static int sum(int[] values) {
    int total = 0;
    for (int value : values) {
        total += value;
    }
    return total;
}

// O(n²) time: n iterations of the outer loop times n inner iterations
static void printPairs(int[] values) {
    for (int first : values) {
        for (int second : values) {
            System.out.println(first + ", " + second);
        }
    }
}
```

Sequential loops are added, not multiplied: two separate `O(n)` loops are `O(2n)`, simplified to `O(n)`. Nested loops multiply only when each runs broadly for each iteration of the other. Complexity is a guide to scalability, not a substitute for measuring a real program.

## 17. When to use what

A quick practical reference for where Java fundamentals appear in real backend development.

| Concept | When / Where to Use |
|---|---|
| Variables & Data Types | Represent IDs, quantities, status, flags, prices, dates, etc. |
| Primitive vs Reference Types | Choosing nullable/non-nullable values, memory-sensitive code, collections |
| `==` vs `.equals()` | `==` for primitives/identity; `.equals()` for value comparison |
| References | Working with objects, collections, entity relationships, caching |
| Pass-by-Value | Understanding method behavior and debugging unexpected object changes |
| Stack vs Heap | Debugging recursion, memory issues, `StackOverflowError`, `OutOfMemoryError` |
| Scope | Controlling where variables can be accessed; methods, loops, classes |
| Mutability vs Immutability | DTOs, value objects, configuration, thread-safe code |
| Methods | Business logic, service operations, utility functions, API operations |
| Recursion | Trees, nested structures, file systems, graphs, parsing |
| Exceptions | Handling failures, validation errors, business exceptions, database errors |
| `null` | Optional database/request values and handling missing data safely |
| Arrays | Binary data, fixed-size data, low-level processing, algorithms |
| Strings | User input, JSON, URLs, headers, JWTs, logs, messages |
| Collections | Storing and processing groups of objects |
| Big-O | Choosing data structures and avoiding performance problems as data grows |

## 18. Practical checklist

- Use `int` for ordinary whole numbers; use `long` when its range is needed.
- Use `double` for general decimal calculations and understand its precision limits.
- Prefer `.equals()` for comparing object contents; make it null-safe with a known non-null literal on the left.
- Check a nullable reference before dereferencing it.
- Use `final` for variables that should not be reassigned; remember it does not freeze an object.
- Keep variable scope as small as practical.
- Use parentheses and descriptive names to make intent obvious.
- Distinguish mutation of an object from reassignment of a reference.
- Remember: Java is always pass-by-value.
- Know the difference between fixed-size arrays and resizable collections.
- Estimate whether a loop is constant, linear, logarithmic, or quadratic before optimizing it.
- Read compiler errors carefully; they often identify the exact file, line, and type mismatch.
