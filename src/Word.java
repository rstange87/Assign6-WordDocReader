import java.util.ArrayList;
import java.util.List;


public class Word implements Comparable{
    private String stem;
    private List<Document> docList;

    public Word (String sentWord) {
        stem = sentWord;
        docList = new ArrayList<Document>();
    }

    public void addDocument (Document tempDoc) {
        docList.add(tempDoc);
    }

    public List<Document> getDocList(){
        return docList;
    }

    public void displayDocList() {
        for (int i=0; i<docList.size(); i++) {
            System.out.println(docList.get(i).toString());
        }
    }

    public String getWord() {
        return stem;
    }

    public String toString() {
        return stem;
    }

    @Override
    public int compareTo(Object o) {
        Word sentWord = (Word) o;
        if (this.stem.compareTo(sentWord.getWord()) > 0) {
            return 1;
        } else if (this.stem.compareTo(sentWord.getWord()) < 0) {
            return -1;
        }
        return 0;
    }
}
