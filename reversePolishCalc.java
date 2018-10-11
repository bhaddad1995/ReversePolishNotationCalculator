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
    public Stack<Integer> eval = new Stack<Integer>();
    public Vector<String> postfixExp = new Vector<String>();
    public Vector<String> infixExp = new Vector<String>();
    public static void main(String[] args){
        reversePolishCalc calc = new reversePolishCalc();
        while(true){
            calc.promptUserForInfixInput();
            System.out.println("\nInfix Equation:");
            calc.printInfxExp();
            calc.convertToPostfix();
            System.out.println("\nPostfix Equation:");
            calc.printPostfixExp();
            System.out.println("\nEvaluated answer:");
            Integer ans = calc.evaluatePostfixExp();
            if(ans != null){
                System.out.println(ans+ "\n");
            }
        }
    }

    public void promptUserForInfixInput(){
        String infix = "";
        while(infix.isEmpty()){
                System.out.print("Enter an Infix expression (enter 'quit' to exit program): ");
                infix = reader.nextLine(); // Scans the next token of the input as an int.
                // if(resumeGame == 0 || resumeGame == 1){
                //     break;
                // }
                if(infix.equals("quit") || infix.equals("Quit")){
                    System.exit(0);
                }
        }
        setInfixExp(infix);
    }

    public void setInfixExp(String infix){
        String toAdd;
        for(int i = 0; i < infix.length(); i++){
            toAdd = "";
            if(infix.charAt(i) == ' '){
                continue;
            }else if(!checkIfNumber(infix.charAt(i))){
                toAdd = Character.toString(infix.charAt(i));
            }else{
                toAdd = Character.toString(infix.charAt(i));
                if(i != infix.length()-1){
                    while(checkIfNumber(infix.charAt(i+1))){
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

    public void printInfxExp(){
        for(int i=0; i< this.infixExp.size(); i++){
            System.out.print(" ");
            System.out.print(this.infixExp.get(i));
            System.out.print(" ");
        }
    }

    public void printPostfixExp(){
        for(int i=0; i< this.postfixExp.size(); i++){
            System.out.print(" ");
            System.out.print(this.postfixExp.get(i));
            System.out.print(" ");
        }
    }

    public boolean checkIfNumber(char a){
        if((int) a >= 48 && (int) a <= 57){
            return true;
        }else{
            return false;
        }
    }

    public void convertToPostfix(){
        String t;
        while(!this.infixExp.isEmpty()){
            t = this.infixExp.firstElement();
            this.infixExp.remove(0);
            if(checkIfNumber(t.charAt(0))){
                this.postfixExp.add(t);
            }else if(this.opStack.isEmpty()){
                this.opStack.push(t.charAt(0));
            }else if(t.equals("(")){
                this.opStack.push(t.charAt(0));
            }else if(t.equals(")")){
                while(this.opStack.peek() != '('){
                    this.postfixExp.add(Character.toString(this.opStack.peek()));
                    this.opStack.pop();
                }
                this.opStack.pop();//get rid of ( character
            }else{
                while(!this.opStack.isEmpty() && this.opStack.peek() != '(' && checkPrecedence(t, Character.toString(this.opStack.peek()))){
                    this.postfixExp.add(Character.toString(this.opStack.peek()));
                    this.opStack.pop();
                }
                this.opStack.push(t.charAt(0));
            }
        }
        while(!this.opStack.isEmpty()){
            this.postfixExp.add(Character.toString(this.opStack.peek()));
            this.opStack.pop();
        }

    }

    public Integer evaluatePostfixExp(){
        String t;
        int topNum, nextNum, answer = 0;
        while(!this.postfixExp.isEmpty()){
            t = this.postfixExp.firstElement();
            this.postfixExp.remove(0);
            if(checkIfNumber(t.charAt(0))){
                this.eval.push(Integer.parseInt(t));
            }else{
                topNum = this.eval.peek();
                this.eval.pop();
                nextNum = this.eval.peek();
                this.eval.pop();

                switch(t){
                    case "+": answer = nextNum + topNum; break;
                    case "-": answer = nextNum - topNum; break;
                    case "*": answer = nextNum * topNum; break;
                    case "/": 
                        if(topNum == 0){
                            System.out.println("Error: Cannot divide by 0\n");
                            return null;
                         }else{
                            answer = nextNum / topNum;
                         }
                         break;
                    case "%": answer = nextNum % topNum; break;
                }

                this.eval.push(answer);
            }
        }
        return this.eval.peek();

    }

    public boolean checkPrecedence(String a, String b){//checks if precedence of a <= precedence of b. Assumes a comes later in the equation than b
        if(a.equals("+") || a.equals("-")){
            return true;
        }else{
            if(b.equals("*") || b.equals("/") || b.equals("%")){
                return true;
            }else{
                return false;
            }
        }
    }

    public char getOpStackTop(){
        return (char) this.opStack.peek();
    }

}