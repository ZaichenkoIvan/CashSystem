package com.shop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "quant")
    private double quant;

    @Basic(optional = false)
    @Column(name = "price")
    private double price;

    @Basic(optional = false)
    @Column(name = "total")
    private double total;

    @Column(name = "nds")
    private Integer nds;

    @Basic(optional = false)
    @Column(name = "ndstotal")
    private double ndstotal;

    @Basic(optional = false)
    @Column(name = "canceled", columnDefinition = "integer default 0")
    private Integer canceled;

    @JoinColumn(name = "id_check", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CheckEntity check;

    @JoinColumn(name = "id_good", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GoodsEntity goods;
}
