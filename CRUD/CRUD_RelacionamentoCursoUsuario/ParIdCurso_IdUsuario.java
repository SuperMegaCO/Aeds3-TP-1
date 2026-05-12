package CRUD_RelacionamentoCursoUsuario;

import java.io.*;
import aed3.RegistroArvoreBMais;

public class ParIdCurso_IdUsuario implements RegistroArvoreBMais<ParIdCurso_IdUsuario> {

    private String codigoCurso;
    private int idUsuario;
    private String nomeCurso;

    private final short TAMANHO = 104; // 20 bytes (String 10 chars) + 4 bytes (int) + 80 bytes (String 40 chars)

    public ParIdCurso_IdUsuario() {
        this("", -1, "");
    }

    public ParIdCurso_IdUsuario(String codigoCurso, int idUsuario, String nomeCurso) {
        this.codigoCurso = ajustarCodigo(codigoCurso);
        this.idUsuario = idUsuario;
        this.nomeCurso = ajustarString(nomeCurso);
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

    public String getCodigoCurso() {
        return codigoCurso.trim();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNomeCurso() {
        return nomeCurso.trim();
    }

    @Override
    public short size() {
        return TAMANHO;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        String cod = ajustarCodigo(codigoCurso);
        for (int i = 0; i < 10; i++) {
            dos.writeChar(cod.charAt(i));
        }
        dos.writeInt(idUsuario);

        String s = ajustarString(nomeCurso);
        for (int i = 0; i < 40; i++) {
            dos.writeChar(s.charAt(i));
        }

        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        char[] cod = new char[10];
        for (int i = 0; i < 10; i++) {
            cod[i] = dis.readChar();
        }
        codigoCurso = new String(cod);
        idUsuario = dis.readInt();

        char[] c = new char[40];
        for (int i = 0; i < 40; i++) {
            c[i] = dis.readChar();
        }
        nomeCurso = new String(c);
    }

    @Override
    public int compareTo(ParIdCurso_IdUsuario a) {
        if (!this.codigoCurso.trim().equals(a.codigoCurso.trim())) {
            return this.codigoCurso.trim().compareToIgnoreCase(a.codigoCurso.trim());
        }
        if (this.idUsuario != a.idUsuario) {
            return Integer.compare(this.idUsuario, a.idUsuario);
        }
        return this.nomeCurso.trim().compareToIgnoreCase(a.nomeCurso.trim());
    }

    @Override
    public ParIdCurso_IdUsuario clone() {
        return new ParIdCurso_IdUsuario(this.codigoCurso, this.idUsuario, this.nomeCurso);
    }
}
