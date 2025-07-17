package com.example.contracts.domain.repositories;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.domains.entities.Video;

@RepositoryRestResource(path="videos", itemResourceRel="video", collectionResourceRel="videos")
public interface VideosRepository  extends CassandraRepository<Video, UUID> {
}
