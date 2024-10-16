package Services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionService {
    protected static void NewQuestion() {
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

    protected static void PrintQuestions() {
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

    protected static void DeleteQuestion() {
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
            System.out.println("Pergunta deletada");

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

    private static int countLines(File file) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                count++;
            }
        }
        return count;
    }

}
