package Day7;

import java.util.HashMap;
import java.util.Map;

public class CardBidPart2 extends CardBid {

    public CardBidPart2(String cards, int bid) {
        super(cards, bid);
    }

    @Override
    int getCardValue(char c) {
        switch (c) {
            case 'A': return 13;
            case 'K': return 12;
            case 'Q': return 11;
            case 'T': return 10;
            case 'J': return 1;
            default:
                return (int) c - 48;
        }
    }

    @Override
    HandType getHandType() {
        if (!cards.contains("J")) {
            return super.getHandType();
        }
        // From this point on, we know we have at least one joker
        Map<Character, Integer> cardCounts = new HashMap<>();
        for (Character c : cards.toCharArray()) {
            Integer count = cardCounts.putIfAbsent(c, 1);
            if (count != null) {
                if (count == 3) {
                    // 4 of a kind + 1 joker = 5 of a kind
                    return HandType.FIVE_OF_A_KIND;
                }
                cardCounts.put(c, count + 1);
            }
        }
        int jokerCount = cardCounts.get('J');
        cardCounts.remove('J');
        boolean foundPair = false;
        for (Integer count : cardCounts.values()) {
            if (count == 3) {
                if (jokerCount == 2) {
                    // 3 of a kind + 2 jokers = 5 of a kind
                    return HandType.FIVE_OF_A_KIND;
                } else {
                    // 3 of a kind + 1 joker, so the best possible hand is 4 of a kind
                    return HandType.FOUR_OF_A_KIND;
                }
            } else if (count == 2) {
                if (jokerCount == 3) {
                    // 2 of a kind + 3 jokers = 5 of a kind
                    return HandType.FIVE_OF_A_KIND;
                } else if (jokerCount == 2) {
                    // 2 of a kind + 2 jokers = 4 of a kind
                    return HandType.FOUR_OF_A_KIND;
                } else if (foundPair) {
                    // 2 pair + 1 joker = full house
                    return HandType.FULL_HOUSE;
                } else {
                    foundPair = true;
                }
            }
        }
        if (foundPair) {
            // 1 pair + 1 joker = 3 of a kind
            return HandType.THREE_OF_A_KIND;
        }
        switch (jokerCount) {
            case 4: return HandType.FIVE_OF_A_KIND;
            case 3: return HandType.FOUR_OF_A_KIND;
            case 2: return HandType.THREE_OF_A_KIND;
            default: return HandType.ONE_PAIR;
        }
    }
}
