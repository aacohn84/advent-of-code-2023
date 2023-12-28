package Day7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardBidTest {

    @Test
    void compareTo() {
        CardBid a1 = new CardBid("AAAAA", 0);
        CardBid a2 = new CardBid("AAAAK", 0);
        assertTrue(a1.compareTo(a2) > 0);

        CardBid b1 = new CardBid("AAAAA", 0);
        CardBid b2 = new CardBid("AAAAA", 0);
        assertEquals(0, b1.compareTo(b2));

        CardBid c1 = new CardBid("22345", 0);
        CardBid c2 = new CardBid("AKQJT", 0);
        assertTrue(c1.compareTo(c2) > 0);
    }

    @Test
    void getHandType() {
        CardBid fiveOfAKind = new CardBid("AAAAA", 0);
        assertEquals(HandType.FIVE_OF_A_KIND, fiveOfAKind.getHandType());
        CardBid fourOfAKind = new CardBid("AA8AA", 0);
        assertEquals(HandType.FOUR_OF_A_KIND, fourOfAKind.getHandType());
        CardBid fullHouse = new CardBid("23332", 0);
        assertEquals(HandType.FULL_HOUSE, fullHouse.getHandType());
        CardBid threeOfAKind = new CardBid("TTT98", 0);
        assertEquals(HandType.THREE_OF_A_KIND, threeOfAKind.getHandType());
        CardBid twoPair = new CardBid("23432", 0);
        assertEquals(HandType.TWO_PAIR, twoPair.getHandType());
        CardBid onePair = new CardBid("A23A4", 0);
        assertEquals(HandType.ONE_PAIR, onePair.getHandType());
        CardBid highCard = new CardBid("23456", 0);
        assertEquals(HandType.HIGH_CARD, highCard.getHandType());
    }

    @Test
    void calculateHandValue() {
        CardBid a = new CardBid("AAAAA", 0);
        assertEquals(2629991, a.getHandValue());
        CardBid b = new CardBid("22345", 0);
        assertEquals(402432, b.getHandValue());
        CardBid c = new CardBid("AKQJT", 0);
        assertEquals(399655, c.getHandValue());
    }
}