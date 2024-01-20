package com.jjs.ClothingInventorySaleReformPlatform.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User implements UserDetails {  // 구매자
    @Id
    @Column(name = "EMAIL",nullable = false, unique = true)  // unique = true : 유일값(중복x)
    private String email;  // 이메일

    @Column(name = "PASSWORD", nullable = false)
    private String password;  // 비밀번호

    @Column(name = "NAME", nullable = false)
    private String name;  // 이름

    @Column(name = "PHONENUMBER", unique = true, nullable = false)
    private String phoneNumber;  // 전화번호

    @Column(name = "ROLE", nullable = false)
    private String role;  // 권한

/*
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
 */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
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


    /*
    public static Purchaser toPurchaser(PurchaserDTO purchaserDTO) {
        Purchaser purchaser = new Purchaser();

        purchaser.setEmail(purchaserDTO.getEmail());
        purchaser.setPassword(purchaserDTO.getPassword());
        purchaser.setNickname(purchaserDTO.getNickname());
        purchaser.setName(purchaserDTO.getName());
        purchaser.setAddress(purchaserDTO.getAddress());
        purchaser.setPhoneNumber(purchaserDTO.getPhonenumber());

        return purchaser;
    }

     */

/*
    @OneToMany(mappedBy = "clientEmail")
    private Set<Estimate> estimates = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sellerEmail")
    private Set<Purchaselist> purchaselists = new LinkedHashSet<>();

    @OneToMany(mappedBy = "purchaserEmail")
    private Set<Question> questions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "clientEmail")
    private Set<Request> requests = new LinkedHashSet<>();

    @OneToMany(mappedBy = "purchaserEmail")
    private Set<Review> reviews = new LinkedHashSet<>();
*/
}