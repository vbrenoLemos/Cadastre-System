package Services;

import Exceptions.ValuesException;

import java.util.Scanner;

public class Menu {
   protected static void Menu() {
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
                    UserService.Register();
                    break;
                } catch (ValuesException e) {
                    throw new RuntimeException(e);
                }
            case 2:
                UserService.ListUsers();
                break;
            case 3:
                QuestionService.NewQuestion();
                break;
            case 4:
                QuestionService.DeleteQuestion();
                break;
            case 5:
                UserService.SearchUser();
            default:
        }
    }


}
