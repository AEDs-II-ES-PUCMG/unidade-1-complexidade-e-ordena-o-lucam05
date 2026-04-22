import java.util.Comparator;

public class ComparadorPorPorcentagemDesconto implements Comparator<Produto> {
     @Override
    public int compare(Produto p1, Produto p2) {
        double desconto1 = p1.getPorcentagemDesconto();
        double desconto2 = p2.getPorcentagemDesconto();
        return Double.compare(desconto2, desconto1);
    }
}
