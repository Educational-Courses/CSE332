Chun-Wei Chen
CSE 332
Project 3 - Write-Up Questions

1. Chun-Wei Chen
2. Java API
3. I think the project took me three days to finish it. The 
   most difficult part to me was the trick to build grid in 
   order to answer the query in constant time. I think if 
   the numeric example given in the version 3 specification 
   start from lower left to upper right instead of upper 
   left to lower right would be better to understand since 
   there is a confusion between the numeric example and the 
   example with column/row position.
4. None
5. I tested my program version by version. I tested the 
   methods that find corners, answers the query, and build 
   grid in version 3-5. Since I created a class for each 
   version, so I could construct an object of each version 
   and test each method. I used two inputs to test my program. 
   One is 10 by 10 square with some positive number in each 
   square, and the number of column and row are both 5. The 
   other one is 9 by 9 square which has value 0 in some 
   square and non-zero positive value in other squares, and 
   the number of column and row are both 18. I tested the 
   case that query method takes invalid arguments. I also 
   thought about testing the case where census-block-group 
   falls exactly on the border of more than one grid position 
   in the second input. Although my program passed all the 
   tests I wrote. I don't think it covered that case.
6. Please Refer to ParallelFindCornersVariedSequentialCutOff.java, 
   VariedSequentialCutOffFindCornersTimer.java, 
   FindCornersSequentialCutOff1-3.txt (3 text files), 
   and FindCornersSequentialCutOff.pdf
   I ran the extra code I wrote three times to compute the 
   average runtime for finding corners with different cut-off. I 
   chose cut-off from 2, which is hi - lo < 2, to 220000. 
   Based on the result I got, it seems like setting the 
   sequrntial cut-off to be around 1000 to 2000 is the fastest. 
   However, I only chose the power of two and 220000 to be the 
   value of the cut-off and some other cut-off values also get 
   the very close, or I should said the same, average runtime, 
   it's really hard to tell if I range of the optimal sequential 
   cut-off I got in my conclusion is correct.
   Please refer to ParallelBuildGridVariedSequentialCutOff.java, 
   VariedSequentialCutOffBuildGridTimer.java, 
   BuildGridSequentialCutOff1-3.txt (3 text files), 
   and BuildGridSequentialCutOff.pdf
   I ran the extra code I wrote three times to compute the 
   average runtime for vuilding grid with different cut-off. The 
   grid size I used is 100 x 500. I chose cut-off from 2, which 
   is hi - lo < 2, to 220000. I only chose the power of two and 
   220000 to be the value of the cut-off for the experiment. 
   Based on the result I got, build grid finished fastest when 
   cut-off is 65536. The specification mentions that the input 
   contains around 220000 data points. 65536 is between 220000/4 
   and 220000/3. Therefore, I concluded that build grid runs the 
   fastest when the cut-off is 1/4 of the total data points in 
   the input.
7. Please refer to Vesion4And5Timer.java, 
   Version4&5Comparison1-3.txt (3 text files), 
   and Version4&5Comparison.pdf
   Intuitively, version 5 should work faster when the grid size 
   is big since threads have less probability to update the same 
   census block group in the grid and therefore threads have 
   more probability to update different groups at the same time. 
   Using the same way of arguing, version 4 should work faseter 
   when grid size is small. The result of the experiment 
   confirms my hypothesis.
8. Please refer to Version1And3QueryTimer.java, 
   Version2And4QueryTimer.java, input.txt, 
   Version1And3QueryTimeComparison1-3.txt (3 text files), 
   Version2And4QueryTimeComparison1-3.txt (3 text files), 
   Version1And3QueryTimeComparison.pdf, and 
   Version2And4QueryTimeComparison.pdf
   The experiment of comparison between the performance of 
   version 1 and version 3 shows that preprocessing is worth 
   after the 13th query. The experiment of comparison between 
   the performance of version 2 and version 4 shows that 
   preprocessing is worth after the 7th query.
9. N/A