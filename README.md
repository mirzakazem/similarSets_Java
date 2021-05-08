# similarSets_Java

1- Get the association rules for triplets ( a,b --> c)
2- Support used = 3 
3- Confidence level = 0.75
4- dataset consists of 20000 rows, 1886 orders and 7338 products.


## How to use it:

1- compile the java file:
  javac .\A_priori_Kazem.java

2- run the java file:
   java .\A_priori_Kazem.java

note: on my pc (i7 cpu & 8 GB RAM) it takes around 2 min to complete processing. 
   
## How it works:

1- Read the orders and the products from the file, and store them in a two Hashtable (orders & products)

2- Build an Array which stores all the products related to the same order toghether

3- Clean the products hashtable, so only products that meet the support remain.

4- combine the rest of the products into groups of two. (take all the possible possibilities)

5- repeat the step number three, but instead of looking for single product, looking for the pairs

6- repeat the step number four, however now we form group of three

7- then keep the group that meet the support

8- finally, check the confidence level, and keep those who meet it
