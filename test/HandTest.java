import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HandTest {
    List<Card> HighCardCards;
    List<Card> OnePairCards;
    List<Card> TwoPairCards;
    List<Card> ThreeOfAKindCards;
    List<Card> StraightCards;
    List<Card> FlushCards;
    List<Card> FullHouseCards;
    List<Card> FourOfAKindCards;
    List<Card> StraightFlushCards;
    List<Card> RoyalStraightFlusCards;

    @Before
    public void setUp() throws Exception {
        HighCardCards = new ArrayList<Card>() {{
            add(new Card("H", 6));
            add(new Card("H", 4));
            add(new Card("H", 8));
            add(new Card("D", 13));
            add(new Card("H", 9));
        }};

        OnePairCards = new ArrayList<Card>() {{
            add(new Card("D", 4));
            add(new Card("H", 8));
            add(new Card("H", 4));
            add(new Card("D", 13));
            add(new Card("H", 9));
        }};

        TwoPairCards = new ArrayList<Card>() {{
            add(new Card("D", 4));
            add(new Card("H", 4));
            add(new Card("H", 8));
            add(new Card("D", 9));
            add(new Card("C", 8));
        }};

        ThreeOfAKindCards = new ArrayList<Card>() {{
            add(new Card("D", 4));
            add(new Card("H", 4));
            add(new Card("S", 8));
            add(new Card("C", 4));
            add(new Card("H", 9));
        }};

        StraightCards = new ArrayList<Card>() {{
            add(new Card("D", 4));
            add(new Card("H", 2));
            add(new Card("S", 3));
            add(new Card("D", 6));
            add(new Card("H", 5));
        }};

        FullHouseCards = new ArrayList<Card>() {{
            add(new Card("D", 4));
            add(new Card("H", 4));
            add(new Card("S", 3));
            add(new Card("S", 4));
            add(new Card("H", 3));
        }};

        FourOfAKindCards = new ArrayList<Card>() {{
            add(new Card("D", 4));
            add(new Card("H", 4));
            add(new Card("S", 3));
            add(new Card("S", 4));
            add(new Card("C", 4));
        }};

        FlushCards = new ArrayList<Card>() {{
            add(new Card("D", 4));
            add(new Card("D", 5));
            add(new Card("D", 7));
            add(new Card("D", 2));
            add(new Card("D", 13));
        }};

        StraightFlushCards = new ArrayList<Card>() {{
            add(new Card("D", 4));
            add(new Card("D", 5));
            add(new Card("D", 3));
            add(new Card("D", 6));
            add(new Card("D", 2));
        }};

        RoyalStraightFlusCards = new ArrayList<Card>() {{
            add(new Card("D", 11));
            add(new Card("D", 14));
            add(new Card("D", 12));
            add(new Card("D", 13));
            add(new Card("D", 10));
        }};

    }

    @Test
    public void shouldHighCardRankZero() throws Exception {
        Hand hand = new Hand(HighCardCards);

        assertThat(hand.get_rank(), is(Rank.HighCard));
    }

    @Test
    public void shouldOnePairRankOne() throws Exception {
        Hand hand = new Hand(OnePairCards);

        assertThat(hand.get_rank(), is(Rank.OnePair));
    }

    @Test
    public void shouldTwoPairRankTwo() throws Exception {
        Hand hand = new Hand(TwoPairCards);

        assertThat(hand.get_rank(), is(Rank.TwoPair));
    }

    @Test
    public void shouldThreeOfAKindRankThree() throws Exception {
        Hand hand = new Hand(ThreeOfAKindCards);

        assertThat(hand.get_rank(), is(Rank.ThreeOfAKind));
    }

    @Test
    public void shouldFullHouseRankSix() throws Exception {
        Hand hand = new Hand(FullHouseCards);

        assertThat(hand.get_rank(), is(Rank.FullHouse));
    }

    @Test
    public void shouldFourOfAKindRankSeven() throws Exception {
        Hand hand = new Hand(FourOfAKindCards);

        assertThat(hand.get_rank(), is(Rank.FourOfAKind));
    }

    @Test
    public void shouldStraightRankFour() throws Exception {
        Hand hand = new Hand(StraightCards);

        assertThat(hand.get_rank(), is(Rank.Straight));
    }

    @Test
    public void shouldSpecialStraightRankFour() throws Exception {
        Hand hand = new Hand(new ArrayList<Card>() {{
            add(new Card("H", 14));
            add(new Card("H", 2));
            add(new Card("H", 5));
            add(new Card("S", 3));
            add(new Card("H", 4));
        }});

        assertThat(hand.get_rank(), is(Rank.Straight));
    }

    @Test
    public void shouldFlushRankFive() throws Exception {
        Hand hand = new Hand(FlushCards);

        assertThat(hand.get_rank(), is(Rank.Flush));
    }

    @Test
    public void shouldStraightFlushRankEight() throws Exception {
        Hand hand = new Hand(StraightFlushCards);

        assertThat(hand.get_rank(), is(Rank.StraightFlush));
    }

    @Test
    public void shouldRoyalStraightFlushRank9() throws Exception {
        Hand hand = new Hand(RoyalStraightFlusCards);

        assertThat(hand.get_rank(), is(Rank.RoyalStraightFlush));
    }

    @Test
    public void shouldRoyalStraightFlushWinFlush() throws Exception {
        Hand royalStraightFlush = new Hand(RoyalStraightFlusCards);
        Hand flush = new Hand(FlushCards);

        assertThat(royalStraightFlush.compare(flush), is(1));
    }

    @Test
    public void shouldHighCardLoseToTwoPair() throws Exception {
        Hand highCard = new Hand(HighCardCards);
        Hand twoPair = new Hand(TwoPairCards);

        assertThat(highCard.compare(twoPair), is(-1));
    }

    @Test
    public void shouldCompareTwoHighCard() throws Exception {
        Hand hand1 = new Hand(HighCardCards);
        List<Card> cards = new ArrayList<Card>() {{
            add(new Card("H", 6));
            add(new Card("H", 4));
            add(new Card("H", 8));
            add(new Card("D", 10));
            add(new Card("H", 9));
        }};
        Hand other = new Hand(cards);

        assertThat(hand1.compare(other), is(1));
    }

    @Test
    public void shouldCompareTwoSameNumHighCard() throws Exception {
        Hand hand1 = new Hand(HighCardCards);
        List<Card> cards = new ArrayList<Card>() {{
            add(new Card("S", 6));
            add(new Card("S", 4));
            add(new Card("S", 8));
            add(new Card("H", 13));
            add(new Card("C", 9));
        }};
        Hand other = new Hand(cards);

        assertThat(hand1.compare(other), is(0));
    }

    @Test
    public void shouldCompareTwoTwoPair() throws Exception {
        Hand hand = new Hand(TwoPairCards);
        List<Card> cards = new ArrayList<Card>() {{
            add(new Card("S", 4));
            add(new Card("C", 4));
            add(new Card("S", 9));
            add(new Card("D", 8));
            add(new Card("H", 9));
        }};
        Hand other = new Hand(cards);
        List<Card> cards2 = new ArrayList<Card>() {{
            add(new Card("S", 4));
            add(new Card("C", 4));
            add(new Card("S", 9));
            add(new Card("D", 8));
            add(new Card("H", 8));
        }};
        Hand other2 = new Hand(cards2);

        assertThat(hand.compare(other), is(-1));
        assertThat(hand.compare(other2), is(0));
    }

    @Test
    public void shouldCompareTwoThreeOfAKind() throws Exception {
        Hand hand = new Hand(ThreeOfAKindCards);
        List<Card> cards = new ArrayList<Card>() {{
            add(new Card("D", 5));
            add(new Card("H", 5));
            add(new Card("S", 8));
            add(new Card("C", 4));
            add(new Card("S", 5));
        }};
        Hand other = new Hand(cards);

        assertThat(hand.compare(other), is(-1));
    }

    @Test
    public void shouldCompareTwoStraight() throws Exception {
        Hand hand = new Hand(StraightCards);
        List<Card> cards = new ArrayList<Card>() {{
            add(new Card("S", 4));
            add(new Card("H", 7));
            add(new Card("D", 3));
            add(new Card("H", 6));
            add(new Card("D", 5));
        }};
        Hand other = new Hand(cards);
        List<Card> cards2 = new ArrayList<Card>() {{
            add(new Card("S", 4));
            add(new Card("H", 2));
            add(new Card("D", 3));
            add(new Card("H", 6));
            add(new Card("D", 5));
        }};
        Hand other2 = new Hand(cards2);

        assertThat(hand.compare(other), is(-1));
        assertThat(hand.compare(other2), is(0));
    }

    @Test
    public void shouldCompareTwoFullHouse() throws Exception {
        Hand hand = new Hand(FullHouseCards);
        List<Card> cards = new ArrayList<Card>() {{
            add(new Card("D", 4));
            add(new Card("H", 4));
            add(new Card("S", 2));
            add(new Card("S", 4));
            add(new Card("H", 2));
        }};
        Hand other = new Hand(cards);

        assertThat(hand.compare(other), is(1));
    }

    @Test
    public void shouldCompareTwoFourOfAKind() throws Exception {
        Hand hand = new Hand(FourOfAKindCards);
        List<Card> cards = new ArrayList<Card>() {{
            add(new Card("D", 5));
            add(new Card("H", 5));
            add(new Card("S", 6));
            add(new Card("S", 5));
            add(new Card("C", 5));
        }};
        Hand other = new Hand(cards);

        assertThat(hand.compare(other), is(-1));
    }

    @Test
    public void shouldCompareTwoFlush() throws Exception {
        Hand hand = new Hand(FlushCards);
        List<Card> cards = new ArrayList<Card>() {{
            add(new Card("H", 4));
            add(new Card("H", 5));
            add(new Card("H", 7));
            add(new Card("H", 2));
            add(new Card("H", 12));
        }};
        Hand other = new Hand(cards);

        assertThat(hand.compare(other), is(1));
    }

    @Test
    public void shouldCompareTwoStraightFlush() throws Exception {
        Hand hand = new Hand(StraightFlushCards);
        List<Card> cards = new ArrayList<Card>() {{
            add(new Card("H", 4));
            add(new Card("H", 5));
            add(new Card("H", 3));
            add(new Card("H", 6));
            add(new Card("H", 7));
        }};
        Hand other = new Hand(cards);

        assertThat(hand.compare(other), is(-1));
    }

    @Test
    public void shouldCompareTwoRoyalStraightFlus() throws Exception {
        Hand hand = new Hand(RoyalStraightFlusCards);
        List<Card> cards = new ArrayList<Card>() {{
            add(new Card("H", 11));
            add(new Card("H", 14));
            add(new Card("H", 12));
            add(new Card("H", 13));
            add(new Card("H", 10));
        }};
        Hand other = new Hand(cards);

        assertThat(hand.compare(other), is(0));
    }
}
