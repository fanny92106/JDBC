# PreparedStatement vs Statement 


### Relationship: 
    Statement interface is parent interface of PreparedStatement interface
    PreparedStement has completely replaced Statement





### Statement:
    1. need String concatenation, which is error-prone
    2. [fatal] SQL injection issue 
    3. cannot accept binary data, such as image
    4. low performance especially in bulk operation, because need to create large amount
    of String, and to each String DB engine conducts syntax & symantics check




### PreparedStatement:
    1. avoid String concatenation, this error-prone operation
    2. resolve SQL injection issue by pre-compiling the sql statement [lock SQL symantic]
    3. can accept binary data and specify its inputStream, such as image
    4. high performance especially in bulk operation, because the SQL statement String just
    need to be created once, pre-compiled once, DB engine's syntax & symantic check for once
    


