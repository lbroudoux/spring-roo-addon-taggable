
## Spring Roo Taggable Addon

This is an addon to Spring Roo that enhance domain objects so that they
can be tagged.


### Features

Enable domain objects to be marked with tags. Adding taggable behaviour to a domain
object adds the following methods :

    void addTag(String tag);
    Set<String> getTags();
    List<Domain> findAllDomainsWithTag(String tag);

See https://github.com/lbroudoux/spring-roo-addon-taggable/blob/master/addon-taggable-test/src/test/java/com/github/lbroudoux/roo/addon/taggable/domain/TweetIntegrationTest.java
for usage examples.


### Build instructions

For now, start fetching the code from Github using this Git Url : https://www.github.com/lbroudoux/spring-roo-addon-taggable.git

Then build addon using Maven with this command :

    mvn clean install

This should add the addon artifact to your local Maven repository
($MAVEN_REPO in the following).


### Install instructions

Once addon built, start a new Roo project or use an existing one.
Install addon by typing in a Roo shell :

    roo> osgi start --url file://$MAVEN_REPO/com/github/lbroudoux/roo/addon/com.github.lbroudoux.roo.addon.taggable/0.1.0.BUILD-SNAPSHOT/com.github.lbroudoux.roo.addon.taggable-0.1.0.BUILD-SNAPSHOT.jar

Check that addon is successfully installed by typing :

    roo> osgi ps

you should see a line like this one telling that addon is installed :

    ...
    [  75] [Active     ] [    1] RooTaggable (0.1.0.BUILD-SNAPSHOT)
    ...


###  Usage instructions

From your Roo shell, start invoking the setup command on taggable :

    roo> taggable setup

then add the taggable behaviour to one of your domain object. For example :

    roo> taggable add --type ~.domain.Tweet

Now see the tag related methods added to your domain class through a new ITD !
