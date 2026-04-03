import java.io.*;

public class Curso {
    private int id;
    private int idUsuario;
    private String nome;
    private String dataInicio;
    private String descricao;
    private String codigo;
    private int estado;

    public Curso() {}

    public Curso(int idUsuario, String nome, String dataInicio,
                 String descricao, String codigo, int estado) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.descricao = descricao;
        this.codigo = codigo;
        this.estado = estado;
    }

    // GETTERS E SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdUsuario() { return idUsuario; }
    public String getCodigo() { return codigo; }
    public int getEstado() { return estado; }

    // SERIALIZAÇÃO
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

    @Override
    public String toString() {
        return "\nID: " + id +
               "\nUsuário: " + idUsuario +
               "\nNome: " + nome +
               "\nCódigo: " + codigo +
               "\nEstado: " + estado + "\n";
    }
}