/**
 * Classe principal para iniciar um jogo de cartas Pife.
 * 
 * @author cgomesu
 * @version 01 jul 2022
 */
public class Main {
    
    /**
     * M&eacute;todo principal, que define os jogadores para duas partidas, iniciando-as.
     * @param args Argumentos da linha de comandos (n&atilde;o s&atilde;o utilizados).
     */
    public static void main(String[] args) {
        /* Partida 1: 2 jogadores: usuario x computador */
        Jogador[] jogadoresPartida2 = {
            new Jogador("VOCE",TipoJogador.USUARIO),
            new Jogador("COMPUTADOR",TipoJogador.COMPUTADOR),
        };
        Pife partida2 = new Pife(jogadoresPartida2);
        partida2.jogar();
    }
}
