package part1.logic.simulation;

import part1.logic.activeComponent.SimulationWorker;
import part1.logic.monitor.barrier.CyclicBarrier;
import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.agent.task.ParallelTask;
import java.util.ArrayList;
import java.util.List;

public class TaskSplitter {

	public TaskSplitter(int numOfThread, List<AbstractCarAgent> listOFAgent, int numOfStep,
						CyclicBarrier barrier) {
		List<ParallelTask> listOfParallelTasks = new ArrayList<>();
		for (AbstractCarAgent agent : listOFAgent) {
			listOfParallelTasks.add(agent.getParallelAction());

		}
		int size = listOFAgent.size();
		int split = size / numOfThread;
		List<Integer> splitIndex = new ArrayList<>();
		for (int i = 1; i < numOfThread + 1; i++) {
			splitIndex.add(i * split + size % numOfThread);
		}
		for (int i = 0; i < numOfThread; i++) {
			int start = i == 0 ? 0 : splitIndex.get(i - 1);
			int end = splitIndex.get(i);
			List<ParallelTask> subList = listOfParallelTasks.subList(start, end);
			new SimulationWorker(subList, numOfStep, barrier).start();

		}
	}

}
