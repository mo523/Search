package Search;

import java.util.List;
import java.util.Random;

public class RushHour implements IPuzzle
{
	private int[][] board;

	public RushHour(int diff)
	{
		board = new int[6][6];
		createRandomBoard(diff);
	}

	private void createRandomBoard(int diff)
	{
		Random random = new Random();

		int row, col;

		col = random.nextInt(4);
		board[2][col] = 1;
		board[2][col + 1] = 1;

		for (int i = 1; i < 5; i++)
		{
			boolean good;
			do
			{
				good = false;
				int size = random.nextDouble() >= .25 ? 2 : 3;
				boolean direction = random.nextBoolean();
				row = random.nextInt(6);
				col = random.nextInt(6);

				while (board[row][col] != 0)
				{
					size--;
				}

			} while (!good);
		}

		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public double getHeuristic()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IPuzzle getNewBoard(int m)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean solved()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<INode> getNextMoves(INode i)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrintable()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doMove(int move)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getMoveCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean[] getMoves()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
