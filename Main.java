/**
 * Classe principal para iniciar um jogo de cartas Pife.
 * 
 * @author cgomesu
 * @version 23 jun 2022
 */
public class Main {
    
    /**
     * M&eacute;todo principal, que define os jogadores para duas partidas, iniciando-as.
     * @param args Argumentos da linha de comandos (n&atilde;o s&atilde;o utilizados).
     */
    public static void main(String[] args) {
        // /* Partida 1: 4 jogadores autonomos */
        // Jogador[] jogadoresPartida1 = {
        //     new Jogador("COMPUTADOR1",TipoJogador.COMPUTADOR),
        //     new Jogador("COMPUTADOR2",TipoJogador.COMPUTADOR),
        //     new Jogador("COMPUTADOR3",TipoJogador.COMPUTADOR),
        //     new Jogador("COMPUTADOR4",TipoJogador.COMPUTADOR)
        // };
        // Pife partida1 = new Pife(jogadoresPartida1);
        // partida1.jogar();

        /* Partida 2: 2 jogadores: usuario x computador */
        Jogador[] jogadoresPartida2 = {
            new Jogador("VOCE",TipoJogador.USUARIO),
            new Jogador("COMPUTADOR",TipoJogador.COMPUTADOR),
        };
        Pife partida2 = new Pife(jogadoresPartida2);
        partida2.jogar();
    }
}
