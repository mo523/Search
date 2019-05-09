package Search;

import java.util.*;

public class Program
{
	private static FifteenPuzzle fp;
	private static Scanner kb = new Scanner(System.in);
	private static int moveAmt;

	public static void main(String[] args)
	{
		initialMenu();
		kb.close();
	}

	private static void initialMenu()
	{
		print("Welcome to Fifteen Puzzle");
		print("\nDo you see >> \u001B[38;2;112;140;78m**\u001B[0m\n1. Colored stars\n2. Gibberish");
		boolean debug = kb.nextInt() == 2;
		if (debug)
			print("Ok, using simple output");
		print("\n1. Manual\n2. AI");
		boolean ai = kb.nextInt() == 2;
		print("How big would you like your board to be?");
		int size;
		int manLim = Integer.MAX_VALUE;
		do
		{
			size = kb.nextInt();
			if (size < 2)
				System.out.println("ERROR! Size must be at least 2");
			if (size > 5)
				System.out.println("ERROR! Size cannot be more than 5");
		} while (size < 2 || size > 5);

		if (ai && size > 3)
		{
			print("\nA* may take a long time calculating this board size");
			print("Would you like to limit the Manhatten Distance?\n1. Yes\n2. No");
			if (kb.nextInt() == 1)
			{
				print("\nWhat would you like to limit it to?");
				printStats(size);
				manLim = kb.nextInt();
			}
			else
				print("\nThis may work, but increase the heap and stack size and prepare to wait a while");
		}
		fp = new FifteenPuzzle(size, manLim, debug);
		print("\nPuzzle created with a size of: " + size + "x" + size + "\n\t& a Manhatten distance of: "
				+ fp.getManhatten());
		if (ai)
			playAI();
		else
		{
			kb.nextLine();
			moveAmt = 0;
			playGame();
		}
	}

	private static void printStats(int size)
	{
		int max, min, avg;
		switch (size)
		{
			case 4:
				max = 57;
				min = 14;
				avg = 37;
				break;
			case 5:
				max = 107;
				min = 40;
				avg = 76;
				break;
			case 6:
				max = 185;
				min = 80;
				avg = 135;
				break;
			case 7:
				max = 283;
				min = 145;
				avg = 218;
				break;
			case 8:
				max = 420;
				min = 235;
				avg = 330;
				break;
			case 9:
				max = 585;
				min = 335;
				avg = 472;
				break;
			default:
				max = 0;
				min = 0;
				avg = 0;
				break;
		}
		print("The max is: " + max + ", the min is: " + min + ", the avg is: " + avg);
	}

	private static void playAI()
	{
		IPuzzleSearch ps = new PuzzleSearch();
		Node n = new Node(null, fp);
		ps.search(n);
	}

	private static void playGame()
	{
		do
		{
			fp.printBoard();
			int move = getMove();
			fp.doMove(move);
			moveAmt++;
		} while (!fp.solved());

		fp.printBoard();
		System.out.println("Congrats! You won in " + moveAmt + " moves");
	}

	private static int getMove()
	{
		boolean[] moves = fp.getMoves();
		int move;
		do
		{
			move = moveToInt(kb.nextLine().toLowerCase().charAt(0));
			if (move == -1)
				System.out.println("ERROR! Please type 'u', 'd', 'l' or 'r'");
			else if (moves[move] == false)
				System.out.println("ERROR! Illegal move");
		} while (move == -1 || moves[move] == false);

		System.out.println(move);
		return move;
	}

	private static int moveToInt(char c)
	{
		switch (c)
		{
			case 'u':
				return 0;
			case 'd':
				return 1;
			case 'l':
				return 2;
			case 'r':
				return 3;
			default:
				return -1;
		}
	}

	private static void print(String msg)
	{
		System.out.println(msg);
	}
}
