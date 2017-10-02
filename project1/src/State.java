public class State {
	
	private static final int MISSIONARIES = 0;
	private static final int CANNIBALS = 1;
	private static final int BOAT_LOCATION = 2;
	
	private static final int VECTOR_LENGTH = 3;
	
	private int[] start = new int[VECTOR_LENGTH];
	private int[] end = new int[VECTOR_LENGTH];
	
	private State(int[] start, int[] end) throws Exception {
		
		this.start = start;
		this.end = end;
	}
	
	public static State createState(int[] start, int[] end) throws Exception {
		
		if (start.length > VECTOR_LENGTH || end.length > VECTOR_LENGTH)
			throw new Exception("Make sure both of the arrays have a length of 3");
		
		State newState = new State(start, end);
		
		if (!validState(newState))
			throw new Exception("State is invalid");
		
		return newState;
	}
	
	public static boolean validState(State state) {
		
		if (state.getStart()[MISSIONARIES] < state.getStart()[CANNIBALS])
			return false;
		else if (state.getEnd()[MISSIONARIES] < state.getEnd()[CANNIBALS])
			return false;
		else if (state.getStart()[BOAT_LOCATION] != 1 && state.getStart()[BOAT_LOCATION] != 0)
			return false;
		else if (state.getEnd()[BOAT_LOCATION] != 1 && state.getEnd()[BOAT_LOCATION] != 0)
			return false;
		
		return true;
	}
	
	public State move(int numMissionaries, int numCannibals) throws Exception {		
		
		int[] newStart = this.start;
		newStart[MISSIONARIES] += numMissionaries;
		newStart[CANNIBALS] += numCannibals;
		newStart[BOAT_LOCATION] = start[BOAT_LOCATION] == 1 ? 0 : 1;
	
		int[] newEnd = this.end;
		newEnd[MISSIONARIES] -= numMissionaries;
		newEnd[CANNIBALS] -= numCannibals;
		newEnd[BOAT_LOCATION] = start[BOAT_LOCATION] == 1 ? 1 : 0;
		
		return createState(newStart, newEnd);
	}

	public int[] getStart() {
		return start;
	}

	public int[] getEnd() {
		return end;
	}
	
	public boolean boatAtStart() {
		
		return start[BOAT_LOCATION] == 1;
	}
}