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
		/* div {
			border-style: dotted;
			border-color: green;
		} */
		div.gallery {
			display: none;
			margin: 5px;
			border: 1px solid #ccc;
			float: left;
			width: 180px;
			height: 250px;
			background-color: #F1F1F1;
			border-radius: 8px;
			border-color: black;
			box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
		}
		.label {
			font-style: arial, serif;
			font-size: 15px;
			width: 100%;
			border-radius: 8px;
			margin: 4px;
		}
		div.gallery:hover {
			border: 1px solid #777;
			box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
		}
		div.gallery img {
			padding: 5px;
			width: 100%;
			height: auto;
			border-color: black;
			border-radius: 10px;
		}
		
		.squareMainCard img {
			border-radius: 15px;
		}
		div.desc {
			text-align: center;
			font-family: arial, serif;
			font-size: 15px
		}
		.goBackButton {
			max-width: 65px;
			min-width: 65px;
			margin: 5px;
			background-color: #FF7C30;
			-webkit-transition-duration: 0.4s;
			/* Safari */
			transition-duration: 0.4s;
			font-size: 20px;
			font-family: arial, serif;
			font-size: 20px;
			color: white;
			cursor: pointer;
			width: 100%;
			border-radius: 8px;
			box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
		}
		.goBackButton:hover {
			background-color: #F25900;
			font-color: white;
			box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
		}
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
		.modalButton {
			margin: 5px;
			
			background-color: #FF7C30;
			-webkit-transition-duration: 0.4s;
			/* Safari */
			transition-duration: 0.4s;
			font-size: 20px;
			font-family: arial, serif;
			font-size: 20px;
			font-color: white;
			cursor: pointer;
			width: 20%;
			padding: 20px;
			border-radius: 8px;
			box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
		}
		.modalButton:hover {
			background-color: #F25900;
			font-color: white;
			box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
		}
		.modal {
			position: fixed;
			/* Stay in place */
			z-index: 1;
			/* Sit on top */
			width: 100%;
			/* Full width */
			height: 100%;
			/* Full height */
			overflow: auto;
			/* Enable scroll if needed */
			background-color: rgb(0, 0, 0);
			/* Fallback color */
			background-color: rgba(0, 0, 0, 0.4);
			/* Black w/ opacity */
			border-radius: 8px;
			text-align: center;
		}
		div.buttonAI{
			display:none;
			width: 100px;
			height: 70px;
			/* margin: 5px; */
			border: none;
			background-color: #ff7c30;
			border-radius: 8px;
			color: white;
			padding: 15px 25px;
			text-align: center;
			font-size: 30px;
			font-family: arial,serif;
			cursor: pointer;
			/* float: left; */
			
			
			margin: 5px;
			background-color: #FF7C30;
			-webkit-transition-duration: 0.4s;
			/* Safari */
			transition-duration: 0.4s;
			/* font-size: 20px; */
			/* font-family: arial, serif; */
			/* font-size: 20px; */
			/* font-color: white; */
			cursor: pointer;
			/* width: 15%; */
			/* padding: 20px; */
			/* border-radius: 8px; */
			box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
		}
		
		div.textAI {
			display:none;
			color: black;
			font-size: 15px;
			font-family: arial, serif;
			text-align: center;
			white-space: nowrap;
			padding: 7px 10px;
			margin-bottom: 10px;
		}
		
		div.buttonAI:hover{
			background-color: #F25900;
			font-color: white;
			box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
		}
		/* Modal Content/Box */
		.modal-content {
			background-color: #F1F1F1;
			margin: 15% auto;
			padding: 30px;
			padding-top: 40px;
			border: 1px solid #888;
			width: 60%;
			height: 60%;
			text-align: center;
			font-size: 20px;
		}
		div.squareMainCard {
			height: 370px;
			width: 250px;
			border-radius: 8px;
			border-style: solid;
			border-width: 1px;
			margin-left: -150px;
			position: fixed;
			background: #f1f1f1;
			bottom: 25px;
			left: 50%;
		}
		div.btn-group button {
			width: 230px;
			height: 35px;
			padding-top: 2%;
			left: 10px;
			margin: 4px;
			border-radius: 8px;
			display: block;
			cursor: pointer;
			text-align: center;
			background-color: #FF7C30;
			color: white;
			font-style: arial, serif;
		}
		/* .btn-group button:not(:last-child) {
			border-bottom: none;
		} */
		div.btn-group button:hover {
			background-color: #f25900 !important;
		}
		div.textSquare {
			text-align: left;
			padding-top: 2%;
			padding-left: 2%;
			font: arial, serif;
			font-size: 25px;
		}
		.cardPile {
			background: #f1f1f1;
			height: 200px;
			width: 150px;
			font-style: arial, serif;
			font-size: 15px;
			text-align: center;
			border-radius: 8px;
			border-width: 1px;
			border-style: solid;
			position: fixed;
			bottom: 200px;
			right: 75px;
			border-color: black;
			box-shadow:
				0 -1px 2px rgba(0, 0, 0, 0.15),
				0 -10px 0 -5px #eee,
				0 -10px 2px -4px rgba(0, 0, 0, 0.15),
				0 -20px 0 -10px #eee,
				0 -20px 2px -9px rgba(0, 0, 0, 0.15);
			padding: 30px;
		}
		.buttonForward {
			background: #ff7c30;
			border-radius: 8px;
			font-size: 20px;
			font-family: arial, serif;
			cursor: pointer;
			padding: 10px 15px;
			color: white;
			text-align: center;
			bottom: 40px;
			position: fixed;
			right: 75px;
			min-width: 160px;
		}
		.buttonForward:hover {
			background: #f25900;
		}
		.buttonNext {
			background: #ff7c30;
			border-radius: 8px;
			font-size: 20px;
			font-family: arial, serif;
			cursor: pointer;
			padding: 10px 15px;
			color: white;
			text-align: center;
			bottom: 120px;
			position: fixed;
			right: 75px;
			min-width: 160px;
		}
		p span.cardVal {
			font-size: 34px;
			text-align: center;
		}
		.buttonNext:hover {
			background: #f25900;
		}
		#txtStatus6 {
			color: red;
		}
		#squareMainCard btn-group button:disabled, #Speedbtn:disabled, #Cargobtn:disabled, #Sizebtn:disabled, #Rangebtn:disabled, #Firepowerbtn:disabled {
			/* background-color: rgb(102, 102, 102); */
			cursor: default !important;
		}
		#squareMainCard btn-group button:disabled:hover,  #Speedbtn:disabled:hover, #Cargobtn:disabled:hover, #Sizebtn:disabled:hover, #Rangebtn:disabled:hover, #Firepowerbtn:disabled:hover {
			 background-color: rgb(102, 102, 102) !important;/* #FF7C30 !important;*/
		}
	</style>

</head>

<body onload="initialize()">
	<!-- Call the initialize method when the page loads -->


	<div class="container-fluid">
		<div class="container-fluid">





			<!-- The Modal-->

			<div id="popup" class="modal">

				<!-- Modal content -->
				<div class="modal-content">

					<h1 id="txtWinner"></h1>

					<p>Play another game or return to Menu to view statistics
						players </p>
					<div class="row" style="display:block;align:center">
						<div class="row" style="display:block;">
							<p>
								<button class="modalButton" id="playButton" onclick= showButtons();>PLAY</button>
								<button class="modalButton" id="goBack3" onclick=quitGame();>MENU</button>
							</p>
						</div>
						<div class="row" style="display:block;">
							<div class="textAI">Select number of AI players to play against:</div>
							<div class="buttonAI" onclick = chosenNumberOfPlayers(1);>1</div>
							<div class="buttonAI" onclick = chosenNumberOfPlayers(2);>2</div>
							<div class="buttonAI" onclick = chosenNumberOfPlayers(3);>3</div>
							<div class="buttonAI" onclick = chosenNumberOfPlayers(4);>4</div>
						</div>


					</div>
					</span>


				</div>


			</div>



			<div id="startModal" class="modal">

				<!-- Modal content -->
				<div class="modal-content">

					<h1 id="txtWinner">Welcome to Top Trumps</h1>

					
					<div class="row" style="display:block;align:center">

						<button class="modalButton" id="playButton" onclick = initializeGame();>PLAY</button>
						<button class="modalButton" id="goBack3" onclick=goBack();>MENU</button>
						
   

					</div>
					</div>	


				</div>


		




			<div id="modalOut" class="modal">

				<!-- Modal content -->
				<div class="modal-content">

					<h1 id="txtWinner">You're out!</h1>

					<p> </p>
					<div class="row" style="display:block;align:center">

						<button class="modalButton" id="playButton" onclick=$("#modalOut").hide();>Continue playing</button>
						<button class="modalButton" id="goBack3" onclick=goBack();>MENU</button>

					</div>
				


				</div>


			</div>

			<div class="row" id="content">
				<div class="col-sm-1" style="width:20%">
					<button class="goBackButton" id="goBack2" onclick=goBack();>Go <br> Back</button>
				</div>

				<div class="col-sm-9" style="width:80%">

					<div class="gallery" id="card1">
						<div class="label" style="padding-left:5px;padding-right: 5px">
							<p style="text-align:left;" id="p1"> Player 1
								<span class="deckVal" style="float:right;color: #f25900" id="p1deck"> <b> # </b> </span>
						</div>
						</span>
						
							<img src="#" id="img1" alt="Player 1" style="width:177px;height:110px;">
						
						<div class="desc">
							<p> <span id="name1">Name of card </span>
								<br> <span class="cardVal" id="cat1"> # </span> </p>
						</div>
					</div>


					<div class="gallery" id="card2">
						<div class="label" style="padding-left:5px;padding-right: 5px">
							<p style="text-align:left;" id="p2"> Player 2
								<span class="deckVal" style="float:right;color: #f25900" id="p2deck"> <b> </b> </span>
						</div>
						</span>
						
							<img src="#" id="img2" alt="Player 2" style="width:177px;height:110px;">
						
						<div class="desc">
							<p> <span id="name2">Name of card </span>
								<br> <span class="cardVal" id="cat2"> # </span> </p>
						</div>
					</div>

					<div class="gallery" id="card3">
						<div class="label" style="padding-left:5px;padding-right: 5px">
							<p style="text-align:left;" id="p3"> Player 3
								<span class="deckVal" style="float:right;color: #f25900" id="p3deck"> <b> # </b> </span>
						</div>
						</span>
						
							<img src="#" id="img3" alt="Player 3" style="width:177px;height:110px;">
						
						<div class="desc">
							<p> <span id="name3">Name of card </span>
								<br> <span class="cardVal" id="cat3"> # </span> </p>
						</div>
					</div>

					<div class="gallery" id="card4">
						<div class="label" style="padding-left:5px;padding-right:5px">
							<p style="text-align:left;" id="p4"> Player 4
								<span class="deckVal" style="float:right;color: #f25900" id="p4deck"> <b> # </b> </span>
						</div>
						</span>
						
							<img src="" id="img4" alt="Player 4" style="width:177px;height:110px;">
						
						<div class="desc">
							<p> <span id="name4">Name of card </span>
								<br> <span class="cardVal" id="cat4"> # </span> </p>
						</div>
					</div>

				</div>


				<div class="row">
					<div class="col-sm-12">
						<div class="rectangleBackGround">
							<div class="textSquare">

								<p><span><b>Category:</b></span>
									<span id="txtCat1"> </span><br>

									<span><b>Turn:</b></span>
									<span id="txtTurn2"> </span><br>

									<span><b>Winner:</b></span>
									<span id="txtWin3"> </span><br>

									<span><b>Round:</b></span>
									<span id="txtRound4"> </span><br>

									<span><b>Cards in your deck:</b></span>
									<span id="txtDeck5"></span><br>

									<span id="txtStatus6"></span>
								</p>
							</div>
							<div class="buttonNext" id="nextRound" onclick=startARound();>Next Round</div>
							<div class="buttonForward" id="fastForward" onclick=fastforward(false);>Fast Forward</div>
						</div>


						<div class="col-sm-9">
							<div class="squareMainCard" id="squareMainCard">
								<img src="#" id="img5" alt="Player 0" style="width:247px;height:145px;padding:5px">
								<div class="desc" id="name5"> Description</div>
								<div class="btn-group">
									<ul style="padding-left: 4px">
										<button id="Speedbtn" onclick=chosenCategory("Speed");>Speed <var class="var" id="c1"></var></button>

										<button id="Cargobtn" onclick=chosenCategory("Cargo");>Cargo <var class="var" id="c2"></var></button>

										<button id="Sizebtn" onclick=chosenCategory("Size");>Size <var class="var" id="c3"></var></button>

										<button id="Rangebtn" onclick=chosenCategory("Range");>Range <var class="var"
												id="c4"></var></button>

										<button id="Firepowerbtn" onclick=chosenCategory("Firepower");>Firepower <var class="var"
												id="c5"></var></button>
									</ul>

								</div>
							</div>

							<div class="col-sm-3">
								<div class="cardPile">
									<p id="commPile">
										<h5>
											<b>Communal Pile</b>
										</h5>
									</p><br>
									<p>
									<span class="cardVal"	id="commNum">
											0
									</span>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


	</div>

	<script type="text/javascript">
		// Method that is called on page load
		function initialize() {
			$(".modal").hide();
			$("#startModal").show();
		}
		function initializeGame() {
			$(".modal").hide();
			// newGame();
			initialiseGame();
			
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
		//######################################################################################################
		//Function that goes through the testresponse object and display whats not null to the screen.
		function testResponse(jsonAsString) {
			if (jsonAsString == "state error") {
				$('#txtStatus6').html("You can't do that right now!");
			} else if (jsonAsString == null){
				//do nothing
			} else {
				var response = JSON.parse(jsonAsString);
			
			//if (jsonAsString === "state error") {
			//	alert("Function cant be used in this state");
			//	}
			$('.gallery').hide();
			$('.btn-group').find('button').prop('disabled', true);
			
			if (response.overall_winner != null) {
				
				$("#popup").show();
				var winner = (response.overall_winner == 0) ? "You " : "Player " + response.overall_winner;
				$("#txtWinner").html(winner + " won the game!");
			
			}
				
			
			// done at the start of every request, since round number is never 0
			if (response.round != null) {
				$("#txtRound4").html(response.round);
				if (response.round == 1) {
					initialPlayerDecksize(response);
				}
				resetHighlightedElements();
				$('#txtStatus6').html("");
			}
			if (response.communal_pile_size != null) {
				$("#commNum").html(response.communal_pile_size);
				if (response.communal_pile_size == 0) {
					$('.cardPile').css({'box-shadow':'none'})
				} else {
					$('.cardPile').css({'box-shadow': '0 -1px 2px rgba(0, 0, 0, 0.15), 0 -10px 0 -5px #eee, 0 -10px 2px -4px rgba(0, 0, 0, 0.15), 0 -20px 0 -10px #eee, 0 -20px 2px -9px rgba(0, 0, 0, 0.15)'});
				}
			}
			if (response.category != null) {
				$("#txtCat1").text(response.category);
				$("#" + response.category + "btn").css({"background-color": "rgb(248, 190, 157)"});
			}
			if (response.decksize !== null) {
				$("#txtDeck5").html(response.decksize);
			}
			if (response.player_values != null) {
				setPlayersValues(response);
			}
			if (response.winning_player != null) {
				$('#txtTurn2').html('Your Turn Ended');
				if (response.winning_player == 0) {
					$("#txtWin3").html("You won!");
					$("#squareMainCard").css({'background-color': 'rgb(141, 205, 240)'});
				} else {
					$("#txtWin3").html("Player " + response.winning_player + " won");
					$("#card" + response.winning_player).css({'background-color': 'rgb(141, 205, 240)'});
				}
			} else {
				$("#txtWin3").html('It was a draw!');
			}
			if (response.card != null) {
				var cardArray = Object.values(response.card.valueMap);
				setHumanCard(cardArray);
				$("#name5").html(response.card.cardName);
				$("#img5").attr("src", "/assets/card_images/" + response.card.cardName + ".jpg");
			}
			if (response.current_player != null) {
				if (response.current_player == 0) {
					//$('.cardVal').hide();
					$('#txtTurn2').html('You choose a category!');
					$("#txtWin3").html("");
					$('.btn-group').find('button').prop('disabled', false);
				} else {
					$("#txtTurn2").html("Player " + response.current_player);
				}
			}
			if (response.losing_players != null) {
				if (response.losing_players.indexOf(0) != -1){
					$("#modalOut").show();
				}
				$('#txtStatus6').html("Losing players this round: " + response.losing_players);
				// alert("player out decksizes length: " + response.playerDeckSizes.length);
			} 
	
			if (response.playerDeckSizes != null && response.playerDeckSizes.length == 1) {
				$("#popup").show();
				var winner = (response.overall_winner == 0) ? "You " : "Player " + response.overall_winner;
				$("#txtWinner").html(winner + " won the game!");
			}
			if (response.was_quit) {
				windows.location = "http://localhost:7777/toptrumps/";
			}
		}
		}
		//Function that initially sets players deck of cards
		function initialPlayerDecksize(response) {
			if (response.player_values != null) {
				if (response.player_values.length === 3) {
					$(' .deckVal').each(function () {
						$(this).text(response.decksize - 1)
					});
				} else {
					$(' .deckVal').each(function () {
						$(this).text(response.decksize)
					});
				}
			}
		}
		//Function that displays the AIplayers card values and number of cards. 
		//Takes in a 2d-array and a string as parameters
		function setPlayersValues(response) {
			var array = response.playerCardNames;
			array.sort();
			// set player deck size values to 0
			for (var i = 0; i < 5; i++) {
				if (i == 0) {
					$("#txtDeck5").html("0");
				} else {
					$("#p" + i + "deck").html("0");
				}
			}
			// set updated player deck sizes for end of round
			for (var i=0; i < response.playerDeckSizes.length; i++) {
				var player = response.playerDeckSizes[i][0];
				if (player == 0) {
					$("#txtDeck5").html(response.playerDeckSizes[i][1]);
				} else {
					$("#p" + player + "deck").html(response.playerDeckSizes[i][1]);
				}
			}
			// set player round information for round which was just played
			for (var i = 0; i < response.player_values.length; i++) {
				var value = response.player_values[i][0];
				$("#card" + value).show();
				$("#cat" + value).html(response.player_values[i][1]).show();
				$("#img" + value).attr("src", "/assets/card_images/" + array[i][1] + ".jpg");
				$("#name" + array[i][0]).html(array[i][1]);
			}
		}
		function resetHighlightedElements() {
			$("div.btn-group button").css({"background-color": "#ff7c30"});
			// $("div.btn-group button:disabled").css({"background-color": "rgb(102, 102, 102)"});
			$("#card1").css({'background-color': '#F1F1F1'});
			$("#card2").css({'background-color': '#F1F1F1'});
			$("#card3").css({'background-color': '#F1F1F1'});
			$("#card4").css({'background-color': '#F1F1F1'});
			$("#squareMainCard").css({'background-color': '#F1F1F1'});
		}
		//Function that displays the humanplayers card values, takes an array as parameter.
		function setHumanCard(array) {
			for (var i = 0; i < array.length; i++) {
				$("#c" + (i + 1)).text(array[i]);
			}
		}
		
		
		
		function showButtons() {
			$(".buttonAI").css({"display" : "inline", "text-align" : "center"});
			$(".textAI").show();
			}
		function quitGame() {
			$("#popup").hide();
			window.location = "http://localhost:7777/toptrumps/";
			
		}
		function goBack() {
			window.location = "http://localhost:7777/toptrumps/";
		}
		// function newGame() {
		// 	var xhr = createCORSReq('GET', "http://localhost:7777/toptrumps/game/newGame");
		// 	xhr.send();
		// 	xhr.onload = function (e) {
		// 		testResponse(xhr.response);
		// 	}
		// }
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
				if (xhr.response == "state error"){
					testResponse(xhr.response);
				} else {
					var result = JSON.parse(xhr.response);
					if (result.state == "round ended" && (result.losing_players == null || skipLosingPlayers)) {
						fastforward(skipLosingPlayers);
					} else {
						testResponse(xhr.response);
					}
				}
			}
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
  			// 		("CORS not supported");
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