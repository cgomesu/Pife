import java.util.Scanner;

/**
 * Classe para criar e executar jogos de cartas Pife.
 * 
 * As regras usadas na implementa&ccedil;&atilde;o s&atilde;o basicamente 
 * 
 * @author cgomesu
 * @version 01 jul 2022
 */
public class Pife {
    
    private Baralho compras;
    private Baralho lixo;
    private Baralho[] maoJogador;
    private Baralho[] mesaJogador;
    private Baralho[] combinacaoJogador;
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
        combinacaoJogador = new Baralho[numJogadores];
        maoJogador = new Baralho[numJogadores];
        
        compras.embaralha();

        for (int i=0;i<numJogadores;++i) {
            maoJogador[i] = new Baralho();
            mesaJogador[i] = new Baralho();
            combinacaoJogador[i] = new Baralho();
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
            System.out.printf("Baralho (0): -\n");
        else 
            System.out.printf("Baralho (%d): ?\n",compras.obtemNumCartas());

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
                mostraMao(i, false);
            else
                mostraMao(i, true);
        }
        System.out.println("========================================================================");
    }

    /**
     * Mostra a mao de um jogador.
     * @param jogador
     */
    public void mostraMao(int jogador) {
        System.out.printf("- Mao (%d): %s\n",
                          maoJogador[jogador].obtemNumCartas(),
                          maoJogador[jogador].toString());
    }
    /**
     * Mostra a mao de um jogador.
     * @param jogador Numero do jogador.
     * @param ocultaCartas <code>true</code> indica que as cartas nao devem ser apresentadas
     * com face virada para cima.
     */
    public void mostraMao(int jogador, boolean ocultaCartas) {
        if (ocultaCartas) System.out.printf("- Mao (%d): ...\n", maoJogador[jogador].obtemNumCartas());
        else mostraMao(jogador);
    }

    /**
     * Mostra a mesa de um jogador.
     * @param jogador Numero do jogador.
     */
    public void mostraMesaJogador(int jogador) {
        System.out.printf("- Mesa Jogador (%d): %s\n",
                          mesaJogador[jogador].obtemNumCartas(),
                          mesaJogador[jogador].toString());
    }

    /**
     * Mostra a combinacao de um jogador.
     * @param jogador Numero do jogador.
     */
    public void mostraCombinacao(int jogador) {
        System.out.printf("- Combinação (%d): %s\n",
                          combinacaoJogador[jogador].obtemNumCartas(),
                          combinacaoJogador[jogador].toString());
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

    /**
     * Move todas as cartas de um baralho para outro.
     * @param origem Baralho de origem, ou seja, daonde as cartas sairão.
     * @param destino Baralho de destino, ou seja, daonde as cartas entrarão.
     * @return <code>true</code> se foi possivel mover todas cartas do baralho de origem para o destino.
     */
    public boolean moveBaralho(Baralho origem, Baralho destino) {
        if (origem.obtemNumCartas()<1)
            return false;
        while (origem.obtemNumCartas()>0) {
            Carta c = origem.remove();
            destino.insere(c);
        }
        return true;
    }

    /**
     * Baixa uma combinacao valida para mesa
     * @param jogadorDaVez
     * @return
     */
    public boolean baixarCombinacao(int jogador, boolean trinca, Scanner in) {
        //mao precisa ter ao menos 3 cartas para formar uma combinacao valida
        if (maoJogador[jogador].obtemNumCartas()<3)
            return false;
        boolean combinacaoValida = false;
        do {
            int selecao;
            int limite = maoJogador[jogador].obtemNumCartas()-1;
            mostraMao(jogador);
            if (combinacaoJogador[jogador].obtemNumCartas()>0)
                mostraCombinacao(jogador);
            System.out.printf("\nQual carta da sua mao [0;%d]? (negativo para finalizar) ", limite);
            if (in.hasNextInt()) {
                selecao = in.nextInt();
            } else {
                in.next();
                continue;
            }
            //valida selecao
            if (selecao >= 0 && selecao <= limite) {
                //selecao valida indicando carta
                Carta c = maoJogador[jogador].remove(selecao);
                if (combinacaoJogador[jogador].obtemNumCartas()==0) {
                    combinacaoJogador[jogador].insere(c);
                } else if (trinca) {
                    // segue definicao trinca?
                    if (c.obtemFigura()==combinacaoJogador[jogador].topo().obtemFigura()) {
                        combinacaoJogador[jogador].insere(c);
                    } else {
                        //devolve carta para mao
                        maoJogador[jogador].insere(c);
                    }
                } else {
                    // segue sequencia? primeiro ver naipe, entao valores possiveis
                    boolean achouSequencia = false;
                    if (c.obtemNaipe()==combinacaoJogador[jogador].topo().obtemNaipe()) {
                        // loop se selecao e de um valor possivel com o que existe na combinacao atual
                        for (int i=0; i<combinacaoJogador[jogador].obtemNumCartas(); ++i) {
                            if (c.obtemValor()==combinacaoJogador[jogador].posicao(i).obtemValor()+1
                                || c.obtemValor()==combinacaoJogador[jogador].posicao(i).obtemValor()-1) {
                                    //carta selecionada é válida
                                    combinacaoJogador[jogador].insere(c);
                                    achouSequencia = true;
                            }
                        }
                    }
                    if (!achouSequencia)
                        maoJogador[jogador].insere(c);
                }
            } else if (selecao < 0) {
                // selecao valida indicando terminacao
                //baixar combinacao para mesa se valida; devolver cartas para mao caso contrario.
                if (combinacaoJogador[jogador].obtemNumCartas()>=3) {
                    moveBaralho(combinacaoJogador[jogador], mesaJogador[jogador]);
                    //mostra combinação baixada
                    mostraMesa(jogador);
                    combinacaoValida = true;
                } else {
                    moveBaralho(combinacaoJogador[jogador], maoJogador[jogador]);
                    mostraMao(jogador);
                    return false;
                }
            } else {
                // selecao invalida
                combinacaoValida = false;
            }
        } while (!combinacaoValida && maoJogador[jogador].obtemNumCartas()>0);
        return true;
    }

    //TODO: (cgomesu) implementar jogadas automatizadas via alguma estrategia de jogo
    /**
     * Implementacao de teste de jogada automatizada em que computador apenas compra mesa ou lixo e entao
     * descarta uma carta aleatoria.
     * @param jogador Corresponde ao &iacute;ndice do jogador que deve jogar.
     * @return <code>true</code> se estiver tudo certo e a jogada tiver sido conclu&iaculte;da com sucesso ou <code>false</code>
     * se houver algum erro. A princ&iacute;pio este m&eacute;todo sempre retornar&aacute; <code>true</code>, pois o "computador"
     * n&atilde;o tentar&aacute; executar jogadas inv&aacute;lidas.
     */
    private boolean jogadaAutomatica(int jogadorDaVez) {
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
        // (1) pode comprar do baralho?
        boolean podeCompras = (compras.obtemNumCartas()>0) ? true : false;
        // (2) pode comprar do lixo?
        boolean podeLixo = (lixo.obtemNumCartas()>0) ? true : false;

        boolean menuCompraValida = false;
        do {
            //opções de menu compra
            if (podeCompras) System.out.println("\n1 - COMPRAR DO BARALHO");
            if (podeLixo) System.out.println("2 - COMPRAR DO LIXO");
            System.out.println("9 - SAIR");
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
                    menuCompraValida = compraCarta(jogadorDaVez,compras); break;
                case 2:
                    //compra do lixo
                    menuCompraValida = compraCarta(jogadorDaVez,lixo); break;
                case 9:
                    //sair
                    return false;
                default:
                    menuCompraValida = false;
            }
        } while (!menuCompraValida);

        //mostrar nova mão
        System.out.println("\nPós-compra:");
        mostraMao(jogadorDaVez);

        //opcao de bater ou descartar
        boolean menuPrincipalValido = false;
        do {
            //novas opções de menu
            System.out.println("\n1 - BATER");
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
                    //bater jogo
                    boolean menuBaterValido = false;
                    do {
                        System.out.println("\nQual o tipo de combinação?");
                        System.out.println("1 - TRINCA");
                        System.out.println("2 - SEQUENCIA");
                        System.out.println("3 - DESISTIR DE BATER");
                        System.out.print("JOGADA? ");
                        int opcaoBater;
                        if (in.hasNextInt()) {
                            opcaoBater = in.nextInt();
                        } else {
                            opcaoBater = -1;
                            in.next();
                        }
                        switch (opcaoBater) {
                            case 1:
                                baixarCombinacao(jogadorDaVez, true, in);
                                menuBaterValido = (maoJogador[jogadorDaVez].obtemNumCartas()>1) ? false : true;
                                //trinca
                                break;
                            case 2:
                                //sequencia
                                baixarCombinacao(jogadorDaVez, false, in);
                                menuBaterValido = (maoJogador[jogadorDaVez].obtemNumCartas()>1) ? false : true;
                                break;
                            case 3:
                                //desistir
                                menuBaterValido = (mesaJogador[jogadorDaVez].obtemNumCartas()>0)
                                                   ? moveBaralho(mesaJogador[jogadorDaVez], 
                                                                 maoJogador[jogadorDaVez])
                                                   : true;
                                mostraMao(jogadorDaVez);
                                break;
                            default:
                                menuBaterValido = false;
                        }
                    } while (!menuBaterValido);
                    //valido apenas se for pular descarte
                    menuPrincipalValido = (maoJogador[jogadorDaVez].obtemNumCartas()==0);
                    break;
                case 2:
                    //descarta
                    int selecao;
                    if (maoJogador[jogadorDaVez].obtemNumCartas()==1)
                        selecao = 0;
                    else {
                        System.out.println("\nPós-compra:");
                        mostraMao(jogadorDaVez);
                        System.out.printf("Qual carta da sua mao [0;%d]? ",
                                          maoJogador[jogadorDaVez].obtemNumCartas()-1);
                        selecao = in.nextInt();
                    }
                    menuPrincipalValido = descarta(jogadorDaVez,selecao);
                    break;
                case 9:
                    //sair
                    return false;
                default:
                    menuPrincipalValido = false;
            }
        } while (!menuPrincipalValido);

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

            // espaçamento entre mesas
            for (int i = 0; i<40; ++i) System.out.println();
            
            // condicoes de fim de partida
            if (maoJogador[jogadorDaVez].obtemNumCartas() == 0) {
                System.out.printf("Vencedor: %s\n", jogadores[jogadorDaVez].obtemNome());
                mostraMesaJogador(jogadorDaVez);
                continuar = false;
            }

            ++jogadorDaVez;
            if (jogadorDaVez >= numJogadores)
                jogadorDaVez = 0;
        } while (continuar);

        System.out.println("\n---- PARTIDA ENCERRADA ----");
    }
}
