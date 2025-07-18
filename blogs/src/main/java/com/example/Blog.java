package com.example;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
 
// Importar blogs.csv con Kibana: Get started by adding integrations > Upload a file
// Importar blogs.csv con Kibana: Machine Learning > Visualize data from a file > Upload data from a file

@Data
@Builder
@Document(indexName = "blogs")
public class Blog {
	private @Id String id;
	private @Field(type = Keyword) String author;
	private @Field(type = Text) String category;
	private @Field(type = Text) String content;
	private @Field(type = Keyword) String locales;
	private @Field(type = Date, name = "publish_date" ) String publishDate;
    private @Field(type = Text) String seo_title;
    private @Field(type = Text) String title;
    private @Field(type = Keyword) String url;
	private @Field(type = Date, name = "@timestamp" ) @ReadOnlyProperty String timestamp;

}
