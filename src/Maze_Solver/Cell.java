/**
 * File: Cell.java
 * Author: Kalis Sandlin
 * Created: MAY 5, 2026
 * Modified: MAY 5, 2026
 * Description: Subclass that should help with making custom cells for the maze
 */
package Maze_Solver;

public class Cell {
	// Each boolean represents whether a wall exists on that side of the cell.
	// true = wall present, false = open passage.
	private boolean top, bottom, left, right, visited;

	/** Default construct that makes a blank cell */
	public Cell( ) {
		this(true, true, true, true, false);
	}
	
	/**
	 * Creates a cell with explicit wall state.
	 * Generation code starts with all walls true, then carves passages by setting walls false.
	 */
	public Cell(boolean top, boolean bottom, boolean left, boolean right, boolean visited) {
		setTop(top);
		setBottom(bottom);
		setLeft(left);
		setRight(right);
		setVisited(visited);
	}
	
	/** Getter and Setter Methods */
	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isBottom() {
		return bottom;
	}

	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
