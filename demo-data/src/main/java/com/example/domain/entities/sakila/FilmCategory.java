package com.example.domain.entities.sakila;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;

import com.example.core.domain.entities.AbstractEntity;


/**
 * The persistent class for the film_category database table.
 * 
 */
@Entity
@Table(name="film_category")
@NamedQuery(name="FilmCategory.findAll", query="SELECT f FROM FilmCategory f")
public class FilmCategory extends AbstractEntity<FilmCategory> implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FilmCategoryPK id/* = new FilmCategoryPK()*/;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@MapsId("categoryId")
	@JoinColumn(name="category_id")
	private Category category;

	//bi-directional many-to-one association to Film
	@ManyToOne
	@MapsId("filmId")
	@JoinColumn(name="film_id")
	private Film film;

	public FilmCategory() {
	}

	public FilmCategory(Film film, Category category) {
		super();
		id = new FilmCategoryPK();
		this.film = film;
		this.category = category;
	}

	public FilmCategoryPK getId() {
		return this.id;
	}

	public void setId(FilmCategoryPK id) {
		this.id = id;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

}