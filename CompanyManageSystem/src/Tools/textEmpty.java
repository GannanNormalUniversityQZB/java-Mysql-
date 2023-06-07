package Tools;

public class textEmpty {
	
	public static boolean textEmpty(String...a) {
			boolean isEmpty=false;
			for(String b:a) {
				if(b.equals("")) {
					isEmpty=true;
					break;
				}
			}
			return isEmpty;
		}
}
