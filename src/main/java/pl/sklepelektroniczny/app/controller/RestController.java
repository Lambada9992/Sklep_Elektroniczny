package pl.sklepelektroniczny.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sklepelektroniczny.app.inModels.DaneZamowienia;
import pl.sklepelektroniczny.app.model.Typ;
import pl.sklepelektroniczny.app.outModels.*;
import pl.sklepelektroniczny.app.service.ProduktyService;
import pl.sklepelektroniczny.app.service.UslugiService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    ProduktyService produktyService;

    @Autowired
    UslugiService uslugiService;

    @RequestMapping("/nadTyp")
    public List<Typ> nadTypy() {
        return produktyService.getAllNadTypy("Smartwatch");
    }

    @RequestMapping("/produkty")
    public List<ProduktExt> produktExtAll() {
        return produktyService.getAllProdukty();
    }

//    @RequestMapping("/produktyFilrt")
//    public List<ProduktExt> produktyFiltrowane() {
//        ProduktExtFiltr filtr = new ProduktExtFiltr();
//        List<ParametrFix> parametry = new ArrayList<>();
//        parametry.add(new ParametrFix("Pamięć wewnętrzna", 3, Integer.toString(64)));
//        parametry.add(new ParametrFix("Pamięć wewnętrzna", 3, Integer.toString(128)));
//
//        filtr.prepare(parametry);
//        return produktyService.getAllProdukty(filtr);
//    }

    @RequestMapping("/koszykAdd")
    public boolean dodanieProduktuDoKoszyka() {
        return uslugiService.dodanieProduktuDoKoszyka(1);
    }

    @RequestMapping("/koszykRemove")
    public boolean usuwanieProduktuDoKoszyka() {
        return uslugiService.usuwanieProduktuZKoszyka(1);
    }

    @RequestMapping("/logged")
    public boolean isLoggedIn() {
        return uslugiService.isLoggedIn();
    }

    @RequestMapping("/koszyk")
    public KoszykExt getKoszyk() {
        return uslugiService.getKoszyk();
    }

    @RequestMapping("/koszykAny")
    public boolean czyJestCosWKoszyku() {
        return uslugiService.czySaProduktyWKoszyku();
    }

    @RequestMapping("/zamowienia")
    public List<ZamowienieExt> getZamowienia() {
        return uslugiService.getZamowienia();
    }

    @RequestMapping("/Zamow")
    public boolean ZlozZamowienie() {
        return uslugiService.zlozZamowienie(new DaneZamowienia("miasto", "ulica", "kodpocztowy"));
    }

    @RequestMapping("/anuluj")
    public boolean anulujZamowienie() {
        return uslugiService.anulujZamowienie(1);
    }

    @RequestMapping("/typMenu")
    public List<TypTree> typyMenu() {
        return produktyService.getPodTypyDeep2(null);
    }

    @RequestMapping("/typ")
    public List<Typ> getPodtypy() {
        return produktyService.getAllPodTypyFlat(null);
    }

    @RequestMapping("/param")
    public List<ParametrExt> parametry() {
        return produktyService.getAllParametr(null);
    }

}
