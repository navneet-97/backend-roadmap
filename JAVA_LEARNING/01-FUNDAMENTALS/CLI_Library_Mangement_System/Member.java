public class Member {
    String name;
    int age;
    private String role;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
        this.role = "user";
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
