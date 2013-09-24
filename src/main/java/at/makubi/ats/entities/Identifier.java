package at.makubi.ats.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Identifier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
