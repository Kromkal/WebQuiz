function f(id) {
    //let answer = $('input[name=check]:checked')
    let arr2 = [];

    let answer = [];
    let answers = [];
    let answer_values = [];
    let size = document.getElementsByClassName("quiz");
    let name = "check";

    console.log(1);

    for (let i = 0; i < size.length; i++) {
        answer = document.getElementsByName("" + name + i);

        //console.log(answer.length)
        for (j = 0; j < answer.length; j++) {
            if(answer[j].checked) {
                console.log(answer[j].value)
                answer_values.push(answer[j].value)
            }
        }
        let test = {answer: answer_values}
        answers.push(test)
        answer_values = []



    }

    /*for (i = 0; i < size; i++) {
        name = "check" + size;
    }

    for (i = 0; i < answer.length; i++) {
        arr2.push(answer[i].value)
    }

    for (i = 0; i < arr2.length; i++) {
        answer.push(arr2);
    }*/

    const json = {
        //userQuizAnswers: answers
        userQuizAnswers: answers
        //userQuizAnswers: [{answer: [0,1]}, {answer: [1,2]}]
    }



    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/api/quizzes/' + id + '/solve/',true);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            for (let i = 0; i < size.length; i++){
                let style = document.getElementById("test" + i).style;
                style.padding = '20px';
                style.borderRadius = '10px';
                style.opacity = '0.9';


                if (xhr.response[i] === "1") {
                    style.background = 'MediumSpringGreen';
                } else {
                    style.background = 'Salmon';
                }
            }
        }
    };
    xhr.send(JSON.stringify(json));
}


