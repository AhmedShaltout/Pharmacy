package pharmacy;

import java.util.ArrayList;

import fx.Admin;

public abstract class Manager extends Person{
	static boolean x=true;
	
	/**returns true if the password entered matches the one in the database**/
	public static boolean login(String password){
		return password.equals(DB.managerPassword());
	}
	
	/**returns true if the password was changed correctly**/
	public static boolean changePassword(String newPass){
		return DB.changeAdminPassword(newPass);
	}
	
	/**returns true if the medicine was add correctly to the database or false if not**/
	public static boolean add(Medicine medicine){
		if(!DB.exists(medicine.getID()))/**returns true if the medicine already in the database**/{
			saveWithoutDisturbing("You have added new medicine : ", medicine);
			return DB.addMedicine(medicine);
		}
		return false;
	}
	
	/**returns true if the medicine data is updated // the things what can be updated is the location of all the collection of this medicine or its price**/
	public static boolean update(Medicine medicine){
		if(DB.exists(medicine.getID())){
			saveWithoutDisturbing("You have Updated medicine : " , medicine);
			return DB.updateMedicine(medicine);
		}
		return false;
	}
	
	/**deletes all the Quantity of a specific medicine if exists so if u want to delete specific Quantity or add pls use adderSubtractor method**/
	public static boolean delete(int ID){
		if(DB.exists(ID)){
			saveWithoutDisturbing("You have deleted medicine : ", DB.findByID(ID));
			return DB.delete(ID);
		}
		return false;
	}
	
	
	
	/**returns Array of activities of what happened for last 7 days**/
	public static ArrayList<Activity> getWeeklyReport(){
		return DB.weeklyReport();
	}

	/**sends list of the medicine with low quantity**/
	public static void QuantityCheckerForAdmin(){
		new Thread(new Runnable(){
			public void run() {
				ArrayList<Medicine> low=new ArrayList<>();
				low=DB.LowQuantity();
				if(!low.isEmpty()){
					if(x)
						Admin.colorLowQuantity(x);
					x=false;
					Admin.getMedicine(low);
				}
				else{
					Admin.stopColoringAndDelTable(low,false);
					x=true;
				}
			}
		}).start();
	}
	
	private static void saveWithoutDisturbing(String message,Medicine medicine){
		new Thread(new Runnable() {
			@Override
			public void run() {
				new Activity(message + medicine.toString());
			}
		}).start();
	}

	public static Medicine bringN_P_QC(Short s, Integer i) {
		Medicine medicine=DB.findByID(i);
		if(medicine.getQuantity()>=s){
			DB.sellMedicine(i, s);
			new Activity(""+s+" packet(s) was sold of "+medicine.getName()+" total price = "+s*medicine.getPrice()+"");
			return medicine;
			
		}
		return null;
	}
}