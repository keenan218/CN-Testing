package com.qa;

public class BlackJack {

    public static int play(int player1, int player2) {
        if (player1 > 21 && player2 > 21) {
            return 0;
        } else if (player1 > 21) {
            return player2;
        } else if (player2 > 21) {
            return player1;
        } else if (player1 > player2) {
            return player1;
        } else if (player2 > player1) {
            return player2;
        } else {
            return 0;
        }
    }
}
