import java.util.Comparator;

public class ComparadorPorValor implements Comparator<Pedido>{

	@Override
	public int compare(Pedido o1, Pedido o2) {
	
		return (int) (o1.valorFinal() - o2.valorFinal());
	}
}
