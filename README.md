# N11-Assignment-2

It is an application that allows to add books (name,author,isbn and price) and listing them.
Book list will be cached in 10 minutes(CacheConfig.java) and cache will evict if user add a new book.
Data will be validated backend simply(type and nullity)
Adding form is protected against csrf attack via simple session token
Also has some unit test cases those check database i/o , validation and csrf validty
