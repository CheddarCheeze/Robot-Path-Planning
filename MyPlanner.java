
import java.awt.Graphics;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class MyPlanner {
    Model m;
    MyPlanner(Model _m, Agent _a) {
        m = _m;
    }
    
  MyState uniform_cost_search(MyState startState, MyState goalState, float lowestCost, boolean useAstar) {
    aStarComparator starcomp = new aStarComparator();
    CostComparator costcomp = new CostComparator();
    PriorityQueue<MyState> frontier;
    if(useAstar) {
        frontier = new PriorityQueue<MyState>(starcomp);
    }
    else {
        frontier = new PriorityQueue<MyState>(costcomp);
        lowestCost = 0;
    }
    StateComparator comp = new StateComparator();
    TreeSet<MyState> beenthere = new TreeSet<MyState>(comp);
    MyState s;
    MyState child;
    MyState oldchild;
    int stepSize = 5;
    double acost = 0.0;
    double aStarModifier = 0.3;
    startState.cost = 0.0;
    startState.parent = null;
    beenthere.add(startState);
    frontier.add(startState);
    while(frontier.size() > 0) {
      s = frontier.remove(); // get lowest-cost state
      
      if(s.state[0] > (goalState.state[0] - 3) && s.state[0] < (goalState.state[0] + 3) &&
         s.state[1] > (goalState.state[1] - 3) && s.state[1] < (goalState.state[1] + 3)) {
//        while(s.parent.parent != null) {
//            s = s.parent;
//        }
//        m.setDestination(s.state[0], s.state[1]);
        m.frontier = frontier;
        return s;
      }
      
      // Right, left, up, down
      for(int i = 0; i < s.state.length; i++) {
        child = new MyState(s);
        child.state[i] += stepSize;
        child.heuristic = lowestCost * getDist(child,goalState) * aStarModifier;
        if(child.state[0] > 1196) {
            child.state[0] = 1196;
        }
        if(child.state[1] >= 596) {
            child.state[1] = 596;
        }
        child.parent = s;
        acost = action_cost(child); // compute the cost of the action
        if(beenthere.contains(child)) {
          oldchild = beenthere.floor(child);
          if(s.cost + acost + child.heuristic < oldchild.cost + oldchild.heuristic) {
            oldchild.cost = s.cost + acost;
            oldchild.parent = s;
          }
        }
        else {
          child.cost = s.cost + acost;
          child.parent = s;
          frontier.add(child);
          beenthere.add(child);
        }
        
        child = new MyState(s);
        child.state[i] -= stepSize;
        child.heuristic = lowestCost * getDist(child,goalState) * aStarModifier;
        if(child.state[i] <= 0) {
            child.state[i] = 1;
        }
        child.parent = s;
        acost = action_cost(child); // compute the cost of the action
        if(beenthere.contains(child)) {
          oldchild = beenthere.floor(child);
          if(s.cost + acost + child.heuristic < oldchild.cost + oldchild.heuristic) {
            oldchild.cost = s.cost + acost;
            oldchild.parent = s;
          }
        }
        else {
          child.cost = s.cost + acost;
          child.parent = s;
          frontier.add(child);
          beenthere.add(child);
        }
      }
      
      // Four diagnol directions
      for(int i = 0; i < 4; i++) {
        child = new MyState(s);
        if (i == 0) {
            child.state[0] += stepSize;
            child.state[1] += stepSize;
        }
        else if (i == 1) {
            child.state[0] += stepSize;
            child.state[1] -= stepSize;
        }
        else if (i == 2) {
            child.state[0] -= stepSize;
            child.state[1] += stepSize;
        }
        else {
            child.state[0] -= stepSize;
            child.state[1] -= stepSize;
        }
        child.heuristic = lowestCost * getDist(child,goalState) * aStarModifier;
        
        if(child.state[0] > 1195) {
            child.state[0] = 1195;
        }
        if(child.state[1] >= 595) {
            child.state[1] = 595;
        }
        if(child.state[0] <= 0) {
            child.state[0] = 10;
        }
        if(child.state[1] <= 0) {
            child.state[1] = 10;
        }
        child.parent = s;
        acost = action_cost(child); // compute the cost of the action
        if(beenthere.contains(child)) {
          oldchild = beenthere.floor(child);
          if(s.cost + acost + child.heuristic < oldchild.cost + oldchild.heuristic) {
            oldchild.cost = s.cost + acost;
            oldchild.parent = s;
          }
        }
        else {
          child.cost = s.cost + acost;
          child.parent = s;
          frontier.add(child);
          beenthere.add(child);
        }
      }
      
      //System.out.println(frontier.size());
    }
    throw new RuntimeException("There is no path to the goal");
  }
  
  double getDist(MyState a, MyState b) {
      return Math.sqrt((Math.pow((b.state[0] - a.state[0]), 2) + Math.pow((b.state[1] - a.state[1]),2)));
  }
  
  double action_cost(MyState s) {
      return (double)(1/m.getTravelSpeed((float)(s.state[0]),(float)s.state[1]));
  }
}