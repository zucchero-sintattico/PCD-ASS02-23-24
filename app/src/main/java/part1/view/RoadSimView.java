package part1.view;

import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.environment.Environment;
import part1.logic.passiveComponent.environment.road.Road;
import part1.logic.passiveComponent.environment.RoadsEnvironment;
import part1.logic.passiveComponent.environment.trafficLight.TrafficLight;
import part1.logic.simulation.listeners.SimulationListener;
import part1.utils.Vector2D;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RoadSimView extends JFrame implements SimulationListener {

	private RoadSimViewPanel panel;
	private static final int CAR_DRAW_SIZE = 10;
	
	public RoadSimView() {
		super("RoadSim View");
		setSize(1500,600);
			
		panel = new RoadSimViewPanel(1500,600); 
		panel.setSize(1500, 600);

		JPanel cp = new JPanel();
		LayoutManager layout = new BorderLayout();
		cp.setLayout(layout);
		cp.add(BorderLayout.CENTER,panel);
		setContentPane(cp);		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
	}
	
	public void display() {
		SwingUtilities.invokeLater(() -> {
			this.setVisible(true);
		});
	}

	@Override
	public void notifyInit(int t, List<AbstractCarAgent> agents, Environment env) {}

	@Override
	public void notifyStepDone(int t,List<AbstractCarAgent> agents, Environment env) {
		var e = ((RoadsEnvironment) env);
		panel.update(e.getRoads(), e.getCarAgents(), e.getTrafficLights());
	}
	
	
	class RoadSimViewPanel extends JPanel {
		
		List<AbstractCarAgent> cars;
		List<Road> roads;
		List<TrafficLight> sems;
		
		public RoadSimViewPanel(int w, int h){
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);   
	        Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.clearRect(0,0,this.getWidth(),this.getHeight());
			
			if (roads != null) {
				for (var r: roads) {
					g2.drawLine((int)r.getStartPoint().x(), (int)r.getStartPoint().y(), (int)r.getEndPoint().x(), (int)r.getEndPoint().y());
				}
			}
			
			if (sems != null) {
				for (var s: sems) {
					if (s.isGreen()) {
						g.setColor(new Color(0, 255, 0, 255));
					} else if (s.isRed()) {
						g.setColor(new Color(255, 0, 0, 255));
					} else {
						g.setColor(new Color(255, 255, 0, 255));
					}
					g2.fillRect((int)(s.getPosition().x()-5), (int)(s.getPosition().y()-5), 10, 10);
				}
			}
			int i = 0;
			Color c1 = new Color(255,0,0);
			Color c2 = new Color(0,255,0);
			//g.setColor(new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)));

			if (cars != null) {
				for (var c: cars) {
					double pos = c.getPosition();
					Road r = c.getRoad();
					Vector2D dir = Vector2D.makeV2d(r.getStartPoint(), r.getEndPoint()).getNormalized().mul(pos);
					g.setColor(c2);
					if (i == 0) {
						g.setColor(c1);
						i++;
					}
					g2.fillOval((int)(r.getStartPoint().x() + dir.x() - CAR_DRAW_SIZE/2), (int)(r.getStartPoint().y() + dir.y() - CAR_DRAW_SIZE/2), CAR_DRAW_SIZE , CAR_DRAW_SIZE);
				}
			}
  	   }
	
	   public void update(List<Road> roads, 
			   			  List<AbstractCarAgent> cars,
			   			List<TrafficLight> sems) {
		   this.roads = roads;
		   this.cars = cars;
		   this.sems = sems;
		   repaint();
	   }
	}


	@Override
	public void notifySimulationEnded() {}

	@Override
	public void notifyStat(double averageSpeed) {}
}
