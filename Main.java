import java.util.Random;
import java.util.Scanner;



public class Main {
    // Estes caracteres são aceitos como caracteres para representarem
    // os jogadores. Utizado para evitar caracteres que não combinem com
    // o desenho do tabuleiro.
    final static String CARACTERES_IDENTIFICADORES_ACEITOS = "XOUC"; //U -> usuário, C -> Computador

    // Tamanho do tabuleiro 3x3. 
    final static int TAMANHO_TABULEIRO = 3;

    static char[][] tabuleiro = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    
    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        
        inicializarTabuleiro();

    // Definimos aqui qual é o caractere que cada jogador irá utilizar no jogo.
    //TODO 01: modificado por YURI
        char caractereUsuario = obterCaractereUsuario();  
        char caractereComputador = obterCaractereComputador(caractereUsuario);  

    // Esta variavel é utilizada para definir se o usuário começa a jogar ou não.
    // Valor true, usuario começa jogando, valor false computador começa.
    //TODO 02:modificado por YURI
        boolean vezUsuarioJogar =  sortearValorBooleano();  

        boolean jogoContinua;
        

        do {
    // controla se o jogo terminou
            jogoContinua = true;
            exibirTabuleiro();
            
    //Verifica se é a vez do usuário jogar.
    //TODO 03: Modificado por ADRIANA
            if (vezUsuarioJogar == true){
                processarVezUsuario(caractereUsuario); 
                
    // Verifica se o usuario venceu
    //TODO 04:Modificado por ADRIANA¹ 
    //Modificado por João Victor² | if (teveGanhador == true) -> if (teveGanhador(caractereUsuario))
                if (teveGanhador(caractereUsuario)){
                    exibirTabuleiro();
                    exibirVitoriaUsuario();
                    jogoContinua = false;
                }

    // define que na proxima execucao do laco o jogador nao joga, ou seja, será a vez do computador
    //TODO 05:Modificado por ADRIANA
                vezUsuarioJogar = false;
            } else {
                processarVezComputador(caractereComputador); 
    
    // Verifica se o computador venceu
    //TODO 06:Modificado por ADRIANA
    //Modificado por João Victor² | if (teveGanhador == true) -> if (teveGanhador(caractereComputador))
                if (teveGanhador(caractereComputador)) {
                    exibirTabuleiro();
                    exibirVitoriaComputador(); 
    //TODO 07:Modificado por ADRIANA
                    jogoContinua = false;
                }

    //TODO 08:Modificado por ADRIANA
                vezUsuarioJogar = true;
            }
        
    //Este if deve executar apenas se o jogo continua e se ocorreu empate.
    //TODO 09:Modificado por ADRIANA
            if (jogoContinua && teveEmpate()) {
                exibirTabuleiro();
                exibirEmpate();
                jogoContinua = false;
            }
        } while (jogoContinua);

        teclado.close();
    }


    //Descrição: Utilizado para iniciar a matriz/tabuleiro com o caractere ' '
    //espaço, no início do jogo. Matrizes de char precisam ter um valor diferente de '' vazio. 
    //TODO 10:Modificado por Bruna
    static void inicializarTabuleiro() {
        
        for(int linha = 0; linha < tabuleiro.length; linha++){
            for(int coluna = 0; coluna < tabuleiro[linha].length; coluna++){
                tabuleiro[linha][coluna] = ' ';
            }
        }
    }

    //Descrição: Utilizado para obter no início do jogo qual o caractere que o
    //usuário quer utilizar para representar ele próprio. 
    //TODO 11: modificado por YURI
    // procurei uma forma de transformar as letras em maiusculas e achei esse toupperCase.
    static char obterCaractereUsuario() {
        
        char c;
        while (true) {
            System.out.print("Escolha seu caractere (X, O, U ou C): ");
            c = teclado.next().toUpperCase().charAt(0);

            if (CARACTERES_IDENTIFICADORES_ACEITOS.indexOf(c) == -1) {
                System.out.printf("%s é uma entrada inválida. Escolha uma das opções disponíveis.\n\n", c);
                continue;
            }
            return c;
        }
    }

    //Descrição: Utilizado para obter no início do jogo qual o caractere que o
    //usuário quer utilizar para representar o computador. */
    //TODO 12:Modificado por YURI 
    static char obterCaractereComputador(char caractereUsuario) {
        
        char c;
        while (true) {
            System.out.print("Escolha o caractere do computador: ");
            c = teclado.next().toUpperCase().charAt(0);

            if (CARACTERES_IDENTIFICADORES_ACEITOS.indexOf(c) == -1) {
                System.out.printf("%s é uma entrada inválida. Escolha uma das opções disponíveis.\n\n", c);
                continue;
            } else if (c == caractereUsuario) {
                System.out.printf("%s já escolhido. Use uma das outras opções.\n\n", c);
                continue;
            } else {
                return c;
            }
        }
        
    }

    //Descrição: Utilizado para validar se a jogada do usuário é uma jogada válida.
    //TODO 13: Modificado por
        static boolean jogadaValida(String posicoesLivres, int linha, int coluna) {
            String alvo = "" + linha + coluna + ";";
            return posicoesLivres.contains(alvo);    
    }
    

    //Descrição: Utilizado para obter do usuário a linha e a coluna que ele deseja jogar. 
    //TODO 14: Modificado por 
    static int[] obterJogadaUsuario(String posicoesLivres, Scanner teclado) {
    
        System.out.println("Sua vez!\n");
        System.out.println("Faça a sua jogada.\n" +
                "Exemplo: 1 1");
        System.out.println();
        
        while (true) {
            System.out.println("Digite linha e coluna (1 a 3): ");
            String[] entradaUsuario = teclado.nextLine().split(" ");

            if (entradaUsuario.length != 2) {
                System.out.println("\nDigite exatamente dois valores.");
                System.out.println("Exemplo: 1 1\n");
                continue;
            }

            int linha, coluna;
            try {
                linha = Integer.parseInt(entradaUsuario[0]) - 1;
                coluna = Integer.parseInt(entradaUsuario[1]) - 1;
            } catch (NumberFormatException e) {
                System.out.println("");
                System.out.println("Digite apenas números.");
                continue;
            }

            if (jogadaValida(posicoesLivres, linha, coluna)) {
                return new int[]{linha, coluna};
            }

            System.out.println("");
            System.out.println("Jogada inválida.");
            System.out.printf("Possíveis disponíveis para jogar: ");

            String[] posicoes = posicoesLivres.split(";");
            for (String posicao : posicoes) {
                System.out.printf(posicao + " ");
            }
        }
    }

    //Descrição: Utilizado para obter do computador a linha e a coluna sorteada.
    //TODO 15: Modificado por
    static int[] obterJogadaComputador(String posicoesLivres, Scanner teclado) {
        Random random = new Random();
        String[] posicoes = posicoesLivres.split(";");

        if(posicoes.length > 0 && !posicoes[0]. isEmpty()){
            String jogadaSelecionada = posicoes[random.nextInt(posicoes.length)];
            return converterJogadaStringParaVetorInt(jogadaSelecionada);
        }

        return new int[]{0,0};
    }

    //Descrição: Utilizado para converter uma jogada no formato xy (linha/coluna)
    //de string para um vetor de int. 
    //TODO 16:Modificado por
    static int[] converterJogadaStringParaVetorInt(String jogada) {
        
    int[] v = new int[2];
    v[0] = Character.getNumericValue(jogada.charAt(0));
    v[1] = Character.getNumericValue(jogada.charAt(1));
    return v;
}
 
    //Descrição: Utilizado para realizar as ações necessárias para processar a vez
    //do usuário jogar. 
    //TODO 17: Modificado por ADRIANA
    static void processarVezUsuario(char caractereUsuario) {
        
        String livres = retornarPosicoesLivres();
        int[] jogada = obterJogadaUsuario(livres , teclado);
        atualizaTabuleiro(jogada, caractereUsuario);
    }

    //Descrição: Utilizado para realizar as ações necessárias para processar a vez
    //do computador jogar. 
    //TODO 18: Modificado por ADRIANA
    static void processarVezComputador(char caractereComputador) {
        
        String livres = retornarPosicoesLivres();
        int[] jogada = obterJogadaComputador(livres , teclado);
        atualizaTabuleiro(jogada, caractereComputador);
    }

    //Descrição: Utilizado para identificar a lista de posições livres no tabuleiro. 
    //TODO 19: Modificado por
    static String retornarPosicoesLivres() {
        String posicoes ="";

        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (tabuleiro[i][j] == ' ') {
                    posicoes += i + "" + j + ";";
                }
            }
        }
        return posicoes;
    }

    //Descrição: Utilizado para verificar se o jogador identificado por
    //caractereJogador ganhou o jogo. 
    //TODO 20: Modificado por
    static boolean teveGanhador(char caractereJogador) {
        
        return teveGanhadorLinha(caractereJogador) || teveGanhadorColuna(caractereJogador) ||
                teveGanhadorDiagonalPrincipal(caractereJogador) || teveGanhadorDiagonalSecundaria(caractereJogador);
    }

    /* Descrição: Todos os métodos abaixo, teveGanhador... funcionam da mesma forma.
     * Recebem como parametro o tabuleiro e o caractereJogador. Cada um dos métodos
     * verificam no tabuleiro se o caractere do jogador está presente em todas as
     * posições. Se estiver presente retorna true, caso contrário retorna
     * false.*/
    //TODO 21: Modificado por 
    static boolean teveGanhadorLinha(char caractereJogador) {
        
        for(int i = 0; i < tabuleiro.length; i++){
            if (tabuleiro[i][0] == caractereJogador && tabuleiro[i][1] == caractereJogador && tabuleiro[i][2] == caractereJogador){
                return true;
            }
        }
        return false;
    }
    
    //TODO 22: Modificado por
    static boolean teveGanhadorColuna(char caractereJogador) {
        
        for(int linha = 0; linha < tabuleiro.length; linha++){
            for(int coluna = 0; coluna < tabuleiro[linha].length; coluna++){
                if((tabuleiro[0][coluna] == caractereJogador) && (tabuleiro[1][coluna] == caractereJogador) && (tabuleiro[2][coluna] == caractereJogador)){
                    return true;
                }
            }
        }
        return false;
    }
    
    //TODO 23: Modificado por
    static boolean teveGanhadorDiagonalPrincipal( char caractereJogador) {
        
        return tabuleiro[0][0] == caractereJogador && tabuleiro[1][1] == caractereJogador && tabuleiro[2][2] == caractereJogador;
    }

    //TODO 24: Modificado por
    static boolean teveGanhadorDiagonalSecundaria(char caractereJogador) {
        
        return tabuleiro[2][0] == caractereJogador && tabuleiro[1][1] == caractereJogador && tabuleiro[0][2] == caractereJogador;
    }

    //Descrição: Utilizado para limpar a console, para que seja exibido apenas o
    //conteúdo atual do jogo. 
    //TODO 25: Modificado por
    static void limparTela() {
        
        System.out.println("\n".repeat(20));
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //Descrição: Utilizado para imprimir o tabuleiro o conteúdo do tabuleiro na
    //tela. Recebe o tabuleiro como parametro e imprime o conteúdo de cada posição
    //do tabuleiro na tela. Imprime o conteúdo no formato de uma grade. */
    //TODO 26: Modificado por ADRIANA 
    static void exibirTabuleiro() {
     
        limparTela();
        System.out.println("   1   2   3");
        
        for (int i = 0; i < tabuleiro.length; i++) {
            System.out.print((i + 1) + " ");
            for(int j = 0; j < tabuleiro[i].length; j++) {
                if(j == 0){
                    System.out.print(" " + tabuleiro[i][j] + " | "); 
                }
                else if(j == 1) {
                    System.out.print(tabuleiro[i][j] + " | "); 

                }
                else {
                    System.out.print(tabuleiro[i][j] + "\n"); 
                }
            }
           
            if(i < tabuleiro.length - 1) {
                System.out.println("  ---+---+---");
            }
        }
        System.out.println();
    }

    //Descrição: Utilizado para atualizar o tabuleiro com o caractere que
    //identifica o jogador. 
    //TODO 27: Modificado por
    static void atualizaTabuleiro(int[] jogada, char caractereJogador) {
        int linha = jogada[0];
        int coluna = jogada[1];

        tabuleiro[linha][coluna] = caractereJogador;
        
    }

    //Descrição: Utilizado para exibir a frase: O computador venceu!, e uma ART
    //ASCII do computador feliz.
    //TODO 28: Modificado por
    static void exibirVitoriaComputador() {
        
		System.out.println("O computador venceu!");
		System.out.println("    ___");
		System.out.println("   |___|");
		System.out.println("   |o o|");
		System.out.println("   | ^ |");
		System.out.println("   |___|");
		System.out.println("  /|   |\\");
		System.out.println(" / |   | \\");
    }

    //Descrição: Utilizado para exibir a frase: O usuário venceu!, e uma ARTE ASCII
    //do usuário feliz. 
    //TODO 29:Modificado por
    static void exibirVitoriaUsuario() {
        //TODO 29: Implementar método conforme explicação
		System.out.println("O usuário venceu!");
		System.out.println("    \\o/");
		System.out.println("     |");
		System.out.println("    / \\");
		System.out.println("  PARABÉNS!");
    }

    //Utilizado para exibir a frase: Ocorreu empate!, e uma ARTE ASCII
    //do placar 0 X 0. 
    //TODO 30: Modificado por
    static void exibirEmpate() {
      
		System.out.println("Ocorreu empate!");
		System.out.println("   _____");
		System.out.println("  |0 X 0|");
		System.out.println("  |-----|");
		System.out.println("  |EMPATE|");
		System.out.println("   -----");
    }

    //Descrição: Utilizado para analisar se ocorreu empate no jogo. 
    //TODO 31: Modificado por
    static boolean teveEmpate() {
        String posicoes = retornarPosicoesLivres();
        return posicoes.isEmpty();        

    }

    //Descrição: Utilizado para realizar o sorteio de um valor booleano. Este
    //método deve sortear um valor entre true ou false. Este valor será
    //utilizado para identificar quem começa a jogar. 
    //TODO 32: Modificado por
    static boolean sortearValorBooleano() {
        Random random = new Random();
        return random.nextBoolean();
        
    }
}