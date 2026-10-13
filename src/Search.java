import javax.print.attribute.HashAttributeSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.Queue;

public class Search {

    public static void main(String[] args) {
        Location p = new Location();
    
        System.out.println(p);
        System.out.println(p.row());
        System.out.println(p.column());

        Set<Location> visitedLocations = new HashSet<>();
        visitedLocations.add(p);
        System.out.println(visitedLocations.contains(p));

    }
     /**
     * Finds the location of the nearest reachable cheese from the rat's position.
     * Returns a 2-element int array: [row, col] of the closest 'c'. If there are multiple
     * cheeses that are tied for the same shortest distance to reach, return
     * any one of them.
     * 
     * 'R' - the rat's starting position (exactly one)
     * 'o' - open space the rat can walk on
     * 'w' - wall the rat cannot pass through
     * 'c' - cheese that the rat wants to reach
     * 
     * If no rat is found, throws EscapedRatException.
     * If more than one rat is found, throws CrowdedMazeException.
     * If no cheese is reachable, throws HungryRatException
     *
     * oooocwco
     * woowwcwo
     * ooooRwoo
     * oowwwooo
     * oooocooo
     *
     * The method will return [0,4] as the nearest cheese.
     *
     * @param maze 2D char array representing the maze
     * @return int[] location of the closest cheese in row, column format
     * @throws EscapedRatException if there is no rat in the maze
     * @throws CrowdedMazeException if there is more than one rat in the maze
     * @throws HungryRatException if there is no reachable cheese
     */
    public static int[] nearestCheese(char[][] maze) throws EscapedRatException, CrowdedMazeException, HungryRatException {
        Location start = ratLocation(maze);

        Set<Location> visited = new HashSet<>();
        Queue<Location> queue = new LinkedList<>();
        queue.add(start);

        while(!queue.isEmpty()) {
            Location current = queue.remove();
            if(visited.contains(current)) {
                continue;
            }
            visited.add(current);
            if(maze[current.row()][current.column()] == 'c') {
                return new int[]{current.row(), current.column()};
            }
            List<Location> neighbors = possibleMoves(maze, current);
            for(Location neighbor : neighbors) {
                queue.add(neighbor);
            }
        }

        throw new HungryRatException();
        
    }

    public static List<Location> possibleMoves(char[][] maze, Location current) {
        int[][] moves = new int[][] {
            {-1, 0}, //UP
            {1, 0}, //DOWN
            {0, -1}, //LEFT
            {0, 1} //RIGHT
        };
        List<Location> validMoves = new ArrayList<>();

        for (int[] move : moves) {
            Location possibleMove = new Location(current.row() + move[0],
                                                 current.column() + move[1]);

            if (possibleMove.row() >= 0 && possibleMove.row() < maze.length &&
                possibleMove.column() >= 0 && possibleMove.column() < maze[0].length && 
            maze[possibleMove.row()][possibleMove.column()] != 'w') {
                validMoves.add(possibleMove);
            }
        }

        return validMoves;
    } 

     public static Location ratLocation(char[][] maze) {
        Location location = null;

        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[r].length; c++) {
                if (maze[r][c] == 'R') {
                    if (location == null) {
                        location = new Location(r, c);
                    } else {
                        throw new CrowdedMazeException();
                    }
                }
            }
        }

        if (location == null) {
            throw new EscapedRatException();
        }

        return location;
    }
}

