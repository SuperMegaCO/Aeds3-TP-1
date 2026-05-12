package CRUD_Curso;

import java.io.IOException;

import aed3.*;

public class ParCodigoID implements RegistroHashExtensivel<ParCodigoID> {

    private String codigo;
    private int id;

    public ParCodigoID() {
        this("", -1);
    }

    public ParCodigoID(String codigo, int id) {
        this.codigo = codigo;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static int hash(String codigo) {
        return codigo.hashCode();
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    @Override
    public short size() {
        return 50; // tamanho fixo (ajustável)
    }

    @Override
    public byte[] toByteArray() throws IOException {
        return (codigo + ";" + id).getBytes();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        String s = new String(ba).trim();
        if (s.isEmpty() || s.indexOf(';') == -1) {
            codigo = "";
            id = -1;
            return;
        }
        String[] partes = s.split(";");
        if (partes.length < 2) {
            codigo = "";
            id = -1;
            return;
        }

        String first = partes[0].trim();
        String second = partes[1].trim();

        if (first.matches("^-?\\d+$") && !second.matches("^-?\\d+$")) {
            // Suporte a formato antigo ou invertido: id;codigo
            id = Integer.parseInt(first);
            codigo = second;
        } else if (!first.matches("^-?\\d+$") && second.matches("^-?\\d+$")) {
            // Formato esperado: codigo;id
            codigo = first;
            id = Integer.parseInt(second);
        } else {
            // Não é possível interpretar com segurança; marca como inválido.
            codigo = "";
            id = -1;
        }
    }
}