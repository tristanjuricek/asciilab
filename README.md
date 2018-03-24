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

#### Using Kotlin-based views for admin

In some ways this is an experiment, trying to limit the number of tools and languages in use down to: Just Use Kotlin.
The View templates are really just functions that write directly to a writer.
Thus, there is no "load script and cache it" kind of thing going on.
And, it's awfully nice to have a real language for doing views: we can add complex configuraitons, etc, with pretty minimal pain.

The disadvantage is that we're really rolling new territory in terms of output writing performance.
I have no idea if this is going to be a problem yet at all.
So I suspect as things grow we'll need to figure out where the bottlenecks are and adjust.

TODO: Blog this decision - benefits vs problems

#### Using blocking JDBC

So, while all the APIs are going to be mostly reactive, it turns out that, well, Java hasn't really standardized on async APIs for JDBC.
Some projects, like [postgres-async-driver](https://github.com/alaisi/postgres-async-driver) are blazing trails in this area.

The problem, is that I only see basic functionality, and no roadmap.
So interesting features, like the Copy API are unavailable.
I'm reticent to jump into a brand new driver since I've used the copy API to completely shift performance of PG interop.
And when I search the mailing lists for postgres, I really don't see a lot of chatter for async tooling.

So... yeah, going to be slightly "custom" `ReactiveRepository` style interfaces that basically wrap an underlying `Repository`.
This allows us to stick with our "handler" basically using the `BodyInserter` style API.
Our reactive implementation will just probably have to manage it's own coroutine context and proxy.

#### NOT Using Spring-Data

I'm continuing an experiment where I'm avoiding all AOP, with all the scanning, etc and automatic proxies etc.
This means that a lot of Spring projects, like spring data, end up getting skipped.
We DO want to maintain some consistency, however that will be another project.

TODO: Blog this decision - benefits vs problems

#### Spring Boot's @ConfigProperties Doesn't Allow Idiomatic Usage

See: https://github.com/spring-projects/spring-boot/issues/8762

Personally, given that I'm _also_ not using AOP, this makes `@ConfigurationProperties` kind of a moot point.


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

Add a source administration application page.
This should just allow very minimal CRUD operations over a list of source repos.

Create a mechanism for cloning a repo based on the source status.

Generate simple HTML files from the underlying repo.

Generate the nginx configuration for each new repo.

Allow underlying repo to be updated via a webhook in Github or GitLab.

Figure out how to configure styling (expose "templates" repos?)

Consider an integrated smoke testing suite.

