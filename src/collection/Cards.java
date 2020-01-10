package collection;

public interface Cards {

    int getId();

    void setId(int id);

    String getCategoria();

    void setCategoria(String categoria);

    String getClasse();

    void setClasse(String classe);

    int getLivello();

    void setLivello(int livello) ;

    String getRarità();

    void setRarità(String rarità);

    String getTipo();
    void setTipo(String tipo);
    String getNome();

    void setNome(String nome);

    String getDescrizione();

    void setDescrizione(String descrizione);

    @Override
    String toString();

}
