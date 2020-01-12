package collection;
//Decorator pattern
/** Class that represents a card
 */
public class Card extends AbstractCard implements Comparable {


    public Card(int id, String categoria, String classe, int livello, String rarità, String tipo, String nome, String descrizione) {
        this.id = id;
        this.categoria = categoria;
        this.classe = classe;
        this.livello = livello;
        this.rarità = rarità;
        this.tipo = tipo;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public String getRarità() {
        return rarità;
    }

    public void setRarità(String rarità) {
        this.rarità = rarità;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }



    /**
     * @return the description of the card
     */
    public String toString() {
        return categoria + " " + classe + " " + livello + " " + rarità + " " + tipo + " " + nome + " " + descrizione +"\n";
    }

    /**
     * Compares the ids of cards
     */
    @Override
    public int compareTo(Object o) {
        if ((o != null) && (o instanceof Card)) {
            Card c = (Card) o;
            if (this.getId() > c.getId()) {
                return 1;
            } else if (this.getId() < c.getId()) {
                return -1;
            } else
                return 0;


        }
        return -1;
    }
}


