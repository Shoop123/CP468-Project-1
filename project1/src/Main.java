import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws Exception {		
		Node initialNode = createStateSpace(3, 3);
		
		System.out.println();
		
//		System.out.println("Initial: " + initialNode.getState());
		
		dig(initialNode);
	}
	
	private static Node createStateSpace(int numMissionaries, int numCannibals) {
		
		State initialState;
		
		try {
			
			initialState = State.createState(new int[] {numMissionaries, numCannibals, 1}, new int[] {0, 0, 0});
		} catch (Exception e) {
			
			return null;
		}
		
		Node initialNode = new Node(initialState);
		
		ArrayList<State> existingStates = new ArrayList<State>();
		existingStates.add(initialState);
				
		createStateSpaceAux(initialNode, existingStates);
		
		return initialNode;
	}
	
	private static void createStateSpaceAux(Node node, ArrayList<State> existingStates) {
		
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
				} catch (Exception e) {
					
					System.out.format("Invalid state with start params: {%d, %d, %d}\n", start[State.MISSIONARIES], start[State.CANNIBALS], start[State.BOAT_LOCATION]);
				}
			}
		}
		
		for (Node newNode : node.getNodes()) {
			createStateSpaceAux(newNode, existingStates);
		}
	}
	
	private static void dig(Node top) {
		
		System.out.println(top.getState());
		
		if (top.getNodes().size() > 0)
			for (Node node : top.getNodes())				
				dig(node);
	}
}




























