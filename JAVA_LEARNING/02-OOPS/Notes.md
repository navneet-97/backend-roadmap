What is a class?
At the simplest level, it's a blueprint. It defines what an object is and what it can do.

What is an object?
An object is a particular instance of that class.

Example: 
```java
public class Human{
    int eyes;
    int ears;
    String skinColor;
    void walk();
}
```

```java
Human h1=new Human();
Human h2=new Human();
```
So Human is a blueprint(i.e. class) and h1, h2 are objects. They have the same structure bcoz they come from the same class. 
But their state can be different. 
Example: h1 and h2 can have different skin colors.

type vs instance:
```java
Human h=new Human();
             ↑
      ↑     object(instance)
  ↑   reference variable
 reference type
```

Why do classes exist?
Bcoz we don't want our program to be a giant collection of unrelated variables and functions.

State and behavior: eyes, ears r state and all the fns r behavior.
Why shouldn't everything be public?
Suppose:
```java
public class BankAccount {
    public BigDecimal balance;
}
```

Then anyone can do
```java
account.balance = new BigDecimal("-500000");
```
Our object can now be invalid. By making it private and write methods to control it will give the controls to object to change state.


Constuctors:
A constructor is a special method used to initialize an object.
OR
A constructor is the entry point through which an object is created and brought into a valid initial state.

```java
BankAccount account=new BankAccount("ACC001","user",5000);
```
new BankAccount(...)
       ↓
Java allocates memory for the object
       ↓
fields initially receive default values
       ↓
constructor executes
       ↓
constructor initializes the object
       ↓
reference returned
       ↓
account points to the object
```

`new` creates the object and the constructor initializes it.

Example:
```java
public class BankAccount {

    private String accountNumber;
    private String ownerName;
    private BigDecimal balance;

    public BankAccount(
            String accountNumber,
            String ownerName,
            BigDecimal balance) {

        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }
}
```

Then:
```java
BankAccount account =
        new BankAccount(
            "ACC001",
            "user",
            BigDecimal.valueOf(5000)
        );
```

`this` -> it points the current instance of the class(object)
In this, `this.accountNumber = accountNumber`
left side accountNumber is the field and right one is the parameter of the constructor. So it's besically used to get access of the fields outside.

What is Invariant:
It is a condition that should always be true for an object.
Example: for a bank account `balance>=0`

With code:
```java
public BankAccount(BigDecimal initialBalance) {

    if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException(
            "Initial balance cannot be negative"
        );
    }

    this.balance = initialBalance;
}

public void withdraw(BigDecimal amount) {

    if (amount.compareTo(balance) > 0) {
        throw new IllegalArgumentException(
            "Insufficient balance"
        );
    }

    balance = balance.subtract(amount);
}
```
Here, both creation and behavior protect the invariant.

Constructor Types:
1. No-Argument Constructor
```java
public BankAccount() {
}
```

If we don't write an constructor, Java provides a default no-argument constructor automatically.

Rule: Suppose you write:
```java
public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }
}
```

Now this won't work
```java
User user=new User();
```

Because once you defined a constructor yourself, Java doesn't automatically provide no-argument constructor. If we need we can write one.

Constructor Overloading:
You can multiple constructors
Example:
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
Then:
```java
User user1 = new User("user");

User user2 =
    new User("User", "user@example.com");
```
This is constructor overloading. Same constructor name, different parameter lists.

Rule: Don't abouse constructor overloading
```
User(String name)
User(String name, String email)
User(String name, String email, int age)
User(String name, String email, int age, String city)
User(String name, String email, int age, String city, String phone)
```

This becomes ugly. You now have a class with many ways to construct it, and it's difficult to know which combination represents a valid object.
This is called telescoping constructor problem. In larger applications, alternatives such as builders, factories can be better.

Constructor Chaining:
You can have one constructor call another. It avoids duplicating initialization logic.

Example:
```java
public class User {
    private String name;
    private String email;

    public User(String name){
        this(name,"user@example.com");
    }
    public User(String name, String email){
        this.name=name;
        this.email=email;
    }
}
```

`this()` vs `super()`

this() - calls another constructor in the same class.
super() - calls the constructor of parent class.

Constructor Restrictions:
A Constructor:
- has same name as class
- has no return type
- can be overloaded
- can have access modifiers
- isn't inherited
- can call another constructor using `this()`
- can call parent class constructor using `super()`

And `this(...)` or `super(...)` must be the first statement in the constructor.

We can make constructors private
Example:
```java
public class User{
    private User(){}
}
```

Now nobody outside the class can create its object. And why we do this bcoz sometimes we dont want arbitrary object creation. We make constructors private in util classes to prevent creation of meaningless instances.

Encapsulation:
Encapsulation means controlling access to an object's state and protecting the rules that govern that state.
Or
An object should control how it's state can be changed.

Example:
```java
public class BankAccount{
    int balance;
}
```

Anyone can do
```java
account.balance-=amount;
```

Now your object has invalid state. The class has no control over itself.
That's why encapsulation comes in picture. Make fields private and use getters/setters and ensure object's state never go invalid.

Example:
```java
public class BankAccount{
    private int balance;

    public void getBalance(){
        return this.balance;
    }

    public void setBalance(int amount){
        // If we don't validate, again object state can go invalid and that's not good encapsulation.
        if(amount<0){
            throw new IllegalArgumentException("Invalid amount");
        }
        if(balance<amount){
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance-=amount;
    }
}
```

Also, encapsulation hides implementation details. User does not need to know how he/she getting balance, we could have a balance field or we could be calculating it from list of transactions.

Encapsulation also helps in maintaining the invariant for a object.
Example:
```java
public void changePrice(BigDecimal price) {

    if (price == null || price.signum() <= 0) {
        throw new IllegalArgumentException(
            "Price must be greater than zero"
        );
    }

    this.price = price;
}
```
This will makes sure that price will never be negative. So it maintains the invariant for price. Now object protects itself.

Encapsulation vs Data hiding:
Data hiding - Don't allow direct access to internal implementation details.
Example:
```java
private BigDecimal balance;
```

Encapsulation - Is broader: Bundle state and behavior together behind a controlled interface, while protecting the object's invariants.
private fields
        +
controlled operations
        +
business rules
        +
hidden implementation
        =
strong encapsulation



Inheritance:
Inheritance allows one class to acquire properties and behavior from another class.

Example:
```java
class Animal{
    String name;
    public Animal(String name){
        this.name=name;
    }

    public void eat(){}
}
```
Then:
```java
class Dog extends Animal{
    public Dog(String name){
        super(name);
    }
    public void bark(){}
}
```
Usage:
```java
Dog dog=new Dog("name");
dog.eat();
dog.bark();
```
`Dog` inherits behavior from `Animal`.

HAS-A vs IS-A:

IS-A: usually represents with inheritance.
Dog IS-A Animal
Example:
```java
class Dog extends Animal{}
```

HAS-A: usually represents with composition.
Car HAS-A Engine
Composition generally gives you more flexibility. you can change the component without changing the class hierarchy.
Example:
```java
class Car{
    private Engine engine;
}
```
It doesn't mean Car class can't extend Engine class but it just don't make sense bcoz it has HAS-A relationship not IS-A.

When considering inheritance, the question should be is the child genuinely a specialized form of the parent? not can i reuse some code from the parent. we can use composition for that.
A child class can only inherit the accessed members from parent not everything.

super(name) - use to call the parent constructor.
First parent state initialized then Dog state initialized.
And super() should be the first statement always. As super class state should be initialized before child class state.
Why must the parent constructor execute? bcoz the child is also an instance of the parent type. When creating child class object the parent portion of the object needs to be initialized too.
The parent constructor runs first.
if the child constructor doesnt explicitly call super(...),Java implicitly inserts super().But this only works if the parent has an accessible no-argument constructor.

Suppose:
```java
class Animal {
    Animal(String name) {
    }
}
```
There is no no-argrument constructor.
Now:
```java
class Dog extends Animal {

    Dog() {
    }
}
```
This won't compile.Bcoz java tries to insert super() but Animal() doesnt exist. you must explicitly call.





Method overriding:
Suppose:
```java
class Animal{
    public void makeSound(){}
}
```

The dog wants different behavior:

```java
class Dog extends Animal{
    @Override
    public void makeSound(){
        sout();
    }
}
```
The child provides its own implementation of the inherited method.
While overriding the parent class method, child class method ensures that it must have a compatible return type and compatible access level(means we can't make the child class method more restrictive than parent's one)

By writing @Override, it tells the compiler we intend this method to override a parent method. Without it,Java might treat it as a completely new method.

`final` methods cannot be overriden.
`private` methods cannot be inherited as an accessible method by the subclass, so it cannot be overridden.

`static` methods arn't overridden in the normal polymorphic sense. They can be hidden.
A constructor cannot be inherited.
