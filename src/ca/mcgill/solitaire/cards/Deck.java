package ca.mcgill.solitaire.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck of 52 cards in a pre-determined (winnable) order.
 * Each configuration lists cards in draw order. A card is a rank symbol
 * (A, 2-10, J, Q, K) followed by a suit symbol (C, D, H, S).
 */
public class Deck {
	private static final String[] CONFIGURATIONS = {
		"2C 8D 3C 7D QC 9C QD 8S 6D AS 3H JS JC 4C 5H 7H 9S 9D 3S 6S JH 5C KH 7C 9H 2D AC 7S AD JD AH 10S 8C 4H 4S QS 8H KS 10C 5S 6H KC 4D 2H 5D KD 10D 6C 2S QH 10H 3D",
		"7H 2H QH 8C 2S QS 4C 3H 8S 2D 5D QD KH AC KD JH 8H JS 4D 5C QC 7S 6S KS 7D 10S 2C 6D AD 3D AH 3C 5S 6C KC 9C 10D 7C AS 4H 9H 5H JC 9D 10H 8D 9S JD 4S 3S 6H 10C",
		"5C 6C 10S QC 7D QH 6S 8H AD 4C 9H 6H 7S QD AH 4H 4S QS 5D 8D 8C KH 3D 2C KC 7C 4D 2H KS JC 2D 3S 9D 10D 5S AC AS 3H 5H KD 7H JH 10H 3C 6D JD 2S 8S 9S 10C 9C JS" };

	private final int aConfiguration;
	private CardStack aCards;

	public Deck() {
		this(0);
	}

	public Deck(int pConfiguration) {
		assert pConfiguration >= 0 && pConfiguration < CONFIGURATIONS.length;
		aConfiguration = pConfiguration;
		reset();
	}

	public static int numberOfConfigurations() {
		return CONFIGURATIONS.length;
	}

	/**
	 * Reinitializes the deck in the pre-determined order of its configuration.
	 */
	public void reset() {
		List<Card> cards = new ArrayList<>();
		for (String code : CONFIGURATIONS[aConfiguration].split(" ")) {
			cards.add(parse(code));
		}
		Collections.reverse(cards); // first card drawn must be on top
		aCards = new CardStack(cards);
	}

	private static Card parse(String pCode) {
		String rankCode = pCode.substring(0, pCode.length() - 1);
		Rank rank = switch (rankCode) {
			case "A" -> Rank.ACE;
			case "J" -> Rank.JACK;
			case "Q" -> Rank.QUEEN;
			case "K" -> Rank.KING;
			default -> Rank.values()[Integer.parseInt(rankCode) - 1];
		};
		Suit suit = switch (pCode.charAt(pCode.length() - 1)) {
			case 'C' -> Suit.CLUBS;
			case 'D' -> Suit.DIAMONDS;
			case 'H' -> Suit.HEARTS;
			default -> Suit.SPADES;
		};
		return Card.get(rank, suit);
	}

	public void push(Card pCard) {
		assert pCard != null;
		aCards.push(pCard);
	}

	public Card draw() {
		assert !isEmpty();
		return aCards.pop();
	}

	public boolean isEmpty() {
		return aCards.isEmpty();
	}
}