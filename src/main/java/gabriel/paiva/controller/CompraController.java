package gabriel.paiva.controller;

import gabriel.paiva.model.ItemCompra;
import gabriel.paiva.service.ItemCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    private ItemCompraService itemCompraService;

    @GetMapping
    public String compraPage(Model model) {
        model.addAttribute("item", new ItemCompra());
        model.addAttribute("itensCompra", itemCompraService.listarTodos());
        return "compra";
    }

    @PostMapping
    public String salvarItemCompra(ItemCompra itemCompra, RedirectAttributes redirectAttributes) {
        ItemCompra savedItem = itemCompraService.salvar(itemCompra);
        redirectAttributes.addFlashAttribute("mensagem", "Item de compra salvo com sucesso!");
        return "redirect:/compra";
    }

    @GetMapping("/editar/{id}")
    public String editarItemCompra(@PathVariable Long id, Model model) {
        ItemCompra itemCompra = itemCompraService.buscarPorId(id);
        model.addAttribute("item", itemCompra);
        model.addAttribute("itensCompra", itemCompraService.listarTodos());
        model.addAttribute("editando", true);
        return "compra";
    }

    @PostMapping("/atualizar")
    public String atualizarItemCompra(ItemCompra itemCompra, RedirectAttributes redirectAttributes) {
        ItemCompra savedItem = itemCompraService.atualizar(itemCompra);
        redirectAttributes.addFlashAttribute("mensagem", "Item de compra atualizado com sucesso!");
        return "redirect:/compra";
    }

    @GetMapping("/excluir/{id}")
    public String excluirItemCompra(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        itemCompraService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Item de compra excluído com sucesso!");
        return "redirect:/compra";
    }
}