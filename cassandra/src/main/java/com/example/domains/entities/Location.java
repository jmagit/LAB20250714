package com.example.domains.entities;

import org.springframework.data.cassandra.core.mapping.Element;
import org.springframework.data.cassandra.core.mapping.Tuple;

import lombok.Value;

@Tuple
@Value
public class Location {
 	@Element(0) private float longitude;
 	@Element(1) private float latitude;
}
