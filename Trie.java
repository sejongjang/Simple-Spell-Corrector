package spell;

import java.util.HashSet;
import java.util.Set;

public class Trie implements ITrie {

    public Node root;
    private int nodeCount;
    private int wordCount;

    @Override
    public int hashCode(){
        int hash = ((this.nodeCount)^2+(this.wordCount)^2)%3333;
        return hash;
    }
    @Override
    public String toString(){
        StringBuilder word = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toStringHelper(root, word, output);
        return output.toString();
    }
    private void toStringHelper(Node n, StringBuilder word, StringBuilder output){
        if(n==null)
            return;
        if(n.getValue() > 0){
//			System.out.println("\t"+word.toString() + ": "+n.getValue());
            output.append(word.toString() + "\n");
        }
        for(int i=0;i<26;i++){
            word.append(alphabet[i]);
//			System.out.println("Current word: " + word.toString());
            toStringHelper(n.nodes[i],word,output);
            word.setLength(word.length()-1);
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o.getClass() != this.getClass()){
            return false;
        }
        Trie test = (Trie)o;
        if(this.wordCount!=test.getWordCount() || this.nodeCount != test.getNodeCount()){
            return false;
        }
        else if(!this.toStringWithValues().equals(test.toStringWithValues())){
            return false;
        }
        else{
            return equalRecurrsive(this.root, test.root);
        }
    }

    private boolean equalRecurrsive(Node n1, Node n2){
        for(int i=0; i<26; ++i) {
            if (n1.nodes[i] != null && n2.nodes[i] != null) {
                if (n1.nodes[i].getValue() != n2.nodes[i].getValue()) {
                    return false;
                }
                else {
                    equalRecurrsive(n1.nodes[i], n2.nodes[i]);
                }
            }
            else if(n1.nodes[i] == null && n2.nodes[i] != null){
                return false;
            }
            else if(n1.nodes[i] != null && n2.nodes[i] == null) {
                return false;
            }
        }
        return true;
    }

    char[] alphabet = new char[26];

    public Trie(){
        root = new Node();
        nodeCount =1;
        wordCount = 0;
        alphabet[0] ='a';
        alphabet[1] = 'b';
        alphabet[2] = 'c';
        alphabet[3] = 'd';
        alphabet[4] = 'e';
        alphabet[5] = 'f';
        alphabet[6] = 'g';
        alphabet[7] = 'h';
        alphabet[8] = 'i';
        alphabet[9] = 'j';
        alphabet[10] = 'k';
        alphabet[11] = 'l';
        alphabet[12] = 'm';
        alphabet[13] = 'n';
        alphabet[14] = 'o';
        alphabet[15] = 'p';
        alphabet[16] = 'q';
        alphabet[17] = 'r';
        alphabet[18] = 's';
        alphabet[19] = 't';
        alphabet[20] = 'u';
        alphabet[21] = 'v';
        alphabet[22] = 'w';
        alphabet[23] = 'x';
        alphabet[24] = 'y';
        alphabet[25] = 'z';
    }

    @Override
    public void add(String word) {
        if(word.equals("")){
            return;
        }
        word = word.toLowerCase();
        Node currentNode = root;

        for(int i=0; i<word.length(); ++i){
            if(currentNode.nodes[word.charAt(i)-'a']==null){
                currentNode.nodes[word.charAt(i)-'a'] = new Node();
                nodeCount++;
            }
            currentNode = currentNode.nodes[word.charAt(i)-'a'];
            if(i == word.length()-1){
                if(currentNode.getValue() == 0){
                    wordCount++;
                }
                currentNode.increaseCount();
            }
        }
    }

    @Override
    public INode find(String word) {
        Node currentNode = root;
        if(word == null){
            return null;
        }
        for(int i=0; i<word.length(); ++i){
            if(currentNode.nodes[word.charAt(i)-'a'] == null){
                return null;
            }
            else{
                currentNode = currentNode.nodes[word.charAt(i)-'a'];
            }
        }
        if(currentNode != null && currentNode.getValue()>0){
            return currentNode;
        }
        else {
            return null;
        }
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    public String[] deletion(String inputWord){
        String[] modifiedWords = new String[inputWord.length()];

        for(int i=0; i<modifiedWords.length; ++i){
            StringBuilder sb = new StringBuilder();
            sb.append(inputWord.substring(0,i));
            sb.append(inputWord.substring(i+1));
            modifiedWords[i] = sb.toString();
        }
        return modifiedWords;
    }

    public String[] insertion(String word){
        String[] modifiedWords = new String[(word.length()+1)*26];
        for(int i=0; i<word.length()+1; ++i){
            int j=0;
            for(int k=0; k<26; ++k){
                StringBuilder sb = new StringBuilder();
                sb.append(word.substring(0,i));
                sb.append(alphabet[k]);
                sb.append(word.substring(i));
                modifiedWords[i*26+j++] = sb.toString();
            }
        }
        return modifiedWords;
    }

    public String[] transposition(String word){
        int size =0;
        if(word.length() == 0){
            size=1;
        }
        else{
            size = word.length()-1;
        }
        String[] modifiedWords = new String[size];
        for(int i=0; i<word.length()-1; i++){
            StringBuilder sb = new StringBuilder();
            sb.append(word.substring(0,i));
            sb.append(word.charAt(i+1));
            sb.append(word.charAt(i));
            sb.append(word.substring(i+2));
            modifiedWords[i] = sb.toString();
        }
        return modifiedWords;
    }

    public String[] alteration(String word){
        String[] modifiedWords = new String[word.length()*25];
        for(int i=0; i<word.length(); i++){
            int j=0;
            char currentLetter = word.charAt(i);
            for(int k=0; k<26; k++){
                if(currentLetter == alphabet[k]){
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(word.substring(0,i));
                sb.append(alphabet[k]);
                sb.append(word.substring(i+1));
                modifiedWords[i*25+(j++)] = sb.toString();
            }
        }
        return modifiedWords;
    }

    public Set<String> firstModify(String inputWord){
        Set<String> modifiedWords = new HashSet<String>();

        for(String modWord:deletion(inputWord)){
            if(inputWord!=null){
                modifiedWords.add(modWord);
            }
        }
        for(String modWord:insertion(inputWord)){
            if(inputWord!=null){
                modifiedWords.add(modWord);
            }
        }
        for(String modWord:alteration(inputWord)){
            if(inputWord!=null){
                modifiedWords.add(modWord);
            }
        }
        for(String modWord:transposition(inputWord)){
            if(inputWord!=null){
                modifiedWords.add(modWord);
            }
        }
        return modifiedWords;
    }

    public Set<String> secondModify(Set<String> words){
        Set<String> doubleModifiedWords = new HashSet<String>();

        for(String word : words){
            for(String modWord : deletion(word)){
                if(word!=null)
                    doubleModifiedWords.add(modWord);
            }
            for(String modWord : insertion(word)){
                if(word!=null)
                    doubleModifiedWords.add(modWord);
            }
            for(String modWord : alteration(word)){
                if(word!=null)
                    doubleModifiedWords.add(modWord);
            }
            for(String modWord : transposition(word)){
                if(word!=null)
                    doubleModifiedWords.add(modWord);
            }
        }
        return doubleModifiedWords;
    }


    public String toStringWithValues(){
        StringBuilder word = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toStringHelperWithValues(root, word, output);
        return output.toString();
    }

    public void toStringHelperWithValues(Node n, StringBuilder word, StringBuilder output) {
        if(n==null){
            return;
        }
        if(n.getValue()>0){
            output.append("\t" + word.toString() + " ");
            output.append(this.findWordCount(word.toString()) + "\n");
        }
        for(int i=0; i<26; i++){
            word.append(alphabet[i]);
            toStringHelperWithValues(n.nodes[i],word,output);
            word.setLength(word.length()-1);
        }
    }

    public int findWordCount(String word){
        Node currentNode = root;
        for(int i=0; i<word.length(); i++){
            if(currentNode.nodes[word.charAt(i)-'a'] == null){
                return 0;
            }
            else{
                currentNode = currentNode.nodes[word.charAt(i) - 'a'];
            }
        }
        if(currentNode != null && currentNode.getValue()>0){
            return currentNode.getValue();
        }
        else{
            return 0;
        }
    }
}
