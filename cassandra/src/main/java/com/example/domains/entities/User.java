package com.example.domains.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Data
@Table(value = "users")
public class User {
	@PrimaryKey
	private String username;
	private String firstname;
	private String lastname;
	private List<String> email;
	private String password;
	@Column("created_date")
	private LocalDateTime createdDate;
	@CassandraType(type = CassandraType.Name.UDT, userTypeName = "address")
	private Address address;
//	@Version
//	int version;
}
