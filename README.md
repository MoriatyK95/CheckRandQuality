# CheckRandQuality

invoke the program via commandline 
e.g. javac CheckRandQuality.java
     java CheckRandQuality filename.txt analyze
     (2nd argument calculates the standard deviation, mean value and mean all value)
     

Binary Search Tree data structure to check the quality of RANDOMNESS of a file of data (integers) sepereated by line by taking the mean value (sum of all distinct node's value divided by total nodes) , mean ALL value (sum of all value including the duplicate node divided by total number of nodes), and the standard deviation (SD). As well as checking how many times a specific integer has appeared in the file and its occurrences.

Note: invoke this program through command line, with the first argument being the filename of list of integers, and the subsequent arguments parsed to the command line in integers separated by a space (e.g. 1 3 7 8) to check how many times a specific integer appeared and the order or occurrences associated with it. If the second argument is "analyze", program calculates the mean value, mean all value, and the standard deviation of the datas)
