import java.util.Scanner;

/**
 * Classe para criar e executar jogos de cartas Pife.
 * 
 * As regras usadas na implementa&ccedil;&atilde;o s&atilde;o basicamente 
 * 
 * @author cgomesu
 * @version 23 jun 2019
 */
public class Pife {
    
    private Baralho compras;
    private Baralho lixo;
    private Baralho[] mesaJogador;
    private Baralho[] maoJogador;
    private Jogador[] jogadores;
    private int numJogadores;

    //constantes de acordo com regras de Pife
    private static final int CARTAS_MAO = 9;

    /**
     * Construtor para a classe <code>Pife</code>.
     * 
     * Quando se cria um objeto desta classe, este m&eacute;todo inicializa todas as vari&aacute;veis de
     * inst&acirc;ncia necess&aacute;rias.
     * @param jog Vetor de objetos da classe <code>Jogador</code> que jogaram a partida.
     * @throws IllegalArgumentException &Eacute; lan&ccedil;ada quando o n&uacute;mero de jogadores &eacute; inv&aacute;lido.
     */
    public Pife(Jogador[] jog) throws IllegalArgumentException {
        int nJ = jog.length;
        if (nJ < 2 || nJ > 4)
            throw new IllegalArgumentException("Número inválido de jogadores.");
        jogadores = jog;
        
        numJogadores = nJ;
        compras = new Baralho(false);
        lixo = new Baralho();
        mesaJogador = new Baralho[numJogadores];
        maoJogador = new Baralho[numJogadores];
        
        compras.embaralha();

        for (int i=0;i<numJogadores;++i) {
            maoJogador[i] = new Baralho();
            mesaJogador[i] = new Baralho();
        }

        // Distribuicao inicial de cartas na mao de cada jogador
        Carta c;
        for (int i=0;i<CARTAS_MAO;++i) {
            for (int j=0;j<numJogadores;++j) {
                c = compras.remove();
                maoJogador[j].insere(c);
            }
        }
        // Restante das cartas ficam no monte de compras

    }

    /**
     * Mostra a mesa (no terminal) do ponto de vista de determinado jogador.
     * @param jogadorDaVez Corresponde ao &iacute;ndice do jogador que deve jogar na rodada atual.
     */
    private void mostraMesa(int jogadorDaVez) {
        System.out.println("========================================================================");
        //Compras
        int numCartasCompras = compras.obtemNumCartas();
        if (numCartasCompras == 0)
            System.out.printf("Compras (0): -\n");
        else 
            System.out.printf("Compras (%d): %s\n",compras.obtemNumCartas(),
                                                   compras.topo().toString());

        //Lixo
        int numCartasLixo = lixo.obtemNumCartas();
        if (numCartasLixo == 0)
            System.out.printf("Lixo (0): -\n");
        else 
            System.out.printf("Lixo (%d): %s\n",lixo.obtemNumCartas(),
                                                lixo.topo().toString());

        //Por Jogador
        for (int i=0;i<numJogadores;++i) {
            System.out.println("------------------------------------------------------------------------");
            System.out.printf("Jogador %s (%d):\n",jogadores[i].obtemNome(),i);
            if (i == jogadorDaVez)
                System.out.printf("- Mao (%d): %s\n",maoJogador[i].obtemNumCartas(),
                                                     maoJogador[i].toString());
            else
                System.out.printf("- Mao (%d): ...\n",maoJogador[i].obtemNumCartas());
            int nc = mesaJogador[i].obtemNumCartas();
            System.out.printf("- Mesa (%d): ",nc);
            if (nc == 0)
               System.out.printf("-\n");
            else
               System.out.printf("%s\n",mesaJogador[i].toString());
        }
        System.out.println("========================================================================");
    }
    
    /**
     * Realiza compra da carta do topo de um baralho.
     * @param jogador Corresponde ao &iacute;ndice do jogador que est&aatuce; tentando comprar uma carta.
     * @param baralho Um objeto da classe Baralho daonde o <code>jogador</code> comparará do topo.
     * @return <code>true</code> se estiver tudo certo e a compra for conclu&iaculte;do com sucesso ou <code>false</code>
     * se houver algum erro.
     */
    private boolean compraCarta(int jogador, Baralho baralho) {
        if (baralho.obtemNumCartas() < 1) return false;
        Carta cartaTopo = baralho.topo();
        maoJogador[jogador].insere(cartaTopo);
        baralho.remove();
        return true;
    }
    
    /**
     * Realiza o descarte (na mesa) da carta da m&atilde;o de um jogador.
     * @param jogador Corresponde ao &iacute;ndice do jogador que est&aatuce; realizando o descarte.
     * @param c &Iacute;ndice da carta da m&atilde;o do jogador em quest&atilde;o que se quer descartar.
     * @return <code>true</code> se estiver tudo certo e o roubo for conclu&iaculte;do com sucesso ou <code>false</code>
     * se houver algum erro.
     */
    private boolean descarta(int jogador, int c) {
        if (c < 0 || c >= maoJogador[jogador].obtemNumCartas())
            return false;
        Carta carta = maoJogador[jogador].remove(c);
        lixo.insere(carta);
        return true;
    }

    //TODO: (cgomesu) implementar jogadas automatizadas via alguma estrategia de jogo
    /**
     * Implementacao de teste
     * @param jogador Corresponde ao &iacute;ndice do jogador que deve jogar.
     * @return <code>true</code> se estiver tudo certo e a jogada tiver sido conclu&iaculte;da com sucesso ou <code>false</code>
     * se houver algum erro. A princ&iacute;pio este m&eacute;todo sempre retornar&aacute; <code>true</code>, pois o "computador"
     * n&atilde;o tentar&aacute; executar jogadas inv&aacute;lidas.
     */
    private boolean jogadaAutomatica(int jogadorDaVez) {
        // // Avalia cartas do Jogador:
        // // (1) para roubar monte...
        // int cartaParaRoubarMonte = -1;
        // int monteParaRoubar = -1;
        // for (int cm=0; cm<maoJogador[jogadorDaVez].obtemNumCartas(); ++cm) {
        //     int monte = qualMontePodeSerRoubado(jogadorDaVez,cm);
        //     if (monte != -1) {
        //         cartaParaRoubarMonte = cm;
        //         monteParaRoubar = monte;
        //         break;
        //     }
        // }
        // if (cartaParaRoubarMonte != -1)
        //     return roubaMonte(jogadorDaVez,cartaParaRoubarMonte,monteParaRoubar);
        // // (2) para roubar carta da mesa...
        // int cartaParaRoubarMesa = -1;
        // int cartaMesaParaRoubar = -1;
        // for (int cm=0; cm<maoJogador[jogadorDaVez].obtemNumCartas(); ++cm) {
        //     int cartaMesa = qualCartaMesaPodeSerRoubada(jogadorDaVez,cm);
        //     if (cartaMesa != -1) {
        //         cartaParaRoubarMesa = cm;
        //         cartaMesaParaRoubar = cartaMesa;
        //         break;
        //     }
        // }
        // if (cartaParaRoubarMesa != -1)
        //     return roubaMesa(jogadorDaVez,cartaParaRoubarMesa,cartaMesaParaRoubar);
        // // ... ou eh obrigado a descartar!
        // versao de teste apenas compra mesa ou lixo e descarta aleatorio
        boolean podeCompras = (compras.obtemNumCartas()>0) ? true : false;
        boolean podeLixo = (lixo.obtemNumCartas()>0) ? true : false;
        if (podeCompras)
            compraCarta(jogadorDaVez,compras);
        else if (podeLixo)
            compraCarta(jogadorDaVez,lixo);
        else
            return false;
        return descarta(jogadorDaVez, (int)((maoJogador[jogadorDaVez].obtemNumCartas()-1)*Math.random()));
    }
    
    /**
     * Realiza uma jogada para determinado jogador, que sempre ser&aacute; do tipo <code>TipoJogador.USUARIO</code>, usando determinado
     * dispositivo de entrada.
     * Neste m&eacute;todo apresenta-se um menu que mostra apenas op&ccedil;&otilde;es que realmente podem ser selecionadas pelo
     * usu&aacute;rio, considerando-se as regras do jogo Rouba Monte.
     * @param jogador Corresponde ao &iacute;ndice do jogador que deve jogar.
     * @param in Dispositivo a partir do qual a entrada deve ser lida (tipo <code>Scanner</code>).
     * @return <code>true</code> se tiver conseguido realizar uma jogada v&aacute;lida ou <code>false</code> caso o jogador
     * tenha selecionado a op&ccedil;&atilde;o para encerrar o jogo.
     */
    private boolean jogadaManual(int jogadorDaVez, Scanner in) {
        // Avalia cartas do Jogador:

        // (1) pode comprar do baralho?
        boolean podeCompras = (compras.obtemNumCartas()>0) ? true : false;

        // (2) pode comprar do lixo?
        boolean podeLixo = (lixo.obtemNumCartas()>0) ? true : false;

        boolean respostaValida;
        do {
            //opções de menu
            if (podeCompras) System.out.println("\n1 - COMPRAR DO BARALHO");
            if (podeLixo) System.out.println("2 - COMPRAR DO LIXO");
            System.out.println("9 - SAIR");
            // recebe e valida input do usuario para selecao de inteiro do menu
            System.out.print("JOGADA? ");
            int opcaoMenu;
            if (in.hasNextInt()) {
                opcaoMenu = in.nextInt();
            } else {
                opcaoMenu = -1;
                in.next();
            }
            switch (opcaoMenu) {
                case 1:
                    //compra do baralho
                    respostaValida = compraCarta(jogadorDaVez,compras); break;
                case 2:
                    //compra do lixo
                    respostaValida = compraCarta(jogadorDaVez,lixo); break;
                case 9:
                    //sair
                    return false;
                default:
                    respostaValida = false;
            }
        } while (!respostaValida);

        //mostrar nova mão
        mostraMesa(jogadorDaVez);

        //opcao de bater ou descartar
        int selecao;
        do {
            //novas opções de menu
            System.out.println("\n1 - BAIXAR JOGO");
            System.out.println("2 - DESCARTAR");
            System.out.println("9 - SAIR");
            // recebe e valida input do usuario para selecao de inteiro do menu
            System.out.print("JOGADA? ");
            int opcaoMenu;
            if (in.hasNextInt()) {
                opcaoMenu = in.nextInt();
            } else {
                opcaoMenu = -1;
                in.next();
            }
            switch (opcaoMenu) {
                case 1:
                    //baixar jogo
                    //TODO: (cgomesu) chamar metodo para baixar jogos
                    respostaValida = (maoJogador[jogadorDaVez].obtemNumCartas()>0) ? false : true;
                    break;
                case 2:
                    //descarta
                    if (maoJogador[jogadorDaVez].obtemNumCartas()==1)
                        selecao = 0;
                    else {
                        System.out.printf("Qual carta da sua mao [0;%d]? ", maoJogador[jogadorDaVez].obtemNumCartas()-1);
                        selecao = in.nextInt();
                    }
                    respostaValida = descarta(jogadorDaVez,selecao);
                    break;
                case 9:
                    //sair
                    return false;
                default:
                    respostaValida = false;
            }
        } while (!respostaValida);

        //condicao para termino do jogo
            //return false
        return true;
    }
    
    /**
     * Desenvolve o jogo entre os jogadores especificados no construtor.
     * Este m&eacute;todo ser&aacute; encerrado quando a partida tiver terminado ou quando um jogador tiver selecionado
     * a op&ccedil;&atilde;o para encerrar o jogo.
     */
    public void jogar() {
        Scanner in = new Scanner(System.in);
        int jogadorDaVez = 0;
        
        boolean continuar = true;
        do {
            mostraMesa(jogadorDaVez);
            
            if (jogadores[jogadorDaVez].obtemTipo() == TipoJogador.COMPUTADOR)
                continuar = jogadaAutomatica(jogadorDaVez);
            else
                continuar = jogadaManual(jogadorDaVez,in);
            
            mostraMesa(jogadorDaVez);

            for (int i = 0; i<10; ++i) System.out.println();
            
            // condicoes de fim de partida
            if (maoJogador[jogadorDaVez].obtemNumCartas() == 0) {
                System.out.printf("Vencedor: %s\n", jogadores[jogadorDaVez].obtemNome());
                continuar = false;
            }

            ++jogadorDaVez;
            if (jogadorDaVez >= numJogadores)
                jogadorDaVez = 0;
        } while (continuar);
        System.out.println("\n---- PARTIDA ENCERRADA ----");
    }
}
