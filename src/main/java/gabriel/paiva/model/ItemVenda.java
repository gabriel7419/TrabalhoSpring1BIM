package gabriel.paiva.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemVenda extends Item {
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    private Integer quantidade;
}