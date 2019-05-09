package Search;

import java.util.List;

public interface IPuzzle
{
	public double getHeuristic();
	public IPuzzle getNewBoard(int m);
	public boolean solved();
	public List<INode> getNextMoves(INode i);
	public String getPrintable();
	public void doMove(int move);
	public int getMoveCount();
	public boolean[] getMoves();
}
