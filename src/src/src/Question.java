import java.util.Random;

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
