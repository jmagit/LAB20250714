package com.example;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.SearchTemplateQuery;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("blogs")
@Tag(name = "blog-search-controller")
public class SearchResource {

	private final ElasticsearchOperations operations;

	public SearchResource(ElasticsearchOperations operations) {
		this.operations = operations;
	}

	@GetMapping("/_doc/{id}")
	public Blog doc(@PathVariable String id) {
		return operations.get(id, Blog.class);
	}


	@GetMapping("/_search/{terms}")
	public List<SearchHit<Blog>> search(@PathVariable String terms, @ParameterObject Pageable pageable) {
		var query = NativeQuery.builder()
				.withQuery(q -> q
					.match(m -> m
						.field("content")
						.query(terms)
					)
				)
				.build();
		SearchHits<Blog> searchHits = operations.search(query, Blog.class);
		return searchHits.getSearchHits();
//		return SearchHitSupport.searchPageFor(searchHits, pageable);
	}

	@GetMapping("/_template/{terms}")
	public Stream<SearchHit<Blog>> _template(@PathVariable String terms, @ParameterObject Pageable pageable) {
		var query = SearchTemplateQuery.builder().withId("search-in-content-template")
				.withParams(Map.of("query_string", terms, "from", pageable.getOffset(), "size", pageable.getPageSize()))
				.build();
		SearchHits<Blog> searchHits = operations.search(query, Blog.class);
		return searchHits.get();
	}
	
	@Autowired
	RestTemplate restTemplate;

	@Value("${spring.elasticsearch.uris}")
	private String elasticsearchUri;
	
	@PostMapping(path = "/_template", produces = "application/json")
	public String postTemplate(@RequestBody String body) {
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
		return restTemplate.postForObject(elasticsearchUri + "/blogs/_search/template", new HttpEntity(body, headers), String.class);
	}

}
