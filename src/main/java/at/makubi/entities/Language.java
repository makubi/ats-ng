package at.makubi.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String code;

    @NotNull
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}
