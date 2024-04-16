package part1.logic.activeComponent;

import part1.logic.monitor.barrier.CyclicBarrier;
import part1.logic.passiveComponent.agent.task.ParallelTask;
import java.util.List;

public class SimulationWorker extends Thread {

	private final List<ParallelTask> tasks;
	private final int step;
	private final CyclicBarrier barrier;

	public SimulationWorker(List<ParallelTask> tasks, int step, CyclicBarrier barrier) {
		this.tasks = tasks;
		this.step = step;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		for (int i = 0; i < step; i++) {
			for (ParallelTask p: tasks) {
				p.run();
			}
			try {
				this.barrier.hitAndWaitAll();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
