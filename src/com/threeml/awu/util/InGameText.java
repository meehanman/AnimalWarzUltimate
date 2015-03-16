package com.threeml.awu.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class to generate random (and not so random) strings to be used in user
 * notifications Cannot create InGameText object, methods are static, strings
 * are private
 * 
 * @author Mary-Jane References: Thanks to
 *         (http://www.you-can-be-funny.com/Euphemisms-For-Death.html) for all
 *         the death euphemisms lol
 */
public class InGameText {
	/**
	 * Enum holds a variety of strings to be used when a player dies
	 * 
	 * @author Mary-Jane
	 * 
	 */
	private enum DeathText {
		checkedOut(" has checked out..."), dancedLastDance(
				" has danced its last dance..."), deaderThanADoornail(
				" is deader than a doornail."), givenUpTheGhost(
				" has given up the ghost."), meetMaker(
				" has gone to meet its maker!"), pushingDaisies(
				" is pushing up daisies now."), betterPlace(
				" is in a better place now..."), kickedTheBucket(
				" has kicked the bucket!"), leftTheBuilding(
				" has left the building!"), oxygenHabit(
				" has finally kicked their oxygen habit!"), unableToBreatheList(
				" is on the Unable To Breathe List!"), passedAway(
				" has passed away."), ceasedToBe(" has ceased to be..."), terminated(
				" has been terminated."), elysianFields(
				" is wandering the Elysian Fields now..."), sixFeetUnder(
				" has gone six feet under."), wormFood(
				" has become worm food (cannibalism ftw!)"), doctorWorm(
				" has died. Too bad worms don't regenerate... DOOO WEEE OOOO"), valhalla(
				" has gone to Valhalla"), mortalCoil(
				" has shuffled off this mortal coil..."), zombieApocalypse(
				" has gone to fight in the Zombie Apocalypse!"), ancestors(
				" is feasting with its ancestors."), greatSlumber(
				" has become one with The Will."), legend(
				" lives on only in legend."), circleOfLife(
				" has entered the next stage in the Circle of Life."), insurance(
				" has faked its death as part of an insurance scam."), ;

		private String text;
		// http://stackoverflow.com/questions/1972392/java-pick-a-random-value-from-an-enum
		private static final List<DeathText> VALUES = Collections
				.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();

		DeathText(String s) {
			this.text = s;
		}

		private String getText() {
			return text;
		}

		/** Get a random text from the enum */
		private static DeathText randomText() {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}

	/**
	 * Enum holds a variety of strings to be used when a player dies in the
	 * water
	 * 
	 * @author Mary-Jane
	 * 
	 */
	private enum SeaDeathText {
		sleepingWithFish(" is sleeping with the fish..."), davyJones(
				" has gone to Davy Jones's Locker."), styx(
				" has taken a one way trip down the River Styx."), captainWormerica(
				" has fallen in the sea, maybe we'll find it in 70 years?"), bubbling(
				"'s life is bubbling away!"), venus(
				" will not be reborn from the sea foam, like Venus. Alas!"), ;

		private String text;
		private static final List<SeaDeathText> VALUES = Collections
				.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();

		SeaDeathText(String s) {
			this.text = s;
		}

		private String getText() {
			return text;
		}

		/** Get a random text from the enum */
		private static SeaDeathText randomText() {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}

	/**
	 * Enum holds a variety of strings to be used for droppables
	 * 
	 * @author Mary-Jane
	 * 
	 */
	private enum DroppableText {
		hasGained(" has gained "), health(" health."),

		;

		private String text;

		DroppableText(String s) {
			this.text = s;
		}

		private String getText() {
			return text;
		}
	}

	/**
	 * Enum holds a variety of strings to be used for general purposes
	 * 
	 * @author Mary-Jane
	 * 
	 */
	private enum General {
		gameOver("Game over!"), teamWins(" wins, congratulations!"), surrendered(
				" has surrendered."), teamSucks(" sucks!"), yourTurn(
				", it's your turn!"), ;

		private String text;

		General(String s) {
			this.text = s;
		}

		private String getText() {
			return text;
		}
	}

	/** Generates a random string to display when player dies */
	public static String generateDeathText(String playerName) {
		return playerName + DeathText.randomText().getText();
	}

	/** Generates a random string to display when character dies in water */
	public static String generateSeaDeathText(String playerName) {
		return playerName + SeaDeathText.randomText().getText();
	}

	/** Generates a string for when team surrenders */
	public static String generateSurrenderText(String surrenderedTeamName,
			String winningTeamName) {
		return surrenderedTeamName + General.surrendered.getText() + " "
				+ winningTeamName + General.teamWins.getText();
	}

	/** Generates string for when game is over to display winning team */
	public static String generateWinText(String winningTeamName) {
		return winningTeamName + General.teamWins.getText();
	}

	/** Generates a string for when player collects health pack */
	public static String generateCollectedHealthText(String playerName,
			int health) {
		return playerName + DroppableText.hasGained.getText() + health
				+ DroppableText.health.getText();
	}

	/** Generates string for when active player changes */
	public static String generateChangePlayerText(String playerName) {
		return playerName + General.yourTurn.getText();
	}
}
