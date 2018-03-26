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
     | GitLab |  |  +----------+   |  git repos   |                  | +------------+
     +--------+  |  |              +--------------+                  | |            |
                 |  |              +---------------------+           | | PostgreSQL |
                 |  |              | API server (kotlin) |           | |            |
                 |  |              +---------------------+           | +------------+
                 |  |              +--------------------+            |
                 |  |              | Admin App          |            |  
                 |  |              +--------------------+            |
                 |  |                                                |
    Peeps+-------+  +------------------------------------------------+

That whole bit on the right should be a deployable Docker container, with the option for another one for PostgreSQL.

### API Server

- Spring Boot 5 app
- use asciidoctorj (for JVM usage) for conversion
- use jgit for git interop

NOTE: We may want options for background processes to handle some operations.

### Possible: Admin Application

We may want to configure a little web application that provides the ability to initialize and administer the server. 


### Decisions, Decisions

#### Replaced Spring Webflux with ktor

Spring 5 introduced Webflux, which piqued my interest, having had some experience with Spring in the past.
The practical matter, however, is that reactive APIs are simply much more challenging then coroutines.
I found myself regularly getting surprised by behaviors, and then realizing I didn't have the mental model quite right in my head.
In the end, this can only create more serious bugs and problems over time.

The conversion took a few hours, and there's a couple of weird quirks with JSON rendering.
Overall, the code is _significantly_ cleaner for simple usage.


## Product Concepts

ASCIILab enables formal publication workflows for articles and reports of internal review.
By treating content like source code, tooling like branching and review, etc, is made easy.

This is not just another static website generation system, but a way to wean a large group from thinking a Wiki is all they need.
When someone wants to float a concept, like a new architecture, any reports and presentations should look professional.
They should also adhere to a company style and identity.
Reviews should annotate the pure content, and not get lost in style details.

Output of publications should include:

- Technical documentation website
- Blogs
- PDF reports
- Presentations

[Asciidoc](https://asciidoc.org) is the basis for how content will be generated.

NOTE: The name "asciilab" is mostly just a placeholder.

Two versions:

1. Community open source (pretty much git repo -> stuff and that's it.)
2. Enterprise (RBAC, etc)


## Design Ideas

An "adminstration" application should guide people on how to create a publication.

For a blog, they should basically indicate how articles are structured.


## TO DO

Create minimal "add source" page.

Create a mechanism for cloning a repo based on the source status.

Generate simple HTML files from the underlying repo.

Generate the nginx configuration for each new repo.

Allow underlying repo to be updated via a webhook in Github or GitLab.

Figure out how to configure styling (expose "templates" repos?)

Consider an integrated smoke testing suite.

