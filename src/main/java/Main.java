import dao.BoardDAO;
import dao.CardDAO;
import dao.ColumnDAO;
import java.util.List;
import java.util.Scanner;
import model.Board;
import model.BoardColumn;
import model.Card;

public class Main {
    public static void main(String[] args) {
        BoardDAO boardDAO = new BoardDAO();
        ColumnDAO columnDAO = new ColumnDAO();
        CardDAO cardDAO = new CardDAO();

        Scanner sc = new Scanner(System.in);
        int opc;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Criar novo board");
            System.out.println("2 - Selecionar board");
            System.out.println("3 - Excluir boards");
            System.out.println("4 - Sair");
            System.out.print("Escolha: ");
            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    System.out.print("Nome do board: ");
                    String nome = sc.nextLine();
                    boardDAO.createBoard(nome);
                    System.out.println("Board criado com sucesso!");
                    break;
                case 2:
                    List<Board> boards = boardDAO.listBoards();
                    if (boards.isEmpty()) {
                        System.out.println("Nenhum board encontrado.");
                        break;
                    }
                    boards.forEach(System.out::println);
                    System.out.print("ID do board: ");
                    int boardId = sc.nextInt();
                    sc.nextLine();

                    manipularBoard(boardId, columnDAO, cardDAO, sc);
                    break;
                case 3:
                    System.out.print("ID do board para excluir: ");
                    int delId = sc.nextInt();
                    sc.nextLine();
                    boardDAO.deleteBoard(delId);
                    System.out.println("Board excluído!");
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opc != 4);

        sc.close();
    }

    private static void manipularBoard(int boardId, ColumnDAO columnDAO, CardDAO cardDAO, Scanner sc) {
        int opc;
        do {
            System.out.println("\n=== MENU BOARD ===");
            System.out.println("1 - Criar card");
            System.out.println("2 - Mover card para próxima coluna");
            System.out.println("3 - Cancelar card");
            System.out.println("4 - Bloquear card");
            System.out.println("5 - Desbloquear card");
            System.out.println("6 - Listar cards por coluna");
            System.out.println("7 - Fechar board");
            System.out.print("Escolha: ");
            opc = sc.nextInt();
            sc.nextLine();

            List<BoardColumn> cols = columnDAO.listColumnsByBoard(boardId);
            BoardColumn inicial = cols.stream().filter(c -> c.getTipo().equals("INICIAL")).findFirst().orElse(null);
            BoardColumn cancelamento = cols.stream().filter(c -> c.getTipo().equals("CANCELAMENTO")).findFirst().orElse(null);

            switch (opc) {
                case 1:
                    if (inicial != null) {
                        System.out.print("Título do card: ");
                        String titulo = sc.nextLine();
                        System.out.print("Descrição: ");
                        String desc = sc.nextLine();
                        cardDAO.createCard(boardId, inicial.getId(), titulo, desc);
                        System.out.println("Card criado!");
                    }
                    break;
                case 2:
                    listarTodosCards(cols, cardDAO);
                    System.out.print("ID do card: ");
                    int moveId = sc.nextInt();
                    sc.nextLine();

                    Card card = cardDAO.getCardById(moveId);
                    if (card != null && !card.isBloqueado()) {
                        BoardColumn atual = cols.stream().filter(c -> c.getId() == card.getColunaId()).findFirst().orElse(null);
                        if (atual != null) {
                            BoardColumn proxima = cols.stream().filter(c -> c.getOrdem() == atual.getOrdem() + 1).findFirst().orElse(null);
                            if (proxima != null) {
                                cardDAO.moveCard(moveId, proxima.getId());
                                System.out.println("Card movido para: " + proxima.getNome());
                            }
                        }
                    } else {
                        System.out.println("Card bloqueado ou inválido.");
                    }
                    break;
                case 3:
                    listarTodosCards(cols, cardDAO);
                    System.out.print("ID do card para cancelar: ");
                    int cancelId = sc.nextInt();
                    sc.nextLine();
                    cardDAO.cancelCard(cancelId, cancelamento.getId());
                    System.out.println("Card movido para cancelamento.");
                    break;
                case 4:
                    listarTodosCards(cols, cardDAO);
                    System.out.print("ID do card para bloquear: ");
                    int blockId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Motivo do bloqueio: ");
                    String motivoB = sc.nextLine();
                    cardDAO.blockCard(blockId, motivoB);
                    System.out.println("Card bloqueado.");
                    break;
                case 5:
                    listarTodosCards(cols, cardDAO);
                    System.out.print("ID do card para desbloquear: ");
                    int unblockId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Motivo do desbloqueio: ");
                    String motivoD = sc.nextLine();
                    cardDAO.unblockCard(unblockId, motivoD);
                    System.out.println("Card desbloqueado.");
                    break;
                case 6:
                    listarTodosCards(cols, cardDAO);
                    break;
                case 7:
                    System.out.println("Fechando board...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opc != 7);
    }

    private static void listarTodosCards(List<BoardColumn> cols, CardDAO cardDAO) {
        for (BoardColumn col : cols) {
            System.out.println("\n--- " + col.getNome() + " ---");
            List<Card> cards = cardDAO.listCardsByColumn(col.getId());
            if (cards.isEmpty()) {
                System.out.println("Nenhum card.");
            } else {
                cards.forEach(System.out::println);
            }
        }
    }
}
