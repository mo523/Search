package Search;

import java.util.List;

public class Node implements INode
{

	private int level;
	private INode parent;
	private IPuzzle puzzle;

	public Node(INode parent, IPuzzle puzzle)
	{
		this.parent = parent;
		this.puzzle = puzzle;
		if (parent != null)
			level = parent.getLevel() + 1;
	}

	@Override
	public int hashCode()
	{
		return puzzle.hashCode();
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (parent != null)
			sb.append(parent.toString() + "\n\nStep # " + level + "\n");
		else
			sb.append("Initial board:\n");
		sb.append(puzzle.getPrintable());
		return sb.toString();
	}

	@Override
	public int getLevel()
	{
		return level;
	}

	@Override
	public INode getParent()
	{
		return parent;
	}

	@Override
	public boolean isSolution()
	{
		return puzzle.solved();
	}

	@Override
	public double getHValue()
	{
		return puzzle.getHeuristic();
	}

	@Override
	public double getGValue()
	{
		return level;
	}

	@Override
	public List<INode> getNextNodes()
	{
		return puzzle.getNextMoves(this);
	}
}
