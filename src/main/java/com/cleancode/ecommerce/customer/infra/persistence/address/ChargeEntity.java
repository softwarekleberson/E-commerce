package com.cleancode.ecommerce.customer.infra.persistence.address;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_charge")
public class ChargeEntity extends AdrressEntity {

}
