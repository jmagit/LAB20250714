package com.example.domains.entities;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.core.domain.entities.AbstractEntity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


/**
 * The persistent class for the film database table.
 * 
 */
@Entity
@Table(name="film")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
public class Film extends AbstractEntity<Film> implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	public static enum Rating {
		GENERAL_AUDIENCES("G"), PARENTAL_GUIDANCE_SUGGESTED("PG"), PARENTS_STRONGLY_CAUTIONED("PG-13"), RESTRICTED("R"),
		ADULTS_ONLY("NC-17");

		String value;

		Rating(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Rating getEnum(String value) {
			switch (value) {
			case "G":
				return Rating.GENERAL_AUDIENCES;
			case "PG":
				return Rating.PARENTAL_GUIDANCE_SUGGESTED;
			case "PG-13":
				return Rating.PARENTS_STRONGLY_CAUTIONED;
			case "R":
				return Rating.RESTRICTED;
			case "NC-17":
				return Rating.ADULTS_ONLY;
			case "":
				return null;
			default:
				throw new IllegalArgumentException("Unexpected value: " + value);
			}
		}

		public static final String[] VALUES = { "G", "PG", "PG-13", "R", "NC-17" };
	}
	
	public static enum SpecialFeature {
	    Trailers("Trailers"),
	    Commentaries("Commentaries"),
	    DeletedScenes("Deleted Scenes"),
	    BehindTheScenes("Behind the Scenes");

	    String value;

	    SpecialFeature(String value) {
	        this.value = value;
	    }

		public String getValue() {
			return value;
		}

	    public static SpecialFeature getEnum(String specialFeature) {
	        return Stream.of(SpecialFeature.values())
	                .filter(p -> p.getValue().equals(specialFeature))
	                .findFirst()
	                .orElseThrow(IllegalArgumentException::new);
	    }
	}
	
	@Converter
	private static class RatingConverter implements AttributeConverter<Rating, String> {
		@Override
		public String convertToDatabaseColumn(Rating rating) {
			return rating == null ? null : rating.getValue();
		}

		@Override
		public Rating convertToEntityAttribute(String value) {
			return value == null ? null : Rating.getEnum(value);
		}
	}
	
	@Converter
	private static class SpecialFeatureConverter implements AttributeConverter<Set<SpecialFeature>, String> {
	    @Override
	    public String convertToDatabaseColumn(Set<SpecialFeature> attribute) {
	        if (attribute == null || attribute.size() == 0) {
	            return null;
	        }
	        return attribute.stream()
	                .map(SpecialFeature::getValue)
	                .collect(Collectors.joining(","));
	    }

	    @Override
	    public Set<SpecialFeature> convertToEntityAttribute(String value) {
	        if (value == null) {
	            return EnumSet.noneOf(SpecialFeature.class);
	        }
	        return Arrays.stream(value.split(","))
	                .map(SpecialFeature::getEnum)
	                .collect(Collectors.toCollection(() -> EnumSet.noneOf(SpecialFeature.class)));
	    }
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="film_id", unique=true, nullable=false)
	private int filmId;

	@Lob
	private String description;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	private Timestamp lastUpdate;

	private Integer length;

	@Convert(converter = RatingConverter.class)
	private Rating rating;

	@Column(name="release_year")
	private Short releaseYear;

	@Column(name="rental_duration", nullable=false)
	private byte rentalDuration;

	@Column(name="rental_rate", nullable=false, precision=10, scale=2)
	private BigDecimal rentalRate;

	@Column(name="replacement_cost", nullable=false, precision=10, scale=2)
	private BigDecimal replacementCost;

	@Convert(converter = SpecialFeatureConverter.class)
	private Set<SpecialFeature> specialFeatures = EnumSet.noneOf(SpecialFeature.class);

	@Column(nullable=false, length=128)
	private String title;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name="language_id", nullable=false)
	private Language language;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name="original_language_id")
	private Language languageVO;

	//bi-directional many-to-one association to FilmActor
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "film_actor",
        joinColumns = @JoinColumn(name = "film_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
	private Set<Actor> actors = new HashSet<Actor>();

	//bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy="film", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<FilmCategory> filmCategories = new HashSet<FilmCategory>();

	//bi-directional many-to-one association to Inventory
	@OneToMany(mappedBy="film")
	private Set<Inventory> inventories = new HashSet<Inventory>();

	public Film() {
	}
	
	public Film(int filmId, @NotBlank @Size(max = 128) String title, String description, @Min(1895) Short releaseYear,
			@NotNull Language language, Language languageVO, @Positive byte rentalDuration,
			@Positive @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 2, fraction = 2) BigDecimal rentalRate,
			@Positive Integer length,
			@DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 3, fraction = 2) BigDecimal replacementCost,
			Rating rating) {
		super();
		this.filmId = filmId;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
		this.language = language;
		this.languageVO = languageVO;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.length = length;
		this.replacementCost = replacementCost;
		this.rating = rating;
	}

	public Film(int filmId) {
		super();
		this.filmId = filmId;
	}

	public int getFilmId() {
		return this.filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Rating getRating() {
		return this.rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Short getReleaseYear() {
		return this.releaseYear;
	}

	public void setReleaseYear(Short releaseYear) {
		this.releaseYear = releaseYear;
	}

	public byte getRentalDuration() {
		return this.rentalDuration;
	}

	public void setRentalDuration(byte rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public BigDecimal getRentalRate() {
		return this.rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}

	public BigDecimal getReplacementCost() {
		return this.replacementCost;
	}

	public void setReplacementCost(BigDecimal replacementCost) {
		this.replacementCost = replacementCost;
	}

	// Special Features
	public List<SpecialFeature> getSpecialFeatures() {
		return specialFeatures.stream().toList();
	}

	public void addSpecialFeatures(SpecialFeature specialFeatures) {
		this.specialFeatures.add(specialFeatures);
	}

	public void removeSpecialFeatures(SpecialFeature specialFeatures) {
		this.specialFeatures.remove(specialFeatures);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Language getLanguageVO() {
		return this.languageVO;
	}

	public void setLanguageVO(Language languageVO) {
		this.languageVO = languageVO;
	}

	public Set<Actor> getActors() {
		return this.actors;
	}

	public void setActors(Set<Actor> filmActors) {
		this.actors = filmActors;
	}


	public Actor addActor(int id) {
		var filmActor = new Actor(id);
		actors.add(filmActor);
//		filmActor.setFilm(this);
		return filmActor;
	}
	
	public Actor addActor(Actor filmActor) {
		actors.add(filmActor);
//		filmActor.setFilm(this);

		return filmActor;
	}

	public Actor removeFilmActor(Actor filmActor) {
		actors.remove(filmActor);
//		filmActor.setFilm(null);
		return filmActor;
	}

	public Set<FilmCategory> getFilmCategories() {
		return this.filmCategories;
	}

	public void setFilmCategories(Set<FilmCategory> filmCategories) {
		this.filmCategories = filmCategories;
	}

	public FilmCategory addFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().add(filmCategory);
		filmCategory.setFilm(this);

		return filmCategory;
	}

	public FilmCategory removeFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().remove(filmCategory);
		filmCategory.setFilm(null);

		return filmCategory;
	}

	public Set<Inventory> getInventories() {
		return this.inventories;
	}

	public void setInventories(Set<Inventory> inventories) {
		this.inventories = inventories;
	}

	public Inventory addInventory(Inventory inventory) {
		getInventories().add(inventory);
		inventory.setFilm(this);

		return inventory;
	}

	public Inventory removeInventory(Inventory inventory) {
		getInventories().remove(inventory);
		inventory.setFilm(null);

		return inventory;
	}

	@Override
	public int hashCode() {
		return Objects.hash(filmId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Film))
			return false;
		return filmId == ((Film) obj).filmId;
	}

	@Override
	public String toString() {
		return "Film [filmId=" + filmId + ", title=" + title + ", description=" + description + ", length=" + length
				+ ", rating=" + rating + ", releaseYear=" + releaseYear + ", rentalDuration=" + rentalDuration
				+ ", rentalRate=" + rentalRate + ", replacementCost=" + replacementCost + ", lastUpdate=" + lastUpdate
				+ "]";
	}

}