------
I tried to come up with such an application so that it would be possible to
use such features of JDBC as batch requests, transactions, ddl and dml operations, also for better performance I used a connection pool in order not to open a new connection every time because this is a very resource-intensive operation, check out code.

- DATABASE DIAGRAM 



![photo](https://i.postimg.cc/HkJx2Dv6/Screenshot-2.png) 




-----
- OPPORTUNITIES




                Author Service 
  
                1. Create author
                2. Create author and book
                3. Get Author by id
                4. Get all authors  (filtering, searching and pagination support)             
                5. Get author and all their books (filtering, searching and pagination support)
----
                Book service

                1.Add book to author     
                2.Get all books (filtering, searching and pagination support    
                3.Get book
                4.Delete book              
                5.Delete all author's books
