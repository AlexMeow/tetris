package controller;

import javax.swing.Timer;

import model.shape;

public class controller {
	//current shape and its location
	private shape currentShape;
	private int currentX = 0;
	private int currentY = 0;

	//board
	private shape.Shapes[] board;
	private int boardWidth;
	private int boardHeight;
	
	//assign STATES and initial
	private boolean isStarted = false;
	private boolean isPaused = false;
	private boolean isFallingFinished = false;
	
	private int score = 0;
	
	private Timer timer;
	
	public controller() {
		
	}
	
	//start or end
	public boolean isStarted() {
		return isStarted;
	}
	
	//running or paused
	public boolean isPaused() {
		return isPaused;
	}
	
	public boolean isFallingFinished() {
		return isFallingFinished;
	}
	
	public boolean isCurrentPieceNoShaped() {
		return currentShape.getPieceShape() == shape.Shapes.NoShape;
	}
	
	//initial
	public void start() {
		
	}
	
	//STATE: pause
	public void pause() {
		
	}
	
	//move or not
	private boolean Move(shape Shape, int X, int Y) {
		//test for its 4 squares
		for(int i = 0; i < 4; i++) {
			int tempX = X + Shape.getX(i);
			int tempY = Y - Shape.getY(i);
			
			//reach the boundaries
			if(tempX < 0 || tempX >= boardWidth)
				return false;
			if(tempY < 0 || tempY >= boardHeight)
				return false;
			
			//already fallen pieces
			if(getShape(tempX, tempY) != shape.Shapes.NoShape)
				return false;
		}
		
		//if ok, update
		currentShape = Shape;
		currentX = X;
		currentY = Y;
		
		//undeveloped: repaint
		
		return true;
	}
	
	//generate new piece
	private void newPiece() {
		currentShape.setRandomShape();
		currentX = boardWidth/2 + 1;
		currentY = boardHeight - 1 + currentShape.minY();
		
		//if cannot generate new piece, game over
		if(!Move(currentShape, currentX, currentY)) {
			currentShape.setPieceShape(shape.Shapes.NoShape);
			timer.stop();
			isStarted = false;//end
			
			//undeveloped: notice "game over"
			
		}
	}
	
	//automatically move down
	public void oneLineDown() {
		if(Move(currentShape, currentX, currentY - 1))
			//if cannot move, it has reached the bottom
			Dropped();
	}
	
	//go bottom at once
	public void directDown() {
		int tempY = currentY;
		while(tempY > 0) {
			if(!Move(currentShape, currentX, temp - 1))
				break;
			tempY--;
		}
		Dropped();
	}
	
	//game control -- fall or finished
	public void gameAction() {
		if(isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}

	//when got bottom, update squares' position and test its STATES
	private void Dropped() {
		for(int i = 0; i < 4; i++) {
			int tempX = currentX + currentShape.getX(i);
			int tempY = currentY + currentShape.getY(i);
			board[(tempY * boardWidth) + tempX] = currentShape.getPieceShape();
		}
		
		testFullLine();
		
		if(isFallingFinished())
			newPiece();
	}
	
	//test and remove full lines
	private void testFullLine() {
		//full line numbers
		int num = 0;
		//test if Line #i is full
		for(int i = boardHeight - 1; i >= 0; i--) {
			boolean flag = true;
			for(int j = 0; j < boardWidth; j++) {
				if(getShape(j, i) == shape.Shapes.NoShape) {
					flag = false;
					break;
				}
			}
			//if it is full, move above lines down
			if(flag) {
				num++;
				for(int p = i;p < boardHeight - 1; p++) {
					for(int q = 0; q < boardWidth; q++) {
						board[(p * boardWidth) + q] = getShape(q, p + 1);
					}
				}
			}
		}
		//update
		if(num > 0) {
			isFallingFinished = true;
			score += num;
			
			//undeveloped: show score
			//undeveloped: repaint
			
			currentShape.setPieceShape(shape.Shapes.NoShape);
		}
	}
	
	private shape.Shapes getShape(int x, int y){
		return board[(y * boardWidth) + x];
	}

	
	public void moveLeft() {
		Move(currentShape, currentX - 1, currentY);
	}
	
	public void moveRight() {
		Move(currentShape, currentX + 1, currentY);
	}
	
	public void rotateLeft() {
		Move(currentShape.getLeftRotatedPiece(), currentX, currentY);
	}
	
	public void rorateRight() {
		Move(currentShape.getRightRotatedPiece(), currentX, currentY);
	}
}
