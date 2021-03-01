# Traverse

Traverse is a Java tool for crawling a given website, following all the links it finds to that same domain and then displaying a rudimentary sitemap. The sitemap is a list of all the pages it found for that domain with a list of all the links that page contains, including links to other domains that were not followed.

## Usage

Traverse requires Java 11 or later to run and requires a URL parameter (e.g. https://wiprodigital.com) for the site to crawl. There is an executable jar file (compiled with Java 11.0.10) in the root of the project that can be run from the command line:
```bash
java -jar traverse.jar {URL}
```
This will run in "verbose" mode, listing all pages and all of their links. If there's a lot of output you can run it in "summary" mode which will just list each page found with the total number of links on each page. This is done by supplying an optional second parameter of "s":
```bash
java -jar traverse.jar {URL} s
```
The URL parameter supplied must be in the format of a valid HTTP URL, a `java.net.MalformedURLException` will be thrown if it is invalid.

You can also build the project yourself and package up your own executable jar by running
```bash
mvn clean package
```
from the project root. This will also run all the tests before creating the jar as `/target/Traverse-1.0-SNAPSHOT-shaded.jar`.
If you want to run the project from your IDE the entry class is `com.wiprodigital.buildit.traverse.Traverse`.

## Notes
### Output
The output is unordered and pretty rudimentary, just a summary of all the pages found with the total number of links found on them, followed by a list of each page with all the individual links listed below. What formatting there is, is just some basic alignment embedded in the app code and printed to std out.

This could be dramatically improved in many ways, some ideas are 
- using a template framework such as thymeleaf or velocity
- writing output as HTML files to disk and just outputting `file://...` links to std out
- repackaging the app from a CLI to a web app the user could run locally, and serve the output directly to the user's browser, allowing them to drill down/up etc.
- The data could be displayed as a more sophisticated sitemap with a representation of the links between pages for instance like a directed graph with cycles
- The individual links found could be classified to show links to other domains that weren't followed, static assets such as images, pdfs etc. and possibly other metadata such as total download size, time to load etc. 

### "Same Domain" test
Traverse won't follow links to domains other than its initial root URL parameter. This "same domain" test however is pretty basic and could be improved. It currently just tests if a link starts with the exact same string as the root URL parameter. This means that for example any links starting with `https://www.facebook.com` will be considered to be in a different domain to `https://facebook.com`.

### Exceptions following links
There are many reasons following a link will cause an exception, from link rot causing a 404 to timeouts or even just malformed URLs. Traverse will save the exception message as the sole "link" to that page for the output and then move on to process the next link as per normal. The main reason for this is so that one bad link will not cause the whole crawl to crash, it also means any patterns in bad links might be noted when viewing the complete output.

### Logging
All output is written to std out. As well as the possible improvements noted earlier in the Output section, the application could have a more robust logging system implemented, writing to logs on disk at various levels.

### URL Fragments and query params
URLs selecting a particular location in a page (i.e. those that end with `...#some-page-section/`) and those with query parameters are honoured as distinct URLs even though they point to the same page. How correct or useful this is depends a lot on your use case: you could argue that they're linking to the same page so should be considered identical, or that despite the destination page being ultimately the same they're different links and should be represented as such in the final site map. So I've just not bothered trying to mess with them for now.

### Asset files
If an asset URL passes the "same domain" test then Traverse will attempt to follow it (e.g. `https://facebook.com/profile.jpg`), whilst this doesn't cause an error it's not efficient or elegant. Wider problems occur trying to follow assets served from Wordpress or other CMSs that use convoluted naming conventions that require some tricky URL encoding, these will result in 404s.

### Progress
Whilst Traverse is working it prints out dots to std out as it progresses, just so there's some sign of life if it's crawling a domain with a lot of links. This could potentially be more informative with an estimation of pages crawled, known pages left, time taken to process each page etc.