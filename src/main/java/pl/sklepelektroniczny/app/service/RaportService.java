package pl.sklepelektroniczny.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sklepelektroniczny.app.raportModel.Raport1out;
import pl.sklepelektroniczny.app.raportModel.Raport2out;
import pl.sklepelektroniczny.app.raportModel.Raport3out;
import pl.sklepelektroniczny.app.repositories.ZamowienieProduktRepository;
import pl.sklepelektroniczny.app.repositories.ZamowienieRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class RaportService {

    @Autowired
    ZamowienieProduktRepository zamowienieProduktRepository;

    @Autowired
    ZamowienieRepository zamowienieRepository;

    @PersistenceContext
    EntityManager entityManager;

    public List<Raport1out> generateRaport1(String od_daty, String do_daty, String producent, String typ, String tryb) {
        List<Raport1out> result = new ArrayList<>();
        tryb.toLowerCase(Locale.ROOT);
        if (tryb == null) return result;

        Query q = null;

        if (tryb.equals("normalny")) {
            String query =
                    "SELECT p.id_produkt,p.nazwa,t.nazwa AS Typ, prod.nazwa AS producent,zp.cena, \n" +
                            "SUM(zp.ilosc) as ilosc,(zp.cena*ilosc) as suma,\n" +
                            "year(z.data_zamowienia) as rok,month(z.data_zamowienia) as miesiac\n" +
                            "FROM zamowienie_produkt AS zp\n" +
                            "JOIN zamowienie AS z USING(id_zamowienia)\n" +
                            "JOIN produkt AS p USING(id_produkt)\n" +
                            "JOIN producent as prod USING(id_producent)\n" +
                            "JOIN typ AS t USING(id_typ)\n" +
                            "WHERE z.data_zamowienia BETWEEN \"" + od_daty + "\" AND \"" + do_daty + "\"\n" +
                            "GROUP BY p.id_produkt ,year(z.data_zamowienia),month(z.data_zamowienia)\n" +
                            "HAVING producent LIKE \"%" + producent + "%\" AND Typ LIKE \"%" + typ + "%\"\n" +
                            "ORDER BY ilosc DESC,t.nazwa ASC,prod.nazwa ASC";
            q = entityManager.createNativeQuery(query);
        } else if (tryb.equals("miesiace")) {
            String query = "SELECT p.id_produkt,p.nazwa,t.nazwa AS Typ, prod.nazwa AS producent,zp.cena, \n" +
                    "SUM(zp.ilosc) as ilosc,(zp.cena*ilosc) as suma,\n" +
                    "year(z.data_zamowienia) as rok,month(z.data_zamowienia) as miesiac\n" +
                    "FROM zamowienie_produkt AS zp\n" +
                    "JOIN zamowienie AS z USING(id_zamowienia)\n" +
                    "JOIN produkt AS p USING(id_produkt)\n" +
                    "JOIN producent as prod USING(id_producent)\n" +
                    "JOIN typ AS t USING(id_typ)\n" +
                    "WHERE z.data_zamowienia BETWEEN \"" + od_daty + "\" AND \"" + do_daty + "\"\n" +
                    "GROUP BY p.id_produkt ,year(z.data_zamowienia),month(z.data_zamowienia)\n" +
                    "HAVING producent LIKE \"%" + producent + "%\" AND Typ LIKE \"%" + typ + "%\"\n" +
                    "ORDER BY ilosc DESC,t.nazwa ASC,prod.nazwa ASC";
            q = entityManager.createNativeQuery(query);
        } else if (tryb.equals("kwartaly")) {
            String query = "SELECT p.id_produkt,p.nazwa,t.nazwa AS Typ, prod.nazwa AS producent,zp.cena, \n" +
                    "SUM(zp.ilosc) as ilosc,(zp.cena*ilosc) as suma,\n" +
                    "year(z.data_zamowienia) as rok,((month(z.data_zamowienia) div 4)+1) as kwartal\n" +
                    "FROM zamowienie_produkt AS zp\n" +
                    "JOIN zamowienie AS z USING(id_zamowienia)\n" +
                    "JOIN produkt AS p USING(id_produkt)\n" +
                    "JOIN producent as prod USING(id_producent)\n" +
                    "JOIN typ AS t USING(id_typ)\n" +
                    "WHERE z.data_zamowienia BETWEEN \"" + od_daty + "\" AND \"" + do_daty + "\"\n" +
                    "GROUP BY p.id_produkt ,year(z.data_zamowienia),((month(z.data_zamowienia) div 4)+1)\n" +
                    "HAVING producent LIKE \"%" + producent + "%\" AND Typ LIKE \"%" + typ + "%\"\n" +
                    "ORDER BY ilosc DESC,t.nazwa ASC,prod.nazwa ASC";
            q = entityManager.createNativeQuery(query);
        } else {
            return result;
        }


        List<Object[]> queryResult = q.getResultList();

        if (tryb.equals("normalny")) {
            for (Object[] a : queryResult) {
                result.add(new Raport1out((int) a[0], (String) a[1], (String) a[2], (String) a[3],
                        ((BigDecimal) a[4]).floatValue(), ((BigDecimal) a[5]).intValue(),
                        ((BigDecimal) a[6]).floatValue(), "", ""));
            }
        } else {
            for (Object[] a : queryResult) {
                result.add(new Raport1out((int) a[0], (String) a[1], (String) a[2], (String) a[3],
                        ((BigDecimal) a[4]).floatValue(), ((BigDecimal) a[5]).intValue(),
                        ((BigDecimal) a[6]).floatValue(), Integer.toString((int) a[7]), Integer.toString((int) a[8])));
            }
        }

        return result;
    }

    public List<Raport2out> generateRaport2(String od_daty, String do_daty, String nazwaProduktu, String typ) {
        List<Raport2out> result = new ArrayList<>();
        String query = "SELECT zp.id_zamowienia,zp.ilosc,z.data_zamowienia,(zp.cena * zp.ilosc) AS cena,u.id_uzytkownik,u.login,ss.status_zamowienia\n" +
                "FROM zamowienie_produkt AS zp\n" +
                "JOIN zamowienie as z USING(id_zamowienia)\n" +
                "JOIN produkt as p USING(id_produkt)\n" +
                "JOIN uzytkownik as u USING(id_uzytkownik)\n" +
                "JOIN slownikstatusow as ss USING(id_status)\n" +
                "JOIN typ AS t USING(id_typ)\n" +
                "WHERE z.data_zamowienia BETWEEN \"" + od_daty + "\" AND \"" + do_daty + "\"\n" +
                "AND p.nazwa LIKE \"%" + nazwaProduktu + "%\" AND t.nazwa LIKE \"%" + typ + "%\"\n" +
                "ORDER BY z.data_zamowienia DESC,u.login ASC, zp.ilosc DESC";
        Query q = entityManager.createNativeQuery(query);
        List<Object[]> queryResult = q.getResultList();

        for (Object[] a : queryResult) {
            result.add(new Raport2out((int) a[0], (int) a[1], ((Timestamp) a[2]).toString(), ((BigDecimal) a[3]).floatValue(),
                    ((int) a[4]), ((String) a[5]),
                    ((String) a[6])));
        }

        return result;
    }

    public List<Raport3out> generateRaport3(String od_daty, String do_daty, String producent, String typ, String tryb) {
        List<Raport3out> result = new ArrayList<>();
        Query q = null;

        if (tryb.equals("normalny")) {
            String query = "SELECT u.plec, COUNT(DISTINCT z.id_zamowienia) as IloscZamowien,\n" +
                    "SUM(zp.ilosc) as IloscProduktow, SUM(zp.ilosc * zp.cena) as wartosc, t.nazwa as Typ\n" +
                    "FROM zamowienie_produkt AS zp\n" +
                    "JOIN zamowienie as z USING(id_zamowienia)\n" +
                    "JOIN uzytkownik as u USING(id_uzytkownik)\n" +
                    "JOIN produkt as p USING(id_produkt)\n" +
                    "JOIN typ as t USING(id_typ)\n" +
                    "JOIN producent as prod USING(id_producent)\n" +
                    "WHERE z.data_zamowienia BETWEEN \"" + od_daty + "\" AND \"" + do_daty + "\"\n" +
                    "AND t.nazwa LIKE \"%" + typ + "%\" AND prod.nazwa LIKE \"%" + producent + "%\"\n" +
                    "GROUP BY u.plec";
            q = entityManager.createNativeQuery(query);
        } else if (tryb.equals("typy")) {
            String query = "SELECT u.plec, COUNT(DISTINCT z.id_zamowienia) as IloscZamowien,\n" +
                    "SUM(zp.ilosc) as IloscProduktow, SUM(zp.ilosc * zp.cena) as wartosc, t.nazwa as Typ\n" +
                    "FROM zamowienie_produkt AS zp\n" +
                    "JOIN zamowienie as z USING(id_zamowienia)\n" +
                    "JOIN uzytkownik as u USING(id_uzytkownik)\n" +
                    "JOIN produkt as p USING(id_produkt)\n" +
                    "JOIN typ as t USING(id_typ)\n" +
                    "JOIN producent as prod USING(id_producent)\n" +
                    "WHERE z.data_zamowienia BETWEEN \"" + od_daty + "\" AND \"" + do_daty + "\"\n" +
                    "AND t.nazwa LIKE \"%" + typ + "%\" AND prod.nazwa LIKE \"%" + producent + "%\"\n" +
                    "GROUP BY u.plec,t.id_typ\n" +
                    "ORDER BY t.id_typ";
            q = entityManager.createNativeQuery(query);
        } else {
            return result;
        }

        List<Object[]> queryResult = q.getResultList();

        if (tryb.equals("normalny")) {
            for (Object[] a : queryResult) {
                result.add(new Raport3out((String) a[0], ((BigInteger) a[1]).intValue(),
                        ((BigDecimal) a[2]).intValue(), ((BigDecimal) a[3]).floatValue(),
                        ""));
            }
        } else {

            for (Object[] a : queryResult) {
                result.add(new Raport3out((String) a[0], ((BigInteger) a[1]).intValue(),
                        ((BigDecimal) a[2]).intValue(), ((BigDecimal) a[3]).floatValue(),
                        ((String) a[4])));
            }
        }

        return result;
    }

}
