package com.servicio.exchange.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String username;
	
	private String email;
	
	private String password;
	
	@ManyToMany
	@JoinTable(name="usuarios_roles", joinColumns = @JoinColumn(name="usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name="rol_id", referencedColumnName = "id"))
	private Set<Rol> roles = new HashSet<>();
	

}
