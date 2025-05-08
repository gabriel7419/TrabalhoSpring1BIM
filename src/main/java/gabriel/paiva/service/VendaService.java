package gabriel.paiva.service;

import gabriel.paiva.model.Venda;
import gabriel.paiva.model.ItemVenda;
import gabriel.paiva.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ItemVendaService itemVendaService;

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada: " + id));
    }

    public Venda salvar(Venda venda) {
        return vendaRepository.save(venda);
    }

    public void adicionarItem(Long vendaId, ItemVenda itemVenda) {
        Venda venda = buscarPorId(vendaId);
        venda.adicionarItem(itemVenda);
        vendaRepository.save(venda);
    }

    public List<ItemVenda> listarItensDaVenda(Long vendaId) {
        return buscarPorId(vendaId).getItens();
    }
}