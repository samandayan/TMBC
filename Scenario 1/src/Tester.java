import java.util.ArrayList;
import java.util.List;

public class Tester {

	int[] pins = new int[9];

	Tester(int[] pins) {
		this.pins = pins;
	}

	/**
	 * This method runs the check to find out which pin is the one that has
	 * unidentical weight. Since the pins can be in any random order a check is
	 * run to find the weight that the other eight pins have in common. In order
	 * to reduce the number of comparisons once the weight is found then the
	 * index of the second found pin is used to check with the other pins for
	 * weight check.
	 */
	public List<Integer> validate() {
		int comparator = 0;

		List<Integer> indices = new ArrayList<Integer>();

		int first = pins[0];
		int second = pins[1];
		int third = pins[2];

		if (first == second) {
			comparator = first;
			indices.add(0);
			indices.add(1);
		} else if (second == third) {
			comparator = second;
			indices.add(1);
			indices.add(2);
		} else if (first == third) {
			comparator = first;
			indices.add(0);
			indices.add(2);
		}
		/*
		 * Comparing of the elements is checked against the second index of
		 * indices to reduce the number of comparisons.
		 * 
		 * The order of this loop is o(n).
		 */
		for (int i = indices.get(1) + 1; i < pins.length; i++) {
			if (pins[i] == comparator) {
				indices.add(i);
			}
		}
		return indices;
	}
}