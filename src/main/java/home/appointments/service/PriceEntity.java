package home.appointments.service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "price")
public class PriceEntity {

    @Id
    @GeneratedValue
    private Integer id;
}
