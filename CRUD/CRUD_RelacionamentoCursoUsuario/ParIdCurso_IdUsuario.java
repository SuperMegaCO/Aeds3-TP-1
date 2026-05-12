package CRUD_RelacionamentoCursoUsuario;

import java.io.*;
import aed3.RegistroArvoreBMais;

public class ParIdCurso_IdUsuario implements RegistroArvoreBMais<ParIdCurso_IdUsuario> {

    private int idCurso;
    private int idUsuario;
    private int idRelacionamento;
    private String nomeCurso;

    private final short TAMANHO = 92; // 4 bytes (int) + 4 bytes (int) + 4 bytes (int) + 80 bytes (String 40 chars)

    public ParIdCurso_IdUsuario() {
        this(-1, -1, -1, "");
    }

    public ParIdCurso_IdUsuario(int idCurso, int idUsuario, int idRelacionamento, String nomeCurso) {
        this.idCurso = idCurso;
        this.idUsuario = idUsuario;
        this.idRelacionamento = idRelacionamento;
        this.nomeCurso = ajustarString(nomeCurso);
    }
    
    // Construtor compatível para buscas no índice (idRelacionamento = -1)
    public ParIdCurso_IdUsuario(int idCurso, int idUsuario, String nomeCurso) {
        this(idCurso, idUsuario, -1, nomeCurso);
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

    public int getIdCurso() {
        return idCurso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdRelacionamento() {
        return idRelacionamento;
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

        dos.writeInt(idCurso);
        dos.writeInt(idUsuario);
        dos.writeInt(idRelacionamento);

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

        idCurso = dis.readInt();
        idUsuario = dis.readInt();
        idRelacionamento = dis.readInt();

        char[] c = new char[40];
        for (int i = 0; i < 40; i++) {
            c[i] = dis.readChar();
        }
        nomeCurso = new String(c);
    }

    @Override
    public int compareTo(ParIdCurso_IdUsuario a) {
        if (this.idCurso != a.idCurso) {
            return Integer.compare(this.idCurso, a.idCurso);
        }
        // Partial search match
        if (this.idUsuario == -1 || a.idUsuario == -1) {
            return 0;
        }
        if (this.idUsuario != a.idUsuario) {
            return Integer.compare(this.idUsuario, a.idUsuario);
        }
        if (this.idRelacionamento != -1 && a.idRelacionamento != -1 && this.idRelacionamento != a.idRelacionamento) {
            return Integer.compare(this.idRelacionamento, a.idRelacionamento);
        }
        return this.nomeCurso.trim().compareToIgnoreCase(a.nomeCurso.trim());
    }

    @Override
    public ParIdCurso_IdUsuario clone() {
        return new ParIdCurso_IdUsuario(this.idCurso, this.idUsuario, this.idRelacionamento, this.nomeCurso);
    }
}
