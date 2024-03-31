package model.activeComponent;

import model.monitor.barrier.CyclicBarrier;
import model.passiveComponent.agent.task.ParallelTask;

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
//			System.out.println("initrun");
			for (ParallelTask p: tasks) {
//				System.out.println("taskinit");
				p.run();
//				System.out.println("taskrunned");

			}
//			this.tasks.forEach(Runnable::run);
//			System.out.println("endrun");
			try {
				this.barrier.hitAndWaitAll();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
