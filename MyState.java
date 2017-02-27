public class MyState {
  public double cost;
  double heuristic;
  MyState parent;
  int[] state = new int[2];

  MyState(int x, int y) {
      //cost = _cost;
      //parent = par;
      state[0] = x;
      state[1] = y;
      heuristic = 0;
  }
  
  MyState(MyState s) {
      cost = s.cost;
      parent = s.parent;
      state[0] = s.state[0];
      state[1] = s.state[1];
      heuristic = 0;
  }
}