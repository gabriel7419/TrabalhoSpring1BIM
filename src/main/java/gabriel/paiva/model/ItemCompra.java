package gabriel.paiva.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemCompra extends Item {
    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;

    private Integer quantidade;
}