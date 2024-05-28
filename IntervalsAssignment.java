/**
 * This is a program that processes two sets of integer intervals: include intervals and exclude intervals.
 * The program takes these two inputs from the command line.
 *
 * <p>
 * The sets of intervals can be given in any order, and they may be empty or overlapping. The program
 * processes these inputs and outputs the result of taking all the includes and "removing" the excludes.
 * </p>
 *
 * <p>
 * The output is given as non-overlapping intervals in a sorted order. The intervals contain integers only.
 * </p>
 *
 * @author Marc
 * @version 1.0
 *
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class IntervalsAssignment { // O(nlog(n))

    public static void main(String[] args) { 
        String input;
        ArrayList<Interval> includes;
        ArrayList<Interval> excludes;
        ArrayList<Interval> output;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter include intervals (format: start-end, start-end, ...) in one line:");
            input = scanner.nextLine();
            includes = readInput(input); // 2n
            System.out.println("Enter exclude intervals (format: start-end, start-end, ...) in one line:");
            input = scanner.nextLine();
            excludes = readInput(input); // 2n
        }
        
        includes = mergeIntervals(includes); // n + nlog(n)
        excludes = mergeIntervals(excludes); // n + nlog(n)
        output = intervalDifference(includes, excludes); // 2n

        printResult(output); //n
    }

    /**
     * Reads a String representing a list of Interval and returns an ArrayList of Interval.
     * 
     * @param input The input is required to be a correct interval of integers in the form of X-Y where X <= Y. 
     * The interval can be empty. A list of three intervals will be represented as X-Y, X-Y, X-Y
     * @return An ArrayList of Intervals representing the parsed input intervals
     * @see Interval
     */
    public static ArrayList<Interval> readInput(String input){
        ArrayList<Interval> intervalsList = new ArrayList<>();
        String[] intervals, parts;
        if(!input.isEmpty()) {
            intervals = input.split(", ");
            for (String interval : intervals) {
                parts = interval.split("-");
                intervalsList.add(new Interval(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
        }
        return intervalsList;
    }

    /**
     * Sorts and merges the intervals within a list of intervals, returning a list of intervals without overlaps.
     * @param intervals An ArrayList of valid Intervals
     * @return An ArrayList of Intervals without overals.
     * @see Interval
     */
    public static ArrayList<Interval> mergeIntervals(ArrayList<Interval> intervals) {
        ArrayList<Interval> result = new ArrayList<>();
        if (intervals.size() <= 1) {
            result = intervals;
        }
        else {
            // Sort
            Collections.sort(intervals); // O(nlog(n))

            // Merge
            int start = intervals.get(0).getStart();
            int end = intervals.get(0).getEnd();
            for (Interval interval : intervals) { // O(n)
                if (interval.getStart() <= end) 
                    // Case [{]} --> [} 
                    // Intervals overlap. E.g., 10-20, 15-30 --> 10-30
                    end = Math.max(end, interval.getEnd());
                else {  
                    // Case [] {} --> {} 
                    // The previous interval is added to the result and the exploration continues with the new one              
                    result.add(new Interval(start, end));
                    start = interval.getStart();
                    end = interval.getEnd();
                }
            }
            // The last merged interval is added
            result.add(new Interval(start, end));
        }
        return result;
    }

    /**
     * Removes the excluded intervals from a list of intervals, returing a list of intervals without the excluded intervals.
     * @param includes An ArrayList of intervals to keep.
     * @param excludes An ArrayList of intervals to exclude.
     * @return An ArrayList of intervals without the excluded intervals
     * @see Interval
     */
    public static ArrayList<Interval> intervalDifference(ArrayList<Interval> includes, ArrayList<Interval> excludes) {
        ArrayList<Interval> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < includes.size() && j < excludes.size()) { // O(2n)
            if (includes.get(i).getEnd() < excludes.get(j).getStart()) {
                // Case: Included interval to the left of the excluded interval (no overlap)
                result.add(includes.get(i));
                i++; // Check the next included interval
            } else if (includes.get(i).getStart() > excludes.get(j).getEnd()) {
                // Case: Included interval to the right of the excluded interval (no overlap)
                j++; // Check the next excluded interval
            } else {
                // Overalp
                if (includes.get(i).getStart() < excludes.get(j).getStart()) {
                    // Case [{}] or [{]} --> [{
                    // Add the left side of the overlap to the result
                    result.add(new Interval(includes.get(i).getStart(), excludes.get(j).getStart() - 1));
                }
                if (includes.get(i).getEnd() > excludes.get(j).getEnd()) {
                    // Case {[}] or [{}] --> }]
                    // Modify the include interval so it is now the right side of the overlap
                    includes.get(i).setStart(excludes.get(j).getEnd() + 1);
                } else {
                    // Case if the remaining right side of the overal is ]}, it is discarded
                    i++;
                }
            }
        }
        // There are no more excluding intervals to the left of the including intervals
        // The remaining including intervals are added to the result
        while (i < includes.size()) {
            result.add(includes.get(i));
            i++;
        }
        return result;
    }

    /**
     * Prints a list of Intervals as an Output
     * @param result A list of Intervals. It can be empty.
     * @see Interval
     */
    public static void printResult(ArrayList<Interval> result){
        System.out.print("Output: ");
        if(!result.isEmpty()){
            System.out.print(result.get(0));
            if(result.size() > 1) {
                for(int i = 1; i < result.size(); i++) {
                    System.out.print(", ");
                    System.out.print(result.get(i));
                }
            }
        }
        System.out.println();
    }
}