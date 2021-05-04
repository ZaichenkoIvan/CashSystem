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
@Table(name = "goods")
public class GoodsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "code")
    private int code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "quant")
    private double quant;

    @Basic(optional = false)
    private double price;

    @Basic(optional = false)
    @Column(name = "measure")
    private String measure;

    @Column(name = "comments")
    private String comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goods")
    private List<OrderEntity> orders;
}
