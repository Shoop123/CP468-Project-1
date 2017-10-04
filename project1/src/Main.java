import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws Exception {		
		dfs(3, 3);
	}
	
	private static Node dfs(int numMissionaries, int numCannibals) {
		
		State initialState;
		
		try {
			
			initialState = State.createState(new int[] {numMissionaries, numCannibals, 1}, new int[] {0, 0, 0});
		} catch (Exception e) {
			
			return null;
		}
		
		Node initialNode = new Node(initialState);
		
		ArrayList<State> existingStates = new ArrayList<State>();
		existingStates.add(initialState);
		
		Stack<Node> path = new Stack<Node>();
		path.push(initialNode);
				
		dfs_traverse(initialNode, existingStates, path);
		
		for (Node node : path) {
			
			System.out.println(node.getState());
		}
		
		return initialNode;
	}
	
	private static void dfs_traverse(Node node, ArrayList<State> existingStates, Stack<Node> path) {
		
		final int BOAT_CAPACITY = 2;
		
		for (int i = 0; i <= BOAT_CAPACITY; i++) {
						
			for (int j = 0; j <= BOAT_CAPACITY; j++) {
				
				if (i == 0 && j == 0 || i + j > BOAT_CAPACITY) continue;
				
				int[] start = Arrays.copyOf(node.getState().getStart(), node.getState().getStart().length);
				int[] end = Arrays.copyOf(node.getState().getEnd(), node.getState().getEnd().length);
				
				if (start[State.BOAT_LOCATION] == 1) {
					
					start[State.MISSIONARIES] -= i;
					end[State.MISSIONARIES] += i;
					
					start[State.CANNIBALS] -= j;
					end[State.CANNIBALS] += j;
					
					start[State.BOAT_LOCATION] = 0;
					end[State.BOAT_LOCATION] = 1;
				} else {
					
					start[State.MISSIONARIES] += i;
					end[State.MISSIONARIES] -= i;
					
					start[State.CANNIBALS] += j;
					end[State.CANNIBALS] -= j;
					
					start[State.BOAT_LOCATION] = 1;
					end[State.BOAT_LOCATION] = 0;
				}
				
				try {
					
					State state = State.createState(start, end);
					
					if (!existingStates.contains(state)) {
						
						existingStates.add(state);
						
						node.addNode(new Node(state));
					}
				} catch (Exception e) {}
			}
		}
			
		if (node.getNodes().isEmpty())
			path.pop();
		
		for (Node newNode : node.getNodes()) {
			
			path.push(newNode);
			
			if (!newNode.getState().isGoal())
				dfs_traverse(newNode, existingStates, path);
			else break;
		}
	}
}




























