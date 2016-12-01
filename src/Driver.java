import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;
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
        System.out.println(fullWordList.toString() + "\n");
        System.out.println(fullDocList.toString() + "\n");

        //Adds all words to the tree that are assigned to documents.
        TreeSet<Word> myWordTree = new TreeSet<Word>();
        for (int i = 0; i< fullWordList.size(); i++) {
            if(fullWordList.get(i).getDocList().size() > 0) {
                myWordTree.add(fullWordList.get(i));
            }
        }

        System.out.println(myWordTree.toString());
        System.out.println();

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

            if (commandAndId.length < 2) {
                System.out.println("Please correctly enter from the list of commands!");
            }
            else if (commandAndId[0].equals("wordlist")){
                for (int i = 0; i<fullDocList.size(); i++) {
                    if (fullDocList.get(i).getId().equals(commandAndId[1])) {
                        fullDocList.get(i).displayWordList();
                        i = fullDocList.size();
                    }
                }
            }
            else if (commandAndId[0].equals("doclist")) {
                for (int i = 0; i<fullWordList.size(); i++) {
                    if (fullWordList.get(i).getWord().equals(commandAndId[1])) {
                        fullWordList.get(i).displayDocList();
                        i = fullWordList.size();
                    }
                }

            }
            else if (commandAndId[0].equals("occurswith")) {
                List<Document> tempDocList;
                for (int i = 0; i<fullWordList.size(); i++) {
                    if (fullWordList.get(i).getWord().equals(commandAndId[1])) {
                        tempDocList = fullWordList.get(i).getDocList();
                        for (int j=0; j<tempDocList.size(); j++) {
                            tempDocList.get(j).displayWordList();
                        }
                        i = fullWordList.size();
                    }
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