/*
 *******************************************************
 * CLASS: Shoot
 * AUTHOR: Andrew Seamon
 * DESCRIPTION: Called when player wants to shoot. Takes
 *              care of anything to do with shooting.
 *******************************************************
*/
package wumpustest;
import java.text.*;
import java.lang.*;
import java.util.*;

/**
 *
 * @author andys
 */
public class Shoot extends WumpusTest{
    
    int checkRoom = 0;
    /**
     * Asks which direction user will fire an arrow, then calls method for that
     * direction to see what happens.
     * @throws InterruptedException showInstructions()
     */
    public void shoot() throws InterruptedException{
    
        Scanner k = new Scanner(System.in);
        
        String direction;
        boolean n = false, s = false, e = false, w = false;
        
        System.out.println("\nYou have " + totalArrows + " arrows.");
        
        shootRooms(n, s, e, w);
        
        System.out.println("\nWhich direction would you like to shoot?");
            direction = k.nextLine();
            
        --totalArrows;
            
        if(direction.equalsIgnoreCase("help")){
            PreGame.showInstructions();
        }

        if(direction.equalsIgnoreCase("north") && currentRoom - 5 >= 0){
            checkRoom = -5;
            shootWumpus();
        }
        else if(direction.equalsIgnoreCase("south") && currentRoom + 5 <= 24){
            checkRoom = 5;
            shootWumpus();
        }    
        else if(direction.equalsIgnoreCase("east") && currentRoom + 1 <= 24){
            checkRoom = 1;
            shootWumpus();
        }    
        else if(direction.equalsIgnoreCase("west") && currentRoom - 1 >= 0){
            checkRoom = -1;
            shootWumpus();

        }
        else if(direction.equalsIgnoreCase("list")){
            availableRooms(n, s, e, w);
        }
        else{
        System.out.println("\n===================================="
                         + "\nInvalid choice. Please choose again."
                         + "\n====================================");
        }
    }
    
    /**
     * Finds and lists adjacent rooms.
     * @param n North
     * @param s South
     * @param e East
     * @param w West
     */
    public static void shootRooms(boolean n, boolean s, boolean e, boolean w){
    
        int count = 0;
        while(count == 0){
                // Following block prints a list of directions in which rooms exist
                System.out.println("\nYou can shoot:");
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
    /**
     * Called to test if player has hit Wumpus
     *     North: -5
     *     South: +5
     *     East:  +1
     *     West:  -1
     */
    public void shootWumpus(){
        if("  W  ".equals(roomValues[currentRoom + checkRoom]))
            deadWumpus();
        else
            missedWumpus();
    }
    
    /**
     * Called when Wumpus dies
     */
    public static void deadWumpus(){
        System.out.println("\n\nYou killed the Wumpus.");
        System.out.println("\n========="
                         + "\nScore: " + score
                         + "\n=========\n");
        System.out.println("========="
                         + "\nGame Over"
                         + "\n=========");
        System.exit(0);
    }
    
    /**
     * Called when arrow misses Wumpus
     */
    public static void missedWumpus(){
        System.out.println("\n=================="
                         + "\nYour arrow missed."
                         + "\n==================\n");
    }
}