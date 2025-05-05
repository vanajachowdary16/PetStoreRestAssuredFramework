package api.endpoints;


/*https://petstore.swagger.io/
create user : https://petstore.swagger.io/#/user/createUser

get user : https://petstore.swagger.io/#/user/getUserByName

update user :https://petstore.swagger.io/#/user/updateUser

delete user : https://petstore.swagger.io/#/user/deleteUser
 
 */

public class Routes {
	
	public static String base_url ="https://petstore.swagger.io/v2";
	
	//user module
	
	public static String post_url = base_url+"/user";
	public static String get_url=base_url+"/user/{username}";
	public static String update_url=base_url+"/user/{username}";
	public static String delete_url=base_url+"/user/{username}";
	
	//store module
	   // here you will create store module URL's
	
	//pet module
	
	   // here you will create pet module URL's
	
	

}
