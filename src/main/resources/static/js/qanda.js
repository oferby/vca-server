var qa_data;

var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue from js!'
  }
})

var app5 = new Vue({
  el: '#app-5',
  data: {
    message: 'Hello Vue.js!'
  },
  methods: {
    reverseMessage: function () {
      this.message = this.message.split('').reverse().join('')
    }
  }
})


var app6 = new Vue({
  el: '#app-6',
  data: {
    message: 'Hello Vue!'
  }
})


Vue.component('todo-item', {
  props: ['todo'],
  template: '<li>{{ todo.paragraph }} {{todo.id}}</li>'
})



function success(data){
    console.log( "success!" );

    qa_data = data;

    var app7 = new Vue({
      el: '#app-7',
      data: {
        qandaList: qa_data

      }
    })

}

$( document ).ready(function() {
    console.log( "ready!" );

    $.ajax({
      dataType: "json",
      url: "http://localhost:8080/data/qa",
      success: success
    });

});
