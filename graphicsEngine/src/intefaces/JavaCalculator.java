package intefaces;

import components.*;
import listeners.PSEvent;
import listeners.PSListener;
import runtime.*;
import runtime.events.EventType;

import static runtime.events.EventType.CLICK;

/**
 * Created by Rustam on 28.05.2015.
 */
public class JavaCalculator {
    private static State state = State.FIRST_OPERAND;
    private static String firstOperand;
    private static String secondOperand;
    private static Operation operation = Operation.NONE;
    private static final PSTextField numberField = new PSTextField(155, 320, 180, 30);


    public static void main(String[] args) {
        GraphicInterface graphicInterface = new GraphicInterface();

        PSComponent label1 = new PSLabelField(250, 750, 250, 50, "Welcome!");
        PSComponent label2 = new PSLabelField(150, 700, 250, 50, "You can calculate here.");
        PSComponent calc = new PSWindow(100, 100, 300, 300, "calculator");


        PSComponent b0 = new PSButton(150, 150, 40, 30, "0");
        PSComponent point = new PSButton(200, 150, 40, 30, ".");
        PSComponent equal = new PSButton(250, 150, 40, 30, "=");
        PSComponent b1 = new PSButton(150, 190, 40, 30, "1");
        PSComponent b2 = new PSButton(200, 190, 40, 30, "2");
        PSComponent b3 = new PSButton(250, 190, 40, 30, "3");
        PSComponent b4 = new PSButton(150, 230, 40, 30, "4");
        PSComponent b5 = new PSButton(200, 230, 40, 30, "5");
        PSComponent b6 = new PSButton(250, 230, 40, 30, "6");
        PSComponent b7 = new PSButton(150, 270, 40, 30, "7");
        PSComponent b8 = new PSButton(200, 270, 40, 30, "8");
        PSComponent b9 = new PSButton(250, 270, 40, 30, "9");
        PSComponent plus = new PSButton(300, 150, 40, 30, "+");
        PSComponent minus = new PSButton(300, 190, 40, 30, "-");
        PSComponent mult = new PSButton(300, 230, 40, 30, "*");
        PSComponent divis = new PSButton(300, 270, 40, 30, "/");
        PSComponent clear = new PSButton(350, 320, 40, 30, "c");

        graphicInterface.add(label1);
        graphicInterface.add(label2);
        graphicInterface.add(calc);

        calc.add(numberField);
        calc.add(b0);
        calc.add(point);
        calc.add(equal);
        calc.add(b1);
        calc.add(b2);
        calc.add(b3);
        calc.add(b4);
        calc.add(b5);
        calc.add(b6);
        calc.add(b7);
        calc.add(b8);
        calc.add(b9);
        calc.add(plus);
        calc.add(minus);
        calc.add(mult);
        calc.add(divis);
        calc.add(clear);

        b0.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                buttonAction("0");

            }
        });
        b1.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                buttonAction("1");

            }
        });
        b2.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                buttonAction("2");

            }
        });
        b3.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                buttonAction("3");

            }
        });
        b4.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                buttonAction("4");

            }
        });
        b5.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                buttonAction("5");

            }
        });
        b6.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                buttonAction("6");

            }
        });
        b7.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                buttonAction("7");

            }
        });
        b8.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                buttonAction("8");

            }
        });
        b9.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                buttonAction("9");

            }
        });
        point.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                String text = numberField.getText();
                if(!contains(text, '.')) {
                    buttonAction(".");
                }

            }
        });



        clear.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                numberField.setText("");
                state = State.FIRST_OPERAND;
                operation = Operation.NONE;
                firstOperand = "";
                secondOperand = "";
            }
        });

        plus.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                switch(state){

                    case FIRST_OPERAND:
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.ADD;
                        break;
                    case SECOND_OPERAND:
                        calculate();
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.ADD;
                        break;
                    case OPERATOR:
                        operation = Operation.ADD;
                        break;
                    case RESULT:
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.ADD;
                        break;
                }
            }
        });

        minus.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                switch(state){
                    case FIRST_OPERAND:
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.SUB;
                        break;
                    case SECOND_OPERAND:
                        calculate();
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.SUB;
                        break;
                    case OPERATOR:
                        operation = Operation.SUB;
                        break;
                    case RESULT:
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.SUB;
                        break;
                }
            }
        });

        mult.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                switch(state){

                    case FIRST_OPERAND:
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.MUL;
                        break;
                    case SECOND_OPERAND:
                        calculate();
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.MUL;
                        break;
                    case OPERATOR:
                        operation = Operation.MUL;
                        break;
                    case RESULT:
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.MUL;
                        break;
                }
            }
        });

        divis.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                switch(state){

                    case FIRST_OPERAND:
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.DIV;
                        break;
                    case SECOND_OPERAND:
                        calculate();
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.DIV;
                        break;
                    case OPERATOR:
                        operation = Operation.DIV;
                        break;
                    case RESULT:
                        firstOperand = numberField.getText();
                        state = State.OPERATOR;
                        operation = Operation.DIV;
                        break;
                }
            }
        });

        equal.addListener(CLICK, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                switch (state){

                    case FIRST_OPERAND:
                        break;
                    case SECOND_OPERAND:
                        secondOperand = numberField.getText();
                        state = State.RESULT;
                        calculate();
                        break;
                    case OPERATOR:
                        break;
                    case RESULT:

                        break;
                }
            }
        });

        graphicInterface.setVisible(true);

    }

    private static boolean contains(String text, char c) {
        for(int i = 0 ; i < text.length(); i ++){
            if(text.charAt(i)== c){
                return true;
            }
        }
        return false;
    }

    private static void calculate() {
        double op1 = Double.parseDouble(firstOperand);
        double op2 = Double.parseDouble(secondOperand);
        double res = 0;
        switch (operation) {
            case ADD:
                res = op1 + op2;
                break;
            case SUB:
                res = op1 - op2;
                break;
            case MUL:
                res = op1 * op2;
                break;
            case DIV:
                res = op1 / op2;
                break;
            case NONE:
                res = 0;
                break;
        }
        if(res - ((int) res) == 0.0){
            numberField.setText(Integer.toString((int) res));
        }
        else{
            numberField.setText(Double.toString(res));
        }
    }

    private static void buttonAction(String s) {
        switch (state) {
            case FIRST_OPERAND:
            case SECOND_OPERAND:
                appendNumberField(s);
                break;
            case OPERATOR:
                JavaCalculator.state = State.SECOND_OPERAND;
                numberField.setText(s);
                break;
            case RESULT:
                JavaCalculator.state = State.FIRST_OPERAND;
                numberField.setText(s);
                break;
        }




    }

    private static void moveToSecondOperandState() {
        if(JavaCalculator.state == State.OPERATOR){
            JavaCalculator.state = State.SECOND_OPERAND;
            numberField.setText("abs");
            System.out.println(numberField.getText());

        }
    }

    private static void  appendNumberField(String str) {
        String text = numberField.getText();
        numberField.setText(text + str);
    }

    private enum State {
        FIRST_OPERAND, SECOND_OPERAND, OPERATOR, RESULT
    }

    private enum Operation {
        ADD, SUB, MUL, DIV, NONE;
    }

}