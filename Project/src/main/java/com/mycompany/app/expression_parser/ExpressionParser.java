package com.mycompany.app.expression_parser;

/**
 * Created by devlakhova on 1/27/15.
 */
public class ExpressionParser {

    public static int evaluate(String expression) {
        int leftOperand = 0;
        int pos = 0;
        System.out.println("Evaluating " + expression);
        if (expression.startsWith("(")) {
            // find closable token
            int tokensToSkip = 0;
            for (int i = 1; i < expression.length(); i++) {
                Character c = expression.charAt(i);
                if (c == ')') {
                    if (tokensToSkip > 0) {
                        tokensToSkip--;
                        continue;
                    } else {
                        leftOperand = evaluate(expression.substring(1, i));
                        pos = i+2;
                        break;
                    }
                }
                if (c == '(') {
                    tokensToSkip++;
                }

            }
        }
//        else  {

            while (pos < expression.length() - 1) {
                if (Character.isDigit(expression.charAt(pos))) {
                    pos++;
                }
                leftOperand = Integer.valueOf(expression.substring(0, pos));
                if(pos == expression.length()-1) {
                    return leftOperand;
                }
                char operation = expression.charAt(pos);
                int rightOperand = evaluate(expression.substring(pos+1));
                switch (operation) {
                    case '+':
                        System.out.println(expression  + " = " + (leftOperand + rightOperand));
                        return leftOperand + rightOperand;
                    case '-':
                        System.out.println(expression  + " = " + (leftOperand - rightOperand));
                        return leftOperand - rightOperand;
                    case '*':
                        System.out.println(expression  + " = " + (leftOperand * rightOperand));
                        return leftOperand * rightOperand;
                    case '/':
                        System.out.println(expression  + " = " + (leftOperand / rightOperand));
                        return leftOperand / rightOperand;
                }

            }
//        }
        return Integer.valueOf(expression.substring(pos));
    }

    public static int eval(String expr, int from, int to) {
        if (expr.charAt(from) == '(') {
            return eval(expr, from + 1, to - 1);
        } else {
            int pos = from;
            while (pos < to) {
                if (Character.isDigit(expr.charAt(pos))) {
                    pos++;
                } else {
                    int leftOperand = Integer.valueOf(expr.substring(from, pos));
                    char operation = expr.charAt(pos);
                    int rightOperand = eval(expr, pos+1, to);
                    switch (operation) {
                        case '+':
                            System.out.println(expr  + " = " + (leftOperand + rightOperand));
                            return leftOperand + rightOperand;
                        case '-':
                            System.out.println(expr  + " = " + (leftOperand - rightOperand));
                            return leftOperand - rightOperand;
                        case '*':
                            System.out.println(expr  + " = " + (leftOperand * rightOperand));
                            return leftOperand * rightOperand;
                        case '/':
                            System.out.println(expr  + " = " + (leftOperand / rightOperand));
                            return leftOperand / rightOperand;
                    }
                }
            }
            return Integer.valueOf(expr.substring(from, to));
        }
    }

    public static void main(String[] args) {
        String expr = "5-(3+1)";
        System.out.println(eval(expr, 0, expr.length()));
    }


}
