package gabriel.paiva.service;

import gabriel.paiva.model.Compra;
import gabriel.paiva.model.ItemCompra;
import gabriel.paiva.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {
    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ItemCompraService itemCompraService;

    public List<Compra> listarTodas() {
        return compraRepository.findAll();
    }

    public Compra buscarPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra não encontrada: " + id));
    }

    public Compra salvar(Compra compra) {
        return compraRepository.save(compra);
    }

    public void adicionarItem(Long compraId, ItemCompra itemCompra) {
        Compra compra = buscarPorId(compraId);
        compra.adicionarItem(itemCompra);
        compraRepository.save(compra);
    }

    public List<ItemCompra> listarItensDaCompra(Long compraId) {
        return buscarPorId(compraId).getItens();
    }
}