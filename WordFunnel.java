package app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WordFunnel {
    /*
     * A word funnel is a series of words formed by removing one letter at a time
     * from a starting word, keeping the remaining letters in order. For the purpose
     * of this challenge, a word is defined as an entry in the enable1 word list. An
     * example of a word funnel is:
     * 
     * gnash => gash => ash => ah
     * 
     * This word funnel has length 4, because there are 4 words in it.
     * 
     * Given a word, determine the length of the longest word funnel that it starts.
     * You may optionally also return the funnel itself (or any funnel tied for the
     * longest, in the case of a tie).
     */
    private final Set<String> dictionary;

    WordFunnel(Collection<String> dictionary) {
        this.dictionary = new HashSet<>(dictionary);
    }

    List<String> findLongestFunnel(String word) {
        if (!dictionary.contains(word)) {
            return Collections.emptyList();
        }
        return findAllFunnels(word).stream().max(Comparator.comparing(List::size)).map(l -> {
            Collections.reverse(l);
            return l;
        }).orElse(Collections.emptyList());
    }

    private Collection<List<String>> findAllFunnels(String word) {
        assert dictionary.contains(word);

        Collection<String> nextWordsInFunnel = findNextInFunnel(word);
        if (nextWordsInFunnel.isEmpty()) {
            List<String> lastWordInFunnel = new ArrayList<>();
            lastWordInFunnel.add(word);
            return Collections.singleton(lastWordInFunnel);
        }

        return nextWordsInFunnel.stream().map(this::findAllFunnels).flatMap(Collection::stream)
                .peek(funnel -> funnel.add(word)).collect(Collectors.toList());
    }

    private Collection<String> findNextInFunnel(String word) {
        IntStream charactersToRemove = IntStream.range(0, word.length());
        return charactersToRemove.mapToObj(i -> word.substring(0, i) + word.substring(i + 1))
                .filter(dictionary::contains).distinct().collect(Collectors.toList());
    }
}