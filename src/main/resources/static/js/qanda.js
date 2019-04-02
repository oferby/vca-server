var qa_data;
var qa_paragraph;
var qa_questions;
var index = 0;
var app;


function next() {

    if (qa_data.length > index + 1 ) {
        index+=1;
        qa_paragraph.text = qa_data[index].paragraph;
        qa_questions.list = qa_data[index].questions;
    }

}

function back() {
    if (index > 0) {
        index-=1;
        qa_paragraph.text = qa_data[index].paragraph;
        qa_questions.list = qa_data[index].questions;


    }

}

function update() {

    var l = qa_data.length

}


Vue.component('qa-item', {
  props: ['qa_par'],
  template: '<div class="qa-item"><textarea class="qa_paragraph" v-model="qa_par.text"></textarea></div>'
})

Vue.component('qa-questions', {
    props: ['qa_q'],
    template: '<div><textarea class="qa_questions" v-model="qa_q" placeholder="Write your question here."></textarea><button v-on:click="$emit(\'remove\')">X</button><button v-on:click="$emit(\'update\', qa_q)">V</button></div>'

})


function success(data){
    console.log( "success!" );
    qa_data = data;
    qa_paragraph = {text: qa_data[index].paragraph}
    qa_questions = {list : qa_data[index].questions}

    app = new Vue({
      el: '#app',
      data: {
        qap: qa_paragraph,
        qaq: qa_questions
      }
    })

}

$( document ).ready(function() {
    console.log( "ready!" );

    $.ajax({
      dataType: "json",
//      url: "http://0f7e4450.ngrok.io/data/qa",
      url: "http://10.100.99.123:8080/data/qa",
      success: success
    });

});
