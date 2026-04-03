package CRUD_Usuario;


import aed3.*;

public class ArquivoUsuario extends aed3.Arquivo<Usuario> {

    Arquivo<Usuario> arqUsuarios;
    HashExtensivel<ParEmailID> indiceIndiretoEmail;

    public ArquivoUsuario() throws Exception {
        super("Usuarios", Usuario.class.getConstructor());
        indiceIndiretoEmail = new HashExtensivel<>(
            ParEmailID.class.getConstructor(), 
            4, 
            ".\\dados\\Usuarios\\indiceEmail.d.db",   // diretório
            ".\\dados\\Usuarios\\indiceEmail.c.db"    // cestos 
        );
    }

    @Override
    public int create(Usuario c) throws Exception {
        int id = super.create(c);
        indiceIndiretoEmail.create(new ParEmailID(c.getEmail(), id));
        return id;
    }

    public Usuario read(String Email) throws Exception {
        ParEmailID pci = indiceIndiretoEmail.read(ParEmailID.hash(Email));
        if(pci == null)
            return null;
        return read(pci.getId());
    }
    
    public boolean delete(String Email) throws Exception {
        ParEmailID pci = indiceIndiretoEmail.read(ParEmailID.hash(Email));
        if(pci != null) 
            if(delete(pci.getId())) 
                return indiceIndiretoEmail.delete(ParEmailID.hash(Email));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Usuario c = super.read(id);
        if(c != null) {
            if(super.delete(id))
                return indiceIndiretoEmail.delete(ParEmailID.hash(c.getEmail()));
        }
        return false;
    }

    @Override
    public boolean update(Usuario novoUsuario) throws Exception {
        Usuario UsuarioVelho = read(novoUsuario.getId());
        if(super.update(novoUsuario)) {
            if(novoUsuario.getEmail().compareTo(UsuarioVelho.getEmail())!=0) {
                indiceIndiretoEmail.delete(ParEmailID.hash(UsuarioVelho.getEmail()));
                indiceIndiretoEmail.create(new ParEmailID(novoUsuario.getEmail(), novoUsuario.getId()));
            }
            return true;
        }
        return false;
    }
}
