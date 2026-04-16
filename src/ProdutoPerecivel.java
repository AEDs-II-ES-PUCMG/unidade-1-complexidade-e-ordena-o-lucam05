
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;



public class ProdutoPerecivel extends Produto{
    /** Desconto para proximidade de validade: 25% */
    private static final double DESCONTO = 0.25;
    
    /** Prazo, em dias, para conceder o desconto por proximidade da validade */
    private static final int PRAZO_DESCONTO = 7;
    
    /** Data de validade do produto. Não pode ser anterior à data da criação ou venda */
    private LocalDate dataDeValidade;

    /**
     * Construtor completo. 
     * Causa exceção em caso de valores inválidos
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param precoCusto Preço de compra do produto (mínimo 0.01)
     * @param margemLucro Margem de lucro para a venda (mínimo 0.01)
     * @param validade Data de validade do produto, que deve ser posterior à data atual.
     * @throws IllegalArgumentException em caso dos limites acima serem desrespeitados.
     */
    public ProdutoPerecivel(String descricao, double precoCusto, double margemLucro, LocalDate validade){
        super(descricao, precoCusto, margemLucro);
        if(validade.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Validade anterior ao dia de hoje!");
        dataDeValidade = validade;
    }

    /**
     * Retorna o valor de venda do produto, considerando seu preço de custo, margem de lucro e
     * dias de validade. Se o prazo de validade estiver a menos de 7 dias, será concedido desconto de 25%.
     * @return Valor de venda do produto (double, positivo)
     */
    @Override
    public double valorDeVenda() {
        double desconto = 0d;
        long diasValidade = LocalDate.now().until(dataDeValidade,ChronoUnit.DAYS);
        if(diasValidade<=PRAZO_DESCONTO)
            desconto = DESCONTO;
        return (precoCusto * (1+margemLucro)) * (1-desconto);
    }

    /**
     * Retorna a porcentagem de desconto aplicada ao produto
     * @return Porcentagem de desconto (0.25 se dentro do prazo, 0.0 caso contrário)
     */
    @Override
    public double getPorcentagemDesconto() {
        long diasValidade = LocalDate.now().until(dataDeValidade, ChronoUnit.DAYS);
        return (diasValidade <= PRAZO_DESCONTO) ? DESCONTO : 0.0;
    }

    /**
     * Descrição em string do produto, contendo sua descrição, o valor de venda e data de validade.
     *  @return String com o formato:
     * [NOME]: R$ [VALOR DE VENDA]
     * Válido até [DD/MM/YYYY]
     */
    @Override
    public String toString(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        String dados = super.toString();
        dados += " - Válido até "+formato.format(dataDeValidade);
        return dados;
    }

    /**
     * Gera uma linha de texto a partir dos dados do produto. Preço e margem de lucro vão formatados com 2 casas decimais.
     * Data de validade vai no formato dd/mm/aaaa
     * @return Uma string no formato "2; descrição;preçoDeCusto;margemDeLucro;dataDeValidade"
     */
    @Override
    public String gerarDadosTexto() {
        DateTimeFormatter formatador =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = formatador.format(dataDeValidade);
        
        return String.format("2;%s;%.2f;%.2f;%s", 
                descricao, precoCusto, margemLucro, dataFormatada);

    }        
    
}
