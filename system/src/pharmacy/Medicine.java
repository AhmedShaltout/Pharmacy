package pharmacy;

public class Medicine {
	private Integer ID;
	private Short Location;
	private String Name ; 
	private String Formula;
	private String Indications ; 
	private String AllowedPerson;
	private Short Quantity;
	private Float Price;
	private static final String Update= "Upadte";
	private static final String Delete= "Delete";
	
	public Medicine(int ID,short Location,String Name,String Formula,String Indications,String AllowedPerson,short Quantity,float Price){
		this.ID=ID;
		this.Location=Location;
		this.Name=Name;
		this.Formula=Formula;
		this.Indications=Indications;
		this.AllowedPerson=AllowedPerson;
		this.Quantity=Quantity;
		this.Price=Price;
	}
	
	public Float getPrice() {
		return this.Price;
	}
	
	public Short getQuantity() {
		return this.Quantity;
	}
	
	public Integer getID() {
		return this.ID;
	}
	
	public Short getLocation() {
		return this.Location;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public String getFormula() {
		return this.Formula;
	}
	
	public String getIndications() {
		return this.Indications;
	}
	
	public String getAllowedPerson() {
		return this.AllowedPerson;
	}
	
	public String getUpdate() {
		return Update;
	}
	
	public void setLocation(Short location) {
		this.Location = location;
	}

	public void setQuantity(Short quantity) {
		this.Quantity = quantity;
	}

	public void setPrice(Float price) {
		this.Price = price;
	}
	
	public String getDelete(){
		return Delete;
	}
	@Override
	public String toString() {
		return "[ID=" + ID + ", Name=" + Name + ", Location=" + Location + ", Quantity=" + Quantity+ ", Price=" + Price + "]";
	}
	@Override
	public boolean equals(Object obj) {
		Medicine m=(Medicine)obj;
		return this.ID==m.getID();
	}
	
}
