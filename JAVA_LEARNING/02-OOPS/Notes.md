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
First parent state initialized then child state initialized.
And super() should be the first statement always. As super class state should be initialized before child class state.
Why must the parent constructor execute? bcoz the child is also an instance of the parent type. When creating child class object the parent portion of the object needs to be initialized too.
The parent constructor runs first.
if the child constructor doesnt explicitly call super(...), Java implicitly inserts super(). But this only works if the parent has an accessible no-argument constructor.

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
This won't compile. Bcoz java tries to insert super() but Animal() doesnt exist. you must explicitly call.

this() vs super():
this(...) - calls another constructor in the same class.
super(...) -  calls a constructor in the parent class.

Both must be the first statement. And you cannot do:
```java
Dog(){
    this("");
    super("");
}
```
Bcoz a constructor can have only one constructor chain leading upward. If this() calls another constructor in the same class, that constructor will eventually call super(). And we don't need 2 independent paths.

Using super(), we can also access parent members
Example:
```java
class Animal{
    public void eat(){}
}

class Dog extends Animal{

    @Override
    public void eat(){
        super.eat(); // call the parent implementation of eat().
        sout();
    }
}
```


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

By writing @Override, it tells the compiler we intend this method to override a parent method. Without it, Java might treat it as a completely new method.

`final` methods cannot be overriden.
`private` methods cannot be inherited as an accessible method by the subclass, so it cannot be overridden.

`static` methods arn't overridden in the normal polymorphic sense. They can be hidden.
A constructor cannot be inherited.


Inheritance leves:
Java supports multilevel inheritance.
Animal
   ↑
 Mammal
   ↑
  Dog


Every class ultimately extends `Object`
If you write:
```java
class Dog{}
```

Java treats it conceptually as:
```java
class Dog extends Object{}
```

That's why every objects has methods such as: toString(), equals(), ...

Java does not support multiple inheritance:
This is invalid:
```java
class Dog extends Animal, Pet{}
```

A java class can extend only one class.
Reason: Ambiguity/Diamond Problem
lets suppose a class inherits 2 classes which has same method, and we call that method using our child class. Now java do not know which method to call.

Example: 
```java
class A{
    public void show(){}
}

class B{
    public void show(){}
}
class C extends A, B{

}
```

Now:
```java
C obj=new C();
obj.show();
```
Which show() should execute?

Java uses interface for multiple inheritance.

Inheritance is not primarily for code reuse:
Even if inheritance lets use reuse code , the relationship should make sense.
THe child should satisfy the meaning of being a parent. If not, dont use inheritance just for reuse.

Upcasting:
```java
class Dog extends Animal{}
```
A dog can be treated as a animal.
So we can do this:
```java
Animal animal=new Dog();
```
Bcoz Dog IS-A Animal.


Inheritance is useful when:
- There is a genuine specialization relationship.
- The child should satisfy the parent type
- There is meaningful shared behavior/state
- You want runtime polymorphism


Polymorphism:
The same interface/reference can represent different concreate types, and the behavior can depend on the actual object.

Why do we need Polymorphism?
```java
class Dog{
    void makeSound(){}
}

class Cat{
    void makeSound(){}
}
```

We have 2 different classes with the same conceptual operation.
Without polymorphism, if we wanted to handle both:
```java
Dog dog=new Dog();
Cat cat=new Cat();

dog.makeSound();
cat.makeSound();
```
We have to know specifically that one is a `Dog` and one is a `Cat`.
Polymorphism let us work with them through a common type.

The Parent type:
```java
class Animal{
    void makeSound(){}
}

class Dog extends Animal{
    @Override
    void makeSound(){}
}

class Cat extends Animal{
    @Override
    void makeSound(){}
}
```
Now:
```java
Animal animal=new Dog();
Animal animal=new Cat();
```

We have a common type Animal(reference type) and the actual objects are Dog and Cat.


Consider:
```java
class Animal{
    void eat(){}
}

class Dog extends Animal{
    void bark(){}
}
```

Then:
```java
Animal animal=new Dog();
```

We can do animal.eat() but cant animal.bark() bcoz reference type is `Animal`. The compiler looks at the reference type to determine what methods are available to you.

With Overridden methods:
```java
class Animal{
    void makeSound(){}
}

class Dog extends Animal{
    @Override
    void makeSound(){}
}
```

Then:
```java
Animal animal=new Dog();
```

Now, animal.makeSound() executes the child method. Because Java uses the actual object type when deciding which overridden instance method implementation execute. This is runtime polymorphism.


Dynamic method Dispatch:
Consider:
```java
Animal animal;
animal=new Dog();
animal.makeSound(); // execute Dog's method

animal=new Cat();
animal.makeSound(); // execute Cat's method
```
The same code animal.makeSound() produces different behavior depending on the actual object. That's polymorphism.


```java
void makeAnimalSound(Animal animal){
    animal.makeSound();
}
```
code that expects an Animal should be able to work with a Dog.
Then:
```java
makeAnimalSound(new Dog());
makeAnimalSound(new Cat());
```

you don't need to check type:
```java
if(animal instanceof Dog){
    ...
}else if(animal instanceof Cat){
    ...
}
```

The object itself provides the appropriate behavior.

Compile-time polymorphism:
Compile-time polymorphism is generally associated with method overloading.
Example:

```java
class Calculator {
    int add(int a,int b){}
    int add(int a,int b,int c){}
    double add(double a,double b){}
}
```
Java determines which method to call bases on the arguments.
Why is overloading compile time? Bcoz the compiler can determine the method before the program runs.

Methods are overloaded when they have the same name but different parameter lists.
You cannot overload a method only by changing its return type.


Runtime Polymorphism:
Runtime polymorphism happens through method overriding.

Example:
```java
class Animal {

    void makeSound() {
        System.out.println("Animal");
    }
}

class Dog extends Animal {

    @Override
    void makeSound() {
        System.out.println("Dog");
    }
}
```

```java
Animal animal=new Dog();
animal.makeSound();
```

The actual implementation is determined at runtime.

Upcasting:
```java
Dog dog=new Dog();
Animal animal=dog;
```

You're converting a child reference into a parent reference. It's safe bcoz
Dog IS-A Animal.

Why would you upcast? Bcoz u often care about the general behavior, not the specific implementation.
For example:
```java
void makeSound(Animal animal) {
    animal.makeSound();
}
```
Then:

```java
makeSound(new Dog());
makeSound(new Cat());
```

The method doesn't need seperate versions, it works with the common parent type.

With Collection:

```java
List<Animal> animals = new ArrayList<>();

animals.add(new Dog());
animals.add(new Cat());
animals.add(new Dog());
```

Then:
```java
for (Animal animal : animals) {
    animal.makeSound();
}
```

This is polymorphism working naturally with collections. The list doesn't need to know every concrete subtype.

Downcasting:
```java
Dog dog=(Dog) animal;
dog.bark();
```

But it is not safe.
```java
Animal animal=new Cat();
Dog dog=(Dog)animal;
```

This compiles. But at runtime ClassCastException.

instanceof:
Before downcasting you can check:

```java
if(animal instanceof Dog dog){
    dog.bark();
}
```

Usage:
```java
List<String> list=new ArrayList<>();
```

you are using the reference type List but actual is ArrayList implementation.

Abstraction:
Abstraction means exposing the essential parts of something while hiding the implementation details.

Abstraction is about what you need to know:
Example:
```java
class PaymentService {

    public void pay(BigDecimal amount) {
        // complicated payment processing
    }
}
```

A caller only needs 
paymentService.pay(amount);
The caller doesnt need to know
validate payment
        ↓
create transaction
        ↓
call payment gateway
        ↓
handle response
        ↓
update database
        ↓
handle failure

Those details are hidden. So abstraction answers what should the outside world need to know in order to use this thing?

Abstraction is not simply hiding code. Good abstraction means the exposed operation represents a meaningful concept.

Why do we need abstraction?
Without abstraction, users of a component need to understand its internal implementation.
Imagine if every time you wanted to save a user you had to write:
```java
Connection connection = DriverManager.getConnection(...);

PreparedStatement statement =
    connection.prepareStatement(...);

statement.setString(...);

statement.executeUpdate();

connection.close();
```

Instead, you want something like:
```java
userRepository.save(user);
```

The repository provides an abstraction over the underlying persistance mechanism.

Abstraction at different levels:
Method level:
```java
sendEmail(user);
```

Class Level:
```java
paymentService.processPayment();
```
hides the internal payment workflow.

library level:
```java
List<String> names=new ArrayList<>();
```
You don't need to know how ArrayList manages its internal array.


Framework level:
```java
repository.save(user);
```
You don't need to manually write SQL for every operation.

Abstraction is all about exposing the right level of detail.

Abstraction and change:
One of the biggest benefits of abstraction is that it can protect users from implementation changes.
Suppose today:
PaymentService
     ↓
Stripe
Tomorrow:
PaymentService
     ↓
Razorpay
The caller ideally still does:
paymentService.processPayment(order);
The caller doesn't need to change because the implementation behind the abstraction changed.

Abstraction in APIs
Suppose your frontend calls:
POST /orders
The frontend doesn't need to know:
validate request
     ↓
check food availability
     ↓
create order
     ↓
calculate price
     ↓
save order
     ↓
send notification
It just knows:
POST /orders
The API abstracts away the internal implementation of the backend.

The caller thinks about:
WHAT I want
rather than:
HOW it is implemented
Abstraction is all about exposing the right level of detail.

Interfaces:
An interface defines a contract or capability that a class agrees to provide.

Why do interfaces exist?
Suppose we have:
```java
class Dog{
    void makeSound(){}
}
class Cat{
    void makeSound(){}
}
```

Both can make a sound, but there is no common contract saying anything that can be treated as an animal must provide makeSound().

An interface can define that contract:
```java
interface Animal{
    void makeSound();
}
```

Then:
```java
class Dog implements Animal{
    @Override
    public void makeSound(){
        // implementation
    }
}

class Cat implements Animal{
    @Override
    public void makeSound(){
        // implementation
    }
}
```

Now, interface says: Dog and Cat both provide the makeSound() capability.

class extends class
class implements interface
interface extends interface

A interface can't be initialized. So we cannot do this:
```java
Animal animal=new Animal();
```
Because an interface doesn't provide a concrete implementation for its abstract behavior.


Interface Methods:
Traditionally, an interface could contain abstract methods:
```java
interface Animal{
    void eat();
}
```
Then implementing class must provide them.

Interface methods are implicitly `public` by default.
So, 
```java
void eat();
```
is effectively:
```java
public abstract void eat();
```

When implementing the method, you cannot reduce its visibility.
It must be:
```java
public void eat(){
    //
}
```


Interface fields:
Interface can contain fields, but these are fundamentally different from normal instance fields.
so int value=3; This field is implicitly:
public static final int value=3;

So interface fields are constants.


Interfaces dont have instance state:
you cannot do
```java
interface User{
    String name;
}
```

and expect every implementing object to get an instance field name from the interface. Interface define behavior/constracts, not ordinary per-object state.


Multiple Inheritance:
Java normally does not allow multiple inheritance, but interfaces make it possible.

Example:
```java
interface Animal{
    void makeSound();
}

interface Pet{
    void eat();
}

class Dog implements Animal,Pet{
    @Override
    public void makeSound(){

    }

    @Override
    public void eat(){
        
    }
}
```

Why Java allow multiple interfaces?
Because interfaces represent contract/capabilities. So when 2 interfaces will have same method, and implementing class have to provide the implementation so there wont be problem of choosing which method to call at runtime.

Interfaces can extend interfaces.
Example:
```java
interface Animal{
    void makeSound();
}

interface Mammal extends Animal{
    void eat();
}

class Dog implements Mammal{
    @Override
    public void eat(){}

    @Override
    public void makeSound(){}
}
```

Default Methods:
Example:
```java
interface Animal{
    void makeSound();

    default void sleep(){
        // implementation
    }
}

class Dog implements Animal{
    @Override
    public void makeSound(){
        // implementation
    }
}
```

default methods introduced bcoz of interface evolution.
Imagine an existing interface, many classes implement it. Now you want to add another abstract method, every implementation must immediately implement it. That could break existing implementations.
A default method can provide a default implementation instead.
This allows interfaces to evolve more safely.

Default methods can be overridden
```java
interface Animal{
    void makeSound();

    default void sleep(){
        // implementation
    }
}

class Dog implements Animal{
    @Override
    public void makeSound(){
        // implementation
    }

    @Override
    public void sleep(){
        // new implementation
    }
}
```

Static Methods in interfaces:
Interfaces can also have static methods.
```java
interface Animal{
    static int age(){
        return 23;
    }
}
```
And You call it through the interface itself:
```java
Animal.age();
```

Static interface methods belong to the interface, not implementing objects.

Private methods in interfaces
Example:
```java
interface NotificationSender {

    default void sendNotification() {
        validate();
        // sending logic
    }

    private void validate() {
        // shared internal logic
    }
}
```

The private methods exists to support other methods inside the interface. Implementing classes cannot directly call it.

An interface cannot have a constructor.
Because interface cannot instantiated directly. They dont represent a concrete object that needs constructor initilization.

Interface doesn't guarantee good behavior
Suppose:
```java
interface PaymentGateway{
    void charge();
}
```

A class could technically implement it badly:
```java
class BadGateway implements PaymentGateway{
    @Override
    public void charge(){
        // does nothing
    }
}
```

Java only checks that the method exists with the correct signature.
The interface doesn't automatically guarantee that the implementation obeys the semantic meaning of the contract.

Interface vs inheritance:
Class Inheritance:
```java
class Dog extends Animal
```
represents Dog is a specialized Animal.

Interface Implementation:
```java
class StripeGateway implements PaymentGateway
```
represents StripeGateway statisfies the PaymentGateway contract.

Functional Interface:
An interface which has exactly one abstract method.
```java
@FunctionalInterface
interface Calculator{
    int add(int a,int b);
}
```

Then you can use a lambda:
```java
Calculator add=(a,b)->a+b;
```


Abstract Classes:
An abstract class is a class that is intended to be used as a base class and cannot be instantiated directly.
Example:
```java
abstract class Animal{
    abstract void makeSound();
}
```
you cannot do:
```java
Animal animal=new Animal();
```

But you can create a subclass, then `Animal animal=new Dog();`

Why do abstract classes exist?
Suppose several classes share some common characteristics and behavior.
You could put common behavior in abstract class.

Example:
```java
abstract class Animal {

    protected String name;

    void eat() {
        System.out.println("Eating");
    }

    abstract void makeSound();
}
```
The abstract class provides common implementation while also forcing subclasses to provide behavior that is specific to them.

Abstract class can contain abstract methods. An abstract method has no implementation.
Abstract class can also contain concrete methods.
Abstract class can contain fields. So abstract class can contain state.
Abstract class can have contructors. You cannot instantiate an abstract class but an abstract class can have a constructor.
Example:
```java
abstract class Animal {
    protected String name;
    Animal(String name){
        this.name=name;
    }
    abstract void makeSound();
}

class Dog extends Animal{
    Dog(String name){
        super(name);
    }
    @Override
    public void makeSound(){
        // implementation
    }
}
```

Why does an abstract class need a constructor? Because although you don't create the abstract class directly, its part of the subclass object still needs initilization.
```java
Dog dog=new Dog("");
```
This object contains the `Animal` portion as well. That's why.

An abstract class does not have to contain an abstract method.
Example:
```java
abstract class Animal {
    public void makeSound(){
        // implementation
    }
}
```

There are no abstract methods here.
Yet the class is still abstract. you may want to prevent direct instantiation while still providing shared implementation.

A concrete subclass must implement abstract methods.    
Abstract class can have access modifiers.
Abstract methods cannot be private. Bcoz an abstract method must be overridden by a subclass.

Abstract methods cannot be final. Bcoz an abstract method must be overridden by a subclass. If we make it final or private subclass won't be able to override it. So they contradict each other.

Abstract methods cannot be static. Static methods belong to the class rather than an individual object and aren't dynamically overridden in the normal polymorphic sense.
Abstract methods require subclass implementation through overriding.

| Abstract Class | Interface |
|---|---|
| Declared with `abstract class` | Declared with `interface` |
| Class uses `extends` | Class uses `implements` |
| Can have instance fields | No normal instance fields |
| Can have constructors | No constructors |
| Can have concrete methods | Can have default/static/private methods |
| Can have abstract methods | Can have abstract methods |
| Can have access modifiers on members | Interface contract members have specific rules |
| A class can extend only one class | A class can implement multiple interfaces |
| Represents a base class/type hierarchy | Represents a contract/capability |


```java
class Bird extends Animal implements Flyable
```
Now Bird IS-A Animal and has capability of Flyable.

Abstract class can provide partial implementation
```java
abstract class ReportGenerator {

    public final void generate() {
        fetchData();
        formatData();
        export();
    }

    protected abstract void fetchData();

    protected abstract void formatData();

    protected abstract void export();
}
```
The abstract class can define common structure while requiring subclasses to fill in specific steps. This is a pattern called the Template Method pattern.

Composition vs Inheritance:
Inheritance represents an: IS-A relationship

Composition represents a: HAS-A relationship

Inheritance allows the child to inherit accessible behavior/state from the parent.

Composition:
```java
class Engine{
    void start(){}
}

class Car {
    private Engine engine;
    Car(){
        this.engine=new Engine();
    }
}
```

Use composition when one object needs another object to perform part of its job.

When deciding between inheritance and composition, ask:
Is the child genuinely a specialized form of the parent, or does it simply need the parent's functionality?


Inheritance is tightly coupled. The child is dependent on the parent implementation. Slight change in parent can have consequences for subclasses. While composition is less coupled.

Prefer composition when both approaches could work bcoz composition often gives you more flexibility and less coupling. 
With composition you can choose what functionality an object contains.
Example:
```java
class OrderService {

    private PaymentGateway paymentGateway;
}
```
you can control what object u will provide.

Association:
Association means that 2 classes have a relationship with each other.

Example:
```java
class Teacher{}
class Student{}
```

If a teacher teaches a Student. That's a association.
The relationship can exist through:
- fields
- method parameters
- return values
- local variables
- other interactions

Association is a general relationship. Every composition is an association, but not every association is composition.

Association can be bidirectional and unidirectional both.
Example:
```java
class Customer{
    List<Order> orders;
}

class Order{
    private Customer customer;
}
```

```java
class Order{
    private Customer customer;
}

class Customer{

}
```

one-to-one:
One person has one passport.
```java
class Person{
    private Passport passport;
}
```

one-to-many:
One customer can have many orders.
```java
class Customer{
    private List<Order> orders;
}
```

many-to-one:
Each order belongs to one customer.
```java
class Order{
    private Customer customer;
}
```

many-to-many:
Student ───── Course
```java
class Student{
    List<Course> courses;
}
class Course{
    List<Student> students;
}
```

Association does not imply ownership
```java
class Doctor {

    private List<Patient> patients;
}
```
The doctor is associated with patients. Patients exist independently. If Doctor object gets deleted patients still exist.


Association vs Composition
```java
class Doctor {

    private Patient patient;
}
```
The doctor interacts with a patient. The patient exists independently.

```java
class House {

    private Room room;
}
```
If the Room is conceptually a component whose lifecycle belongs to that particular House, So Room doesn't exist without House.


