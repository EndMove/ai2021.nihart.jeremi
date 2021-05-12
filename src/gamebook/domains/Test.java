package gamebook.domains;

import java.util.ArrayDeque;
import java.util.Deque;

public class Test {

	public static void main(String[] args) {
		
		Deque<Integer> queue = new ArrayDeque<>();
		
		queue.add(5);
		queue.add(5);
		queue.add(9);
		queue.add(5);
		queue.add(1);
		
		for (int t : queue) {
			System.out.println(t);
		}

	}

}
