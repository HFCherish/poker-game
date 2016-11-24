import java.util.*;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public enum Rank {
    HighCard {
        @Override
        boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
            return true;
        }
    }, OnePair {
        @Override
        boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
            return cardCount.size() == 4 && maxCount == 2;
        }
    }, TwoPair {
        @Override
        boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
            return cardCount.size() == 3 && maxCount == 2;
        }
    }, ThreeOfAKind{
        @Override
        boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
            return cardCount.size() == 3 && maxCount == 3;
        }

    }, Straight {
        @Override
        boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
            return checkStraight(cards);
        }
    },
    Flush {
        @Override
        boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
            return checkSameSuit(cards);
        }
    }, FullHouse {
        @Override
        boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
            return cardCount.size() == 2 && maxCount == 3;
        }


    }, FourOfAKind {
        @Override
        boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
            return cardCount.size() == 2 && maxCount == 4;
        }

    }, StraightFlush {
        @Override
        boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
            return Flush.isQualified(cards, cardCount, maxCount) && Straight.isQualified(cards, cardCount, maxCount);
        }
    }, RoyalStraightFlush {
        @Override
        boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
            return StraightFlush.isQualified(cards, cardCount, maxCount) && (cards.get(cards.size() - 1).getNum() == 10);
        }
    };

    boolean isQualified(List<Card> cards, Map<Integer, Integer> cardCount, int maxCount) {
        return false;
    }

    protected boolean checkSameSuit(List<Card> cards) {
        for (int i = 1; i < cards.size(); i++) {
            if (!cards.get(i).getSuit().equals(cards.get(i - 1).getSuit()))
                return false;
        }
        return true;
    }

    protected boolean checkStraight(List<Card> cards) {
        List<Card> tem = new ArrayList<>();
        Collections.addAll(tem, new Card[cards.size()]);
        Collections.copy(tem, cards);
        if (tem.get(0).getNum() == 14 && tem.get(tem.size() - 1).getNum() == 2) {
            tem.remove(0);
            tem.add(new Card(tem.get(tem.size() - 1).getSuit(), 1));
        }
        for (int i = 1; i < tem.size(); i++) {
            if (tem.get(i - 1).getNum() - tem.get(i).getNum() != 1)
                return false;
        }
        return true;
    }

    protected static Rank from(List<Card> cards) {
        Map<Integer, Integer> cardCount = countCard(cards);
        int maxCount = getMaxCount(cardCount);
        List<Rank> ranks = asList(values())
                .stream().filter(r -> r.isQualified(cards, cardCount, maxCount))
                .collect(toList());
        return ranks.get(ranks.size() - 1);
    }

    private static Integer getMaxCount(Map<Integer, Integer> cardCount) {
        return cardCount.values().stream().sorted((a, b) -> a > b ? -1 : 1).findFirst().get();
    }

    private static Map<Integer, Integer> countCard(List<Card> cards) {
        Map<Integer, Integer> cardCount = new HashMap<>();
        for (Card card : cards) {
            int num = card.getNum();
            if (!cardCount.containsKey(num))
                cardCount.put(num, 0);
            cardCount.put(num, cardCount.get(num) + 1);
        }
        return cardCount;
    }
}
