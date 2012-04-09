package com.github.lbroudoux.roo.addon.taggable.domain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = Tweet.class)
public class TweetIntegrationTest {

   @Autowired
   private TweetDataOnDemand dod;
   
   @Test
   public void testTaggableAddOn() {
      Tweet obj1 = dod.getSpecificTweet(1);
      obj1.addTag("tag_1");
      obj1.merge();
      
      Tweet obj2 = dod.getSpecificTweet(2);
      obj2.addTag("tag_2");
      obj2.merge();
      
      Tweet obj3 = dod.getSpecificTweet(3);
      obj3.addTag("tag_2");
      obj3.merge();
      
      List<Tweet> result = Tweet.findAllTweetsWithTag("tag_1");
      Assert.assertEquals(1, result.size());
      
      result = Tweet.findAllTweetsWithTag("tag_2");
      Assert.assertEquals(2, result.size());
      
      obj3.getTags().clear();
      obj3.merge();
      
      result = Tweet.findAllTweetsWithTag("tag_2");
      Assert.assertEquals(1, result.size());
   }
}
