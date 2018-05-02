package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ParseCategories {
	private int id;
    private String name;
    private int category_id;
    int startIndexId, endIndexId, startIndexName, endIndexName, startIndexCategory, endIndexCategori;

    
	List<String> categories = new ArrayList<String> ();
	
	public ParseCategories() {
		
	}
	
	public ParseCategories(List<String> categories){
		
	}
	
	
	
	public int getId(int i) {
		if (i>0) {
		int idIndex = categories.get(i).indexOf(",",0);
    	id = Integer.valueOf(categories.get(i).substring(0,idIndex));
		}else id = -2;
        return id;
    }

    public String getName(int i) {
    	if (i>0) {
    	int nameIndex = categories.get(i).indexOf(",",categories.get(i).indexOf(",",0))+1;
    	name = categories.get(i).substring(nameIndex,categories.get(i).indexOf(",",nameIndex));
    	} else name = "-2";
        return name;
    }

    public int getCategory_id(int i) {
	    	int nameIndex = categories.get(i).indexOf(",",categories.get(i).indexOf(",",0))+1;
	    	int startCategoryIdIndex = categories.get(i).indexOf(",",nameIndex) +1;
	    	int endCategoryIdIndex = categories.get(i).length();
	    	//System.out.println(startCategoryIdIndex);
	    	//System.out.println(endCategoryIdIndex);
    	if(i == 0) {
    		category_id = -2;
    	}
    	else if(startCategoryIdIndex == endCategoryIdIndex)
    		category_id = -1;
    	else {
    		category_id = Integer.valueOf(categories.get(i).substring(startCategoryIdIndex, endCategoryIdIndex));
    	}
    	return category_id;
    }
    
    public List<String> getCategories(){
    		return categories;
    }
	
	public List<String> getCategories(InputStream body) throws IOException {
		
		BufferedReader in   = new BufferedReader (new InputStreamReader (body));
	    String line;
	    while ((line = in.readLine()) != null) {
	    		
	        categories.add(line);
	    }
	    
		return categories;
    }
	
	
}
