package pl.sklepelektroniczny.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sklepelektroniczny.app.model.Uzytkownik;
import pl.sklepelektroniczny.app.repositories.UzytkownikRepository;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UzytkownikRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Uzytkownik> user = userRepository.findByLogin(userName);
        user.orElseThrow(()-> new UsernameNotFoundException("Not found:" + userName));
        return user.map(MyUserDetail::new).get();
    }
}
