# AsciiLab

Making it easy to version Asciidoctor in Git and convert to:

-> An intranet!
-> A PDF!


## Architecture

The basic idea is to have a Git host push things to an API, which then updates... stuff.

                    +------------------------------------------------+
                    |                                                |
     +--------+     |              +-----------------------------+   |
     | GitHub |     |              |filesystem (html, PDF builds)|   |
     +--------+     +----------+   +-----------------------------+   |
     +-----------+-->   nginx  |   +--------------+                  |
     | GitLab |  |  +----------+   |  git repos   |                  |
     +--------+  |  |              +--------------+                  |
                 |  |              +---------------------+           |
                 |  |              | API server (kotlin) |           |
                 |  |              +---------------------+           |
                 |  |              +--------------------+            |
                 |  |              | Admin App          |            |  
                 |  |              +--------------------+            |
                 |  |                                                |
    Peeps+-------+  +------------------------------------------------+

That whole bit on the right should be a deployable Docker container, as a starting point.

### API Server

- Spring Boot 5 app
- use asciidoctorj (for JVM usage) for conversion
- use jgit for git interop

NOTE: We may want options for background processes to handle some operations.

### Possible: Admin Application

We may want to configure a little web application that provides the ability to initialize and administer the server. 

TODO: Determine the database. Need some minimal functions, hopefully nothing too heavyweight. 


## Product Concepts

Two versions:

1. Open Source (single Git repo)
2. Enterprise (protected pages, auth, search, etc)

Value proposition:

The idea is that a group chooses this over, say, a stupid Wiki server like Confluence. (Wikis! Booo.)

The primary workflow is just simply to push changes to a master branch in a repo.

Repo setup:

    index.adoc <- literally your home page
    sub_dir/index.adoc <- becomes the index of a web page

Now, this may be too simple, so we can add a "descriptor" that allows you to state "hey this is for the website".


## TO DO

1. Create the open source framework
2. Validate that yep, we can spin up a docker container
3. 

