import java.util.Scanner;
import java.util.Random;


public class HangmanGame {

  // CONSTANT VARIABLES

  // an enum is used to define the constants for the game difficulty
  public static enum Difficulty {HARD, MEDIUM, EASY, IMPOSSIBLE};
  
  // this constant determines how many guesses the user has. By default it is 6 and that is what the game is built around. Change this at your own risk
  public static final int GUESSES = 6;

  // We will be using random and scanner in this program so we must initialize them as objects
  public static final Scanner SCANNER = new Scanner(System.in);
  public static final Random RANDOM = new Random();

  // These are the predefined dictionaries for each diffuclty level. 
  // selectWord() will randomly select one of these words depending on the game difficulty;
  // EASY has simple words.
  public static final String[] WORDS_DICT_EASY = new String[] {
                                                              "Hello",
                                                              "Orange",
                                                              "Red",
                                                              "Phone",
                                                              "Message",
                                                              "Hangman",
                                                              "Coffee"
                                                            };
  
  // MEDIUM has some programming related words and concepts as well as some slightly harder words
  public static final String[] WORDS_DICT_MED = new String[] {
                                                              "Constant",
                                                              "JavaScript",
                                                              "Worlds",
                                                              "Iterator",
                                                              "Destiny",
                                                              "Scientist",
                                                              "function",
                                                              "Superior",
                                                              "Biased"
                                                            };
 
  // HARD has reasonable but obscure words that one may know but they would have to dig deep into their mind to think of
  public static final String[] WORDS_DICT_HARD= new String[] {
                                                              "Oxymoronic",
                                                              "Distrobution",
                                                              "Canonical",
                                                              "Referenceable",
                                                              "Unreasonably",
                                                              "Obscurity",
                                                              "Likelyhood",
                                                              "Implicit",
                                                              "Explicit",
                                                              "Radioactiveness",
                                                              "Exodus"
                                                            };

  // IMPOSSIBLE consists of unreasonably obscure medical conditions that nobody knows as well as that one word from mary poppins as a joke.
  public static final String[] WORDS_DICT_IMPOSSIBLE=  new String[] {
                                                                      "Supercalifragilisticexpialidocious",
                                                                      "Prosopagnosia",
                                                                      "Encephalopathy",
                                                                      "Fibrodysplasia",
                                                                      "Lachrymation",
                                                                      "Sphenopalatine",
                                                                      "ganglioneuralgia",
                                                                      "onychocryptosis"
                                                                  };

  // These variables are needed by alot of the built in methods of the program. Thus they are here.
  public static String currentWord; // currentword is of course the word currently being guessed
  public static char[] currentWordArray; // currentwordarray is the current word split into an array of char
  public static int mistakes; // mistakes is if course the ammount of mistakes the player has made
  public static Difficulty difficulty; // difficulty contains the current game difficulty that the user has chosen
  public static boolean[] guessedLetters; // guessedletters is an array of 26 bool that determine which letters have been guessed
  public static boolean win = true; // this boolean will determine in isgameover if the user failed or won
  
  // main will simply run the game
  public static void main(String[] args) {
    runGame();
  }

  // The following functions are not necessarily part of the main game loop
  // chartoCapitals converts ant character to uppercase
  public static char charToCapitals(char input) {
    return Character.toUpperCase(input);
  }

  // This method will check if an array of char contains a specific char
  public static boolean charArrayContains(char[] array, char input) {

    boolean arrayContains = false;

    // for loop will continue until the iterator has gone through all of the array indices
    for (int i=0; i < array.length; i++) {

      // if the current index of the iterator is equal to the input character end the loop and return true
      if (array[i] == input) {
        arrayContains = true;
        break;
      }
    
    }

    return arrayContains;
  }

  // splitString will change our string to a char
  public static char[] splitString(String str) {
    
    // final char array that we will return at the end
    char[] splitted = new char[str.length()];

    // for loop will continue until our iterator is at the last index possible of our string
    for (int i=0; i < str.length(); i++) {

      // Add the char at the same index in the array
      splitted[i] = charToCapitals(str.charAt(i));
     
    }

    return splitted;

  }
  
  // selectWord will select the world randomly depending on the chosen difficulty
  public static String selectWord() {

    String word = null;

    // set the current word randomly based on the difficulty
    switch (difficulty) {
      case EASY:
        word = WORDS_DICT_EASY[RANDOM.nextInt(WORDS_DICT_EASY.length)];
        break;
      case MEDIUM:
        word = WORDS_DICT_MED[RANDOM.nextInt(WORDS_DICT_MED.length)];
        break;
      case HARD:
        word = WORDS_DICT_HARD[RANDOM.nextInt(WORDS_DICT_HARD.length)];
        break;
      case IMPOSSIBLE:
        word = WORDS_DICT_IMPOSSIBLE[RANDOM.nextInt(WORDS_DICT_IMPOSSIBLE.length)];
      default:
        break;
    }

    return word;

  }
  

  // init and runGame are our two essential functions they are the main part of this program.
  // init will contain our initial setup of the game. The player will chose the desired settings.
  public static void init() {

    // Welcome messages
    System.out.print("\033[H\033[2J");
    System.out.println("Welcome to hangman!");
    System.out.print("Get a friend to type in a word, or type the letter 'A' for automatic mode (NO SPACES, SPECIAL CHARACTERS, OR NUMBERS): ");

    String userInput = SCANNER.next();

    // if the player chooses automatic mode
    if (charToCapitals(userInput.charAt(0)) == 'A' && userInput.length() == 1) {

      // Ask for what difficulty they would like to play at
      System.out.print("Please select a difficulty (e for easy / m for medium / h for hard / i for impossible) ");

      char diffSelected = charToCapitals(SCANNER.next().charAt(0));

      // set the difficulty to what the player has selected
      switch (diffSelected) {
        case 'E':
          difficulty = Difficulty.EASY;
          break;
        case 'M':
          difficulty = Difficulty.MEDIUM;
          break;
        case 'H':
          difficulty = Difficulty.HARD;
          break;
        case 'I':
          difficulty = Difficulty.IMPOSSIBLE;
          break;
        default:
          System.out.println("Invalid choice exiting...");
          System.exit(0);
          break;
      }
      
      // set the current word randomly
      currentWord = selectWord();

    // For custom mode ask the friend to enter a word
    } else {

      // set the current word to the user input
      currentWord = userInput;

    }

    // convert the word to an array.
    currentWordArray = splitString(currentWord);
    // reset the mistakes to 0 
    mistakes = 0;
    // reset the guessed letters
    guessedLetters = new boolean[26];

  }

  // runGame will contain our main game loop.
  public static void runGame() {

    // run the init method to get the word we are guessing today.
    init();

    // set some variables that we will use throughout all the game
    boolean gameRunning = true;
    char currentGuess = ' ';
    
    // main game loop.
    while (gameRunning) {

      // this will simply clear the console view.
      System.out.print("\033[H\033[2J");

      // print the hangman, the word and then the guessed letters
      printHangman(mistakes);
      printWord();
      
      // Here is where we will check if the user has won the game. It is placed here only for aesthetic reasons. If its placed at the bottom, then the user would not see the final word after guessing the last letter.
      if (isGameOver()) {
        endGame();

        // init will only run again if endGame does not call System.exit()
        init();
        continue;
      }

      printGuesses(guessedLetters);

      // start of the game: get the users guess.
      currentGuess = getGuess();
      
      // try and add the guess to the array of guessed letters.
      try { 
        guessedLetters[currentGuess-65] = true;
      // If the index is out of bounds that means that the user did not input a standard english character
      } catch (IndexOutOfBoundsException e) {

        // tell them to try again and go back to the start of the loop.
        System.out.println("Input a valid letter please."); 
        continue;

      }

      // if the character is not in the current word
      if (!charArrayContains(currentWordArray, currentGuess)) {
        // Add to the ammount of mistakes the user has made
        mistakes++;
      }



    }

  }


  // The following methods belong to the runGame loop. Some could have been directly in the runGame method but fragmenting code is better for readability.
  // getGuess is a function made for the runGame loop
  // it will get the user input for guessing a letter and return it.
  public static char getGuess() {
    System.out.print("\nEnter a letter for your guess: ");
    return charToCapitals( SCANNER.next().charAt(0));

  }

  // printword is a function made for the rungame loop
  // it will print the word but hidden from the user and only start to open up if theyve guesed the word
  public static void printWord() {

    // this loop will go through every leter in the current word
    for (int i=0; i < currentWordArray.length; i++) {

      // if the letter is already guessed
      if (guessedLetters[currentWordArray[i] - 65]) {
        
        // print the letter and continue
        System.out.print(currentWordArray[i] + " ");
        continue;
      }

      // when the if statement requirement is not met, it will skip to here which just prints an underscore.
      System.out.print("_ ");

    }
  }

  // printGuesses is a function made for the runGame loop 
  // it will print which letters the user has guessed
  public static void printGuesses(boolean[] guessesArray) {

    // print how many guesses are left.
    System.out.print("\nYou have " + (GUESSES - mistakes) +" guesses left.\n");

    // this loop is slightly comlicated, but it uses the fact that chars have a corresponding int value to its advantage
    // our iterator I is initialized as 0. We don't actually care that it is a char because it will still functionally work as an int. 
    // our second varaible J starts at 'A'. The loop will continue 26 times. Each time , I going up by one and J going up by one.
    for(char i=0, j = 'A'; i < guessesArray.length; i+=1, j+=1) {
      
      // if I of guesses array is true. That means that the letter we are at on J has already been guessed. 
      if(guessesArray[i]) {

        // Thus we will print J
        System.out.print(j + ", ");

      }

    }

  }

  // isGameover is a function made for the runGame loop
  // it will determine if the user has failed or if the game shall continue
  public static boolean isGameOver() {

    // The result is true by default;
    boolean result = true;

    // this will loop though all the letters in the current word and determine if they have been guessed;
    for (int i=0; i < currentWordArray.length; i++) {

      // if even one of them arent guessed we know the user has not guessed the word, therfore the game is not over
      if (!guessedLetters[currentWordArray[i] - 65]) {
        result = false;

        // we can break and end the loop here
        break;

      }

    }

    
    // If the user has 6 mistakes, the game is over
    if (mistakes >= 6) {
      
      result = true;
      win = false;
    }

    return result;


  }

  // endGame will first ask the user if they want to play again.
  // If they do the function will do nothing.
  // If they dont the function will exit the program.
  public static void endGame() {

    // Congrats!
    if (win) {
      System.out.println("\n\nYOU GUESSED THE WORD CONGRATS!");
    } else {
      System.out.println("\n\nGAME OVER, You failed! The word was "  + currentWord);
      
    }
   

    // Ask the user if they want to play again
    System.out.print("Would you like to play again? (y for yes / anything else for no): ");
    char playAgain = charToCapitals(SCANNER.next().charAt(0));

    // Switch case will determine what the method will od
    switch (playAgain) {

      // Y will simply break and end the method here
      // the main game loop will continue on and call init() again
      case 'Y':
        break;

      // anything else will completley exit the program
      default:
        System.exit(1);
    }


  }

  //printHanging takes as input the number of misses the player has made,
  //and prints out the hangman corresponding to the number of misses they have made
  //printHanging does not return any values
  public static void printHangman(int misses) {
    //Initially, no part of the hangman should be drawn, leave blank spaces for each
    String head = " ", body = " ", larm = " ", rarm = " ", lleg = " ", rleg = " ";
    switch (misses){
      default: head = "0"; //After making 6 or more misses, we add on the head - the player has lost
      case 5: rleg = "\\"; //After making 5 or more misses, we draw the right leg
      case 4: lleg = "/";  //After making 4 or more misses, we draw the left leg
      case 3: rarm = "\\"; //After making 3 or more misses, we draw the right arm
      case 2: larm = "/";  //After making 2 or more misses, we draw the left arm
      case 1: body = "|";  //After making 1 or more misses, we draw the left arm
      case 0: ;            //With 0 misses, nothing should be drawn
    }    

    //Print statement which draws the hangman
    System.out.println("___________\n" +
                       "|         |\n" +
                       "|         " + head +"\n" +
                       "|        " + larm + body + rarm + "\n" +
                       "|       " + larm + " " + body + " " + rarm + "\n" +
                       "|        " + lleg + " " + rleg + "\n" +
                       "|       " + lleg + "   " + rleg + "\n" +
                       "|__________\n");
  } 

}
