/*
Laura Vail
COSC 600
4/4/18

Josephus Problem:
This program gets input from a user: the number of soldiers, a name for each soldier, and which position to eliminate. It eliminates the soldier at that position until there is only one soldier left, and prints that name.
*/

import java.util.*;

public class Josephus {
	
	public static void main(String args[]) {

		Scanner in = new Scanner(System.in);
		int num_soldiers;
		String next_soldier;
		String skipped_soldier;
		int eliminate;
		LinkedList<String> army = new LinkedList<String>();

		System.out.print("Enter number of soldiers: ");
		num_soldiers = in.nextInt();
		
		for (int i=0; i<num_soldiers; i++) {
			System.out.printf("Enter name for soldier %d: ",i+1);
			next_soldier = in.next();
			army.add(next_soldier);
		}

		System.out.print("Enter position to eliminate: ");
		eliminate = in.nextInt();
		
		//System.out.println(Arrays.toString(army.toArray()));
		//System.out.println(army.size());

		while (army.size() > 1) {
			for(int j=0; j<eliminate-1; j++) {
				skipped_soldier = army.get(0);
				army.removeFirst();
				army.add(skipped_soldier);
			}
			army.removeFirst();
			//System.out.println(Arrays.toString(army.toArray()));
		}
			
		/*for loop that removes and readds all soldiers until the one at position eliminate, which it only removes; only one time through army
		for(int j=0; j<eliminate-2; j++) {
			skipped_soldier = army.get(0);
			army.removeFirst();
			army.add(skipped_soldier);
		}
		army.removeFirst();
		*/

		System.out.println(Arrays.toString(army.toArray()));
	}
}
