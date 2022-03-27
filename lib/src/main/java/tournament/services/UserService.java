package tournament.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tournament.models.User;
import tournament.repos.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	UserRepository uRep;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return uRep.findAnyByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such username") );
	}
	
	public User saveUser(User user) {
		user.setPassword(encoder.encode(user.getRawPassword() ) );
		return uRep.save(user);
	}

}
