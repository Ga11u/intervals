public class Interval implements Comparable<Interval>{
    private int start, end;

    Interval(int start, int end){
        this.start = start;
        this.end = end;
    }

    public void addStart(int start){
        this.start = start;
    }

    public int getStart(){
        return this.start;
    }

    public void addEnd(int end){
        this.end = end;
    }

    public int getEnd(){
        return this.end;
    }

    @Override
    public String toString() {
        return this.start + "-" +this.end;
    }

    @Override
    public int compareTo(Interval i){
        return Integer.compare(this.getStart(), i.getStart());
    }

}