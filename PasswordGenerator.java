import java.util.*;

public class PasswordGenerator {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_-+=<>?";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User Input and Preferences
        int passwordLength = getPasswordLength(scanner);
        String characterTypes = getCharacterTypes(scanner);

        // Generating a Random Password
        String generatedPassword = generatePassword(passwordLength, characterTypes);

        // Password Strength
        String passwordStrength = getPasswordStrength(generatedPassword);

        // Display or Copy Password
        System.out.println("Generated Password: " + generatedPassword);
        System.out.println("Password Strength: " + passwordStrength);

        scanner.close();
    }

    private static int getPasswordLength(Scanner scanner) {
        int length = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter the desired password length: ");
            String input = scanner.nextLine();

            try {
                length = Integer.parseInt(input);
                if (length >= 8) {
                    validInput = true;
                } else {
                    System.out.println("Password length should be at least 8 characters.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return length;
    }

    private static String getCharacterTypes(Scanner scanner) {
        System.out.println("Select the character types to include in the password:");
        System.out.println("1. Uppercase letters");
        System.out.println("2. Lowercase letters");
        System.out.println("3. Numbers");
        System.out.println("4. Symbols");
        System.out.println("Enter the corresponding numbers separated by commas (e.g., 1,3): ");

        String input = scanner.nextLine();

        Set<Character> characterSet = new HashSet<>();
        String[] choices = input.split(",");

        for (String choice : choices) {
            switch (choice.trim()) {
                case "1":
                    addCharactersToSet(characterSet, UPPERCASE_LETTERS);
                    break;
                case "2":
                    addCharactersToSet(characterSet, LOWERCASE_LETTERS);
                    break;
                case "3":
                    addCharactersToSet(characterSet, NUMBERS);
                    break;
                case "4":
                    addCharactersToSet(characterSet, SYMBOLS);
                    break;
                default:
                    System.out.println("Invalid choice: " + choice);
                    break;
            }
        }

        StringBuilder characterTypes = new StringBuilder();
        for (char c : characterSet) {
            characterTypes.append(c);
        }

        return characterTypes.toString();
    }

    private static void addCharactersToSet(Set<Character> characterSet, String characters) {
        for (char c : characters.toCharArray()) {
            characterSet.add(c);
        }
    }

    private static String generatePassword(int length, String characterTypes) {
        StringBuilder password = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characterTypes.length());
            password.append(characterTypes.charAt(randomIndex));
        }

        return password.toString();
    }

    private static String getPasswordStrength(String password) {
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;
        boolean hasSymbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else {
                hasSymbol = true;
            }
        }

        if (password.length() >= 12 && hasUppercase && hasLowercase && hasNumber && hasSymbol) {
            return "Strong";
        } else if (password.length() >= 8 && (hasUppercase || hasLowercase || hasNumber || hasSymbol)) {
            return "Medium";
        } else {
            return "Weak";
        }
    }
}
