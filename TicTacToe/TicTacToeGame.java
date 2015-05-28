package Project5;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * COP 2551: Project 5 – Tic-Tac-Toe
 * <p>
 * The TicTacToeGame class represents a one player vs the computer game of 
 * Tic-Tac-Toe.  The human player gets to pick whether they want to be X or O
 * and then the first person to move is chosen at random.  The game uses 
 * JOptionPane's to interact with the user both to display data and to accept
 * input.  There is also some input validation with error reporting.
 * <p>
 * The computer's algorithm makes it intelligent enough to see immediate
 * moves, where the user could move next to win, or where it could move next in  
 * order to win but is totally random in its selections beyond that.
 * <p>
 * The following is a set of examples of the output that could result from a 
 * game of Tic-Tac-Toe, depicting a game where the computer wins, an example of
 * an error in input from the user and the response, and each of three possible 
 * ending frames: the computer winning, the user winning, or a tie.  These are 
 * represented in text format, but in actual play the messages would appear 
 * in JOptionPane windows.
 * <p>
 * COP 2551 Project 5 - Brad Walsh
 * Let's play a game of Tic-Tac-Toe
 * Would you like to be X or O?
 * ((user indicates X by clicking the respective button))
 *     Tic-Tac-Toe
 *      1 | 2 | O
 *     ---+---+---
 *      4 | 5 | 6
 *     ---+---+---
 *      7 | 8 | 9
 *   It is your move!
 * Enter the number of
 * an available space.
 *      You are X
 * ((user enters 5 and clicks OK))
 *     Tic-Tac-Toe
 *      1 | 2 | O
 *     ---+---+---
 *      4 | X | 6
 *     ---+---+---
 *      O | 8 | 9
 *   It is your move!
 * Enter the number of
 * an available space.
 *      You are X
 * ((user enters 3 and clicks OK))
 *     Tic-Tac-Toe
 *      X | 2 | O
 *     ---+---+---
 *      4 | X | 6
 *     ---+---+---
 *      O | 8 | O
 *   It is your move!
 * Enter the number of
 * an available space.
 *      You are X
 * ((user enters 3 and clicks OK, but this space is already taken, so an error is given))
 * Invalid space chosen -->3
 * Please pick an available space
 * ((user clicks OK to acknowledge))
 *     Tic-Tac-Toe
 *      X | 2 | O
 *     ---+---+---
 *      4 | X | 6
 *     ---+---+---
 *      O | 8 | O
 *   It is your move!
 * Enter the number of
 * an available space.
 *      You are X
 * ((user enters an 8 and clicks OK))
 *  The computer wins!
 *      X | 2 | O
 *     ---+---+---
 *      4 | X | O
 *     ---+---+---
 *      O | X | O
 * ((user is to click OK, ending and exiting the game))  
 * ---OR---
 *       You win!
 *      X | 2 | O
 *     ---+---+---
 *      X | X | 6
 *     ---+---+---
 *      X | O | O
 * ((user is to click OK, ending and exiting the game))  
 * ---OR---
 *      Cats game!
 *      O | X | O
 *     ---+---+---
 *      X | X | O
 *     ---+---+---
 *      X | O | X
 * ((user is to click OK, ending and exiting the game))  
 * <p>
 * 
 * @author Brad Walsh
 * @version 15 November 2013
 */
public class TicTacToeGame 
{
    /*
     * Algorithm:
     *
     * 1. The board is initialized with the values 1 to 9, from left to right 
     * and then top down
     * 
     * 2. A window is displayed with intro information which asks whether the 
     * user wants to be X or O
     * 
     * 3. Formatting for JOptionPane windows is set for font and color
     * 
     * 4. Which player is to go first is established by random number generator
     * 
     * 5. Players take turns picking spots to move into until one wins or all 
     * the spots are filled in resulting in a tie (assuming no one won)
     * 
     *     C1. Computer checks to see if there is a space it can move into and 
     *     win, if so it does
     * 
     *     C2. Computer checks to see if there is a spot the user can move into
     *     which would result in a win.  If so, it moves there
     * 
     *     C3. Otherwise the computer moves to a random spot
     * 
     *     U1. The user is presented with the current board and asked to select
     *     a spot.  
     * 
     *     U2. If the user selects CANCEL here, the game exits
     * 
     *     U3. Error checking is conducted on the user's input, if it passes,
     *     the spot is recorded for the user, otherwise an error message is 
     *     displayed and the user is asked to try again.
     * 
     * 6. A final results window is displayed showing the board and winner/tie
     * 
     * 
     */
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        Random generator = new Random();
        
        int moveCounter = 0, move;
        char user, computer;
        char[][] board = new char[3][3];
        
        initBoard(board);
        user = askUserXorO();
        if (user == 'X') computer = 'O';
        else computer = 'X';
        
        javax.swing.UIManager.put("OptionPane.messageFont", 
            new FontUIResource(new Font("Consolas", Font.BOLD, 20))); 
        javax.swing.UIManager.put("Panel.background", new 
            Color(177,211,177)); 

        // Pick who moves first randomly
        if (generator.nextInt(2)==0) 
        {
            move = computerMoves(board, computer, user);
            board[(move-1)/3][(move-1)%3] = computer;
            moveCounter++;
        }
        
        while (TicTacToeBoard.determineWinner(board)==0 && moveCounter<9) 
        {
            move = userMoves(board, user);
            board[(move-1)/3][(move-1)%3] = user;
            moveCounter++;
            
            if (TicTacToeBoard.determineWinner(board)==0 && moveCounter<9) 
            {
                move = computerMoves(board, computer, user);
                board[(move-1)/3][(move-1)%3] = computer;
                moveCounter++;
            }
        }
        
        if (TicTacToeBoard.determineWinner(board)==user)
            userWins(board);
        else if (TicTacToeBoard.determineWinner(board)==computer)
            computerWins(board);
        else tieGame(board);
        
    }
    
    /**
     * initBoard() sets the values of the char array to 1 to 9, from left to
     * right and then top down
     * 
     * @param board char[][] object representing the current state of the game
     * @return void
     */
    private static void initBoard(char[][] board)
    {
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                board[i][j] = (char)((3*i) + j + 49);
    }
    
    /**
     * askUserXorO() displays a JOptionPane with two buttons, one for X and the 
     * other for O, to let the user choose which character they want to be.  It
     * also displays the intro information.
     * 
     * @return char 'X' or 'O', the character the user wishes to be
     */
    private static char askUserXorO()
    {
        UIManager.put("OptionPane.yesButtonText", "X"); 
        UIManager.put("OptionPane.noButtonText", "O"); 
        int player = JOptionPane.showConfirmDialog(null, 
                "COP 2551 Project 5 - Brad Walsh\n\n" + 
                "Let's play a game of Tic-Tac-Toe\n\n" +
                "Would you like to be X or O?", "Tic-Tac-Toe", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE);
        
        if (player==JOptionPane.YES_OPTION) return 'X';
        else return 'O';
    }
    
    /**
     * computerMoves() uses three helper methods of the TicTacToeBoard class
     * in order to choose which spot to move to next.  First the findWinningSpot()
     * method with the current board and computer's char to see if it can win 
     * during this move.  Second, the findWinningSpot() method with the current 
     * board and user's character to see if it needs to block the user from 
     * winning during their next move.  Finally, if neither of the above return
     * a move for the computer, it calls the pickRandomAvailableSpot() method
     * to pick a move for it at random.
     * 
     * @param board char[][] object representing the current state of the game
     * @param computer char representing the computer's character
     * @param user char representing the user's character  
     * @return int a number from 1 to 9 that represents the spot the computer
     * wishes to move to
     */
    private static int computerMoves(char[][] board, char computer, char user) 
    {
        int move;
        
        move = TicTacToeBoard.findWinningSpot(board, computer);
        if (move!=0) return move;
        
        move = TicTacToeBoard.findWinningSpot(board, user);
        if (move!=0) return move;
        
        return TicTacToeBoard.pickRandomAvailableSpot(board);
    }
    
    /**
     * userMoves() displays a JOptionPane window displaying the current board, 
     * stating that it is the user's turn to move, and what their character is.
     * The user is then prompted to input a single digit from 1 to 9, the spot
     * in which they wish to move.  If they select cancel, the game will exit.
     * Otherwise the input is checked to see if it begins with an integer using
     * a Scanner tokenizer, and to make sure that it contains only one character.
     * This is to filter out non-single-digit numbers, or inputs that may consist
     * of multiple parts.  If it passes, the integer is checked to make sure 
     * it is not 0, and that the space it represents contains the value itself,
     * indicating that it has not yet been taken.  If any of these conditions 
     * are not met, an error message is delivered, and the process begins again.
     * Otherwise the selection is returned to the main method to be added to the
     * board.
     * 
     * @param board char[][] object representing the current state of the game
     * @param user char representing the user's character
     * @return int a number from 1 to 9 representing the spot the user wishes
     * to move to
     */
    private static int userMoves(char[][] board, char user) 
    {
        String str;
        int move = 0;
        do {
            str = JOptionPane.showInputDialog(null, "     Tic-Tac-Toe\n" + 
                    TicTacToeBoard.printableBoard(board) +
                    "\n   It is your move!\n\n"
                    + " Enter the number of \n"
                    + " an available space.\n\n"
                    + "      You are " + user, "Tic-Tac-Toe", 
                    JOptionPane.PLAIN_MESSAGE);
            
            if (str==null) System.exit(0);
            
            Scanner sc = new Scanner(str);
            if (sc.hasNextInt() && str.length()==1) 
            {
                move = sc.nextInt();
                if (move==0 || board[(move-1)/3][(move-1)%3]!=(move + 48)){
                    JOptionPane.showMessageDialog(null, "Invalid space chosen -->"
                    + str + "\nPlease pick an available space", 
                    "Error", JOptionPane.OK_OPTION);
                    move = 0;
                }
            }
            else 
            {
                JOptionPane.showMessageDialog(null, "Invalid space chosen -->"
                + str + "\nPlease pick an available space", 
                "Error", JOptionPane.OK_OPTION);
                move = 0;
            }
        } while (move == 0);
        
        return move;
    }
    
    /**
     * computerWins() displays a JOptionPane window with the final board and
     * reveals that the computer has selected a winning move.  The user can then
     * click on the OK button to exit the game.
     * 
     * @param board char[][] object representing the current state of the game
     * @return void
     */
    private static void computerWins(char[][] board) 
    {
        JOptionPane.showMessageDialog(null, "  The computer wins!\n\n" + 
                TicTacToeBoard.printableBoard(board), "Computer Wins!",
                JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * userWins() displays a JOptionPane window with the final board and
     * reveals that the user has selected a winning move.  The user can then
     * click on the OK button to exit the game.
     * 
     * @param board char[][] object representing the current state of the game
     * @return void
     */
    private static void userWins(char[][] board) 
    {
        JOptionPane.showMessageDialog(null, "       You win!\n\n" + 
                TicTacToeBoard.printableBoard(board), "Player Wins!",
                JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * tieGame() displays a JOptionPane window with the final board and
     * reveals that the game has ended in a tie.  The user can then
     * click on the OK button to exit the game.
     * 
     * @param board char[][] object representing the current state of the game
     * @return void
     */
    private static void tieGame(char[][] board) 
    {
        JOptionPane.showMessageDialog(null, "      Cats game!\n\n" + 
                TicTacToeBoard.printableBoard(board), "Tie!",
                JOptionPane.PLAIN_MESSAGE);
    }
}
