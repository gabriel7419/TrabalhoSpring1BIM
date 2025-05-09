package gabriel.paiva.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private String fornecedor;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCompra> itens = new ArrayList<>();

    public void adicionarItem(ItemCompra item) {
        itens.add(item);
        item.setCompra(this);
    }

    public Double getTotal() {
        return itens.stream()
                .mapToDouble(item -> item.getPreco() * item.getQuantidade())
                .sum();
    }
}