import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class IntervalsAssignment { // O(nlog(n))
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) { //9n + nlog(n)
        ArrayList<Interval> includes = new ArrayList<>();
        ArrayList<Interval> excludes = new ArrayList<>();
        ArrayList<Interval> result = new ArrayList<>();

        System.out.println("Enter include intervals (format: start-end, start-end, ...) in one line:");
        includes = readInput(); // 2n
        System.out.println("Enter exclude intervals (format: start-end, start-end, ...) in one line:");
        excludes = readInput(); // 2n

        includes = mergeIntervals(includes); // n + nlog(n)
        excludes = mergeIntervals(excludes); // n + nlog(n)
        result = intervalDifference(includes, excludes); // 2n

        printResult(result); //n
    }

    public static ArrayList<Interval> readInput(){
        ArrayList<Interval> intervalsList = new ArrayList<>();
        String input;
        String[] intervals, parts;
        input = scanner.nextLine();
        if(!input.isEmpty()) {
            intervals = input.split(", ");
            for (String interval : intervals) {
                parts = interval.split("-");
                intervalsList.add(new Interval(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
        }
        return intervalsList;
    }

    public static ArrayList<Interval> mergeIntervals(ArrayList<Interval> intervals) {
        ArrayList<Interval> result = new ArrayList<>();
        if (intervals.size() <= 1) {
            result = intervals;
        }
        else {
            Collections.sort(intervals); //O(nlog(n))
            int start = intervals.get(0).getStart();
            int end = intervals.get(0).getEnd();
            for (Interval interval : intervals) { //O(n)
                if (interval.getStart() <= end) 
                    end = Math.max(end, interval.getEnd());
                else {                     
                    result.add(new Interval(start, end));
                    start = interval.getStart();
                    end = interval.getEnd();
                }
            }
            result.add(new Interval(start, end));
        }
        return result;
    }

    public static ArrayList<Interval> intervalDifference(ArrayList<Interval> includes, ArrayList<Interval> excludes) {
        ArrayList<Interval> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < includes.size() && j < excludes.size()) { // O(2n)
            if (includes.get(i).getEnd() < excludes.get(j).getStart()) {
                result.add(includes.get(i));
                i++;
            } else if (includes.get(i).getStart() > excludes.get(j).getEnd()) {
                j++;
            } else {
                if (includes.get(i).getStart() < excludes.get(j).getStart()) {
                    result.add(new Interval(includes.get(i).getStart(), excludes.get(j).getStart() - 1));
                }
                if (includes.get(i).getEnd() > excludes.get(j).getEnd()) {
                    includes.get(i).addStart(excludes.get(j).getEnd() + 1);
                } else {
                    i++;
                }
            }
        }
        while (i < includes.size()) {
            result.add(includes.get(i));
            i++;
        }
        return result;
    }

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