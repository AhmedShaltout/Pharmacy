package pharmacy;

import java.util.ArrayList;

public abstract class Person {
	
	/**return medicine with this name**/
	public static Medicine searchMedicineByName(String search){
		return DB.findName(search);
	}
	
	/**returns ArrayList of the medicine for this illness**/
	public static ArrayList<Medicine> searchMedicineByIllness(String search){
		return DB.findIndication(search);
	}
	
	/**returns array list of the medicine what has this word in it's name**/
	public static ArrayList<Medicine> searchMedicineByLetters(String search){
		return DB.findLetter(search);
	}
	
	/**returns Specific medicine with this ID if found or null if not**/
	public static Medicine searchMedicine(int search){
		return DB.findByID(search);
	}

	public static ArrayList<Medicine> searchMedicineByFormula(String formula) {
		return new ArrayList<Medicine>();
	}
}
