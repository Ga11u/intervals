
/**
 * Interval is a class for representing closed intervals of integers.  
 * It encapsulates the information of the start and end of the interval
 * and provides methods to compare and print intervals. 
 * 
 * <p>
 * An Interval has a start and an end, both of which are integers. 
 * The start of the interval is less than or equal to the end.
 * </p>
 * 
 * @author Marc
 * @version 1.0
 */
public class Interval implements Comparable<Interval>{
    private int start;
    private int end;

    /**
     * Constructs a new Interval with the specified start and end.
     *
     * @param start The start of the interval. Must be less than or equal to end.
     * @param end The end of the interval. Must be greater than or equal to start.
     */
    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return this.start + "-" +this.end;
    }

    /**
     * Compares two intervals numerically <b>only</b> by the start value.
     * @param i Interval to be compare to.
     */
    @Override
    public int compareTo(Interval i){
        return Integer.compare(this.getStart(), i.getStart());
    }


}