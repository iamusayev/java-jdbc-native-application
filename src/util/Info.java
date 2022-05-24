package util;

public class Info {

    private Info(){}
    
    private static final Info INSTANCE = new Info();
    
    public void mainInfo (){
        System.out.println("""
                                
                1. Authors
                2. Books
                                      
                """);
    }

    public void authorsInfo(){
        System.out.println("""
                
                1. Create author
                2. Create author and book
                3. Get Author by id
                4. Get all authors  (filtering, searching and pagination support)             
                5. Get author and all their books (filtering, searching and pagination support)
                6. Back to main menu
                
                """);
    }

    public void booksInfo(){
        System.out.println("""
                                
                1.Add book to author     
                2.Get all books (filtering, searching and pagination support    
                3.Get book
                4.Delete book              
                5.Delete all author's books
                6. Back to main menu
                                
                                
                """);
    }

            public static Info getInstance() {
                return INSTANCE;
            }
}
