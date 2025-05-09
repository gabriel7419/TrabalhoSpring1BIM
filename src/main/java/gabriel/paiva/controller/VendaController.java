package gabriel.paiva.controller;

import gabriel.paiva.model.Venda;
import gabriel.paiva.model.ItemVenda;
import gabriel.paiva.service.VendaService;
import gabriel.paiva.service.ItemVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private ItemVendaService itemVendaService;

    // Lista todas as vendas
    @GetMapping
    public String listarVendas(Model model) {
        model.addAttribute("vendas", vendaService.listarTodas());
        return "vendas/list";
    }

    // Formulário para nova venda
    @GetMapping("/nova")
    public String novaVenda(Model model) {
        model.addAttribute("venda", new Venda());
        return "vendas/form";
    }

    // Salvar nova venda
    @PostMapping
    public String salvarVenda(@Validated @ModelAttribute Venda venda,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "vendas/form";
        }

        vendaService.salvar(venda);
        redirectAttributes.addFlashAttribute("mensagem", "Venda salva com sucesso!");
        return "redirect:/vendas";
    }

    // Editar venda existente
    @GetMapping("/editar/{id}")
    public String editarVenda(@PathVariable Long id, Model model) {
        model.addAttribute("venda", vendaService.buscarPorId(id));
        return "vendas/form";
    }

    // Atualizar venda
    @PostMapping("/editar/{id}")
    public String atualizarVenda(@PathVariable Long id,
                                 @Validated @ModelAttribute Venda venda,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "vendas/form";
        }

        venda.setId(id);
        vendaService.salvar(venda);
        redirectAttributes.addFlashAttribute("mensagem", "Venda atualizada com sucesso!");
        return "redirect:/vendas";
    }

    // Visualizar itens de uma venda específica
    @GetMapping("/{id}/itens")
    public String listarItensVenda(@PathVariable Long id, Model model) {
        Venda venda = vendaService.buscarPorId(id);
        model.addAttribute("venda", venda);
        model.addAttribute("itens", venda.getItens());
        model.addAttribute("novoItem", new ItemVenda());
        return "vendas/itens";
    }

    // Adicionar item a uma venda
    @PostMapping("/{vendaId}/itens")
    public String adicionarItemVenda(@PathVariable Long vendaId,
                                     @ModelAttribute ItemVenda itemVenda,
                                     RedirectAttributes redirectAttributes) {
        vendaService.adicionarItem(vendaId, itemVenda);
        redirectAttributes.addFlashAttribute("mensagem", "Item adicionado com sucesso!");
        return "redirect:/vendas/" + vendaId + "/itens";
    }

    // Formulário para editar item
    @GetMapping("/itens/editar/{id}")
    public String editarItemVenda(@PathVariable Long id, Model model) {
        ItemVenda itemVenda = itemVendaService.buscarPorId(id);
        model.addAttribute("item", itemVenda);
        model.addAttribute("venda", itemVenda.getVenda());
        return "vendas/editar-item";
    }

    // Atualizar item
    @PostMapping("/itens/atualizar")
    public String atualizarItemVenda(@ModelAttribute ItemVenda itemVenda,
                                     RedirectAttributes redirectAttributes) {
        ItemVenda savedItem = itemVendaService.atualizar(itemVenda);
        redirectAttributes.addFlashAttribute("mensagem", "Item atualizado com sucesso!");
        return "redirect:/vendas/" + savedItem.getVenda().getId() + "/itens";
    }

    // Excluir item
    @GetMapping("/itens/excluir/{id}")
    public String excluirItemVenda(@PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {
        ItemVenda item = itemVendaService.buscarPorId(id);
        Long vendaId = item.getVenda().getId();
        itemVendaService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Item excluído com sucesso!");
        return "redirect:/vendas/" + vendaId + "/itens";
    }
}