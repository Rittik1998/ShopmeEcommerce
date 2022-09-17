package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWihOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userRittikB = new User("rittik@gmail.com", "Rittik2022", "Rittik", "Banerjee");
		userRittikB.addRole(roleAdmin);
		
		User savedUser = repo.save(userRittikB);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWihTwoRoles() {
		User userRavi = new User("ravi@gmail.com", "Ravi2022", "Ravi", "Kumar");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		User savedUser = repo.save(userRavi);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
		
	}
	
	@Test
	public void testGetUserById() {
		User userRittik = repo.findById(1).get();
		System.out.println(userRittik);
		assertThat(userRittik).isNotNull();
		
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userRittik = repo.findById(1).get();
		userRittik.setEnabled(true);
		userRittik.setEmail("rittikb@gmail.com");
		
		repo.save(userRittik);
		
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userRavi = repo.findById(2).get();
		Role roleEditor = new Role(3);
		userRavi.getRoles().remove(roleEditor);
		Role roleSalesperson = new Role(2);
		userRavi.addRole(roleSalesperson);
		
		repo.save(userRavi);
		
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
		
	}
}
