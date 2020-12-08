package database;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "maps", schema = "korkowy", catalog = "")
public class MapsEntity {
	private int id;
	private String name;
	private String author;
	private String description;
	private BigDecimal rating;
	private int time;
	private BigDecimal difficultyLevel;
	private Timestamp creationDate;
	private Timestamp editionDate;
	private int numOfPlays;
	private int numOfStages;
	private String src;
	private byte[] img;

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "rating")
	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	@Basic
	@Column(name = "time")
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Basic
	@Column(name = "difficulty_level")
	public BigDecimal getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(BigDecimal difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	@Basic
	@Column(name = "creation_date")
	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	@Basic
	@Column(name = "edition_date")
	public Timestamp getEditionDate() {
		return editionDate;
	}

	public void setEditionDate(Timestamp editionDate) {
		this.editionDate = editionDate;
	}

	@Basic
	@Column(name = "num_of_plays")
	public int getNumOfPlays() {
		return numOfPlays;
	}

	public void setNumOfPlays(int numOfPlays) {
		this.numOfPlays = numOfPlays;
	}

	@Basic
	@Column(name = "num_of_stages")
	public int getNumOfStages() {
		return numOfStages;
	}

	public void setNumOfStages(int numOfStages) {
		this.numOfStages = numOfStages;
	}

	@Basic
	@Column(name = "src")
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Basic
	@Column(name = "img")
	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MapsEntity that = (MapsEntity) o;
		return id == that.id &&
				time == that.time &&
				numOfPlays == that.numOfPlays &&
				numOfStages == that.numOfStages &&
				Objects.equals(name, that.name) &&
				Objects.equals(author, that.author) &&
				Objects.equals(description, that.description) &&
				Objects.equals(rating, that.rating) &&
				Objects.equals(difficultyLevel, that.difficultyLevel) &&
				Objects.equals(creationDate, that.creationDate) &&
				Objects.equals(editionDate, that.editionDate) &&
				Objects.equals(src, that.src) &&
				Arrays.equals(img, that.img);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(id, name, author, description, rating, time, difficultyLevel, creationDate, editionDate, numOfPlays, numOfStages, src);
		result = 31 * result + Arrays.hashCode(img);
		return result;
	}
}
