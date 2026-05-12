package CRUD_RelacionamentoCursoUsuario;

import java.io.*;
import aed3.RegistroArvoreBMais;

public class ParIdUsuario_IdCurso implements RegistroArvoreBMais<ParIdUsuario_IdCurso> {

    private int idUsuario;
    private String codigoCurso;
    private String nomeUsuario;

    private final short TAMANHO = 104; // 4 bytes (int) + 20 bytes (String 10 chars) + 80 bytes (String 40 chars)

    public ParIdUsuario_IdCurso() {
        this(-1, "", "");
    }

    public ParIdUsuario_IdCurso(int idUsuario, String codigoCurso, String nomeUsuario) {
        this.idUsuario = idUsuario;
        this.codigoCurso = ajustarCodigo(codigoCurso);
        this.nomeUsuario = ajustarString(nomeUsuario);
    }

    private String ajustarCodigo(String s) {
        if (s == null)
            s = "";
        if (s.length() > 10)
            return s.substring(0, 10);
        while (s.length() < 10)
            s += " ";
        return s;
    }

    private String ajustarString(String s) {
        if (s == null)
            s = "";
        if (s.length() > 40)
            return s.substring(0, 40);
        while (s.length() < 40)
            s += " ";
        return s;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getCodigoCurso() {
        return codigoCurso.trim();
    }

    public String getNomeUsuario() {
        return nomeUsuario.trim();
    }

    @Override
    public short size() {
        return TAMANHO;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(idUsuario);

        String cod = ajustarCodigo(codigoCurso);
        for (int i = 0; i < 10; i++) {
            dos.writeChar(cod.charAt(i));
        }

        String s = ajustarString(nomeUsuario);
        for (int i = 0; i < 40; i++) {
            dos.writeChar(s.charAt(i));
        }

        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        idUsuario = dis.readInt();

        char[] cod = new char[10];
        for (int i = 0; i < 10; i++) {
            cod[i] = dis.readChar();
        }
        codigoCurso = new String(cod);

        char[] c = new char[40];
        for (int i = 0; i < 40; i++) {
            c[i] = dis.readChar();
        }
        nomeUsuario = new String(c);
    }

    @Override
    public int compareTo(ParIdUsuario_IdCurso a) {
        if (this.idUsuario != a.idUsuario) {
            return Integer.compare(this.idUsuario, a.idUsuario);
        }
        if (!this.codigoCurso.trim().equals(a.codigoCurso.trim())) {
            return this.codigoCurso.trim().compareToIgnoreCase(a.codigoCurso.trim());
        }
        return this.nomeUsuario.trim().compareToIgnoreCase(a.nomeUsuario.trim());
    }

    @Override
    public ParIdUsuario_IdCurso clone() {
        return new ParIdUsuario_IdCurso(this.idUsuario, this.codigoCurso, this.nomeUsuario);
    }
}