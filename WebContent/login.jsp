<!DOCTYPE html>
<html lang="en">
  
<head>
    <meta charset="utf-8">
    <title>Admin Panel</title>

	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"> 
    
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />

<link href="css/font-awesome.css" rel="stylesheet">
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600" rel="stylesheet">
    
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/pages/signin.css" rel="stylesheet" type="text/css">

<style>
   body,h1,h2,h3,h4,h5 {font-family: "Poppins", sans-serif;}
   body {font-size:16px;}
   .btn-success,.btn-large {background-color:#49635c;}    
</style>
</head>

<body>
	
	<div class="navbar navbar-fixed-top">
		
	<div class="navbar-inner">	
		
		<div class="container">
			<a class="brand" href="index.html">
				Parking Management System			
			</a>		
	
		</div> <!-- /container -->
		
	</div> <!-- /navbar-inner -->
	
	</div> <!-- /navbar -->



<div class="account-container">
	
	<div class="content clearfix">
		
		<form action="LoginServlet" method="post">
		
			<h1>Admin Login</h1>		
			
			<div class="login-fields">
				
				<p>Please provide your details</p>
				
				<div class="field">
					<label for="username">Username</label>
					<input type="text" id="username" name="username" value="" placeholder="Username" class="login username-field" />
				</div> <!-- /field -->
				
				<div class="field">
					<label for="password">Password:</label>
					<input type="password" id="password" name="password" value="" placeholder="Password" class="login password-field"/>
				</div> <!-- /password -->
				
			</div> <!-- /login-fields -->
			
			<div class="login-actions">
				
				<span class="login-checkbox">
					<input id="Field" name="Field" type="checkbox" class="field login-checkbox" value="First Choice" tabindex="4" />
					<label class="choice" for="Field">Remember Me</label>
				</span>
									
			<button class="button btn btn-success btn-large">Sign In</button>
				<br/>
				<br/>
       		Message: ${message}
       		<br>
       		<c:remove var="message" scope="session" />
			</div> <!-- .actions -->
						
		</form>
		
	</div> <!-- /content -->
	
</div> <!-- /account-container -->

<script src="js/jquery-1.7.2.min.js"></script>
<script src="js/bootstrap.js"></script>

<script src="js/signin.js"></script>

</body>

</html>
