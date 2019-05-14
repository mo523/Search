package Search;

import java.util.*;

public class Program
{
	private static IPuzzle puzzle;
	private static Scanner kb;

	public static void main(String[] args)
	{
		kb = new Scanner(System.in);
		initialMenu();
		kb.close();
	}

	private static void initialMenu()
	{
		print("Welcome to the puzzle solver");
		int gameChoice = printGet("Which puzzle would you like to do?", 1, 10);
		boolean debug = printGet("\nDo you see >> \u001B[38;2;112;140;78m**\u001B[0m\n1. Colored stars\n2. Gibberish",
				1, 2) == 2;
		boolean ai = printGet("\n1. Manual\n2. AI", 1, 2) == 2;

		switch (gameChoice)
		{
			case 1:
				int size = printGet("How big would you like your board to be?", 1, 10);
				int manLim = Integer.MAX_VALUE;
				puzzle = new FifteenPuzzle(size, manLim, debug);
				print("\nPuzzle created with a size of: " + size + "x" + size + "\n\t& a Manhatten distance of: "
						+ puzzle.getHeuristic());
				break;

			default:
				System.out.println("Not yet Implemented");
				break;
		}

		if (ai)
			playAI();
		else
			playGame();
	}

	private static void playAI()
	{
		IPuzzleSearch ps = new PuzzleSearch();
		ps.search(new Node(null, puzzle));
	}

	private static void playGame()
	{
		do
		{
			print(puzzle.getPrintable());
			int move = getMove();
			puzzle.doMove(move);
		} while (!puzzle.solved());

		print(puzzle.getPrintable());
		System.out.println("Congrats! You won in " + puzzle.getMoveCount() + " moves");
	}

	private static int getMove()
	{
		boolean[] moves = puzzle.getMoves();
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

	private static int printGet(String msg, int low, int high)
	{
		return (int) printGet(msg, (double) low, (double) high);
	}

	private static double printGet(String msg, double low, double high)
	{
		print(msg);
		double choice;
		do
		{
			choice = kb.nextDouble();
			if (choice < low || choice > high)
				System.out.println("\nInvalid choice!\n" + low + " - " + high);
		} while (choice < low || choice > high);
		kb.nextLine();
		return choice;
	}

	private static void print(String msg)
	{
		System.out.println(msg);
	}
}
