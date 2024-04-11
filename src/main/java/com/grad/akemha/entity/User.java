package com.grad.akemha.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grad.akemha.entity.enums.Gender;
import com.grad.akemha.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotNull(message = "Name cannot be null")
    @NotBlank(message = "Please add a user name")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true, unique = true, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    //Creation Date
    @Column()
    private Timestamp creationDate;

    @Column(name = "dob", nullable = true)
    private LocalDate dob;

    @Column(name = "profile_image",nullable = true)
    private String profileImage;

    @Column(name = "is_active",nullable = true)
    private Boolean isActive = true;

    @Column(name = "description",nullable = true)
    private String description;

    @Column(name = "gender",nullable = true)
    private Gender gender;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Role role;

//    @OneToMany(cascade = CascadeType.ALL) //fetch = FetchType.LAZY, cascade = CascadeType.ALL
////    @JoinColumn(name = "user_id")
////    private List<Like> likes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        String role = String.valueOf(getRole());
        list.add(new SimpleGrantedAuthority("ROLE_"+role));
        System.out.println("list is"+list);
        return list;
    }

    @Override
    public String getUsername() {
        return getEmail();
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
        return getIsActive();
    }

}
