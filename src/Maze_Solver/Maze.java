/**
 * File: Maze.java
 * Author: Kalis Sandlin
 * Created: MAY 5, 2026
 * Modified: MAY 5, 2026
 * Description: This file creates a maze object that will be used to solve the maze. 
 * It contains a 2D array of cells that will be used to represent the maze. 
 * It also contains methods to create the maze and solve the maze.
 */
package Maze_Solver;

import java.util.ArrayList;
import java.util.Collections;

public class Maze {
	// Grid dimensions and maze storage. Access pattern is maze[column][row].
	private int columns, rows;
	private Cell[][] maze;
	// Start and end coordinates chosen on maze edges.
	private int startColumn, startRow, endColumn, endRow;
	// DFS path encoded as: column * rows + row.
	private ArrayList<Integer> solutionPath = new ArrayList<>();

	/** Default constructor that creates a 10x10 maze */
	public Maze() {
		this(10, 10);
	}

	/** Constructor that creates a maze with the specified number of columns and rows */
	public Maze(int columns, int rows) {
		setColumns(columns);
		setRows(rows);
		setMaze(new Cell[columns][rows]);
		// Initialize the maze with blank cells
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				// Creates a cell with all walls up, this will be the default for the maze
				maze[i][j] = new Cell(true, true, true, true, false);
			}
		}
	}
	
	/** Set Start point */
	public void setStartPoint(int column, int row) {
		setStartColumn(column);
		setStartRow(row);
	}

	/** Set End point */
	public void setEndPoint(int column, int row) {
		setEndColumn(column);
		setEndRow(row);
	}

	/** Randomly places start and end on different outer edges and opens their boundary walls */
	public void placeStartAndEnd() {
		// Pick distinct edges so the maze has meaningful traversal across the grid.
		int startEdge = (int) (Math.random() * 4);
		int endEdge = (startEdge + 1 + (int) (Math.random() * 3)) % 4;

		setStartPoint(randomColumnOnEdge(startEdge), randomRowOnEdge(startEdge));
		setEndPoint(randomColumnOnEdge(endEdge), randomRowOnEdge(endEdge));

		openBoundaryWall(startColumn, startRow, startEdge);
		openBoundaryWall(endColumn, endRow, endEdge);
	}

	/** Returns a random column for a given edge (fixed for top/bottom, random for left/right) */
	private int randomColumnOnEdge(int edge) {
		switch (edge) {
			case 0: return 0;
			case 1: return columns - 1;
			default: return (int) (Math.random() * columns);
		}
	}

	/** Returns a random row for a given edge (fixed for left/right, random for top/bottom) */
	private int randomRowOnEdge(int edge) {
		switch (edge) {
			case 2: return 0;
			case 3: return rows - 1;
			default: return (int) (Math.random() * rows);
		}
	}

	/** Opens the outer boundary wall of a cell on a given edge */
	private void openBoundaryWall(int column, int row, int edge) {
		Cell cell = getCell(column, row);
		// Edge mapping: 0 left, 1 right, 2 top, 3 bottom.
		switch (edge) {
			case 0: cell.setLeft(false);   break;
			case 1: cell.setRight(false);  break;
			case 2: cell.setTop(false);    break;
			case 3: cell.setBottom(false); break;
		}
	}
	
	
	/** Method to return cell position */
	public Cell getCell(int column, int row) {
		return maze[column][row];
	}

	/** Recursive method to generate the maze */
	public void generateMaze(int column, int row) {
		Cell current = getCell(column, row);
		current.setVisited(true);

		ArrayList<Integer> directions = new ArrayList<>();
		directions.add(0); // Left	
		directions.add(1); // Right
		directions.add(2); // Up
		directions.add(3); // Down
		// Shuffle directions so DFS carves a different maze each run.
		Collections.shuffle(directions);
		
		for (int direction : directions) {
			switch(direction) {
				case 0: // Left
					if (column > 0 && !getCell(column - 1, row).isVisited()) {
						// Carve both touching walls to keep wall symmetry valid.
						current.setLeft(false);
						getCell(column - 1, row).setRight(false);
						generateMaze(column - 1, row);
					}
					break;
				case 1: // Right
					if (column < getColumns() - 1 && !getCell(column + 1, row).isVisited()) {
						current.setRight(false);
						getCell(column + 1, row).setLeft(false);
						generateMaze(column + 1, row);
					}
					break;
				case 2: // Up
					if (row > 0 && !getCell(column, row - 1).isVisited()) {
						current.setTop(false);
						getCell(column, row - 1).setBottom(false);
						generateMaze(column, row - 1);
					}
					break;
				case 3: // Down
					if (row < getRows() - 1 && !getCell(column, row + 1).isVisited()) {
						current.setBottom(false);
						getCell(column, row + 1).setTop(false);					
						generateMaze(column, row + 1);
					}
					break;
			}
		}
	}

	/** Maze solver method */
	public boolean solveFromStart() {
		// Always reset before solve so repeated runs do not mix old paths.
		solutionPath.clear();
		return solveMaze(startColumn, startRow, new boolean[columns][rows]);
	}

	/** Recursive DFS solver from a specific coordinate */
	public boolean solveMaze(int column, int row, boolean[][] visited) {
		// Base case: destination reached.
		if (column == endColumn && row == endRow) {
			solutionPath.add(column * rows + row);
			return true;
		}
		// Recursive case: mark current cell and explore reachable neighbors.
		visited[column][row] = true;
		Cell current = getCell(column, row);
		
		// Direction order controls which valid path DFS finds first.
		if (!current.isLeft() && column > 0 && !visited[column - 1][row]) {
			if (solveMaze(column - 1, row, visited)) {
				// Backtracking step: record path while unwinding recursion.
				solutionPath.add(column * rows + row);
				return true;
			}
		}
		if (!current.isRight() && column < getColumns() - 1 && !visited[column + 1][row]) {
			if (solveMaze(column + 1, row, visited)) {
				solutionPath.add(column * rows + row); // Add the current cell to the solution path
				return true;
			}
		}
		if (!current.isTop() && row > 0 && !visited[column][row - 1]) {
			if (solveMaze(column, row - 1, visited)) {
				solutionPath.add(column * rows + row); // Add the current cell to the solution path
				return true;
			}
		}
		if (!current.isBottom() && row < getRows() - 1 && !visited[column][row + 1]) {
			if (solveMaze(column, row + 1, visited)) {
				solutionPath.add(column * rows + row); // Add the current cell to the solution path
				return true;
			}
		}
		
		return false; // No path found from this cell
	}

	/** Method to print solution path */
	public void printSolutionPath() {
		if (solutionPath.isEmpty()) {
			System.out.println("Solution Path: No path found");
			return;
		}
		System.out.println("Solution Path:");
		for (int i = solutionPath.size() - 1; i >= 0; i--) {
			System.out.println("(" + (solutionPath.get(i) / rows) + ", " + (solutionPath.get(i) % rows) + ")");
		}
	}

	/** Returns a copy of solution path encoded as column * rows + row, ordered start to end */
	public ArrayList<Integer> getSolutionPath() {
		ArrayList<Integer> path = new ArrayList<>();
		// Stored end->start during recursion unwind; return reversed for consumers.
		for (int i = solutionPath.size() - 1; i >= 0; i--) {
			path.add(solutionPath.get(i));
		}
		return path;
	}

	/** Getter and Setter methods */
	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public Cell[][] getMaze() {
		return maze;
	}

	public void setMaze(Cell[][] maze) {
		this.maze = maze;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public void setStartColumn(int startColumn) {
		if (startColumn < 0 || startColumn >= columns)
			throw new IllegalArgumentException("Start column out of bounds: " + startColumn);
		this.startColumn = startColumn;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		if (startRow < 0 || startRow >= rows)
			throw new IllegalArgumentException("Start row out of bounds: " + startRow);
		this.startRow = startRow;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public void setEndColumn(int endColumn) {
		if (endColumn < 0 || endColumn >= columns)
			throw new IllegalArgumentException("End column out of bounds: " + endColumn);
		this.endColumn = endColumn;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		if (endRow < 0 || endRow >= rows)
			throw new IllegalArgumentException("End row out of bounds: " + endRow);
		this.endRow = endRow;
	}
}