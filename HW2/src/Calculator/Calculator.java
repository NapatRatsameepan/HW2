package Calculator;

import java.util.Stack;

public class Calculator {
    private static Calculator instance;

    private Calculator() {}

    // Singleton getInstance method
    public static Calculator getInstance() {
        if (instance == null) {
            instance = new Calculator();
        }
        return instance;
    }

    // Method to evaluate expression using basic arithmetic parsing
    public String calculate(String expression) {
        try {
            double result = evaluateExpression(expression);
            return String.valueOf(result);
        } catch (Exception e) {
            return "Error";
        }
    }

    // Method to evaluate basic arithmetic expression using two stacks
    private double evaluateExpression(String expression) throws Exception {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If current character is a number, parse it
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                values.push(Double.parseDouble(sb.toString()));
                i--; // Adjust for the next iteration
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }

    private double applyOperator(char op, double b, double a) throws Exception {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new Exception("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}
