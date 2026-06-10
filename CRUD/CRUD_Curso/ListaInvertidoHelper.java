    package CRUD_Curso;

    import java.text.Normalizer;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.HashMap;
    import java.util.HashSet;
    import java.util.regex.Pattern;

    import aed3.ElementoLista;
    import aed3.ListaInvertida;

    public class ListaInvertidoHelper {
        public ListaInvertidoHelper() {

        }

        public ArrayList<String> palavraCleaner(String input) {
            input = input.toLowerCase();
            ArrayList<String> output = new ArrayList<String>();
            // eu procurei na internet as especificas de fazer a limpeza das palavra .
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

        public void InserirLista(ListaInvertida a, ArrayList<String> wordList, int  curso) {
            float wordTotal = wordList.size();
            HashMap<String, Integer> frequencia = new HashMap<>();
            for (String word : wordList) {
                if (frequencia.containsKey(word)) {
                    frequencia.put(word, frequencia.get(word) + 1);
                } else {
                    frequencia.put(word, 1);
                }
            }
            for (String word : frequencia.keySet()) {

                float tf = frequencia.get(word) / wordTotal;

                ElementoLista elemento = new ElementoLista(curso, tf);

                try {
                    a.create(word, elemento);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        public void operacaoCompleta(ListaInvertida a, int curso, String cursoNome) {
            ArrayList<String> cursoCleaned = palavraCleaner(cursoNome);
            InserirLista(a, cursoCleaned, curso);
        }
         public void operacaoRemover(ListaInvertida a, String texto, int id) {
        ArrayList<String> cleanedText = palavraCleaner(texto);
        for (String word : cleanedText) {
            try {
                a.delete(word, id); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    }
