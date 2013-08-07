# Geektic RESTX.io sample app


## Credits

This app is based on code developed for Devoxx Code Story 2013 edition.

Original repo: https://github.com/CodeStory/code-story-geektic

## Running

Build with `mvn package dependency:copy-dependencies`

Run with `java -Xmx512M -cp target/classes:target/dependency/* main.MainGeekticServer`

You will need a MongoDB server running on localhost.


## Features

Here is the list of features this sample demonstrate:

### No servlet

This sample uses the simple framework support of restx, and as such does not require a servlet container.

### MongoDB integration

This sample uses the restx-jongo module and demonstrates storing data in MongoDB, using [jongo](http://jongo.org) API to access mongo.
It also demonstrates the integration of mongo in spec tests, setting the collections in the given section.

### Using RESTX to serve static assets / compile coffee and less resources on the fly

Although RESTX is not designed as a web framework, this sample demonstrates how RESTX routes can be used to serve other content than JSON, in this case static content, as well as coffeescript and less resources compiled on the fly.

### FluentLenium / PhantomJS / Ghostdriver

Also not related to RESTX, this sample demonstrates how to use fluentlenium / phantomJS / ghost driver together to test the pages accessing the RESTX base REST API.
The web UI itself is implemented using AngularJS.
