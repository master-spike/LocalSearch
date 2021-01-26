package najeeb.localsearch;

public abstract class SimulatedAnnealing {

	private double temperature;
	private int steps;
	
	public double getTemp() {
		return temperature;
	}
	public int getSteps() {
		return steps;
	}

	public void solve() {
		steps = 0;
		temperature = tempFunction(0);
		genInitState();
		while (!isInTerminationCondition()) {
			LocalMove move = selectNextMove();
			move.updateLegality();
			move.updateNetChange();
			move.doMoveIfAllowed();
			steps++;
			temperature = tempFunction(steps);
		}
	}

	protected abstract class LocalMove {
		boolean legality;
		double net_change;

		protected void updateLegality() {
			legality = isLegal();
		}
		protected void updateNetChange() {
			net_change = calcNetChange();
		}

		private void doMoveIfAllowed() {
			if (isLegal() && acceptFunction(temperature, calcNetChange())) {
				execute();
			}
		}

		protected abstract boolean acceptFunction(double temperature, double change);

		public abstract double calcNetChange();

		public abstract boolean isLegal();

		protected abstract void execute();
	}

	protected abstract boolean isInTerminationCondition();

	protected abstract double tempFunction(int steps);

	protected abstract LocalMove selectNextMove();

	protected abstract void genInitState();

}
