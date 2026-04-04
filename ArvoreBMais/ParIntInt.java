import java.io.*;

public class ParIntInt implements aed3.RegistroArvoreBMais<ParIntInt> {

  private int num1;
  private String num2;

  private final short TAMANHO = 84;
  // 4 bytes do int + 80 bytes da string

  public ParIntInt() {
    this(-1, "");
  }

public ParIntInt(int n1) {
  this.num1 = n1;
  this.num2 = "";
}

  public ParIntInt(int n1, String n2) {
    this.num1 = n1;
    this.num2 = ajustarString(n2);
  }

  private String ajustarString(String s) {
    if (s.length() > 40)
      return s.substring(0, 40);

    while (s.length() < 40)
      s += " ";

    return s;
  }

  public int getNum1() {
    return num1;
  }

  public String getNum2() {
    return num2.trim();
  }

  @Override
  public ParIntInt clone() {
    return new ParIntInt(this.num1, this.num2);
  }

  public short size() {
    return TAMANHO;
  }

  public int compareTo(ParIntInt a) {

    if (this.num1 != a.num1)
      return this.num1 - a.num1;

    // coringa para buscar todos os cursos do usuário
    if (a.num2.trim().length() == 0)
      return 0;

    return this.num2.trim().compareTo(a.num2.trim());
  }

  public String toString() {
    return num1 + ";" + num2.trim();
  }

  public byte[] toByteArray() throws IOException {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    dos.writeInt(num1);

    String s = ajustarString(num2);
    for (int i = 0; i < 40; i++) {
      dos.writeChar(s.charAt(i));
    }

    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {

    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);

    num1 = dis.readInt();

    char[] c = new char[40];
    for (int i = 0; i < 40; i++) {
      c[i] = dis.readChar();
    }

    num2 = new String(c);
  }
}