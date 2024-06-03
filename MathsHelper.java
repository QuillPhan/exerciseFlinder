import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
//import MathsHelperTest.QuestionGenerator.Question;


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
    public int input;
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
                        System.out.println("Invalid number Questions. Please try again.");
                        continue;
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number Questions.");
                    scan.next();
                }
                
            }

            setNumQuestions(setNumberQuestions(numQuestions));
            if (confirmSessionDetails(getYearLevel(), getNumQuestions())) {
                System.out.println("Let's begin ...! (press 'Q' at any time to quit)");
                break;
            }else {
                continue;
            }


        }



        // boolean quit = false;
        // while (!quit) {
        //     displayYearMenu();
        //     choice = scan.next();
        //     while (!isYearLevel(choice)) {
        //         System.out.println("That's not a valid number. Please enter valid number (0 - 7)");
        //         choice = scan.next();
        //     }
        //     setYearLevel(Integer.parseInt(choice));
        //     displayQuestionMenu();
        //     choice = scan.next();
        //     while (!isQuestion(choice)) {
        //         System.out.println("That's not a valid number. Please enter valid number (1 - 5)");
        //         choice = scan.next();
        //     }
        //     setnumQuestions(Integer.parseInt(choice));


        //     if (confirmSessionDetails(yearLevel, numQuestions)) {
        //         System.out.println("Let's begin ...! (press 'Q' at any time to quit)");
        //         break;
        //     }else {
        //         continue;
        //     }
        // }
        QuestionGenerator quiz = new QuestionGenerator(getYearLevel());
        setQuiz(quiz);
        quiz.generateQuestions(getNumQuestions());

        // Call askQuestions method
        askQuestions();

        return;
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
        //String yearLevel = (year == 0) ? "Reception" : "Year " + year;
        String yearLevel;
            if (year == 0) {
                yearLevel = "Reception";
            } else {
                yearLevel= "Year " + year;
            }
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
    public int setNumberQuestions(int numQuestions) {
        switch(numQuestions) {
            case 1: return 10;
            case 2: return 20;
            case 3: return 30;
            case 4: return 40;
            case 5: return 50;
            default: return 10;
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

    public static class Student implements YearLevel {
        private YearLevelType yearLevel;
        private RangeLevel rangeLevel;
        private int numQuestions;

        public Student(YearLevelType yearLevel, int numQuestions) {
            this.yearLevel = yearLevel;
            this.numQuestions = numQuestions;
        }

        @Override
        public YearLevelType getYearLevelType(int year) {
            return yearLevel = getYearLevelType(year);
        }

        public boolean confirmSessionDetails(int year, int questions) {
            // TO DO: implement confirmation logic
            return true;
        }
        
    }


    public void askQuestions() {

        int countQuestion = 1;
        int correctCount = 0;
        int totalQuestions = quiz.getQuestions().size();
        int hintCounter = 0;
        //quiz.generateQuestions(getQuestions(numQuestions));
        ArrayList<Question> questions = quiz.getQuestions();
        boolean quit = false;
        String correctAnswer ;
        DecimalFormat df = new DecimalFormat("#.##");
        double calculatedPercentage;
        double totalPercentage;
        
        while (!quit) {
            for (int i = 0; i < getNumQuestions(); i++) {

                System.out.println(questions.get(i).getQuestion());
                System.out.print("Your answer: ");
                //System.out.print(questions.get(i).getAnswer());
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
                if(correctCount == 0){
                    calculatedPercentage = 0;
                }else{
                    quiz.setCalculatedPercentage(correctCount, totalQuestions);
                    calculatedPercentage = quiz.getCalculatedPercentage();
                }
                
                System.out.println("Your current percentage is " + df.format(calculatedPercentage)  + "%.");

                if(countQuestion / 5 == 1){
                    System.out.println("You are doing really well! Maybe try a harder difficulty.");
                    countQuestion=0;
                }
                countQuestion++;

                

            }
        totalPercentage = quiz.getCalculatedPercentage();
        System.out.println("Your total percentage was " + df.format(totalPercentage) + "%");
        System.out.println(getMessageForPercentage(totalPercentage));
        System.out.print("Did you want to start a new Session or Quit (S/Q)? ");
        String response = scan.next().toUpperCase();
    
        if (response.equals("Q")) {
            quit = true;
        } else if (response.equals("S")) {
            // Restart the program from the beginning
            letsPlay();
        } else {
            System.out.println("Sorry that input was not valid. Did you want to start a new Session or Quit (S/Q)?");
        }

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


    public class Question {
    
        private String question;
        private String answer;        
    
        public Question(int min, int max, char[] operations) {
        Random random = new Random();
        
            int value1 = random.nextInt(max - min + 1) + min;
            int value2 = random.nextInt(max - min + 1) + min;
            char operation = operations[random.nextInt(operations.length)];
    
            if (operation == '/' || operation == '%') {
                while (value2 == 0) {
                    value2 = random.nextInt(max - min + 1) + min;
                }
            }
    
            question = value1 + " " + operation + " " + value2 + " = " ;
            //System.out.println(question);
            switch (operation) {
                case '+':
                    answer = String.valueOf(value1 + value2);
                    break;
                case '-':
                    answer = String.valueOf(value1 - value2);
                    break;
                case '*':
                    answer = String.valueOf(value1 * value2);
                    break;
                case '/':
                    answer = String.valueOf((int) Math.round((double) value1 / value2));
                    break;
                case '%':
                    int quotient = value1 / value2;
                    int remainder = value1 % value2;
                    answer = quotient + "r" + remainder;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation");
            }
        }
    
        public String getQuestion() {
            return question;
        }
    
        public void setQuestion(String question) {
            this.question = question;
        }
    
        public String getAnswer() {
            return answer;
        }
    
        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }

    
public class QuestionGenerator {
    
    private int min;
    private int max;
    private char[] operations;
    private ArrayList<Question> questions;
    private List<String> correctAnswers = new ArrayList<>();
    private List<String> wrongAnswers = new ArrayList<>();
    private double calculatedPercentage;
    private int totalQuestions;

    public QuestionGenerator(int yearLevel) {
        min = findMin(yearLevel);
        max = findMax(yearLevel);
        operations = findOperations(yearLevel);
        questions = new ArrayList<>();
        
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public char[] getOperations() {
        return operations;
    }

    public void setOperations(char[] operations) {
        this.operations = operations;
    }

    public void setCalculatedPercentage(int correctCount, int totalQuestions) {
        this.calculatedPercentage =  (double) correctCount / totalQuestions * 100;
    }    

    public double getCalculatedPercentage() {
        return calculatedPercentage;
    }

    public static int findMin(int year) {
        switch (year) {
            case 0: return 0;
            case 1: return 0;
            case 2: return 0;
            case 3: return 0;
            case 4: return 0;
            case 5: return -999;
            case 6: return -999;
            case 7: return -9999;
            default: throw new IllegalArgumentException("Invalid year: " + year);
        }
    }

    public static int findMax(int year) {
        switch (year) {
            case 0: 
                return 9;
            case 1: 
                return 9;
            case 2:
                return 9;
            case 3:
                return 99;
            case 4:
                return 99;
            case 5:
                return 999;
            case 6:
                return 999;
            case 7:
                return 9999;
            default:
                throw new IllegalArgumentException("Invalid year level");
        }
    }

    public static char[] findOperations(int year) {
        switch (year) {
            case 0:
                return new char[] {'+'};
            case 1:
                return new char[] {'+', '-'};
            case 2:
                return new char[] {'+', '-'};
            case 3:
                return new char[] {'+', '-', '*', '/'};
            case 4:
                return new char[] {'+', '-', '*', '/'};
            case 5:
                return new char[] {'+', '-', '*', '/'};
            case 6:
                return new char[] {'+', '-', '*', '/'};
            case 7:
                return new char[] {'+', '-', '*', '/', '%'};
            default:
                throw new IllegalArgumentException("Invalid year level");
        }
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void generateQuestions(int num) {
        //Random random = new Random();
        for (int i = 0; i < num; i++) {
            questions.add(new Question(min, max, operations));
            //answers[i] = questions.get(i).getAnswer();
            correctAnswers.add(questions.get(i).getAnswer());
        }
    }
    // public void generateQuestions(){

    // }
}



    public static void main(String[] args) {
        new MathsHelper().letsPlay();
    }
}