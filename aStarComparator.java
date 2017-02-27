
import java.util.Comparator;

public class aStarComparator implements Comparator<MyState> {
    public int compare(MyState a, MyState b)
    {
        if((a.cost + a.heuristic) < (b.cost + b.heuristic))
            return -1;
        else if((a.cost + a.heuristic) > (b.cost + b.heuristic))
            return 1;
        return 0;
    }
}
