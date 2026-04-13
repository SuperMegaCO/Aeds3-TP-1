package CRUD_Curso;

import aed3.*;

public class ParIdUsuarioIdCurso implements RegistroHashExtensivel<ParIdUsuarioIdCurso> {

    private int idUsuario;
    private int idCurso;

    public ParIdUsuarioIdCurso() {
        this(-1, -1);
    }

    public ParIdUsuarioIdCurso(int idUsuario, int idCurso) {
        this.idUsuario = idUsuario;
        this.idCurso = idCurso;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public static int hash(int idUsuario, int idCurso) {
        return (idUsuario + "-" + idCurso).hashCode();
    }

    @Override
    public int hashCode() {
        return (idUsuario + "-" + idCurso).hashCode();
    }

    @Override
    public short size() {
        return 20;
    }

    @Override
    public byte[] toByteArray() throws Exception {
        return (idUsuario + ";" + idCurso).getBytes();
    }

    @Override
    public void fromByteArray(byte[] ba) throws Exception {
        String s = new String(ba);
        String[] partes = s.split(";");
        idUsuario = Integer.parseInt(partes[0]);
        idCurso = Integer.parseInt(partes[1]);
    }
}