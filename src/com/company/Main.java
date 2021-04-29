package com.company;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String str1 = "a+b*c-d/(a+b)";
        String str2 = "((A-(B+C))*D)^(E+F)";
        String str3 = "a+b/(c-d)";
        String message = "To postfix: " + str1 + ", press = 1\n" +
                "To postfix: " + str2 + ", press = 2\n" +
                "To prefix: " + str3 + ", press = 3\n" +
                "Your line, press 4\nTest = 5\nExit = 6";
        boolean inLoop = true;
        do {
            System.out.println(message);
            switch (scanner.nextLine()) {
                case "1" -> toPostfix(str1);
                case "2" -> toPostfix(str2);
                case "3" -> toPrefix(str3);
                case "4" -> example();
                case "5" -> test();
                case "6" -> inLoop = false;
                default -> System.out.println("Error");
            }
        } while (inLoop);
    }

    private static void test() {
        String[] postfixStrings = {
                "A+B-C",
                "(A+B)*(c-d)",
                "A^b*c-D+E/F/(g+H)",
                "a-b/(c*d^e)"
        };
        System.out.println("Postfix");
        for (String s : postfixStrings) {
            System.out.print("Input: ");
            System.out.println(s);
            System.out.print("Result: ");
            System.out.println(postfix(s));
            System.out.println();
        }

        System.out.println("Prefix");
        String[] prefixStrings = {
                "A+B-C",
                "(A+B)*(c-d)",
                "(a+b)*c-(d-e)^(f+g)",
                "a-b/(c*d^e)"
        };
        for (String s : prefixStrings) {
            System.out.print("Input: ");
            System.out.println(s);
            System.out.print("Result: ");
            System.out.println(prefix(s));
            System.out.println();
        }
    }
    private static void example() {
        System.out.println("Press 1 to postfix, Else prefix");
        if ("1".equals(scanner.nextLine())) {
            System.out.println("Enter line");
            toPostfix(scanner.nextLine());
        } else {
            System.out.println("Enter line");
            toPrefix(scanner.nextLine());
        }
    }

    private static void toPostfix(String input) {
        System.out.println(postfix(input));
    }
    private static void toPrefix(String input) {
        String rev1 = reverseString(input);
        System.out.print("Reverse: ");
        System.out.println(rev1);

        String handled = postfix(rev1);
        System.out.print("To postfix: ");
        System.out.println(handled);

        String rev2 = reverseString(handled);
        System.out.print("Reverse, result: ");
        System.out.println(rev2);
    }

    private static String postfix(String input) {
        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();
        char ch;
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                popOperators(stack, output, priorityOf(ch));
            } else if (isOperand(ch)) {
                output.append(ch);
            } else {
                int priority = priorityOf(ch);
                if (!stack.isEmpty() && priority <= priorityOf(stack.peek()))
                    popOperators(stack, output, priority);
                stack.push(ch);
            }
        }
        while (!stack.isEmpty())
            output.append(stack.pop());
        return output.toString();
    }
    private static void popOperators(Stack<Character> stack, StringBuilder output, int priority) {
        char ch;
        while (!stack.isEmpty() && (ch = stack.pop()) != '(') {
            if (priority <= priorityOf(ch)) {
                output.append(ch);
            } else {
                stack.push(ch);
                break;
            }
        }
    }
    private static boolean isOperand(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    private static int priorityOf(char c) {
        return switch (c) {
            case '(' -> 0;
            case ')' -> 1;
            case '+', '-' -> 2;
            case '*', '/' -> 3;
            case '^' -> 4;
            default -> -1;
        };
    }

    private static String prefix(String input) {
        return reverseString(postfix(reverseString(input)));
    }
    private static String reverseString(String input) {
        if (input == null || input.isEmpty())
            return "";
        int i = input.length() - 1;
        StringBuilder builder = new StringBuilder(i + 3);
        while (i != -1)
            builder.append(reverseChar(input.charAt(i--)));
        return builder.toString();
    }
    private static char reverseChar(char c) {
        return c == '(' ? ')' : c == ')' ? '(' : c;
    }
}