package com.evelin.cars.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@JsonIgnoreProperties({"authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class User {
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    @Length(min = 2, max = 60)
    private String firstName;
    @NonNull
    @Length(min = 2, max = 60)
    private String lastName;
    @NonNull
    @EqualsAndHashCode.Include
    @Column(unique = true, nullable = false)
    @Length(min = 4, max = 15)
    private String username;
    @NonNull
    @Length(min = 4, max = 15)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    private Boolean active = true;
    @Length(min = 8, max = 512)
    private String imageUrl;
    private Date created;
    private Date modified;

    @OneToMany(mappedBy = "seller")
    @ToString.Exclude
//    @JsonIgnore
    private Collection<Offer> offers = new ArrayList<>();

    public User(String firstName, String lastName,
                @NotNull String username,
                @NotNull String password,
                Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.imageUrl = "/pic/user-avatar.png";
    }

    public User(@Length(min = 2, max = 60) String firstName,
                @Length(min = 2, max = 60) String lastName,
                @Length(min = 2, max = 60) @NotNull String username,
                @Length(min = 2, max = 60) @NotNull String password,
                Set<Role> roles,
                @Length(min = 8, max = 512) String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.imageUrl = imageUrl;
    }

}
