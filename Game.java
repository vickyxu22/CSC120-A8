import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * This class Game implements the Contract interface and provides functionality for interacting with items in a game world.
 */
public class Game implements Contract {
    private Map<String, Integer> fruitTrees;
    private int experiencePoints;
    private String season;
    private Stack<String> actionHistory; 
    

    public Game() {
        fruitTrees = new HashMap<>();
        fruitTrees.put("place1", 10); 
        fruitTrees.put("place2", 5); 
        experiencePoints = 0;
        season = "spring"; 
        actionHistory = new Stack<>(); 
    }

    /**

    The grab method allows user to grab a fruit, and add the length of the fruit's name to the experience points of the player.
    @param fruit A string representing the name of the fruit to be grabbed.
    */
    public void grab(String fruit) {
        System.out.println("Grabbing " + fruit);
        int fruitLength = fruit.length();
        experiencePoints += fruitLength;
        System.out.println("You gained " + fruitLength + " experience points.");
    }
    /**

    The drop method allows user to drop a fruit, and remove the length of the fruit's name to the experience points of the player.
    @param fruit A string representing the name of the fruit to be dropped.
    @return A string indicating the fruit dropped.
    */
    public String drop(String fruit) {
        System.out.println("Dropping " + fruit);
        int fruitLength = fruit.length();
        experiencePoints -= fruitLength;
        System.out.println("You lost " + fruitLength + " experience points.");
        return "Dropped " + fruit;
    }

    /**

    The examine method is used to examine a fruit tree and obtain information about the fruit on the tree. 
    @param fruit A string representing the name of the fruit to be examined.
    */
    public void examine(String fruit) {
        System.out.println("Examining " + fruit);
        if (fruitTrees.containsKey(fruit)) {
            int fruitCount = fruitTrees.get(fruit);
            System.out.println("There are " + fruitCount + " " + fruit + "s on the tree.");
        } else {
            System.out.println("You do not see any " + fruit + " tree here.");
        }
        System.out.println("Your current experience points: " + experiencePoints);
    }
    /**

    The use method allows user to use a fruit. The experience points of the player will decrease 2 points per use.
    @param fruit A string representing the name of the fruit to be used.
    */
    public void use(String fruit) {
        experiencePoints -= 2;
        System.out.println("Used " + fruit + ". Experience points decremented by 2. Current experience points: " + experiencePoints);
    }
    /**

    The walk method is used for gamers to walk in a specified direction.
    @param direction A string representing the direction in which the player wants to walk.
    @return A boolean value indicating whether the player is walking or not.
    */
    public boolean walk(String direction) {
        boolean isWalking = false;
        if (direction.equalsIgnoreCase("forward") || direction.equalsIgnoreCase("backward") ||
            direction.equalsIgnoreCase("left") || direction.equalsIgnoreCase("right")) {
            System.out.println("Walking " + direction);
            isWalking = true;
        } else {
            System.out.println("Invalid direction");
        }
        return isWalking;
    }
    /**

    The fly method is used for gamers to fly in a specified direction.
    @param direction A string representing the direction in which the player wants to walk.
    @param x An integer representing the x-coordinate of the destination.
    @param y An integer representing the y-coordinate of the destination.
    @return A boolean value indicating whether the player is flying or not. 
    */
    public boolean fly(int x, int y) {
        boolean isFlying = false;
        if (x >= 0 && y >= 0) {
            System.out.println("Flying to coordinates (" + x + ", " + y + ")");
            isFlying = true;
        } else {
            System.out.println("Invalid coordinates");
        }
        return isFlying;
    }
    
    /**

    The shrink method is used to shrink the fruit count in all places during winter season.
    @return A Number object representing the result of the shrink operation. Always returns 0.
    @throws NullPointerException If the "season" field is null.
    */
    public Number shrink() {
        System.out.println("Shrinking number");
        if (season.equals("winter")) {
            for (String place : fruitTrees.keySet()) {
                int fruitCount = fruitTrees.get(place);
                fruitCount -= 10;
                if (fruitCount < 0) {
                    System.out.println("Warning: Fruit count in " + place + " is negative.");
                    fruitTrees.put(place, 0);
                    System.out.println("The place " + place + " is now closed.");
                } else {
                    fruitTrees.put(place, fruitCount);
                }
            }
        }
        return 0;
    }
    /**
     * The grow method is used to grow the fruit count in all places during spring or summer season.
     * @return An integer representing the final fruit count after the grow operation.
     * @throws NullPointerException If the "season" field is null.
     */
    public Number grow() {
        System.out.println("Growing number");
        if (season.equals("spring") || season.equals("summer")) {
            for (String place : fruitTrees.keySet()) {
                int fruitCount = fruitTrees.get(place);
                fruitCount += 100;
                fruitTrees.put(place, fruitCount);
            }
            System.out.println("All fruit trees have been replenished with fruits.");
            if (season.equals("spring") || season.equals("summer")) {
                experiencePoints += 10;
                System.out.println("You gained 10 experience points for visiting the fruit trees during " + season + ".");
            }
        }
        return 0;
    }
    /**
     * The rest method is used to allow the player to rest and gain 2 experience points/time.
     */
    public void rest() {
        System.out.println("Resting");
        experiencePoints += 2;
        System.out.println("You gained 2 experience points for resting.");
    }
     /**
     * The undo method is used to allow the player to undo the prior step.
     */
    public void undo() {
        System.out.println("Undoing last action");
        if (!actionHistory.isEmpty()) {
            String lastAction = actionHistory.pop();
            if (lastAction.equals("grab")) {
                String item = actionHistory.pop(); 
                drop(item);
                System.out.println("Last action (grab) undone: Dropped " + item);
            } else if (lastAction.equals("drop")) {
                String item = actionHistory.pop();
                grab(item);
                System.out.println("Last action (drop) undone: Grabbed " + item);
            } else if (lastAction.equals("rest")) {
                int points = Integer.parseInt(actionHistory.pop());
                experiencePoints -= points;
                System.out.println("Last action (rest) undone: Subtracted " + points + " experience points");
            } else {
                System.out.println("Unable to undo last action: Unknown action type");
            }
        } else {
            System.out.println("No actions to undo");
        }
    }

    public void setSeason(String season) {
        this.season = season;
    }
    
    public String getSeason() {
        return season;
    }
    
    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }
    
    public int getExperiencePoints() {
        return experiencePoints;
    }
}
