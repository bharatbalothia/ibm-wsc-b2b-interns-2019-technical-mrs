<!DOCTYPE html>
<html lang="en">
<head>
<title>Log in</title>
<link rel="stylesheet" href="./css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="./css/font-awesome.min.css" type="text/css">
<style>
.container-fluid {
	margin-top: 68px;
}
</style>
</head>
<body>
	<!--Header Start-->
	<%
		if (session.getAttribute("name") != null) {
			request.getRequestDispatcher("MrsGenerator.jsp").forward(request,
					response);
		}
	%>
	<nav class="navbar navbar-default navbar-static navbar-fixed-top">
		<div class="navbar-header">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <img
				class="img-responsive" src="./images/logo.png" style="margin: 5px;">

		</div>
	</nav>


	<div class="container-fluid ">
		<div class="minHightContainer">
			<br></br> <br></br> <br></br>

			<div id="divLogin"
				class="col-xs-12 col-md-10 col-sm-10 col-md-offset-1 ">
				<div class="col-md-6 col-sm-6 col-xs-12">
					<form action="loginServlet" method="post">
						<div class="panel panel-danger">
							<div class="panel-heading">
								<h4>Tech MRS Generator</h4>


							</div>
							<%
								if (request.getAttribute("error") != null) {
									out.print(request.getAttribute("error"));
								}
							%>
							<div class="panel-body">
								<div class="error"></div>
								<div class="form-group form-horizontal">
									<div class="input-group col-sm-10 col-sm-offset-1">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-user"></i></span> <input
											class="form-control" required="required" id="UserName"
											type="email" name="UserName" placeholder="IBM Intranet Email ID"
											type="text" value="">
									</div>
								</div>
								<!--user name-->
								<div class="form-group form-horizontal">
									<div class="input-group col-sm-10 col-sm-offset-1">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-lock"></i></span><input
											class="form-control" id="Password" name="Password"
											placeholder="IBM Intranet Password" required="required" type="password">
									</div>
								</div>
								<!--password-->
								<div class="form-group ">
									<div class="col-sm-10 col-sm-offset-1 ">
										<br></br>
										<button class="btn btn-danger"
											style="background-color: #337AB7; border-color: #337AB7">Submit</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>