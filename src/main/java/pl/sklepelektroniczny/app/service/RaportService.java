package pl.sklepelektroniczny.app.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sklepelektroniczny.app.model.ZamowienieProdukt;
import pl.sklepelektroniczny.app.raportModel.Raport1;
import pl.sklepelektroniczny.app.raportModel.Raport2;
import pl.sklepelektroniczny.app.raportModel.Raport3;
import pl.sklepelektroniczny.app.repositories.ZamowienieProduktRepository;
import pl.sklepelektroniczny.app.repositories.ZamowienieRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaportService {

    @Autowired
    ZamowienieProduktRepository zamowienieProduktRepository;

    @Autowired
    ZamowienieRepository zamowienieRepository;

    @PersistenceContext
    EntityManager entityManager;

    //TODO Raport 1
    public List<Raport1> generateRaport1(Timestamp od_daty,Timestamp do_daty,String producent, String typ){
        Query q = entityManager.createNativeQuery("SELECT * FROM cena");
        List<Object[]> test = q.getResultList();
        for (Object[] a : test){
            System.out.println(a[0]+" " +a[1]+" " +a[2]+" "+a[3]+" "+a[4]);
        }

        return null;
    }

//    public List<Raport1> generateRaport1(Timestamp od_daty,Timestamp do_daty,String producent, String typ){
//        List<Raport1> result = new ArrayList<>();
//        List<ZamowienieProdukt> zamowienieProdukt = zamowienieProduktRepository.getZamowieniaOkres(od_daty.toString(),do_daty.toString());
//        zamowienieProdukt.forEach(zp -> {
//            Raport1 r1 = result.stream().filter(r->r.getId_produkt()== zp.getId_produkt()).findFirst().orElse(null);
//            if (r1==null){
//                r1 = new Raport1();
//                result.add(r1);
//            }else {
//
//            }
//
//        });
//        System.out.println("legia");
//        return result;
//    }
    //TODO Raport 2
    public List<Raport2> generateRaport2(){
        List<Raport2> result = null;


        return result;
    }
    
    //TODO Raport 3
    public List<Raport3> generateRaport3(){
        List<Raport3> result = null;


        return result;
    }



}
