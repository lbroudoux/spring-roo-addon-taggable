// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.github.lbroudoux.roo.addon.taggable.domain;

import com.github.lbroudoux.roo.addon.taggable.domain.Tweet;

privileged aspect Tweet_Roo_JavaBean {
    
    public String Tweet.getAuthor() {
        return this.author;
    }
    
    public void Tweet.setAuthor(String author) {
        this.author = author;
    }
    
    public String Tweet.getContent() {
        return this.content;
    }
    
    public void Tweet.setContent(String content) {
        this.content = content;
    }
    
}
