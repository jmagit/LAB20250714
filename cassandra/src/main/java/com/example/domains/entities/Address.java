package com.example.domains.entities;

import java.util.Set;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Data;

@Data
@UserDefinedType
public class Address {
	private String street;
	private String city;
	private Integer zip;
	@CassandraType(type = CassandraType.Name.UDT, userTypeName = "phone")
	private Set<Phone> phones;
	@CassandraType(type = Name.TUPLE, typeArguments = {Name.FLOAT, Name.FLOAT})
	private Location location;
}
