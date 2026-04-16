import java.util.Comparator;

public class ComparadorPorPorcentagemDesconto implements Comparator<Produto> {
     @Override
    public int compare(Produto p1, Produto p2) {
        double desconto1 = (p1.precoCusto * p1.margemLucro) / p1.precoCusto;
        double desconto2 = (p2.precoCusto * p2.margemLucro) / p2.precoCusto;
        return Double.compare(desconto2, desconto1);
    }
}
