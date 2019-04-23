POST http://localhost:8080/oauth/token

Basic Auth: oauthclient 123456  
body:  
username:123   
pasword:123  
grant_type:password  

```sql
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8
```  

mvn clean package docker:build
