package oljek.webinarformalobesh.object;

/**
 * Created by Rezure
 */
public class Operation {

    private char type;
    private double numberOne;
    private double numberTwo;

    public Operation(char type, double numberOne, double numberTwo) {
        this.type = type;
        this.numberOne = numberOne;
        this.numberTwo = numberTwo;
    }

    public char getType() {
        return type;
    }

    public double getNumberOne() {
        return numberOne;
    }

    public double getNumberTwo() {
        return numberTwo;
    }

}
