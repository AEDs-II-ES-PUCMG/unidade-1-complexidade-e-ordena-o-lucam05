import java.util.Comparator;

/**
 * Critério A - Valor Final do Pedido (crescente).
 * Desempate 1: Volume Total de Itens (quantProdutos).
 * Desempate 2: Código Identificador do primeiro item do pedido.
 */
public class ComparadorValorfinaldoPedido implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
      double valor1 = o1.valorFinal();
      double valor2 = o2.valorFinal();
      return Double.compare(valor2, valor1);
    }
}
