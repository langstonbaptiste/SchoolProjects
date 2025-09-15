import java.util.ArrayList;
import java.util.Collections;

public class Trainer 
{
	private ArrayList<Pokemon> team;
	private String name;
	private int pokemonRemaining;
	private int teamSize;
	private int activePokemonIndex;

	public Trainer(String name, int teamSize) 
	{
        this.name = name;
        this.teamSize = teamSize;
        this.team = new ArrayList<>();
        generateTeam();
        this.pokemonRemaining = teamSize; 
        this.activePokemonIndex = 0;
	}

	public Pokemon getActivePokemon() 
	{
		return team.get(activePokemonIndex);
	}

	public String getName() 
	{
		return name;
	}

	public ArrayList<Pokemon> getTeam() 
	{
		return team;
	}

	public int getPokemonRemaining() 
	{
		return pokemonRemaining;
	}

	public void fainted() 
	{
		pokemonRemaining--;
	}

	public int getTeamSize() 
	{
		return teamSize;
	}

	public int getActiveIndex() 
	{
		return activePokemonIndex;
	}

	public void setActiveIndex(int newIndex) 
	{
		activePokemonIndex = newIndex;
	}

	// to-do: create teamSize number of Pokemon objects and
	// add them to the team instance var.
	// use the randomly generated indexes to get the pokemon name and type
	// (the name and type string arrays are parallel)
	private void generateTeam() 
	{
		String[] pokemonNames = 
		{
				"Eevee",
				"Charmander",
				"Squirtle",
				"Pikachu",
				"Bulbasaur",
				"Dewgong",
				"Machop",
				"Ekans",
				"Geodude",
				"Pidgey",
				"Abra",
				"Caterpie",
				"Onix",
				"Ghastly",
				"Dratini",
				"Umbreon",
				"Magnemite",
				"Jigglypuff"
		};
		// getTypeList() is a static method of the Type class
		String[] types = Type.getTypeList();
		ArrayList<Integer> indexes = getRandomIndexes(pokemonNames.length);
		for (int i = 0; i < teamSize; i++) 
		{
			int index = indexes.get(i);
			team.add(new Pokemon(pokemonNames[index], types[index]));
		}

	}

	// return an ArrayList of size teamSize of random, distinct integers
	// from 0 to (length-1)
	private ArrayList<Integer> getRandomIndexes(int length) 
	{
		ArrayList<Integer> indexes = new ArrayList<>(length);
		for (int i = 0; i < length; i++)
		{
			indexes.add(i);
		}
		Collections.shuffle(indexes);
		return indexes;
	}

	// the trainer toString() should match the screenshot in the google doc
	public String toString() 
	{
		String str = "~~~~~~~~~~~~\nTrainer " + name + "\n" + pokemonRemaining + "/" + teamSize
				+ " pokemon remaining\n~~~~~~~~~~~~";
		for (int i = 0; i < teamSize; i++) 
		{
			str += "\nPokemon " + i + ":\n" + team.get(i).toString() + "\n";
		}
		return str;
	}

	// sort of an abbreviated toString, used to give a more concise team overview
	public void printTeam() 
	{
        System.out.println(name + "'s current team status:");
        for (int i = 0; i < teamSize; i++) 
        {
            Pokemon p = team.get(i);
            System.out.println("Pokemon #" + i + ": " +
                    p.getName() + " - " + p.getTypeStr() +
                    " - " + p.getCurrentHP() + "/" + p.getMaxHP() + " HP");
            if (i == activePokemonIndex) 
            {
                System.out.print(" (active)");
            }
            if (p.getAttackBoost() > 1) 
            {
                System.out.print(" **Attack Boost = " + p.getAttackBoost());
            }
            System.out.println();
		}
	}
}