
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class AppOficina {

    static final int MAX_PEDIDOS = 100;
    static Produto[] produtos;
    static int quantProdutos = 0;
    static Produto[] produtosPorCodigo;
    static Produto[] produtosPorDescricao;
    static Produto[] produtosPorDesconto;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;

    // #region utilidades
    static Scanner teclado;

    

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMÉRCIO DE COISINHAS v0.2\n================");
    }
    

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preço máximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha");
        System.out.println("2 - Inserção");
        System.out.println("3 - Seleção");
        System.out.println("4 - Mergesort");
        System.out.println("5 - Quicksort");
        System.out.println("6 - Heapsort");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Padrão");
        System.out.println("2 - Por código");
        System.out.println("3 - Por desconto");
        
        return lerNumero("Digite sua opção", Integer.class);
    }

    // #endregion
    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        quantProdutos = 0;
        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());
            
            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos++] = novoProduto;
            }
            dados.close();
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
            dadosCarregados = null;
        }
        return dadosCarregados;
    }


    static Produto localizarProduto() {
        if (produtos == null || quantProdutos == 0) {
            System.out.println("Nenhum produto carregado.");
            return null;
        }
        cabecalho();
        System.out.println("Localizando um produto");
        int numero = lerNumero("Digite o identificador do produto", Integer.class);
        Produto localizado = null;
        
        for (int i = 0; i < quantProdutos && localizado == null; i++) {
            if (produtos[i].hashCode() == numero)
                localizado = produtos[i];
        }
        return localizado;
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados inválidos";
        
        if(produto!=null){
            mensagem = String.format("Dados do produto:\n%s", produto);            
        }
        
        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo(){
        if (produtos == null || quantProdutos == 0) {
            System.out.println("Nenhum produto carregado.");
            return;
        }
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        double valor = lerNumero("valor", Double.class);
        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() < valor)
                relatorio.append(produtos[i]+"\n");
        }
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos(){
        if (produtos == null || quantProdutos == 0) {
            System.out.println("Nenhum produto carregado para ordenar.");
            return;
        }

        cabecalho();
        int opcao = exibirMenuOrdenadores();
        switch (opcao) {
            case 1 -> ordenador = new Bubblesort<>();
            case 2 -> ordenador = new InsertSort<>();
            case 3 -> ordenador = new SelectionSort<>();
            case 4 -> ordenador = new Mergesort<>();
            case 5 -> ordenador = new Quicksort<>();
            case 6 -> ordenador = new HeapSort<>();
            default -> ordenador = null;
        }

        if (ordenador == null) {
            System.out.println("Operação de ordenação cancelada.");
            return;
        }

        Produto[] copiaProdutos = Arrays.copyOf(produtos, quantProdutos);
        
        int opcaoComparador = exibirMenuComparadores();
        Comparator<Produto> comparador = switch (opcaoComparador) {
            case 2 -> new ComparadorPorCodigo();
            case 3 -> new ComparadorPorPorcentagemDesconto();
            default -> Comparator.naturalOrder();
        };
        
        Produto[] ordenados = ordenador.ordenar(copiaProdutos, comparador);

        System.out.println("Produtos ordenados:");
        for (Produto produto : ordenados) {
            System.out.println(produto);
        }

        verificarSubstituicao(produtos, ordenados);
    }

    static void embaralharProdutos(){
        if (produtos == null) {
            return;
        }
        Collections.shuffle(Arrays.asList(produtos));
    }

    static void verificarSubstituicao(Produto[] dadosOriginais, Produto[] copiaDados){
        cabecalho();
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)?");
        String resposta = teclado.nextLine().toUpperCase();
        if(resposta.equals("S"))
            produtos = Arrays.copyOf(copiaDados, copiaDados.length);
    }

    static void listarProdutos(){
        if (produtos == null || quantProdutos == 0) {
            System.out.println("Nenhum produto carregado.");
            return;
        }
        cabecalho();
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        produtos = carregarProdutos("src/produtos.txt");
        embaralharProdutos();

        // Tarefa 3: Criar três cópias dos dados, ordenadas por critérios diferentes
        if (produtos != null && quantProdutos > 0) {
            IOrdenador<Produto> ordenadorInicial = new Quicksort<>(); // Quicksort é eficiente para a carga inicial
            
            produtosPorDescricao = ordenadorInicial.ordenar(produtos, Comparator.naturalOrder());
            produtosPorCodigo = ordenadorInicial.ordenar(produtos, new ComparadorPorCodigo());
            produtosPorDesconto = ordenadorInicial.ordenar(produtos, new ComparadorPorPorcentagemDesconto());
            
            System.out.println("Cópias de dados ordenadas geradas com sucesso.");
            pausa();
        }

        int opcao = -1;
        
        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            pausa();
        }while (opcao != 0);
        teclado.close();
    }                        
}
