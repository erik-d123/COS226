Programming Assignment 3: Autocomplete


/* *****************************************************************************
 *  Describe how your firstIndexOf() method in BinarySearchDeluxe.java
 *  finds the first index of a key that is equal to the search key.
 **************************************************************************** */

The firstIndexOf() method in Binary Search utilizes a traditional binary search 
till it finds a "hit" or the key. Then, the binary search stores the last index 
of a hit, setting the high equal to the mid element and conducting more binary 
searches till it reaches an element that is not the target.



/* *****************************************************************************
 *  Identify which sorting algorithm (if any) that your program uses in the
 *  Autocomplete constructor and instance methods. Choose from the following
 *  options:
 *
 *    none, selection sort, insertion sort, mergesort, quicksort, heapsort
 *
 *  If you are using an optimized implementation, such as Arrays.sort(),
 *  select the principal algorithm.
 **************************************************************************** */

Autocomplete() Utilized array.sort, which utilizes a version of timsort algorithm 

allMatches() Utilized array.sort, which utilizes a version of timsort algorithm 

numberOfMatches() Utilized array.sort, which utilizes a version of timsort algorithm 


/* *****************************************************************************
 *  How many compares (in the worst case) does each of the operations in the
 *  Autocomplete data type make, as a function of both the number of terms n
 *  and the number of matching terms m? Use Big Theta notation to simplify
 *  your answers.
 *
 *  Recall that with Big Theta notation, you should discard both the
 *  leading coefficients and lower-order terms, e.g., Theta(m^2 + m log n).
 **************************************************************************** */

Autocomplete():     Theta(nlogn)

allMatches():       Theta(mlogm + logn)

numberOfMatches():  Theta(logn)




/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
N/A


/* *****************************************************************************
 *  Describe any serious problems you encountered.                    
 **************************************************************************** */
N/A



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **************************************************************************** */

