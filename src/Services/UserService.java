package Services;

import Domain.User;
import Exceptions.EmailValidator;
import Exceptions.ValuesException;
import Validate.EmailValidate;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserService  extends EmailValidate {
    protected static void Register() throws ValuesException {
        File questions = new File("formulario.txt");
        try (FileReader fr = new FileReader(questions)) {
            BufferedReader bufferedReader = new BufferedReader(fr);
            String line;
            int lineCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                lineCount++;
            }
        } catch (IOException e) {

        }
        Scanner sc = new Scanner(System.in);
        File Directory = new File("src/Users");
        String name = sc.nextLine();
        String email = sc.nextLine();
        if (emailExists(email)) {
            System.out.println("E-mail já existe");
            return;
        }
        try {
            EmailValidator.validateEmail(email);
        } catch (EmailValidator e) {
            throw new ValuesException("Invalid email address: " + e.getMessage());
        }
        int age;
        while (true) {
            try {
                age = sc.nextInt();
                sc.nextLine();
                if (age < 0 || age > 150) {
                    throw new ValuesException("Invalid Age.");
                }
                break;
            } catch (InputMismatchException e) {
                sc.nextLine();
                throw new ValuesException("Invalid Age. Must be a whole number." + e.getMessage());
            }
        }
        double height;
        while (true) {
            try {
                height = sc.nextDouble();
                sc.nextLine();
                if (height < 0 || height > 3.0) {
                    throw new ValuesException("Invalid height.");
                }
                break;
            } catch (InputMismatchException e) {
                sc.nextLine();
                throw new ValuesException("Invalid height. Must be a decimal number." + e.getMessage());
            }
        }
        List<String> answers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(questions))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= 5) {
                    answers.add(sc.nextLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = new User(name, email, age, height, answers);
        SaveUserInfo(user);
        File file = new File(Directory, name.toUpperCase().trim().replaceAll("\\s+", "") + ".txt");
        try (FileWriter fw = new FileWriter(file);) {
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write(user.toString());
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void SaveUserInfo(User user) {
        File file = new File("src/Users", user.getName().toUpperCase().trim().replaceAll("\\s+", "") + ".txt");
        try (FileWriter fw = new FileWriter(file);) {
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write(user.toString());
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void ListUsers() {
        File Directory = new File("src/Users");
        File[] files = Directory.listFiles((file) -> file.isFile() && file.getName().endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("Nenhum usuário registrado.");
            return;
        }

        System.out.println("Usuários registrados:");
        for (File file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                System.out.println(line);
            } catch (IOException e) {
                System.err.println("Erro ao ler arquivo: " + file.getName());
            }
        }
    }

    protected static void SearchUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o nome, idade ou e-mail do usuário que você deseja pesquisar: ");
        String search = sc.nextLine().toLowerCase();

        File Directory = new File("src/Users");
        File[] files = Directory.listFiles((file) -> file.isFile() && file.getName().endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("Nenhum usuário registrado.");
            return;
        }

        boolean found = false;
        for (File file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String name = br.readLine().trim().toLowerCase();
                String email = br.readLine().trim().toLowerCase();
                String age = br.readLine().trim();
                String height = br.readLine().trim();

                if (name.contains(search) || email.contains(search) || age.contains(search) || height.contains(search)) {

                    System.out.println("Nome: " + name);
                    System.out.println("E-mail: " + email);
                    System.out.println("Idade: " + age);
                    System.out.println("Altura: " + height);
                    System.out.println();
                    found = true;
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler arquivo: " + file.getName());
            }
        }

        if (!found) {
            System.out.println("Usuário não encontrado.");
        }
    }

}