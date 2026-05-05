/**
 * File: Maze_Input_Output.java
 * Author: Kalis Sandlin
 * Created: MAY 5, 2026
 * Modified: MAY 5, 2026
 * Description: File to take Maze input and output it as readable consoles text and later store
 * as a file system that can be loaded back up
 */
package Maze_Solver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

public class Maze_Input_Output {

	/** ASCII characters used for maze drawing. */
	private final static String CORNER = "+";
	private static final String HORIZONTAL_WALL = "---";
	private static final String VERTICAL_WALL = "|";
	private static final String PATH_SOLUTION = ".";
	
	/**
	 * Prints maze walls plus start/end markers.
	 * Each logical maze row renders as two text lines:
	 * 1) top walls, 2) cell interior with left walls.
	 */
	public static void printMaze(Maze maze) {
		int columns = maze.getColumns();
		int rows = maze.getRows();
		
		// Loop through each row in the maze
		for (int row = 0; row < rows; row++) {
			// Line 1: Top walls of current row
			StringBuilder topLine = new StringBuilder();
			for (int col = 0; col < columns; col++) {
				Cell current = maze.getCell(col, row);
				topLine.append(CORNER);
				if (current.isTop()) {
					topLine.append(HORIZONTAL_WALL);
				} else {
					topLine.append("   ");
				}
			}
			topLine.append(CORNER); // closing corner
			System.out.println(topLine.toString());
			
			// Line 2: Interior of current row (left walls and spaces)
			StringBuilder interiorLine = new StringBuilder();
			for (int col = 0; col < columns; col++) {
				Cell current = maze.getCell(col, row);
				if (current.isLeft()) {
					interiorLine.append(VERTICAL_WALL);
				} else {
					interiorLine.append(" ");
				}
				
				// Draw priority: Start and End labels override empty interior.
				if (col == maze.getStartColumn() && row == maze.getStartRow()) {
					interiorLine.append(" S ");
				} else if (col == maze.getEndColumn() && row == maze.getEndRow()) {
					interiorLine.append(" E ");
				} else {
					interiorLine.append("   ");
				}
			}
			// Closing right wall: check the rightmost cell's right wall
			if (maze.getCell(columns - 1, row).isRight()) {
				interiorLine.append(VERTICAL_WALL);
			} else {
				interiorLine.append(" "); // open passage on the right edge
			}
			System.out.println(interiorLine.toString());
		}

		// Final bottom wall line: check each cell's bottom wall
		StringBuilder bottomLine = new StringBuilder();
		for (int col = 0; col < columns; col++) {
			bottomLine.append(CORNER);
			if (maze.getCell(col, rows - 1).isBottom()) {
				bottomLine.append(HORIZONTAL_WALL);
			} else {
				bottomLine.append("   "); // open passage on the bottom edge
			}
		}
		bottomLine.append(CORNER);
		System.out.println(bottomLine.toString());
	}

	/**
	 * Prints maze with solved path overlay.
	 * Display priority is Start, End, then path marker.
	 */
	public static void printMazeWithPath(Maze maze) {
		try {
			writeMazeWithPath(maze, new PrintWriter(System.out));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the maze with solved path overlay to any Writer (console or file).
	 * Display priority is Start, End, then path marker.
	 */
	private static void writeMazeWithPath(Maze maze, Writer writer) throws IOException {
		int columns = maze.getColumns();
		int rows = maze.getRows();
		List<Integer> solutionPath = maze.getSolutionPath();
		String nl = System.lineSeparator(); // platform-safe newline

		// Loop through each row in the maze
		for (int row = 0; row < rows; row++) {
			// Line 1: Top walls of current row
			StringBuilder topLine = new StringBuilder();
			for (int col = 0; col < columns; col++) {
				Cell current = maze.getCell(col, row);
				topLine.append(CORNER);
				if (current.isTop()) {
					topLine.append(HORIZONTAL_WALL);
				} else {
					topLine.append("   "); // open passage above
				}
			}
			topLine.append(CORNER); // closing corner
			writer.write(topLine.toString() + nl);

			// Line 2: Interior of current row (left walls and cell content)
			StringBuilder interiorLine = new StringBuilder();
			for (int col = 0; col < columns; col++) {
				Cell current = maze.getCell(col, row);
				if (current.isLeft()) {
					interiorLine.append(VERTICAL_WALL);
				} else {
					interiorLine.append(" "); // open passage to the left
				}

				// Draw priority: S > E > solution path dot > empty space
				if (col == maze.getStartColumn() && row == maze.getStartRow()) {
					interiorLine.append(" S ");
				} else if (col == maze.getEndColumn() && row == maze.getEndRow()) {
					interiorLine.append(" E ");
				} else if (solutionPath.contains(col * rows + row)) {
					interiorLine.append(" " + PATH_SOLUTION + " "); // part of solved path
				} else {
					interiorLine.append("   ");
				}
			}
			// Closing right wall: check the rightmost cell's right wall
			if (maze.getCell(columns - 1, row).isRight()) {
				interiorLine.append(VERTICAL_WALL);
			} else {
				interiorLine.append(" "); // open passage on the right edge
			}
			writer.write(interiorLine.toString() + nl);
		}

		// Final bottom wall line: check each cell's bottom wall
		StringBuilder bottomLine = new StringBuilder();
		for (int col = 0; col < columns; col++) {
			bottomLine.append(CORNER);
			if (maze.getCell(col, rows - 1).isBottom()) {
				bottomLine.append(HORIZONTAL_WALL);
			} else {
				bottomLine.append("   "); // open passage on the bottom edge
			}
		}
		bottomLine.append(CORNER);
		writer.write(bottomLine.toString() + nl);
		writer.flush(); // ensure all buffered content is written
	}

	/**
	 * Saves maze metadata, solution path coordinates, and ASCII maze to a file.
	 * Output file is created (or overwritten) at maze_output.txt in the project root.
	 */
	public static void fileCreation(Maze maze) {
		// try-with-resources automatically closes the writer when done
		try (
			BufferedWriter writer = new BufferedWriter(new FileWriter("maze_output.txt"));
		) {
			// Write maze metadata header
			writer.write("Maze dimensions: " + maze.getColumns() + " columns x " + maze.getRows() + " rows");
			writer.newLine();
			writer.write("Maze start point: (" + maze.getStartColumn() + ", " + maze.getStartRow() + ")");
			writer.newLine();
			writer.write("Maze end point: (" + maze.getEndColumn() + ", " + maze.getEndRow() + ")");
			writer.newLine();

			// Write each step of the solution path as decoded (column, row) coordinates
			writer.write("Maze solution path (column, row):");
			writer.newLine();
			List<Integer> solutionPath = maze.getSolutionPath();
			for (int i = 0; i < solutionPath.size(); i++) {
				// Decode: encoded = column * rows + row
				writer.write("(" + (solutionPath.get(i) / maze.getRows()) + ", " + (solutionPath.get(i) % maze.getRows()) + ")");
				writer.newLine();
			}

			// Write the full ASCII maze with path overlay
			writer.newLine();
			writeMazeWithPath(maze, writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fileLoading() {
		try (
			BufferedReader reader = new BufferedReader(new FileReader("maze_output.txt"));
		) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line); // For demonstration, just print the loaded file content
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error loading maze from file: " + e.getMessage());
		}
	}
}
