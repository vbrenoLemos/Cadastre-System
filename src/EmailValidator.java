public class EmailValidator extends Exception {
    static final String EMAIL_PATTERN = "^[\\w.+\\-]+@gmail\\.com$";
    public static void validateEmail(String email) throws EmailValidator {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new EmailValidator("Email is not valid");
        }
    }

    public EmailValidator(String message) {
        super(message);
    }

}