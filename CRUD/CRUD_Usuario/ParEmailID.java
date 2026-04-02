package CRUD_Usuario;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import aed3.RegistroHashExtensivel;

public class ParEmailID implements aed3.RegistroHashExtensivel<ParEmailID> {
    
    private String Email; // chave
    private int id;     // valor
    private final short TAMANHO = 15;  // tamanho em bytes

    public ParEmailID() {
        this.Email = "00000000000";
        this.id = -1;
    }

    public ParEmailID(String Email, int id) throws Exception {
        this.Email = Email;
        this.id = id;
    }

    public String getEmail() {
        return Email;
    }

    public int getId() {
        return id;
    }

 
    @Override
    public int hashCode() {
        return hash(this.Email);
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "("+this.Email + ";" + this.id+")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.write(this.Email.getBytes());
        dos.writeInt(this.id);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] b = new byte[11];
        dis.read(b);
        this.Email = new String(b);
        this.id = dis.readInt();
    }

    public static int hash(String Email) throws IllegalArgumentException {
        // Certifique-se de que o Email contém exatamente 11 dígitos
        long EmailLong = Long.parseLong(Email);

        // Aplicar uma função de hash usando um número primo grande
        int hashValue = (int) (EmailLong % (int)(1e9 + 7));

        // Retornar um valor positivo
        return Math.abs(hashValue);
    }


}
