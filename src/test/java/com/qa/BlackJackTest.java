package com.qa;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import org.junit.jupiter.api.Test;

public class BlackJackTest {

    @Test //flags as test
    public void testP1Wins() {
        // error msg (optional), EXPECTED VALUE, ACTUAL VALUE
        assertEquals("Player 1 has not won", 21, BlackJack.play(21, 17));
    }

    @Test
    public void testP2WINS() {
        assertEquals("Player 2 has not won", 21, BlackJack.play(17, 21));
    }

    @Test
    public void testP1GoesBust() {
        assertEquals("Player 1 has gone bust, Player 2 wins", 24, BlackJack.play(24 , 16));
    }

    @Test
    public void testP2GoesBust() {
        assertEquals("Player 2 has gone bust, Player 1 wins", 24, BlackJack.play(16, 24));
    }

    @Test
    public void testDraw() {
        assertEquals("Players drew, returns 0", 0, BlackJack.play(16,16));
    }

    @Test
    public void testBothBust() {
        assertEquals("Both payers bust, returns 0", 0, BlackJack.play(24, 24));
    }
}
