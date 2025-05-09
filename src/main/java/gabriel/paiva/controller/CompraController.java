package gabriel.paiva.controller;

import gabriel.paiva.model.Compra;
import gabriel.paiva.model.ItemCompra;
import gabriel.paiva.service.CompraService;
import gabriel.paiva.service.ItemCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private ItemCompraService itemCompraService;

    // Página principal de compras
    @GetMapping
    public String listarCompras(Model model) {
        model.addAttribute("compras", compraService.listarTodas());
        return "compras/list";
    }

    // Formulário para nova compra
    @GetMapping("/nova")
    public String novaCompra(Model model) {
        model.addAttribute("compra", new Compra());
        return "compras/form";
    }

    // Salvar uma nova compra
    @PostMapping
    public String salvarCompra(@Validated @ModelAttribute Compra compra,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "compras/form";
        }

        compraService.salvar(compra);
        redirectAttributes.addFlashAttribute("mensagem", "Compra salva com sucesso!");
        return "redirect:/compras";
    }

    // Página de itens de uma compra específica
    @GetMapping("/{id}/itens")
    public String listarItensCompra(@PathVariable Long id, Model model) {
        model.addAttribute("compra", compraService.buscarPorId(id));
        model.addAttribute("itens", compraService.listarItensDaCompra(id));
        model.addAttribute("novoItem", new ItemCompra());
        return "compras/itens";
    }

    // Adicionar item a uma compra
    @PostMapping("/{compraId}/itens")
    public String adicionarItemCompra(
            @PathVariable Long compraId,
            @ModelAttribute ItemCompra itemCompra,
            RedirectAttributes redirectAttributes) {

        compraService.adicionarItem(compraId, itemCompra);
        redirectAttributes.addFlashAttribute("mensagem", "Item adicionado com sucesso!");
        return "redirect:/compras/" + compraId + "/itens";
    }

    // Métodos existentes para editar/excluir itens (mantidos)
    @GetMapping("/itens/editar/{id}")
    public String editarItemCompra(@PathVariable Long id, Model model) {
        ItemCompra itemCompra = itemCompraService.buscarPorId(id);
        model.addAttribute("item", itemCompra);
        model.addAttribute("compra", itemCompra.getCompra());
        return "compras/editar-item";
    }

    @PostMapping("/itens/atualizar")
    public String atualizarItemCompra(ItemCompra itemCompra, RedirectAttributes redirectAttributes) {
        ItemCompra savedItem = itemCompraService.atualizar(itemCompra);
        redirectAttributes.addFlashAttribute("mensagem", "Item atualizado com sucesso!");
        return "redirect:/compras/" + savedItem.getCompra().getId() + "/itens";
    }

    @GetMapping("/itens/excluir/{id}")
    public String excluirItemCompra(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ItemCompra item = itemCompraService.buscarPorId(id);
        Long compraId = item.getCompra().getId();
        itemCompraService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Item excluído com sucesso!");
        return "redirect:/compras/" + compraId + "/itens";
    }

    // Adicionar método para edição
    @GetMapping("/editar/{id}")
    public String editarCompra(@PathVariable Long id, Model model) {
        model.addAttribute("compra", compraService.buscarPorId(id));
        return "compras/form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarCompra(@PathVariable Long id,
                                  @Validated @ModelAttribute Compra compra,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "compras/form";
        }

        compraService.salvar(compra);
        redirectAttributes.addFlashAttribute("mensagem", "Compra atualizada com sucesso!");
        return "redirect:/compras";
    }
}