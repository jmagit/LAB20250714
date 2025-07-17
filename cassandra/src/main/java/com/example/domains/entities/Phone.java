package com.example.domains.entities;

import java.util.Set;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Data;

@Data
@UserDefinedType
public class Phone {
	private String number;
	private Set<String> tags;
}
