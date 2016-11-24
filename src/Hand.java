import java.util.*;

import static java.util.stream.Collectors.toList;

public class Hand {
    private List<Card> cards;
    private int rank;

    private Rank _rank;

    public Hand(List<Card> cards) {
        this.cards = cards.stream()
                .sorted(Comparator.comparing(Card::getNum).reversed()).collect(toList());;
        _rank = Rank.from(this.cards);
        rank = _rank.ordinal();
    }

    public int getRank() {
        return rank;
    }

    public Rank get_rank() {
        return _rank;
    }

    public List getOrderSet() {
        List orderSet = new ArrayList();
        orderSet.add(cards.get(0).getNum());

        for (int i = 1; i < cards.size(); i++)
            if (cards.get(i).getNum() == (int) orderSet.get(orderSet.size() - 1)) {
                orderSet.remove(orderSet.size() - 1);
                orderSet.add(0, cards.get(i).getNum());
            } else if (cards.get(i).getNum() != (int) orderSet.get(0)) {
                orderSet.add(cards.get(i).getNum());
            }
        return orderSet;
    }

    public int compare(Hand other) {
        if (rank > other.getRank())
            return 1;
        if (rank < other.getRank())
            return -1;
        List selfOrderSet = getOrderSet();
        List otherOrderSet = other.getOrderSet();
        for (int i = 0; i < selfOrderSet.size(); i++) {
            if ((int) selfOrderSet.get(i) > (int) otherOrderSet.get(i))
                return 1;
            if ((int) selfOrderSet.get(i) < (int) otherOrderSet.get(i))
                return -1;
        }
        return 0;
    }
}
