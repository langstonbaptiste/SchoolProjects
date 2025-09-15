
public class Type 
{
	private String typeName;

	private static String[] typeList = 
	{
			"Normal",
			"Fire",
			"Water",
			"Electric",
			"Grass",
			"Ice",
			"Fighting",
			"Poison",
			"Ground",
			"Flying",
			"Psychic",
			"Bug",
			"Rock",
			"Ghost",
			"Dragon",
			"Dark",
			"Steel",
			"Fairy"
	};

	// rows = attacker type
	// columns = defender type
	private double[][] typeChart = 
	{
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0, 1, 1, 0.5, 1 },
			{ 1, 0.5, 0.5, 1, 2, 2, 1, 1, 1, 1, 1, 2, 0.5, 1, 1, 1, 1, 1 },
			{ 1, 2, 0.5, 1, 0.5, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0.5, 1, 1, 1 },
			{ 1, 1, 2, 0.5, 0.5, 1, 1, 1, 0, 2, 1, 1, 1, 1, 0.5, 1, 1, 1 },
			{ 1, 0.5, 2, 1, 0.5, 1, 1, 0.5, 2, 0.5, 1, 0.5, 2, 1, 0.5, 1, 0.5, 1 },
			{ 1, 0.5, 0.5, 1, 2, 0.5, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1, 0.5, 1 },
			{ 2, 1, 1, 1, 1, 2, 1, 0.5, 1, 0.5, 0.5, 0.5, 2, 0, 1, 2, 2, 0.5 },
			{ 1, 1, 1, 1, 2, 1, 1, 0.5, 0.5, 1, 1, 1, 0.5, 0.5, 1, 1, 0, 2 },
			{ 1, 2, 1, 2, 0.5, 1, 1, 2, 1, 0, 1, 0.5, 2, 1, 1, 1, 2, 1 },
			{ 1, 1, 1, 0.5, 2, 1, 2, 1, 1, 1, 1, 2, 0.5, 1, 1, 1, 0.5, 1 },
			{ 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 0.5, 1, 1, 1, 1, 0, 0.5, 1 },
			{ 1, 0.5, 1, 1, 2, 1, 0.5, 0.5, 1, 0.5, 2, 1, 1, 0.5, 1, 2, 0.5, 0.5 },
			{ 1, 2, 1, 1, 1, 2, 0.5, 1, 0.5, 2, 1, 2, 1, 1, 1, 1, 0.5, 1 },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0.5, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0.5, 0 },
			{ 1, 1, 1, 1, 1, 1, 0.5, 1, 1, 1, 2, 1, 1, 2, 1, 0.5, 1, 0.5 },
			{ 1, 0.5, 0.5, 0.5, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 0.5, 2 },
			{ 1, 0.5, 1, 1, 1, 1, 2, 0.5, 1, 1, 1, 1, 1, 1, 2, 2, 0.5, 1 },
	};

	public Type(String type) 
	{
		typeName = type;
	}

	public String toString() 
	{
		return typeName;
	}

	// static methods can be called without having to
	// create an instance of the class
	public static String[] getTypeList() 
	{
		return typeList;
	}

	// given a string, find and return the index of the string in
	// the typeList array, or -1 if not found
	public static int findIndex(String aType) 
	{
		for (int i = 0; i < typeList.length; i++) 
		{
			if (typeList[i].equals(aType))
				return i;
		}
		return -1;
	}

	// returns a value from the typeChart 2D array where the Type object calling the
	// method
	// is the attacker and the Type object parameter is the defender
	public double getEffectiveness(Type other) 
	{
		/*
		 * When you call this method in another class, it will look like:
		 * type1.getEffectiveness(type2) -> this.method(other)
		 * 
		 * type1 = this
		 * type2 = other
		 * 
		 * To access the typeName private instance variable of each of those two
		 * objects:
		 * type1 -> this.typeName OR just typeName
		 * type2 -> other.typeName
		 * 
		 * Note that other.typeName ONLY works to access the private instance variable
		 * because this method is actually a part of the Type class
		 * (private restricts access in classes other than this one)
		 */
        int attackerIndex = findIndex(typeName);
        int defenderIndex = findIndex(other.typeName);

        if (attackerIndex == -1 || defenderIndex == -1) 
        {
            throw new IllegalArgumentException("Invalid type name.");
        }

        return typeChart[attackerIndex][defenderIndex];
	}

}