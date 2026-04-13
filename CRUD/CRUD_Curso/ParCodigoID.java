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
        String s = new String(ba);
        String[] partes = s.split(";");
        codigo = partes[0];
        id = Integer.parseInt(partes[1]);
    }
}