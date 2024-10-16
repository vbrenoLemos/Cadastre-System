package Validate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EmailValidate {
    protected static boolean emailExists(String email) {
        File Directory = new File("Users");
        File[] files = Directory.listFiles((file) -> file.isFile() && file.getName().endsWith(".txt"));

        if (files == null || files.length == 0) {
            return false;
        }

        for (File file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("E-mail: ")) {
                        String fileEmail = line.substring(7).trim().toLowerCase();
                        if (fileEmail.equals(email.toLowerCase())) {
                            return true;
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler arquivo: " + file.getName());
            }
        }

        return false;
    }
}
