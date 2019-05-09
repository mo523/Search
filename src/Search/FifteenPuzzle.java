package Search;

import java.util.*;

public class FifteenPuzzle
{
	private int[][] board;
	private int zx, zy;
	private int size;
	private boolean debug;

	public FifteenPuzzle(int size, int manLim, boolean debug)
	{
		this.size = size;
		this.debug = debug;
		randomBoard(manLim);
	}

	private FifteenPuzzle(int[][] board, int move, boolean debug, int zx, int zy)
	{
		this.size = board.length;
		this.board = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				this.board[i][j] = board[i][j];
		this.debug = debug;
		this.zx = zx;
		this.zy = zy;
		doMove(move);
	}

	public int[][] getBoard()
	{
		return board;
	}

	public void doMove(int direction)
	{
		int x = direction == 0 ? 1 : direction == 1 ? -1 : 0;
		int y = direction == 2 ? 1 : direction == 3 ? -1 : 0;
		board[zx][zy] = board[zx + x][zy + y];
		board[zx + x][zy + y] = 0;
		zx += x;
		zy += y;
	}

	public boolean[] getMoves()
	{
		boolean[] moves = new boolean[4];

		if (zx + 1 < size)
			moves[0] = true;
		if (zx - 1 >= 0)
			moves[1] = true;
		if (zy + 1 < size)
			moves[2] = true;
		if (zy - 1 >= 0)
			moves[3] = true;

		return moves;
	}

	public boolean solved()
	{
		int curr = 1;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (board[i][j] != curr++ && (i + 1 != size || j + 1 != size))
					return false;
		return true;
	}

	public boolean isSolution()
	{
		int curr = 1;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (i + j != size && board[i][j] != curr++)
					return false;
		return true;
	}

	private void randomBoard(int manLim)
	{
		board = new int[size][size];
		ArrayList<Integer> flatBoard;
		Random ran = new Random();
		do
		{
			int zeroLoc = 0;
			do
			{
				flatBoard = new ArrayList<>();
				for (int i = 0; i < size * size; i++)
				{
					int temp;
					do
					{
						temp = ran.nextInt(size * size);
					} while (flatBoard.contains(temp));
					flatBoard.add(temp);
					if (temp == 0)
						zeroLoc = i;
				}
			} while (!solvableBoard(flatBoard, zeroLoc));
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
					board[i][j] = flatBoard.get(i * size + j);
			zx = zeroLoc / size;
			zy = zeroLoc % size;
		} while (getManhatten() > manLim || isSolution());
	}

	private boolean solvableBoard(ArrayList<Integer> flatBoard, int zeroLoc)
	{
		int inversionCount = 0;
		for (int i = 0; i < flatBoard.size(); i++)
			for (int j = i + 1; j < flatBoard.size(); j++)
				if (flatBoard.get(i) != 0 && flatBoard.get(j) != 0)
					if (flatBoard.get(i) > flatBoard.get(j))
						inversionCount++;

		zeroLoc /= size;
		zeroLoc %= 2;
		inversionCount %= 2;
		if (size % 2 == 1)
			if (inversionCount == 0)
				return true;
			else
				return false;
		return inversionCount != zeroLoc;
	}

	public void printBoard()
	{
		System.out.println(getPrintable());
	}

	public String getPrintable()
	{
		return debug ? getDebug() : getCool();
	}

	private String getCool()
	{
		StringBuilder sb = new StringBuilder();
		String bgDark = "\u001B[48;2;107;17;0m";
		String bgLite = "\u001B[48;2;163;127;73m";
		String bgZero = "\u001B[48;2;63;63;63m";
		String fg = "\u001B[38;2;175;175;175m";
		String bgBox = "\u001B[48;2;100;100;100m";
		String reset = "\u001B[0m";
		String newLine = "\n";
		sb.append(bgBox);
		for (int i = 0; i < size * 3 + 2; i++)
			sb.append("  ");
		sb.append(reset);
		sb.append(newLine);
		for (int i = 0; i < size * 3; i++)
		{
			sb.append(bgBox + "  " + reset);
			for (int j = 0; j < size; j++)
			{
				int num = board[i / 3][j];
				boolean numRow = i % 3 == 1;
				String bg = num == 0 ? bgZero : i / 3 % 2 == j % 2 ? bgLite : bgDark;
				String pn = num == 0 || !numRow ? "  " : num < 10 ? " " + num : "" + num;
				sb.append(bg + fg + "  " + pn + "  " + reset);
			}
			sb.append(bgBox + "  " + reset);
			sb.append(newLine);
		}
		sb.append(bgBox);
		for (int i = 0; i < size * 3 + 2; i++)
			sb.append("  ");
		sb.append(reset);
		return sb.toString();
	}

	public String getDebug()
	{
		StringBuilder sb = new StringBuilder();
		String line = "-";
		for (int i = 0; i < size; i++)
			line += "-----";
		sb.append(line);
		for (int i = 0; i < size; i++)
		{
			sb.append("\n|");
			for (int j = 0; j < size; j++)
			{
				int num = board[i][j];
				sb.append(" ");
				if (num == 0)
					sb.append("  ");
				else if (num < 10)
					sb.append("0" + num);
				else
					sb.append(num);
				sb.append(" |");
			}
			sb.append("\n" + line);
		}
		return sb.toString();
	}

	public FifteenPuzzle newBoardFromMove(int move)
	{
		return new FifteenPuzzle(board, move, debug, zx, zy);
	}

	@Override
	public int hashCode()
	{
		StringBuilder sb = new StringBuilder();
		for (int[] ia : board)
			for (int i : ia)
				sb.append(i);
		String s = sb.toString();
		return s.hashCode();
	}

	public int getManhatten()
	{
		int total = 0;
		int currNum = 1;
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
			{
				int rcp = board[row][col];
				if (rcp != 0 && rcp != currNum)
				{
					int tempCurr = rcp - 1;
					int corRow = tempCurr / size;
					int corCol = tempCurr % size;
					total += Math.abs(row - corRow);
					total += Math.abs(col - corCol);
				}
				currNum++;
			}

		return total;
	}
}
