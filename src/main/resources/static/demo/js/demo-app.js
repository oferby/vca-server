var dialogue = {};
var visible = false;
var baseUrl = '/api/rasa';

function getTime() {
    var today = new Date();
    return today.getHours() + ":" + today.getMinutes();
};


function showChatbot() {

    visible = !visible;
    if (visible) {
        $('#smartbot').show();



    } else {
        $('#smartbot').hide();
    }
};

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


function addBotResponse(dialogue){

    async function displayWait() {

        $('#smartbotBody').append('<div id="waiting-div" class="messageBox incoming"><div class="messageText"><div class="waiting"><img src="/images/waiting.gif" alt="Enter"></div></div></div>');
        scroll_window();
        await sleep(1500);
        $('#waiting-div').remove();
        var text = dialogue.text
        addBotText(text);
        scroll_window();

    }

    displayWait()

}


function addBotText(text) {

    $('#smartbotBody').append('<div class="messageBox incoming"><div class="messageText">'+text+'</div><div class="messageTime">'+getTime()+'</div></div>');

}


function send(text, url, callback) {
    dialogue.text = text
    var jsonData = JSON.stringify(dialogue);
    $.ajax({
        dataType: "json",
        processData: false,
        contentType: 'application/json',
        url: url,
        data: jsonData,
        method: "POST",
        success: callback
    });

}


$(document).ready( function(){

    $('#user-input').keyup(function(e){
        if(e.keyCode == 13)
        {
            var userInput = $('#user-input').val();
            $('#user-input').val('');
            addUserInput(userInput);
            scroll_window();
            send(userInput, baseUrl, addBotResponse);
            console.log('user entered: ' + userInput);
        }
    });

    async function displayGreeting() {
        await sleep(1000);
        send('hello', baseUrl, addBotResponse)
    }

    displayGreeting();

})