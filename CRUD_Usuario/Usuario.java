package CRUD_Usuario;
import java.time.LocalDate;

import aed3.Registro;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Usuario implements Registro {

    public int id;
    public String nome;
    public String email;
    public String HashSenha;
    public String PerguntaSecreta;
    public String HashRespostaSecreta;

    public Usuario() {
        this(-1, "", "", "", "", "");
    }

    public String getNome() {
        return nome;
    }
    private void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    private void setEmail(String email) {
        this.email = email;
    }
    public String getHashSenha() {
        return HashSenha;
    }
    private void setHashSenha(String hashSenha) {
        HashSenha = hashSenha;
    }
    public String getPerguntaSecreta() {
        return PerguntaSecreta;
    }
    public void setPerguntaSecreta(String perguntaSecreta) {
        PerguntaSecreta = perguntaSecreta;
    }
    public String getHashRespostaSecreta() {
        return HashRespostaSecreta;
    }
    public void setHashRespostaSecreta(String hashRespostaSecreta) {
        HashRespostaSecreta = hashRespostaSecreta;
    }
    public Usuario(int i, String n, String e, String h, String p, String hrs) {
        this.id = i;
        this.nome = n;
        this.email = e;
        this.HashSenha = h;
        this.PerguntaSecreta = p;
        this.HashRespostaSecreta = hrs;
        
    }

    public String toString() {
        return "\nID........: " + this.id +
               "\nNome......: " + this.nome +
               "\nEmail.....: " + this.email;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.email);
        dos.writeUTF(this.HashSenha);
        dos.writeUTF(this.PerguntaSecreta);
        dos.writeUTF(this.HashRespostaSecreta);
        return baos.toByteArray();
    }


    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        byte[] cpf = new byte[11];
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.email = dis.readUTF();
        this.HashSenha = dis.readUTF();
        this.PerguntaSecreta = dis.readUTF();
        this.HashRespostaSecreta = dis.readUTF();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
