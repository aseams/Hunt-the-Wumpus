/*
 *******************************************************
 * CLASS: PreGame
 * AUTHOR: Andrew Seamon
 * DESCRIPTION: Takes care of 'set up' phase
 *******************************************************
*/
package wumpustest;
import java.text.*;
import java.lang.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
class PreGame extends WumpusTest{

    public static Object[] shuffleArray(Object[] roomValues) {
        
        Random rnd = ThreadLocalRandom.current();
        for (int i = roomValues.length - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            Object a = roomValues[index];
            roomValues[index] = roomValues[i];
            roomValues[i] = a;
        }
        
        return roomValues;
    }
    
    public static void findStartRoom(int startRoom) {
        
        int c = 0, x, findPlayer = 0;
        String player = " YYY ";
        for(x = startRoom; x < 25; ++x){
        
            if("     ".equals(String.valueOf(roomValues[startRoom])) && findPlayer < 1){
                roomValues[startRoom] = String.valueOf(player);
                currentRoom = java.util.Arrays.asList(roomValues).indexOf(" YYY ");
                visited[x] = 1;
                break;
            }
            else{}
            ++startRoom;
            ++c;
        }
    }
    
    public static void printMap() {
        System.out.println("===================================");
        
        for(int i = 0; i < 25; ++i){
            
                if(i % 5 == 0 && i != 0){
                    System.out.println("\n");
                }
                else{}
                    System.out.print("|" + roomValues[i] + "|");    
        }
        
        System.out.println("\n===================================\n");
    }
    
    public static void showInstructions() throws InterruptedException {
        
        System.out.println("\t    ============"
                       + "\n\t    Instructions"
                       + "\n\t    ============");
        System.out.println("\nThere are 25 rooms in this cave system."
                         + "\nDuring each turn, you will be asked to"
                         + "\neither shoot or move.");
        System.out.println("\nSimply type your choice and press ENTER.");
        System.out.println("\nBe on the lookout for [G]old. This will be your score.");
        
        System.out.println("\n\t   ==================="
                         + "\n\t   Moving and Shooting"
                         + "\n\t   ===================");
        System.out.println("\nTo move or shoot, simply enter the"
                         + "\ndesired direction."
                         + "\n"
                         + "\n    [North, South, East, West]    \n");
        
        System.out.println("\n\t========================"
                         + "\n\tEnemies and Obstructions"
                         + "\n\t========================");
        System.out.println("\nIf an enemy or obstruction is in an adjacent"
                         + "\nroom, a warning will appear.");
        System.out.println("\n[W]umpus:"
                         + "\n'You hear a terrifying roar, but you"
                         + "\ndo not know where it's coming from.'");
            System.out.println("\n[B]ats:"
                             + "\n'You hear something rustling around nearby.'");
            System.out.println("\nBottomless [P]it:"
                             + "\n'The wind starts to pick up around you.'");
            
        System.out.println("\n\nRemember:"
                         + "\nIf at any time, you need to see these"
                         + "\ninstructions again, just type 'help'.");
        
    }
    
}