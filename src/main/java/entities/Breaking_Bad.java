/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author alexa
 */

public class Breaking_Bad implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String quote;
    private String author;
    private String url;

    public Breaking_Bad(String id, String quote, String author, String url) {
        this.id = id;
        this.quote = quote;
        this.author = author;
        this.url = url;
        
    }

    public Breaking_Bad() {
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
    

}
