import java.util.Scanner;
interface Operation {
    double execute(double a, double b);
}
class Addition implements Operation {
    @Override
    public double execute(double a, double b) {
        return a + b;
    }
}
class Subtraction implements Operation {
    @Override
    public double execute(double a, double b) {
        return a - b;
    }
}
class Multiplication implements Operation {
    @Override
    public double execute(double a, double b) {
        return a * b;
    }
}
class Division implements Operation {
    @Override
    public double execute(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero!");
        }
        return a / b;
    }
}
class Calculator {
    private Operation operation;
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
    public double calculate(double a, double b) {
        if (operation == null) {
            throw new IllegalStateException("Operation not set!");
        }
        return operation.execute(a, b);
    }
}
class OperationFactory {
    public static Operation createOperation(String operator) {
        switch (operator) {
            case "+":
                return new Addition();
            case "-":
                return new Subtraction();
            case "*":
                return new Multiplication();
            case "/":
                return new Division();
            default:
                throw new IllegalArgumentException("Invalid operator!");
        }
    }
}
class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter operator (+, -, *, /): ");
        String operator = scanner.nextLine();
        Operation operation = OperationFactory.createOperation(operator);
        calculator.setOperation(operation);
        System.out.println("Enter first operand: ");
        double operand1 = scanner.nextDouble();
        System.out.println("Enter second operand: ");
        double operand2 = scanner.nextDouble();
        double result = calculator.calculate(operand1, operand2);
        System.out.println("Result: " + result);
        scanner.close();
    }
}
