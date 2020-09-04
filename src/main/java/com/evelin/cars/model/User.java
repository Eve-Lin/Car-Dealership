package com.evelin.cars.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "user_id")
    private Long id;
    @NonNull
    private String username;
    @NonNull
    @Column(name = "email")
    @Email
    private String email;
    @NonNull
    @Length(min = 2, max = 60)
    private String firstName;
    @NonNull
    @Length(min = 2, max = 60)
    private String lastName;
    @NonNull
    @Length(min = 4)
    private String password;
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Role role;
    private Boolean active = true;
    @OneToMany(mappedBy = "seller")
    @ToString.Exclude
    private Collection<Offer> offers = new ArrayList<>();

    public User(User user) {
        this.active = user.getActive();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
        this.password = user.getPassword();
    }

    public User(@Length(min = 2, max = 60) String firstName,
                @Length(min = 2, max = 60) String lastName,
                @Length(min = 2, max = 60) String username,
                @Length(min = 2) @NotNull String email,
                @Length(min = 2) @NotNull String password,
                Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}