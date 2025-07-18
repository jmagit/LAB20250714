package com.example.domain.entities.sakila;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Set;


import com.example.core.domain.entities.AbstractEntity;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@EqualsAndHashCode(of = "categoryId", callSuper = false)
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category extends AbstractEntity<Category> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id", unique=true, nullable=false)
	private int categoryId;

	@Column(name="last_update", nullable=false)
	private Timestamp lastUpdate;

	@Column(nullable=false, length=25)
	private String name;

	//bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy="category")
	private Set<FilmCategory> filmCategories;

	public Category() {
	}

	public Category(int categoryId) {
		super();
		this.categoryId = categoryId;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<FilmCategory> getFilmCategories() {
		return this.filmCategories;
	}

	public void setFilmCategories(Set<FilmCategory> filmCategories) {
		this.filmCategories = filmCategories;
	}

	public FilmCategory addFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().add(filmCategory);
		filmCategory.setCategory(this);

		return filmCategory;
	}

	public FilmCategory removeFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().remove(filmCategory);
		filmCategory.setCategory(null);

		return filmCategory;
	}

}