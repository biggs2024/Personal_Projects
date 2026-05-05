# Maze Solver Notes

Created: May 5, 2026  
Modified: May 5, 2026  
Time Spent: 9:30 AM – 7:00 PM (9.5 hours)

## Project Summary

This is my Java maze project for reenforcing concepts covered over a few semesters and also an exploration of new topics.

Main idea of the project:

1. Build a random maze.
2. Solve the maze from a start point to an end point.
3. Print it in a way that is easy to read in the console.
4. Later compare two solving methods (DFS and BFS).

## What I Have Finished So Far

1. Created a Cell class to store each cell's wall data and visited state.
2. Created a Maze class that builds the 2D maze grid.
3. Added random start and end points on the outer edges.
4. Implemented randomized DFS maze generation.
5. Implemented DFS maze solving.
6. Added solution path tracking so I can show the route.
7. Added ASCII output for the maze.
8. Added start and end markers in output (S and E).
9. Added solved path overlay marker in output (.).
10. Cleaned comments throughout the codebase for readability.
11. Refactored printMazeWithPath to share a private writeMazeWithPath helper used by both console and file output.
12. Implemented file output (fileCreation) — writes maze metadata, solution path coordinates, and ASCII maze to maze_output.txt.
13. Implemented file loading (fileLoading) — reads and prints saved maze file to console.
14. Fixed edge wall bug — start/end openings on the right and bottom edges now render correctly.

## What Is Still In Progress

1. BFS solver.
2. Comparison stats between DFS and BFS.
3. Pending JavaFX Representation of Maze and Solving

## Class Roles

Cell

- Represents one spot in the maze.
- Knows whether each wall exists.
- Stores visited state used by maze logic.

Maze

- Owns the maze grid.
- Creates and manages start/end points.
- Generates the maze layout.
- Solves the maze and stores the path.

Maze_Input_Output

- Handles console printing.
- Prints normal maze view.
- Prints solved maze view with markers.
- Saves maze and solution data to a text file.
- Loads and displays a previously saved maze file.

Maze_Test

- Runs the full flow quickly.
- Lets me verify each stage in order.

## Problems I Ran Into and How I Fixed Them

1. Problem: Maze printing looked broken and misaligned.
Fix: Stopped trying to store display text as maze data and switched to a real Cell[][] structure.
What I learned: Keep data structure and output formatting separate.

2. Problem: Cell class was becoming overloaded.
Fix: Reduced Cell to data only and moved maze logic and printing to the right classes.
What I learned: Smaller class responsibility makes debugging easier.

3. Problem: DFS generation left some cells untouched.
Fix: Tried all directions in a shuffled order instead of trying one random direction.
What I learned: Randomized does not mean incomplete.

4. Problem: Validation numbers were off.
Fix: Counted only internal cell-to-cell connections, not boundary openings.
What I learned: A small counting rule can change whether tests pass.

5. Problem: NullPointerException on solution path.
Fix: Properly initialized the path list before adding elements.
What I learned: Always check object setup before assuming logic is wrong.

6. Problem: Start and end markers were not visible in console output.
Fix: Added marker checks in the interior print logic for each cell.
What I learned: Rendering logic needs clear priority rules.

7. Problem: Path marker could overwrite start/end markers.
Fix: Set display order to Start first, End second, Path third.
What I learned: UI output should have clear symbol priority.

8. Problem: Constructor accepted visited input but did not set it.
Fix: Added visited assignment in the Cell constructor.
What I learned: Constructor parameters should always match constructor behavior.

9. Problem: Start and end openings on the right and bottom edges were not rendering — walls showed as closed even when the boundary was opened.
Fix: Changed the closing right wall and bottom wall rendering logic to check isRight() and isBottom() on the actual cells instead of always drawing a wall.
What I learned: Hardcoded edge drawing will always override the actual cell state. Every wall needs to be looked up, not assumed.

10. Problem: File output and console output duplicated the same rendering loop.
Fix: Extracted a private writeMazeWithPath(Maze, Writer) helper so both paths share one loop. printMazeWithPath wraps System.out in a PrintWriter; fileCreation passes its BufferedWriter.
What I learned: When two methods do the same thing to different destinations, extract the logic and pass the destination in.

## How I Test the Project Right Now

Current test routine:

1. Create maze and place start/end.
2. Print maze before generation.
3. Generate maze and print again.
4. Solve maze.
5. Print maze with path overlay.

Why this helps:

- I can quickly spot where a bug starts.
- I can confirm generation and solving are separate steps.
- I can verify symbols are shown correctly.

## Next Milestones

1. Implement BFS solving.
2. Add comparison output for:
    - path length
    - nodes explored
    - runtime

## Personal Reflection

Despite the struggles today, this was overall a great project to start with. It offered a lot of review opportunity and really showcased what I had a good grasp on and what I didn't. Although I had outside help understanding some new concepts, nothing felt completely foreign — it was more of an extension of core ideas covered over the semester.

One thing I can take away from this is that I really need to get better at separating responsibilities between classes and defining clear class contracts. I tend to give one class far more responsibility than it needs.

For now I'm putting this project aside for a few days. I may work on some UML diagrams to map out the class relationships before I come back to finish the remaining to-do items. Next up: a Sudoku Solver to keep reinforcing the concepts I worked through here.
