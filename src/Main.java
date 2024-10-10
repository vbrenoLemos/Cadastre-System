import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ValuesException {
        Menu();
    }


    private static void Menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                1 - Cadastrar o usuário
                2 - Listar todos usuários cadastrados
                3 - Cadastrar nova pergunta no formulário
                4 - Deletar pergunta do formulário
                5 - Pesquisar usuário por nome ou idade ou email""");
        System.out.print("\n Escolha sua ação: ");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                try {
                    Register();
                    break;
                } catch (ValuesException e) {
                    throw new RuntimeException(e);
                }
            case 2:
                ListUsers();
                break;
            case 3:
                NewQuestion();
                PrintQuestions();
                break;
            case 4:
                DeleteQuestion();
                break;
            case 5:
                SearchUser();
            default:
        }
    }

    private static void Register() throws ValuesException {
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
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        File Directory = new File("Users");
        String name = sc.nextLine();
        String email = sc.nextLine();
        if(emailExists(email)) {
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
                if (age < 0 || age > 150) {
                    throw new ValuesException("Invalid Age.");
                }
                break;
            } catch (InputMismatchException e) {
                throw new ValuesException("Invalid Age. Must be a whole number." + e.getMessage());
            }
        }
        double height;
        while (true) {
            try {
                height = sc.nextDouble();
                if (height < 0 || height > 3.0) {
                    throw new ValuesException("Invalid height.");
                }
                break;
            } catch (InputMismatchException e) {
                throw new ValuesException("Invalid height. Must be a decimal number." + e.getMessage());
            }
        }


        User user = new User(name, email, age, height);
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

    private static void SaveUserInfo(User user) {
        File file = new File("Users", user.getName().toUpperCase().trim().replaceAll("\\s+", "") + ".txt");
        try (FileWriter fw = new FileWriter(file);) {
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write(user.toString());
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ListUsers() {
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

    private static void NewQuestion() {
        File file = new File("formulario.txt");
        Scanner sc = new Scanner(System.in);
        System.out.print("Quantas perguntas você quer adicionar ? ");
        int num = sc.nextInt(); // número de perguntas
        sc.nextLine();
        try (FileWriter fw = new FileWriter(file, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            int lines = countLines(file);
            for (int i = 1; i <= num; i++) {
                System.out.print("Digite sua pergunta: ");
                String question = sc.nextLine();
                bw.newLine();
                bw.write((i + lines) + " - " + question);
                bw.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void PrintQuestions() {
        File file = new File("formulario.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int countLines(File file) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                count++;
            }
        }
        return count;
    }

    private static void DeleteQuestion() {
        File file = new File("formulario.txt");
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o número da pergunta que você deseja deletar: ");
        int questionNumber = sc.nextInt();

        if (questionNumber < 5) {
            System.out.println("Não é possível apagar as 4 primeiras perguntas.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            if (lines.size() < questionNumber) {
                System.out.println("Pergunta não encontrada.");
                return;
            }

            lines.remove(questionNumber - 1); // remover a pergunta da lista

            // Renumerar as perguntas subsequentes
            for (int i = questionNumber - 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(" - ");
                if (parts.length > 1) {
                    int newNumber = Integer.parseInt(parts[0]) - 1;
                    lines.set(i, newNumber + " - " + parts[1]);
                }
            }

            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                for (String lineToRemove : lines) {
                    pw.println(lineToRemove);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void SearchUser () {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o nome, idade ou e-mail do usuário que você deseja pesquisar: ");
        String search = sc.nextLine().toLowerCase();

        File Directory = new File("Users");
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

    private static boolean emailExists(String email) {
        File Directory = new File("Users");
        File[] files = Directory.listFiles((file) -> file.isFile() && file.getName().endsWith(".txt"));

        if (files == null || files.length == 0) {
            return false;
        }

        for (File file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.readLine(); // skip the first line (name)
                String fileEmail = br.readLine().trim().toLowerCase();
                if (fileEmail.equals(email.toLowerCase())) {
                    return true;
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler arquivo: " + file.getName());
            }
        }

        return false;
    }
}
