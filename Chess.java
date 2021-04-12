import java.util.Scanner;

public class Chess {
	
	/*
	*	Created by: Micah Trent
	*	Name of Program: Command Console Chess
	*/
	
	public static void main(String[] args){
		Scanner scnr = new Scanner(System.in);
		String[][] chessPieces = createNewPieces();
		String newMove;
		boolean gameIsActive = true;
		
		gameInstructions(scnr);
		do{
			printBoard(chessPieces);
			newMove = userInput(scnr, chessPieces);
			movePiece(chessPieces, newMove);
			gameIsActive = kingsAreAlive(chessPieces);
		}while(gameIsActive);
		
		printBoard(chessPieces);
		System.out.println("\n\t  GAME OVER");
	}
	
	public static String[][] createNewPieces(){
		String[][] pieces = {
			{"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
			{"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", ""},
			{"WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP"},
			{"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"}
		};
		return pieces;
	}
	
	public static void gameInstructions(Scanner input){
		for(int i = 0; i < 40; i++){
			System.out.print("-");
		}
		
		System.out.println("\nYou are now playing Chess\n");
		System.out.println("The \"+\" symbols mark the white tiles. \nThe blank spaces represent the black tiles.\n");
		System.out.println("The abreviations for the pieces are as follows: ");
		System.out.println("BR = Black Rook				WR = White Rook\n" 
		+ "BN = Black Knight			WN = White Knight\n"
		+ "BB = Black Bishop			WB = White Bishop\n"
		+ "BQ = Black Queen			WQ = White Queen\n"
		+ "BK = Black King				WK = White King\n"
		+ "BP = Black Pawn				WP = White Pawn\n");
		System.out.println("Gameplay Instructions:");
		System.out.println("You will play as White and the computer will play as Black.");
		System.out.println("To move a given piece, type in the current position of the piece and the tile you would like to move it to.");
		System.out.println("The letters at the bottom of the board represent the column name and \nthe numbers at the left side represent the row name.");
		System.out.println("The \"name\" of the tile location is determined by the intersection of the row and the tile.\n");
		System.out.println("EXAMPLE: In order to move the White Pawn from its starting position at the bottom left of the board \nupwards by two tiles, you must write: a2-a4\n");
		System.out.println("Press any key then the Enter key to start the game...");
		String userHadReadInstructions = input.next();
		System.out.println();
		
		for(int i = 0; i < 40; i++){
			System.out.print("-");
		}
		System.out.println();
	}
	
	public static void printBoard(String[][] chessPieces){
		String whiteSquareElement = "++";
		String blackSquareElement = "  ";
		String currentChessPiece = "++";
		boolean isWhiteSquare = true;
		
		System.out.print("\n  ");
		//	This for loop makes it so that there are 8 rows (the next for loop determines the rows of text within each tile)
		for(int i = 0; i < chessPieces.length; i++){
			//	This for loop makes each tile three "rows" tall
			for(int j = 0; j < 3; j++){
				//	This for loop creates the horizontal sequence of tiles
				for(int k = 0; k < 8; k++){
					
					//	If the program is on the second row of the given tile (which is j == 1) and the array element at the given index is not empty,
					//	then update the variable currentChessPiece to equal the array element at the given index
					if((j == 1) && (chessPieces[i][k] != "")){
						currentChessPiece = chessPieces[i][k];
					}
					
					//	These logic statements determine whether to print a "black" or "white" tile
					if(isWhiteSquare){
						System.out.print("+" + currentChessPiece + "+");
						currentChessPiece = blackSquareElement;
					}
					else{
						System.out.print(" " + currentChessPiece + " ");
						currentChessPiece = whiteSquareElement;
					}
					
					//Alternates the color of the tiles horizontally each iteration
					isWhiteSquare = !(isWhiteSquare);
				}
				System.out.println();
				
				//	These logic statements determine whether or not to print a "row number" before each row of tiles
				if(j == 0){
					System.out.print((8 - i) + " ");
				}
				else{
					System.out.print("  ");
				}
			}
			//	This boolean statement and the following logic statements alternate "black" and "white" tiles from row to row
			isWhiteSquare = !(isWhiteSquare);
			if(isWhiteSquare){
				currentChessPiece = whiteSquareElement;
			}
			else{
				currentChessPiece = blackSquareElement;
			}
		}
		
		//	This for loop prints out the letters at the bottom of the board
		for(char i = 'a'; i <= 'h'; i++){
			System.out.print(" " + i + "  ");
		}
		System.out.println();
	}
	
	public static String userInput(Scanner scnr, String[][] chessBoard){
		String inputString;
		boolean hasFirstChar;
		boolean hasFirstInt;
		boolean hasSecondChar;
		boolean hasSecondInt;
		
		boolean correctInput = false;
		
		System.out.println();
		do{
			System.out.print("Enter a letter and a number to move a piece: ");
			inputString = scnr.next();
			
			if(!(inputString.length() == 5)){
				System.out.println("This is not a legal move.	Try again.");
				correctInput = false;
				continue;
			}
			
			hasFirstChar = (inputString.charAt(0) >= 'a') && (inputString.charAt(0) <= 'h');
			hasFirstInt = (Character.getNumericValue(inputString.charAt(1)) >= 1) && (Character.getNumericValue(inputString.charAt(1)) <= 8);
			hasSecondChar = (inputString.charAt(3) >= 'a') && (inputString.charAt(3) <= 'h');
			hasSecondInt = (Character.getNumericValue(inputString.charAt(4)) >= 1) && (Character.getNumericValue(inputString.charAt(4)) <= 8);
			if(hasFirstChar && hasFirstInt && hasSecondChar && hasSecondInt && isMoveLegal(chessBoard, inputString)){
				correctInput = true;
			}
			else{
				System.out.println("This is not a legal move.	Try again.");
				correctInput = false;
			}
			
		}while(!correctInput);
		System.out.println();
		
		return inputString;
	}
	
	public static boolean isMoveLegal(String[][] chessBoard, String pieceMove){
		int firstCharToInt = Character.getNumericValue(pieceMove.charAt(0)) - 10;
		int firstInt = Character.getNumericValue(pieceMove.charAt(1));
		int secondCharToInt = Character.getNumericValue(pieceMove.charAt(3)) - 10;
		int secondInt = Character.getNumericValue(pieceMove.charAt(4));
		
		//Variables to keep track of castling
		boolean whiteKingHasNotMoved = true;
		//boolean blackKingHasNotMoved = true;			--Not needed yet
		boolean whiteRookRightHasNotMoved = true;
		//boolean blackRookRightHasNotMoved = true;		--Not needed yet
		boolean whiteRookLeftHasNotMoved = true;
		//boolean blackRookLeftHasNotMoved = true;		--Not needed yet
		
		//General legality Checks
		if(chessBoard[8 - firstInt][firstCharToInt] == ""){
			//Checks if piece exists
			return false;
		}
		else if(chessBoard[8 - firstInt][firstCharToInt].charAt(0) == 'B'){
			//Checks if the user is trying to move the computer's pieces
			return false;
		}
		else if(!(chessBoard[8 - secondInt][secondCharToInt] == "") && chessBoard[8 - secondInt][secondCharToInt].charAt(0) == 'W'){
			//User cannot attack their own pieces
			return false;
		}
		
		//Rook
		if(chessBoard[8 - firstInt][firstCharToInt].charAt(1) == 'R'){
			//Checks if rooks have moved.  If a rook has moved, it cannot castle.
			if(whiteRookLeftHasNotMoved && (firstInt == 1) && (firstCharToInt == 0)){
				whiteRookLeftHasNotMoved = false;
			}
			else if(whiteRookRightHasNotMoved && (firstInt == 1) && (firstCharToInt == 7)){
				whiteRookRightHasNotMoved = false;
			}
			
			//Keeps rooks in "lanes"
			if(firstInt == secondInt){	//Horizontal Lane
				if(firstCharToInt < secondCharToInt){
					for(int i = firstCharToInt + 1; i < secondCharToInt; i++){
						if(chessBoard[8 - firstInt][i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
				else{
					for(int i = firstCharToInt - 1; i > secondCharToInt; i--){
						if(chessBoard[8 - firstInt][i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
			}
			else if(firstCharToInt == secondCharToInt){	//Vertical Lane
				if(firstInt < secondInt){
					//Check path when going up
					for(int i = (8 - firstInt - 1); i > (8 - secondInt); i--){
						if(chessBoard[i][firstCharToInt] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
				else{
					//Check path when going down
					for(int i = (8 - firstInt + 1); i < (8 - secondInt); i++){
						if(chessBoard[i][firstCharToInt] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
			}
		}
		//Knight
		if(chessBoard[8 - firstInt][firstCharToInt].charAt(1) == 'N'){
			if((Math.abs(firstInt - secondInt) == 2) && (Math.abs(firstCharToInt - secondCharToInt) == 1) || (Math.abs(firstInt - secondInt) == 1) && (Math.abs(firstCharToInt - secondCharToInt) == 2)){
				return true;
			}
		}
		//Bishop
		if(chessBoard[8 - firstInt][firstCharToInt].charAt(1) == 'B'){
			if(Math.abs(firstInt - secondInt) == Math.abs(firstCharToInt - secondCharToInt)){
				if((firstCharToInt < secondCharToInt) && (firstInt < secondInt)){
					for(int i = 1; i < Math.abs(firstCharToInt - secondCharToInt); i++){
						if(chessBoard[8 - firstInt - i][firstCharToInt + i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
				else if((firstCharToInt < secondCharToInt) && (firstInt > secondInt)){
					for(int i = 1; i < Math.abs(firstCharToInt - secondCharToInt); i++){
						if(chessBoard[8 - firstInt + i][firstCharToInt + i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
				else if((firstCharToInt > secondCharToInt) && (firstInt < secondInt)){
					for(int i =  1; i < Math.abs(firstCharToInt - secondCharToInt); i++){
						if(chessBoard[8 - firstInt - i][firstCharToInt - i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
				else{
					for(int i =  1; i < Math.abs(firstCharToInt - secondCharToInt); i++){
						if(chessBoard[8 - firstInt + i][firstCharToInt - i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
			}
		}
		//Queen
		if(chessBoard[8 - firstInt][firstCharToInt].charAt(1) == 'Q'){
			if(firstInt == secondInt){
				if(firstCharToInt < secondCharToInt){
					for(int i = firstCharToInt + 1; i < secondCharToInt; i++){
						if(chessBoard[8 - firstInt][i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
				else{
					for(int i = firstCharToInt - 1; i > secondCharToInt; i--){
						if(chessBoard[8 - firstInt][i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
			}
			else if(firstCharToInt == secondCharToInt){
				if(firstInt < secondInt){
					//Check path when going up
					for(int i = (8 - firstInt - 1); i > (8 - secondInt); i--){
						if(chessBoard[i][firstCharToInt] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
				else{
					//Check path when going down
					for(int i = (8 - firstInt + 1); i < (8 - secondInt); i++){
						if(chessBoard[i][firstCharToInt] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
			}
			else if(Math.abs(firstInt - secondInt) == Math.abs(firstCharToInt - secondCharToInt)){
				if((firstCharToInt < secondCharToInt) && (firstInt < secondInt)){
					for(int i = 1; i < Math.abs(firstCharToInt - secondCharToInt); i++){
						if(chessBoard[8 - firstInt - i][firstCharToInt + i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
				else if((firstCharToInt < secondCharToInt) && (firstInt > secondInt)){
					for(int i = 1; i < Math.abs(firstCharToInt - secondCharToInt); i++){
						if(chessBoard[8 - firstInt + i][firstCharToInt + i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
				else if((firstCharToInt > secondCharToInt) && (firstInt < secondInt)){
					for(int i =  1; i < Math.abs(firstCharToInt - secondCharToInt); i++){
						if(chessBoard[8 - firstInt - i][firstCharToInt - i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
				else{
					for(int i =  1; i < Math.abs(firstCharToInt - secondCharToInt); i++){
						if(chessBoard[8 - firstInt + i][firstCharToInt - i] != ""){
							return false;
						}
					}
					//Otherwise, return true
					return true;
				}
			}
		}
		//King
		if(chessBoard[8 - firstInt][firstCharToInt].charAt(1) == 'K' && (((firstInt == secondInt) || (firstCharToInt == secondCharToInt)) || (Math.abs(firstInt - secondInt) == Math.abs(firstCharToInt - secondCharToInt)))){
			if(whiteKingHasNotMoved){
				if(whiteRookRightHasNotMoved && (secondInt == 1) && (secondCharToInt == 6)){
					if((chessBoard[7][5] == "") && (chessBoard[7][6] == "")){
						movePiece(chessBoard, "h1-f1"); // move rook
						whiteKingHasNotMoved = false;
						whiteRookRightHasNotMoved = false;
						return true;
					}
				}
				else if(whiteRookLeftHasNotMoved && (secondInt == 1) && (secondCharToInt == 2)){
					if((chessBoard[7][2] == "") && (chessBoard[7][3] == "")){
						movePiece(chessBoard, "a1-d1"); //move rook
						whiteKingHasNotMoved = false;
						whiteRookLeftHasNotMoved = false;
						return true;
					}
				}
			}
			if(Math.abs(firstInt - secondInt) == 1){
				whiteKingHasNotMoved = false;
				return true;
			}
		}
		//Pawn
		if(chessBoard[8 - firstInt][firstCharToInt].charAt(1) == 'P'){
			if(firstInt == 2){	//	First move for pawn
				if((Math.abs(firstInt - secondInt) <= 2) && !(secondInt < firstInt) && (chessBoard[8 - firstInt - 1][firstCharToInt] == "")){
					if(((chessBoard[8 - secondInt][secondCharToInt] == "") && (firstCharToInt == secondCharToInt)) || (!(chessBoard[8 - secondInt][secondCharToInt] == "") && (Math.abs(firstInt - secondInt) == Math.abs(firstCharToInt - secondCharToInt)))){
						return true;
					}
				}
			}
			else{	//	All moves after the first move
				if((Math.abs(firstInt - secondInt) == 1) && !(secondInt < firstInt)){
					if(((chessBoard[8 - secondInt][secondCharToInt] == "") && (firstCharToInt == secondCharToInt)) || (!(chessBoard[8 - secondInt][secondCharToInt] == "") && (Math.abs(firstInt - secondInt) == Math.abs(firstCharToInt - secondCharToInt)))){
						return true;
					}
				}
			}
		}
		
		//If all conditions have been check and none return true, return false
		return false;
	}
	
	public static void movePiece(String[][] chessBoard, String pieceMove){
		int firstCharToInt = Character.getNumericValue(pieceMove.charAt(0)) - 10;
		int firstInt = Character.getNumericValue(pieceMove.charAt(1));
		int secondCharToInt = Character.getNumericValue(pieceMove.charAt(3)) - 10;
		int secondInt = Character.getNumericValue(pieceMove.charAt(4));
		String temporaryPiece;
		
		temporaryPiece = chessBoard[8 - firstInt][firstCharToInt];
		chessBoard[8 - firstInt][firstCharToInt] = "";
		chessBoard[8 - secondInt][secondCharToInt] = temporaryPiece;
	}
	
	public static boolean kingsAreAlive(String[][] board){
		boolean whiteKingIsAlive = false;
		boolean blackKingIsAlive = false;
		
		for(int row = 0; row < board.length; row++){
			for(int column = 0; column < board[row].length; column++){
				if((board[row][column] != "") && (board[row][column] == "WK")){
					whiteKingIsAlive = true;
				}
				else if((board[row][column] != "") && (board[row][column] == "BK")){
					blackKingIsAlive = true;
				}
			}
		}
		if(whiteKingIsAlive && blackKingIsAlive){
			return true;
		}
		else{
			return false;
		}
	}
}