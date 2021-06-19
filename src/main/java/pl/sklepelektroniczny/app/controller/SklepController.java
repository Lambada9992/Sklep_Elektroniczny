package pl.sklepelektroniczny.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sklepelektroniczny.app.inModels.DaneZamowienia;
import pl.sklepelektroniczny.app.inModels.ProduktExtFiltr;
import pl.sklepelektroniczny.app.outModels.ParametrExt;
import pl.sklepelektroniczny.app.outModels.ProduktExt;
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
    public String produkty(Model model) {
        model.addAttribute("logged", uslugiService.isLoggedIn());
        model.addAttribute("koszykIlosc", uslugiService.getKoszykIlosc());
        model.addAttribute("kategorie", produktyService.getPodTypyDeep2(null));
        model.addAttribute("produkty", produktyService.getAllProdukty());
        List<ParametrExt> parametry = produktyService.getAllParametr(null);
        model.addAttribute("parametry", parametry);
        ProduktExtFiltr filtry = new ProduktExtFiltr();
        filtry.prepare(parametry);
        model.addAttribute("filtry", filtry);

        model.addAttribute("nadKategorie", null);
        return "produkty";
    }

    @PostMapping
    public String produktyPost(@ModelAttribute("filtry") ProduktExtFiltr filtry, Model model) {
        model.addAttribute("logged", uslugiService.isLoggedIn());
        model.addAttribute("koszykIlosc", uslugiService.getKoszykIlosc());
        model.addAttribute("filtry", filtry);
        System.out.println(filtry.getTest());
        return "produkty";
    }

    @GetMapping("produkt/{id_produkt}")
    public String produkt(@PathVariable int id_produkt, Model model) {
        model.addAttribute("logged", uslugiService.isLoggedIn());
        model.addAttribute("koszykIlosc", uslugiService.getKoszykIlosc());
        ProduktExt produkt = produktyService.getProdukt(id_produkt);
        if (produkt == null) return "redirect:/sklep";

        model.addAttribute("produkt", produktyService.getProdukt(id_produkt));
        return "produkt";
    }

    @GetMapping("produkt/{id_produkt}/dodaj")
    public String produktDodaj(@PathVariable int id_produkt, Model model) {
        uslugiService.dodanieProduktuDoKoszyka(id_produkt);
        return "redirect:/sklep/produkt/" + Integer.toString(id_produkt);
    }


    @RequestMapping("/dodaj/{id_produkt}")
    public String dodaj(@PathVariable int id_produkt, Model model) {
        boolean done = uslugiService.dodanieProduktuDoKoszyka(id_produkt);

        return "redirect:/sklep";
    }

    @RequestMapping("/koszyk")
    public String koszyk(Model model) {
        model.addAttribute("logged", uslugiService.isLoggedIn());
        model.addAttribute("koszykIlosc", uslugiService.getKoszykIlosc());
        model.addAttribute("koszyk", uslugiService.getKoszyk());

        return "koszyk";
    }

    @RequestMapping("/koszyk/dodaj/{id_produkt}")
    public String koszykDodaj(@PathVariable int id_produkt, Model model) {
        boolean done = uslugiService.dodanieProduktuDoKoszyka(id_produkt);
        return "redirect:/sklep/koszyk";
    }

    @RequestMapping("/koszyk/usun/{id_produkt}")
    public String koszykUsun(@PathVariable int id_produkt, Model model) {
        boolean done = uslugiService.usuwanieProduktuZKoszyka(id_produkt);

        return "redirect:/sklep/koszyk";
    }

    @GetMapping("/zamowienia")
    public String zamowienia(Model model) {
        model.addAttribute("logged", uslugiService.isLoggedIn());
        model.addAttribute("koszykIlosc", uslugiService.getKoszykIlosc());
        model.addAttribute("zamowienia", uslugiService.getZamowienia());
        return "zamowienia";
    }

    @GetMapping("/zamowienia/anulowanie/{id_zamowienia}")
    public String anulowanieZamowienia(@PathVariable int id_zamowienia, Model model) {
        uslugiService.anulujZamowienie(id_zamowienia);
        return "redirect:/sklep/zamowienia";
    }

    @GetMapping("/zamawianie")
    public String daneZamowienia(Model model) {
        model.addAttribute("logged", uslugiService.isLoggedIn());
        model.addAttribute("koszykIlosc", uslugiService.getKoszykIlosc());
        model.addAttribute("koszyk", uslugiService.getKoszyk());
        model.addAttribute("dane", new DaneZamowienia());
        return "dane_zamowienia";
    }

    @PostMapping("/zamawianie")
    public String daneZamowieniaPrzyjecie(@ModelAttribute("dane") DaneZamowienia dane, Model model) {
        model.addAttribute("logged", uslugiService.isLoggedIn());
        model.addAttribute("koszykIlosc", uslugiService.getKoszykIlosc());
        model.addAttribute("koszyk", uslugiService.getKoszyk());
        if (dane == null) {
            model.addAttribute("dane", new DaneZamowienia());
            return "dane_zamowienia";
        } else {
            model.addAttribute("dane", dane);
        }
        if (dane.getUlica() == null || dane.getMiasto() == null || dane.getKodPocztowy() == null)
            return "dane_zamowienia";
        if (dane.getUlica().length() == 0 || dane.getMiasto().length() == 0 || dane.getKodPocztowy().length() == 0)
            return "dane_zamowienia";

        boolean done = uslugiService.zlozZamowienie(dane);
        if (done) {
            return "redirect:/sklep/zamowienia";
        } else {
            return "dane_zamowienia";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "logowanie";
    }

}
