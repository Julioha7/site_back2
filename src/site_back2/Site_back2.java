import model.*;
import java.util.Scanner;
import java.io.IOException;

public class Site_back2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PessoaFisicaRepo repoPF = new PessoaFisicaRepo();
        PessoaJuridicaRepo repoPJ = new PessoaJuridicaRepo();
        
        int opcao = -1;
        
        while (opcao != 0) {
            System.out.println("\n=================================");
            System.out.println("1 - Incluir");
            System.out.println("2 - Alterar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Exibir pelo ID");
            System.out.println("5 - Exibir Todos");
            System.out.println("6 - Salvar Dados");
            System.out.println("7 - Recuperar Dados");
            System.out.println("0 - Sair");
            System.out.println("=================================");
            System.out.print("Opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                if (opcao == 0) break;

                // Para opções que dependem do tipo (1 a 5)
                String tipo = "";
                if (opcao >= 1 && opcao <= 5) {
                    System.out.print("Tipo (F - Fisica, J - Juridica): ");
                    tipo = scanner.nextLine().toUpperCase();
                }

                switch (opcao) {
                    case 1: // Incluir
                        if (tipo.equals("F")) {
                            System.out.print("ID: "); int id = Integer.parseInt(scanner.nextLine());
                            System.out.print("Nome: "); String nome = scanner.nextLine();
                            System.out.print("CPF: "); String cpf = scanner.nextLine();
                            System.out.print("Idade: "); int idade = Integer.parseInt(scanner.nextLine());
                            repoPF.inserir(new PessoaFisica(id, nome, cpf, idade));
                        } else if (tipo.equals("J")) {
                            System.out.print("ID: "); int id = Integer.parseInt(scanner.nextLine());
                            System.out.print("Nome: "); String nome = scanner.nextLine();
                            System.out.print("CNPJ: "); String cnpj = scanner.nextLine();
                            repoPJ.inserir(new PessoaJuridica(id, nome, cnpj));
                        }
                        break;

                    case 2: // Alterar
                        System.out.print("ID do registro a alterar: ");
                        int idAlt = Integer.parseInt(scanner.nextLine());
                        if (tipo.equals("F")) {
                            PessoaFisica pf = repoPF.obter(idAlt);
                            if (pf != null) {
                                pf.exibir();
                                System.out.print("Novo Nome: "); pf.setNome(scanner.nextLine());
                                System.out.print("Novo CPF: "); pf.setCpf(scanner.nextLine());
                                System.out.print("Nova Idade: "); pf.setIdade(Integer.parseInt(scanner.nextLine()));
                                repoPF.alterar(pf);
                            } else { System.out.println("ID não encontrado."); }
                        } else {
                            PessoaJuridica pj = repoPJ.obter(idAlt);
                            if (pj != null) {
                                pj.exibir();
                                System.out.print("Novo Nome: "); pj.setNome(scanner.nextLine());
                                System.out.print("Novo CNPJ: "); pj.setCnpj(scanner.nextLine());
                                repoPJ.alterar(pj);
                            } else { System.out.println("ID não encontrado."); }
                        }
                        break;

                    case 3: // Excluir
                        System.out.print("ID do registro a excluir: ");
                        int idExc = Integer.parseInt(scanner.nextLine());
                        if (tipo.equals("F")) repoPF.excluir(idExc);
                        else repoPJ.excluir(idExc);
                        System.out.println("Exclusão realizada.");
                        break;

                    case 4: // Exibir pelo ID
                        System.out.print("ID a buscar: ");
                        int idBusca = Integer.parseInt(scanner.nextLine());
                        if (tipo.equals("F")) {
                            PessoaFisica pf = repoPF.obter(idBusca);
                            if (pf != null) pf.exibir(); else System.out.println("Não encontrado.");
                        } else {
                            PessoaJuridica pj = repoPJ.obter(idBusca);
                            if (pj != null) pj.exibir(); else System.out.println("Não encontrado.");
                        }
                        break;

                    case 5: // Exibir Todos
                        if (tipo.equals("F")) {
                            for (PessoaFisica p : repoPF.obterTodos()) { p.exibir(); System.out.println("---"); }
                        } else {
                            for (PessoaJuridica p : repoPJ.obterTodos()) { p.exibir(); System.out.println("---"); }
                        }
                        break;

                    case 6: // Salvar
                        System.out.print("Prefixo dos arquivos: ");
                        String prefixoS = scanner.nextLine();
                        repoPF.persistir(prefixoS + ".fisica.bin");
                        repoPJ.persistir(prefixoS + ".juridica.bin");
                        System.out.println("Dados salvos com sucesso.");
                        break;

                    case 7: // Recuperar
                        System.out.print("Prefixo dos arquivos: ");
                        String prefixoR = scanner.nextLine();
                        repoPF.recuperar(prefixoR + ".fisica.bin");
                        repoPJ.recuperar(prefixoR + ".juridica.bin");
                        System.out.println("Dados recuperados com sucesso.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro durante a operação: " + e.getMessage());
            }
        }
        System.out.println("Execução finalizada.");
    }
}