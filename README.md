# spring-hateoas-demo
Demo Spring HATEOAS basic

Examples:

   * Get all posts
   
   ```
   curl http://localhost:8080/posts | json_pp
   ```
     {
           "_links" : {
              "self" : {
                 "href" : "http://localhost:8080/posts/"
              }
           },
           "_embedded" : {
              "postList" : [
                 {
                    "id" : 1,
                    "content" : "This is content of post 1",
                    "_links" : {
                       "self" : {
                          "href" : "http://localhost:8080/posts/1"
                       }
                    },
                    "title" : "Post 1"
                 },
                 {
                    "title" : "Post 2",
                    "_links" : {
                       "self" : {
                          "href" : "http://localhost:8080/posts/2"
                       }
                    },
                    "content" : "This is content of post 2",
                    "id" : 2
                 },
                 {
                    "id" : 3,
                    "content" : "This is content of post 3",
                    "_links" : {
                       "self" : {
                          "href" : "http://localhost:8080/posts/3"
                       }
                    },
                    "title" : "Post 3"
                 }
              ]
           }
        }

   * Get a post
   
   ```
   curl http://localhost:8080/posts/1 | json_pp
   ```
        {
           "content" : "This is content of post 1",
           "title" : "Post 1",
           "_links" : {
              "self" : {
                 "href" : "http://localhost:8080/posts/1"
              },
              "comments" : {
                 "href" : "http://localhost:8080/posts/1/comments"
              }
           },
           "id" : 1
        }
        
   * Get comments of a post
    
   ```
   curl http://localhost:8080/posts/1/comments | json_pp
   ```
        {
           "_embedded" : {
              "commentList" : [
                 {
                    "id" : 1,
                    "content" : "Comment 1",
                    "_links" : {
                       "self" : {
                          "href" : "http://localhost:8080/comments/1"
                       }
                    }
                 },
                 {
                    "_links" : {
                       "self" : {
                          "href" : "http://localhost:8080/comments/2"
                       }
                    },
                    "content" : "Comment 2",
                    "id" : 2
                 },
                 {
                    "content" : "Comment 3",
                    "_links" : {
                       "self" : {
                          "href" : "http://localhost:8080/comments/3"
                       }
                    },
                    "id" : 3
                 }
              ]
           },
           "_links" : {
              "posts" : {
                 "href" : "http://localhost:8080/posts/1"
              },
              "self" : {
                 "href" : "http://localhost:8080/posts/1/comments"
              }
           }
        }
        
