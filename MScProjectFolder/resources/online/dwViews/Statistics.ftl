<html>

<head>
	<!-- Web page title -->
	<title>Top Trumps</title>

	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

	<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css" />
	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css" />
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

	<style>
		body{
			background-color: #12A5F4; 
		}

		#goBackButton{
			background-color: #FF7C30;
			-webkit-transition-duration: 0.4s; /* Safari */
			transition-duration: 0.4s;
			vertical-align:bottom;
			font-size: 20px;
			font-family: arial,serif;
			font-size: 25px;
			color: white;
			cursor:pointer;
			border-radius: 8px;
			box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
			white-space: nowrap;
		}

		#goBackButton:hover{
			background-color: #F25900;
		}
		
		.statsDetails{

			background-color:#F1F1F1;
			padding: 20px;
			font-size: 20px;
			font-family: arial, serif; 
			color:black;
			padding:20px;
			margin-left: auto;
			margin-right: auto;
			border-radius: 8px;
			box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
		}

		.statstitle{
			padding-right: 40px;
		}

		table{
			font-size: 20px; 
			font-style: Arial, serif"
		}

		h1{
			text-align: center
		}

		/* div{
			border-color: blue;
			border-style: dotted;
			border-width: 3px;
		} */

		#containsStats{
			margin-right: auto;
			margin-left: auto;
		}

		#buttonDiv{
			padding-top: 10px;
			padding-left: 20px;
		}

		/* On small screens, set height to 'auto' for sidenav and grid */
		@media screen and (max-width: 767px) {
			.header{
			height: auto;
			padding: 20px;
		
		}
		.row.content {height:auto;} 
		}


	</style>


</head>


<body onload="initalize()">
	<!-- Call the initalize method when the page loads -->

	<div class="container-fluid">
		<div class="row">
			<div id="buttonDiv">
				<!-- Button to go back to original screen, code to add a link <a href= "#" class = "btn btn-default btn-lg" role="button" style="background-color:#F47D30;color:white;" >Go Back</a>-->
				<button id="goBackButton" onclick=goBack();>Go Back</button>
			</div>
		</div>
		<div class="row" style="padding: 20px">
			<div id="containsStats">
				<div class="media screen">
					<div class="statsDetails">
						<h1>Game Statistics</h1>

						<hr>

						<table>
							<tr>
								<th class="statstitle"> Total games played overall: </td>
								<td id="statline1"></td>
							</tr>
							<tr>
								<th class="statstitle"> Computer wins: </td>
								<td id="statline2"></td>
							</tr>
							<tr>
								<th class="statstitle"> Human wins: </td>
								<td id="statline3"></td>
							</tr>
							<tr>
								<th class="statstitle"> Average number of draws: </td>
								<td id="statline4"></td>
							</tr>
							<tr>
								<th class="statstitle"> Most rounds played in a single game: </td>
								<td id="statline5"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">

		// Method that is called on page load
		function initalize() {

			initialiseStats();
		}

		// This is a reusable method for creating a CORS request. Do not edit this.
		function createCORSRequest(method, url) {
			var xhr = new XMLHttpRequest();
			if ("withCredentials" in xhr) {

				// Check if the XMLHttpRequest object has a "withCredentials" property.
				// "withCredentials" only exists on XMLHTTPRequest2 objects.
				xhr.open(method, url, true);

			} else if (typeof XDomainRequest != "undefined") {

				// Otherwise, check if XDomainRequest.
				// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
				xhr = new XDomainRequest();
				xhr.open(method, url);

			} else {

				// Otherwise, CORS is not supported by the browser.
				xhr = null;

			}
			return xhr;
		}

		function createCORSReq(method, url) {
			var xhr = createCORSRequest(method, url);
			// Message is not sent yet, but we can check that the browser supports CORS
			if (!xhr) {
				alert("CORS not supported");
			}
			return xhr;
		}

		function updateStats(statsArr) {
			var stats = JSON.parse(statsArr)
			//alert(statsArr)
			$("#statline1").html(stats[0])
			$("#statline2").html(stats[1])
			$("#statline3").html(stats[2])
			$("#statline4").html(stats[3])
			$("#statline5").html(stats[4])
		}

		function initialiseStats() {
			var xhr = createCORSReq('GET', "http://localhost:7777/toptrumps/stats/initialise");
			xhr.send();

			xhr.onload = function (e) {
				updateStats(xhr.response);
			}
		}

		function goBack() {
			window.location.href = '/toptrumps';
		}

	</script>

</body>

</html>