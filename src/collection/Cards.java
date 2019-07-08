package collection;

public interface Cards {

    public int getId();

    public void setId(int id);

    public String getCategoria();

    public void setCategoria(String categoria);

    public String getClasse();

    public void setClasse(String classe);

    public int getLivello();

    public void setLivello(int livello) ;

    public String getRarità();

    public void setRarità(String rarità);

    public String getTipo();
    public void setTipo(String tipo);
    public String getNome();

    public void setNome(String nome);

    public String getDescrizione();

    public void setDescrizione(String descrizione);

    @Override
    public String toString();

}
