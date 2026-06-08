package CRUD_Curso;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

public class ListaInvertidoHelper {
    public ListaInvertidoHelper() {
        
    }
    public ArrayList<String> palavraCleaner(String input) {
        input = input.toLowerCase();
        ArrayList<String> output = new ArrayList<String>();
        //eu procurei na internet as especificas de fazer a limpeza das palavras.
        HashSet<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "o", "a", "os", "as", "um", "uma", "uns", "umas", 
        "de", "do", "da", "dos", "das", "em", "no", "na", "nos", "nas", 
        "por", "para", "com", "sem", "e", "ou", "mas", "à", "ao", "aos", "às"));
        input = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        input = pattern.matcher(input).replaceAll("");
        String[] outputParcial = input.split("\\W+");
        for (String word : outputParcial) {
            if (!word.isEmpty() && !STOP_WORDS.contains(word)) {
                output.add(word);
            }
        }
        return output;
    }
}
