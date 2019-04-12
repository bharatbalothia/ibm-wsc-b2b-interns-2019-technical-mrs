<jsp:include page="header.jsp" />
<body>
	<%
		if (session.getAttribute("name") == null) {
			request.getRequestDispatcher("login.jsp").forward(request,
					response);
		}
	%>
	<div id="wrapper">
		<div id="page">
			<div id="page-bgtop">
				<div id="page-bgbtm">
					<div id="content">
						<div class="post">
							<h2 class="date">Report</h2>
							<div class="container-fluid ">
								<div class="minHightContainer">
									<br></br>

									<h3>
										<strong>${requestScope.message}</strong>
									</h3>
								</div>
								<div>
									<br />
									<br />
									<br />
									<br />
									<br />
									<p style="color: red;">${requestScope.filesNotProcessed}</p>
								</div>
							</div>
						</div>

						<div style="clear: both;">&nbsp;</div>
					</div>
					<div style="clear: both;">&nbsp;</div>
				</div>
			</div>
		</div>
		<!-- end #page -->
	</div>
	<!-- end #footer -->
</body>
</html>
