package com.example.saywhonow_backend;

import com.example.saywhonow_backend.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {
    //TODO set up many to many instead of 1 to many


    @Id @GeneratedValue(strategy = GenerationType.AUTO )
    @Column(name="role_id")
    private Long roleId;


    private String authority;
    @ManyToOne(optional = false)
    private User user;



    public Role () {
        super();
    }

    public Role (String authority){
        this.authority = authority;
    }

    public Role ( Long roleId, String authority){
        this.roleId = roleId;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return roleId;
    }

    public void setId(Long roleId) {
        this.roleId = roleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
