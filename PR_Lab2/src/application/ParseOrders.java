package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javafx.util.converter.BigDecimalStringConverter;

public class ParseOrders {
	private String id;
	private BigDecimal total;
	private int category_id;
	private String created;
	
	List<String> orders = new ArrayList<String> ();
	
	public String getId(int i) {
		if (i>0) {
			int idIndex = orders.get(i).indexOf(",",0);
	    	id = orders.get(i).substring(0,idIndex);
			}else id = "-2";
		return this.id;
	}
	
	public BigDecimal getTotal(int i) {
		if (i>0) {
		    	int totalIndex = orders.get(i).indexOf(",",orders.get(i).indexOf(",",0))+1;
		    	this.total = new BigDecimal(orders.get(i).substring(totalIndex,orders.get(i).indexOf(",",totalIndex)));
	    	} else this.total = new BigDecimal(-2);
		return this.total;
	}
	
	public int getCategoryID(int i) {
		int nameIndex = orders.get(i).indexOf(",",orders.get(i).indexOf(",",0))+1;
	    	int startCategoryIdIndex = orders.get(i).indexOf(",",nameIndex) +1;
	    	int endCategoryIdIndex = orders.get(i).indexOf(",",startCategoryIdIndex);
	    	
		if (i>0) {
			category_id = Integer.valueOf(orders.get(i).substring(startCategoryIdIndex, endCategoryIdIndex));
		}
		return this.category_id;
	}
	
	public String getCreated(int i) {
		int nameIndex = orders.get(i).indexOf(",",orders.get(i).indexOf(",",0))+1;
	    	int startCategoryIdIndex = orders.get(i).indexOf(",",orders.get(i).indexOf(",",nameIndex) +1);
	    	int endCategoryIdIndex = orders.get(i).length();
	    	//System.out.println(startCategoryIdIndex);
	    	//System.out.println(endCategoryIdIndex);
		if(i>0) {
			this.created = orders.get(i).substring(startCategoryIdIndex+1, endCategoryIdIndex);
		}
		return this.created;
	}
	
	public List<String> getOrders(){
    		return orders;
    }
	
	
	
	public List<String> getOrders(InputStream body) throws IOException {
		
		BufferedReader in   = new BufferedReader (new InputStreamReader (body));
	    String line;
	    int i=0;
	    while ((line = in.readLine()) != null) {
	        i++;
	        orders.add(line);
	    }
		System.out.println(i);
		return orders;
    }
	
}
