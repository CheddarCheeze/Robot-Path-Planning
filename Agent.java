import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.SwingUtilities;

class Agent {

    MyState step;
    MyState goal;
    float goalX = 100, goalY = 100; 
    float lowestCost;
    boolean finishedSearch = false;
    boolean rightClick = false;
    
	void drawPlan(Graphics g, Model m) {
            g.setColor(Color.red);
            
            if(step.parent != null) {
                while(step.parent.parent != null) {
                    g.drawLine((int)step.state[0], (int)step.state[1], (int)step.parent.state[0], (int)step.parent.state[1]);
                    step = step.parent;
                }
            }
            while(!m.frontier.isEmpty()) {
                MyState circle = m.frontier.remove();
                g.drawOval(circle.state[0],circle.state[1],5,5);
            }
	}

	void update(Model m)
	{
            if(!finishedSearch) {
                findLowestCost(m);
            }
            
            Controller c = m.getController();
            MyPlanner planner = new MyPlanner(m,this);
            while(true)
            {
                MouseEvent e = c.nextMouseEvent();
                
                if(!(m.getX() > (goalX - 3) && m.getX() < (goalX + 3) &&
                m.getY() > (goalY - 3) && m.getY() < (goalY + 3))) {
                    
                    if(step.parent != null) {
                        while(step.parent.parent != null) {
                            step = step.parent;
                        }
                        m.setDestination(step.state[0], step.state[1]);
                    }
                    
                    step = planner.uniform_cost_search(new MyState((int)m.getX(),(int)m.getY()), new MyState((int)goalX,(int)goalY),lowestCost, rightClick);
                }
                else {
                    step = new MyState((int)m.getX(),(int)m.getY());
                }
                
                
                //m.setDestination(step.state[0], step.state[1]);
                
                if(e == null) {
                    break;
                }
                goalX = e.getX();
                goalY = e.getY();
                //m.setDestination(e.getX(), e.getY());
            }
	}
        
        public void findLowestCost(Model m) {
            lowestCost = 10000;
            float temp;
            for(int i = 0; i < 1200; i++) {
                for(int j = 0; j < 600; j++) {
                    temp = 1/m.getTravelSpeed(i, j);
                    if(temp < lowestCost) {
                        lowestCost = temp;
                    }
                }
            }
            finishedSearch = true;
        }

	public static void main(String[] args) throws Exception
	{
            Controller.playGame();
	}
}
