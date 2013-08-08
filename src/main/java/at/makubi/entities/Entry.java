package at.makubi.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Translation> texts;

    private int maxLength = 0;

    @OneToOne(cascade = CascadeType.ALL)
    private Identifier identifier;

    private String metaInformation;


    public Collection<Translation> getTexts() {
        return texts;
    }

    public void setTexts(Collection<Translation> texts) {
        this.texts = texts;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getMetaInformation() {
        return metaInformation;
    }

    public void setMetaInformation(String metaInformation) {
        this.metaInformation = metaInformation;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }
}
