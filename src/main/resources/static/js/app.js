var visible = false
var greetingSent = false;

var intent = {};
var dialogue = {};

function getTime() {
    var today = new Date();
    return today.getHours() + ":" + today.getMinutes();
}

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
        $('#smartbot').show();

        async function displayGreeting() {
            await sleep(1000);
            addBotText('Hello, how may I help you?')
            greetingSent = true;
        }

        if (!greetingSent) {
            displayGreeting()
        }
    } else {
        $('#smartbot').hide();
    }
}

function addUserInput(text) {

    $('#smartbotBody').append('<div class="messageBox outgoing"><div class="messageText">'+text+'</div><div class="messageTime">'+getTime()+'</div></div>');

}

function scroll_window(){
    var element = document.getElementById("smartbotBody");
    element.scrollTop = element.scrollHeight - element.clientHeight;

}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

function addBotText(text) {

    $('#smartbotBody').append('<div class="messageBox incoming"><div class="messageText">'+text+'</div><div class="messageTime">'+getTime()+'</div></div>');

}



function addBotResponse(dialogue){

    async function displayWait() {

        $('#smartbotBody').append('<div id="waiting-div" class="messageBox incoming"><div class="messageText"><div class="waiting"><img src="/images/waiting.gif" alt="Enter"></div></div></div>');
        scroll_window();
        await sleep(1500);
        $('#waiting-div').remove();

         var text = dialogue.text
         addBotText(text);

//         var urlUp = dialogue.feedbackUrl+'/up';
//         var urlDown = dialogue.feedbackUrl+'/down';
//
//        $('#history').append('<div class="bot-text-line"><span class="bot-text">Was this helpful?</span><img class="feedback-btn" src="/images/up.png" onclick="javascript:sendFeedback('+urlUp+')"><img src="/images/down.png" class="feedback-btn" onclick="javascript:sendFeedback('+urlDown+')"></div>');
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