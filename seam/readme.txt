Programming Assignment 7: Seam Carving


/* *****************************************************************************
 *  Describe concisely your algorithm to find a horizontal (or vertical)
 *  seam.
 **************************************************************************** */

To find a vertical (or horizontal) seam, I iteratively calculate the shortest seam
Path for each vertex, storing its distanceTo and edgeTo, by checking its 3 parent
Vertecies. Using this approach, I can see which vertex at the bottom row/column
Of the picture has the smallest distanceTo and trace its edgeTo array to find the
Path.

/* *****************************************************************************
 *  Describe what makes an image suitable to the seam-carving approach
 *  (in terms of preserving the content and structure of the original
 *  image, without introducing visual artifacts). Describe an image that
 *  would not work well.
 **************************************************************************** */

The seam-carving approach requires pictures to have clear energy differences, 
A clear object and non-important foreground (i.e picture of a house in a field).
To preserve the original image, it requires important details to have a high energy
Value, while the less important seams blend into the picture. In this sense,
A picture that would not work well with this algorithm is images with fine details
And a lack of distinction between object and foreground (i.e. a consistent, high amount
Of energy). These irregular pictures could include irregular shapes or complex textures. 


/* *****************************************************************************
 *  Perform computational experiments to estimate the running time to reduce
 *  a W-by-H image by one column and one row (i.e., one call each to
 *  findVerticalSeam(), removeVerticalSeam(), findHorizontalSeam(), and
 *  removeHorizontalSeam()). Use a "doubling" hypothesis, where you
 *  successively increase either W or H by a constant multiplicative
 *  factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one.
 **************************************************************************** */

(keep W constant)
 W = 2000
 multiplicative factor (for H) = 2

 H           time (seconds)      ratio       log ratio
------------------------------------------------------
250		0.372		N/A		N/A
500		0.641		1.72		0.78
1000		1.023		1.60		0.68
2000		1.746		1.71		0.78
4000		3.098		1.77		0.82
8000		6.182		2.00		1


(keep H constant)
 H = 2000
 multiplicative factor (for W) = 2

 W           time (seconds)      ratio       log ratio
------------------------------------------------------
250		0.365		N/A		N/A
500		0.587		1.60		0.68
1000		1.034		1.76		0.82
2000		1.639		1.59		0.67
4000		3.001		1.83		0.87
8000		5.828		1.94		0.96



/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula 
 *  (using tilde notation) for the running time (in seconds) as a function
 *  of both W and H, such as
 *
 *       ~ 5.3*10^-8 * W^5.1 * H^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */


Running time (in seconds) to find and remove one horizontal seam and one
vertical seam, as a function of both W and H:


    ~ 7.285 * 10^-4 W^1 * 7.725 * 10^-4 H^1
       _______________________________________


In order to calculate the running time, I determined the ratio via the doubling
Method. With this same method, I could also extract the log ratio, which
Appeared to converge to 1. With this ratio found (and thus the value of b),
I could use the power law to find the coefficient of a via
Time = a * n^b 



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */




/* *****************************************************************************
 *  Describe any serious problems you encountered.                    
 **************************************************************************** */




/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **************************************************************************** */
