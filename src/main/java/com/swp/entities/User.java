package com.swp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "app_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")})
@Getter
@Setter
public class User implements UserDetails {

    public User(Integer usId) {
        this.usId = usId;
    }
    public Integer getUsId() {
        return usId;
    }
    public User(String name) {
        this.display_name = name;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer usId;

    @Column(name = "display_name", nullable = false)
    private String display_name;

    @Column(name = "additional_info")
    private String additional_info;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"role_id\"")
    private Role role_id;

    @Column(name = "created_date")
    private LocalDateTime created_date;

    @Column(name = "contribution_point")
    private Integer contributionPoint;


    @OneToMany(mappedBy = "createdByUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role_id.getRoleInfo()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(usId, user.usId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usId);
    }


}
