import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Driver {

    private static List<Word> fullWordList;
    private static List<Document> fullDocList;

    private static void readWords(String sentFile) {
        fullWordList = new ArrayList<Word>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(sentFile));
            String nextWord = br.readLine();
            while (nextWord != null) {
                fullWordList.add(new Word(nextWord));
                nextWord = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading words.txt!");
        }
    }

    private static void readDocs(String sentFile) {
        fullDocList = new ArrayList<Document>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(sentFile));
            String nextLine = br.readLine();
            while (nextLine != null) {
                String[] docIdAndWords = nextLine.split(" ");
                Document tempDoc = new Document(docIdAndWords[0]);
                //Adds all word indexes to the new Document.
                for (int i=1; i < docIdAndWords.length; i++) {
                    tempDoc.addWord(fullWordList.get(Integer.parseInt(docIdAndWords[i]) - 1));
                }
                fullDocList.add(tempDoc);
                nextLine = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading documents.txt!");
        }
    }

    public static void main(String[] args) {

        //Runs methods to turn sent files into Object Lists.
        //Uses input paths to read files.
        readWords(args[0]);
        readDocs(args[1]);

        //Prints current full word list.
        //System.out.println(fullWordList.toString() + "\n");
        //System.out.println(fullDocList.toString() + "\n");

        //Adds all words to the tree that are assigned to documents.
        TreeMap<String, Word> wordsWithDocsTree = new TreeMap<String, Word>();
        for (int i = 0; i< fullWordList.size(); i++) {
            if(fullWordList.get(i).getDocList().size() > 0) {
                wordsWithDocsTree.put(fullWordList.get(i).getWord(), fullWordList.get(i));
            }
        }

        Scanner myScanner = new Scanner(System.in);
        String[] commandAndId = {"",""};

        while (!commandAndId[0].equals("quit")) {
            System.out.println("Please enter a command:\n");
            System.out.println("wordlist 'document id'    - - NOTE: displays document's word list");
            System.out.println("doclist 'word'            - - NOTE: displays word's document list");
            System.out.println("occurswith 'word'         - - NOTE: displays word's sharing documents");
            System.out.println("quit                      - - NOTE: exits the program");
            System.out.println();

            commandAndId = myScanner.nextLine().split(" ");

            if (commandAndId.length < 2 && !commandAndId[0].equals("quit")) {
                System.out.println("Please correctly enter from the list of commands!");
            }

            //Displays all of the words assigned to the sent document.
            else if (commandAndId[0].equals("wordlist")){
                for (int i = 0; i<fullDocList.size(); i++) {
                    if (fullDocList.get(i).getId().equals(commandAndId[1])) {
                        if (fullDocList.get(i).getWordList().size() == 0) {
                            System.out.println("Document '" + commandAndId[1] + "' has no words.");
                        }
                        fullDocList.get(i).displayWordList();
                        i = fullDocList.size();
                    } else if (i == fullDocList.size() - 1) {
                        System.out.println("Document '" + commandAndId[1] + "' does not exist.");
                    }
                }
            }

            //Displays all documents assigned to sent word.
            else if (commandAndId[0].equals("doclist")) {
                if (wordsWithDocsTree.get(commandAndId[1]) != null) {
                    wordsWithDocsTree.get(commandAndId[1]).displayDocList();
                } else {
                    System.out.print("That word has no documents assigned.");
                }

            }

            //Displays all words that occur with sent word.
            else if (commandAndId[0].equals("occurswith")) {
                if (wordsWithDocsTree.get(commandAndId[1]) != null) {

                    //Used TreeSet to create a list of words occurring with words to prevent duplicates.
                    TreeSet<Word> wordOccurrenceTree = new TreeSet<Word>();

                    //Using tempDocList pointer to make for loop easier to read.
                    List<Document> tempDocList = wordsWithDocsTree.get(commandAndId[1]).getDocList();

                    for (int docIndex=0; docIndex<tempDocList.size(); docIndex++) {
                        int numWords = tempDocList.get(docIndex).getWordList().size();
                        for (int wordIndex=0; wordIndex<numWords; wordIndex++) {
                            wordOccurrenceTree.add(tempDocList.get(docIndex).getWordList().get(wordIndex));
                        }
                    }

                    while (wordOccurrenceTree.size() > 0) {
                        System.out.println(wordOccurrenceTree.pollFirst());
                    }

                } else {
                    System.out.print("That word has no documents assigned.");
                }
            }
            else if (!commandAndId[0].equals("quit")){
                System.out.println("Please enter one of the 4 listed commands!");
            }
            System.out.println();
        }
        //create binary search tree for words that have documents referencing them.
        //not all words will be in the binary search tree.

    }
}