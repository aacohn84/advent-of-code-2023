package Day7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardBidPart2Test {

    @Test
    void getHandType() {
        CardBid fiveOfAKind = new CardBidPart2("AAAAA", 0);
        assertEquals(HandType.FIVE_OF_A_KIND, fiveOfAKind.getHandType());
        CardBid fiveOfAKindJ0 = new CardBidPart2("AAAAJ", 0);
        assertEquals(HandType.FIVE_OF_A_KIND, fiveOfAKindJ0.getHandType());
        CardBid fiveOfAKindJ1 = new CardBidPart2("JJJJ2", 0);
        assertEquals(HandType.FIVE_OF_A_KIND, fiveOfAKindJ1.getHandType());
        CardBid fiveOfAKindJ2 = new CardBidPart2("JJ333", 0);
        assertEquals(HandType.FIVE_OF_A_KIND, fiveOfAKindJ2.getHandType());
        CardBid fiveOfAKindJ3 = new CardBidPart2("JJJ22", 0);
        assertEquals(HandType.FIVE_OF_A_KIND, fiveOfAKindJ3.getHandType());

        CardBid fourOfAKind = new CardBidPart2("AA8AA", 0);
        assertEquals(HandType.FOUR_OF_A_KIND, fourOfAKind.getHandType());
        CardBid fourOfAKindJ0 = new CardBidPart2("222J3", 0);
        assertEquals(HandType.FOUR_OF_A_KIND, fourOfAKindJ0.getHandType());
        CardBid fourOfAKindJ1 = new CardBidPart2("22JJ3", 0);
        assertEquals(HandType.FOUR_OF_A_KIND, fourOfAKindJ1.getHandType());
        CardBid fourOfAKindJ2 = new CardBidPart2("2JJJ3", 0);
        assertEquals(HandType.FOUR_OF_A_KIND, fourOfAKindJ2.getHandType());

        CardBid fullHouse = new CardBidPart2("23332", 0);
        assertEquals(HandType.FULL_HOUSE, fullHouse.getHandType());
        CardBid fullHouseJ = new CardBidPart2("22J33", 0);
        assertEquals(HandType.FULL_HOUSE, fullHouseJ.getHandType());

        CardBid threeOfAKind = new CardBidPart2("TTT98", 0);
        assertEquals(HandType.THREE_OF_A_KIND, threeOfAKind.getHandType());
        CardBid threeOfAKindJ = new CardBidPart2("TTJ45", 0);
        assertEquals(HandType.THREE_OF_A_KIND, threeOfAKindJ.getHandType());

        CardBid twoPair = new CardBidPart2("23432", 0);
        assertEquals(HandType.TWO_PAIR, twoPair.getHandType());

        CardBid onePair = new CardBidPart2("A23A4", 0);
        assertEquals(HandType.ONE_PAIR, onePair.getHandType());
        CardBid onePairJ = new CardBidPart2("2345J", 0);
        assertEquals(HandType.ONE_PAIR, onePairJ.getHandType());

        CardBid highCard = new CardBidPart2("23456", 0);
        assertEquals(HandType.HIGH_CARD, highCard.getHandType());
    }
}