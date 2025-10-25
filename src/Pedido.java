import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {

    private static int proximoId = 1; // gerando id sequencial automaticamente (atributo que vai ser somaddo a cada novo pedido)
    private final int idPedido; // 'final' pois o ID não deve mudar após a criação
    private String status;
    private String nomeCliente;
    private String endereco;
    private final LocalDateTime dataHoraCompra; // final pq a data/hora de compra não muda depois de criada
    private LocalDateTime dataHoraEntrega;
    private int quantidadeBotijoes;
    private double valorTotal;
    private String numeroCartao;

    // construtor
    public Pedido(String endereco, String nomeCliente) {
        this.idPedido = proximoId++;
        this.dataHoraCompra = LocalDateTime.now();
        this.status = "Em Aberto";
        this.endereco = endereco;
        this.nomeCliente = nomeCliente;
    }

    // metodos

    // marca o pedido como entregue
    public void marcarComoEntregue() {
        this.status = "Entregue";
    }

    // confirma o pedido
    public void confirmarPedido() {
        this.status = "Confirmado";
    }

    // calculando o valor total do pedido
    public void calcularValor(int quantidade, double precoUnitario) {
        this.quantidadeBotijoes = quantidade;
        this.valorTotal = quantidade * precoUnitario;
    }

    // calculando a previsão de entrega de duas horas após a compra
    public void calcularPrevisaoDeEntrega() {
        this.dataHoraEntrega = this.dataHoraCompra.plusHours(2);
    }

    public void registrarPagamento(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    // getters e setters

    public int getIdPedido() {
        return idPedido;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getStatus() {
        return status;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    // metodo privado pq so uso aqui dentro da classe Pedido
    // retorno o numeo do cartao mascarado com asteriscos
    private String getNumeroCartaoMascarado() {
        if (this.numeroCartao != null && this.numeroCartao.length() > 4) {
            return "**** **** **** " + this.numeroCartao.substring(this.numeroCartao.length() - 4);
        }
        return "Não informado";
    }

    // formata a data da compra para um formato legivel
    public String getDataHoraCompraFormatada() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.dataHoraCompra.format(formatador);
    }

    // formata a data de entrega para um formato legivel
    public String getDataHoraEntregaFormatada() {
        if (this.dataHoraEntrega == null) {
            return "Aguardando cálculo";
        }
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.dataHoraEntrega.format(formatador);
    }

    // metodos sobrescritos
    @Override
    public String toString() {
        // bloco base do resumo do pedido sempre exibido
        String detalhes = "---------------------------------\n" +
                "DADOS DO PEDIDO #" + idPedido + "\n" +
                "---------------------------------\n" +
                "Nome: " + nomeCliente + "\n" +
                "Endereço: " + endereco + "\n" +
                "Data da Compra: " + getDataHoraCompraFormatada() + "\n" +
                "Status: " + status;

        // informações adicionais exibidas apenas se o pedido foi confirmado
        if (quantidadeBotijoes > 0) {
            detalhes += "\nQuantidade: " + quantidadeBotijoes +
                    "\nValor Total: R$ " + String.format("%.2f", valorTotal) +
                    "\nPrevisão de Entrega: " + getDataHoraEntregaFormatada();
        }

        // bloco de pagamento que é exibido apenas se o cartão foi informado
        if (this.numeroCartao != null) {
            detalhes += "\nForma de Pagamento: Cartão " + getNumeroCartaoMascarado();
        }

        detalhes += "\n---------------------------------";
        return detalhes;
    }
}