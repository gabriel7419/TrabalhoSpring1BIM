package gabriel.paiva.controller;

import gabriel.paiva.model.ItemVenda;
import gabriel.paiva.service.ItemVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private ItemVendaService itemVendaService;

    @GetMapping
    public String vendaPage(Model model) {
        model.addAttribute("item", new ItemVenda());
        model.addAttribute("itensVenda", itemVendaService.listarTodos());
        return "venda";
    }

    @PostMapping
    public String salvarItemVenda(ItemVenda itemVenda, RedirectAttributes redirectAttributes) {
        ItemVenda savedItem = itemVendaService.salvar(itemVenda);
        redirectAttributes.addFlashAttribute("mensagem", "Item de venda salvo com sucesso!");
        return "redirect:/venda";
    }

    @GetMapping("/editar/{id}")
    public String editarItemVenda(@PathVariable Long id, Model model) {
        ItemVenda itemVenda = itemVendaService.buscarPorId(id);
        model.addAttribute("item", itemVenda);
        model.addAttribute("itensVenda", itemVendaService.listarTodos());
        model.addAttribute("editando", true);
        return "venda";
    }

    @PostMapping("/atualizar")
    public String atualizarItemVenda(ItemVenda itemVenda, RedirectAttributes redirectAttributes) {
        ItemVenda savedItem = itemVendaService.atualizar(itemVenda);
        redirectAttributes.addFlashAttribute("mensagem", "Item de venda atualizado com sucesso!");
        return "redirect:/venda";
    }

    @GetMapping("/excluir/{id}")
    public String excluirItemVenda(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        itemVendaService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Item de venda excluído com sucesso!");
        return "redirect:/venda";
    }
}