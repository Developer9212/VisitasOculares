package com.fenoreste.voculares.service;

import com.fenoreste.voculares.dao.UserDao;
import com.fenoreste.voculares.entity.Tabla;
import com.fenoreste.voculares.entity.TablaPK;
import com.fenoreste.voculares.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService{
    
	@Autowired
	private UserDao userRepository;

	@Autowired
	private ITablaService tablaService;

	@Override
	public UserDetails loadUserByUsername(
			String username)
			throws UsernameNotFoundException {

		System.out.println("🔍 Buscando usuario: " + username);

		// 🔐 1 — Buscar usuario técnico (Basic Auth)
		TablaPK pk = new TablaPK("voculares", "user-ws");

		Tabla tbSecurity =tablaService.buscarPorId(pk);

		if (tbSecurity != null &&
				tbSecurity.getDato1().equals(username)) {

			System.out.println("✅ Usuario BASIC encontrado");


			System.out.println("Password BD raw: ["
					+ tbSecurity.getDato2() + "]");

			String passwordDb = tbSecurity.getDato2();

			if (!passwordDb.startsWith("{bcrypt}")) {
				passwordDb = "{bcrypt}" + passwordDb;
			}

			return new org.springframework.security.core.userdetails.User(
					tbSecurity.getDato1(),
					tbSecurity.getDato2(),
					Arrays.asList(
							new SimpleGrantedAuthority("ROLE_API")
					)
			);


		}

		// 🔑 2 — Buscar usuario normal
		User user =	userRepository.findUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(
					"Usuario no valido"
			);
		}

		System.out.println("✅ Usuario APP encontrado");


		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				Arrays.asList(
						new SimpleGrantedAuthority("ROLE_USER")
				)
		);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
    	return (List<User>)userRepository.findAll();
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	

}
