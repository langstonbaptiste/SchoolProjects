import java.util.Scanner;

public class PokemonRunner {
	private static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {

		Type fire = new Type("Fire");
		Type water = new Type("Water");

		assertEqual(Type.findIndex("Electric"), 3);
		assertEqual(Type.findIndex("Type A Personality"), -1);
		assertEqual(fire.getEffectiveness(water), 0.5);
		assertEqual(fire.getEffectiveness(fire), 0.5);
		assertEqual(water.getEffectiveness(fire), 2.0);

		// Pokemon p1 = new Pokemon("joe", "Fire");
		// Pokemon p2 = new Pokemon("moe", "Water");
		// p1.attack(p2);
		// p2.attack(p1);
		// assertEqual(p1.getCurrentHP() < p1.getMaxHP(), true);
		// assertEqual(p2.getCurrentHP() < p2.getMaxHP(), true);

		Trainer t1 = new Trainer("A", 2);
		Trainer t2 = new Trainer("B", 2);
		// System.out.println(t1);
		// System.out.println(t2);

		battle(t1, t2);
	}

	public static void assertEqual(Object a, Object b) {
		if (!a.equals(b)) {
			throw new RuntimeException("Assertion failed, got " + a + ", expected " + b);
		}
	}

	// returns true of current trainer's last pokemon
	// just fained
	private static boolean checkBattleEnd(Trainer current, Trainer opponent) {
		if (current.getPokemonRemaining() == 0) {
			System.out.print(opponent.getName() +
					" wins the battle!");
			return true;
		} else {
			switchPokemon(current);
			return false;
		}
	}

	static String getTurnInputBase(Trainer t) {
		String input = s.nextLine().trim();
		if (!(input.equals("1") || input.equals("2") || input.equals("3"))) {
			System.out.println("Invalid input: " + input);
			return getTurnInputBase(t);
		}

		if (input.equals("3") && t.getPokemonRemaining() <= 1) {
			System.out.println("You don't have any other available pokemon to switch to");
			return getTurnInputBase(t);
		}

		return input;

	}

	private static String getTurnInput(Trainer t) {
		String options = "1: Attack\n" +
				"2: Boost Attack\n" +
				"3: Switch Pokemon";

		// Your code to complete the method below here
		// ---------------
		System.out.println(t.getName() + ":");
		System.out.println(options);
		String input = getTurnInputBase(t);
		// ---------------
		System.out.println("\n\n\n\n\n\n\n\n");
		return input;
	}

	private static void switchPokemon(Trainer t) {
		System.out.println(t.getName() + " - SWITCHING POKEMON");
		int currentIndex = t.getActiveIndex();
		boolean validChoice = false;
		while (!validChoice) {
			t.printTeam();
			System.out.println("Enter the index of a pokemon with more than 0 HP remaining");
			int choice;
			try {

				choice = Integer.parseInt(s.nextLine());
			} catch (Exception e) {
				System.out.println("\nInput a number.\n");
				continue;
			}

			if (choice == currentIndex) {
				System.out.println("\nPick a pokemon that's not already active.\n");

			} else if (choice >= 0 && choice < t.getTeamSize() && t.getTeam().get(choice).getCurrentHP() > 0) {
				t.getTeam().get(currentIndex).resetAttackBoost();
				t.setActiveIndex(choice);

				System.out.println(t.getTeam().get(choice).getName() + " I choose you!");
				validChoice = true;
			} else {
				System.out.println("\nInvalid choice, try again.\n");
			}
		}
	}

	private static void battle(Trainer t1, Trainer t2) {
		// update this to true when the battle ends to end the loop
		boolean battleOver = false;
		int turnNum = 1;
		while (!battleOver) {
			System.out.println("Turn #" + turnNum);

			/*
			 * -speed only matters when BOTH trainers attack
			 * (speed ties go to trainer 1)
			 * -switches happen before attacks, so the newly
			 * switched in pokemon takes the damage
			 * -doesn't matter if attack boost happens before or
			 * after damage
			 */

			// t1 input formatting
			t1.printTeam();
			System.out.println(".........");
			t2.printTeam();
			System.out.println(".........");
			String option1 = getTurnInput(t1);
			// --------------------------
			// t2 input formatting
			t1.printTeam();
			System.out.println(".........");
			t2.printTeam();
			System.out.println(".........");
			String option2 = getTurnInput(t2);

			// ---------------------------
			if (option1.equals("3") || option2.equals("3")) {
				if (option1.equals("3"))
					switchPokemon(t1);
				if (option2.equals("3"))
					switchPokemon(t2);
			}

			Pokemon p1 = t1.getActivePokemon();
			Pokemon p2 = t2.getActivePokemon();

			int spd1 = option1.equals("1") ? p1.getSpeed() : -1;
			int spd2 = option2.equals("1") ? p2.getSpeed() : -1;

			if (option1.equals("2"))
				p1.boostAttack();
			if (option2.equals("2"))
				p2.boostAttack();

			if (spd1 != -1 || spd2 != -1) {
				Trainer firstTrainer;
				Pokemon firstPokemon;
				boolean firstAttacking;
				Trainer secondTrainer;
				Pokemon secondPokemon;
				boolean secondAttacking;

				if (spd1 > spd2) {
					firstTrainer = t1;
					secondTrainer = t2;
					firstAttacking = option1.equals("1");
					secondAttacking = option2.equals("1");
				} else {
					firstTrainer = t2;
					secondTrainer = t1;
					firstAttacking = option2.equals("1");
					secondAttacking = option1.equals("1");
				}
				firstPokemon = firstTrainer.getActivePokemon();
				secondPokemon = secondTrainer.getActivePokemon();

				if (firstAttacking) {
					firstPokemon.attack(secondPokemon);
					if (secondPokemon.getCurrentHP() <= 0) {
						secondTrainer.fainted();
						if (checkBattleEnd(secondTrainer, firstTrainer))
							return;
					}

				}
				if (secondAttacking && secondPokemon.getCurrentHP() > 0) {
					secondPokemon.attack(firstPokemon);
					if (firstPokemon.getCurrentHP() <= 0) {
						firstTrainer.fainted();
						if (checkBattleEnd(firstTrainer, secondTrainer))
							return;
					}
				}
			}

			// ---------------------------
			// don't edit after this line
			turnNum++;
			System.out.println("(press enter to continue to next turn)");
			s.nextLine();
		}
	}

}
