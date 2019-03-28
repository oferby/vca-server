var visible = false

var intent = {};
var dialogue = {};

function sendIntent(userInput){
    intent.text = userInput;
    console.log('Sending user input to server');
    dialogue.text = userInput
    stompClient.send("/app/parseDialogue", {}, JSON.stringify(dialogue));
}

function connect() {
    var socket = new SockJS('/intent-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/dialogue', function (hint) {
            dialogue  = JSON.parse(hint.body);
//            if (res.status == 'DONE') {
//            }
            addBotResponse(dialogue);

        });

    });
}

function showChatbot() {

    visible = !visible;
    if (visible) {
        $('#chatbox-main').show();
    } else {
        $('#chatbox-main').hide();
    }

}

function addUserInput(text) {

    $('#history').append('<div class="user-text-line"><span class="user-text">'+text+'</span></div>');

}


function scroll_window(){
    var element = document.getElementById("chatbot-body");
    element.scrollTop = element.scrollHeight - element.clientHeight;

}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

function addBotResponse(dialogue){



    async function displayWait() {

        $('#history').append('<div id="waiting-div" class="bot-text-line"><img class="waiting-gif" src="/images/sa.gif"></div>');
        scroll_window();
        await sleep(2000);
        $('#waiting-div').remove();

         var text = dialogue.text
         $('#history').append('<div class="bot-text-line"><span class="bot-text">'+text+'</span></div>');

         var urlUp = dialogue.feedbackUrl+'/up';
         var urlDown = dialogue.feedbackUrl+'/down';

        $('#history').append('<div class="bot-text-line"><span class="bot-text">Was this helpful?</span><img class="feedback-btn" src="/images/up.png" onclick="javascript:sendFeedback('+urlUp+')"><img src="/images/down.png" class="feedback-btn" onclick="javascript:sendFeedback('+urlDown+')"></div>');
        scroll_window();

    }

    displayWait()

}

$(document).ready( function(){

    connect()

    $('#user-input').keyup(function(e){
        if(e.keyCode == 13)
        {
            var userInput = $('#user-input').val();
            $('#user-input').val('');
            addUserInput(userInput);
            scroll_window();
            sendIntent(userInput)
            console.log('user entered: ' + userInput);
        }
    });

})