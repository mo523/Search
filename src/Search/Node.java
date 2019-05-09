package Search;

import java.util.ArrayList;
import java.util.List;

public class Node implements INode
{

	private int level;
	private INode parent;
	private FifteenPuzzle fp;

	public Node(INode parent, FifteenPuzzle fp)
	{
		this.parent = parent;
		this.fp = fp;
		if (parent != null)
			level = parent.getLevel() + 1;
	}

	@Override
	public int hashCode()
	{
		return fp.hashCode();
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (parent != null)
			sb.append(parent.toString() + "\n\nStep # " + level + "\n");
		else 
			sb.append("Initial board:\n");
		sb.append(fp.getPrintable());
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
		return fp.solved();
	}

	@Override
	public double getHValue()
	{
		return fp.getManhatten();
	}

	@Override
	public double getGValue()
	{
		return level;
	}

	@Override
	public List<INode> getNextNodes()
	{
		List<INode> IList = new ArrayList<>();
		boolean[] moves = fp.getMoves();
		for (int i = 0; i < moves.length; i++)
			if (moves[i])
				IList.add(new Node(this, fp.newBoardFromMove(i)));
		return IList;
	}

}
