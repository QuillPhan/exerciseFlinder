import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * An application to help young children practice their mathematics.  The application
 * will tailor arithmetic problems based on the competency of the user.  Command line
 * interaction allows the user to define the level of difficulty and then provide
 * answers to randomly generated problems.
 *
 * @author Your name, student number and FAN here
 */
public class MathsHelper {

    // IMPORTANT! This Scanner variable must remain public and non-final for code testing purposes
    public Scanner scan;   
    
    public QuestionGenerator quiz;
    public int yearLevel;
    public int numQuestions;
    //public int input;
    public String choice;
public MathsHelper() {
    scan = new Scanner(System.in);
}
/**
 * Performs one session of the Maths quiz
 */
public void letsPlay() {
    displayWelcome();
    while (true) {
        displayYearMenu();
        yearLevel = getValidYearLevel();
        setYearLevel(yearLevel);

        displayQuestionMenu();
        numQuestions = getValidNumQuestions();
       
        setNumQuestions(numQuestions);

        if (confirmSessionDetails(getYearLevel(), getNumQuestions())) {
            quiz = new QuestionGenerator(yearLevel);
            quiz.generateQuestions(numQuestions * 10);
            System.out.println("Let's begin ... (press 'Q' at any time to quit)");
            try {
            askQuestions();
            while (true) {
                System.out.println("Did you want to start a new Session or Quit (S/Q)?");
                String userResponse = scan.next();
                if (userResponse.equalsIgnoreCase("Q")) {
                    return;
                } else if (userResponse.equalsIgnoreCase("S")) {
                    System.out.println("Starting a new session...");
                    letsPlay();
                    return;  // Start from the beginning
                } else {
                    System.out.println("Sorry that input was not valid. Did you want to start a new Session or Quit (S/Q)?");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("No input available. Exiting the game.");
        }
            
            // askQuestions();

            // while (true) {
            //     System.out.println("Did you want to start a new Session or Quit (S/Q)?");
            //     String userChoice = scan.next();
            //     if (userChoice.equalsIgnoreCase("Q")) {
            //         return;
            //     } else if (userChoice.equalsIgnoreCase("S")) {
            //         letsPlay();
            //         return;
            //     } else {
            //         System.out.println("Sorry that input was not valid.");
            //     }
            // }
        } else {
            continue;
        }
    }
}

public void askQuestions() {
    int correctAnswers = 0;
    int totalQuestions = quiz.getQuestions().size();
    DecimalFormat df = new DecimalFormat("#.##");
    ArrayList<Question> questions = quiz.getQuestions();

    for (int i = 0; i < totalQuestions; i++) {
        String correctAnswer = questions.get(i).getAnswer();
        String hintAnswer = generateHint(correctAnswer, 0);
        int hintCount = 0;

        while (true) {
            System.out.print(questions.get(i).getQuestion());
            if (hintCount > 0) {
                System.out.print(hintAnswer + ": ");
            }
            String userAnswer = scan.next();

            if (userAnswer.equalsIgnoreCase("Q")) {
                return;
            }

            if (userAnswer.equalsIgnoreCase("H") || userAnswer.equalsIgnoreCase("?")) {
                hintCount++;
                hintAnswer = generateHint(correctAnswer, hintCount);
                if (hintCount >= correctAnswer.length()) {
                    System.out.println("Bad luck that was incorrect. The correct answer was " + correctAnswer + ".");
                    break;
                }
                continue;
            }

            if (userAnswer.equals(correctAnswer)) {
                correctAnswers++;
                System.out.println("Correct! Well Done!");
                break;
            } else {
                System.out.println("Bad luck that was incorrect. The correct answer was " + correctAnswer + ".");
                break;
            }
        }

        double percentage = ((double) correctAnswers / (i + 1)) * 100;
        System.out.println("Your current percentage is " + df.format(percentage) + "%");

        if ((i + 1) % 5 == 0) {
            if (percentage > 75 && yearLevel < 7) {
                System.out.println("You are doing really well! Maybe try a harder difficulty.");
            } else if (percentage < 30 && yearLevel > 0) {
                System.out.println("It seems you are having some trouble. Maybe try an easier difficulty.");
            }
        }
    }

    double totalPercentage = ((double) correctAnswers / totalQuestions) * 100;
    System.out.println("Your total percentage was " + df.format(totalPercentage) + "%");
    System.out.println(getMessageForTotalPercentage(totalPercentage));
}

private String generateHint(String answer, int hintCount) {
    StringBuilder hint = new StringBuilder();
    for (int i = 0; i < answer.length(); i++) {
        if (i < answer.length() - hintCount) {
            hint.append("_");
        } else {
            hint.append(answer.charAt(i));
        }
    }
    return hint.toString();
}

private String getMessageForTotalPercentage(double totalPercentage) {
    if (totalPercentage < 40) {
        return "Bad luck. Try practicing with some lower year levels to build your confidence and skills.";
    } else if (totalPercentage < 50) {
        return "That was a good effort, but you may need to work on some expressions.";
    } else if (totalPercentage < 60) {
        return "Congratulations you passed. Keep practicing at this year level to improve your score.";
    } else if (totalPercentage < 75) {
        return "Well done. That was a good effort.";
    } else if (totalPercentage < 85) {
        return "Good job. You should try the next year level in your next test.";
    } else {
        return "Excellent work! You really know your stuff. Try the harder levels next time.";
    }
}


private int getValidYearLevel() {
    while (true) {
        try {
            int yearLevel = scan.nextInt();
            if (yearLevel < 0 || yearLevel > 7) {
                System.out.println("Invalid year level. Please try again.");
                continue;
            }
            return yearLevel;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid year level.");
            scan.next();
        }
    }
}

private int getValidNumQuestions() {
    while (true) {
        try {
            int numQuestions = scan.nextInt();
            if (numQuestions < 0 || numQuestions > 5) {
                System.out.println("Invalid number of questions. Please try again.");
                continue;
            }
            return numQuestions;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number of questions.");
            scan.next();
        }
    }
}
//-------------------------operational methods------------------------------

    /**
     * Defines the Welcome Message text
     */
    private void displayWelcome() {
        System.out.println("+------------------------------------------------------------------------+");
        System.out.println("|                      Welcome to the Maths Helper.                      |");
        System.out.println("|       Use this application to test your knowledge of mathematics.      |");
        System.out.println("|      This program is intended for children from reception to year 7    |");
        System.out.println("+------------------------------------------------------------------------+");
    }

    /**
     * Defines the Year Menu
     */
    private void displayYearMenu() {
        System.out.println("What is your year level? Choose an option from the list below:");
        System.out.println("+: addition, -: subtraction, *: multiplication, /: division, %: division with remainder");
        System.out.println("[0] Reception [+]");
        System.out.println("[1] Year 1 [+, -]");
        System.out.println("[2] Year 2 [+, -]");
        System.out.println("[3] Year 3 [+, -, *, /]");
        System.out.println("[4] Year 4 [+, -, *, /]");
        System.out.println("[5] Year 5 [+, -, *, /]");
        System.out.println("[6] Year 6 [+, -, *, /]");
        System.out.println("[7] Year 7 [+, -, *, /, %]");




    }

    /**
     * Defines the Question Menu
     */
    private void displayQuestionMenu() {
        System.out.println("How many questions would you like to attempt? Choose an option from the list below:");
        System.out.println("[1] 10 questions");
        System.out.println("[2] 20 questions");
        System.out.println("[3] 30 questions");
        System.out.println("[4] 40 questions");
        System.out.println("[5] 50 questions");
    }

    /**
     * Displays the confirmation message to the user and processes user input to
     * determine the accuracy of the information provided. If correct then
     * return true otherwise return false.
     *
     * @param year the selected Year Menu item {0,1,2,3,4,5,6,7}
     * @param questions the selected Question Menu item {1,2,3,4,5}
     * @return boolean based on user's confirmation of correct data entry
     */
    public boolean confirmSessionDetails(int year, int questions) {
        String yearLevel = (year == 0) ? "Reception" : "Year " + year;
        int numQuestions = questions * 10;


        System.out.print("You are a " + yearLevel + " student and want to do " + numQuestions + " questions. Is this correct (Y/N)?: ");

        String choice = scan.next();
        // if (choice.equalsIgnoreCase("Q")) {
        //     break;
        // }
        if (choice.equalsIgnoreCase("N") || choice.equalsIgnoreCase("Q")) {
            return false;
        }

        return choice.equalsIgnoreCase("Y");
    }

    public boolean isYearLevel(String choice) {
        try {

            int c = Integer.parseInt(choice);
            return c >= 0 && c <= 7;

        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean isQuestion(String choice) {
        try {
            int c = Integer.parseInt(choice);
            return c >= 0 && c <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public QuestionGenerator getQuiz() {
        return quiz;
    }

    public void setQuiz(QuestionGenerator quiz) {
        this.quiz = quiz;
    }
}