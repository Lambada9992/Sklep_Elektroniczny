package pl.sklepelektroniczny.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sklepelektroniczny.app.raportModel.*;
import pl.sklepelektroniczny.app.service.RaportService;

import java.util.ArrayList;

@Controller
public class RaportController {

    @Autowired
    RaportService raportService;

    @GetMapping("/raport")
    public String raport() {

        return "raporty";
    }

    @GetMapping("/raport/1")
    public String raport1Get(Model model) {
        model.addAttribute("raport", new Raport1in());
        model.addAttribute("dane", new ArrayList<Raport1out>());
        return "raport1";
    }

    @PostMapping("/raport/1")
    public String raport1Post(@ModelAttribute("raport") Raport1in raport, Model model) {
        model.addAttribute("raport", raport);
        model.addAttribute("dane", raportService.generateRaport1(raport.getOd_daty(), raport.getDo_daty(),
                raport.getProducent(), raport.getTyp(), raport.getTryb()));
        return "raport1";
    }

    @GetMapping("/raport/2")
    public String raport2Get(Model model) {
        model.addAttribute("raport", new Raport2in());
        model.addAttribute("dane", new ArrayList<Raport2out>());
        return "raport2";
    }

    @PostMapping("/raport/2")
    public String raport2Post(@ModelAttribute("raport") Raport2in raport, Model model) {
        model.addAttribute("raport", raport);
        model.addAttribute("dane", raportService.generateRaport2(raport.getOd_daty()
                , raport.getDo_daty(), raport.getNazwaProduktu(), raport.getTyp()));
        return "raport2";
    }

    @GetMapping("/raport/3")
    public String raport3Get(Model model) {
        model.addAttribute("raport", new Raport3in());
        model.addAttribute("dane", new ArrayList<Raport3out>());
        return "raport3";
    }

    @PostMapping("/raport/3")
    public String raport3Post(@ModelAttribute("raport") Raport3in raport, Model model) {
        model.addAttribute("raport", raport);
        model.addAttribute("dane", raportService.generateRaport3(raport.getOd_daty(), raport.getDo_daty(),
                raport.getProducent(), raport.getTyp(), raport.getTryb()));

        return "raport3";
    }

}
