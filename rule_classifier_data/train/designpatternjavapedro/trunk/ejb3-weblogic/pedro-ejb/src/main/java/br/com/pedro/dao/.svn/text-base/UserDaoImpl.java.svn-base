package br.com.pedro.dao;

import java.util.Date;

import javax.ejb.Stateless;

import br.com.pedro.domain.User;

@Stateless
public class UserDaoImpl implements UserDao {

	public User getUser() {
		User user = new User();
		user.setFirstName("Pedro Aoki");
		user.setBirthday(new Date());
		user.setId(new Long("1"));
		user.setLastName("Magalhaes");
		return user;
	}

}
