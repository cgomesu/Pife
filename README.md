# Pife
Este projeto é um fork de [RoubaMonte](https://github.com/RolandTeodorowitsch/RoubaMonte) adaptado para o jogo de [Pife (pif-paf)](https://pt.wikipedia.org/wiki/Pife_(jogo_de_cartas)).

## Regras
O objetivo do jogador de Pife é formar combinações com as cartas que receber ou comprar.

### Início
- Um baralho de 52 cartas (sem curinga):
  - 02 até 04 jogadores
- Cada jogador recebe **09** cartas.
- As cartas restantes formam o monte de compras.

### A partida
O primeiro jogador compra **01** carta e então escolhe um dos seguintes:

- Ficar com a carta comprada e descartar outra;
- Ou descartar a carta comprada.

As cartas descartadas ficam **viradas para cima** na mesa, formando uma pilha de descartes (referido por **lixo**).

O jogador seguinte (no sentido horário) poderá então comprar a primeira carta do monte de compras **ou do topo do lixo**.

No fim da vez de cada jogador, o numero total de cartas na mão deve sempre ser de **09** cartas.

Os jogadores só podem baixar cartas quando forem feitas [**combinações**](#combinações). Caso contrário, as cartas permanecem na mão e em segredo até que seja possível [**bater**](#bater).

Ganha a partida o primeiro jogador que conseguir montar combinações com todas as suas cartas e, portanto, bater.  Um jogador só pode bater durante o seu turno.

### Combinações
Combinações precisam ser compostas por **três ou mais cartas** e podem ser de dois tipos:
- **Trincas**: cartas de mesmo valor e naipes diferentes;
- **Sequências**: cartas de valor diferente e de mesmo naipe.

### Bater
Um jogador só pode bater durante o seu turno, que começa com a compra de uma carta e termina com o descarte de uma carta da mão.

É possível bater tanto com 09 quanto com 10 cartas:
- Ao bater com 09, o jogador baixa combinações com 09 cartas e então descarta a última, declarando que bateu;
- Ao bater com 10, o jogador baixa combinações com 10 cartas e declara que bateu.

(De forma mais simples, um jogador bate quando é impossível descartar uma carta ou se após o descarte, o jogador não possui cartas na mão.)
