package com.shop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usrs")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "email", unique = true)
    private String email;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<CheckEntity> checks;

    @JoinColumn(name = "id_user_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserTypeEntity userType;
}
