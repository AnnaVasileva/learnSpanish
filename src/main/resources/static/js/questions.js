// Thanks for the help, webdevtrick (https://webdevtrick.com)
function createQuiz(questionsFromDb, lessonNumber) {

    function Quiz(questions) {
        this.score = 0;
        this.questions = questions;
        this.questionIndex = 0;
    }

    Quiz.prototype.getQuestionIndex = function() {
        return this.questions[this.questionIndex];
    }

    Quiz.prototype.guess = function(answer) {
        if (this.getQuestionIndex().isCorrectAnswer(answer)) {
            this.score++;
        }

        this.questionIndex++;
    }

    Quiz.prototype.isEnded = function() {
        return this.questionIndex === this.questions.length;
    }


    function Question(text, choices, answer) {
        this.text = text;
        this.choices = choices;
        this.answer = answer;
    }

    Question.prototype.isCorrectAnswer = function(choice) {
        return this.answer === choice;
    }


    function populate() {
        if (quiz.isEnded()) {
            showScores();
        } else {
            // show question
            var element = document.getElementById("question");
            element.innerHTML = quiz.getQuestionIndex().text;

            // show options
            var choices = quiz.getQuestionIndex().choices;
            for (var i = 0; i < choices.length; i++) {
                var element = document.getElementById("choice" + i);
                element.innerHTML = choices[i];
                guess("option" + i, choices[i]);
            }

            showProgress();
        }
    };

    function guess(id, guess) {
        var button = document.getElementById(id);
        button.onclick = function() {
            quiz.guess(guess);
            populate();
        }
    };


    function showProgress() {
        var currentQuestionNumber = quiz.questionIndex + 1;
        var element = document.getElementById("progress");
        element.innerHTML = "Question " + currentQuestionNumber + " of " + quiz.questions.length;
    };

    function showScores() {
        var gameOverHTML = "<div class='result'>Your score: " + quiz.score + " / " + questions.length + "</div>";
        var nextLesson = Number(lessonNumber) + 1;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/practice/practiceUp-" + lessonNumber, true);
        xhr.setRequestHeader('Content-Type', 'application/json');

        if (quiz.score == questions.length) {
            gameOverHTML += "<div class='resultMessage'>Congratulations! You have perfect score!</div>";
            xhr.send(JSON.stringify({
                session: "${session.email}"
            }));
            gameOverHTML += "<div class='button-container'><button type='submit' class='btn float-right create_btn yellow_btn'><a id='tryAgainButton' href='/practice/lesson-" + nextLesson + "'>Next</a></button></div>";
        } else if (quiz.score >= (questions.length / 2)) {
            gameOverHTML += "<div class='resultMessage'>Well done!</div>";
            xhr.send(JSON.stringify({
                session: "${session.email}"
            }));
            gameOverHTML += "<div class='button-container'><button type='submit' class='btn float-right create_btn yellow_btn'><a id='tryAgainButton' href='/practice/lesson-" + nextLesson + "'>Next</a></button></div>";
        } else {
            gameOverHTML += "<div class='resultMessage'>Keep on training to improve your score.</div>";
            gameOverHTML += "<div class='btn float-right create_btn yellow_btn'><a id='tryAgainButton' href='/practice/lesson-" + lessonNumber + "'>Try again</a></div>";
        }

        var element = document.getElementById("lessonContent");
        element.innerHTML = gameOverHTML;
    };

    // create questions here
    var questionsJSON = JSON.parse(questionsFromDb);
    var questions = [];

    for (var i = 0; i < questionsJSON.length; i++) {
        var q = new Question(questionsJSON[i].text, questionsJSON[i].choices, questionsJSON[i].answer);
        questions.push(q);
    }

    // create quiz
    var quiz = new Quiz(questions);

    // display quiz
    populate();
}