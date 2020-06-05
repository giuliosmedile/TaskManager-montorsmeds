package com.uniroma3.montorsmeds.TaskManager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "Projects")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	private LocalDateTime dataCreazione;
	
	private LocalDateTime dataUltimoAggiornamento;
	
	// è lazy perchè non voglio che si aggiorni quando persisto
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(nullable = false)
	private User proprietario;

	@ManyToMany
	private List<User> utentiCondivisi;
	
	// è eager perchè aggiorno quando persisto	
	@OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "project_id")
	private List<Task> tasks;
	
	public Project() {
		this.utentiCondivisi = new ArrayList<>();
		this.tasks = new ArrayList<>();
	}
	
	public Project(String nome, User proprietario) {
		this();
		this.nome = nome;
		this.proprietario = proprietario;
	}

	public void addMember(User user) {
		this.utentiCondivisi.add(user);
	}
	
	public void removeMember(User user) {
		this.utentiCondivisi.remove(user);
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
	}
	
	public void removeTask(Task task) {
		this.tasks.remove(task);
	}
	// ==================================================================
	// ======================== GETTER E SETTER =========================
	// ==================================================================
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public User getProprietario() {
		return proprietario;
	}

	public void setProprietario(User proprietario) {
		this.proprietario = proprietario;
	}

	public List<User> getUtentiCondivisi() {
		return utentiCondivisi;
	}

	public void setUtentiCondivisi(List<User> utentiCondivisi) {
		this.utentiCondivisi = utentiCondivisi;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public LocalDateTime getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}

	public void setDataUltimoAggiornamento(LocalDateTime dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	@PrePersist
	protected void onPersist() {
		this.dataCreazione = LocalDateTime.now();
		this.dataUltimoAggiornamento = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.dataUltimoAggiornamento = LocalDateTime.now();
	}

	


	
}
