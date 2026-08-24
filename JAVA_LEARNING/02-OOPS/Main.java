abstract class Animal {
    abstract void eat();
}


class Dog extends Animal{
}
public class Main{
    public static void main(String[] args) {
        Animal animal=new Dog();
        animal.makeSound();
    }
}