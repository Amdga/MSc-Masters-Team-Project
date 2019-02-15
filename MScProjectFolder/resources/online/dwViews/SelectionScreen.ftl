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
		body {
			background-color: #12A5F4;
		}

		.center {
			margin: auto;
			width: 100%;
			padding: 200px;
		}


		.button {
			width: 150px;
			height: 70px;
			margin: 5px;
			border: none;
			background-color: #ff7c30;
			border-radius: 8px;
			color: white;
			padding: 15px 25px;
			text-align: center;
			font-size: 30px;
			font-family: arial, serif;
			cursor: pointer;

		}

		.button:hover {
			background-color: #f25900;
		}

		div.textHeader {
			color: #ffffff;
			font-size: 40px;
			font-family: arial, serif;
			font-weight: bold;
			text-align: center;
			text-transform: uppercase;
		}

		div.textSub {
			color: #ffffff;
			font-size: 27px;
			font-family: arial, serif;
			text-align: center;
		}

		div.textAI {
			display: none;
			color: #ffffff;
			font-size: 20px;
			font-family: arial, serif;
			text-align: left;
			white-space: nowrap;
			padding: 7px 10px;
			margin-bottom: 10px;
		}

		div.buttonAI {
			display: none;
			width: 100px;
			height: 70px;
			margin: 5px;
			border: none;
			background-color: #ff7c30;
			border-radius: 8px;
			color: white;
			padding: 15px 25px;
			text-align: center;
			font-size: 30px;
			font-family: arial, serif;
			cursor: pointer;
			/* float: left; */
		}

		div.buttonAI:hover {
			background-color: #f25900;

		}
	</style>

</head>

<body onload="initalize()">
	<!-- Call the initalize method when the page loads -->


	<body>
		<div class="center">
			<div class="container-fluid">
				<div class="row">
					<div class="col-sm-12">
						<div class="textHeader">Welcome to food is life's top trumps game</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12">
						<div class="textSub">Choose to play a game or see latest statistics below</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12">
						<div class="text-center">
							<button class="button" id="gameButton" onclick=showButtons();>Game</button>
							<button class="button" id="statsButton" onclick=goToStatPage();>Stats</button>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12">
						<div class="text-center">
							<p>
							<div class="textAI">Number of AI players:</div>
							</p>
						</div>
					</div>

					<div class="col-sm-12">
						<div class="text-center">
							<div class="buttonAI" id="AI1" onclick=chosenNumberOfPlayers(1);>1</div>
							<div class="buttonAI" id="AI2" onclick=chosenNumberOfPlayers(2);>2</div>
							<div class="buttonAI" id="AI3" onclick=chosenNumberOfPlayers(3);>3</div>
							<div class="buttonAI" id="AI4" onclick=chosenNumberOfPlayers(4);>4</div>
						</div>
					</div>
				</div>
			</div>
		</div>





	</body>

</html>


<script type="text/javascript">

	// Method that is called on page load
	function initalize() {

		// --------------------------------------------------------------------------
		// You can call other methods you want to run when the page first loads here
		// --------------------------------------------------------------------------


	}

	function showButtons() {
		$(".buttonAI").css("display", "inline");
		$(".textAI").css("display", "inline");

	}

	function goToStatPage() {
		window.location = "http://localhost:7777/toptrumps/stats"
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

	function chosenNumberOfPlayers(number) {
		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/chosenNumberOfPlayers?number=" + number);
		xhr.onload = function (e) {
			window.location = "http://localhost:7777/toptrumps/game";
		};
		xhr.send();
	}


</script>

<!-- Here are examples of how to call REST API Methods -->
<script type="text/javascript">



	// This calls the helloJSONList REST method from TopTrumpsRESTAPI
	function helloJSONList() {

		// First create a CORS request, this is the message we are going to send (a get request in this case)
		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloJSONList"); // Request type and URL

		// Message is not sent yet, but we can check that the browser supports CORS
		if (!xhr) {
			alert("CORS not supported");
		}






		// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
		// to do when the response arrives 
		xhr.onload = function (e) {
			var responseText = xhr.response; // the text of the response
			alert(responseText); // lets produce an alert
		};

		// We have done everything we need to prepare the CORS request, so send it
		xhr.send();
	}

	// This calls the helloJSONList REST method from TopTrumpsRESTAPI
	function helloWord(word) {

		// First create a CORS request, this is the message we are going to send (a get request in this case)
		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word=" + word); // Request type and URL+parameters

		// Message is not sent yet, but we can check that the browser supports CORS
		if (!xhr) {
			alert("CORS not supported");
		}

		// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
		// to do when the response arrives 
		xhr.onload = function (e) {
			var responseText = xhr.response; // the text of the response
			alert(responseText); // lets produce an alert
		};

		// We have done everything we need to prepare the CORS request, so send it
		xhr.send();
	}

</script>

</body>

</html>