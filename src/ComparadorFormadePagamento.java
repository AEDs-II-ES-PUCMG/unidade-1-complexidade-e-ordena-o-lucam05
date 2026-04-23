import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */
public class ComparadorFormadePagamento implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        if(o1.getFormaDePagamento() ==1 && o2.getFormaDePagamento()==2){
            return o1.hashCode();
        }
        if(o1.getFormaDePagamento() ==2 && o2.getFormaDePagamento()==1){
            return o2.hashCode();
        }
        else{
            double valor1 = o1.valorFinal();
            double valor2 = o2.valorFinal();
            return Double.compare(valor2, valor1);
        }
    }
}
