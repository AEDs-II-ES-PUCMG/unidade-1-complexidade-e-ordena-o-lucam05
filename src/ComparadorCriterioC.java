import java.util.Comparator;

/**
 * Critério C - Índice de Economia (decrescente).
 * O índice de economia é a diferença entre o valor de catálogo atual e o valor efetivamente pago.
 * Desempate 1: Valor Final do Pedido (crescente).
 * Desempate 2: Código Identificador do pedido (crescente).
 */
public class ComparadorCriterioC implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        double valor1 = o1.valorFinal()/o1.getTotalItens();
        double valor2 = o2.valorFinal()/o1.getTotalItens();
        if(valor1>valor2){
            return o1.hashCode();
        }
        else if(valor2>valor1){
            return o2.hashCode();
        }
        else{
            double valor3 = o1.valorFinal();
            double valor4 = o2.valorFinal();
            return Double.compare(valor4, valor3);
        }
    }
}
