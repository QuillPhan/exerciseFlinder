import java.util.ArrayList;
public class QuestionGenerator {
    
    private int yearLevel;
    private int numQuestions;
    private double calculatedPercentage;
    private int min;
    private int max;
    private char[] operations;
    private ArrayList<Question> questions;

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

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
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

    public QuestionGenerator(int year) {
        this.min = findMin(year);
        this.max = findMax(year);
        this.operations = findOperations(year);
        this.questions = new ArrayList<>();
    }

    public void generateQuestions(int num) {
        for (int i = 0; i < num; i++) {
            this.questions.add(new Question(min, max, operations));
        }
    }

    public QuestionGenerator(int yearLevel, int numQuestions) {
        this.yearLevel = yearLevel;
        this.numQuestions = numQuestions;
        operations = findOperations(yearLevel);
        questions = new ArrayList<>();
    }

    public QuestionGenerator(int min, int max, char[] operations) {
        this.min = min;
        this.max = max;
        this.operations = operations;
    }

    public void generateQuestions() {
        int min = findMin(yearLevel);
        int max = findMax(yearLevel);
        char[] operations = findOperations(yearLevel);

        for (int i = 0; i < numQuestions; i++) {
            questions.add(new Question(min, max, operations));
        }
    }


}
