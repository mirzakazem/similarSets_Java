import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class A_priori_Kazem{

        public static void main(String[] args)
    {
	
      	String fileName="order_products_20000.csv"; 
        int support=3;
	double confidence=0.75;
        
        
        
        String order; //to read first lines' values
        String product;//to read second lines' values 
        int tempCount;//to count the products
        int numOfProducts;
        
	Hashtable<String, Integer> productsCount = new Hashtable<String, Integer>();//store count of each product
        Hashtable<String, Integer> ordersCount = new Hashtable<String, Integer>();//store count of each order
       
        //reading the file
	try {   
	    System.out.println("Processing just began");     
            Scanner scanner = new Scanner(new File(fileName));
           
            
            // to skip the first line
            scanner.next();
            scanner.next();
            scanner.next();
            
            
                        
            int counter=0;
           while(scanner.hasNext()){//start reading entries from the file
               
             order= scanner.next() ;//orderID
              
             
             scanner.next(); // to skip the comma
              
             product= scanner.next() ;// productID
             
             
            //count the product occurrences 
            if(productsCount.get(product)==null){
            productsCount.put(product,1);
            }
            else{
            tempCount= productsCount.get(product)+1 ;
            
            productsCount.put(product,tempCount);
            }
            
            //count the orders occurrences 
            if(ordersCount.get(order)==null){
            ordersCount.put(order,1);
            }
            else{
            tempCount= ordersCount.get(order)+1 ;
            
            ordersCount.put(order,tempCount);
            }
            
            counter++;  
            }// end reading from file
            
           numOfProducts = productsCount.size();

// second reading to build the Array
        Scanner scanner_2 = new Scanner(new File(fileName));
        int index=0;
        counter=0;
        String Matrix[] = new String [ordersCount.size()];
      
       int orderOld=0;
       int orderNew;
       int product_2 = 0;
       
        // to skip the first line
            scanner_2.next();
            scanner_2.next();
            scanner_2.next();
            
        while(scanner_2.hasNext())//start reading entries from the file
        {
            orderNew= Integer.parseInt(scanner_2.next()) ;//orderID
            scanner_2.next(); // to skip the comma
            product_2= Integer.parseInt(scanner_2.next()) ;//productID
            
            if(counter == 0)
            {
               orderOld=orderNew;
               Matrix[index]= Integer.toString(product_2) ;
            }
            
            else
                {
                   // products belong for the same order
                    if(orderNew == orderOld)
                    {
                       Matrix[index]+= " ,"+ product_2;
                    } 
                    else // products belong for the next order
                    {
                        index++;
                        Matrix[index]= Integer.toString(product_2) ;

                    }

                }
            
            
          
            orderOld=orderNew;
            counter ++;
        }
        // end the 2nd read
           
           
           
           //first cleaning
       //keep products that exist at least as the support
       
        // get keys() from Hashtable and iterate
        Enumeration<String> enumeration = productsCount.keys();
        
        //System.out.println("The size before trimming "+productsCount.size());
       // iterate using enumeration object
        while(enumeration.hasMoreElements()) {
 
            String key = enumeration.nextElement();
            if(productsCount.get(key)<support)
            {
                productsCount.remove(key);
            }
          
        }
        
        
        
        // hash map to array
        String [] supportedProducts = productsCount.keySet().toArray(new String[productsCount.size()]);
        
        //clear products hashmap
        productsCount.clear();
        
        //loop over the array to collect all possible pair combinations & store them in the hashMap
        for (int i=0;i<supportedProducts.length -1;i++)
        {
            for(int j=i+1;j<supportedProducts.length;j++)
            {
                String TempPair = supportedProducts[i]+","+supportedProducts[j] ;
                productsCount.put(TempPair,0);
            }
        }
        
        
        
        //check if the pair exist in the Matrix
        
        // get keys() from Hashtable and iterate
        enumeration = productsCount.keys();
        
       
       // iterate using enumeration object
        while(enumeration.hasMoreElements()) 
        
        {
 
            String key = enumeration.nextElement();
            
            
            String firstP = key.substring(0, key.indexOf(","));
            String secondP = key.substring(key.indexOf(",")+1);;
            
            for(int i=0;i<Matrix.length;i++)
            {
                if(Matrix[i].contains(firstP)&& Matrix[i].contains(secondP))
                {
                   int newValue = productsCount.get(key)+1;
                   productsCount.put(key,newValue);  
                }
            }
            
        }
        
        
        // second cleaning (for pairs)
        // get keys() from Hashtable and iterate
        enumeration = productsCount.keys();
                
      //  System.out.println("The size before trimming "+productsCount.size());
       // iterate using enumeration object
        while(enumeration.hasMoreElements()) {
 
            String key = enumeration.nextElement();
            if(productsCount.get(key)<support)
            {
                productsCount.remove(key);
            }
           
        }
        
        //convert pairs in the hashmap to array
        // new array
        String [] supportedProducts_temp= new String [productsCount.size()*2] ;
        
        // get keys() from Hashtable and iterate
        enumeration = productsCount.keys();
        
       // iterate using enumeration object
       
       counter =0;
        while(enumeration.hasMoreElements()) 
        {
            String key = enumeration.nextElement();
            
            String firstP = key.substring(0, key.indexOf(","));
            supportedProducts_temp[counter]=firstP;
            //System.out.println("supportedProducts_temp[counter] : "+supportedProducts_temp[counter]);
            counter++;
            
            
            String secondP = key.substring(key.indexOf(",")+1);
            supportedProducts_temp[counter]=secondP;
           //System.out.println("supportedProducts_temp[counter] : "+supportedProducts_temp[counter]);
           counter++;
        }
        
        
        Set<String> supportedProducts_2_set = new HashSet<String>(); 
        Collections.addAll(supportedProducts_2_set, supportedProducts_temp);
        
        String[] supportedProducts_2 = supportedProducts_2_set.toArray(new String[0]);
        
        //clear products hashmap
        productsCount.clear();
        
        //loop over the array to collect all possible triplet combinations & store them in the hashMap
        for (int i=0;i<supportedProducts_2.length -2;i++)
        {
            for(int j=i+2;j<supportedProducts_2.length;j++)
            {
                String TempPair = supportedProducts_2[i]+","+supportedProducts_2[i+1]+","+supportedProducts_2[j] ;
                productsCount.put(TempPair,0);
            }
        }
        
        //check if the triplet exist in the Matrix
        
        // get keys() from Hashtable and iterate
        enumeration = productsCount.keys();
        
       
       // iterate using enumeration object
        while(enumeration.hasMoreElements()) 
        
        {
 
            String key = enumeration.nextElement();
            
            
            int firstIndex =key.indexOf(",");
            int secondIndex =key.indexOf(",",firstIndex + 1);
            
            String firstP = key.substring(0, firstIndex);
            String secondP = key.substring( firstIndex + 1,secondIndex);
            
            String thirdP = key.substring( secondIndex +1);
            
            for(int i=0;i<Matrix.length;i++)
            {
                if(Matrix[i].contains(firstP)&& Matrix[i].contains(secondP)&& Matrix[i].contains(thirdP))
                {
                   int newValue = productsCount.get(key)+1;
                   productsCount.put(key,newValue);  
                }
            }
            
        }
        
        // third cleaning (for triplets)
        // get keys() from Hashtable and iterate
        enumeration = productsCount.keys();
                
      //  System.out.println("The size before trimming "+productsCount.size());
       // iterate using enumeration object
        while(enumeration.hasMoreElements()) {
 
            String key = enumeration.nextElement();
            if(productsCount.get(key)<support)
            {
                productsCount.remove(key);
            }
           
        }
        
        System.out.println(" ");
        System.out.println("---------------------------------------------");
        System.out.println("# File name                                 : "+fileName);
        System.out.println("# Support  threshold                        : "+support);
        System.out.println("# confidence threshold                      : "+confidence);
        System.out.println("# Number of orders                          : "+ordersCount.size());
        System.out.println("# Number of all products                    : "+numOfProducts);
        System.out.println("# Number of supported products  (triplets)  : "+productsCount.size());
      
 
        System.out.println("---------------------------------------------");
        
        
        //analyze the association rules:
        
        // get keys() from Hashtable and iterate
        enumeration = productsCount.keys();
       // iterate using enumeration object
        while(enumeration.hasMoreElements()) 
        
        {
           String key = enumeration.nextElement();
           
           int firstIndex =key.indexOf(",");
           int secondIndex =key.indexOf(",",firstIndex + 1);
            
           String firstP = key.substring(0, firstIndex);
           String secondP = key.substring( firstIndex + 1,secondIndex);
            
           String thirdP = key.substring( secondIndex +1);
           
           
           
           double numerator=0;
           double first_denominator=0;
           double second_denominator=0;
           double third_denominator=0;
           
           for(int i=0;i<Matrix.length;i++)
            {
                if(Matrix[i].contains(firstP) && Matrix[i].contains(secondP))
                {
                   first_denominator++;  
                }
                if(Matrix[i].contains(firstP) && Matrix[i].contains(thirdP))
                {
                   second_denominator++;  
                }
                if( Matrix[i].contains(secondP) && Matrix[i].contains(thirdP))
                {
                   third_denominator++;  
                }
                
                if(Matrix[i].contains(firstP)&& Matrix[i].contains(secondP)&& Matrix[i].contains(thirdP))
                {
                   numerator++;  
                }
                
            }
          
            
          if(numerator/first_denominator>=confidence)
          {
              System.out.println("Association rule: "+firstP+" , "+secondP+" --> "+thirdP+" C.Level: "+numerator/first_denominator);
          }
          if(numerator/second_denominator>=confidence)
          {
              System.out.println("Association rule: "+firstP+" , "+thirdP+" --> "+secondP+" C.Level: "+numerator/second_denominator);
          }
          if(numerator/third_denominator>=confidence)
          {
              System.out.println("Association rule: "+secondP+" , "+thirdP+" --> "+firstP+" C.Level: "+numerator/third_denominator);
          }
        }
        System.out.println(" ");  
           
        
	}
	catch(FileNotFoundException fnfe)
        {
        }

	
	 
        

	
}

}
