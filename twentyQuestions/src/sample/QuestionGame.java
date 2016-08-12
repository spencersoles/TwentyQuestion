package sample;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by Spencer on 10/22/2015.
 *
 * File builds tree based off of content of txt file chosen at beginning
 */
public class QuestionGame<E> implements Serializable{
    /**
     * sub class that creates a node object for each data point in the tree
     * @param <E>
     */

    protected static class QuestionNode<E> implements Serializable {
        protected String data;//each question, if a leaf then holds an object
        protected QuestionNode<E> left;
        protected QuestionNode<E> right;

        /**
         * constructor for node based off of string for recursive function
         * @param info - contructor for leaf, info = final answer
         */
        public QuestionNode(String info){
            data = info;
            left = null;
            right = null;
        }

        /**
         * constructor for questionNode, taking lines from txt file and populating data field along with
         * each nodes left and right branches
         * @param input
         */

        public QuestionNode(Scanner input){
            String label = input.nextLine();
            data = input.nextLine();

            if(label.startsWith("Q")){//Q for question
                left = new QuestionNode(input);
                right = new QuestionNode(input);
            }
        }

        /**
         * Basic to string of each node
         * @return
         */
        public String toString(){
            return data.toString();
        }
    }

    protected QuestionNode<E> root;

    /**
     * contructor which begins population of tree by creating one node at a time
     * @param input
     */

    public QuestionGame(Scanner input){

        String label=input.nextLine();
        root = new QuestionNode (input.nextLine());

            root.left = new QuestionNode(input);
            root.right = new QuestionNode(input);


    }

    /**
     * creats a new question game based off of the first line of data, constructor
     * @param object
     */
    public QuestionGame(String object){
        root = new QuestionNode(object);
    }


    /**
     * calls the pre order traverse function to create the stringbuilder so that altered file can be saved
     * Saves new questions
     * @param output
     */
    public void saveQuestions(PrintStream output){
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root, sb);
        output.println(sb);

    }

    /**
     * first call of play, used to convert data to call other play function easier
     * takes user answer, determining whether to go left (user answers yes) or right (user answers no)
     * controls the users naviagation through tree and most importantly drives the game
     * @param console
     */
    public void play(Scanner console){
        QuestionNode<E> main = root;
        String answer="";


            System.out.println(main.data);
            answer = console.nextLine();
            if(answer.toUpperCase().equals("Y")){//user answers yes
                play(console, main.left);
            }else if(answer.toUpperCase().equals("N")){//user answers no
                play(console, main.right);
            }


    }

    /**
     * recursively calls play, determining when the game is over and when to add a new leaf
     * @param console
     * @param main
     */
    public void play(Scanner console, QuestionNode<E> main){

        String answer=" ";

        if(main.data.contains("?")) {// base case determining if a question was asked by the computer or an answer given

            System.out.println(main.data);
            answer = console.nextLine();
        }
        if(!main.data.contains("?")) {// answer guessed by computer logic
            System.out.println("Is it a " + main.data + " ?");
            answer = console.nextLine();
            if(answer.toUpperCase().equals("Y")){//computer guessed right
                System.out.println("Yay I win");
            }else if(answer.toUpperCase().equals("N")){//computer guessed wrong
                System.out.println("Boo");
                add(console, main);
            }

        } else if(answer.toUpperCase().equals("Y")) {//human answer was yes and the next node in tree is not a leaf
            play(console, main.left);


        }else if(answer.toUpperCase().equals("N") ){//human answer was yes and the next node in tree is not a leaf
            play(console, main.right);
        }


    }


    /**
     * adds a question and answer to the tree if the object is not found in the answer set txt file
     * @param console
     * @param node
     */
    public void add(Scanner console, QuestionNode<E> node){
        String object = "";
        String createdQuestion ="";
        String answer="";

        System.out.println("What is your object?");
        object = console.nextLine();

        System.out.println("Please give me a yes/no question that distinguishes between " + object + " and " + node.data);
        System.out.println("Q: ");
        createdQuestion = console.nextLine();

        System.out.println("Is the answer yes for " + object + " (y/n)?");
        answer = console.nextLine();

        if(answer.toUpperCase().equals("y")){//logic to determine whether the new object should be left or right of
                                            //previous node
            node.right=new QuestionNode(object);
            node.left = new QuestionNode(node.data);
            node.data = createdQuestion;
        }else{
            node.left=new QuestionNode(object);
            node.right = new QuestionNode(node.data);
            node.data = createdQuestion;
        }

    }

    /**
     * basic toString method for entire tree
     * @return
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root, sb);
        return sb.toString();
    }

    /**
     * traverses through the binary tree in pre-order fashion
     * @param node
     * @param sb
     */
   private void preOrderTraverse(QuestionNode<E> node, StringBuilder sb){

        if(node == null){
            sb.append("");
            return;
        }else{
            if(!node.data.contains("?")){
                sb.append("A:");
            }else{
                sb.append("Q:");
            }
            sb.append("\n");
            sb.append(node.toString());
            sb.append("\n");
            preOrderTraverse(node.left, sb);
            preOrderTraverse(node.right, sb);
        }
    }

}
