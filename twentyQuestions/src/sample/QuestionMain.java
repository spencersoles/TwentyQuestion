package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 * Using a binary to create a automated "20 Question" game
 * User thinks of an item and the program attempts to guess in under 20 questions
 * If computer guesses wrong, there is option to give a question new question and save the user's object for future use
 *
 */
public class QuestionMain {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner console = new Scanner(System.in);

        System.out.println("Welcome to the 20 Questions Game!");
        System.out.println();
        System.out.print("Which questions file would you like to use? ");//game uses pre-created questions file,
        // two files currently are stored in package, big-question.txt is more detailed then questions.txt
        String filename = console.nextLine().trim();


        File questionsFile = new File(filename);
        if (!questionsFile.exists()) {
            questionsFile.createNewFile();
        }

        Scanner questions = new Scanner(questionsFile);

        QuestionGame game;

        /* Check if the file has anything in it.  If it does, use it.  Otherwise, initialize
         * a new game. */
        if (!questions.hasNext()) {
            System.out.println("There are no objects to guess in that questions file.");
            System.out.print("Can you provide me with an initial object? ");
            String initialObject = console.nextLine().toLowerCase().trim();
            game = new QuestionGame(initialObject);
        }
        else {
            game = new QuestionGame(questions);//being game using file that was chosen
        }

        System.out.print("Let's play!  ");
        do {
            System.out.println("Please choose your object, and I'll start guessing.");
            /*
            just hit enter and follow prompt, no need to type guess to computer
             */
            System.out.println("Press Enter when you're ready to begin!");
            console.nextLine();
            game.play(console);
            System.out.println();
            game.saveQuestions(new PrintStream(questionsFile));
            System.out.print("Do you want to play again (y/n)? ");
        } while (console.nextLine().trim().toLowerCase().startsWith("y"));


    }
}