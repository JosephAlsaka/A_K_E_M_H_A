package com.grad.akemha.entity;

import com.grad.akemha.entity.enums.Gender;
import com.grad.akemha.entity.enums.Role;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    //Creation Date
    @Column()
    private Timestamp creationDate;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "description")
    private String description;

    @Column(name = "gender")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "doctor_specialization_id")
    private DoctorSpecialization doctorSpecialization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

//    @OneToMany(cascade = CascadeType.ALL) //fetch = FetchType.LAZY, cascade = CascadeType.ALL
////    @JoinColumn(name = "user_id")
////    private List<Like> likes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
//        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
//        String role = String.valueOf(getRole());
//        list.add(new SimpleGrantedAuthority("ROLE_"+role));
//        System.out.println("list is"+list);
//        return list;
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
