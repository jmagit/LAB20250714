package com.example.domain.entities;

import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the film_category database table.
 * 
 */
@Embeddable
public class FilmCategoryPK implements Serializable {
    //default serial version id, required for serializable classes.
    @Serial
    private static final long serialVersionUID = 1L;

	@Column(name="film_id", insertable=false, updatable=false)
	private int filmId;

	@Column(name="category_id", insertable=false, updatable=false)
	private int categoryId;

	public FilmCategoryPK() {
	}
	public FilmCategoryPK(int filmId, int categoryId) {
		this.filmId = filmId;
		this.categoryId = categoryId;
	}
	public int getFilmId() {
		return this.filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public int getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FilmCategoryPK)) {
			return false;
		}
		FilmCategoryPK castOther = (FilmCategoryPK)other;
		return 
			(this.filmId == castOther.filmId)
			&& (this.categoryId == castOther.categoryId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.filmId;
		hash = hash * prime + ((int) this.categoryId);
		
		return hash;
	}
}