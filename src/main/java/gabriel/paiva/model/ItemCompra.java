package gabriel.paiva.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemCompra extends Item {
    // Poderia adicionar atributos específicos para compras
}