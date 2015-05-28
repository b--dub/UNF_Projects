package Project5;

import java.util.Random;

/**
 * The TicTacToeBoard class represents a set of static methods that anyone could
 * use to help in making a Tic-Tac-Toe game.  Each of the methods accepts a 3x3
 * char array with the current state of the game that is being played.  The array
 * must contain the numbers '1' to '9' marking the positions that are available
 * (working left to right and then top down) or an 'X' or 'O' if a player has already 
 * moved there.  The findWinningSpot() method also requires a char denoting the
 * player that is being checked ('X' or 'O').  
 * <p>
 * The four methods help the computer to select a position to move to, return a
 * printable String version of the board, or determine whether a player has 
 * already won the game.
 * <p>
 *
 * @author Brad Walsh
 * @version 15 November 2013
 */
public class TicTacToeBoard 
{
    /**
     * findWinningSpot() looks at the game board and determines whether the 
     * player given in the arguments could make a game winning move if it were
     * currently their turn.  This can be used to determine if there is a spot
     * that the computer should move to, either to win or to prevent the other
     * player from winning (depending on the player char passed in to the method).
     * <p>
     * The method searches for any occurence of two of the player characters in
     * any single row, column, or diagonal where the third of these spaces is 
     * open.  This is done by adding the numeric equivalent of the chars and 
     * seeing if by subtracting two times the player's char value, an acceptable
     * number value remains (one that ranges 1 to 9).  The numbers and columns 
     * are cycled through with a for loop and the two diagonals are then 
     * checked individually.  
     * <p>
     * 
     * @param board char[][] object representing the current state of the game
     * @param player char object representing 'X' or 'O', the player to check for
     * @return int number from 1 to 9 of the spot that the given player could 
     * move to and win the game on their next move (numbered left to right and
     * then top down)
     */
    public static int findWinningSpot(char[][] board, char player) 
    {
	int col = 0;
        
        for (int row = 0; row < 3; ++row) 
        {
            if (board[row][0] + board[row][1] + board[row][2] - (2 * player) > 48 &&
                    board[row][0] + board[row][1] + board[row][2] - (2 * player) < 58) 
                return board[row][0] + board[row][1] + board[row][2] - (2 * player) - 48;
            
            if (board[0][col] + board[1][col] + board[2][col] - (2 * player) > 48 &&
                    board[0][col] + board[1][col] + board[2][col] - (2 * player) < 58)
                return board[0][col] + board[1][col] + board[2][col] - (2 * player) - 48;
            
            col++;
        }
        
        if (board[0][0] + board[1][1] + board[2][2] - (2 * player) > 48 &&
                board[0][0] + board[1][1] + board[2][2] - (2 * player) < 58)
            return board[0][0] + board[1][1] + board[2][2] - (2 * player) - 48;
        
        if (board[2][0] + board[1][1] + board[0][2]  - (2 * player) > 48 &&
                board[2][0] + board[1][1] + board[0][2]  - (2 * player) < 58)
            return board[2][0] + board[1][1] + board[0][2] - (2 * player) - 48;
        
        return 0;
    }
    
    /**
     * printableBoard() creates a String that represents a Tic-Tac-Toe board
     * where unused spaces are marked 1 through 9 (left to right and then top
     * down).  Spacing is added to make the lines horizontally centered and 
     * dashes, bars, and plus signs are added to complete the visual effect.
     * 
     * @param board char[][] object representing the current state of the game
     * @return String representing a Tic-Tac-Toe board, currently in play, when
     * open spaces are marked with the numbers 1 to 9 (left to right, and then
     * top down).  Spacing creates horizontal centering.
     */
    public static String printableBoard(char[][] board) 
    {
        String ret = 
            "      " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + "\n" +
            "     ---+---+---\n" +
            "      " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + "\n" +
            "     ---+---+---\n" +
            "      " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + "\n";
        
        return ret;
    }
    
    /**
     * determineWinner() determines whether there is a row, column, or diagonal
     * which contains either three 'X's or three 'O's.  To do this it uses the 
     * numeric ASCII values of these characters (75 and 88), cycles through
     * the rows and columns, and then checks the two diagonals, looking for an
     * occurence of three values that add to either 3*75 or 3*88.  If it finds
     * one it returns the char value representing the player who wins.
     * 
     * @param board char[][] object representing the current state of the game
     * @param player char object representing 'X' or 'O'
     * @return char an 'X' or 'O' if that player has won the game, otherwise a 
     * zero (0) to indicate that no one has won
     */
    public static char determineWinner(char[][] board) 
    {
        int col = 0;
        
        for (int row = 0; row < 3; ++row) 
        {
            if (board[row][0] + board[row][1] + board[row][2] == 264) return 'X';
            if (board[0][col] + board[1][col] + board[2][col] == 264) return 'X';
            if (board[row][0] + board[row][1] + board[row][2] == 237) return 'O';
            if (board[0][col] + board[1][col] + board[2][col] == 237) return 'O';
            col++;
        }
        if (board[0][0] + board[1][1] + board[2][2] == 264) return 'X';
        if (board[0][0] + board[1][1] + board[2][2] == 237) return 'O';
        if (board[2][0] + board[1][1] + board[0][2] == 264) return 'X';
        if (board[2][0] + board[1][1] + board[0][2] == 237) return 'O';
        
        return 0;
    }
    
    /**
     * pickRandomAvailableSpot() uses a Random number generator to pick a random
     * spot on the board.  After a spot is selected, the board is checked to see
     * if it is available by comparing the row and column of the board with the
     * one selected.  If the spot is taken, it will continue to loop through 
     * iterations of randomly selecting spots until an available spot has been
     * found.
     * <p>
     * 
     * @param board char[][] object representing the current state of the game
     * @return int a number from 1 to 9 of a random spot on the board that has
     * not yet been taken
     */
    public static int pickRandomAvailableSpot(char[][] board) 
    {
        Random generator = new Random();
        
        int row, col;
        
        do {
            row = generator.nextInt(3);
            col = generator.nextInt(3);
        } while (board[row][col] == 'X' || board[row][col] == 'O');
        
        return (3 * row) + col + 1;
    }
}
