public class User {
    String name;
    String email;
    int age;
    double height;

    public User(String name, String email, int age, double height) {
        this.age = age;
        this.email = email;
        this.height = height;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "\n" + email + "\n" + age + "\n" + height;
    }
}
