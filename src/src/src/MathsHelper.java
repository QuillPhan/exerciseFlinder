import java.text.DecimalFormat;
import java.util.ArrayList;
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
    private YearLevelType yearLevelType;
    // private QuestionType questionType;
    private RangeLevel rangeLevel;
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
        //Student student = new Student();

        displayWelcome();
        boolean quit = false;

        while (!quit) {
            displayYearMenu();
            choice = scan.next();
            if (choice.equalsIgnoreCase("Q")) {
                return;
            }else {
                while (!isYearLevel(choice)) {
                    System.out.println("That's not a valid number. Please enter valid number (0 - 7)");
                    choice = scan.next();
                    
                }
                setYearLevel(Integer.parseInt(choice));
            }
            
            displayQuestionMenu();
            choice = scan.next();
            if (choice.equalsIgnoreCase("Q")) {
                return;
            }else {
                while (!isQuestion(choice)) {
                    System.out.println("That's not a valid number. Please enter valid number (1 - 5)");
                    choice = scan.next();
                }
                setNumQuestions(Integer.parseInt(choice));
            }
            if (confirmSessionDetails(yearLevel, numQuestions)) {
                System.out.println("Let's begin ...! (press 'Q' at any time to quit)");
                quiz = new QuestionGenerator(yearLevel);
                quiz.generateQuestions(numQuestions);
                askQuestions();
            }else {
                continue;
            }
        System.out.print("Did you want to start a new Session or Quit (S/Q)?");

        choice = scan.next();
        if (choice.equalsIgnoreCase("Q")) {
            //break;
            return;
        }else{
            while (!choice.equalsIgnoreCase("S")) {
                System.out.print("Sorry that input was not valid. Did you want to start a new Session or Quit (S/Q)?");
                choice = scan.next();
                if (choice.equalsIgnoreCase("Q")) {
                    return;
                    //return;
                }
            }
        }
        }
        

        
        
    }

    
            //System.out.println("Let's begin ...! (press 'Q' at any time to quit)");
            
            
            //QuestionGenerator questionGenerator = new QuestionGenerator();
        // setQuiz(questionGenerator);
        // QuestionGenerator quiz = getQuiz();
            // quiz.generateQuestions(getQuestions(numQuestions));
                    // ArrayList<Question> questions = quiz.getQuestions();
                    // for (Question question : questions) {
                    //     System.out.println(question.getQuestion());
                    // }
                    

                
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
        int numQuestions = questions;
        

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
    
    class RangeLevel implements YearLevel {
        private int min;
        private int max;
        private YearLevelType type;

        public RangeLevel(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public RangeLevel(int min, int max, YearLevelType type) {
            this.min = min;
            this.max = max;
            this.type = type;
        }

        public boolean contains(int value) {
            return value >= min && value <= max;
        }

        @Override
        public YearLevelType getYearLevelType(int yearLevel) {
            return this.type = getYearLevelType(yearLevel);
        }
    }

    public void askQuestions() {

        int countQuestion = 1;
        int correctCount = 0;
        int totalQuestions = quiz.getQuestions().size();
        int hintCounter = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        //quiz.generateQuestions(getQuestions(numQuestions));
        ArrayList<Question> questions = quiz.getQuestions();
        boolean quit = false;
        String correctAnswer ;
        
        double calculatedPercentage ;
        double totalPercentage;
        while (!quit) {
            for (int i = 0; i < getQuestions(numQuestions); i++) {

                System.out.println(questions.get(i).getQuestion());
                System.out.print("Your answer: ");
                correctAnswer = questions.get(i).getAnswer();
                
                String input = scan.next();
                if (input.equalsIgnoreCase("Q")) {
                    break;
                }
                while (input.equalsIgnoreCase("H") || input.equalsIgnoreCase("?")) {
                    // Display hint
                    //char[] hint;
                    //hintCounter = 0;
                    char[] hintArray = questions.get(i).getAnswer().toCharArray();
                    char[] hint = new char[hintArray.length];
                    for (int j = 0; j < hintArray.length - hintCounter; j++) {
                        hint[j] = '_';
                    }
                    

                    if(hintCounter == hintArray.length - 1){
                        // System.out.println("Bad luck, that was incorrect. The correct answer was " + questions.get(i).getAnswer() + ".");
                        // totalQuestions++;
                        // double percentage = (double) correctCount / totalQuestions * 100;
                        // System.out.println("Your current percentage is " + percentage + "%.");
                        break;
                    }else{
                        hintCounter++;
                        for (int j = hintArray.length - 1 ; j >= hintArray.length - hintCounter; j--) {
                            hint[j] = hintArray[j];
                        }
                        String hintString = new String(hint); // Convert char[] to String
                        System.out.println(questions.get(i).getQuestion() + " " + hintString +" : ");
                        
                        input = scan.next();
                    }
                    if (input.equalsIgnoreCase("Q")) {
                        return;
                    }
                }
                

                String actualAnswer = input;
                hintCounter = 0;

                // if(hintCounter == questions.get(i).getAnswer().length()){
                //     break;
                // }

                if (actualAnswer.equals(questions.get(i).getAnswer()) ){
                    System.out.println("Correct! Well done!");
                    correctCount++;
                } else {
                    System.out.println("Bad luck, that was incorrect. The correct answer was " + correctAnswer + ".");
                }

                quiz.setCalculatedPercentage(correctCount, totalQuestions);
                calculatedPercentage = quiz.getCalculatedPercentage();
                System.out.println("Your current percentage is " + df.format(calculatedPercentage)  + "%.");

                if(countQuestion / 5 == 1){
                    System.out.println("You are doing really well! Maybe try a harder difficulty.");
                    countQuestion=0;
                }
                countQuestion++;

                //quiz.setCalculatedPercentage(correctCount, totalQuestions);
                //calculatedPercentage = quiz.getCalculatedPercentage();

                

                
            }
            totalPercentage = quiz.getCalculatedPercentage();
                System.out.println("Your total percentage was " + df.format(totalPercentage) + "%");
                System.out.println(getMessageForPercentage(totalPercentage));
        
       
            break;
        }

    }
    // if (totalPercentage < 40) {
                //     System.out.println("Bad luck. Try practicing with some lower year levels to build your confidence and skills.");
                // } else if (totalPercentage >= 40 && totalPercentage < 50) {
                //     System.out.println("That was a good effort, but you may need to work on some expressions.");
                // } else if (totalPercentage >= 50 && totalPercentage < 60) {
                //     System.out.println("Congratulations you passed. Keep practicing at this year level to improve your score.");
                // } else if (totalPercentage >= 60 && totalPercentage < 75) {
                //     System.out.println("Well done. That was a good effort.");
                // } else if (totalPercentage >= 75 && totalPercentage < 85) {
                //     System.out.println("Good job. You should try the next year level in your next test.");
                // } else if (totalPercentage >= 85) {
                //     System.out.println("Excellent work! You really know your stuff. Try the harder levels next time.");
                // }

                // System.out.printf("Your current percentage is %.2f%%%n", calculatedPercentage);
                // if((totalQuestions==0)){
                    
                //     return;
                // }
                // String finish1 = "Your total percentage was " + calculatedPercentage + "%. ";
                // System.out.println(finish1);
// String response = scan.next().toUpperCase();
    
        // if (response.equals("Q")) {
        //     quit = true;
        // } else if (response.equals("S")) {
        //     // Restart the program from the beginning
        //     letsPlay();
        // } else {
        //     System.out.println("Sorry that input was not valid. Did you want to start a new Session or Quit (S/Q)?");
        // }


        ////double overallPercentage = (double) correctCount / totalQuestions * 100;


        // quiz.setCalculatedPercentage(correctCount, totalQuestions);
        // calculatedPercentage = quiz.getCalculatedPercentage();


        ////(double) correctCount / (quiz.getQuestions().indexOf(questions) + 1) * 100;


        // System.out.printf("Your current percentage is %.2f%%%n", calculatedPercentage);
        // if((totalQuestions==0)){
            
        //     return;
        // }
        // String finish1 = "Your total percentage was " + calculatedPercentage + "%. ";
        // System.out.println(finish1);
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

    private static int calculateAnswer(int num1, int num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            case "%":
                return num1 % num2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
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
        this.numQuestions = numQuestions * 10;
    }
    public int getQuestions(int numQuestions) {
        switch(numQuestions) {
            case 1: return 10;
            case 2: return 20;
            case 3: return 30;
            case 4: return 40;
            case 5: return 50;
            default: return 10;
        }
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