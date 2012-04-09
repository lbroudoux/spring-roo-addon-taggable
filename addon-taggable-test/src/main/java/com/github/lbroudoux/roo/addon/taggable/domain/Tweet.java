package com.github.lbroudoux.roo.addon.taggable.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.github.lbroudoux.roo.addon.taggable.RooTaggable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooTaggable
public class Tweet {
   
   @NotNull
   String author;
   
   @NotNull
   @Size(max=140)
   String content;
}
