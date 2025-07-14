package com.example.domain.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.example.core.domain.entities.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;



/**
 * The persistent class for the actor database table.
 * 
 */
@Entity
@Table(name="actor")
@NamedQuery(name="Actor.findAll", query="SELECT a FROM Actor a")
public class Actor extends AbstractEntity<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="actor_id", unique=true, nullable=false)
	private int actorId;

	@Column(name="first_name", nullable=false, length=45)
	private String firstName;

	@Column(name="last_name", nullable=false, length=45)
	private String lastName;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	private Timestamp lastUpdate;

    @ManyToMany(mappedBy = "actors")
//    @ManyToMany
//    @JoinTable(
//        name = "film_actor",
//        joinColumns = @JoinColumn(name = "actor_id"),
//        inverseJoinColumns = @JoinColumn(name = "film_id")
//    )
	private Set<Film> films = new HashSet<Film>();

	public Actor() {
	}

	public Actor(int actorId) {
		super();
		this.actorId = actorId;
	}

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<Film> getFilms() {
		return this.films;
	}

	public void setFilms(Set<Film> filmActors) {
		this.films = filmActors;
	}

	public Set<Film> addFilm(Film film) {
		films.add(film);
		return films;
	}

//	public Set<Film> addFilm(int id) {
//		var 
//		films.add(film);
//		return films;
//	}
//
//	public FilmActor removeFilmActor(FilmActor filmActor) {
//		getFilmActors().remove(filmActor);
//		filmActor.setActor(null);
//
//		return filmActor;
//	}

	@Override
	public int hashCode() {
		return Objects.hash(actorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Actor))
			return false;
		return actorId == ((Actor) obj).actorId;
	}

	@Override
	public String toString() {
		return "Actor [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate="
				+ lastUpdate + "]";
	}

}