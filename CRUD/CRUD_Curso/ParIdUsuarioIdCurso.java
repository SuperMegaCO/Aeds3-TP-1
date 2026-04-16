package CRUD_Curso;

import java.io.*;
import aed3.RegistroArvoreBMais;

public class ParIdUsuarioIdCurso implements RegistroArvoreBMais<ParIdUsuarioIdCurso> {

    private int idUsuario;
    private String nomeCurso;
    private int idCurso;

    private final short TAMANHO = 88; // 4 bytes (int) + 80 bytes (String 40 chars) + 4 bytes (int)

    public ParIdUsuarioIdCurso() {
        this(-1, "", -1);
    }

    public ParIdUsuarioIdCurso(int idUsuario, String nomeCurso, int idCurso) {
        this.idUsuario = idUsuario;
        this.nomeCurso = ajustarString(nomeCurso);
        this.idCurso = idCurso;
    }

    private String ajustarString(String s) {
        if (s == null) s = "";
        if (s.length() > 40) return s.substring(0, 40);
        while (s.length() < 40) s += " ";
        return s;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNomeCurso() { return nomeCurso.trim(); }
    public int getIdCurso() { return idCurso; }

    @Override
    public short size() {
        return TAMANHO;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(idUsuario);
        
        String s = ajustarString(nomeCurso);
        for (int i = 0; i < 40; i++) {
            dos.writeChar(s.charAt(i));
        }
        
        dos.writeInt(idCurso);

        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        idUsuario = dis.readInt();

        char[] c = new char[40];
        for (int i = 0; i < 40; i++) {
            c[i] = dis.readChar();
        }
        nomeCurso = new String(c);

        idCurso = dis.readInt();
    }

    @Override
    public int compareTo(ParIdUsuarioIdCurso a) {
        if (this.idUsuario != a.idUsuario) {
            return Integer.compare(this.idUsuario, a.idUsuario);
        }

        if (this.idCurso == -1 || a.idCurso == -1 || this.nomeCurso.trim().isEmpty() || a.nomeCurso.trim().isEmpty()) {
            return 0;
        }

        if (!this.nomeCurso.trim().equals(a.nomeCurso.trim())) {
            return this.nomeCurso.trim().compareToIgnoreCase(a.nomeCurso.trim());
        }

        return Integer.compare(this.idCurso, a.idCurso);
    }

    @Override
    public ParIdUsuarioIdCurso clone() {
        return new ParIdUsuarioIdCurso(this.idUsuario, this.nomeCurso, this.idCurso);
    }
}