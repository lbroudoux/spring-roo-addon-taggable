// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.github.lbroudoux.roo.addon.taggable.domain;

import com.github.lbroudoux.roo.addon.taggable.domain.Tweet;
import com.github.lbroudoux.roo.addon.taggable.domain.TweetDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect TweetDataOnDemand_Roo_DataOnDemand {
    
    declare @type: TweetDataOnDemand: @Component;
    
    private Random TweetDataOnDemand.rnd = new SecureRandom();
    
    private List<Tweet> TweetDataOnDemand.data;
    
    public Tweet TweetDataOnDemand.getNewTransientTweet(int index) {
        Tweet obj = new Tweet();
        setAuthor(obj, index);
        setContent(obj, index);
        return obj;
    }
    
    public void TweetDataOnDemand.setAuthor(Tweet obj, int index) {
        String author = "author_" + index;
        obj.setAuthor(author);
    }
    
    public void TweetDataOnDemand.setContent(Tweet obj, int index) {
        String content = "content_" + index;
        if (content.length() > 140) {
            content = content.substring(0, 140);
        }
        obj.setContent(content);
    }
    
    public Tweet TweetDataOnDemand.getSpecificTweet(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Tweet obj = data.get(index);
        Long id = obj.getId();
        return Tweet.findTweet(id);
    }
    
    public Tweet TweetDataOnDemand.getRandomTweet() {
        init();
        Tweet obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Tweet.findTweet(id);
    }
    
    public boolean TweetDataOnDemand.modifyTweet(Tweet obj) {
        return false;
    }
    
    public void TweetDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Tweet.findTweetEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Tweet' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Tweet>();
        for (int i = 0; i < 10; i++) {
            Tweet obj = getNewTransientTweet(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
