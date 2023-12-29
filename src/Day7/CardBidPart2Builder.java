package Day7;

public class CardBidPart2Builder implements CardBidBuilder {
    @Override
    public CardBid build(String cards, int bid) {
        return new CardBidPart2(cards, bid);
    }
}
