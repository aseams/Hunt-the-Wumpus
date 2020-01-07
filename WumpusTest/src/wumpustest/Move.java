/*
 *******************************************************
 * CLASS: Move
 * AUTHOR: Andrew Seamon
 * DESCRIPTION: Called when player wants to move. Takes
 *              care of anything to do with movement.
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
public class Move extends WumpusTest{
    
    int checkRoom = 0;
    
    /**
     * Asks user which direction to move
     * @throws InterruptedException showInstructions()
     */
    public void move() throws InterruptedException{
        
        Scanner k = new Scanner (System.in);
        int count = 0;
        String direction = "none", blank = "";
        boolean n = false, s = false, e = false, w = false;
        System.out.println("You've chosen to move...");
        
        currentRoom = java.util.Arrays.asList(roomValues).indexOf(" YYY ");
        
        availableRooms(n, s, e, w);
        
        Thread.sleep(500);
        System.out.println("\nWhere would you like to go? You can type"
                         + "\n'list' to see a list of available directions.");
            direction = k.nextLine();

        if(direction.equalsIgnoreCase("help")){
            PreGame.showInstructions();
        }
        if(direction.equalsIgnoreCase("north") && currentRoom - 5 >= 0){
            checkRoom = -5;
            MoveRoom();
        }
        else if(direction.equalsIgnoreCase("south") && currentRoom + 5 <= 24){
            checkRoom = 5;
            MoveRoom();
        }    
        else if(direction.equalsIgnoreCase("east") && currentRoom + 1 <= 24){
            checkRoom = 1;
            MoveRoom();
        }    
        else if(direction.equalsIgnoreCase("west") && currentRoom - 1 >= 0){
            checkRoom = -1;
            MoveRoom();
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
     * Called when player moves to a new room
     * @return Value of players' new room (if initially unoccupied)
     */
    public int MoveRoom() {
        
        boolean move = false;
        boolean gold = false;
        
        if (!roomValues[currentRoom + checkRoom].equals("  W  ")) {           // If Wumpus is not in new room
            if (!roomValues[currentRoom + checkRoom].equals("  B  ")) {       // If bats are not in new room
                if (!roomValues[currentRoom + checkRoom].equals("  P  ")) {   // If new room is not a pit
                    System.out.println("\n============="
                            + "\nMoving north."
                            + "\n=============\n");
                    /* 
                     *  If program gets to this point, 
                     *  then the new room has no hazards,
                     * and the player will now occupy it.
                     */
                    
                    if (roomValues[currentRoom + checkRoom].equals("  G  ")) {
                        gold = true;
                    }
                    
                    roomValues[currentRoom] = "     ";       // Resets value at current room to nothing
                    currentRoom = currentRoom + checkRoom;           // Finds index of new room
                    visited[currentRoom] = 1;                // Sets the room to visited
                    roomValues[currentRoom] = " YYY ";       // Changes value at index of new room
                    PreGame.printMap();                      // Prints new map
                    move = true;
                    nearbyEntities();
                    
                    if (gold == true) {
                        System.out.println("You found gold!");
                        ++score;
                    }
                    
                    return currentRoom;
                }
                if (roomValues[currentRoom + checkRoom].equals("  P  ")) {
                    pit();
                }
            } else if (roomValues[currentRoom + checkRoom].equals("  B  ")) {
                bats();
                return currentRoom;
            }
        } else if (roomValues[currentRoom + checkRoom].equals("  W  ")) {
            wumpus();
        }
        
        return currentRoom;
    }

    /**
     * Checks for entities (Wumpus, bats, pit, gold) in adjacent rooms.
     */
    public static void nearbyEntities(){
        
        try{
            //  Following blocks checks for Wumpus in surrounding rooms
            if(roomValues[currentRoom - 1].equals("  W  ") || roomValues[currentRoom + 1].equals("  W  ")
            || roomValues[currentRoom - 5].equals("  W  ") || roomValues[currentRoom + 5].equals("  W  "))
                System.out.println("==================================="
                               + "\nYou hear a terrifying roar, but you"
                               + "\ndo not know where it's coming from."
                               + "\n===================================");
            //  Following block checks for bats in surrounding rooms
            if(roomValues[currentRoom - 1].equals("  B  ") || roomValues[currentRoom + 1].equals("  B  ")
            || roomValues[currentRoom - 5].equals("  B  ") || roomValues[currentRoom + 5].equals("  B  "))
                System.out.println("=========================================="
                               + "\nYou hear something rustling around nearby."
                               + "\n==========================================");
            //  Following block checks for bottomless pits in surrounding rooms
            if(roomValues[currentRoom - 1].equals("  P  ") || roomValues[currentRoom + 1].equals("  P  ")
            || roomValues[currentRoom - 5].equals("  P  ") || roomValues[currentRoom + 5].equals("  P  "))
                System.out.println("======================================"
                               + "\nThe wind starts to pick up around you."
                               + "\n======================================");
        }
        catch(ArrayIndexOutOfBoundsException e){}
        
    }
    
    /**
     * Called if player walks into bats
     */
    public static void bats(){
    
        System.out.println("You enter the room and are immediately"
                       + "\npicked up by giant bats. While"
                       + "\nattempting to free yourself, you"
                       + "\nlost all of your gold. The bats have"
                       + "\nmoved you to a different room.");
        lostGold();
    
    }
    
    /**
     * Called when player walks into bats
     */
    public static void lostGold(){
    
        // Call this method when player finds bats
        // Method places lost gold randomly around map
        String gold = "  G  ";
        String player = " YYY ";
        String empty = "     ";
        int beforeRoom = currentRoom;
        int index = currentRoom;
        
        int newRoom = 50;
        while(beforeRoom != newRoom){
            
            while(newRoom == 50 || !String.valueOf(roomValues[newRoom]).equals(empty))
                newRoom = (int)(Math.random() * 24);
            
            if(roomValues[newRoom].equals(empty)){
                roomValues[beforeRoom] = "     ";
                currentRoom = newRoom;
                roomValues[currentRoom] = " YYY ";
                beforeRoom = newRoom;
            }
            
        }
        // Randomly places gold around map and lowers score to 0. CURRENTLY NOT RANDOM.
        while(score > 0){
            if("     ".equals(String.valueOf(roomValues[index])) && score > 0){
                roomValues[index] = String.valueOf(gold);
                currentRoom = java.util.Arrays.asList(roomValues).indexOf("  G  ");
                --score;
            }
            
            ++index;
            
            if(index == 25)
                index = 0;
        }
        PreGame.printMap();
        System.out.println("========="
                         + "\nScore: " + score
                         + "\n=========");
    }
    
    /**
     * Called when player walks into Wumpus
     */
    public static void wumpus(){
    
        System.out.println("\n\nYou walk straight into the Wumpus and are"
                         + "\nswiftly devoured. Better luck next time.\n");
        System.out.println("=========="
                         + "\nScore: " + score
                         + "\n==========");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                         + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.exit(0);
    
    }
    
    /**
     * Called when player falls into pit
     */
    public static void pit(){
    
        System.out.println("You fell into a bottomless pit and died."
                         + "\nBetter luck next time.");
        System.out.println("=========="
                         + "\nScore: " + score
                         + "\n==========");
        System.exit(0);
    
    }
    
}