package backoffice.client;

public class Payment{ 
	
	private String name; 
	  
	public Payment() {} 
	 
	public Payment (String name) { 
	    this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
} 
