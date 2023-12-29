package Day7;

public class CardBidPart1Builder implements CardBidBuilder {
    @Override
    public CardBid build(String cards, int bid) {
        return new CardBid(cards, bid);
    }
}
