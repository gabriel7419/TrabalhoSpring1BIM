package gabriel.paiva.service;

import gabriel.paiva.model.ItemVenda;
import gabriel.paiva.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    public List<ItemVenda> listarTodos() {
        return itemVendaRepository.findAll();
    }

    public ItemVenda buscarPorId(Long id) {
        return itemVendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de venda não encontrado: " + id));
    }

    public ItemVenda salvar(ItemVenda itemVenda) {
        return itemVendaRepository.save(itemVenda);
    }

    public ItemVenda atualizar(ItemVenda itemVenda) {
        if (itemVenda.getId() == null) {
            throw new RuntimeException("Não é possível atualizar um item sem ID");
        }

        // Verifica se o item existe
        buscarPorId(itemVenda.getId());

        return itemVendaRepository.save(itemVenda);
    }

    public void excluir(Long id) {
        ItemVenda itemVenda = buscarPorId(id);
        itemVendaRepository.delete(itemVenda);
    }
}