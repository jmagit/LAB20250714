package com.example.domains.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Table(value = "videos")
public class Video {
	@PrimaryKey
	private UUID videoid = UUID.randomUUID();
	@Pattern(regexp = "^[A-Z]+$", message = "tiene que estar en mayúsculas")
	private String videoname;
	@NotBlank
	private String username;
	private String description;
	private List<String> tags;
	@Column("upload_date")
	private LocalDateTime uploadDate;
}
