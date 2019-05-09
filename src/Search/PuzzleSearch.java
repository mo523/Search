package Search;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.PriorityQueue;

public class PuzzleSearch implements IPuzzleSearch
{
	@Override
	public void search(INode i)
	{
		PriorityQueue<INode> PQ;
		PQ = new PriorityQueue<>(new FVComparator());
		PQ.add(i);
		System.out.println("\nIDA:");
		ida(PQ);
		
//		PQ = new PriorityQueue<>(new HVComparator());
//		PQ.add(i);
//		System.out.println("\nBest First Search:");
//		search(PQ);
//		PQ = new PriorityQueue<>(new FVComparator());
//		PQ.add(i);
//		System.out.println("A*:");
//		search(PQ);
	}

	private void ida(PriorityQueue<INode> PQ)
	{
		
	}
	
	private void search(PriorityQueue<INode> PQ)
	{
		LocalDateTime start = LocalDateTime.now();
		INode solution = null;
		HashSet<Integer> hashes = new HashSet<>();
		do
		{
			INode curr = PQ.poll();
			hashes.add(curr.hashCode());
			if (curr.isSolution())
				solution = curr;
			else
				for (INode next : curr.getNextNodes())
					if (!hashes.contains(next.hashCode()))
						PQ.add(next);
		} while (!PQ.isEmpty() && solution == null);
		LocalDateTime end = LocalDateTime.now();
		System.out.println("Total Hashes: " + hashes.size() + "\nTotal running time: " + getDuration(start, end));
		if (solution == null)
			System.out.println("No solution found...");
		else
			System.out.println("Total Steps: " + solution.getLevel()
					+ beautifySteps(solution.toString(), solution.getLevel() + 1));
	}

	private String beautifySteps(String s, int hs)
	{
		String[] sa = s.split("\\n+");
		int size = sa.length / hs;
		String[] steps = new String[(hs / 4 + (hs % 4 > 0 ? 1 : 0)) * size];
		for (int i = 0; i < steps.length; i++)
			steps[i] = "";
		for (int i = 0; i < sa.length; i++)
			steps[i / (size * 4) * size + (i % size)] += sa[i]
					+ (i % size == 0 ? ((size - 2) / 2 == 2 ? "\t" : ((size - 2) / 2 > 4 ? "\t\t\t" : "\t\t")) : "\t");
		String tots = "\r\n\r\n";
		for (int i = 0; i < steps.length; i++)
			tots += steps[i] + (i % size == size - 1 ? "\r\n\r\n" : "\r\n");

		return tots;
	}

	private static StringBuilder getDuration(LocalDateTime start, LocalDateTime end)
	{
		StringBuilder sb = new StringBuilder();
		long hours = ChronoUnit.HOURS.between(start, end);
		long mins = ChronoUnit.MINUTES.between(start, end);
		long secs = ChronoUnit.SECONDS.between(start, end);
		mins %= 60;
		secs %= 60;

		if (secs + mins + hours == 0)
			return new StringBuilder("Instantaneous!");

		if (hours > 0)
			sb.append(hours + " hour");
		if (hours > 1)
			sb.append("s");
		if (hours > 0 && mins > 0)
			sb.append(" & ");
		if (mins > 0)
			sb.append(mins + " minute");
		if (mins > 1)
			sb.append("s");
		if ((mins > 0 || hours > 0) && secs > 0)
			sb.append(" & ");
		if (secs > 0)
			sb.append(secs + " second");
		if (secs > 1)
			sb.append("s");
		return sb;
	}
}