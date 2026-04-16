import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class Quicksort <T extends Comparable<T>> implements IOrdenador<T>  {
    
    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;

    public Quicksort() {
        comparacoes = 0;
        movimentacoes = 0;
    }

    @Override
    public T[] ordenar(T[] dados) {
        return ordenar(dados, T::compareTo);
    }

    @Override
    public T[] ordenar(T[] dados, Comparator<T> comparador) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        int tamanho = dadosOrdenados.length;

        inicio = LocalDateTime.now();

        quicksort(dadosOrdenados, 0, tamanho - 1, comparador);

        termino = LocalDateTime.now();

        return dadosOrdenados;
    }

    private void quicksort(T[] arr, int low, int high, Comparator<T> comparador) {
        if (low < high) {
            int pi = partition(arr, low, high, comparador);
            quicksort(arr, low, pi - 1, comparador);
            quicksort(arr, pi + 1, high, comparador);
        }
    }

    private int partition(T[] arr, int low, int high, Comparator<T> comparador) {
        T pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            comparacoes++;
            if (comparador.compare(arr[j], pivot) < 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(T[] arr, int i, int j) {
        movimentacoes++;
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Override
    public long getComparacoes() {
        return comparacoes;
    }

    @Override
    public long getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return Duration.between(inicio, termino).toMillis();
    }
}
