<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>

	<div>
		<label for="UsageType"><strong>Select the usage type</strong></label><strong
			style="color: red;">*</strong> <input type="radio" name="usageType"
			value="parature"> <label for="parature">Map Change</label> <input
			type="radio" name="usageType" value="development"> <label
			for="development">Development</label> <br /> <br />
	</div>
	<div id="paratureTag">
		<label><strong>Enter the last 8-digit ticket Number</strong> </label><strong
			style="color: red;">*</strong>&nbsp; <input type="text"
			name="ticketNumber" id="ticketNumber" required="required"
			placeholder="Ticket number" maxlength="8"> <br /> <br />
	</div>
	<div id="developmentTag">
		<label><strong>Select the Project Name</strong></label>
		<!--  select
			name="select" id="selectProjectVal">
			<option value="Toshiba">Toshiba</option>
			<option value="CEVA V2">CEVA V2</option>
			<option value="CEVA V5.2">CEVA V5.2</option>
			<option value="Merk Millipore">Merk Millipore</option>
			<option value="Justin Brands">Justin Brands</option>
			<option value="CVG">CVG</option>
			<option value="PDC">PDC</option>
			<option value="Petco">Petco</option>
			<option value="BBU Phase 1">BBU Phase 1</option>
			<option value="Saint Gobain">Saint Gobain</option>
			<option value="Kronoply">Kronoply</option>
			<option value="Allied Mills">Allied Mills</option>
		</select -->
		<select name="select" id="selectProjectVal">
			<c:forEach var="projectName" items="${projectNames}">
				<option value="${projectName}">${projectName}</option>
			</c:forEach>
		</select>
	</div>
	<br /> <br />
	<div>
		<br /> <strong>Do you want to exclude the field definitions?
		</strong>" (data type, format, min, max, etc.)"<br /> <input type="radio"
			name="Fdef" value="Yes"> Yes</input> <input type="radio" name="Fdef"
			value="No" checked="checked"> No</input>

	</div>
	<div>
		<br /> <strong>Do you want to include inactive fields? </strong><br />
		<input type="radio" name="InactiveFld" value="Yes"> Yes</input> <input
			type="radio" name="InactiveFld" value="No" checked="checked">
		No</input>

	</div>

</div>




<script>
	$(document)
			.ready(
					function() {
						$("#developmentTag").hide();
						$("#paratureTag").hide();
						$("form")
								.submit(
										function() {
											var extension;
											console.log("form submit clicked")
											if ($("input:checked").val() == null) {
												alert("Please select the usage type");
												return false;
											}
											if ($("input:checked").val() == "parature") {
												if ($("#ticketNumber").val() == null
														|| $("#ticketNumber")
																.val() == "") {
													alert("Please enter the ticket number");
													return false;
												}

												var ticketNumber = $(
														"#ticketNumber").val();
												var intRegex = /^[0-9]+$/;
												if (!(ticketNumber
														.match(intRegex))) {
													alert("Please enter a valid ticket number. Ticket number does not contain alphabets");
													return false;
												}
												if (!(ticketNumber.length == 8)) {
													alert("Please enter a valid ticket number. Ticket value contains 8 numeric characters.");
													return false;
												}

											}
											if ($("#file").val() == null
													|| $("#file").val() == "") {
												alert("Please select the file");

												return false;
											} else {
												var fileName = $("#file").val();
												extension = fileName
														.substring(fileName.length - 4);
												/*if (extension != ".zip") {
													alert("Only zip file can be uploaded");
													return false;
												}*/

											}

											var reportType = $("#reportType")
													.val();
											var urlVal = "UploadServlet?reportName="
													+ reportType
													+ "&usageType=";

											var selectedValue = $(
													"#ticketNumber").val();
											if (selectedValue == null
													|| selectedValue == "") {
												selectedValue = $(
														"#selectProjectVal")
														.val();
											}
											var usageType = $("input:checked")
													.val();
											var Fdef = $(
													'input:radio[name=Fdef]:checked')
													.val();
											var InactiveFld = $(
													'input:radio[name=InactiveFld]:checked')
													.val();
											$("form").attr(
													"action",
													urlVal + usageType
															+ "&selectedValue="
															+ selectedValue
															+ "&Fdef=" + Fdef
															+ "&InactiveFld="
															+ InactiveFld
															+ "&ext="
															+ extension)});
						$("input").on("click", function() {
							if ($("input:checked").val() == "development") {
								console.log("radio clicked clicked")
								$("#paratureTag").hide();
								$("#developmentTag").show();

							}

							if ($("input:checked").val() == "parature") {
								$("#developmentTag").hide();
								$("#paratureTag").show();
							}
						})
					}).change();
</script>