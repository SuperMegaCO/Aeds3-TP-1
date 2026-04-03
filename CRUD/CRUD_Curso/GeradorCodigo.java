package CRUD_Curso;
import java.security.SecureRandom;

public class GeradorCodigo {
    private static final String ALFABETO =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int TAMANHO = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String gerar() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TAMANHO; i++) {
            sb.append(ALFABETO.charAt(random.nextInt(ALFABETO.length())));
        }
        return sb.toString();
    }
}