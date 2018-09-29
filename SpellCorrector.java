package spell;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class SpellCorrector implements ISpellCorrector{

    public Trie myTrie;

    public Trie getDict(){
        return myTrie;
    }

    public SpellCorrector(){
        myTrie = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
            Scanner myScanner = new Scanner(new File(dictionaryFileName));

            while(myScanner.hasNext()){
                myTrie.add(myScanner.next());
               // System.out.println(myScanner.next());
            }
            //System.out.println("Dict1:\n" + myTrie.toStringWithValues());
            myScanner.close();

    }

    @Override
    public String suggestSimilarWord(String inputWord){

        inputWord = inputWord.toLowerCase();
        boolean found = false;
        if(inputWord.equals("")){
            return null;
        }
        if(myTrie.find(inputWord) == null){
            Set<String> modifiedWords = myTrie.firstModify(inputWord);
            Set<String> modifiedInDictionary = new HashSet<String>();

            for(String word:modifiedWords){
                if(myTrie.find(word)!= null){
                    found = true;
                    modifiedInDictionary.add(word);
                }
            }
            if(!found){
                Set<String> modifiedTwoWords = myTrie.secondModify(modifiedWords);
                for(String word:modifiedTwoWords)
                    if(myTrie.find(word)!=null){
                        found = true;
                        modifiedInDictionary.add(word);
                    }
            }
            else{
                TreeMap<Integer,String> wordValueMap = new TreeMap<Integer, String>();
                for(String word : modifiedInDictionary){
                    wordValueMap.put(myTrie.findWordCount(word),word);
                }
                return wordValueMap.lastEntry().getValue();
            }


                TreeMap<Integer, String> wordValueMap = new TreeMap<Integer,String>();
                for(String word:modifiedInDictionary){
                    wordValueMap.put(myTrie.findWordCount(word),word);

                return wordValueMap.lastEntry().getValue();
            }
        }
        else{
            return inputWord;
        }

        return null;
    }

}
