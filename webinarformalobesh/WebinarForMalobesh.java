package oljek.webinarformalobesh;

import oljek.webinarformalobesh.object.Operation;

import java.util.Scanner;

public class WebinarForMalobesh {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WebinarForMalobesh calculator = new WebinarForMalobesh();

        Operation operation = calculator.getOperation(scanner);

        if (operation == null)
            return;

        double result = calculator.performAnOperation(operation);

        System.out.println("Ваш результат равен = " + result);
    }

    Operation getOperation(Scanner scanner) {
        System.out.println("Введите пожалуйста тип операции: \"/, *, -, +\"");

        String line = scanner.nextLine();

        if (line.length() > 1) {
            System.out.println("Произошла ошибка! Вы указали неверный тип данных!");
            return null;
        }

        char type = line.charAt(0);

        if (type != '/' && type != '*' && type != '-' && type != '+') {
            System.out.println("Вы ввели неизвестный нам символ! Пожалуйста, повторите попытку, используя данные символы: \"/, *, +, -\"");
            return null;
        }

        System.out.println("Введите пожалуйста два числа с которыми вы хотите провести операцию:");

        double numberOne = scanner.nextDouble();
        double numberTwo = scanner.nextDouble();

        return new Operation(type, numberOne, numberTwo);
    }

    double performAnOperation(Operation operation) {
        if (operation == null)
            return 0;

        switch (operation.getType()) {
            case '/':
                return operation.getNumberOne() / operation.getNumberTwo();

            case '*':
                return operation.getNumberOne() * operation.getNumberTwo();

            case '-':
                return operation.getNumberOne() - operation.getNumberTwo();

            case '+':
                return operation.getNumberOne() + operation.getNumberTwo();
        }

        return 0;
    }

}
