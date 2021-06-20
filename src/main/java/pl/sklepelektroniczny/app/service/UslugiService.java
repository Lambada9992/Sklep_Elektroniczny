package pl.sklepelektroniczny.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.sklepelektroniczny.app.inModels.DaneZamowienia;
import pl.sklepelektroniczny.app.model.*;
import pl.sklepelektroniczny.app.outModels.KoszykExt;
import pl.sklepelektroniczny.app.outModels.KoszykPozycja;
import pl.sklepelektroniczny.app.outModels.ZamowienieExt;
import pl.sklepelektroniczny.app.outModels.ZamowieniePozycja;
import pl.sklepelektroniczny.app.repositories.*;
import pl.sklepelektroniczny.app.security.MyUserDetail;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class UslugiService {


    @Autowired
    KoszykRepository koszykRepository;
    @Autowired
    ProduktRepository produktRepository;
    @Autowired
    UzytkownikRepository uzytkownikRepository;
    @Autowired
    SlownikStatusowRepository slownikStatusowRepository;
    @Autowired
    ZamowienieRepository zamowienieRepository;
    @Autowired
    ZamowienieProduktRepository zamowienieProduktRepository;
    @Autowired
    CenaRepository cenaRepository;

    @Autowired
    ProduktyService produktyService;

    public List<ZamowienieExt> getZamowienia() {
        Uzytkownik user = getUser();
        if (user == null) return null;

        List<ZamowienieExt> result = new ArrayList<>();
        result.addAll(zamowienieRepository.zamowieniaUzytkownika(user.getId_uzytkownik())
                .stream().map(zam -> new ZamowienieExt(zam)).collect(Collectors.toList()));

        result.forEach(zamowienie -> {
            List<ZamowienieProdukt> zp = zamowienieProduktRepository
                    .getZamowienieProduktByIdZamowienia(zamowienie.getId_zamowienia());
            List<ZamowieniePozycja> zamowieniePozycja = zp.stream()
                    .map(zps -> new ZamowieniePozycja(produktyService.getProdukt(zps.getId_produkt()), zps))
                    .collect(Collectors.toList());
            zamowienie.setPozycje(zamowieniePozycja);
            zamowienie.setSuma((float) zp.stream().mapToDouble(p -> p.getCena() * p.getIlosc()).sum());
            zamowienie.setStatus(slownikStatusowRepository
                    .getById(zamowienie.getZamowienie().getId_status()).getStatus_zamowienia());
            zamowienie.setAnulowalne(czyMoznaAnulowacZamowienie(zamowienie.getId_zamowienia()));
        });

        return result;
    }

    public boolean czyMoznaAnulowacZamowienie(int id_zamowienia) {
        Uzytkownik user = getUser();
        if (user == null) return false;

        Zamowienie zamowienie = zamowienieRepository.getById(id_zamowienia);
        if (zamowienie == null) return false;

        if (user.getId_uzytkownik() != zamowienie.getId_uzytkownik()) return false;

        SlownikStatusow status = slownikStatusowRepository.getById(zamowienie.getId_status());
        if (status == null) return false;

        if (status.getStatus_zamowienia().equals("Zlozone") ||
                status.getStatus_zamowienia().equals("Zaakceptowane")) {
            return true;
        } else return false;
    }

    @Transactional
    public boolean anulujZamowienie(int id_zamowienia) {
        if (!czyMoznaAnulowacZamowienie(id_zamowienia)) return false;
        Uzytkownik user = getUser();
        Zamowienie zamowienie = zamowienieRepository.getById(id_zamowienia);
        SlownikStatusow status = slownikStatusowRepository.findByNazwaStatusu("Anulowane").orElse(null);
        if (status == null) return false;

        zamowienieRepository.updateStatus(zamowienie.getId_zamowienia(), status.getId_status());

        return true;
    }

    @Transactional
    public boolean zlozZamowienie(DaneZamowienia dane) {
        Uzytkownik user = getUser();
        if (user == null) return false;
        if (!czySaProduktyWKoszyku()) return false;
        if (dane == null) return false;

        Zamowienie zamowienie = new Zamowienie();
        zamowienie.setData_zamowienia(new Timestamp(System.currentTimeMillis()));
        zamowienie.setId_uzytkownik(user.getId_uzytkownik());
        zamowienie.setMiasto(dane.getMiasto());
        zamowienie.setKod_pocztowy(dane.getKodPocztowy());
        zamowienie.setUlica(dane.getUlica());
        SlownikStatusow status = slownikStatusowRepository.findByNazwaStatusu("Zlozone").orElse(null);
        if (status == null) return false;
        zamowienie.setId_status(status.getId_status());

        //sprawdzenie czy wszystkie produkty maja cene
        List<Koszyk> koszyk = koszykRepository.findKoszykUzytkownika(user.getId_uzytkownik());
        AtomicBoolean isFine = new AtomicBoolean(true);
        koszyk.forEach(pozycja -> {
            Cena cena = cenaRepository.findAktCena(pozycja.getId_produkt()).orElse(null);
            if (cena == null) isFine.set(false);
        });
        if (!isFine.get()) return false;

        zamowienieRepository.save(zamowienie);
        int zamowienie_id = zamowienie.getId_zamowienia();

        List<ZamowienieProdukt> zamowioneProdukty = koszyk.stream().map(pozycja -> {
            ZamowienieProdukt zp = new ZamowienieProdukt();
            zp.setId_produkt(pozycja.getId_produkt());
            zp.setId_zamowienia(zamowienie_id);
            zp.setIlosc(pozycja.getIlosc());
            Cena cena = cenaRepository.findAktCena(pozycja.getId_produkt()).orElse(null);
            zp.setCena(cena == null ? -1f : cena.getCena());
            return zp;
        }).collect(Collectors.toList());

        zamowioneProdukty.forEach(pozycja -> {
            zamowienieProduktRepository.save(pozycja);
        });

        usuniecieKoszyka();

        return true;
    }

    public boolean czySaProduktyWKoszyku() {

        Uzytkownik user = getUser();
        if (user == null) return false;

        if (koszykRepository.findIloscPozycjiUzytkownika(user.getId_uzytkownik()) >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public KoszykExt getKoszyk() {
        Uzytkownik user = getUser();
        if (user == null) return null;

        List<KoszykPozycja> koszykPozycja = new ArrayList<>();
        List<Koszyk> pozycje = koszykRepository.findKoszykUzytkownika(user.getId_uzytkownik());
        pozycje.forEach(pozycja -> {
            koszykPozycja.add(new KoszykPozycja(
                    produktyService.getProdukt(pozycja.getId_produkt()), pozycja));
        });
        KoszykExt result = new KoszykExt(koszykPozycja);

        return result;
    }

    public int getKoszykIlosc() {
        KoszykExt koszyk = getKoszyk();
        if (koszyk == null) return 0;
        int ilosc = koszyk.getPozycje().stream().mapToInt(p -> p.getIlosc()).sum();
        return ilosc;
    }

    @Transactional
    public boolean dodanieProduktuDoKoszyka(int id_produkt) {
        Uzytkownik user = getUser();
        if (user == null) return false;

        Produkt produkt = produktRepository.findById(id_produkt).orElse(null);
        if (produkt == null) return false;
        Koszyk koszyk = koszykRepository
                .findById_uzytkownikAndId_produkt(user.getId_uzytkownik(), produkt.getId_produkt());
        if (koszyk == null) {
            koszyk = new Koszyk();
            koszyk.setIlosc(1);
            koszyk.setId_uzytkownik(user.getId_uzytkownik());
            koszyk.setId_produkt(produkt.getId_produkt());
            koszykRepository.save(koszyk);
        } else {
            koszykRepository.updateIlosc(koszyk.getId_koszyk(), koszyk.getIlosc() + 1);
        }

        return true;
    }

    @Transactional
    public boolean usuwanieProduktuZKoszyka(int id_produkt) {
        Uzytkownik user = getUser();
        if (user == null) return false;

        Produkt produkt = produktRepository.findById(id_produkt).orElse(null);
        if (produkt == null) return false;
        Koszyk koszyk = koszykRepository
                .findById_uzytkownikAndId_produkt(user.getId_uzytkownik(), produkt.getId_produkt());
        if (koszyk == null) {
            return false;
        } else {
            if (koszyk.getIlosc() > 1) {
                koszykRepository.updateIlosc(koszyk.getId_koszyk(), koszyk.getIlosc() - 1);
            } else {
                koszykRepository.deleteById_koszyk(koszyk.getId_koszyk());
            }
        }
        return true;
    }

    @Transactional
    public boolean usuwanieCalkowiteProduktuZKoszyka(int id_produkt) {
        Uzytkownik user = getUser();
        if (user == null) return false;

        Produkt produkt = produktRepository.findById(id_produkt).orElse(null);
        if (produkt == null) return false;
        Koszyk koszyk = koszykRepository
                .findById_uzytkownikAndId_produkt(user.getId_uzytkownik(), produkt.getId_produkt());
        if (koszyk == null) {
            return false;
        } else {
            koszykRepository.deleteById_koszyk(koszyk.getId_koszyk());
        }
        return true;
    }

    @Transactional
    public boolean usuniecieKoszyka() {
        Uzytkownik user = getUser();
        if (user == null) return false;
        koszykRepository.deleteCalyKoszykUzytkownika(user.getId_uzytkownik());
        return true;
    }

    private Uzytkownik getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof MyUserDetail) {
            username = ((MyUserDetail) principal).getUsername();
        } else {
            return null;
        }
        Uzytkownik user = uzytkownikRepository.findByLogin(username).orElse(null);
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }

    public boolean isLoggedIn() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUserDetail) {
            return true;
        } else {
            return false;
        }
    }
}
