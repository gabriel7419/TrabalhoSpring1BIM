package gabriel.paiva.service;

import gabriel.paiva.model.ItemCompra;
import gabriel.paiva.repository.ItemCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCompraService {

    @Autowired
    private ItemCompraRepository itemCompraRepository;

    public List<ItemCompra> listarTodos() {
        return itemCompraRepository.findAll();
    }

    public ItemCompra buscarPorId(Long id) {
        return itemCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de compra não encontrado: " + id));
    }

    public ItemCompra salvar(ItemCompra itemCompra) {
        return itemCompraRepository.save(itemCompra);
    }

    public ItemCompra atualizar(ItemCompra itemCompra) {
        if (itemCompra.getId() == null) {
            throw new RuntimeException("Não é possível atualizar um item sem ID");
        }

        // Verifica se o item existe
        buscarPorId(itemCompra.getId());

        return itemCompraRepository.save(itemCompra);
    }

    public void excluir(Long id) {
        ItemCompra itemCompra = buscarPorId(id);
        itemCompraRepository.delete(itemCompra);
    }
}