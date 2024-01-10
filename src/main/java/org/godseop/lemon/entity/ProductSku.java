package org.godseop.lemon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Getter
@Entity
@DynamicUpdate
@ToString(exclude = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ProductSku implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "option_code", nullable = false)
    private String optionCode;

    @Column(name = "option_name", nullable = false)
    private String optionName;

    @CreatedBy
    @Column(name = "created_by")
    private Long createBy;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Instant updatedDate;


    @Builder(builderClassName = "ProductSkuBuilder", builderMethodName = "ProductSkuBuilder")
    public ProductSku(String optionCode, String optionName) {
        this.optionCode = optionCode;
        this.optionName = optionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductSku productSku = (ProductSku) o;
        return Objects.equals(this.id, productSku.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}