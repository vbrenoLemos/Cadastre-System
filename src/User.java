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

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public double getHeight() {
        return height;
    }

    private void setHeight(double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + "\n" + email + "\n" + age + "\n" + height;
    }
}
