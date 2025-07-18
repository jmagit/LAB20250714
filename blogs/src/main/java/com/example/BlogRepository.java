package com.example;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.SourceFilters;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.repository.CrudRepository;

public interface BlogRepository extends CrudRepository<Blog, String> {
	@Highlight(fields = { 
			@HighlightField(name = "content"), 
			@HighlightField(name = "title") 
			})
	SearchHits<Blog> findByContentContains(String search);
	
	@SourceFilters(includes = {"author", "title"})
	SearchHits<Blog> findByTitleContains(String search);
}
