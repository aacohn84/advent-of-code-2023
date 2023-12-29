package Day7;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CardBid implements Comparable<CardBid> {
    final String cards;
    final int handValue;
    final int bid;

    public CardBid (String cards, int bid) {
        this.cards = cards;
        this.bid = bid;
        this.handValue = calculateHandValue();
    }

    int calculateHandValue() {
        HandType type = getHandType();
        int value = type.ordinal() * (int) Math.pow(13, 5);
        for (int i = 0; i < 5; i++) {
            int cardValueInHand = getCardValue(cards.charAt(i)) * (int) Math.pow(13, 4 - i);
            value += cardValueInHand;
        }
        return value;
    }

    int getCardValue(char c) {
        switch (c) {
            case 'A': return 13;
            case 'K': return 12;
            case 'Q': return 11;
            case 'J': return 10;
            case 'T': return 9;
            default:
                return (int) c - 49;
        }
    }

    HandType getHandType() {
        Map<Character, Integer> cardCounts = new HashMap<>();
        for (Character c : cards.toCharArray()) {
            Integer count = cardCounts.putIfAbsent(c, 1);
            if (count != null) {
                if (count == 4) return HandType.FIVE_OF_A_KIND;
                cardCounts.put(c, count + 1);
            }
        }
        boolean foundThreeOfAKind = false;
        boolean foundPair = false;
        for (Integer count : cardCounts.values()) {
            if (count == 4) {
                return HandType.FOUR_OF_A_KIND;
            } else if (count == 3) {
                foundThreeOfAKind = true;
            } else if (count == 2) {
                if (foundPair) {
                    return HandType.TWO_PAIR;
                } else {
                    foundPair = true;
                }
            }
        }
        if (foundPair) {
            if (foundThreeOfAKind) {
                return HandType.FULL_HOUSE;
            } else {
                return HandType.ONE_PAIR;
            }
        } else if (foundThreeOfAKind) {
            return HandType.THREE_OF_A_KIND;
        } else {
            return HandType.HIGH_CARD;
        }
    }

    public int getBid() {
        return bid;
    }

    public int getHandValue() {
        return handValue;
    }

    @Override
    public int compareTo(@NotNull CardBid other) {
        return handValue - other.handValue;
    }
}
