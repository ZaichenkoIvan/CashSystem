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
@Table(name = "fiscal")
@NamedNativeQuery(name = "FiscalEntity.createXReport", query = "SELECT current_timestamp() AS printtime," +
        "	cancel.countcanceled," +
        "	SUM(COUNT(DISTINCT c.id)) OVER() AS countcheck," +
        "	s.nds," +
        "	SUM(s.total) AS total," +
        "	round(SUM(s.ndstotal), 2) AS ndstotal, " +
        "	round(SUM(SUM(s.total)) OVER(), 2) AS sumtotal, " +
        "	round(SUM(SUM(s.ndstotal)) OVER(), 2) AS sumndstotal " +
        "	FROM orders s" +
        "	INNER JOIN chec c ON c.id = s.id_check" +
        "	LEFT JOIN (SELECT COUNT(c1.canceled) AS countcanceled FROM chec c1 " +
        "			 		WHERE c1.canceled = 1 ) cancel ON true" +
        "	WHERE c.canceled = 0 AND s.canceled = 0" +
        "	GROUP BY s.nds, cancel.countcanceled")
@NamedNativeQuery(name = "FiscalEntity.createZReport", query = "SELECT current_timestamp() AS printtime," +
        "	(SELECT COUNT(c1.canceled) FROM chec c1 " +
        "		WHERE c1.canceled = 1 AND cast(c1.crtime as date) = current_date()) AS countcanceled, " +
        "	SUM(COUNT(DISTINCT c.id)) OVER() AS countcheck," +
        "	SUM(CASE WHEN s.nds = 20 THEN s.total ELSE 0 END) AS totalA," +
        "	round(SUM(CASE WHEN s.nds = 20 THEN s.ndstotal ELSE 0 END), 2) AS ndstotalA," +
        "	SUM(CASE WHEN s.nds = 7 THEN s.total ELSE 0 END) AS totalB," +
        "	round(SUM(CASE WHEN s.nds = 7 THEN s.ndstotal ELSE 0 END), 2) AS ndstotalB," +
        "	SUM(CASE WHEN s.nds = 0 THEN s.total ELSE 0 END) AS totalC," +
        "	round(SUM(CASE WHEN s.nds = 0 THEN s.ndstotal ELSE 0 END), 2) AS ndstotalC," +
        "	SUM(SUM(s.total)) OVER() AS sumtotal, " +
        "	round(SUM(SUM(s.ndstotal)) OVER(), 2) AS sumndstotal " +
        "	FROM orders s" +
        "	INNER JOIN chec c ON c.id = s.id_check" +
        "	WHERE c.canceled = 0 AND s.canceled = 0 AND cast(c.crtime as date) = current_date()" +    //закоментировано для debug-а
        "		AND c.registration IS NULL")

public class FiscalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "total")
    private Double total;
}
