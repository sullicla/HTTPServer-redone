# HTTPServer-redone

### A redone copy of my HTTP Server assignment.

For this assignment, you will be building your own HTTP server that implements HTTP/1.1 (RFC2616)!

Creating an HTTP Server
-----------------------

For this assignment, you will be building your own HTTP server that implements [HTTP/1.1 (RFC2616)](https://www.rfc-editor.org/rfc/rfc2616)!

You don't have to read the whole RFC, but you do need to understand the following:

-   HTTP Message Structure
-   HTTP Request-Line and Status-Line
-   HTTP Methods
-   HTTP Headers (Content-Type and Content-Length)
-   MIME Types
-   Status Codes

Create a Github repo named `HTTPServer` and upload your HTTP Server source code.

### Rubric (10 pts)

* * * * *

#### [GET](https://www.rfc-editor.org/rfc/rfc2616#page-53) (2 pts): retrieve static file resource requested

-   Make sure Content-Type is correct.
-   Make sure Content-Length is correct.
-   Your application should support at least two MIME types.

**Example**

1\. If /*example.txt* exists, your server responds back with 200 OK, the correct response headers, and the content of that example.txt file in the response body.

2\. If /*example.txt* doesn't exist, your server responds back with an error code.

* * * * *

#### [POST](https://www.rfc-editor.org/rfc/rfc2616#page-54 "Link")(2 pts): append the body of the request to the resource

-   text/plain types only

* * * * *

#### [PUT](https://www.rfc-editor.org/rfc/rfc2616#page-55 "Link")(2 pts): puts the body of the request to the resource

-   Creates a new file using the body of the request.
-   Overrides the content if the file already exists.

* * * * *

#### [DELETE](https://www.rfc-editor.org/rfc/rfc2616#page-56) (2 pts): delete the resource

* * * * *

#### Support the right [response codes](https://www.rfc-editor.org/rfc/rfc2616#section-10) (2 pts)

-   Any response code that fits; use your best judgement

### Extra Credit (4 pts)

#### [OPTIONS](https://www.rfc-editor.org/rfc/rfc2616#section-9.2) (1 pt): for a given URL, indicate what verbs can be used

-   GET (always), POST (if the file is a text file), and so on

* * * * *

#### [HEAD](https://www.rfc-editor.org/rfc/rfc2616#page-54) (1 pt): just return the headers for a given URL (no body)

* * * * *

#### [HTTP Cats](https://http.cat/)(2 pts): images as the body of an HTML page sent back with an error code

-   This is actually quite common for many web servers.
-   Only 400s, 500s would need error pages like this.
-   The server responds back with HTML containing the correct HTTP Cat image for 400s and 500s status codes.

Submission
----------

Submit your Github repository link
