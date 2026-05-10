package CRUD_RelacionamentoCursoUsuario;

import java.time.LocalDate;

import aed3.Registro;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class RelacionamentoCursoUsuario implements Registro {

    public int id;
    public LocalDate dataInscricao;
    public int idCurso;
    public String nomeCurso;
    public int idUsuario;
    public String nomeUsuario;

    public RelacionamentoCursoUsuario() {
        this(-1, LocalDate.now(), -1, -1);
    }

    public RelacionamentoCursoUsuario(LocalDate dataInscricao, int idCurso, int idUsuario) {
        this(-1, dataInscricao, idCurso, idUsuario);
    }

    public RelacionamentoCursoUsuario(int id, LocalDate dataInscricao, int idCurso, int idUsuario) {
        this.id = id;
        this.dataInscricao = dataInscricao;
        this.idCurso = idCurso;
        this.idUsuario = idUsuario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "\nID........: " + this.id +
                "\nData Inscrição.: " + this.dataInscricao +
                "\nID Curso......: " + this.idCurso +
                "\nID Usuario....: " + this.idUsuario;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeInt((int) this.dataInscricao.toEpochDay());
        dos.writeInt(this.idCurso);
        dos.writeInt(this.idUsuario);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.dataInscricao = LocalDate.ofEpochDay(dis.readInt());
        this.idCurso = dis.readInt();
        this.idUsuario = dis.readInt();
    }

    public int getIdCurso() {
        return idCurso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }
}
