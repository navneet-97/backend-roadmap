import java.util.*;

public class Main {
    static final HashMap<String, Book> books = new HashMap<>();
    static final HashMap<Integer, String> input = new HashMap<>();
    static final HashMap<String, Member> members = new HashMap<>();
    static final HashMap<String, List<Book>> borrowedBooks = new HashMap<>();

    static final Scanner sc = new Scanner(System.in);

    public static void seedCommands() {
        input.put(1, "Add Book");
        input.put(2, "Remove Book");
        input.put(3, "Search Book");
        input.put(4, "Register Member");
        input.put(5, "Borrow Book");
        input.put(6, "Return Book");
        input.put(7, "Show Borrowed Books");
        input.put(8, "Show Available Books");
    }

    public static void seedBooks() {
        books.put("The Alchemist", new Book("The Alchemist", 399, "Paulo Coelho", 25));
        books.put("Atomic Habits", new Book("Atomic Habits", 599, "James Clear", 40));
        books.put("Clean Code", new Book("Clean Code", 799, "Robert C. Martin", 15));
        books.put("The Pragmatic Programmer", new Book("The Pragmatic Programmer", 899, "David Thomas", 20));
        books.put("Design Patterns", new Book("Design Patterns", 699, "Erich Gamma", 12));
        books.put("Effective Java", new Book("Design Patterns", 749, "Joshua Bloch", 18));
        books.put("Head First Java", new Book("Head First Java", 649, "Kathy Sierra", 30));
        books.put("The Hobbit", new Book("The Hobbit", 449, "J.R.R Tolkien", 22));
        books.put("Rich Dad Poor Dad", new Book("Rich Dad Poor Dad", 499, "Robert Kiyosaki", 35));
        books.put("Deep Work", new Book("Deep Work", 549, "Cal Newport", 28));
    }

    public static void seedUsers() {
        Member member = new Member("Navneet", 23);
        member.setRole("admin");

        members.put("Navneet", member);

    }

    private static String checkMemberExistOrNot() {
        System.out.print("Enter your name: ");
        sc.nextLine();
        String userName = sc.nextLine();

        if (members.containsKey(userName)) {
            return userName;
        } else {
            return "";
        }
    }

    private static boolean checkMemberRoleAdminOrNot() {
        String userName = checkMemberExistOrNot();
        if (userName.isEmpty()) {
            return false;
        }
        Member currentUser = members.get(userName);
        return currentUser.getRole().equals("admin");
    }

    public static String addBook() {
        if (!checkMemberRoleAdminOrNot()) {
            return "you do not have permission to add!";
        }

        System.out.print("Enter book name: ");
        String bookName = sc.nextLine();

        System.out.print("Enter the book auther: ");
        String autherName = sc.nextLine();

        System.out.print("Enter the book price: ");
        int price = sc.nextInt();

        System.out.print("Enter the quantity: ");
        int quantity = sc.nextInt();

        Book book = new Book(bookName, price, autherName, quantity);
        books.put(bookName, book);

        return "Your book is added";
    }

    public static String removeBook() {
        if (!checkMemberRoleAdminOrNot()) {
            return "you do not have permission to remove book!";
        }

        System.out.print("Enter book name: ");
        String bookName = sc.nextLine();

        while (!books.containsKey(bookName)) {
            System.out.print("No book found, Enter the correct name: ");
            bookName = sc.nextLine();
        }

        books.remove(bookName);
        return bookName + " is removed!";
    }

    public static String searchBook() {
        System.out.print("Enter book name: ");
        sc.nextLine();
        String bookName = sc.nextLine();

        if (books.containsKey(bookName.trim())) {
            Book book = books.get(bookName);
            return book.name + " by " + book.auther + ", " + "price: " + book.price;
        }

        return "No book found";
    }

    public static String registerMember() {
        System.out.print("Enter your name: ");
        sc.nextLine();
        String userName = sc.nextLine();

        while (members.containsKey(userName)) {
            System.out.print("User already exists, Enter new user name: ");
            userName = sc.nextLine();
        }

        System.out.print("Enter your age: ");
        int age = sc.nextInt();

        members.put(userName, new Member(userName, age));
        return "Welcome, " + userName;
    }

    public static String borrowBook() {
        String userName = checkMemberExistOrNot();
        if (userName.isEmpty()) {
            return "Please register first!";
        }

        System.out.print("Enter the book name: ");
        String bookName = sc.nextLine();

        if (books.containsKey(bookName)) {
            Book book = books.get(bookName);

            if (book.quantity > 0) {
                Book newBook = new Book(book.name, book.price, book.auther, book.quantity - 1);
                List<Book> list = borrowedBooks.getOrDefault(userName, new ArrayList<>());
                list.add(newBook);
                
                books.put(bookName, newBook);
            } else {
                return "Not available right now, try later!";
            }
        } else {
            return "Book not found";
        }
        return "Happy reading!";
    }

    public static String returnBook() {
        String userName = checkMemberExistOrNot();
        if (userName.isEmpty()) {
            return "Please register first!";
        }

        System.out.print("Enter the book name: ");
        String bookName = sc.nextLine();

        while (!books.containsKey(bookName)) {
            System.out.print("Book not found, Enter the correct name: ");
            bookName = sc.nextLine();
        }

        Book book = books.get(bookName);
        List<Book> borrowedBookList = borrowedBooks.get(userName);
        borrowedBookList.remove(book);
        books.put(bookName, new Book(book.name, book.price, book.auther, book.quantity + 1));

        return "Thanks for returning on time!";
    }

    public static String showBorrowedBooks() {
        if (!checkMemberRoleAdminOrNot()) {
            return "you have no permision to access borrowed books";
        }

        for (String user : borrowedBooks.keySet()) {
            List<Book> books = borrowedBooks.get(user);
            for (Book book : books) {
                System.out.println(book.name + " by " + book.auther + ": " + user);
            }
        }
        return "All borrowed books are listed above";
    }

    public static String showAvailableBooks() {
        for (Book book : books.values()) {
            if (book.quantity > 0) {
                System.out.println(book.name + " by " + book.auther + ", price: " + book.price);
            }
        }
        return "All the available books are listed above";
    }

    public static void main(String[] args) {
        seedCommands();
        seedBooks();
        seedUsers();

        System.out.println("Hi, welcome to our library!");
        System.out.print("Enter 0 to see our services: ");
        int commandNo = sc.nextInt();

        while (commandNo == 0) {
            System.out.println();
            for (int key : input.keySet()) {
                System.out.println("Enter " + key + " to " + input.get(key));
            }

            System.out.println();
            System.out.print("Enter number from 1-8 to use our respective service: ");
            int serviceCommand = sc.nextInt();

            System.out.println();
            while (1 <= serviceCommand && serviceCommand <= 8) {
                String result = switch (serviceCommand) {
                    case 1 -> addBook();
                    case 2 -> removeBook();
                    case 3 -> searchBook();
                    case 4 -> registerMember();
                    case 5 -> borrowBook();
                    case 6 -> returnBook();
                    case 7 -> showBorrowedBooks();
                    case 8 -> showAvailableBooks();
                    default -> "Not valid input";
                };
                System.out.println(result);
                System.out.println();
                System.out.print("Enter number from 1-8 to use our respective service: ");
                serviceCommand = sc.nextInt();
                System.out.println();
            }

            System.out.println();
            System.out.print("Enter 0 to see our services again: ");
            commandNo = sc.nextInt();
        }
    }
}
