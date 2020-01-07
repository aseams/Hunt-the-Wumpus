package wumpustest;
import java.util.*;
import java.lang.*;
import java.util.concurrent.ThreadLocalRandom;
//import org.apache.commons.lang3.ArrayUtils;

/*
NOTES:
    - Add method for Wumpus to move around map. Does not hunt player.
    - Display score whenever player finds gold
*/

/**
 *
 * @author Andrew Seamon
 */
public class WumpusTest{
/*
 * Game: Hunt The Wumpus
 * 
 * Rules:
 *    - Each turn, player can choose to move (or shoot) into an adjacent room.
 *    - Player can move only north, south, east, or west.
 *    - There will be no map, so a pencil and paper will be useful.
 *    - Gold will spawn randomly as you move throughout the cave system.
 *         Each piece scores the player 5 points.
 *    - A random amount of gold (1 - 5 pieces) will be dropped if you
 *         are picked up by a bat.
 *    - If there is rustling in the distance, look out for bats. They will move
 *         you to a random room. You [might] not have visited said room before.
 *    - If a cold breeze is felt, watch out for a pit.
*/
    
    
    static String[] roomValues = 
            {"  G  ", "     ", "  G  ", "  G  ", "  G  ",

            "  P  ", "     ", "     ", "  P  ", "  G  ", 

            "  P  ", "     ", "  W  ", "     ", "  B  ", 

            "     ", "     ", "     ", "     ", "  B  ", 

            "     ", "     ", "     ", "     ", "     ",
            };
        
    static int[] visited = 
            {0,  0,  0,  0,  0,

             0,  0,  0,  0,  0,

             0,  0,  0,  0,  0,

             0,  0,  0,  0,  0,

             0,  0,  0,  0,  0
             };
    
    static int currentRoom = 0;
    static int totalArrows = 5;
    static int score = 0;
    
    /**
     * Shuffles map, gets initial room, starts pre-game set up,
     * shows instructions, then asks user to start game. 
     * @param args Unused
     * @throws ArrayIndexOutOfBoundsException Thrown if game messes up and picks bad room
     * @throws InterruptedException Thrown in showInstructions() because of Thread.sleep()
     */
    public static void main(String[] args) throws ArrayIndexOutOfBoundsException, InterruptedException{
        
        Scanner k = new Scanner (System.in);
        Random r = new Random();
        
        int startRoom = 0;
        Object startGame;
        
        PreGame.shuffleArray(WumpusTest.roomValues);                                       // Shuffles the indexes in roomValues to allow for a random map
        
        startRoom = r.nextInt(24 - 0) + 0;
        
        PreGame.showInstructions();                                             // Shows instructions
        
        System.out.println("\n\nReady to play? y/n");
            startGame = k.next();
            
        if(startGame.equals("y") || startGame.equals("yes")){}
        else{System.exit(0);}
            
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                         + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                         + "You're stuck in a cave system"
                         + "\noccupied by a Wumpus. Kill"
                         + "\nthe Wumpus...or die trying.\n");
        
        PreGame.findStartRoom(startRoom);     // Places player in the startingRoom
        
        playGame();                     // Initiates the true start of the game. Asks user to move or shoot, and sends data to either move() or shoot().
        
    }
    
    /**
     * Controls what user will do (move or shoot). Also prints map, and checks if enemies are nearby.
     * @throws InterruptedException Thread.sleep()
     */
    public static void playGame() throws InterruptedException {
        
        String choice, extra;
        Scanner k = new Scanner (System.in);
        
        Move move = new Move();
        Shoot shoot = new Shoot();
        
        PreGame.printMap();
        
        Move.nearbyEntities();
        
        Thread.sleep(500);
        
        do{
            
            System.out.println("Move or shoot?");
                choice = k.nextLine();
                
            if(choice.equalsIgnoreCase("help")){
                PreGame.showInstructions();
                System.out.println("Strike any key to continue.");
                    extra = k.next();
            }
            if(choice.equalsIgnoreCase("move"))
                move.move();
            else if(choice.equalsIgnoreCase("shoot")){
                
                if(totalArrows == 0){
                    System.out.println("You have no more arrows");
                    break;
                }
                
                shoot.shoot();
                
            }
            else
                System.out.println("\n================================="
                                 + "\nInvalid choice. Please try again."
                                 + "\n=================================\n");
            
        }while(!choice.equals("move") || !choice.equals("shoot"));
        
    }
    
    /**
     * Checks for available rooms.
     * Example: If player is on top row, North is unavailable.
     * @param n North
     * @param s South
     * @param e East
     * @param w West
     */
    public static void availableRooms(boolean n, boolean s, boolean e, boolean w) {
        
        int count = 0;
        while(count == 0){
                // Following block prints a list of directions in which rooms exist
                System.out.println("\nThere are rooms to the:");
                // Checks surrounding rooms to see if they exist. If they do, user is notified.
                if(currentRoom > 4){                                     // Checks if room exists to the north
                    System.out.println("North");
                    n = true;
                }
                if(currentRoom < 20){                                    // Checks if room exists to the south
                    System.out.println("South");
                    s = true;
                }
                if(currentRoom + 1 <= 24 && (currentRoom + 1) % 5 != 0){ // Checks if room exists to the east
                    System.out.println("East");
                    e = true;
                }
                if(currentRoom - 1 >= 0  && currentRoom % 5 != 0){       // Checks if room exists to the west
                    System.out.println("West");
                    w = true;
                }
                count = 1;
                break;
        }
        
    }
    
}