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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column
	private String descrizione;
	
	@Column(updatable = false, nullable = false)
	private LocalDateTime dataCreazione;
	
	@Column(nullable = false)
	private LocalDateTime dataUltimoAggiornamento;
	
	// è lazy perchè non voglio che si aggiorni quando persisto
	@ManyToOne(fetch = FetchType.EAGER)
	//column(nullable = false)
	private User proprietario;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<User> utentiCondivisi;
	
	// è eager perchè aggiorno quando persisto	
	@OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	//@JoinColumn(name = "project_id")
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCreazione == null) ? 0 : dataCreazione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((proprietario == null) ? 0 : proprietario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (dataCreazione == null) {
			if (other.dataCreazione != null)
				return false;
		} else if (!dataCreazione.equals(other.dataCreazione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (proprietario == null) {
			if (other.proprietario != null)
				return false;
		} else if (!proprietario.equals(other.proprietario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", dataCreazione="
				+ dataCreazione + ", dataUltimoAggiornamento=" + dataUltimoAggiornamento + ", proprietario="
				+ proprietario + "]";
	}

	


	
}
