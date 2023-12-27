package com.recondev.newsaggregator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "source") // Customize table name if needed
public class Source {
    @Id
    private String id;
    private String name;
    @OneToMany(mappedBy = "source")
    private Set<NewsArticle> articles; // A set of articles related to this source

    public Source() {
    }

    public Source(String id, String name, Set<NewsArticle> articles) {
        this.id = id;
        this.name = name;
        this.articles = articles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return Objects.equals(id, source.id) && Objects.equals(name, source.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "SourceRepository{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
// Getters and setters
}
