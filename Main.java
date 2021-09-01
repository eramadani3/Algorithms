import java.util.Scanner; //Import scanner
import java.util.stream.IntStream;
import java.util.Random;

public class Main {
    public static int turn;
    public static int CPU = 1;
    public static int HUMAN = 2;
    public static int CPUWIN = 3;
    public static int method;
    public static int[] lengths; //Array of lengths
    public static Scanner obj; //create the scanner
    public static int userPos[];
    public static int total;
    public static String colorChoice;
    public static String colors[]; //Array of colors

    public static void main(String[] args) {
        obj = new Scanner(System.in);
        userPos = new int[]{};
        lengths = new int[]{3, 7, 5};
        colors = new String[]{"green", "yellow", "orange"};
        total = IntStream.of(lengths).sum();
        //Start of game with a board of 15 pieces
        //Choosing the person who goes first
        if (total == 15) {
            System.out.println("Do you want to start first? (yes/no): ");
            String starter = obj.nextLine();
            checkFirst(starter);
        }
        play(turn, method);

    }

    public static void personRemove() {
        Scanner obj = new Scanner(System.in);

        boolean done = false;
        while(!done){
            System.out.println("What color do you want to take away from? (lowercase): ");
            colorChoice = obj.nextLine(); //read color
            switch(colorChoice){
                case "green":
                    done = true;
                    break;
                case "yellow":
                    done = true;
                    break;
                case "orange":
                    done = true;
                    break;
                default:
                    System.out.println("Not a color");
            }
        }
        System.out.println("You chose the color " + colorChoice);

        int index = -1;

        //Get index based on color
        switch (colorChoice) {
            case "green":
                index = 0;
                break;
            case "yellow":
                index = 1;
                break;
            case "orange":
                index = 2;
                break;
        }

        int n = 0;
        boolean valid = false;
        while(!valid){
            System.out.println("How many pieces do you want to take?: ");
            int numPieces = obj.nextInt(); // read input of pieces
            if (numPieces == 0) {
                System.out.println("You have to take atleast one piece!");
            } else if (numPieces > lengths[index]) {
                System.out.println("Thats too many pieces!");
            } else if (numPieces < 0) {
                System.out.println("You can't take away a negative value");
            } else {
                valid = true;
                lengths[index] -= numPieces;
                userPos = addX(n, userPos, numPieces);
                System.out.println("You took away " + numPieces);
                System.out.println("There are " + lengths[index] + " pieces left in the " + colorChoice + " row");
            }
        }

    }

    public static void cpuRemove() {
        Random rand = new Random();
        int colorIndex = rand.nextInt(2);

        int maxPieces = lengths[colorIndex];

        while (maxPieces == 0) {
            colorIndex = rand.nextInt(3);
            maxPieces = lengths[colorIndex];
        }
        String color = colors[colorIndex];
        int cpuPieces = rand.nextInt(maxPieces) +1;
        lengths[colorIndex] -= cpuPieces;

        System.out.println("The CPU took " + cpuPieces + " pieces away from the " + color + " row");

        System.out.println("There are " + lengths[colorIndex] + " pieces left in the " + color + " row");
    }



    public static String checkWinner(int turn){
        if (turn == CPU || turn == CPUWIN) {
            System.out.println("You win");
        } else if(turn == HUMAN){
           System.out.println("CPU wins");
        }
        return null;
    }

    public static void cpuWin(){
        Random rand = new Random();

        if(total == 15){
            lengths[2] -= 1;
            System.out.println("The CPU took 1 piece away from the orange row");
        }else{
            int pieces = userPos[userPos.length - 1];

            int colorIndex = rand.nextInt(2);

            boolean good = false;
            while (!good) {
                if (pieces <= lengths[colorIndex]) {
                    good = true;
                } else {
                    colorIndex = rand.nextInt(3);
                }
            }
            String color = colors[colorIndex];
            lengths[colorIndex] -= pieces;
            System.out.println("The CPU took " + pieces + " pieces away from the " + color + " row");
            System.out.println("There are " + lengths[colorIndex] + " pieces left in the " + color + " row");
        }

    }

    //Check who starts the game
    public static void checkFirst(String input){
        if (input.equals("yes")) {
            method = CPU;
            turn = HUMAN;
        } else if(input.equals("no")){
            method = CPUWIN;
            turn = CPUWIN;
        }else{
            System.out.println("That wasnt a right answer so the computer will start first");
            method = CPUWIN;
            turn = CPUWIN;
        }

    }

    //Check if the game is done
    public static boolean isDone(int[] board){
        total = IntStream.of(lengths).sum();
        if(total == 0){
            return true;
        }
        return false;
    }

    //Add how many pieces into userPos array
    public static int[] addX(int n, int arr[], int x) {
        int i;
        // create a new array of size n+1
        int newarr[] = new int[n + 1];
        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];
        newarr[n] = x;
        return newarr;
    }

    public static void play(int turn, int method){
        while (isDone(lengths) == false) {
            System.out.println("There are " + total + " pieces left in the game");
            if (turn == HUMAN) {
                personRemove();
                turn = method;
            }else if(turn == CPU){
                cpuRemove();
                turn = HUMAN;
            }else if(turn == CPUWIN){
                cpuWin();
                turn = HUMAN;
            }
        }
        checkWinner(turn);
        return;
    }

}
