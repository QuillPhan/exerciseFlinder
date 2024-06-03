import java.util.InputMismatchException;
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
            while (true) {
                try {
                    yearLevel = scan.nextInt();
                    if (yearLevel < 0 || yearLevel > 7) {
                        System.out.println("Invalid year level. Please try again.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid year level.");
                    scan.next();
                }
            }
            setYearLevel(yearLevel);

            displayQuestionMenu();
            while (true) {
                try {
                    numQuestions = scan.nextInt();
                    if (numQuestions < 0 || numQuestions > 5) {
                        System.out.println("Invalid number of questions. Please try again.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number of questions.");
                    scan.next();
                }
            }

            setNumQuestions(numQuestions);
            if (confirmSessionDetails(getYearLevel(), getNumQuestions())) {
                quiz = new QuestionGenerator(yearLevel);
                quiz.generateQuestions(numQuestions);
                System.out.println("Let's begin ...! (press 'Q' at any time to quit)");
                askQuestions();
                
                // Prompt for new session or quit
                while (true) {
                    System.out.println("Did you want to start a new Session or Quit (S/Q)?");
                    String userChoice = scan.next();
                    if (userChoice.equalsIgnoreCase("Q")) {
                        return;
                    } else if (userChoice.equalsIgnoreCase("S")) {
                        letsPlay();
                        return;
                    } else {
                        System.out.println("Sorry that input was not valid.");
                    }
                }
            } else {
                continue;
            }
        }
    }

    public void askQuestions() {
        int correctAnswers = 0;
        int totalQuestions = quiz.getQuestions().size();

        for (int i = 0; i < totalQuestions; i++) {
            Question currentQuestion = quiz.getQuestions().get(i);
            String hintAnswer = generateHint(currentQuestion.getAnswer(), 0);
            int hintCount = 0;

            while (true) {
                System.out.print(currentQuestion.getQuestion());
                if (hintCount > 0) {
                    System.out.print(hintAnswer + ": ");
                }
                String userAnswer = scan.next();

                if (userAnswer.equalsIgnoreCase("Q")) {
                    return;
                }

                if (userAnswer.equalsIgnoreCase("H")) {
                    hintCount++;
                    hintAnswer = generateHint(currentQuestion.getAnswer(), hintCount);
                    if (hintCount >= currentQuestion.getAnswer().length()) {
                        System.out.println("Bad luck that was incorrect. The correct answer was " + currentQuestion.getAnswer() + ".");
	                        break;
	                    }
	                    continue;
	                }
	
	                if (userAnswer.equals(currentQuestion.getAnswer())) {
	                    correctAnswers++;
	                    System.out.println("Correct! Well Done!");
	                    break;
	                } else {
	                    System.out.println("Bad luck that was incorrect. The correct answer was " + currentQuestion.getAnswer() + ".");
	                    break;
	                }
	            }
	
	            double percentage = ((double) correctAnswers / (i + 1)) * 100;
	            System.out.printf("Your current percentage is %.2f%%\n", percentage);
	
	            // Suggest difficulty change after each block of 5 questions
	            if ((i + 1) % 5 == 0) {
	                if (percentage > 75 && yearLevel < 7) {
	                    System.out.println("You are doing really well! Maybe try a harder difficulty.");
	                } else if (percentage < 30 && yearLevel > 0) {
	                    System.out.println("It seems you are having some trouble. Maybe try an easier difficulty.");
	                }
	            }
	        }
	
	        double totalPercentage = ((double) correctAnswers / totalQuestions) * 100;
	        System.out.printf("Your total percentage was %.2f%%\n", totalPercentage);
	
	        if (totalPercentage < 40) {
	            System.out.println("Bad luck. Try practicing with some lower year levels to build your confidence and skills.");
	        } else if (totalPercentage < 50) {
	            System.out.println("That was a good effort, but you may need to work on some expressions.");
	        } else if (totalPercentage < 60) {
	            System.out.println("Congratulations you passed. Keep practicing at this year level to improve your score.");
	        } else if (totalPercentage < 75) {
	            System.out.println("Well done. That was a good effort.");
	        } else if (totalPercentage < 85) {
	            System.out.println("Good job. You should try the next year level in your next test.");
	        } else {
	            System.out.println("Excellent work! You really know your stuff. Try the harder levels next time.");
	        }
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
        int numQuestions = getNumberQuestions(questions);


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




    interface YearLevel {
        YearLevelType getYearLevelType(int yearLevel);
    }

    enum YearLevelType {
        RECEPTION,
        YEAR_1,
        YEAR_2,
        YEAR_3,
        YEAR_4,
        YEAR_5,
        YEAR_6,
        YEAR_7;
        public static YearLevelType getYearLevelType(int year) {
            switch(year) {
                case 0: return RECEPTION;
                case 1: return YEAR_1;
                case 2: return YEAR_2;
                case 3: return YEAR_3;
                case 4: return YEAR_4;
                case 5: return YEAR_5;
                case 6: return YEAR_6;
                case 7: return YEAR_7;
                default: throw new IllegalArgumentException("Invalid year: " + year);
            }
        }
    }
    public static int getNumberQuestions(int numQuestions) {
        switch(numQuestions) {
            case 1: return 10;
            case 2: return 20;
            case 3: return 30;
            case 4: return 40;
            case 5: return 50;
            default: return 10;
        }
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
    public String getMessageForPercentage(double percentage) {
        if (percentage < 40) {
            return "Bad luck. Try practicing with some lower year levels to build your confidence and skills.";
        } else if (percentage < 50) {
            return "That was a good effort, but you may need to work on some expressions.";
        } else if (percentage < 60) {
            return "Congratulations you passed. Keep practicing at this year level to improve your score.";
        } else if (percentage < 75) {
            return "Well done. That was a good effort.";
        } else if (percentage < 85) {
            return "Good job. You should try the next year level in your next test.";
        } else {
            return "Excellent work! You really know your stuff. Try the harder levels next time.";
        }
    }
}