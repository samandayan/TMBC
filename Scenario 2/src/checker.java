public class checker {
	public static void main(String[] args) {
		String version = "1.2.3";
		for (String value : version.split("\\."))
			System.out.println(value);
	}
}