import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class HeapSort<T extends Comparable<T>> implements IOrdenador<T> {

    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;

    public HeapSort() {
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

        // Build max heap
        for (int i = tamanho / 2 - 1; i >= 0; i--) {
            heapify(dadosOrdenados, tamanho, i, comparador);
        }

        // Extract elements from heap
        for (int i = tamanho - 1; i > 0; i--) {
            // Move current root to end
            swap(0, i, dadosOrdenados);
            // Call max heapify on the reduced heap
            heapify(dadosOrdenados, i, 0, comparador);
        }

        termino = LocalDateTime.now();

        return dadosOrdenados;
    }

    private void heapify(T[] arr, int n, int i, Comparator<T> comparador) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (left < n) {
            comparacoes++;
            if (comparador.compare(arr[left], arr[largest]) > 0) {
                largest = left;
            }
        }

        // If right child is larger than largest so far
        if (right < n) {
            comparacoes++;
            if (comparador.compare(arr[right], arr[largest]) > 0) {
                largest = right;
            }
        }

        // If largest is not root
        if (largest != i) {
            swap(i, largest, arr);
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest, comparador);
        }
    }

    private void swap(int i, int j, T[] vet) {
        movimentacoes++;
        T temp = vet[i];
        vet[i] = vet[j];
        vet[j] = temp;
    }

    public long getComparacoes() {
        return comparacoes;
    }

    public long getMovimentacoes() {
        return movimentacoes;
    }

    public double getTempoOrdenacao() {
        return Duration.between(inicio, termino).toMillis();
    }
}