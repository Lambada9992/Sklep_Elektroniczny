package pl.sklepelektroniczny.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sklepelektroniczny.app.inModels.DaneZamowienia;
import pl.sklepelektroniczny.app.model.Typ;
import pl.sklepelektroniczny.app.outModels.*;
import pl.sklepelektroniczny.app.raportModel.Raport1out;
import pl.sklepelektroniczny.app.raportModel.Raport2out;
import pl.sklepelektroniczny.app.raportModel.Raport3out;
import pl.sklepelektroniczny.app.service.ProduktyService;
import pl.sklepelektroniczny.app.service.RaportService;
import pl.sklepelektroniczny.app.service.UslugiService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    ProduktyService produktyService;

    @Autowired
    UslugiService uslugiService;

    @Autowired
    RaportService raportService;

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

    @RequestMapping("/testy1")
    public List<Raport1out> test1() {
        return raportService.generateRaport1("2018-09-01 09:01:16", "2022-09-01 09:01:16", "", "", "normalny");
    }

    @RequestMapping("/testy2")
    public List<Raport2out> test2() {
        return raportService.generateRaport2("2018-09-01 09:01:16", "2022-09-01 09:01:16", "", "Sma");
    }

    @RequestMapping("/testy3")
    public List<Raport3out> test3() {
        return raportService.generateRaport3("2018-09-01 09:01:16", "2022-09-01 09:01:16", "", "", "typy");
    }

}