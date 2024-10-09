import java.awt.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) throws IOException, ValuesException {
        File questions = new File("formulario.txt");
        boolean status = true;
        try (FileReader fr = new FileReader(questions)) {
            BufferedReader bufferedReader = new BufferedReader(fr);
            String line;
            int lineCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void Register() throws ValuesException {
        Scanner sc = new Scanner(System.in);
        File Directory = new File("Users");
        System.out.print("\n1- ");
        String name = sc.nextLine();
        System.out.print("2- ");
        String email = sc.nextLine();
        try{
            EmailValidator.validateEmail(email);
        }
        catch (EmailValidator e){
            throw new ValuesException("Invalid email address: " + e.getMessage());
        }
        System.out.print("3- ");
        int age;
        while (true) {
            try {
                age = sc.nextInt();
                if (age < 0 || age > 150) {
                    throw new ValuesException("Invalid Age.");
                }
                break;
            } catch (InputMismatchException e) {
                throw new ValuesException("Invalid Age. Must be a whole number.");
            }
        }
        System.out.print("4- ");
        double height;
        while (true) {
            try {
                height = sc.nextDouble();
                if (height < 0 || height > 3.0) {
                    throw new ValuesException("Invalid height.");
                }
                break;
            } catch (InputMismatchException e) {
                throw new ValuesException("Invalid height. Must be a decimal number.");
            }
        }

            User user = new User(name, email, age, height);
            File file = new File(Directory, name.toUpperCase().trim().replaceAll("\\s+", "") + ".txt");
            try (FileWriter fw = new FileWriter(file);) {
                BufferedWriter bf = new BufferedWriter(fw);
                bf.write(user.toString());
                bf.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private static int Menu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                1 - Cadastrar o usuário
                2 - Listar todos usuários cadastrados
                3 - Cadastrar nova pergunta no formulário
                4 - Deletar pergunta do formulário
                5 - Pesquisar usuário por nome ou idade ou email       
                """);
        System.out.println("\n Escolha sua ação");
        int option = sc.nextInt();
        return option;
    }

    private  static void  ListUsers(){
            File Directory = new File("Users");
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
}
