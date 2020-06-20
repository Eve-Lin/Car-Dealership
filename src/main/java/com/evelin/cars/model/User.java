package com.evelin.cars.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"authorities", "accountNonExpired", "accountNonLocked","credentialsNonExpired", "enabled"})
public class User implements UserDetails{
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    @EqualsAndHashCode.Include
    @Column(unique = true, nullable = false)
    private String username;
    @NonNull
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    private Boolean active = true;
    private String imageUrl;
    private Date created;
    private Date modified;

    @OneToMany(mappedBy = "seller")
    @ToString.Exclude
    @JsonIgnore
    private Collection<Offer> offers = new ArrayList<>();

    public User( String firstName, String lastName,
               @NotNull String username,
                @NotNull String password,
              Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.imageUrl = "/img/user-avatar.svg";
    }

    public User(String firstName,
                 String lastName,
                @NotNull String username,
                 @NotNull String password,
                Set<Role> roles,
              String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.imageUrl = imageUrl;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> role.toString() + "_ROLE")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
