public class Bowler {
	public static void main(String[] pins) {
		Tester tester = new Tester(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10 });
		System.out.println(tester.validate());
	}
}