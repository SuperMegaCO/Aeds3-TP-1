package CRUD_Curso;

import java.io.*;
import aed3.*;

public class Curso implements Registro {

    private int id;
    private int idUsuario;
    private String nome;
    private String dataInicio;
    private String descricao;
    private String codigo;
    private int estado;

    public Curso() {
        this(-1, -1, "", "", "", "", 0);
    }

    public Curso(int id, int idUsuario, String nome, String dataInicio,
                 String descricao, String codigo, int estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.descricao = descricao;
        this.codigo = codigo;
        this.estado = estado;
    }

    // ========================
    // GETTERS E SETTERS
    // ========================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public void setNome(String nome) { this.nome = nome; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setEstado(int estado) { this.estado = estado; }

    // ========================
    // MÉTODOS DO TEMPLATE
    // ========================

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);
        dos.writeInt(idUsuario);
        dos.writeUTF(nome);
        dos.writeUTF(dataInicio);
        dos.writeUTF(descricao);
        dos.writeUTF(codigo);
        dos.writeInt(estado);

        return ba.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bi = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bi);

        id = dis.readInt();
        idUsuario = dis.readInt();
        nome = dis.readUTF();
        dataInicio = dis.readUTF();
        descricao = dis.readUTF();
        codigo = dis.readUTF();
        estado = dis.readInt();
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    // ========================
    // DEBUG
    // ========================
    @Override
    public String toString() {
        return "\nID: " + id +
               "\nUsuário: " + idUsuario +
               "\nNome: " + nome +
               "\nCódigo: " + codigo +
               "\nEstado: " + estado + "\n";
    }
}
