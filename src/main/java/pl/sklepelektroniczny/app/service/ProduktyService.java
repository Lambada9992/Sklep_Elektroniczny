package pl.sklepelektroniczny.app.service;

import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sklepelektroniczny.app.inModels.ProduktExtFiltr;
import pl.sklepelektroniczny.app.model.Cena;
import pl.sklepelektroniczny.app.model.Produkt;
import pl.sklepelektroniczny.app.model.Typ;
import pl.sklepelektroniczny.app.outModels.ParametrExt;
import pl.sklepelektroniczny.app.outModels.ParametrFix;
import pl.sklepelektroniczny.app.outModels.ProduktExt;
import pl.sklepelektroniczny.app.outModels.TypTree;
import pl.sklepelektroniczny.app.repositories.CenaRepository;
import pl.sklepelektroniczny.app.repositories.ProduktParametrRepository;
import pl.sklepelektroniczny.app.repositories.ProduktRepository;
import pl.sklepelektroniczny.app.repositories.TypRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProduktyService {

    @Autowired
    ProduktRepository produktRepository;
    @Autowired
    CenaRepository cenaRepository;
    @Autowired
    ProduktParametrRepository produktParametrRepository;
    @Autowired
    TypRepository typRepository;

    public ProduktExt getProdukt(int id_produkt) {
        ProduktExt result = null;
        Produkt produkt = produktRepository.findById(id_produkt).orElse(null);
        if (produkt == null) return null;
        result = new ProduktExt(produkt);

        Cena cena = cenaRepository.findAktCena(produkt.getId_produkt()).orElse(null);
        if (cena != null) {
            result.setCena(cena.getCena());
        } else {
            result.setCena(-1f);
        }
        result.setParametry(getParametryProduktu(produkt));

        return result;
    }

    public List<ProduktExt> getAllProdukty(ProduktExtFiltr filtr) {
        List<ProduktExt> result = new ArrayList<>();
        List<ProduktExt> allProdukty = getAllProdukty();
        if (filtr == null) return allProdukty;
        allProdukty.forEach(produkt -> {
            if (produkt.checkFiltr(filtr)) {
                result.add(produkt);
            }
        });

        return result;
    }

    public List<ProduktExt> getAllProdukty() {
        List<ProduktExt> result = new ArrayList<>();
        result.addAll(produktRepository.findAll().stream()
                .map(produkt -> new ProduktExt(produkt)).collect(Collectors.toList()));
        result.forEach(produkt -> {
            Cena cena = cenaRepository.findAktCena(produkt.getProdukt().getId_produkt()).orElse(null);
            if (cena != null) {
                produkt.setCena(cena.getCena());
            } else {
                produkt.setCena(-1f);
            }
            produkt.setParametry(getParametryProduktu(produkt.getProdukt()));
        });

        return result;
    }

    public List<ParametrFix> getParametryProduktu(Produkt produkt) {
        List<ParametrFix> result = new ArrayList<>();
        result.addAll(produktParametrRepository
                .findParametryProduktu(produkt.getId_produkt())
                .stream().map(produktParametr -> new ParametrFix(produktParametr))
                .collect(Collectors.toList()));
        return result;
    }

    public List<Typ> getAllNadTypy(String nazwaTypu) {
        if (nazwaTypu == null) return null;
        Typ typ = typRepository.findByNazwa(nazwaTypu).orElse(null);
        return getAllNadTypy(typ);
    }

    public List<Typ> getAllNadTypy(Typ typ) {
        List<Typ> result = new ArrayList<>();
        if (typ == null) {
            return null;
        } else {
            Typ var = typ.getRodzic();
            while (var != null) {
                result.add(var);
                var = var.getRodzic();
            }
        }
        return result;
    }

    public List<TypTree> getPodTypyDeep2(Integer id_typ) {
        List<TypTree> result = new ArrayList<>();
        if (id_typ == null) {
            result.addAll(getPodTypy((Typ) null));
        } else {
            Typ typ = typRepository.findById(id_typ).orElse(null);
            if (typ != null) {
                result.addAll(getPodTypy(typ));
            } else {
                return null;
            }
        }
        result.forEach(typTree -> typTree.setPodTypy(getPodTypy(typTree.getTyp())));
        return result;
    }

    public List<TypTree> getPodTypy(String typNazwa) {
        List<TypTree> result = new ArrayList<>();
        if (typNazwa == null) {
            result.addAll(getPodTypy((Typ) null));
        } else {
            Typ typ = typRepository.findByNazwa(typNazwa).orElse(null);
            if (typ != null) {
                result.addAll(getPodTypy(typ));
            } else {
                return null;
            }
        }
        return result;
    }

    public List<Typ> getAllPodTypyFlat(Typ typ) {
        List<Typ> result = new ArrayList<>();
        List<Typ> dzieci = new ArrayList<>();
        result.addAll(typRepository.findByRodzic(typ));
        result.forEach(dziecko -> {
            dzieci.addAll(getAllPodTypyFlat(dziecko));
        });
        result.addAll(dzieci);
        return result;
    }

    public List<ParametrExt> getAllParametr(Typ typ) {
        List<Produkt> proukty = new ArrayList<>();

        if (typ != null) {
            List<Typ> typy = getAllPodTypyFlat(typ);
            typy.add(typ);


            typy.forEach(_typ -> {
                proukty.addAll(produktRepository.getAllProduktyWhere(_typ.getId_typ()));
            });
        } else {
            proukty.addAll(getAllProdukty().stream().map(produktExt -> produktExt.getProdukt()).collect(Collectors.toList()));
        }

        List<ParametrExt> parametry = new ArrayList<>();
        proukty.forEach(produkt -> {
            List<ParametrFix> parametryProduktu = getParametryProduktu(produkt);
            parametryProduktu.forEach(pp -> {
                AtomicReference<Boolean> found = new AtomicReference<>(false);
                parametry.forEach(p -> {
                    if (p.getParametr().getId_parametr() ==
                            pp.getProduktParametr().getParametr().getId_parametr()) {
                        if (!p.getWartosc().contains(pp.getWartosc())) {
                            p.getWartosc().add(pp.getWartosc());
                        }
                        found.set(true);
                    }
                });
                if (!found.get()) {
                    ParametrExt nowyParametr = new ParametrExt();
                    nowyParametr.setParametr(pp.getProduktParametr().getParametr());
                    nowyParametr.setParametrNazwa(pp.getProduktParametr().getParametr().getNazwa());
                    nowyParametr.setParametrId(pp.getProduktParametr().getParametr().getId_parametr());
                    nowyParametr.getWartosc().add(pp.getWartosc());
                    parametry.add(nowyParametr);
                }
            });

        });

        return parametry;
    }

    private List<TypTree> getPodTypy(Typ typ) {
        List<TypTree> result = new ArrayList<>();
        if (typ != null) {
            if (typ == null) return null;
            result.addAll(typRepository.findByRodzic(typ).stream()
                    .map(_typ -> new TypTree(_typ)).collect(Collectors.toList()));
        } else {
            result.addAll(typRepository.findByRodzicIsNull().stream()
                    .map(_typ -> new TypTree(_typ)).collect(Collectors.toList()));
        }
        return result;
    }

}
