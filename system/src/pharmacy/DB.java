package pharmacy;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

class DB {
	/**======================================== for connection ===================================**/
	
	private static Connection getConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy","root","");
		}
		catch(SQLException |ClassNotFoundException ex){
			return null;
		}
	}
	
	/**==========================================================================================**/
	
	/**responsible for returning values from the database the result can be null**/
	private static ResultSet select(String sql){
		try{
			return getConnection().createStatement().executeQuery(sql);
		}catch (SQLException | NullPointerException e) {
			return null;
		}
	}
	
	/**responsible for executing Queries if done returns true ,on the other hand false**/
	private static boolean editDataBase(String sql){
		try {
			getConnection().createStatement().execute(sql);
			return true;
		} catch (SQLException | NullPointerException e) {
			return false;
		}
	}

	/**Creates Medicine from the DB result**/
	private static Medicine createFromResult(ResultSet result){
		try {
			result.next();
			return new Medicine(result.getInt("ID"), result.getShort("Location"),result.getString("Name"), result.getString("Formula"), result.getString("Indications"),result.getString("AllowedPerson") , result.getShort("Quantity"), result.getFloat("Price"));
		} catch (SQLException | NullPointerException e) {
			return null;
		}
	}
	
	/**returns true if the password update query was executed **/
	public static String managerPassword() {
		try {
			ResultSet result= select("select password from pharmacy.system");
			result.next();
			return result.getString("password");
		} catch (SQLException |NullPointerException e) {
			return null;
		}
	}

	/**true if the password changed**/
	public static boolean changeAdminPassword(String newPass) {
		return editDataBase("update pharmacy.system set password = '"+newPass+"'");
	}
	
	/**returns true if the medicine with this id exists in the database**/
	public static boolean exists(int id){
		try {
			return select("select * from pharmacy.medicine where ID="+id+"").next();
		} catch (SQLException|NullPointerException e) {
			return false;
		}
	}
	
	/**true if the medicine is added**/
	public static boolean addMedicine(Medicine medicine) {
		return editDataBase("insert into pharmacy.medicine (ID,Location,Name,Formula,Indications,AllowedPerson,Quantity,Price) values("+medicine.getID()+","+medicine.getLocation()+",'"+medicine.getName()+"','"+medicine.getFormula()+"','"+medicine.getIndications()+"','"+medicine.getAllowedPerson()+"',"+medicine.getQuantity()+","+medicine.getPrice()+")");
	}
	
	/**return true if the info is updated**/
	public static boolean updateMedicine(Medicine medicine) {
		return editDataBase("update pharmacy.medicine set Location = "+medicine.getLocation()+",Price = "+medicine.getPrice()+" , Quantity="+medicine.getQuantity()+" where ID ="+medicine.getID()+"");
	}
	
	/**true if all the info about this medicine is deleted completely from the database**/
	public static boolean delete(int ID){
		return editDataBase("delete from pharmacy.medicine where ID ="+ID+"");
	}
	
	/**null if medicine with this id not found or the medicine if found**/
	public static Medicine findByID(int ID) {
		ResultSet result= select("select * from pharmacy.medicine where ID ="+ID+"");
		return createFromResult(result);
	}
	
	/**returns array of medicine with this string in it's name**/
	public static ArrayList<Medicine> findLetter(String search) {
		ResultSet result=select("select * from pharmacy.medicine where Name Like '%"+search+"%'");
		ArrayList<Medicine> list=new ArrayList<>();
		try{
			Medicine m;
			while((m=createFromResult(result))!=null){
				list.add(m);
			}
		} catch(NullPointerException e){
			return list;
		}
		return list;
	}

	/**returns medicine with this name**/
	public static Medicine findName(String search) {
		ResultSet result=select("select * from pharmacy.medicine where Name = '"+search+"'");
		try {
			return createFromResult(result);
		} catch (NullPointerException e) {}
		return null;
	}
	
	/**returns Array of Medicine has this string in it's indications**/
	public static ArrayList<Medicine> findIndication(String search) {
		ResultSet result=select("select * from pharmacy.medicine where Indications like '%"+search+"%'");
		ArrayList<Medicine> list=new ArrayList<>();
		try{
			Medicine m;
			while((m=createFromResult(result))!=null){
				list.add(m);
			}
		} catch(NullPointerException e){
			return list;
		}
		return list;
	}
	
	/**returns true if the quantity was subtracted but make sure u don't send it quantity makes the real quantity -**/
	static boolean sellMedicine(int ID, short Quantity){
		return editDataBase("update pharmacy.medicine set Quantity = Quantity -"+Quantity+" where ID = "+ID+"");
	}
	
	/**returns the Quantity of some medicine or -1 if not found**/
	public static short quantityOf(int ID){
		try {
			ResultSet result= select("select Quantity from pharmacy.medicine where ID="+ID+"");
			result.next();
			return result.getShort("Quantity");
		} catch (SQLException e) {
			return -1;
		}
	}
	
	/**returns empty ArrayList of medicine empty if no quantity less than 5**/
	static ArrayList<Medicine> LowQuantity() {
		ArrayList<Medicine> low= new ArrayList<>();
		ResultSet result =select("select * from pharmacy.medicine where Quantity < 5");
		try{
			Medicine m;
			while((m=createFromResult(result))!=null){
				low.add(m);
			}
		} catch(NullPointerException e){
			return low;
		}
		return low;
	}
	
	/**returns true if the activity is saved**/
	public static void saveActivity(Activity activity){
		editDataBase("insert into pharmacy.activity (action,date) values('"+activity.getAction()+"','"+activity.getDate()+"')");
	}
	
	/**returns the weekly report for admin**/
	public static ArrayList<Activity> weeklyReport() {
		ArrayList<Activity> activities=new ArrayList<>();
		for(int min=0;min<7;min++){
			ResultSet result=select("select * from pharmacy.activity where date = '"+LocalDateTime.now().minusDays(min).toLocalDate()+"'");
			try {
				while(result.next()){
					activities.add(new Activity(result.getString("action"),result.getString("date")));
				}
			} catch (SQLException |NullPointerException e) {}
		}
		return activities;
	}
		
}