package delivery;


public class Delivery{
	
	private String orderId;
	private String id;
	private String address;
	private String status;
	
	
	public Delivery(String orderId,String id,String address)
	{
		this.orderId=orderId;
		this.id=id;
		this.address=address;
		this.status="PENDING";
		
	}
	

	public String getid()
	{
		return id;
	}
	
	public String getaddress()
	{
		return address;
	}
	
	public String getstatus()
	{
		return status;
	}
	
	public void setstatus(String status)
	{
		this.status=status;
	}
	
}