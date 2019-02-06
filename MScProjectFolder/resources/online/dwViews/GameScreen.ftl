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
		background-color: #12A5F4; }
	
		div.gallery {
		  margin: 5px;
		  border: 1px solid #ccc;
		  float: left;
		  width: 180px;
		  height: 250px;
		  background-color: #F1F1F1;
		  border-radius: 8px;
		  border-color: black;
		  box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
		}
	
		div.gallery:hover {
		  border: 1px solid #777;
		  box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
		}
	
	div.gallery img {
		padding: 5px;
	  width: 100%;
	  height: auto;
	  border-color: black;
	}
	
	div.desc {
	  padding: 15px;
	  text-align: center;
	  font-family: arial,serif;
	  font-size: 15px
		
	}
	
	.goBackButton{
			margin: 5px;
			background-color: #FF7C30;
			-webkit-transition-duration: 0.4s; /* Safari */
			transition-duration: 0.4s;
			font-size: 20px;
			font-family: arial,serif;
			font-size: 20px;
			color: white;
			cursor:pointer;
			width: 100%;
			border-radius: 8px;
			box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
		}
	
		.goBackButton:hover{
			background-color: #F25900;
			font-color:white;
			box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
		}
	
		.scoresBox{
			background-color:#F1F1F1;
			font-size: 15px;
			font-family: arial, serif; 
			font-color:black;
			   padding:20px;
			   margin: 5px;
			margin-right: 20px;
			   border-radius: 8px;
			border-color: black;
			border-width: 2px;
			   width:100%;
			  border-color: black;
			   box-shadow: 0 8px 16px 0 rgba(0,0,0,0.24), 0 6px 20px 0 rgba(0,0,0,0.19);
	
	</style>

	<style>
		div.rectangleBackGround {
	  height: 250px;
	  width: 200px;
	  border-radius: 8px;
	  background-color: #ffffff;
	  position: fixed;
	  width: 98%;
	  left: 1%;
	  bottom: 10px;
	}
	
	div.squareMainCard {
	  
	  
	  height: 350px;
	  width: 250px;
	  border-radius: 8px;
	  border-style: solid;
	  border-width: 1px;
	  margin-left:-150px;
	  position:fixed;
	  background:#f1f1f1;
	  bottom:30px;
	  left:50%;
	  top: 50%;
	
	}
	
	button {
	  background-color: #ff7c30; 
	  color: white;
	  font-style: arial,serif;
	  width: 100%
	  cursor: pointer; 
	  border-radius: 8px;
	  margin: 4px;
	  text-align: center;
	
	}
	
	div.btn-group button {
	  width: 230px; 
	  height: 35px;
	  bottom: 0px;
	  top: 40%;
	  left: 10px;
	  margin:4px;
	  border-radius: 8px;
	  display: block;
	
	}
	
	.btn-group button:not(:last-child) {
	  border-bottom: none; 
	}
	
	.btn-group button:hover {
	  background-color: #f25900;
	}
	
	div.textSquare {
	  text-align: left;
	  top: 50px;
	  padding-left: 10%;
	  font: arial, serif;
	}
	
	.cardPile {
	  background: #f1f1f1;
	  height: 250px;
	  width: 150px;
	  font-style: arial, serif;
	  font-size: 15px;
	  text-align: left;
	  border-radius: 8px;
	  border-width: 1px;
	  border-style: solid;
	  position: fixed;
	  bottom:200px;
	  right: 75px;
	  border-color: black;
	  box-shadow:
		0 -1px 2px rgba(0,0,0,0.15),
		0 -10px 0 -5px #eee,
		0 -10px 2px -4px rgba(0,0,0,0.15),
		0 -20px 0 -10px #eee,
		0 -20px 2px -9px rgba(0,0,0,0.15);
		padding: 30px;
	
	
	}
	
	</style>

</head>

<body onload="initalize()">
	<!-- Call the initalize method when the page loads -->

	<div class="container">
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-1" style="width:10%">
					<button class="goBackButton">Go Back</button>
				</div>
				<div class="col-sm-9" style="width:70%">
					<div class="gallery">
						<a target="_blank" href="../CardImages/Avenger.jpg">
							<img src="../CardImages/Avenger.jpg" alt="Player 1" style="width:177px;height:110px;">
						</a>
						<div class="desc">Add a description of the image here P1</div>
					</div>

					<div class="gallery">
						<a target="_blank" href="../CardImages/Carrack.jpg">
							<img src="../CardImages/Carrack.jpg" alt="Player 2" style="width:177px;height:110px;">
						</a>
						<div class="desc">Add a description of the image here</div>
					</div>

					<div class="gallery">
						<a target="_blank" href="../CardImages/Constellation.jpg">
							<img src="../CardImages/Constellation.jpg" alt="Player 3" style="width:177px;height:110px;">
						</a>
						<div class="desc">Add a description of the image here</div>
					</div>

					<div class="gallery">
						<a target="_blank" href="../CardImages/350r.jpg">
							<img src="../CardImages/350r.jpg" alt="Player 4" style="width:177px;height:110px;">
						</a>
						<div class="desc">Add a description of the image here</div>
					</div>
				</div>
				<div class="col-sm-2" style="width:20%">
					<div class="scoresBox">
						<p> Your score: <br>
							Round: <br>
							Cards left: <br>
						</p>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="rectangleBackGround">
						<div class="textSquare">
							<p>
								<h3><b>Turn:</b> Player 1<br>
									<b>Winner:</b> You<br>
							</p>
							</h3>
						</div>
					</div>

					<div class="squareMainCard">
						<div class="col-sm-9">

							<div class="squareMainCard" align="bottom">
								<img src="../CardImages/350r.jpg" alt="Player 0" style="width:247px;height:150px;padding:5px">

								<div class="btn-group">
									<ul style="padding-left: 4px">
										<button>Category 1 </button>

										<button>Category 2</button>

										<button>Category 3</button>

										<button>Category 4</button>

										<button>Category 5</button>
									</ul>

								</div>
								<div class="col-sm-3">
									<div class="cardPile">
										<p>
											<h5>
												<center><b>Communal Pile</b></center>
											</h5> <br> No.of Cards:
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Add your HTML Here
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
		<div><button type="button" id="ff" onclick="fastforward(false);">Fast Forward</button>Fast forward till time to choose category or a player is out, or end of game</div>
		<div><button type="button" id="ff" onclick="fastforward(true);">Super FF</button>Fast forward till time to choose category or end of game</div>

		</div>-->

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

		function fastforward(skipLosingPlayers) {
			var xhr = createCORSReq('GET', "http://localhost:7777/toptrumps/game/startARound");
			xhr.send();
			xhr.onload = function (e) {
				var result = JSON.parse(xhr.response);
				if (result.state == "round ended" && (result.losing_players == null || skipLosingPlayers)) {
					fastforward(skipLosingPlayers);
				} else {
					testResponse(xhr.response);
				}
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