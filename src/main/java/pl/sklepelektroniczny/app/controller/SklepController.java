package pl.sklepelektroniczny.app.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sklepelektroniczny.app.model.Produkt;
import pl.sklepelektroniczny.app.repositories.ProduktRepository;
import pl.sklepelektroniczny.app.service.ProduktyService;
import pl.sklepelektroniczny.app.service.UslugiService;

import java.util.List;

@Controller
@RequestMapping("/sklep")
public class SklepController {

    @Autowired
    ProduktyService produktyService;

    @Autowired
    UslugiService uslugiService;

    @GetMapping
    public String produkty(Model model){
        model.addAttribute("logged",uslugiService.isLoggedIn());
        model.addAttribute("kategorie",produktyService.getPodTypyDeep2(null));
        model.addAttribute("produkty", produktyService.getAllProdukty());
        
        model.addAttribute("nadKategorie",null);
        return "produkty";
    }

    @GetMapping("/koszyk")
    public String koszyk(Model model){
        return "moja";
    }

    @GetMapping("/dane")
    public String daneZamowienia(){
        return "dane_zamowienia";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "logowanie";
    }

    @GetMapping("/moja")
    public String moja(Model model){
        model.addAttribute("msg","LegiaWarszawa");
        return "moja";
    }


}
