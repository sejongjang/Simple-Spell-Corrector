package spell;

import java.io.IOException;

public interface ISpellCorrector {
	public static class NoSimilarWordFoundException extends Exception {
	}
		public void useDictionary(String dictionaryFileName) throws IOException;

		public String suggestSimilarWord(String inputWord)throws NoSimilarWordFoundException;
}
