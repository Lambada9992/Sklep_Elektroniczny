package pl.sklepelektroniczny.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sklepelektroniczny.app.model.Producent;
import pl.sklepelektroniczny.app.model.Produkt;
import pl.sklepelektroniczny.app.model.Typ;
import pl.sklepelektroniczny.app.repositories.ProduktRepository;
import pl.sklepelektroniczny.app.repositories.TypRepository;
import pl.sklepelektroniczny.app.service.SklepService;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

@Controller
@RequestMapping("/sklep")
public class SklepController {

    @Autowired
    SklepService sklepService;

    @Autowired
    ProduktRepository produktRepository;

    @GetMapping
    public String produkty(){
        return "produkty";
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

    @GetMapping("/lista")
    public String lista(){

        List<Produkt> lista = produktRepository.findAll();
        for(Produkt produkt : lista){
            System.out.println(//uwazac na null pointer exception
                    produkt.getNazwa()+ " "+
                            produkt.getProducent().getNazwa()+" "+
                            produkt.getTyp().getNazwa()+" "+
                            produkt.getTyp().getRodzic().getNazwa()
            );
        }

        return "moja";
    }

}
