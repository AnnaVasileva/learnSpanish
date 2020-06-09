var deckJson;
var cuurentCardIndex = 0;
var card;

function nextFlashcard(deck) {
    deckJson = JSON.parse(deck);
    document.getElementById("nextCardBtn").click = getNextCard(deckJson);
    if (cuurentCardIndex > 1) {
        document.getElementById("prevCardBtn").style.display = 'block';
    }
}

function getNextCard(deck) {
    document.getElementById("vocBox").style.backgroundColor = "#FFC312";
    document.getElementById("vocBoxContent").style.color = "white";

    if (cuurentCardIndex < deck.length) {
        document.getElementById("flashcard").innerHTML = deck[cuurentCardIndex].front;
        card = deck[cuurentCardIndex];
        cuurentCardIndex += 1;
    } else {
        document.getElementById("flashcard").innerHTML = "Congrats you have finished this deck!";
        document.getElementById("nextCardBtn").style.display = 'none';
        document.getElementById("doneBtn").style.display = 'block';
        cuurentCardIndex = deck.length + 1;
        card = null;
        finishedDeck = true;
    }
};

function getPrevCard() {
    document.getElementById("vocBox").style.backgroundColor = "#FFC312";
    document.getElementById("vocBoxContent").style.color = "white";

    cuurentCardIndex -= 2;
    document.getElementById("doneBtn").style.display = 'none';

    if (cuurentCardIndex <= 0) {
        document.getElementById("prevCardBtn").style.display = 'none';
    }

    document.getElementById("flashcard").innerHTML = deckJson[cuurentCardIndex].front;
    document.getElementById("nextCardBtn").style.display = 'block';
    card = deckJson[cuurentCardIndex];
    cuurentCardIndex += 1;

};

function flipFlashcard() {
    if (card != null) {
        document.getElementById("flashcard").click = flipCard(card);
    }
}

function flipCard(card) {

    var innerCardValue = document.getElementById("flashcard").innerHTML;
    var frontCardValue = card.front;
    var backCardValue = card.back;

    if (innerCardValue == frontCardValue) {
        document.getElementById("vocBox").style.transform = "rotateY(180deg)";
        document.getElementById("vocBox").style.backgroundColor = "whitesmoke";
        document.getElementById("vocBoxContent").style.color = "black";
        document.getElementById("vocBoxContent").style.transform = "rotateY(180deg)";
        document.getElementById("flashcard").innerHTML = backCardValue;
    } else {
        document.getElementById("vocBox").style.transform = "rotateY(0deg)";
        document.getElementById("vocBox").style.backgroundColor = "#FFC312";
        document.getElementById("vocBoxContent").style.color = "white";
        document.getElementById("vocBoxContent").style.transform = "rotateY(0deg)";
        document.getElementById("flashcard").innerHTML = frontCardValue;
    }
}