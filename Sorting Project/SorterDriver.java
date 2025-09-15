import java.util.ArrayList;

public class SorterDriver
{
	//use to run and test the methods from the Sorter class
	public static void main(String[] args)
	{
		int bubblesortcounter = 0;
		for (int i = 0; i < 1000000; i++)
		{
			int[] testarray = Sorter.generateRandomArray(8, 1, 100);
			Sorter.bubbleSort(testarray);
			if (Sorter.isSorted(testarray))
			{
				bubblesortcounter++;
			}
		}
		System.out.println("bubbleSort correctly sorted: " + bubblesortcounter + "/1000000");

		int selectionsortcounter = 0;
		for (int i = 0; i < 1000000; i++)
		{
			int[] testarray = Sorter.generateRandomArray(8, 1, 100);
			Sorter.selectionSort(testarray);
			if (Sorter.isSorted(testarray))
			{
				selectionsortcounter++;
			}
		}
		System.out.println("selectionSort correctly sorted: " + selectionsortcounter + "/1000000");

		int[] testCase = Sorter.generateRandomArray(8, 1, 100);
		Sorter.printArray(testCase);
	} //end of main method
}