// Blake Haddad & Andy Nguyen
// CS-570
// Reverse Polish Calculator Notation Assignment

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

class reversePolishCalc{

    Scanner reader = new Scanner(System.in);  // Reading from System.in

    public Stack<Character> opStack = new Stack<Character>();

    public Vector<String> infixExp = new Vector<String>();
    public static void main(String[] args){
        reversePolishCalc calc = new reversePolishCalc();
        calc.promptUserForInfixInput();
        calc.printInifxExp();
        // System.out.print(calc.getOpStackTop());

    }

    public void promptUserForInfixInput(){
        String infix = "";
        while(infix.isEmpty()){
                System.out.print("Enter an Inifix expression: ");
                infix = reader.nextLine(); // Scans the next token of the input as an int.
                // if(resumeGame == 0 || resumeGame == 1){
                //     break;
                // }
        }
        setInfixExp(infix);
    }

    public void setInfixExp(String infix){
        String toAdd;
        for(int i = 0; i < infix.length(); i++){
            toAdd = "";
            if(infix.charAt(i) == ' '){
                continue;
            }else if(!checkIfCharIsNumber(infix.charAt(i))){
                toAdd = Character.toString(infix.charAt(i));
            }else{
                toAdd = Character.toString(infix.charAt(i));
                if(i != infix.length()-1){
                    while(checkIfCharIsNumber(infix.charAt(i+1))){
                        i++;
                        toAdd += infix.charAt(i);
                        if(i == infix.length()-1){
                            break;
                        }
                    }
                }
            }
            this.infixExp.add(toAdd);
        }
    }

    public void printInifxExp(){
        for(int i=0; i< this.infixExp.size(); i++){
            System.out.println(this.infixExp.get(i));
        }
    }

    public boolean checkIfCharIsNumber(char a){
        if((int) a >= 48 && (int) a <= 57){
            return true;
        }else{
            return false;
        }
    }

    public void convertToPostfix(){
    }

    public char getOpStackTop(){
        return (char) this.opStack.peek();
    }

}