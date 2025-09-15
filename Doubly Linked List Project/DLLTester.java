public class DLLTester
{
	public static void main(String[] args) 
	{
		/*
		FOR EACH METHOD,TEST all of the scenarios in which the method could
		be called before moving on to the next one.

		An example of testing all of the scenarios (including edge cases)
		for addToFront() has been left in this file for you.
		*/
		System.out.println("Testing addToFront");
		//---------------------------
		/*
		3 cases to test if handled correctly:
		1) if data argument is null 
		2) when list is currently empty 
		(adding the very first element to the list)
		3) when already has at least one element
		*/
		DoublyLinkedList<String> testList = new DoublyLinkedList<>();
		//testList.addToFront(null); //case 1
		testList.addToFront("C"); //case 2
		testList.addToFront("A"); //case 3
		testList.addToBack("B");
		testList.addAtIndex(1, "D"); //also case 3
		System.out.println(testList);

		
	}
}












