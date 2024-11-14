Programming Assignment 1: Percolation

Answer these questions after you implement your solution.

/* *****************************************************************************
 *  Describe the data structures (i.e., instance variables) you used to
 *  implement the Percolation API.
 **************************************************************************** */
I utilized a WeightedQuickUnion UF from the algs4 library. Additionally, I used 
n, openSites, and boolean[][] states as additional instance variables. N was 
the input value from the user, opposites was a counter that incremented each 
till the open() function was run in order to keep track of the number of open 
sites, and the boolean[][] states corresponded to a 2D map of the grid and 
kept track of whether a location was blocked or open.

/* *****************************************************************************
 *  Briefly describe the algorithms you used to implement each method in
 *  the Percolation API.
 **************************************************************************** */
open():
Utilized boolean[][] array to check if site is blocked.
Then, checked if up, down, left, and right neighbors are open and in 
the grid borders. If so, I used union on the two points. Lastly, if the site was
 in the top or bottom row, I used union to connect it to the virtual top 
or bottom, respectively. 
isOpen(): 
Returned the state of the site in the boolean[][] array (true or false)
isFull():
Utilized the find function to determine if the root of the site is equal 
to the virtual top cell.
numberOfOpenSites():
Returned the value of integer opposites, which has tracked each time a site 
was opened(in open() function)
percolates():
Utilized the find function to determine if the root of the virtual top and 
bottom are equal.

/* *****************************************************************************
 *  First, implement Percolation using QuickFindUF.
 *  What is the largest value of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?

 *
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
50		0.2270
100		1.743
125		4.1450	
150		8.30
200		25.9330	
245		59.9860


/* *****************************************************************************
 *  Describe the strategy you used for selecting the values of n.
 **************************************************************************** */

I started with a smaller value of n and gradually incremented the value. 
As I reached times where a difference could be noticed, i.e. n = 150, 
I was more selective in increasing n since the order of growth was very fast 
with a small change in N.

/* *****************************************************************************
 *  Next, implement Percolation using WeightedQuickUnionUF.
 *  What is the largest value of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 *
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
125		0.16	
250		0.4400	
1000		6.17
1500		15.5460
1750		24.5790	
2250		54.86 

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

The time for the function increases dramatically with small increases in N, so 
trying to utilize a grid with n > 2500 will take significant time. Additionally, 
backwash remains an issue in the program due to the implementation of a 
virtual top and bottom, which was a trade off in order to increase the
time efficiency of the program.



/* *****************************************************************************
 *  Describe any serious problems you encountered.                    
 **************************************************************************** */

N/A


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **************************************************************************** */
