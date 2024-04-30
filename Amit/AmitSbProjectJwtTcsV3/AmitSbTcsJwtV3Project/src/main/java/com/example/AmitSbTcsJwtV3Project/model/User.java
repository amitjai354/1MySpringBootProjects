package com.example.AmitSbTcsJwtV3Project.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
//@Table(name = "MYUSER")
public class User implements UserDetails {
    //if giving class name as MyUser then table is created but if giving name User then table is not creating
    //in tcs used User only
    //if giving @Table(name = "MYUSER") then MYUSER table created but in data .sql: insert into user
    //oif changing name using @Table then in repo class we write <User, INTEGER> or <MYUSER, INTEGER>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true)
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
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    //enumerated is just to tell want to consider string or ordinal 1, 2.. string so in user_role table string column
    //CollectionTable is to create new table user_role and we are givinf column name as user_id, we can give referenced
    //column name also means pk of other table rhat will be fk here
    //another column will be this roles, this is many to many so there can be multiple roles to same user id
    //ElementCollection is used to tel Target class.. as in Role class we are not writting anything

//    @ManyToMany(fetch=FetchType.EAGER)
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
//          inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"))
//    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r->new RoleGrantedAuthority(r.name())).collect(Collectors.toList());
        //with enum, using r.name, with role class, using r.getRole
        //return roles.stream().map(r->new RoleGrantedAuthority(r.getRole())).collect(Collectors.toList());

        //return roles.stream().map(r->new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
//        List list1 = new ArrayList<>();
//        for(Role r: roles){
//            list1.add(new SimpleGrantedAuthority(r.toString()));
//        }
//        return list1;
    }

    public User() {
    }

    public User(Integer userId, String username, String password, Set<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
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
