import java.util.ArrayList;

public class Main {
    static final ArrayList<Book> books = new ArrayList<>();

    public static void addBook(String name, int price, String auther, int quantity) {
        Book book = new Book(name, price, auther, quantity);
        books.add(book);
    }

    public static void removeBook(String name) {
        books.removeIf(book -> book.name.equals(name));
    }

    public static void showAllBooks() {
        books.forEach(book -> System.out.println(book.name + " by " + book.auther));
    }

    public static void main(String[] args) {
        addBook("book1", 100, "auther1", 5);
        addBook("book2", 400, "auther2", 10);

        removeBook("book1");
    }
}