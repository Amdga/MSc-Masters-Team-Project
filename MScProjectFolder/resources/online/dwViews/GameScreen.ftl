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

</head>

<body onload="initalize()">
	<!-- Call the initalize method when the page loads -->

	<div class="container">

		<!-- Add your HTML Here -->
		<div><button type="button" id="newGame" onclick="newGame();">New Game</button>Click this to create a new game</div>

		<div><button type="button" id="startGame" onclick="initialiseGame();">Start Game</button>Click this to start the game</div>

		<div><button type="button" id="startRound" onclick="startARound();">Start Round</button>Click this to start the next round (use in 'round ended' state)</div>

		<div><button type="button" id="speed" onclick="chosenCategory('Speed');">Speed</button>
			<button type="button" id="cargo" onclick="chosenCategory('Cargo');">Cargo</button>
			<button type="button" id="range" onclick="chosenCategory('Range');">Range</button></div>
		<div><button type="button" id="size" onclick="chosenCategory('Size');">Size</button>
			<button type="button" id="firepower" onclick="chosenCategory('Firepower');">Firepower</button>
		Click these to choose a category (use in 'choosing category' state)</div>

		<div><button type="button" id="quit" onclick="quit();">Quit</button>Click this to quit the current game</div>
		<div>
			<pre><p id="response">######</p></pre>
		</div><br>
		<div>
			<p>~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~</p>
		</div><br>
		<div>
			<p id="error">--------</p>
		</div>

	</div>

	<script type="text/javascript">
		// $('#newGame').onclick = newGame();
		// $('#startGame').onclick = initialiseGame();
		// $('#startRound').onclick = startARound();
		// $('#speed').onclick = chosenCategory("Speed");
		// $('#cargo').onclick = chosenCategory("Cargo");
		// $('#range').onclick = chosenCategory("Range");
		// $('#size').onclick = chosenCategory("Size");
		// $('#firepower').onclick = chosenCategory("Firepower");
		// $('#quit').onclick = quit();

		// Method that is called on page load
		function initalize() {

			// --------------------------------------------------------------------------
			// You can call other methods you want to run when the page first loads here
			// --------------------------------------------------------------------------

			
			// For example, lets call our sample methods
			// helloJSONList();
			// helloWord("Student");
			// showCard();

		}

		// -----------------------------------------
		// Add your other Javascript methods Here
		// -----------------------------------------

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

		function testResponse(jsonAsString) {
			//var response = JSON.parse(jsonAsString)
			if (jsonAsString == "state error") {
				$("#error").html("Function cant be used in this state");
			} else if (jsonAsString == "") {
				$("#error").html($('#error').text() + " + 1")
			} else {
				$("#response").html(jsonAsString);
				$("#error").html("--------");
			}
		}

		function newGame() {
			var xhr = createCORSReq('GET', "http://localhost:7777/toptrumps/game/newGame");
			xhr.send();

			xhr.onload = function (e) {
				testResponse(xhr.response);
			}
		}

		function initialiseGame() {
			var xhr = createCORSReq('GET', "http://localhost:7777/toptrumps/game/initialiseGameplay");
			xhr.send();
			xhr.onload = function (e) {
				testResponse(xhr.response);
			}
		}

		function startARound() {
			var xhr = createCORSReq('GET', "http://localhost:7777/toptrumps/game/startARound");
			xhr.send();
			xhr.onload = function (e) {
				testResponse(xhr.response);
			}
		}

		function chosenCategory(category) {
			var xhr = createCORSReq('GET', "http://localhost:7777/toptrumps/game/chosenCategory?category=" + category);
			xhr.send();
			xhr.onload = function (e) {
				testResponse(xhr.response);
			}
		}

		function quit() {
			var xhr = createCORSReq('GET', "http://localhost:7777/toptrumps/game/quit");
			xhr.send();
			xhr.onload = function (e) {
				testResponse(xhr.response);
			}
		}
	</script>

	<!-- Here are examples of how to call REST API Methods -->
	<script type="text/javascript">
			// function showCard() {
			// 	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/showCard");

			// 	// Message is not sent yet, but we can check that the browser supports CORS
			// 	if (!xhr) {
  			// 		alert("CORS not supported");
			// 	}

			// 	xhr.onload = function(e) {
 			// 		var responseCard = JSON.parse(xhr.response); // the text of the response

			// 		alert(responseCard.cardName + " " + responseCard.headers[1] + " ! " + responseCard.valueMap["2"]); // lets produce an alert
			// 		alert(xhr.response);
			// 	};

			// 	// We have done everything we need to prepare the CORS request, so send it
			// 	xhr.send();		
			// }

			// // This calls the helloJSONList REST method from TopTrumpsRESTAPI
			// function helloJSONList() {

			// 	// First create a CORS request, this is the message we are going to send (a get request in this case)
			// 	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloJSONList"); // Request type and URL

			// 	// Message is not sent yet, but we can check that the browser supports CORS
			// 	if (!xhr) {
  			// 		alert("CORS not supported");
			// 	}

			// 	// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
			// 	// to do when the response arrives 
			// 	xhr.onload = function(e) {
 			// 		var responseText = xhr.response; // the text of the response
			// 		alert(responseText); // lets produce an alert
			// 	};

			// 	// We have done everything we need to prepare the CORS request, so send it
			// 	xhr.send();		
			// }

			// // This calls the helloJSONList REST method from TopTrumpsRESTAPI
			// function helloWord(word) {

			// 	// First create a CORS request, this is the message we are going to send (a get request in this case)
			// 	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word="+word); // Request type and URL+parameters

			// 	// Message is not sent yet, but we can check that the browser supports CORS
			// 	if (!xhr) {
  			// 		alert("CORS not supported");
			// 	}

			// 	// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
			// 	// to do when the response arrives 
			// 	xhr.onload = function(e) {
 			// 		var responseText = xhr.response; // the text of the response
			// 		alert(responseText); // lets produce an alert
			// 	};

			// 	// We have done everything we need to prepare the CORS request, so send it
			// 	xhr.send();		
			// }

	</script>

</body>

</html>