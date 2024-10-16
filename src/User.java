import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private int age;
    private double height;
    private List<String> answers;

    public User(String name, String email, int age, double height, List<String> answers) {
        this.age = age;
        this.email = email;
        this.height = height;
        this.name = name;
        this.answers = answers;
    }

    public int getAge() {
        return age;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getEmail() {
        return email;
    }

    public double getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append(email).append("\n");
        sb.append(age).append("\n");
        sb.append(height).append("\n");

        if (answers != null && !answers.isEmpty()) {
            for (String answer : answers) {
                sb.append(answer).append("\n");
            }
        }
        return sb.toString();
    }
}
