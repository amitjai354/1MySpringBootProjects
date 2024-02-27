package com.example.demo.amitSbProject.models;

//import jakarta.persistence.Entity; for spring boot version 3 and above
import javax.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class MyUser implements UserDetails {

    //auth.userDetailsService(myUserDetailsService); so we created MyUserDetailsService class and
    //in this class we override loadUserByUsername which returns UserDetails and inside this
    //we are calling findUserByUsername.. so in UserRepository class, this method needs to return.
    //  UserDetaisl so we did..  MyUser findUserByUsername(String username);
    //so MyUser needs to implement UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true)//username should be unique
    private String username;

    private String password;

    //UserRole will be as user_role in DB
    //Use of @OneToMany or @ManyToMany targeting an unmapped class error if use manytomany with enum
    //at place of many to maqny use.. @ElementCollect6ion.. can njot use cascade
    //if nopt giving targetClass = Role.class, then also working
    //will give error if @ElementCollection is not written..

    //Use of @JoinTable is working same but do not use inverseJoinColumns targeting an unmapped class so need to
    // use@CollectionTable
    //but can not use inverse join column with this..already told target class as Role.class..
    // and as no role attribute in enum Role now..


    //only difference in enum vs class is that in the table user_role.. if using Role class then Role is integer type
    //if using enum.. then Roles is created with character varying(255) in the user table
    //as in enum we do not have role id.. we have Consumer, SELLER and enumtype is string.. so as character


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "userId"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;// = new HashSet<>(); //working on both for enum..

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "userId"),
//            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "roleId"))
//    private Set<Role> roles;// = new HashSet<>(); //working on both for enum..


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //need to return something which is extending GrantedAuthority
        return this.roles.stream().map(r->new RoleGrantedAuthority(r.name())).collect(Collectors.toList());
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
}
