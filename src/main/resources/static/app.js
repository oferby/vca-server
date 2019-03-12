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
            addBotResponse(dialogue.text);

        });

    });
}

function showChatbot() {

    console.log('clicked!');
    visible = !visible;
    if (visible) {
        $('#chatbox-main').show();
    } else {
        $('#chatbox-main').hide();
    }

}

function addUserInput(text) {

    $('#history').append('<div class="user-text-line"><span class="user-text">'+text+'</span></div>')

}

function addBotResponse(text){

    $('#history').append('<div class="bot-text-line"><span class="bot-text">'+text+'</span></div>')
}

$(document).ready( function(){

    connect()

    $('#user-input').keyup(function(e){
        if(e.keyCode == 13)
        {
            var userInput = $('#user-input').val()
            $('#user-input').val('');
            addUserInput(userInput);
            sendIntent(userInput)
            console.log('user entered: ' + userInput);
        }
    });

})