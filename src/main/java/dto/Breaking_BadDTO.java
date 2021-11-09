package dto;

import entities.Breaking_Bad;

public class Breaking_BadDTO {

    private String url;
    private String quote;
    private String author;
    private Breaking_Bad bb;

    public Breaking_BadDTO() {
    }

    public Breaking_BadDTO(Breaking_Bad bb) {
        this.quote = bb.getQuote();
        this.author = bb.getAuthor();
        this.url = bb.getUrl();
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Breaking_Bad getBb() {
        return bb;
    }

    public void setBb(Breaking_Bad bb) {
        this.bb = bb;
    }

}
