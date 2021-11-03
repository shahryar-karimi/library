package ir.shahryar.library.book;

import ir.shahryar.library.util.JSONable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book")
public class Book implements JSONable {
    @Id
    private String id;
    private String name;
    private String body;
    private String author;
    private String renterNationalId;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRenterNationalId() {
        return renterNationalId;
    }

    public void setRenterNationalId(String renterNationalId) {
        this.renterNationalId = renterNationalId;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
