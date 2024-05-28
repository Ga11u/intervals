# Intervals Assigment

This is a program that processes two sets of integer intervals: include intervals and exclude intervals.
The program takes these two inputs from the command line.

The sets of intervals can be given in any order, and they may be empty or overlapping. The program
processes these inputs and outputs the result of taking all the includes and "removing" the excludes.

The output is given as non-overlapping intervals in a sorted order. The intervals contain integers only.

The program has a complexity of O(nlog(n))

To use the program run the following command in a terminal:

```sh
javac *.java
java IntervalsAssignment
```
Example 1:
Includes: 10-100
Excludes: 20-30
Output should be: 10-19, 31-100

Example 2:
Includes: 50-5000, 10-100
Excludes: 
Output: 10-5000

Example 3:
Includes: 200-300, 50-150
Excludes: 95-205
Output: 50-94, 206-300

Example 4:
Includes: 200-300, 10-100, 400-500
Excludes: 410-420, 95-205, 100-150
Output: 10-94, 206-300, 400-409, 421-500

