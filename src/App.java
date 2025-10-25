import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final double PRECO_BOTIJAO = 110.00; // preco fixo do gas

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // ler a entrada
        List<Pedido> listaDePedidos = new ArrayList<>(); // armazena os pedidos

        int opcao = 1; // forçar a entrada no loop do menu

        while (opcao != 0) {
            exibirMenu(); // metodo para exibir o menu
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpando o buffer do scanner
            switch (opcao) {
                case 1:
                    fazerPedido(scanner, listaDePedidos);
                    break;
                case 2:
                    confirmarEntrega(scanner, listaDePedidos);
                    break;
                case 3:
                    consultarPedidosPorStatus("Confirmado", listaDePedidos);
                    break;
                case 4:
                    consultarPedidosPorStatus("Entregue", listaDePedidos);
                    break;
                case 0:
                    System.out.println("\nSaindo do sistema... Obrigado!");
                    break;
                default:
                    System.out.println("\nOpção inválida! Por favor, tente novamente.");
                    break;
            }
        }
        scanner.close(); // fechando o scanner
    }

    // metodos

    // metodo para exibir o menu
    public static void exibirMenu() {
        System.out.println("\n--- EMPRESA DE GÁS 24h ---");
        System.out.println("1. Fazer pedido");
        System.out.println("2. Confirmar entrega");
        System.out.println("3. Ver pedidos confirmados");
        System.out.println("4. Ver pedidos entregues");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // metodo antigo (para explicação) troquei para evitar repitição de codigo
    /*
     * private static void pedidosConfirmados(List<Pedido> lista) {
     * System.out.println("\n--- PEDIDOS CONFIRMADOS ---");
     * boolean encontrou = false;
     * for (Pedido pedido : lista) {
     * if (pedido.getStatus().equals("Entregue")) {
     * System.out.println(pedido.toString());
     * System.out.println("---------------------------");
     * encontrou = true;
     * }
     * }
     * if (!encontrou) {
     * System.out.println("Nenhum pedido confirmado encontrado.");
     * }
     * }
     */

    // versão atualizada e reutilizável do método acima
    private static void consultarPedidosPorStatus(String statusDesejado, List<Pedido> lista) {
        String titulo = statusDesejado.toUpperCase(); // deixa o título dinâmico
        System.out.println("\n--- PEDIDOS: " + titulo + "S ---");

        boolean encontrou = false;
        for (Pedido pedido : lista) { // iterando por for each
            if (pedido.getStatus().equals(statusDesejado)) { // aqui esta o parametro que usava antes porem agora como
                                                             // variavel (dinamico)
                System.out.println(pedido.toString());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pedido com o status '" + statusDesejado + "' foi encontrado.");
        }
    }

    // metodo para criar novo pedido
    // recebendo por parametro o scanner e a lista de pedidos
    private static void fazerPedido(Scanner scanner, List<Pedido> lista) {

        // coletando dados iniciais do pedido (Letra a)
        System.out.println("\n--- NOVO PEDIDO ---");

        System.out.print("Digite o endereço de entrega: ");
        String endereco = scanner.nextLine();
        
        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        Pedido novoPedido = new Pedido(endereco, nomeCliente); // criando o novo pedido com o nome do cliente

        // loop para confirmar ou alterar o endereço (Letra b)
        while (true) {

            System.out.println("\nConfirme o endereço do seu pedido:");
            System.out.println(novoPedido.toString()); // mostra os dados atuais do pedido
            System.out.print("\nDigite 'C' para confirmar ou 'A' para alterar o endereço: ");
            String confirmacao = scanner.nextLine().toUpperCase(); // pegando c ou a

            if (confirmacao.equals("A")) {
                System.out.print("Digite o novo endereço de entrega: ");
                String novoEndereco = scanner.nextLine();
                novoPedido.setEndereco(novoEndereco); // atualiza endereço
            } else {
                if (confirmacao.equals("C")) {
                    System.out.println("\nEndereço confirmado!");
                    // calculo da quantidade de botijões e da previsão de entrega (Letra c)
                    System.out.println("O preço unitário do botijão de gás é R$ " + String.format("%.2f", PRECO_BOTIJAO)); // numero formatado para float
                    System.out.print("Digite a quantidade de botijões que deseja: ");
                    int quantidade = scanner.nextInt();
                    scanner.nextLine(); // limpa o buffer.
                    novoPedido.calcularValor(quantidade, PRECO_BOTIJAO);
                    novoPedido.calcularPrevisaoDeEntrega();
                    // coletando dados de pagamento e marca como confirmado (Letra d)
                    System.out.print("\nDigite o número do cartão de crédito para pagamento: ");
                    String cartao = scanner.nextLine();
                    novoPedido.registrarPagamento(cartao);
                    // confimação final do pedido (Letra e)
                    novoPedido.confirmarPedido();
                    lista.add(novoPedido); // adiciona o pedido finalizado à lista principal
                    // exibindo o resumo final do pedido
                    System.out.println("\n---------------------------------");
                    System.out.println("PAGAMENTO APROVADO. PEDIDO CONFIRMADO!");
                    System.out.println("O CÓDIGO DO SEU PEDIDO É: #" + novoPedido.getIdPedido());
                    System.out.println("---------------------------------");
                    System.out.println(novoPedido.toString());
                    break;
                } else {
                    System.out.println("Opção inválida. Por favor, digite 'C' ou 'A'.");
                }
            }
        }
    }

    // metodo para confirmar entrega do pedido
    private static void confirmarEntrega(Scanner scanner, List<Pedido> lista) {
        System.out.println("\n--- CONFIRMAR ENTREGA ---");
        System.out.print("Digite o código do pedido que foi entregue: ");
        int idBuscado = scanner.nextInt();
        scanner.nextLine();

        boolean pedidoEncontrado = false;

        // percorrendo pedidos na lista
        for (Pedido pedido : lista) {
            // confirmando o id do pedido
            if (pedido.getIdPedido() == idBuscado) {
                pedido.marcarComoEntregue();
                System.out.println("Status do pedido #" + idBuscado + " alterado para ENTREGUE.");
                pedidoEncontrado = true; // flag ativada
                break;
            }
        }
        // se o pedido não for encontrado
        if (!pedidoEncontrado) {
            System.out.println("ERRO: Pedido com código #" + idBuscado + " não localizado.");
        }
    }
}